<template>
  <!-- 共通：郵便番号入力コントロール -->
  <el-form-item
    ref="trFormItemZipCode"
    :prop="propName"
    :rules="(required && !disabled)? [requiredRule, zipCodeRule] : [zipCodeRule]"
    :size="size"
  >
    <el-input
      :value="inputValue"
      :show-word-limit="false"
      maxlength="8"
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
  name: "TrZipCode",
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
     * noHyphen: ハイフン除去設定
     * true/false（デフォルト：true）trueの場合はハイフン抜きの郵便番号を返す
     */
    noHyphen: {
      type: Boolean,
      default: true,
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
    const checkZipCode = (rule, value, callback) => {
      const zipReg01 = /^[0-9]{7}$/g;
      const zipReg02 = /^[0-9]{3}[-][0-9]{4}$/g;
      setTimeout(() => {
        if (value.length === 0) {
          // 未入力状態の場合はスルー
          callback();
        } else if (zipReg01.test(value)) {
          // 数字7桁の場合は無条件でOKとする。
          callback();
        } else if (zipReg02.test(value)) {
          // 数字3桁＋ハイフン＋数字4桁の場合
          if (this.noHyphen) {
            // ハイフン除去指定がされている場合は除去する。
            this.$emit("input", value.replace(/-/g, ""));
          }
          callback();
        } else {
          callback(
            new Error(`${this.itemLabel ? this.itemLabel : "郵便番号"}の形式が不正です。`),
          );
        }
      }, 100);
    };
    return {
      // 必須バリデーションルール設定
      requiredRule: {
        required: true,
        message: `${this.itemLabel ? this.itemLabel : "郵便番号"}は必須です。`,
        trigger: ["blur", "change"],
      },
      // 個別バリデーションルール設定
      zipCodeRule: {
        validator: checkZipCode,
        trigger: ["blur", "change"],
      },
    };
  },
  /**
   * 算出プロパティ定義
   */
  computed: {
    /**
     * 入力値
     */
    inputValue: {
      get() {
        let val = this.value ? this.value.toString() : "";
        // ハイフン付入力の場合はハイフンを付与して表示
        if (this.noHyphen) {
          if (val.replace(/-/g, "").length >= 7) {
            val = val.replace(/-/g, "");
            val = val.slice(0, 3) + "-" + val.slice(3);
          }
        }
        return val;
      },
    },

  },
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
          this.$refs["trFormItemZipCode"].clearValidate();
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
          this.$refs["trFormItemZipCode"].clearValidate();
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
