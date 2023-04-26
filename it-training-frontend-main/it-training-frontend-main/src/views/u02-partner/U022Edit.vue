<template>
  <!--
   -- U-022 : 取引先登録/更新
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
      <div class="card-content" style="min-height: calc(100vh - 210px)">
        <el-form ref="form" label-position="left" :model="form" status-icon>
          <!--取引先情報部 -->
          <!-- 取引先ID -->
          <el-row :gutter="10" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">取引先ID</span>
            </el-col>
            <el-col :span="5">
              <div v-if="mode=='ADD'" class="grid-content">
                <tr-input
                  v-model="form.partnerId"
                  prop-name="partnerId"
                  :maxlength="16"
                  :required="true"
                  item-label="取引先ID"
                  check-rule="half-width-alphabet-number"
                />
              </div>
              <div v-else class="grid-content-no-input">
                <span>{{ form.partnerId }}</span>
              </div>
            </el-col>
          </el-row>
          <!-- 取引先名 -->
          <el-row :gutter="24" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">取引先名</span>
            </el-col>
            <el-col :span="10">
              <div class="grid-content">
                <tr-input
                  v-model="form.partnerName"
                  prop-name="partnerName"
                  :maxlength="64"
                  :required="true"
                  item-label="取引先名"
                  check-rule="default"
                />
              </div>
            </el-col>
          </el-row>
          <!-- 住所 -->
          <el-row :gutter="24" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">住所</span>
            </el-col>
            <el-col :span="1">
              <div class="grid-content">〒</div>
            </el-col>
            <el-col :span="3">
              <div class="grid-content">
                <tr-zip-code
                  v-model="form.zip"
                  prop-name="zip"
                  :required="true"
                  item-label="郵便番号"
                />
              </div>
            </el-col>
            <el-col :span="10">
              <div class="grid-content">
                <tr-input
                  v-model="form.address"
                  prop-name="address"
                  :maxlength="64"
                  :required="true"
                  item-label="住所"
                  check-rule="default"
                />
              </div>
            </el-col>
          </el-row>
          <!-- 電話番号 -->
          <el-row :gutter="24" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">電話番号</span>
            </el-col>
            <el-col :span="5">
              <div class="grid-content">
                <tr-tel-fax
                  v-model="form.telNo"
                  prop-name="telNo"
                  :required="true"
                  :no-hyphen="true"
                  item-label="電話番号"
                />
              </div>
            </el-col>
          </el-row>
          <!-- FAX -->
          <el-row :gutter="24" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">ＦＡＸ</span>
            </el-col>
            <el-col :span="5">
              <div class="grid-content">
                <tr-tel-fax
                  v-model="form.faxNo"
                  prop-name="faxNo"
                  :no-hyphen="true"
                  item-label="ＦＡＸ"
                />
              </div>
            </el-col>
          </el-row>
          <!-- 取引先担当者 -->
          <el-row :gutter="24" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">取引先担当者</span>
            </el-col>
            <el-col :span="5">
              <div class="grid-content">
                <tr-input
                  v-model="form.responsibleParty"
                  prop-name="responsibleParty"
                  :maxlength="64"
                  :required="true"
                  item-label="取引先担当者"
                  check-rule="default"
                />
              </div>
            </el-col>
          </el-row>
          <!-- メールアドレス -->
          <el-row :gutter="24" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">メールアドレス</span>
            </el-col>
            <el-col :span="5">
              <div class="grid-content">
                <tr-email
                  v-model="form.mailAddress"
                  prop-name="mailAddress"
                  :maxlength="64"
                  :required="true"
                  item-label="メールアドレス"
                />
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b211GetPartnerDetail, b22xRegistPartner } from "@/api/partner";
import { getParameter } from "@/utils/support";
import { messageSuccess, messageError } from "@/utils/support";

export default {
  // --- 画面名 ---
  name: "PartnerEdit",
  // --- 表示フィルター関数定義 ---
  filters: {},
  // --- データプロパティ定義 ---
  data() {
    return {
      // ローディング画面設定（データ登録/更新系には設定）
      fullscreenLoading: false,
      // 画面モード情報
      title: "未設定",
      mode: "",
      // 入力フォーム（取引先）
      form: {},
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
        // 編集モードチェック
        if (this.mode === "ADD") {
          // 新規の場合は入力項目の初期化を行う
          this.form = {
            partnerId: "",
            partnerName: "",
            partnerType: 0,
            zip: "",
            address: "",
            telNo: "",
            faxNo: "",
            responsibleParty: "",
            mailAddress: "",
          };
        } else {
          // 変更の場合は設定済みデータを取得する。
          // 画面間パラメータの取得
          const params = getParameter("PartnerEdit");
          // 画面間パラメータチェック
          if ((!params.partnerId) || (params.partnerId.length <= 0)) {
          // 取引先IDが設定していない場合はエラーページへ遷移する。
            location.replace("/400");
            return;
          }
          // 取引先詳細情報を取得する
          await this.showPartnerDetail(params.partnerId);
        }
      })();
    },
    /**
     *  取引先詳細情報取得
     *  @param {String} partnerId 取引先ID
     */
    async showPartnerDetail(partnerId) {
      // B211:取引先詳細取得
      const paramater = {
        partnerId: partnerId,
      };
      await b211GetPartnerDetail(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`partnerDetail：${JSON.stringify(response.data)}`);

        // 取得した取引先情報をそのままDOMに設定
        this.form = response.data;
      }).catch(
        // 特に処理なし
      );
    },
    /**
     * 保存処理
     */
    onSave() {
      // 入力バリデーションチェックを行う。
      this.$refs["form"].validate((valid) => {
        if (valid) {
          // バリデーションエラーがない場合は確認画面を表示する
          this.$confirm("取引先情報を保存します。\nよろしいですか。", {
            dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
            type: "warning",
          })
            .then(() => {
              // 画面ロック
              this.fullscreenLoading = true;
              // B221/B222:取引先登録/更新（モードにより切り分け）
              const parameter = {
                partnerId: this.form.partnerId,
                partnerName: this.form.partnerName,
                partnerType: 0,
                zip: this.form.zip,
                address: this.form.address,
                telNo: this.form.telNo,
                faxNo: this.form.faxNo,
                responsibleParty: this.form.responsibleParty,
                mailAddress: this.form.mailAddress,
              };
              b22xRegistPartner(this.mode, parameter).then((response) => {
                // 完了メッセージを表示
                messageSuccess("取引先情報を保存しました。");

                // 画面ロック解除
                this.fullscreenLoading = false;

                // 処理モードにより遷移先を変更する。
                if (this.mode === "ADD") {
                  // 登録時は一覧画面に戻る
                  this.$router.push({ name: "PartnerList" });
                } else {
                  // 更新時は照会画面に戻る
                  this.$router.push({ name: "PartnerInquiry" });
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
      this.$router.push({ name: "PartnerList" });
    },
  },
};
</script>

<style scoped>
</style>
