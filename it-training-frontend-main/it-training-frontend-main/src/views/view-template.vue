<template>
  <!--
   --
   -->
  <div class="mixin-components-container">
    <el-card class="box-card">
      <!--ヘッダ -->
      <div slot="header" class="clearfix">
        <!-- ... -->
      </div>
      <!--画面コンテンツエリア -->
      <div class="card-content" style="min-height: calc(100vh - 210px)">
        <!-- ... -->
      </div>
    </el-card>
  </div>
</template>

<script>
// import記載

export default {
  // --- 画面名 ---
  name: "",
  // 他のコンポーネントを使用
  components: {},
  // --- 表示フィルター関数定義 ---
  filters: {},
  // 新しいコンポーネントを合成します
  extends: {},
  // コンポーネントのプロパティ定義
  props: {},
  // --- データプロパティ定義 ---
  data() {
    return {
    };
  },
  // --- 算出プロパティ定義 ---
  computed: {},
  // --- 監視プロパティ定義 ---
  watch: {},
  // --- コンポーネントライフサイクルフック---
  // --- 画面生成イベント ---
  created() {},
  beforeCreate() {},
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeDestroy() {},
  destroyed() {},
  // --- メソッド定義 ---
  methods: {
  },
};
</script>

<!-- export default {
  // このちいさな要素を忘れないでください
  name: "RangeSlider",
  // 他のコンポーネントを使用
  components: {},
  // フィルターを設定
  filters: {},
  // 新しいコンポーネントを合成します
  extends: {},
  // コンポーネントのプロパティ/値
  props: {
    // bar: {}, // アルファベット順
    // foo: {},
    // fooBar: {},
  },
  // 変数
  data() {},
  computed: {},
  watch: {},
  // コンポーネントライフサイクルフック
  beforeCreate() {},
  created() {},
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeDestroy() {},
  destroyed() {},
  // メソッド
  methods: {},
};
</script> -->

<style scoped>
	.Ranger__Wrapper { /* ... */ }
</style>
