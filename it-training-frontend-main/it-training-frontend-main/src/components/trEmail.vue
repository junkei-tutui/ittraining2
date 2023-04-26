<template>
  <!-- 共通：メールアドレス入力コントロール -->
  <el-form-item
    ref="trFormItemEmail"
    :prop="propName"
    :rules="(required && !disabled) ? [requiredRule, emailRule] : [emailRule]"
    :size="size"
  >
    <el-input
      :value="value"
      :maxlength="maxlength"
      :show-word-limit="false"
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
  name: "TrEmail",
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
     * maxlength:最大入力文字数
     * 入力できる最大の文字数
     */
    maxlength: {
      type: Number,
      default: 256,
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
    return {
      // 必須バリデーションルール設定
      requiredRule: {
        required: true,
        message: `${this.itemLabel ? this.itemLabel : "メールアドレス"}の形式が不正です。`,
        trigger: ["blur", "change"],
      },
      // メールアドレス固有バリデーションルール設定
      emailRule: {
        type: "email",
        message: `${this.itemLabel ? this.itemLabel : "メールアドレス"}は必須です。`,
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
          this.$refs["trFormItemEmail"].clearValidate();
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
          this.$refs["trFormItemEmail"].clearValidate();
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
