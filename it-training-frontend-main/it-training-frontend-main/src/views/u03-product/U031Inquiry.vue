<template>
  <!--
   -- U-031 : 商品詳細
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="18" class="card-title page-title">
            <span>商品詳細</span>
          </el-col>
          <!--編集ボタン -->
          <el-col :span="3" align="right">
            <el-button
              class="card-button"
              type="training-main-button"
              @click="onEdit"
            >編集</el-button>
          </el-col>
          <!--戻るボタン -->
          <el-col :span="3" align="right">
            <el-button
              class="card-button"
              type="training-main-button"
              @click="onBackList"
            >戻る</el-button>
          </el-col>
        </el-row>
      </div>
      <!--画面コンテンツエリア -->
      <div class="card-content" style="min-height: calc(100vh - 210px)">
        <!--品目情報部 -->
        <!-- 商品コード -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">商品コード</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ productDetail.itemCode }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- カテゴリ -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">カテゴリ</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ productDetail.categoryCode | dictionaryCaption(categoryDic) }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 分類 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">分類</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ productDetail.classificationCode | dictionaryCaption(classificationDic) }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- シーズン -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">シーズン</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ productDetail.seasonCode | dictionaryCaption(seasonDic) }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 商品名 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">商品名</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ productDetail.itemName }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 仕入先 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">仕入先</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ productDetail.supplierName }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 仕入価格（税抜・税込） -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">仕入価格</span>
          </el-col>
          <el-col :span="3" class="col-item-number">
            <div class="grid-content">
              <span>{{ productDetail.purchasePrice | toThousandFilter }}</span>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="grid-content item-header">
              <span>（税抜）</span>
            </div>
          </el-col>
          <el-col :span="3" class="col-item-number">
            <div class="grid-content">
              <span>{{ productDetail.purchasePriceIntax | toThousandFilter }}</span>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="grid-content item-header">
              <span>（税込）</span>
            </div>
          </el-col>
        </el-row>
        <!-- 販売価格（税抜・税込） -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">販売価格</span>
          </el-col>
          <el-col :span="3" class="col-item-number">
            <div class="grid-content">
              <span>{{ productDetail.sellingPrice | toThousandFilter }}</span>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="grid-content item-header">
              <span>（税抜）</span>
            </div>
          </el-col>
          <el-col :span="3" class="col-item-number">
            <div class="grid-content">
              <span>{{ productDetail.sellingPriceIntax | toThousandFilter }}</span>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="grid-content item-header">
              <span>（税込）</span>
            </div>
          </el-col>
        </el-row>
        <!--商品パターン一覧部 -->
        <el-row style="padding-top: 20px">
          <el-col :span="12" style="text-align: right">
            <el-table
              v-loading="listLoading"
              :data="
                productDetail.productDetailList ?
                  productDetail.productDetailList.slice(
                    (currentPage - 1) * pagesize,
                    currentPage * pagesize
                  )
                  : null
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

              <!-- 商品コード（SKU） -->
              <el-table-column
                header-align="center"
                align="left"
                label="SKU"
                prop="productCode"
                sortable
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.productCode }}</span>
                </template>
              </el-table-column>

              <!-- カラー -->
              <el-table-column
                width="200px"
                header-align="center"
                align="left"
                label="カラー"
                prop="colorCode"
                sortable
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.colorCode | codeNameCaption(scope.row.colorName) }}</span>
                </template>
              </el-table-column>

              <!-- サイズ -->
              <el-table-column
                width="200px"
                header-align="center"
                align="left"
                label="サイズ"
                prop="sizeCode"
                sortable
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.sizeCode | codeNameCaption(scope.row.sizeName) }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <!-- ページネーション部 -->
        <el-row style="padding-top: 20px">
          <el-col :span="12" style="text-align: right">
            <el-pagination
              background
              layout="total, prev, pager, next"
              :page-size="pagesize"
              :total="productDetail.productDetailList ? productDetail.productDetailList.length : 0"
              @current-change="onCurrentChange"
            />
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b311GetProductDetail } from "@/api/product";
import { b001GetGenericCodeList } from "@/api/genericCode";
import { getParameter, setParameter } from "@/utils/support";
import Constants from "@/definition/constant";

