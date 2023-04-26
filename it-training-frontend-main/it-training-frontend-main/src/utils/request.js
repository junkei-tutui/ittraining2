import axios from "axios";
import { MessageBox, Message, Loading } from "element-ui";
import store from "@/store";
import { getToken } from "@/utils/auth";

// Blobでのエラー時にJsonがBlobで連携される際の対応
import fileReader from "@/utils/fileReader";

// API実行中のLoading表示対応
let loading = null;

// API実行カウント（多重実行カウント）
let needLoadingRequestCount = 0;

// API実行開始
const startLoading = () => {
  loading = Loading.service({
    lock: true,
    text: "Loading……",
    target: document.querySelector(".mixin-components-container"),
    background: "rgba(0, 0, 0, 0.7)",
  });
};

// API実行終了
const endLoading = () => {
  loading.close();
};

// 実行中画面表示開始制御
const showFullScreenLoading = () => {
  if (needLoadingRequestCount === 0) {
    // API実行開始（実行中画面を表示）
    startLoading();
  }
  needLoadingRequestCount++;
};

// 実行中画面表示終了制御
const tryHideFullScreenLoading = () => {
  if (needLoadingRequestCount <= 0) {
    // カウンタはマイナスとならないように制御する。
    needLoadingRequestCount = 0;
    return;
  }
  // カウンタのデクリメント
  needLoadingRequestCount--;
  if (needLoadingRequestCount === 0) {
    // API実行終了（実行中画面を閉じる）
    endLoading();
  }
};

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000, // request timeout
});

// request interceptor
service.interceptors.request.use(
  config => {
    // 非同期実行（notLoading指定）により実行中画面の表示/非表示を制御する。
    if (!config.notLoading) {
      // 実行中画面表示開始制御を行う。
      showFullScreenLoading();
    }
    // do something before request is sent
    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      /* -- @taining headerを今回のトークン様に変更する --*/
      // config.headers['X-Token'] = getToken()
      // config.headers["X-Auth-Token"] = getToken();
    }

    // API実行開始時メッセージ表示対応
    // API実行と同時にメッセージを表示する。（API連結時の連結前の結果メッセージ表示）
    if (typeof config.message !== "undefined") {
      if (config.message) {
        // 指定されたメッセージを表示する。
        console.log(config.message);
        // megSuccess(config.message)
      }
    }

    return config;
  },
  error => {
    // do something with request error
    console.error(error); // for debug
    return Promise.reject(error);
  },
);

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    // 実行中画面表示終了制御を行う。
    tryHideFullScreenLoading();

    /* -- HTTPステータスにてレスポンスチェックを行う -- */
    if (response.status !== 200) {
      Message({
        message: response.message || "Error",
        type: "error",
        duration: 5 * 1000,
      });
      return Promise.reject(new Error(response.message || "Error"));
    } else {
      return response;
    }
  },
  async (error) => {
    // 実行中画面表示終了制御を行う。
    tryHideFullScreenLoading();

    // errorJSONがBlobで連携される際の対応
    if (error.request.responseType === "blob") {
      // Blobの場合でJSONを読み込むためのハンドリングを追加します。
      const errorText = await fileReader(error.response.data);

      let errorJson = {};
      try {
        errorJson = JSON.parse(errorText);
      } catch (e) {
        console.error(e);
      }
      // メッセージが設定されている場合のみエラーメッセージを表示する。
      if (errorJson.errorMessage) {
        errorJson["isNonBlob"] = true;
        // エラーメッセージ表示処理
        if (error.config.isSelfErrorHandling) {
          // コードとメッセージを再設定し、Rejectする。
          const errorObj = {
            code: error.response.data.errorCode,
            message: error.response.data.errorMessage,
          };
          return Promise.reject(errorObj);
        } else {
          // メッセージを表示
          Message({
            message: errorJson.errorMessage,
            type: "error",
            duration: 5 * 1000,
          });
          // JSONデータのみReject
          return Promise.reject(errorJson);
        }
      } else {
        // 例外でない場合はBlobをそのまま返却
        return Promise.reject(error);
      }
    }

    // ネットワークエラー対応
    if (error.message === "Network Error") {
      if (!error.config.isSelfErrorHandling) {
        store.dispatch("user/resetToken").then(() => {
          location.replace("/404");
        });
      }
      // サーバーエラーとしてReject
      return Promise.reject(error);
    }

    // エラーのハンドリングを実施する。
    if (error.response && error.response.status === 401) {
      // 認証エラー（もしくはWAS側タイムアウト）の場合
      if (error.config.isSelfErrorHandling) {
        // ログイン処理による認可エラーの場合は、エラーメッセージを表示
        console.error("認証エラー", error);
        const errorObj = {
          code: error.response.data.errorCode,
          message: error.response.data.errorMessage,
        };
        return Promise.reject(errorObj);
      } else {
        const token = getToken();
        // 処理内での認証エラーの場合は再ログインを促す
        if (token && token === "Authenticated") {
          // 認証トークンが残っている場合はダイアログ表示
          MessageBox.confirm("セッションが切れたため、自動的にログアウトされました。再度ログインしてください。", "Information", {
            confirmButtonText: "OK",
            type: "warning",
            showClose: false,
            closeOnClickModal: false,
            closeOnPressEscape: false,
            showCancelButton: false,
          }).then(() => {
            store.dispatch("user/resetToken").then(() => {
              location.reload();
            });
          });
        } else {
          // 認証トークンが削除されている場合は、ログ出力し、エラーをそのまま返す。
          console.error("認可エラー（トークン消失後）", error);
          return Promise.reject(error);
        }
      }
    } else if (error.response && error.response.status === 403) {
      // 認可エラーの場合はサービス利用停止画面へ遷移する。
      console.error("認可エラー", error);
      const errorObj = {
        code: error.response.data.errorCode,
        message: error.response.data.errorMessage,
      };
      return Promise.reject(errorObj);
    } else if (error.code && error.code === "ECONNABORTED") {
      // APIタイムアウトの場合は警告を表示する
      error.message = "timeOut";
      // 接続タイムアウト
      Message({
        message: "接続がタイムアウトしました。再度操作をしてください。",
        type: "warning",
        duration: 5 * 1000,
      });
    } else {
      // 上記以外の場合はエラー表示
      if (error.response && error.response.data.errorMessage) {
        if (error.config.isSelfErrorHandling) {
          console.error("WASエラー", error);
          const errorObj = {
            code: error.response.data.errorCode,
            message: error.response.data.errorMessage,
          };
          return Promise.reject(errorObj);
        } else {
          // WAS側よりメッセージが指定している場合はそのメッセージを表示
          Message({
            message: error.response.data.errorMessage,
            type: "error",
            duration: 5 * 1000,
          });
          // UAT-028: エラーハンドリング修正
          return Promise.reject(error.response.data);
        }
      } else {
        // 上記以外はresponseに設定されたメッセージを表示
        if (error.config.isSelfErrorHandling) {
          console.error("Serverエラー", error);
          return Promise.reject("Server Error");
        } else {
          Message({
            message: error.message,
            type: "error",
            duration: 5 * 1000,
          });
        }
      }
    }
    return Promise.reject(error);
  },
);

export default service;
