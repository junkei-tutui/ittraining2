<template>
  <!--
   -- U-010 : 受注検索・一覧
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="18" class="card-title page-title">
            <span>受注検索・一覧</span>
          </el-col>
          <!-- （空きエリア） -->
          <el-col :span="3"><br /></el-col>
          <!--取込ボタン -->
          <el-col :span="3" align="center">
            <input
              ref="uploadOrderFile"
              style="display: none"
              type="file"
              accept="text/csv"
              @change="onSelectedUploadOrderFile"
              @click="onResetSelectedOrderFile"
            />
            <el-button
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="training-main-button"
              @click="onUploadOrderFile"
            >取込</el-button>
          </el-col>
        </el-row>
      </div>
      <!--画面コンテンツエリア -->
      <div class="card-content" style="min-height: calc(100vh - 210px)">
        <!--検索部 -->
        <div class="group-box">
          <span class="group-box-title">
            <span class="item-header">検索条件</span>
          </span>
          <el-form ref="form" label-position="left" :model="form">
            <el-row :gutter="10">
              <!-- 注文受付ステータス -->
              <el-col :span="3">
                <span class="grid-content item-header">注文受付ステータス</span>
              </el-col>
              <el-col :span="4">
                <div class="grid-content">
                  <tr-select
                    v-model="form.targetOrderStatus"
                    prop-name="targetOrderStatus"
                    :select-list="orderStatusSelectList"
                    key-name="code"
                    label-name="value"
                    item-label="注文受付ステータス"
                  />
                </div>
              </el-col>
              <!-- クライアント名 -->
              <el-col :span="3">
                <span class="grid-content item-header">クライアント名</span>
              </el-col>
              <el-col :span="4">
                <div class="grid-content">
                  <tr-input
                    v-model="form.likeClientName"
                    prop-name="likeClientName"
                    :maxlength="64"
                    item-label="クライアント名"
                    check-rule="default" />
                </div>
              </el-col>
              <!-- 受注日 -->
              <el-col :span="2">
                <span class="grid-content item-header">受注日</span>
              </el-col>
              <el-col :span="5">
                <div class="grid-content">
                  <tr-date-picker
                    v-model="form.targetOrderDate"
                    prop-name="targetOrderDate"
                    item-label="受注日" />
                </div>
              </el-col>
              <!-- 検索ボタン -->
              <el-col :span="3">
                <div class="grid-content">
                  <el-button
                    class="inner-card-button"
                    type="training-sub-button"
                    @click="showOrderList"
                  >検索</el-button>
                </div>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <!--一覧部 -->
        <el-table
          v-loading="listLoading"
          :data="
            orderList ?
              orderList.slice(
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

          <!-- 受注番号 -->
          <el-table-column
            width="150px"
            header-align="center"
            align="center"
            label="受注番号"
            prop="orderNo"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.orderNo | fillNo(10) }}</span>
            </template>
          </el-table-column>

          <!-- 受注日 -->
          <el-table-column
            width="150px"
            header-align="center"
            align="center"
            label="受注日"
            prop="orderDate"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.orderDate | dateFormatSlash }}</span>
            </template>
          </el-table-column>

          <!-- 納期 -->
          <el-table-column
            width="150px"
            header-align="center"
            align="center"
            label="納期"
            prop="deliveryDate"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.deliveryDate | dateFormatSlash }}</span>
            </template>
          </el-table-column>

          <!-- クライアント名 -->
          <el-table-column
            header-align="center"
            align="left"
            label="クライアント名"
            prop="clientName"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.clientName }}</span>
            </template>
          </el-table-column>

          <!-- 受注件名 -->
          <el-table-column
            header-align="center"
            align="left"
            label="受注件名"
            prop="orderSubject"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.orderSubject }}</span>
            </template>
          </el-table-column>

          <!-- 注文受付ステータス -->
          <el-table-column
            width="150px"
            header-align="center"
            align="left"
            label="ステータス"
            prop="orderStatus"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.orderStatus | constantsDicCaption(orderStatusDic) }}</span>
            </template>
          </el-table-column>

          <!-- 受注合計金額（税抜） -->
          <el-table-column
            width="150px"
            header-align="center"
            align="right"
            label="受注額（税抜）"
            prop="totalOrderAmount"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.totalOrderAmount | toThousandFilter }}</span>
            </template>
          </el-table-column>

          <!-- 編集 -->
          <el-table-column
            width="100px"
            prop="oper"
            align="center"
            label="編集"
          >
            <template slot-scope="scope">
              <el-button
                class="icon-button"
                type="primary"
                size="mini"
                icon="training-btn-line-icon el-icon-edit-outline"
                round
                @click="onEditOrder(scope.row)"
              />
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
              :total="orderList ? orderList.length : 0"
              @current-change="onCurrentChange"
            />
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b101GetOrderList, b102UploadOrderFile } from "@/api/order";
import { setViewStatus, getViewStatus, setParameter } from "@/utils/support";
import { messageSuccess, messageError } from "@/utils/support";
import { getFileNameFromResponseHeader } from "@/utils/fileReader";
import Dictionary from "@/definition/dictionary";

