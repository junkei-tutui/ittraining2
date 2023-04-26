<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  // @training セッション情報をセッションストレージに保持する
  created() {
    // ストア情報を復元
    if (sessionStorage.getItem("training-state")) {
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem("training-state")),
        ),
      );
    }
    window.addEventListener("beforeunload", () => {
      // sessionStorage.setItem("token", this.$store.getters.token);
      // 画面リロード時にストア情報を保持
      sessionStorage.setItem("training-state", JSON.stringify(this.$store.state));
    });
  },
};
</script>
