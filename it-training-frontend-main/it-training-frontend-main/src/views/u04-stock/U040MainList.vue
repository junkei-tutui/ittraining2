<template>
  <!--
   -- U-040 : 在庫引当結果・一覧
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="18" class="card-title page-title">
            <span>在庫引当結果・一覧</span>
          </el-col>
          <!-- （空きエリア） -->
          <el-col :span="3"><br /></el-col>
          <!--取込ボタン -->
          <el-col :span="3" align="center">
            <input
              ref="uploadStockFile"
              style="display: none"
              type="file"
              accept="text/csv"
              @change="onSelectedUploadStockFile"
              @click="onResetSelectedStockFile"
            />
            <el-button
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="training-main-button"
              @click="onUploadStockFile"
            >取込</el-button>
          </el-col>
        </el-row>
      </div>
      <!--画面コンテンツエリア -->
      <div class="card-content" style="min-height: calc(100vh - 210px)">
        <!--一覧部 -->
        <el-table
          v-loading="listLoading"
          :data="
            uploadedStockList ?
              uploadedStockList.slice(
                (currentPage - 1) * pagesize,
                currentPage * pagesize
              ) : null
          "
          border
          fit
          highlight-current-row
          style="width: 100%; padding: 5px;"
        >
          <!-- 行番号 -->
          <el-table-column
            width="50px"
            header-align="center"
            label="#"
            align="center"
          >
            <template slot-scope="scope">
              <span class="grid-content-line-label">{{ pagesize * (currentPage-1) + scope.$index + 1 }}</span>
            </template>
          </el-table-column>

          <!-- アップロード日時 -->
          <el-table-column
            width="180px"
            header-align="center"
            align="center"
            label="アップロード日時"
            prop="uploadAt"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.uploadAt | datetimeFormatSlash }}</span>
            </template>
          </el-table-column>

          <!-- 担当者 -->
          <el-table-column
            width="150px"
            header-align="center"
            align="center"
            label="担当者"
            prop="uploadedUserName"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.uploadedUserName }}</span>
            </template>
          </el-table-column>

          <!-- 在庫ファイル名 -->
          <el-table-column
            header-align="center"
            align="left"
            label="在庫ファイル"
            prop="filenameStock"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.filenameStock }}</span>
            </template>
          </el-table-column>

          <!-- 引当後在庫ファイル -->
          <el-table-column
            header-align="center"
            align="left"
            label="引当後在庫ファイル"
            prop="filenameStockResult"
            sortable
          >
            <template slot-scope="scope">
              <span v-if="scope.row.filenameStockResult">
                <el-button
                  class="icon-button"
                  size="mini"
                  icon="training-btn-line-iconmini el-icon-download"
                  circle
                  @click="onDownloadResultStockFile(scope.row)"
                />
                {{ scope.row.filenameStockResult }}</span>
              <span v-else><br /></span>
            </template>
          </el-table-column>

          <!-- 引当不可リストファイル -->
          <el-table-column
            header-align="center"
            align="left"
            label="引当不可リストファイル"
            prop="filenameCannotreserve"
            sortable
          >
            <template slot-scope="scope">
              <span v-if="scope.row.filenameCannotreserve">
                <el-button
                  class="icon-button"
                  size="mini"
                  icon="training-btn-line-iconmini el-icon-download"
                  circle
                  @click="onDownloadNonReserveOrderFile(scope.row)"
                />
                {{ scope.row.filenameCannotreserve }}</span>
              <span v-else><br /></span>
            </template>
          </el-table-column>

        </el-table>
        <!-- ページネーション部 -->
        <el-row style="padding-top: 20px">
          <el-col :span="24" style="text-align: right">
            <el-pagination
              background
              layout="total, prev, pager, next"
              :page-size="pagesize"
              :total="uploadedStockList ? uploadedStockList.length : 0"
              @current-change="onCurrentChange"
            />
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b401GetUploadedStockList, b402UploadStockFile, b404DownloadStockResultFile, b405DownloadNonStockOrderFile } from "@/api/stock";
import { messageSuccess, messageError } from "@/utils/support";
import { getFileNameFromResponseHeader } from "@/utils/fileReader";

