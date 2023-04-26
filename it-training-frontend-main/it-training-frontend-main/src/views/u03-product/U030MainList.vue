<template>
  <!--
   -- U-030 : 商品検索・一覧
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="18" class="card-title page-title">
            <span>商品検索・一覧</span>
          </el-col>
          <!-- （空きエリア） -->
          <el-col :span="3"><br /></el-col>
          <!--追加ボタン -->
          <el-col :span="3" align="center">
            <el-button
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="training-main-button"
              @click="onAddProduct"
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
              <!-- 分類 -->
              <el-col :span="2">
                <span class="grid-content-header">分類</span>
              </el-col>
              <el-col :span="5">
                <div class="grid-content">
                  <tr-select
                    v-model="form.targetClassificationCode"
                    prop-name="targetClassificationCode"
                    :select-list="classificationList"
                    key-name="genericCode"
                    label-name="genericCodeName"
                    item-label="分類"
                  />
                </div>
              </el-col>
              <!-- 商品名 -->
              <el-col :span="2">
                <span class="grid-content-header">商品名</span>
              </el-col>
              <el-col :span="5">
                <div class="grid-content">
                  <tr-input
                    v-model="form.likeItemName"
                    prop-name="likeItemName"
                    :maxlength="64"
                    item-label="商品名"
                    check-rule="default" />
                </div>
              </el-col>
              <!-- （空き領域） -->
              <el-col :span="7"><br /></el-col>
              <!-- 検索ボタン -->
              <el-col :span="3">
                <div class="grid-content">
                  <el-button
                    class="inner-card-button"
                    type="training-sub-button"
                    @click="showItemList()"
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
            itemList ?
              itemList.slice(
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

          <!-- 分類 -->
          <el-table-column
            width="120px"
            header-align="center"
            align="center"
            label="分類"
            prop="classificationCode"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.classificationCode | classificationCaption(classificationDic) }}</span>
            </template>
          </el-table-column>

          <!-- 商品コード -->
          <el-table-column
            width="120px"
            header-align="center"
            align="center"
            label="商品コード"
            prop="itemCode"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.itemCode }}</span>
            </template>
          </el-table-column>

          <!-- 商品名 -->
          <el-table-column
            header-align="center"
            align="left"
            label="商品名"
            prop="itemName"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.itemName }}</span>
            </template>
          </el-table-column>

          <!-- 仕入先 -->
          <el-table-column
            header-align="center"
            align="left"
            label="仕入先"
            prop="supplierName"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.supplierName }}</span>
            </template>
          </el-table-column>

          <!-- 仕入額（税抜） -->
          <el-table-column
            width="150px"
            header-align="center"
            align="right"
            label="仕入額（税抜）"
            prop="purchasePrice"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.purchasePrice | toThousandFilter }}</span>
            </template>
          </el-table-column>

          <!-- 販売額（税抜） -->
          <el-table-column
            width="150px"
            header-align="center"
            align="right"
            label="販売額（税抜）"
            prop="sellingPrice"
            sortable
          >
            <template slot-scope="scope">
              <span>{{ scope.row.sellingPrice | toThousandFilter }}</span>
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
                @click="onInquiryProduct(scope.row)"
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
                @click="onDeleteItem(scope.row)"
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
              :total="itemList ? itemList.length : 0"
              @current-change="onCurrentChange"
            />
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b301GetProductItemList, b302DeleteProductItem } from "@/api/product";
import { b001GetGenericCodeList } from "@/api/genericCode";
import { setViewStatus, getViewStatus, setParameter } from "@/utils/support";
import { messageSuccess } from "@/utils/support";
import Constants from "@/definition/constant";

export default {
  // --- 画面名 ---
  name: "ProductList",
  // --- 表示フィルター関数定義 ---
  filters: {
    /**
     * 分類コード表示フィルター
     * @param {String} code コード値
     * @param {Object} dic 分類コード辞書
     * @returns {String} - コードに対応した分類コードの値（名称）
     */
    classificationCaption: (code, dic) => {
      return dic ? (dic[code] ? dic[code] : "") : "unkown";
    },
  },
  // --- データプロパティ定義 ---
  data() {
    return {
      // リスト読み込み用ローディング画面設定フラグ
      listLoading: true,
      // ローディング画面設定（商品削除時に設定）
      fullscreenLoading: false,
      // 分類コードコンボボックス表示用リスト
      classificationList: [],
      // 分類コード辞書データ
      classificationDic: {},
      // 入力フォーム
      form: {
        targetClassificationCode: null,
        likeItemName: null,
      },
      // 商品一覧情報
      itemList: [],
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
        // 画面初期化
        await this.initClassificationList();

        // 検索条件パラメータを取得する。
        const conditions = await getViewStatus("itemListConditions");
        if (conditions) {
          // 保持していた検索条件（分類、商品名）を初期入力値として設定
          this.form.targetClassificationCode = conditions.targetClassificationCode;
          this.form.likeItemName = conditions.likeItemName;
        }
        // 商品一覧を取得する
        await this.showItemList();
      })();
    },
    /**
     *  分類一覧取得
     */
    async initClassificationList() {
      const paramater = {
        genericCodeTypeId: Constants.GenericCodeType.classification,
      };
      await b001GetGenericCodeList(paramater).then((response) => {
        // console.log(`list:${JSON.stringify(response.data)}`)
        this.classificationList = response.data;
        // 分類の辞書も同時に作成
        for (const row of response.data) {
          this.classificationDic[row.genericCode] = row.genericCodeName;
        }
      });
    },
    /**
     *  受注一覧取得
     */
    async showItemList() {
      this.listLoading = true;
      // B301:商品一覧取得
      const paramater = {
        targetClassificationCode: this.form.targetClassificationCode,
        likeItemName: this.form.likeItemName,
      };
      await b301GetProductItemList(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`itemList:${JSON.stringify(response.data)}`);

        // ページネイションを行っている一覧の場合はページNoを1に戻す
        this.currentPage = 1;
        // 一覧データを画面情報に設定
        this.itemList = response.data;
        // ローディング解除
        this.listLoading = false;
        // 検索時の条件を保持する。
        setViewStatus("itemListConditions", {
          // 検索条件（分類、商品名）を保持
          targetClassificationCode: this.form.targetClassificationCode,
          likeItemName: this.form.likeItemName,
        });
      }).catch(
        // エラーの場合はローディングのみ解除
        this.listLoading = false,
      );
    },
    /**
     * 商品追加
     */
    onAddProduct() {
      // 商品追加画面
      this.$router.push({ name: "ProductInsert" });
      setParameter("ProductEdit", {
        // 品目コードを未設定で起動する
        itemCode: null,
      });
    },
    /**
     * 商品詳細
     * @param { Object } row - 選択行情報
     */
    onInquiryProduct(row) {
      // 商品詳細画面
      this.$router.push({ name: "ProductInquiry" });
      setParameter("ProductEdit", {
        // 品目コードを設定
        itemCode: row.itemCode,
      });
    },
    /**
     * 商品削除
     * @param { Object } row - 選択行情報
     */
    onDeleteItem(row) {
      // 確認メッセージの表示
      this.$confirm(`商品を削除します。<br>一度削除すると戻すことはできません<br>よろしいですか。`, {
        dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
        type: "warning",
      }).then(() => {
        // 画面ロック
        this.fullscreenLoading = true;
        // B302:商品（SKU）削除
        const parameter = {
          itemCode: row.itemCode,
        };
        b302DeleteProductItem(parameter).then((response) => {
          // 完了メッセージを表示
          messageSuccess("商品を削除しました。");
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
