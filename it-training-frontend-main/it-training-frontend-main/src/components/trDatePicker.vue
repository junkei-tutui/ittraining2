<template>
  <!-- 共通：日付入力コンポーネント -->
  <el-form-item
    ref="trFormItemDatePicker"
    :prop="propName"
    :rules="(required && !disabled) ? [requiredRule] : []"
    :size="size"
  >
    <el-date-picker
      :value="inputValue"
      :type="type"
      :disabled="disabled"
      :format="format"
      :value-format="valueFormat"
      :size="size"
      :class="inputInnerClass"
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
  name: "TrDatePicker",
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
     * type: 入力種別
     * 例：year/month/date/dates/week/datetime/datetimerange/daterange/monthrange
     */
    type: {
      type: String,
      default: "date",
      validator: (value) => {
        // プロパティの値は、必ずいずれかの文字列でなければならない
        // "dates", "week", "datetimerange", "daterange", "monthrange"は使用不可
        return ["year", "month", "date", "datetime"].indexOf(value) !== -1;
      },
    },
    /**
     *  format: 入力ボックスに表示される書式。
     * Date Formats：yyyy/M/MM/W/WW/d/dd/H/HH/h/hh/m/mm/s/ss/A/a/timestamp/MM
     * 例） "yyyy/MM/dd" ⇒ "2022/03/01"
     */
    format: {
      type: String,
      default: "yyyy/MM/dd",
    },
    /**
     *  valueFormat: データ値の書式
     *  Date Formats：yyyy/M/MM/W/WW/d/dd/H/HH/h/hh/m/mm/s/ss/A/a/timestamp/MM
     * 例） "yyyy-MM-dd" ⇒ "2022-03-01"
     */
    valueFormat: {
      type: String,
      default: "yyyy-MM-dd",
    },
    /**
     * size: 文字サイズ
     * 例：large/small/mini
     */
    size: {
      type: String,
      default: "",
      validator: (value) => {
        // プロパティの値は、必ずいずれかの文字列でなければならない
        // "medium"はなくデフォルトは""（空白）
        return ["large", "", "small", "mini"].indexOf(value) !== -1;
      },
    },
  },
  /**
   * データプロパティ定義（DOM）
   */
  data() {
    return {
      requiredRule: {
        required: true,
        message: `${this.itemLabel ? this.itemLabel : "項目"}は必須です。`,
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
        const retDate = this.value;
        return retDate;
      },
    },
    /**
     * 入力コンポーネント適用CSSクラス
     */
    inputInnerClass: {
      get() {
        let setClass = "tr-date-picker";
        // 設定されたtypeによって幅を変更する。
        switch (this.type) {
          case "year":
            setClass = "tr-date-picker-year";
            break;
          case "month":
            setClass = "tr-date-picker-month";
            break;
          case "datetime":
            setClass = "tr-date-picker-datetime";
            break;
          default:
            break;
        }
        return setClass;
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
          this.$refs["trFormItemDatePicker"].clearValidate();
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
          this.$refs["trFormItemDatePicker"].clearValidate();
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
