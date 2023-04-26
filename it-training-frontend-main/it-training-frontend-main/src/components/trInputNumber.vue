<template>
  <!-- 共通：数値入力コントロール -->
  <el-form-item
    ref="trFormItemNumber"
    :prop="propName"
    :rules="
      required && !disabled
        ? [requiredRule, commomRule, commomRule2]
        : [commomRule, commomRule2]
    "
    :size="size"
  >
    <el-input
      tr-input-number="TrInputNumber"
      :value="inputValue"
      :precision="precision"
      :size="size"
      :disabled="disabled"
      on-focus="this.select();"
      @input="$emit('input', $event)"
      @blur="$emit('blur', $event)"
    />
  </el-form-item>
</template>

<script>
export default {
  /**
   * コンポーネント名
   */
  name: "TrInputNum",
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
     *  isRequiredMin: 必須時最低入力値判定
     *  必須の場合のみMin設定が有効となる。
     */
    isRequiredMin: {
      type: Boolean,
      default: false,
    },
    /**
     *  min: 最小値設定
     * デフォルト0とする
     */
    min: {
      type: Number,
      default: 0,
    },
    /**
     *  min: 最大値設定
     * デフォルト1兆とする
     */
    max: {
      type: Number,
      default: 9999999999999,
    },
    /**
     * precision:小数点以下の桁数
     * デフォルト0、小数の入力が可能な場合の小数以下の桁数を設定
     */
    precision: {
      type: Number,
      default: 0,
    },
    /**
     * commaSeparated:カンマ編集設定
     * true/false（デフォルト：true）カンマ編集した入力/表示を行う場合はtrueを設定する
     */
    commaSeparated: {
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
     * 数値チェック
     * @param {*} rule チェックルール
     * @param {*} value 入力値
     * @param {*} callback コールバック関数
     * @param {*} changeFlg 値変更時判定フラグ
     */
    var numericalValueCheck = (rule, value, callback, changeFlg = true) => {
      let num;
      if (value === undefined || value === "") {
        callback();
        return;
      } else {
        if (typeof value !== "number") {
          if (value === null) value = "";
          num = Number(value.toString().replace(/,/g, ""));
        } else {
          num = value;
        }
      }
      setTimeout(() => {
        if (!isNaN(num)) {
          // 最大値チェック
          if (this.max) {
            if (this.max < num) {
              const errorMessage = `${this.itemLabel ? this.itemLabel : "項目"}は${this.max.toString()}以下の値を入力してください。`;
              callback(new Error(errorMessage));
              return;
            }
          }
          // 最小値チェック
          if (this.min || this.min === 0) {
            if (this.min > num && (this.isRequiredMin ? this.required : true)) {
              const errorMessage = `${this.itemLabel ? this.itemLabel : "項目"}は${this.min.toString()}以上の値を入力してください。`;
              callback(new Error(errorMessage));
              return;
            }
          }
          if (!changeFlg) {
            // 変更時（入力時は入力反映をしない）
            num = num.toFixed(this.precision);
            this.$emit("input", num);
          }
          callback();
        } else {
          const errorMessage = `${this.itemLabel ? this.itemLabel : "項目"}の形式が不正です。`;
          callback(new Error(errorMessage));
        }
      }, 100);
    };
    /**
     * ロストフォーカス時チェック
     *
     * @param {*} rule チェックルール
     * @param {*} value 入力値
     * @param {*} callback コールバック関数
     */
    var checkOnBlur = (rule, value, callback) => {
      numericalValueCheck(rule, value, callback, false);
    };
    /**
     * 入力値変更時チェック
     *
     * @param {*} rule チェックルール
     * @param {*} value 入力値
     * @param {*} callback コールバック関数
     */
    var checkOnChange = (rule, value, callback) => {
      numericalValueCheck(rule, value, callback);
    };
    return {
      // 必須バリデーションルール設定
      requiredRule: {
        required: true,
        message: `${this.itemLabel ? this.itemLabel : "項目"}は必須です。`,
        trigger: ["blur", "change"],
      },
      // 個別バリデーションルール設定（ロストフォーカス用）
      commomRule: {
        validator: checkOnBlur,
        trigger: ["blur"],
      },
      // 個別バリデーションルール設定（値変更用）
      commomRule2: {
        validator: checkOnChange,
        trigger: ["change"],
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
        // console.log("this.value:" + this.value);
        let num = this.value;
        let precisionLen = 0;
        if (typeof this.value !== "number") {
          if (
            this.value === "" ||
            this.value === undefined ||
            this.value === null
          ) {
            return this.value;
          }
          num = Number(this.value.replace(/,/g, ""));
          if (isNaN(num)) {
            return this.value;
          }

          // 小数部の桁が満たされていない場合は入力途中の可能性があるため入力値のまま表示
          if (this.value.toString().indexOf(".") > 0) {
            var array = this.value.toString().split(".");
            precisionLen = array[1].length;
          } else {
            precisionLen = 0;
          }
          if (precisionLen !== this.precision) {
            return this.value;
          }
          // 上記以外は数値として再表示設定をする。
          // return this.value
        } else {
          precisionLen = this.precision;
        }
        // console.log("init:" + this.value);
        let valStr;
        // 小数部を補填した数値として再登録する。
        if (this.value !== num.toFixed(this.precision)) {
          this.$emit("input", num.toFixed(this.precision));
        }
        // カンマ付き表示チェック
        if (this.commaSeparated) {
          // console.log("precision:" + precisionLen)
          // 常に小数点以下を表示するようにする
          valStr = num.toLocaleString(undefined, {
            minimumFractionDigits: this.precision,
            maximumFractionDigits: this.precision,
          });
        } else {
          // 常に小数点以下を表示するようにする
          valStr = num.toFixed(this.precision).toString();
        }
        return valStr;
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
          this.$refs["trFormItemNumber"].clearValidate();
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
          this.$refs["trFormItemNumber"].clearValidate();
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

<style>
 /* el-input 数値入力 */
.el-input__inner[tr-input-number="TrInputNumber"] {
  text-align:right !important;
  padding-right: 24px;
}
</style>