export default {
  // --- 画面名 ---
  name: "ProductInquiry",
  // --- 表示フィルター関数定義 ---
  filters: {
    /**
     * 汎用コード表示フィルター
     * @param {String} code 汎用コード値
     * @param {Object} dic 汎用コード辞書
     * @returns {String} - コードに対応した汎用コードの値（名称）
     */
    dictionaryCaption: (code, dic) => {
      return dic ? (dic[code + ""] ? `${dic[code + ""]}（${code}）` : code) : "unkown";
    },
    /**
     * カラー/サイズコード表示フィルター
     * @param {String} code カラー/サイズコード値
     * @param {Object} name カラー/サイズ名
     * @returns {String} - コードを付与したカラー/サイズ名キャプション
     */
    codeNameCaption: (code, name) => {
      return `${name}（${code + ""}）`;
    },
  },
  // --- データプロパティ定義 ---
  data() {
    return {
      // リスト読み込み用ローディング画面設定フラグ
      listLoading: true,
      // 汎用コード辞書
      categoryDic: {},
      classificationDic: {},
      seasonDic: {},
      // 商品（SKU）詳細情報
      productDetail: {},
      // ページネーション情報
      currentPage: 1,
      pagesize: 5,
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
      // 画面間パラメータの取得
      const params = getParameter("ProductEdit");
      // 画面間パラメータチェック
      if ((!params.itemCode) || (params.itemCode.length <= 0)) {
        // 品目コードが設定していない場合はエラーページへ遷移する。
        location.replace("/400");
        return;
      }
      // 画面初期情報は同期して取得する
      (async () => {
        // 画面初期化（汎用コード情報取得）
        await this.initDictionaryList();
        // 商品詳細情報を取得する
        await this.showProductDetail(params.itemCode);
      })();
    },
    /**
     *  汎用コード情報取得
     */
    async initDictionaryList() {
      // カテゴリの情報を取得（B001:汎用コード一覧取得）
      b001GetGenericCodeList({ genericCodeTypeId: Constants.GenericCodeType.category }).then((response) => {
        // カテゴリの辞書を作成
        for (const row of response.data) {
          this.categoryDic[row.genericCode] = row.genericCodeName;
        }
        // TODO: 取得データの確認用
        // console.log(`categoryDic：${JSON.stringify(this.categoryDic)}`);
      });
      // 分類の情報を取得（B001:汎用コード一覧取得）
      b001GetGenericCodeList({ genericCodeTypeId: Constants.GenericCodeType.classification }).then((response) => {
        // 分類の辞書を作成
        for (const row of response.data) {
          this.classificationDic[row.genericCode] = row.genericCodeName;
        }
        // TODO: 取得データの確認用
        // console.log(`classificationDic${JSON.stringify(this.classificationDic)}`);
      });
      // シーズンの情報を取得（B001:汎用コード一覧取得）
      b001GetGenericCodeList({ genericCodeTypeId: Constants.GenericCodeType.season }).then((response) => {
        // シーズンの辞書を作成
        for (const row of response.data) {
          this.seasonDic[row.genericCode] = row.genericCodeName;
        }
        // TODO: 取得データの確認用
        // console.log(`seasonDic${JSON.stringify(this.seasonDic)}`);
      });
    },
    /**
     *  商品詳細情報取得
     *  @param {String} itemCode 品目コード
     */
    async showProductDetail(itemCode) {
      this.listLoading = true;
      // B311 商品（SKU）取得
      const paramater = {
        itemCode: itemCode,
      };
      await b311GetProductDetail(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`productDetail：${JSON.stringify(response.data)}`);

        // 取得した商品情報をそのままDOMに設定
        this.productDetail = response.data;
        // ローディングを解除
        this.listLoading = false;
      }).catch(
        // エラーの場合はローディングのみ解除
        this.listLoading = false,
      );
    },
    /**
     * 商品編集画面へ遷移
     */
    onEdit() {
      // 商品編集画面
      this.$router.push({ name: "ProductEdit" });
      setParameter("ProductEdit", {
        // 品目コードを設定する。
        itemCode: this.productDetail.itemCode,
      });
    },
    /**
     * 一覧へ戻る
     */
    onBackList() {
      this.$router.push({ name: "ProductList" });
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
