<template>
  <!-- 共通：プルダウンコントロール -->
  <el-form-item
    ref="trFormItemSelect"
    :prop="propName"
    :rules="required && !disabled ? [requiredRule, existenceRule] : [existenceRule]"
    :size="size"
  >
    <el-select
      v-model="selectvalue"
      :disabled="disabled"
      :filterable="filterable"
      :clearable="!required"
      :size="size"
      :class="widthSizeClass"
      @change="$emit('change', $event)"
      @blur="$emit('blur', $event)"
    >
      <el-option
        v-for="item in selectList"
        :key="item[keyName] ? item[keyName] : null"
        :label="item[labelName] ? item[labelName] : null"
        :value="item[keyName] ? item[keyName] : null"
        @change="$emit('change', $event)"
      />
    </el-select>
  </el-form-item>
</template>

<script>
export default {
  /**
   * コンポーネント名
   */
  name: "TrSelect",
  /**
   * プロパティ定義
   */
  props: {
    /**
     *  value:選択値
     */
    value: {
      type: [String, Number],
      default: null,
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
     * selectList: 選択リストオブジェクト
     */
    selectList: {
      type: Array,
      required: true,
    },
    /**
     * keyName: Keyが設定されているプロパティ名
     */
    keyName: {
      type: String,
      required: true,
    },
    /**
     * labelName: labelが設定されているプロパティ名
     */
    labelName: {
      type: String,
      required: true,
    },
    /**
     * rangeCheck: 入力不可設定
     * true/false（デフォルト：true）入力不可とする場合、trueを設定する
     */
    rangeCheck: {
      type: Boolean,
      default: true,
    },
    /**
     * filterable : リストフィルタリング
     * true/false（デフォルト：false）テキスト入力にてリスト内をフィルタリングする場合、trueを設定する
     */
    filterable: {
      type: Boolean,
      default: false,
    },
    /**
     * multiple: 複数選択
     * true/false（デフォルト：false）複数選択を可能とする場合、trueを設定する
     */
    multiple: {
      type: Boolean,
      default: false,
    },
    /**
     * widthSize: コンポーネントの幅の設定
     * smallが（100vw/24）× 3とし、サイズが上がるごとに+1する
     */
    widthSize: {
      type: String,
      default: "auto",
      validator: (value) => {
        // プロパティの値は、必ずいずれかの文字列でなければならない
        return (
          ["auto", "small", "middle", "large", "x-large"].indexOf(value) !== -1
        );
      },
    },
    /**
     * innerClass: 個別設定CSSクラス
     * サイズ以外のスタイルやwidthSizeでは設定できない幅等を指定する際に使用するClass
     */
    innerClass: {
      type: String,
      default: undefined,
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
    /**
     * 選択肢内設定チェック
     *
     * @param {*} rule チェックルール
     * @param {*} value 入力値
     * @param {*} callback コールバック関数
     */
    const checkRangeValue = (rule, value, callback) => {
      if (!value) {
        callback();
        return;
      }
      if (!this.rangeCheck) {
        callback();
        return;
      }
      setTimeout(() => {
        for (var item of this.selectList) {
          if (item[this.keyName] === value) {
            // 選択している値がリスト内にあれば正常終了
            callback();
            return;
          }
        }
        // 選択肢になかった場合はエラーとする。
        const errorMessage = `${this.itemLabel ? this.itemLabel : "項目"} の値（${value}）は選択肢にありません。`;
        callback(new Error(errorMessage));
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
      existenceRule: {
        validator: checkRangeValue,
        trigger: ["blur", "change"],
      },
    };
  },
  /**
   * 算出プロパティ定義
   */
  computed: {
    /**
     * 選択値（入力値）
     */
    selectvalue: {
      get() {
        return this.value ? "" + this.value : null;
      },
      set(newVal) {
        this.$emit("input", newVal);
      },
    },
    /**
     * 幅設定CSSクラス
     */
    widthSizeClass: {
      get() {
        let className = "tr-select";
        // innerClassが設定されている場合はそれを適用する。
        if (this.innerClass) {
          className = this.innerClass;
        } else {
          // widthSizeにて指定された幅設定CSSを適用する。（tr-components.scssにて設定）
          switch (this.widthSize) {
            case "small":
              className = "tr-select-small";
              break;
            case "middle":
              className = "tr-select-middle";
              break;
            case "large":
              className = "tr-select-large";
              break;
            case "x-large":
              className = "tr-select-x-large";
              break;
          }
        }
        return className;
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
          this.$refs["zcFormItemSelect"].clearValidate();
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
          this.$refs["zcFormItemSelect"].clearValidate();
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
