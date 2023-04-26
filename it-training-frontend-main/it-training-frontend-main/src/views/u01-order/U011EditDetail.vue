<template>
  <!--
   -- U-011 : 受注詳細
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <el-row type="flex" justify="space-between">
          <!--画面タイトル -->
          <el-col :span="9" class="card-title page-title">
            <span>受注詳細</span>
          </el-col>
          <!--確認済ボタン -->
          <el-col :span="3" align="right">
            <el-button
              v-if="orderDetail.orderStatus === orderStatus.unconfirmed"
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="info"
              @click="onConfirmedOrder"
            >確認済</el-button>
          </el-col>
          <!--受注取消ボタン -->
          <el-col :span="3" align="right">
            <el-button
              v-if="isReserved !== true"
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="info"
              @click="onCancelOrder"
            >受注取消</el-button>
          </el-col>
          <!--データ削除ボタン -->
          <el-col :span="3" align="right">
            <el-button
              v-if="orderDetail.orderStatus === orderStatus.unconfirmed"
              v-loading.fullscreen.lock="fullscreenLoading"
              class="card-button"
              type="info"
              @click="onDeleteOrder"
            >データ削除</el-button>
          </el-col>
          <!--戻るボタン -->
          <el-col :span="6" align="right">
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
        <!--受注情報部 -->
        <!-- 受注番号 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">受注番号</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ orderDetail.orderNo | fillNo(10) }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 受注ステータス -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">受注ステータス</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ orderDetail.orderStatus | constantsDicCaption(orderStatusDic) }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- クライアント名 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">クライアント名</span>
          </el-col>
          <el-col :span="8">
            <div class="grid-content">
              <span>{{ orderDetail.clientName }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 受注日 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">受注日</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ orderDetail.orderDate | dateFormatSlash }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 納期 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">納期</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ orderDetail.deliveryDate | dateFormatSlash }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 納品先 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">納品先</span>
          </el-col>
          <el-col :span="10">
            <div class="grid-content">
              <span>{{ orderDetail.deliveryAddress | addressWithZip(orderDetail.deliveryZip) }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 受注件名 -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">受注件名</span>
          </el-col>
          <el-col :span="5">
            <div class="grid-content">
              <span>{{ orderDetail.orderSubject }}</span>
            </div>
          </el-col>
        </el-row>
        <!-- 受注額（税抜・税込） -->
        <el-row :gutter="10" style="padding-left:10px">
          <el-col :span="3">
            <span class="grid-content-header">受注額</span>
          </el-col>
          <el-col :span="3" class="col-item-number">
            <div class="grid-content">
              <span>{{ orderDetail.totalOrderAmount | toThousandFilter }}</span>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="grid-content item-header">
              <span>（税抜）</span>
            </div>
          </el-col>
          <el-col :span="3" class="col-item-number">
            <div class="grid-content">
              <span>{{ orderDetail.totalOrderAmountIntax | toThousandFilter }}</span>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="grid-content item-header">
              <span>（税込）</span>
            </div>
          </el-col>
        </el-row>
        <!--受注明細一覧部 -->
        <el-row style="padding-top: 20px">
          <el-col :span="24" style="text-align: right">
            <el-table
              v-loading="listLoading"
              :data="
                orderDetail.orderDetailList ?
                  orderDetail.orderDetailList
                  : null
              "
              border
              fit
              highlight-current-row
              style="width: 100%; padding: 5px;"
            >
              <!-- 明細番号 -->
              <el-table-column
                width="50px"
                header-align="center"
                label="#"
                align="center"
              >
                <template slot-scope="scope">
                  <span class="grid-content-line-label">{{ scope.row.lineNo }}</span>
                </template>
              </el-table-column>

              <!-- 商品コード -->
              <el-table-column
                width="100px"
                header-align="center"
                align="left"
                label="商品コード"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.itemCode }}</span>
                </template>
              </el-table-column>

              <!-- 商品コード（SKU） -->
              <el-table-column
                width="150px"
                header-align="center"
                align="left"
                label="SKU"
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
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.sizeCode | codeNameCaption(scope.row.sizeName) }}</span>
                </template>
              </el-table-column>

              <!-- 商品名 -->
              <el-table-column
                header-align="center"
                align="left"
                label="商品名"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.itemName }}</span>
                </template>
              </el-table-column>

              <!-- 数量 -->
              <el-table-column
                width="100px"
                header-align="center"
                align="right"
                label="数量"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.quantity | toThousandFilter }}</span>
                </template>
              </el-table-column>

              <!-- 受注明細ステータス -->
              <el-table-column
                width="120px"
                header-align="center"
                align="left"
                label="明細ステータス"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.orderLineStatus | constantsDicCaption(orderDetailStatusDic) }}</span>
                </template>
              </el-table-column>

              <!-- 販売単価（税抜） -->
              <el-table-column
                width="100px"
                header-align="center"
                align="right"
                label="販売単価（税抜）"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.sellingPrice | toThousandFilter }}</span>
                </template>
              </el-table-column>

              <!-- 小計 -->
              <el-table-column
                width="100px"
                header-align="center"
                align="right"
                label="小計"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.subtotalSellingPrice | toThousandFilter }}</span>
                </template>
              </el-table-column>

              <!-- 編集（取消） -->
              <el-table-column
                prop="oper"
                align="center"
                label="編集"
                width="150px"
              >
                <template slot-scope="scope">
                  <el-button
                    v-if="!(scope.row.orderLineStatus === orderDetailStatus.reserved ||
                      scope.row.orderLineStatus === orderDetailStatus.cancel) "
                    v-loading.fullscreen.lock="fullscreenLoading"
                    type="danger"
                    size="mini"
                    icon="training-btn-line-icon el-icon-close"
                    round
                    @click="onCancelOrderLine(scope.row)"
                  ><span class="buttom-inner-text">明細取消</span></el-button>
                  <!-- @training 画面調整用ダミーを配置 ボタンが非表示になると行が詰められるため-->
                  <div v-else></div>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b111GetOrderDetail, b112ConfirmedOrder, b113CancelOrder, b114DeleteOrder, b115CancelOrderLine } from "@/api/order";
