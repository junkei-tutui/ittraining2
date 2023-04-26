<template>
  <!--
   -- U-020 : 取引先検索・一覧
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="18" class="card-title page-title">
            <span>取引先検索・一覧</span>
          </el-col>
          <!-- （空きエリア） -->
          <el-col :span="3"><br /></el-col>
          <!--追加ボタン -->
          <el-col :span="3" align="center">
            <el-button
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="training-main-button"
              @click="onAddPartner"
            >追加</el-button>
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
              <!-- 取引先名 -->
              <el-col :span="3">
                <span class="grid-content-header">取引先名</span>
              </el-col>
              <el-col :span="5">
                <div class="grid-content">
                  <tr-input
                    v-model="form.likePartnerName"
                    prop-name="likePartnerName"
                    :maxlength="64"
                    item-label="取引先名"
                    check-rule="default" />
                </div>
              </el-col>
              <!-- （空きエリア） -->
              <el-col :span="13"><br /></el-col>
              <!-- 検索ボタン -->
              <el-col :span="3">
                <div class="grid-content">
                  <el-button
                    class="inner-card-button"
                    type="training-sub-button"
                    @click="showPartnerList"
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
            partnerList ?
              partnerList.slice(
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

          <!-- 取引先ID -->
          <el-table-column
            width="200px"
            header-align="center"
            align="center"
            label="取引先ID"
            prop="partnerId"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.partnerId }}</span>
            </template>
          </el-table-column>

          <!-- 取引先名 -->
          <el-table-column
            width="300px"
            header-align="center"
            align="left"
            label="取引先名"
            prop="partnerName"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.partnerName }}</span>
            </template>
          </el-table-column>

          <!-- 住所 -->
          <el-table-column
            header-align="center"
            align="left"
            label="住所"
            prop="address"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.address | addressWithZip(scope.row.zip) }}</span>
            </template>
          </el-table-column>

          <!-- 参照 -->
          <el-table-column
            width="100px"
            prop="oper"
            align="center"
            label="参照"
          >
            <template slot-scope="scope">
              <el-button
                class="icon-button"
                type="primary"
                size="mini"
                icon="training-btn-line-icon el-icon-notebook-2"
                round
                @click="onInquiryPartner(scope.row)"
              />
            </template>
          </el-table-column>
          <!-- 削除 -->
          <el-table-column
            width="100px"
            prop="oper"
            align="center"
            label="削除"
          >
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.isCanDeleted == true"
                v-loading.fullscreen.lock="fullscreenLoading"
                class="icon-button"
                type="danger"
                size="mini"
                icon="training-btn-line-icon el-icon-close"
                circle
                @click="onDeletePartner(scope.row)"
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
              :total="partnerList ? partnerList.length : 0"
              @current-change="onCurrentChange"
            />
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b201GetPartnerList, b202DeletePartner } from "@/api/partner";
import { setViewStatus, getViewStatus, setParameter } from "@/utils/support";
import { messageSuccess } from "@/utils/support";

export default {
  // --- 画面名 ---
  name: "PartnerList",
  // --- 表示フィルター関数定義 ---
  filters: {
    /**
     * 郵便番号付き住所表示フィルター
     * @param {Number} address 住所
     * @param {Number} zipCode 郵便番号
     * @returns {String} - 郵便番号付き住所
     */
    addressWithZip: (address, zipCode) => {
      let res = address;
      if (zipCode) {
        res = `〒${zipCode.slice(0, 3)}-${zipCode.slice(3, zipCode.length)} ${res}`;
      }
      return res;
    },
  },
  // --- データプロパティ定義 ---
  data() {
    return {
      // リスト読み込み用ローディング画面設定フラグ
      listLoading: true,
      // ローディング画面設定（取引先削除時に設定）
      fullscreenLoading: false,
      // 入力フォーム
      form: {
        likePartnerName: null,
      },
      // 取引先一覧情報
      partnerList: [],
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
    /**
     *  初期表示
     */
    init() {
      // 画面初期情報は同期して取得する
      (async () => {
        // 検索条件パラメータを取得する。
        const conditions = await getViewStatus("PartnerListConditions");
        if (conditions) {
          // 保持していた検索条件（取引先名）を初期入力値として設定
          this.form.likePartnerName = conditions.likePartnerName;
        }
        // 取引先一覧を取得する
        await this.showPartnerList();
      })();
    },
    /**
     *  取引先一覧取得
     */
    async showPartnerList() {
      this.listLoading = true;
      const paramater = {
        // 検索条件（取引先名）をパラメータに設定
        likePartnerName: this.form.likePartnerName,
      };
      await b201GetPartnerList(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`partnerList:${JSON.stringify(response.data)}`);

        // ページネイションを行っている一覧の場合はページNoを1に戻す
        this.currentPage = 1;
        // 一覧データを画面情報に設定
        this.partnerList = response.data;
        // ローディング解除
        this.listLoading = false;
        // 検索時の条件を保持する。
        setViewStatus("PartnerListConditions", {
          // 検索条件（取引先名）を保持
          likePartnerName: this.form.likePartnerName,
        });
      }).catch(
        // エラーの場合はローディングのみ解除
        this.listLoading = false,
      );
    },
    /**
     * 取引先追加
     */
    onAddPartner() {
      // 取引先追加画面
      this.$router.push({ name: "PartnerInsert" });
      setParameter("PartnerEdit", {
        // 取引先IDを未設定で起動する
        partnerId: null,
      });
    },
    /**
     * 取引先詳細
     * @param { Object } row - 選択行情報
     */
    onInquiryPartner(row) {
      // 取引先詳細画面
      this.$router.push({ name: "PartnerInquiry" });
      setParameter("PartnerEdit", {
        // 取引先IDを設定
        partnerId: row.partnerId,
      });
    },
    /**
     * 取引先削除
     * @param { Object } row - 選択行情報
     */
    onDeletePartner(row) {
      // 確認メッセージの表示
      this.$confirm(`取引先を削除します。<br>一度削除すると戻すことはできません<br>よろしいですか。`, {
        dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
        type: "warning",
      }).then(() => {
        // 画面ロック
        this.fullscreenLoading = true;
        // B202:取引先削除
        const parameter = {
          partnerId: row.partnerId,
        };
        b202DeletePartner(parameter).then((response) => {
          // 完了メッセージを表示
          messageSuccess("取引先を削除しました。");
          // 画面ロック解除
          this.fullscreenLoading = false;
          // 一覧画面を再表示する
          this.init();
        }).catch(
          // エラーの場合は画面ロック解除のみ
          this.fullscreenLoading = false,
        );
      }).catch((err) => { if (err !== "cancel") throw err; });
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