export default {
  // --- 画面名 ---
  name: "StockList",
  // --- 表示フィルター関数定義 ---
  filters: {
  },
  // --- データプロパティ定義 ---
  data() {
    return {
      // リスト読み込み用ローディング画面設定フラグ
      listLoading: true,
      // ローディング画面設定（データ取込時に設定）
      fullscreenLoading: false,
      // 在庫取込結果一覧情報
      uploadedStockList: [],
      // ページネーション情報
      currentPage: 1,
      pagesize: 10,
    };
  },
  // --- 算出プロパティ定義 ---
  computed: {},
  // --- 画面生成イベント ---
  created() {
    // 初期表示処理実行
    this.init();
  },
  // --- メソッド定義 ---
  methods: {
    // 初期表示
    init() {
      // 画面初期情報は同期して取得する
      (async () => {
        // 在庫取込結果一覧を取得する
        await this.showUploadedStockList();
      })();
    },
    /**
     *  在庫取込結果一覧取得
     */
    async showUploadedStockList() {
      this.listLoading = true;
      // B401:在庫取込結果一覧取得
      await b401GetUploadedStockList().then((response) => {
        // TODO: 取得データの確認用
        console.log(`uploadedStockList${JSON.stringify(response.data)}`);
        // ページネイションを行っている一覧の場合はページNoを1に戻す
        this.currentPage = 1;
        // 一覧データを画面情報に設定
        this.uploadedStockList = response.data;
        // ローディング解除
        this.listLoading = false;
      }).catch(
        // エラーの場合はローディングのみ解除
        this.listLoading = false,
      );
      this.listLoading = false;
    },
    /**
     * 在庫取込
     */
    onUploadStockFile() {
      // 実際のファイルアップロードコントローラーにアクション
      this.$refs.uploadStockFile.click();
    },
    /**
     * 在庫データファイルの選択リセット
     */
    onResetSelectedStockFile() {
      // 選択したファイル情報をクリア
      this.$refs.uploadStockFile.value = "";
    },
    /**
     * 在庫データファイルのアップロード
     */
    async onSelectedUploadStockFile() {
      // 選択したファイルを取得する。
      const rawFile = this.$refs.uploadStockFile.files[0];
      if (!rawFile) {
        // ファイル未選択の場合は処理を抜ける
        return;
      }
      // フォームデータを作成する。
      const formData = new FormData();
      formData.append("uploadFile", rawFile);
      formData.append("fileName", rawFile.name);
      // 画面ロック
      this.fullscreenLoading = true;
      await b402UploadStockFile(formData)
        .then((response) => {
          // eslint-disable-next-line eqeqeq
          if (response.headers["content-length"] != 0) {
            // CSV検証エラーあり
            response.data = new Blob([response.data], {
              type: response.headers["content-type"],
            });
            this.handleFileDownloadResponse(response);
            // エラーメッセージ表示
            messageError("在庫データファイルのアップロードに失敗しました。");
            // 画面ロック解除
            this.fullscreenLoading = false;
            return;
          }
          // 完了メッセージを表示
          messageSuccess("在庫データファイルをアップロードしました。");
          // 画面をリロードする
          this.showUploadedStockList();
          // 画面ロック解除
          this.fullscreenLoading = false;
        })
        .catch((error) => {
          // 画面ロック解除
          this.fullscreenLoading = false;
          // APIエラー以外の場合はメッセージを表示する。
          if (!error.errorMessage) {
            messageError(error);
          }
        });
    },
    /**
     * 引当後在庫ファイルダウンロード
     * @param {Object} row 選択行データ
     */
    onDownloadResultStockFile(row) {
      // B404:引当後在庫ファイル取得
      const param = {
        stockCaptureId: row.stockCaptureId,
      };
      b404DownloadStockResultFile(param)
        .then((response) => {
          // eslint-disable-next-line eqeqeq
          if (response.headers["content-length"] != 0) {
            // データが送信されている場合、ダウンロード処理を実施する。
            this.handleFileDownloadResponse(response);
            return;
          }
        })
        .catch();
    },
    /**
     * 引当不可リストファイルダウンロード
     * @param {Object} row 選択行データ
     */
    onDownloadNonReserveOrderFile(row) {
      // B405:引当不可リストファイル取得
      const param = {
        stockCaptureId: row.stockCaptureId,
      };
      b405DownloadNonStockOrderFile(param)
        .then((response) => {
          // eslint-disable-next-line eqeqeq
          if (response.headers["content-length"] != 0) {
            // データが送信されている場合、ダウンロード処理を実施する。
            this.handleFileDownloadResponse(response);
            return;
          }
        })
        .catch();
    },
    /**
     * レスポンスファイル（在庫取込エラーファイル）のダウンロード
     * @param {*} response エラーファイル
     */
    handleFileDownloadResponse(response) {
      const filename = getFileNameFromResponseHeader(response);

      // ダウンロードコンポーネントを作成し、ダウンロードを実施する。
      const fileURL = window.URL.createObjectURL(
        new Blob([response.data], { type: response.headers["content-type"] }),
      );
      const fileLink = document.createElement("a");
      fileLink.href = fileURL;
      fileLink.setAttribute("download", filename);
      document.body.appendChild(fileLink);
      fileLink.click();
    },
    /**
     *  ページネーション処理
     * @param { Number } currentPage - ページ番号
     */
    onCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
  },
};
</script>

<style scoped>
</style>