import { getParameter } from "@/utils/support";
import { messageSuccess } from "@/utils/support";
import Constants from "@/definition/constant";
import Dictionary from "@/definition/dictionary";

export default {
  // --- 画面名 ---
  name: "OrderDetailEdit",
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
    /**
     * カラー/サイズコード表示フィルター
     * @param {String} code コード値
     * @param {Object} dic カラー or サイズデータ一覧
     * @returns {String} - コードに対応したカラー or サイズの値（名称）
     */
    codeNameCaption: (code, name) => {
      return `${name}（${code + ""}）`;
    },
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
      // リスト読み込み用ローディング画面設定
      listLoading: true,
      // ローディング画面設定（データ登録/更新系には設定）
      fullscreenLoading: false,
      // 引当済商品ありフラグ（受注取消不可フラグ）
      isReserved: false,
      // 受注詳細情報
      orderDetail: {},
    };
  },
  // --- 算出プロパティ定義 ---
  computed: {
    /**
     * 受注ステータス定数値
     */
    orderStatus: {
      get() {
        // 項目辞書をプロパティ設定
        return Constants.OrderStatus;
      },
    },
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
     * 受注明細ステータス定数値
     */
    orderDetailStatus: {
      get() {
        // 項目辞書をプロパティ設定
        return Constants.OrderDetailStatus;
      },
    },
    /**
     * 受注明細ステータス辞書データ
     */
    orderDetailStatusDic: {
      get() {
        // 項目辞書をプロパティ設定
        return Dictionary.OrderDetailStatus;
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
      // 画面間パラメータの取得
      const params = getParameter("OrderDetailEdit");
      // 画面間パラメータチェック
      if ((!params.orderNo) || (params.orderNo <= 0)) {
        // 受注番号が設定していない場合はエラーページへ遷移する。
        location.replace("/400");
        return;
      }
      // 画面初期情報は同期して取得する
      (async () => {
        // 受注詳細情報を取得する
        await this.showOrderDetail(params.orderNo);
      })();
    },
    /**
     *  受注詳細情報取得
     *  @param {String} orderNo 受注番号
     */
    async showOrderDetail(orderNo) {
      this.listLoading = true;
      // B111:受注詳細取得
      const paramater = {
        orderNo: orderNo,
      };
      await b111GetOrderDetail(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`orderDetail：${JSON.stringify(response.data)}`);

        // 取得した受注情報をそのままDOMに設定
        this.orderDetail = response.data;
        // 受注取消ボタン使用可否チェック
        this.isReserved = false;
        if (this.orderDetail.orderStatus === Constants.OrderStatus.cancel ||
            this.orderDetail.orderStatus === Constants.OrderStatus.reserved) {
          this.isReserved = true;
        } else {
          for (const dtl of this.orderDetail.orderDetailList) {
            if (dtl.orderLineStatus === Constants.OrderDetailStatus.reserved) {
              // 明細に1件でも明細ステータスが引当済のレコードが存在する場合は受注取消ボタンを使用可
              this.isReserved = true;
            }
          }
        }
        // ローディングを解除
        this.listLoading = false;
      }).catch(
        // エラーの場合はローディングのみ解除
        this.listLoading = false,
      );
    },
    /**
     * 受注確認済更新
     * @param { Object } row - 選択行情報
     */
    onConfirmedOrder() {
      // 確認メッセージの表示
      this.$confirm(`ステータスを確認済に変更します。<br>一度更新を行うと戻すことはできません<br>よろしいですか。`, {
        dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
        type: "warning",
      }).then(() => {
        // 画面ロック
        this.fullscreenLoading = true;
        // B112:受注確認済更新
        const parameter = {
          orderNo: this.orderDetail.orderNo,
        };
        b112ConfirmedOrder(parameter).then((response) => {
          // 完了メッセージを表示
          messageSuccess("ステータスを確認済にしました。");
          // 画面ロック解除
          this.fullscreenLoading = false;
          // データ確認済更新後、一覧画面に戻る
          this.$router.push({ name: "OrderList" });
        }).catch(
          // エラーの場合は画面のみ戻す。
          this.fullscreenLoading = false,
        );
      });
    },
    /**
     * 受注取消更新
     * @param { Object } row - 選択行情報
     */
    onCancelOrder() {
      // 確認メッセージの表示
      this.$confirm(`受注を取消します。<br>一度取消を行うと戻すことはできません<br>よろしいですか。`, {
        dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
        type: "warning",
      }).then(() => {
        // 画面ロック
        this.fullscreenLoading = true;
        // B113:受注取消
        const parameter = {
          orderNo: this.orderDetail.orderNo,
        };
        b113CancelOrder(parameter).then((response) => {
          // 完了メッセージを表示
          messageSuccess("受注取消が完了しました。");
          // 画面ロック解除
          this.fullscreenLoading = false;
          // データ取消後、一覧画面に戻る
          this.$router.push({ name: "OrderList" });
        }).catch(
          // エラーの場合は画面のみ戻す。
          this.fullscreenLoading = false,
        );
      });
    },
    /**
     * 受注データ削除
     */
    onDeleteOrder() {
      // 確認メッセージの表示
      this.$confirm(`受注データを削除します。<br>一度削除を行うと戻すことはできません<br>よろしいですか。`, {
        dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
        type: "warning",
      }).then(() => {
        // 画面ロック
        this.fullscreenLoading = true;
        // B114:受注データ削除
        const parameter = {
          orderNo: this.orderDetail.orderNo,
        };
        b114DeleteOrder(parameter).then((response) => {
          // 完了メッセージを表示
          messageSuccess("データ削除が完了しました。");
          // 画面ロック解除
          this.fullscreenLoading = false;
          // データ削除をしたため、一覧画面に戻る
          this.$router.push({ name: "OrderList" });
        }).catch(
          // エラーの場合は画面のみ戻す。
          this.fullscreenLoading = false,
        );
      });
    },
    /**
     * 明細取消
     * @param { Object } row - 選択行情報
     */
    onCancelOrderLine(row) {
      // 確認メッセージの表示
      this.$confirm(`対象明細の受注を取消します。<br>一度取消を行うと戻すことはできません<br>よろしいですか。`, {
        dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
        type: "warning",
      }).then(() => {
        // 画面ロック
        this.fullscreenLoading = true;
        // B115:受注明細取消
        const parameter = {
          orderNo: this.orderDetail.orderNo,
          lineNo: row.lineNo,
        };
        b115CancelOrderLine(parameter).then((response) => {
          // 完了メッセージを表示
          messageSuccess("対象明細の受注取消が完了しました。");
          // 画面をリロードする
          this.showOrderDetail(this.orderDetail.orderNo);
          // リロード後に画面ロック解除
          this.fullscreenLoading = false;
        }).catch(
          // エラーの場合は画面のみ戻す。
          this.fullscreenLoading = false,
        );
      });
    },
    /**
     * 一覧へ戻る
     */
    onBackList() {
      this.$router.push({ name: "OrderList" });
    },
  },
};
</script>

<style scoped>
</style>
