<template>
  <!--
   -- U-051 : ユーザー登録/更新
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
          <!--ユーザー情報部 -->
          <!-- ユーザーID -->
          <el-row :gutter="10" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">ユーザーID</span>
            </el-col>
            <el-col :span="5">
              <div v-if="mode=='ADD'" class="grid-content">
                <tr-input
                  v-model="form.userId"
                  prop-name="userId"
                  :maxlength="64"
                  :required="true"
                  item-label="ユーザーID"
                  check-rule="half-width-alphabet-number"
                />
              </div>
              <div v-else class="grid-content-no-input">
                <span>{{ form.userId }}</span>
              </div>
            </el-col>
          </el-row>
          <!-- ユーザー名 -->
          <el-row :gutter="10" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">ユーザー名</span>
            </el-col>
            <el-col :span="5">
              <div class="grid-content">
                <tr-input
                  v-model="form.userName"
                  prop-name="userName"
                  :maxlength="64"
                  :required="true"
                  item-label="ユーザー名"
                  check-rule="default"
                />
              </div>
            </el-col>
          </el-row>
          <!-- パスワード -->
          <el-row :gutter="10" style="padding-left:10px">
            <el-col :span="3">
              <span class="grid-content-header">パスワード</span>
            </el-col>
            <el-col :span="5">
              <div class="grid-content">
                <tr-input
                  v-model="form.inputPassword"
                  prop-name="inputPassword"
                  :maxlength="64"
                  :required="true"
                  item-label="パスワード"
                  auto-complete="new-password"
                  :is-password="true"
                  :disabled="(isChangePassword!=true)"
                />
              </div>
            </el-col>
            <el-col v-if="mode!=='ADD'" :span="5">
              <span @click="onChangePasswordLock">
                <svg-icon
                  style="margin-top: 5px;"
                  :icon-class="isChangePassword ? 'zt_unlocked' : 'zt_lock'"
                />
              </span>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import { b511GetUserDetail, b51xRegistUser } from "@/api/user";
import { getParameter } from "@/utils/support";
import { messageSuccess, messageError } from "@/utils/support";

export default {
  // --- 画面名 ---
  name: "UserEdit",
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
      // 入力フォーム（ユーザー）
      form: {},
      // パスワード変更フラグ
      isChangePassword: true,

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
        if (this.mode === "ADD") {
          // 入力項目の初期化を行う
          this.form = {
            userId: "",
            userName: "",
            inputPassword: "",
          };
          // 新規の場合は必ずパスワード変更あり
          this.isChangePassword = true;
        } else {
          // 画面間パラメータの取得
          const params = getParameter("UserEdit");

          // 画面間パラメータチェック
          if ((!params.userId) || (params.userId.length <= 0)) {
          // エラーページへ遷移する。
            location.replace("/400");
            return;
          }
          // ユーザー詳細情報を取得する
          await this.showUserDetail(params.userId);
        }
      })();
    },
    // ユーザー詳細情報取得
    async showUserDetail(userId) {
      // B511:ユーザー情報取得
      const paramater = {
        userId: userId,
      };
      await b511GetUserDetail(paramater).then((response) => {
        // TODO: 取得データの確認用
        console.log(`userDetail：${JSON.stringify(response.data)}`);

        // 取得したユーザー情報をDOMにマッピングする
        // ※ フィールド名が異なるため、項目毎のマッピングを行うこと
        this.form = {
          userId: response.data.userId,
          userName: response.data.userName,
          // 更新の場合はデフォルトパスワード変更なしおよびダミーのパスワードを設定
          inputPassword: "password",
        };
        // パスワード変更フラグを変更なしにデフォルト設定
        this.isChangePassword = false;
      }).catch(
        // 特に処理なし
      );
    },
    /**
     * パスワード変更ロック切替
     */
    onChangePasswordLock() {
      // パスワード変更フラグを反転させる
      this.isChangePassword = !this.isChangePassword;
      // パスワード変更フラグ反転後、変更なしとなった場合は入力パスワードをダミー設定する。
      if (this.isChangePassword !== true) {
        this.form.inputPassword = "password";
      } else {
        // ロック解除時は必ずパスワードは初期化する。
        this.form.inputPassword = "";
      }
    },
    /**
     * 保存処理
     */
    onSave() {
      // 入力バリデーションチェックを行う。
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("ユーザー情報を保存します。\nよろしいですか。", {
            dangerouslyUseHTMLString: true, // メッセージに改行を入れるために設定
            type: "warning",
          })
            .then(() => {
              // 画面ロック
              this.fullscreenLoading = true;
              // B512/B513:ユーザー登録/更新（モードにより切り分け）
              // 登録時はinputPasswordは必須、isChangePasswordは無視される。
              const parameter = {
                userId: this.form.userId,
                userName: this.form.userName,
                isChangePassword: this.isChangePassword,
                inputPassword: this.form.inputPassword,
              };
              b51xRegistUser(this.mode, parameter).then((response) => {
                // 完了メッセージを表示
                messageSuccess("ユーザー情報を保存しました。");

                // 画面ロック解除
                this.fullscreenLoading = false;

                // 登録／更新後は一覧画面に戻る
                this.$router.push({ name: "UserList" });
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
      this.$router.push({ name: "UserList" });
    },
  },
};
</script>

<style scoped>
</style>
