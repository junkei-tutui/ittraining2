<template>
  <!-- 共通：電話・FAX番号入力コントロール -->
  <el-form-item
    ref="trFormItemTelFax"
    :prop="propName"
    :rules="(required && !disabled) ? [requiredRule, telFaxRule] : [telFaxRule]"
    :size="size"
  >
    <el-input
      :value="value"
      :show-word-limit="false"
      maxlength="13"
      :size="size"
      :disabled="disabled"
      @input="$emit('input', $event)"
      @blur="$emit('blur', $event)"
      @change="$emit('change', $event)"
    />
  </el-form-item>
</template>

<script>
export default {
  /**
   * コンポーネント名
   */
  name: "TrTelFax",
  /**
   * プロパティ定義
   */
  props: {
    /**
     *  value:選択値
     */
    value: {
      type: [Number, String],
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
    const checkTelFax = (rule, value, callback) => {
      const TelReg = /^(\+?81|0)\d{1,4}[ \-]?\d{1,4}[ \-]?\d{4}$/;
      setTimeout(() => {
        if (TelReg.test(value)) {
          callback();
        } else {
          if (value !== "") {
            callback(new Error(`${this.itemLabel ? this.itemLabel : "電話番号"}の形式が不正です。`));
          } else {
            callback();
          }
        }
      }, 100);
    };
    return {
      // 必須バリデーションルール設定
      requiredRule: {
        required: true,
        message: `${this.itemLabel ? this.itemLabel : "電話番号"}は必須です。`,
        trigger: ["blur", "change"],
      },
      // 個別バリデーションルール設定
      telFaxRule: {
        validator: checkTelFax,
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
    // 必須フラグの監視
    required: {
      deep: true,
      immediate: false,
      handler(newValue, oldValue) {
        if (newValue !== oldValue) {
          // 必須項目が変更されている場合はバリデーションをクリアする。
          this.$refs["trFormItemTelFax"].clearValidate();
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
          this.$refs["trFormItemTelFax"].clearValidate();
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