export default {
  // --- 画面名 ---
  name: "OrderList",
  // --- 表示フィルター関数定義 ---
  filters: {
    /**
     * 受注番号表示フィルター
     * @param {Number} no 受注番号
     * @param {Number} fixLen 固定表示桁数（桁不足時は左0重鎮）
     * @returns {String} - 固定長受注番号
     */
    fillNo: (no, fixLen) => {
      return String(no).padStart(fixLen, "0");
    },
    /**
     * 辞書表示フィルター
     * @param {String} code コード値
     * @param {Object} dic 辞書データ
     * @returns {String} - コードに対応した辞書の値（名称）
     */
    constantsDicCaption: (code, dic) => {
      return dic[code] ? dic[code] : code;
    },
  },
  // --- データプロパティ定義 ---
  data() {
    return {
      // リスト読み込み用ローディング画面設定フラグ
      listLoading: true,
      // ローディング画面設定（データ取込時に設定）
      fullscreenLoading: false,
      // 入力フォーム
      form: {
        targetOrderStatus: null,
        likeClientName: null,
        targetOrderDate: null,
      },
      // 受注一覧情報
      orderList: [],
      // ページネーション情報
      currentPage: 1,
      pagesize: 10,
    };
  },
  // --- 算出プロパティ定義 ---
  computed: {
    /**
     * 受注ステータス辞書データ
     */
    orderStatusDic: {
      get() {
        // 項目辞書をプロパティ設定
        return Dictionary.OrderStatus;
      },
    },
    /**
     * 受注ステータスコンボボックス表示用リスト
     */
    orderStatusSelectList: {
      get() {
        // 項目辞書をプルダウン用オブジェクトに変換しプロパティ設定
        return Dictionary.createSelectList(Dictionary.OrderStatus);
      },
    },
  },
  // --- 画面生成イベント ---
  created() {
    // 初期表示処理実行
    this.init();
  },
  // --- メソッド定義 ---
  methods: {
    /**
     *  初期表示
     */
    init() {
      // 画面初期情報は同期して取得する
      (async () => {
        // 検索条件パラメータを取得する。
        const conditions = await getViewStatus("orderListConditions");
        if (conditions) {
          // 保持していた検索条件（ステータス、クライアント名、注文日）を初期入力値として設定
          this.form.targetOrderStatus = conditions.targetOrderStatus;
          this.form.likeClientName = conditions.likeClientName;
          this.form.targetOrderDate = conditions.targetOrderDate;
        }
        // 受注一覧を取得する
        await this.showOrderList();
      })();
    },
    /**
     *  受注一覧取得
     */
    async showOrderList() {
      this.listLoading = true;
      // B101:受注一覧取得
      const paramater = {
        targetOrderStatus: this.form.targetOrderStatus,
        likeClientName: this.form.likeClientName,
        targetOrderDate: this.form.targetOrderDate,
      };
      await b101GetOrderList(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`orderList${JSON.stringify(response.data)}`);

        // ページネイションを行っている一覧の場合はページNoを1に戻す
        this.currentPage = 1;
        // 一覧データを画面情報に設定
        this.orderList = response.data;
        // ローディング解除
        this.listLoading = false;
        // 検索時の条件を保持する。
        setViewStatus("orderListConditions", {
          // 検索条件（ステータス、クライアント名、注文日）を保持
          targetOrderStatus: this.form.targetOrderStatus,
          likeClientName: this.form.likeClientName,
          targetOrderDate: this.form.targetOrderDate,
        });
      }).catch(
        // エラーの場合はローディングのみ解除
        this.listLoading = false,
      );
    },
    /**
     *  受注編集
     * @param { Object } row - 選択行情報
     */
    onEditOrder(row) {
      // 受注詳細画面へ遷移
      this.$router.push({ name: "OrderDetailEdit" });
      setParameter("OrderDetailEdit", {
        // 受注番号を設定
        orderNo: row.orderNo,
      });
    },
    /**
     *  受注取込
     */
    onUploadOrderFile() {
      // 実際のファイルアップロードコントローラーにアクション
      this.$refs.uploadOrderFile.click();
    },
    /**
     * 受注データファイルの選択リセット
     */
    onResetSelectedOrderFile() {
      // 選択したファイル情報をクリア
      this.$refs.uploadOrderFile.value = "";
    },
    /**
     * 受注データファイルのアップロード
     */
    async onSelectedUploadOrderFile() {
      // 選択したファイルを取得する。
      const rawFile = this.$refs.uploadOrderFile.files[0];
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
      // B102:受注データ取込
      await b102UploadOrderFile(formData)
        .then((response) => {
          // eslint-disable-next-line eqeqeq
          if (response.headers["content-length"] != 0) {
            // CSV検証エラーあり
            response.data = new Blob([response.data], {
              type: response.headers["content-type"],
            });
            this.handleFileDownloadResponse(response);
            // エラーメッセージ表示
            messageError("注文データファイルのアップロードに失敗しました。");
            // 画面ロック解除
            this.fullscreenLoading = false;
            return;
          }
          // 完了メッセージを表示
          messageSuccess("注文データファイルをアップロードしました。");
          // 画面をリロードする
          this.showOrderList();
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
     * レスポンスファイル（受注取込エラーファイル）のダウンロード
     * @param {*} response エラーファイル
     */
    handleFileDownloadResponse(response) {
      // エラーファイルのファイル名を取得する。
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
