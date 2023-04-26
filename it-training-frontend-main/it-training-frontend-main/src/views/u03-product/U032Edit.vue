<template>
  <!--
   -- U-032 : 商品登録/更新
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="18" class="card-title page-title">
            <span>{{ title }}</span>
          </el-col>
          <!--保存ボタン -->
          <el-col :span="3" align="right">
            <el-button
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="training-main-button"
              @click="onSave"
            >保存</el-button>
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
      <div class="card-content">
        <el-form ref="form" label-position="left" :model="form" status-icon>
          <!--品目情報部 -->
          <!-- 商品コード -->
          <el-row :gutter="10" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">商品コード</span>
            </el-col>
            <el-col :span="5">
              <div v-if="mode=='ADD'" class="grid-content">
                <tr-input
                  v-model="form.itemCode"
                  prop-name="itemCode"
                  :minlength="8"
                  :maxlength="8"
                  :required="true"
                  item-label="商品コード"
                  check-rule="half-width-alphabet-number"
                />
              </div>
              <div v-else class="grid-content-no-input">
                <span>{{ form.itemCode }}</span>
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
                <tr-select
                  v-model="form.categoryCode"
                  prop-name="categoryCode"
                  :select-list="categoryList"
                  key-name="key"
                  label-name="label"
                  :required="true"
                  item-label="カテゴリ"
                />
              </div>
            </el-col>
          </el-row>
          <!-- 分類 -->
          <el-row :gutter="10" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">分類</span>
            </el-col>
            <el-col :span="5">
              <div v-if="mode=='ADD'" class="grid-content">
                <tr-select
                  v-model="form.classificationCode"
                  prop-name="classificationCode"
                  :select-list="classificationList"
                  key-name="key"
                  label-name="label"
                  :required="true"
                  item-label="分類"
                />
              </div>
              <div v-else class="grid-content-no-input">
                <span>{{ form.classificationCode | dictionaryCaption(classificationDic) }}</span>
              </div>
            </el-col>
          </el-row>
          <!-- シーズン -->
          <el-row :gutter="10" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">シーズン</span>
            </el-col>
            <el-col :span="5">
              <div v-if="mode=='ADD'" class="grid-content">
                <tr-select
                  v-model="form.seasonCode"
                  prop-name="seasonCode"
                  :select-list="seasonList"
                  key-name="key"
                  label-name="label"
                  :required="true"
                  item-label="シーズン"
                />
              </div>
              <div v-else class="grid-content-no-input">
                <span>{{ form.seasonCode | dictionaryCaption(seasonDic) }}</span>
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
                <tr-input
                  v-model="form.itemName"
                  prop-name="itemName"
                  :maxlength="64"
                  :required="true"
                  item-label="商品名"
                  check-rule="default"
                />
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
                <tr-select
                  v-model="form.supplierId"
                  prop-name="supplierId"
                  :select-list="supplierList"
                  key-name="key"
                  label-name="label"
                  width-size="large"
                  :required="true"
                  item-label="仕入先"
                />
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
                <tr-input-number
                  v-model="form.purchasePrice"
                  prop-name="purchasePrice"
                  :max="99999"
                  :min="0"
                  :required="true"
                  item-label="仕入価格"
                  @blur="handleCalcTaxByPurchasePrice(form)"
                />
              </div>
            </el-col>
            <el-col :span="2">
              <div class="grid-content item-header">
                <span>（税抜）</span>
              </div>
            </el-col>
            <el-col :span="3" class="col-item-number">
              <div class="grid-content">
                <span>{{ form.purchasePriceIntax | toThousandFilter }}</span>
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
                <tr-input-number
                  v-model="form.sellingPrice"
                  prop-name="sellingPrice"
                  :max="99999"
                  :min="0"
                  :required="true"
                  item-label="販売価格"
                  @blur="handleCalcTaxBySellingPrice(form)"
                />
              </div>
            </el-col>
            <el-col :span="2">
              <div class="grid-content item-header">
                <span>（税抜）</span>
              </div>
            </el-col>
            <el-col :span="3" class="col-item-number">
              <div class="grid-content">
                <span>{{ form.sellingPriceIntax | toThousandFilter }}</span>
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
                :data="form.productDetailList"
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
                    <span class="grid-content-line-label">{{ scope.$index + 1 }}</span>
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
                    <span v-if="form.itemCode && form.itemCode.length == 8">{{ form.itemCode }}</span>
                    <span v-else style="color: red">XXXXXXXX</span>
                    <span>{{ scope.row.colorCode }}{{ scope.row.sizeCode }}</span>
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
                      class="icon-button"
                      type="danger"
                      icon="training-btn-line-icon el-icon-close"
                      circle
                      size="mini"
                      @click="onDeleteProductPattern(scope.row)"
                    />
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
          <!-- パターン追加 -->
          <el-row style="padding-top: 20px">
            <!--パターン追加ボタン -->
            <el-col :span="10" align="center">
              <el-button
                v-loading.fullscreen.lock="fullscreenLoading"
                class="list-line-card-button"
                type="training-sub-button"
                round
                @click="onOpenAddPattern"
              >パターン追加</el-button>
            </el-col>
          </el-row>
        </el-form>
        <!-- パターン追加ダイアログ -->
        <el-dialog title="パターン追加" :visible.sync="dialogFormVisible" width="30%" center>
          <el-form ref="patternForm" :model="patternForm">
            <!-- カラー選択 -->
            <el-row :gutter="20" style="padding-left:10px">
              <el-col :span="10" align="right">
                <span class="grid-content-header">カラー</span>
              </el-col>
              <el-col :span="10">
                <div class="grid-content">
                  <tr-select
                    v-model="patternForm.colorCode"
                    prop-name="colorCode"
                    :select-list="colorList"
                    key-name="code"
                    label-name="caption"
                    :required="true"
                    item-label="カラー"
                  />
                </div>
              </el-col>
            </el-row>
            <!-- サイズ選択 -->
            <el-row :gutter="20" style="padding-left:10px">
              <el-col :span="10" align="right">
                <span class="grid-content-header">サイズ</span>
              </el-col>
              <el-col :span="10">
                <div class="grid-content">
                  <tr-select
                    v-model="patternForm.sizeCode"
                    prop-name="sizeCode"
                    :select-list="sizeList"
                    key-name="code"
                    label-name="caption"
                    :required="true"
                    item-label="サイズ"
                  />
                </div>
              </el-col>
            </el-row>
          </el-form>
          <!-- フッター設定 -->
          <span slot="footer">
            <!-- キャンセルボタン -->
            <el-button
              @click="dialogFormVisible = false"
            >キャンセル</el-button>
            <!-- 追加ボタン -->
            <el-button
              type="primary"
              @click="onAddPattern"
            >追加</el-button>
          </span>
        </el-dialog>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b311GetProductDetail, b32xRegistProductDetail } from "@/api/product";
