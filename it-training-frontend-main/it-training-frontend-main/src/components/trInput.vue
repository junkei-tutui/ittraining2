<template>
  <!-- 共通：文字入力コントロール -->
  <el-form-item
    ref="trFormItemInput"
    :prop="propName"
    :rules="(required && !disabled) ? [requiredRule, individualValidationRule] : [individualValidationRule]"
    :size="size"
  >
    <el-input
      :value="value"
      :minlength="minlength"
      :maxlength="maxlength"
      :show-password="isPassword"
      :type="isPassword ? 'password' : 'text'"
      :size="size"
      :show-word-limit="false"
      :disabled="disabled"
      :autocomplete="autoComplete"
      @input="$emit('input', $event)"
      @blur="$emit('blur', $event)"
      @change="$emit('change', $event)"
    />
  </el-form-item>
</template>

<script>
import stringFormatPattern from "./unit/stringFormatPattern";

export default {
  /**
   * コンポーネント名
   */
  name: "TrInput",
  /**
   * プロパティ定義
   */
  props: {
    /**
     *  value:入力値
     */
    value: {
      type: String,
      default: "",
    },
    /**
     * propName:コントロール名
     */
    propName: {
      type: String,
      required: true,
    },
    /**
     *  required: 必須設定
     *  true/false（デフォルト：false）
     *  必須項目の場合はtrueを設定する。
     */
    required: {
      type: Boolean,
      default: false,
    },
    /**
     *  disabled: 入力不可設定
     *  true/false（デフォルト：false）
     *  入力不可とする場合、trueを設定する。
     */
    disabled: {
      type: Boolean,
      default: false,
    },
    /**
     * itemLabel: エラー時の表示項目名
     * 必須エラー時に表示される項目名を設定する。
     */
    itemLabel: {
      type: String,
      default: undefined,
    },
    /**
     * minlength:最小入力文字数
     * 入力できる最低の文字数
     */
    minlength: {
      type: Number,
      default: 0,
    },
    /**
     * maxlength:最大入力文字数
     * 入力できる最大の文字数
     */
    maxlength: {
      type: Number,
      default: 256,
    },
    /**
     * checkRule:入力規則
     * 指定された入力規則でバリデーションチェックを行う。
     */
    // checkRule:入力規則 。指定された入力規則でバリデーションチェックを行う。
    checkRule: {
      type: String,
      default: "default",
      validator: (value) => {
        // プロパティの値は、必ずいずれかの文字列でなければならない
        return ["default", "without-mark", "full-width", "full-width-kana", "full-width-number",
          "half-width", "half-width-number", "half-width-integer", "half-width-alphabet",
          "half-width-alphabet-number", "half-width-without-kana", "half-width-kana"].indexOf(value) !== -1;
      },
    },
    /**
     * isPassword:パスワード入力設定
     * trueを設定した場合入力値がマスクされる。
     */
    isPassword: {
      type: Boolean,
      default: false,
    },
    // autoComplete: 自動補完設定,
    // 設定可能な値は、以下のWebページを参照
    // https://developer.mozilla.org/ja/docs/Web/HTML/Attributes/autocomplete
    // 新しいアカウント作成などパスワードを自動補完させたくない場合は"new-password"を設定する。
    autoComplete: {
      type: String,
      default: undefined,
    },
    /**
     * isChangeUpperCase:大文字変換フラグ
     * trueを設定した場合、小文字で入力した値を大文字に自動変換する
     * isChangeLowerCaseよりも優先に動作する
     */
    isChangeUpperCase: {
      type: Boolean,
      default: false,
    },
    /**
     * isChangeLowerCase:小文字変換フラグ
     * trueを設定した場合、大文字で入力した値を小文字に自動変換する
     * isChangeUpperCaseにtrueを設定された場合は適用されない
     */
    isChangeLowerCase: {
      type: Boolean,
      default: false,
    },
    /**
     * isNormalizer:自動半角変換設定
     * trueを設定した場合、Normalizerにより全角英数字を半角に変換する
     */
    isNormalizer: {
      type: Boolean,
      default: false,
    },
    /**
     * size: 文字サイズ
     * 例：medium/small/mini
     */
    size: {
      type: String,
      default: "",
      validator: (value) => {
        // プロパティの値は、必ずいずれかの文字列でなければならない
        // デフォルトは""（空白）で"medium"と同じ？
        return ["medium", "", "small", "mini"].indexOf(value) !== -1;
      },
    },
  },
  /**
   * データプロパティ定義（DOM）
   */
  data() {
    /**
     * 個別バリデーションチェック処理
     *
     * @param {*} rule チェックルール
     * @param {*} value 入力値
     * @param {*} callback コールバック関数
     */
    const stringFormatCheck = (rule, value, callback) => {
      let reg;
      // 必須でない場合の未設定はスルー
      if (!value || value === "") {
        callback();
        return;
      }
      // デフォルトのフォーマット（正規表現）を設定
      reg = /./g;
      if (this.isPassword) {
        reg = stringFormatPattern["password"];
      } else if (this.checkRule !== "default") {
        // フォーマット表をチェックする
        if (stringFormatPattern[this.checkRule]) {
          // フォーマット表に存在する番号であれば設定
          reg = stringFormatPattern[this.checkRule];
        }
      }
      setTimeout(() => {
        if (reg.test(value)) {
          // バリデーションチェックOKの場合は終了
          callback();
        } else {
          // errorMessage
          callback(new Error(`${this.itemLabel ? this.itemLabel : "項目"}の形式が不正です。`));
        }
      }, 100);
    };
    return {
      // 必須バリデーションルール設定
      requiredRule: {
        required: true,
        message: `${this.itemLabel ? this.itemLabel : "項目"}は必須です。`,
        trigger: ["blur", "change"],
      },
      // 個別バリデーションルール設定
      individualValidationRule: {
        validator: stringFormatCheck,
        trigger: ["blur", "change"],
      },
    };
  },
  /**
   * 算出プロパティ定義
   */
  computed: {},
  /**
   * 監視設定
   */
  watch: {
    // 入力値の監視
    value: {
      deep: true,
      immediate: false,
      handler(newValue) {
        if (newValue) {
          let changeValue = newValue;
          if (this.isChangeUpperCase === true) {
            changeValue = newValue.toUpperCase();
          } else if (this.isChangeLowerCase === true) {
            changeValue = newValue.toLowerCase();
          }
          // 全角英数字を半角に変換
          if (this.isNormalizer === true) {
            changeValue = changeValue.replace(/[Ａ-Ｚａ-ｚ０-９]/g, (s) =>
              String.fromCharCode(s.charCodeAt(0) - 0xfee0),
            );
          }
          if (newValue !== changeValue) {
            this.$emit("input", changeValue);
          }
        }
      },
    },
    // 必須フラグの監視
    required: {
      deep: true,
      immediate: false,
      handler(newValue, oldValue) {
        if (newValue !== oldValue) {
          // 必須項目が変更されている場合はバリデーションをクリアする。
          this.$refs["trFormItemInput"].clearValidate();
        }
      },
    },
    // 非活性フラグの監視
    disabled: {
      deep: true,
      immediate: false,
      handler(newValue, oldValue) {
        if (newValue) {
          // 非活性可された場合はバリデーションをクリアする。
          this.$refs["trFormItemInput"].clearValidate();
        }
      },
    },
  },
  /**
   * コンポーネント初期化
   */
  created() {},
  /**
   * メソッド定義
   */
  methods: {},
};
</script>

<style lang="scss" scoped>
</style>