import { b001GetGenericCodeList } from "@/api/genericCode";
import { b291GetSupplierList } from "@/api/partner";
import { b391GetColorList, b392GetSizeList } from "@/api/itemPattern";
import { getParameter } from "@/utils/support";
import { messageSuccess, messageError } from "@/utils/support";
import Constants from "@/definition/constant";

export default {
  // --- 画面名 ---
  name: "ProductEdit",
  // --- 表示フィルター関数定義 ---
  filters: {
    /**
     * 汎用コード表示フィルター
     * @param {String} code 汎用コード値
     * @param {Object} dic 汎用コード辞書
     * @returns {String} - コードに対応した汎用コードの値（名称）
     */
    dictionaryCaption: (code, dic) => {
      console.log("check");
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
      // 画面モード情報
      title: "未設定",
      mode: "",
      // リスト読み込み用ローディング画面設定フラグ
      listLoading: true,
      // ローディング画面設定（データ登録/更新系には設定）
      fullscreenLoading: false,
      // 汎用コード辞書
      categoryDic: {},
      classificationDic: {},
      seasonDic: {},
      // 汎用コードコンボボックス用リスト
      categoryList: [],
      classificationList: [],
      seasonList: [],
      // 仕入先コンボボックス用リスト
      supplierList: [],
      // カラー/サイズコード辞書
      colorDic: {},
      sizeDic: {},
      // カラー/サイズコンボボックス用リスト
      colorList: [],
      sizeList: [],
      // 入力フォーム（商品）
      form: {},
      // パターン追加ダイアログ表示制御フラグ
      dialogFormVisible: false,
      // 入力フォーム（パターン追加）
      patternForm: {},
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
      // ルーターから基本情報を取得する。
      this.title = this.$route.meta.title; // タイトル
      this.mode = this.$route.meta.mode; // モード

      // 画面初期情報は同期して取得する
      (async () => {
        // 画面初期化（汎用コード情報取得）
        await this.initDictionaryList();
        // 画面初期化（仕入先情報取得）
        await this.initSupplierList();
        // 画面初期化（カラー・サイズ情報取得）
        await this.initColorSizeList();
        // モードによって取得方法を変更
        if (this.mode === "ADD") {
          // 新規の場合は入力項目の初期化を行う
          this.form = {
            itemCode: "",
            categoryCode: "",
            classificationCode: "",
            seasonCode: "",
            itemName: "",
            supplierId: "",
            purchasePrice: "",
            purchasePriceIntax: "",
            sellingPrice: "",
            sellingPriceIntax: "",
            productDetailList: [],
          };
          // 新規の場合はローディングを停止する。
          this.listLoading = false;
        } else {
          // 画面間パラメータの取得
          const params = getParameter("ProductEdit");
          // 画面間パラメータチェック
          if ((!params.itemCode) || (params.itemCode.length <= 0)) {
            // エラーページへ遷移する。
            location.replace("/400");
            return;
          }
          // 商品詳細情報を取得する
          await this.showProductDetail(params.itemCode);
        }
      })();
    },
    /**
     *  汎用コード情報取得
     */
    async initDictionaryList() {
      // カテゴリの情報を取得（B001:汎用コード一覧取得）
      b001GetGenericCodeList({ genericCodeTypeId: Constants.GenericCodeType.category }).then((response) => {
        // カテゴリの辞書を作成
        this.categoryList = [];
        for (const row of response.data) {
          this.categoryDic[row.genericCode] = row.genericCodeName;
          // プルダウンリスト情報作成
          this.categoryList.push({
            key: row.genericCode,
            label: `${row.genericCodeName}（${row.genericCode + ""}）`,
          });
        }
        // TODO: 取得データの確認用
        // console.log("category：", JSON.stringify(this.categoryDic), JSON.stringify(this.categoryList));
      });
      // 分類の情報を取得（B001:汎用コード一覧取得）
      b001GetGenericCodeList({ genericCodeTypeId: Constants.GenericCodeType.classification }).then((response) => {
        // 分類の辞書を作成
        this.classificationList = [];
        for (const row of response.data) {
          this.classificationDic[row.genericCode] = row.genericCodeName;
          // プルダウンリスト情報作成
          this.classificationList.push({
            key: row.genericCode,
            label: `${row.genericCodeName}（${row.genericCode + ""}）`,
          });
        }
        // TODO: 取得データの確認用
        // console.log("classification", JSON.stringify(this.classificationDic), JSON.stringify(this.classificationList));
      });
      // シーズンの情報を取得
      b001GetGenericCodeList({ genericCodeTypeId: Constants.GenericCodeType.season }).then((response) => {
        // シーズンの辞書を作成
        this.seasonList = [];
        for (const row of response.data) {
          this.seasonDic[row.genericCode] = row.genericCodeName;
          // プルダウンリスト情報作成
          this.seasonList.push({
            key: row.genericCode,
            label: `${row.genericCodeName}（${row.genericCode + ""}）`,
          });
        }
        // TODO: 取得データの確認用
        // console.log("season", JSON.stringify(this.seasonDic), JSON.stringify(this.seasonList));
      });
    },
    /**
     *  仕入先リスト情報取得
     */
    async initSupplierList() {
      // B291:仕入先一覧取得
      b291GetSupplierList().then((response) => {
        // TODO: 取得データの確認用
        // console.log(`supplierList:${JSON.stringify(response.data)}`);
        // 仕入先のプルダウンリストを作成
        this.supplierList = [];
        for (const row of response.data) {
          this.supplierList.push({
            key: row.supplierId,
            label: `${row.supplierName}`,
          });
        }
      });
    },
    /**
     *  カラー・サイズ情報取得
     */
    async initColorSizeList() {
      // B391:カラーコード一覧取得
      b391GetColorList({ genericCodeTypeId: Constants.GenericCodeType.category }).then((response) => {
        // TODO: 取得データの確認用
        // console.log(`colorList:${JSON.stringify(response.data)}`);
        // カラーの辞書およびプルダウンリスト作成
        this.colorList = [];
        for (const row of response.data) {
          // 辞書作成
          this.colorDic[row.code] = row.name;
          // プルダウンリスト作成
          this.colorList.push({
            code: row.code,
            name: row.name,
            caption: `${row.name}（${row.code + ""}）`,
          });
        }
      });
      // B392:サイズコード一覧取得
      b392GetSizeList().then((response) => {
        // TODO: 取得データの確認用
        // console.log(`sizeList:${JSON.stringify(response.data)}`);
        // サイズのプルダウンリスト情報作成
        this.sizeList = [];
        for (const row of response.data) {
          // 辞書作成
          this.sizeDic[row.code] = row.name;
          // プルダウンリスト作成
          this.sizeList.push({
            code: row.code,
            name: row.name,
            caption: `${row.name}（${row.code + ""}）`,
          });
        }
      });
    },
    /**
     *  商品詳細情報取得
     *  @param {String} itemCode 品目コード
     */
    async showProductDetail(itemCode) {
      this.listLoading = true;
      // B311:商品（SKU）取得
      const paramater = {
        itemCode: itemCode,
      };
      await b311GetProductDetail(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`productDetail：${JSON.stringify(response.data)}`);

        // 取得した商品情報をそのままDOMに設定
        this.form = response.data;
        // ローディングを解除
        this.listLoading = false;
      }).catch(
        // エラーの場合はローディングのみ解除
        this.listLoading = false,
      );
    },
    /**
     * 消費税計算
     * @param {Number} price 価格（税抜き）
     * @return {String} - 価格（税込み）※ 浮動小数とならないように文字型として扱う
     */
    calcTax(price) {
      let priceInTax = price ? (price * 110) + "" : "";
      // 下2桁目に小数点を入れる（0以外は3桁以上の数値）
      if (priceInTax.length > 2) {
        // 小数部を取得する
        const decimal = priceInTax.slice(-2);
        // 小数部を切った値を取得
        priceInTax = priceInTax.slice(0, priceInTax.length - 2);
        // 小数部が0でなければ繰り上げ
        if (decimal !== 0) {
          priceInTax = ((priceInTax * 1) + 1) + "";
        }
      }
      return priceInTax;
    },
    /**
     * 仕入価格の消費税計算
     * @param {Object} form 入力フォーム
     */
    handleCalcTaxByPurchasePrice(form) {
      form.purchasePriceIntax = this.calcTax(form.purchasePrice);
    },
    /**
     * 販売価格の消費税計算
     * @param {Object} form 入力フォーム
     */
    handleCalcTaxBySellingPrice(form) {
      form.sellingPriceIntax = this.calcTax(form.sellingPrice);
    },
    /**
     * 保存処理
     */
    onSave() {
      // 入力バリデーションチェックを行う。
      this.$refs["form"].validate((valid) => {
        if (valid) {
          // 固有のバリデーションチェックを行う。
          // 分類コードが異なる場合
          if (this.form.itemCode.slice(0, 2) !== this.form.classificationCode) {
            // 入力不備エラーメッセージを表示
            messageError("商品コードの頭2桁は分類コードと一致している必要があります。");
            return false;
          }
          // シーズンコードが異なる場合
          if (this.form.itemCode.slice(-3) !== this.form.seasonCode) {
            // 入力不備エラーメッセージを表示
            messageError("商品コードの下3桁はシーズンコードと一致している必要があります。");
            return false;
          }
          // 商品（SKU）が設定していない場合
          if (!this.form.productDetailList || this.form.productDetailList.length === 0) {
            // 入力不備エラーメッセージを表示
            messageError("商品（SKU）が設定されていません。");
            return false;
          }

          // 確認画面の表示
          this.$confirm("商品情報を保存します。\nよろしいですか。", {
            dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
            type: "warning",
          })
            .then(() => {
              // 画面ロック
              this.fullscreenLoading = true;
              // B321/B322 商品（SKU）登録/更新（モードにより切り分け）
              const detailList = [];
              for (const product of this.form.productDetailList) {
                detailList.push({
                  colorCode: product.colorCode,
                  sizeCode: product.sizeCode,
                });
              }
              const parameter = {
                itemCode: this.form.itemCode,
                itemName: this.form.itemName,
                categoryCode: this.form.categoryCode,
                classificationCode: this.form.classificationCode,
                seasonCode: this.form.seasonCode,
                supplierId: this.form.supplierId,
                purchasePrice: 0 + this.form.purchasePrice,
                sellingPrice: 0 + this.form.sellingPrice,
                productDetailList: detailList,
              };
              b32xRegistProductDetail(this.mode, parameter).then((response) => {
                // 完了メッセージを表示
                messageSuccess("商品情報を保存しました。");

                // 画面ロック解除
                this.fullscreenLoading = false;

                // 処理モードにより遷移先を変更する。
                if (this.mode === "ADD") {
                  // 登録時は一覧画面に戻る
                  this.$router.push({ name: "ProductList" });
                } else {
                  // 更新時は照会画面に戻る
                  this.$router.push({ name: "ProductInquiry" });
                }
              }).catch(
                // エラーの場合は画面ロック解除のみ
                this.fullscreenLoading = false,
              );
            })
            // キャンセルボタン押下時のハンドリング追加
            .catch((err) => { if (err !== "cancel") throw err; });
        } else {
          // 入力不備エラーメッセージを表示
          messageError("入力フォームに誤りがあります。");
          return false;
        }
      });
    },
    /**
     * 一覧へ戻る
     */
    onBackList() {
      this.$router.push({ name: "ProductList" });
    },
    /**
     * パターン追加ダイアログ表示
     */
    onOpenAddPattern() {
      // 入力値初期化
      this.patternForm = {
        colorCode: "",
        sizeCode: "",
      };
      // パターン追加ダイアログを表示する
      this.dialogFormVisible = true;
    },
    /**
     * パターン追加処理
     */
    onAddPattern() {
      // パターンのバリデーションチェック
      this.$refs["patternForm"].validate((valid) => {
        if (valid) {
          // パターンの重複チェックを行う。
          for (const row of this.form.productDetailList) {
            // 同じカラーコードおよびサイズコードが登録されている場合はエラーとする。
            if (row.colorCode === this.patternForm.colorCode &&
                row.sizeCode === this.patternForm.sizeCode) {
              // 入力不備エラーメッセージを表示
              messageError("既に存在する商品パターンです。");
              return false;
            }
          }

          // チェックOKの場合、追加したパターンを一覧に追加する。
          this.form.productDetailList.push({
            // productCode: "",
            // itemCode: "",
            colorCode: this.patternForm.colorCode,
            colorName: this.colorDic[this.patternForm.colorCode],
            sizeCode: this.patternForm.sizeCode,
            sizeName: this.sizeDic[this.patternForm.sizeCode],
            isCanDeleted: true,
          });

          // パターン追加ダイアログを非表示にする
          this.dialogFormVisible = false;
        } else {
          // 入力不備エラーメッセージを表示
          messageError("入力フォームに誤りがあります。");
          return false;
        }
      });
    },
    /**
     *  商品パターン削除
     *  @param { Object } row - 選択行情報
     */
    onDeleteProductPattern(row) {
      // 商品パターンを削除する。
      // 確認画面の表示
      this.$confirm("商品パターンを削除します。\nよろしいですか。", {
        dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
        type: "warning",
      }).then(() => {
        // 行を削除する。
        this.form.productDetailList = this.form.productDetailList.filter(function(rowItem) {
          return rowItem !== row;
        });
      })
      // キャンセルボタン押下時のハンドリング追加
        .catch((err) => { if (err !== "cancel") throw err; });
    },
  },
};
</script>

<style scoped>
</style>
