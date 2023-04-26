<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"></div>
    <sidebar class="sidebar-container" />
    <!-- @Training needTagsViewは使用しない -->
    <!--
    <div :class="{hasTagsView:needTagsView}" class="main-container">
    -->
    <div class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <navbar />
        <!-- @Training Navバー（TOP）のみ使用
        <tags-view v-if="needTagsView" />
        -->
      </div>
      <app-main />
      <!-- @Training メニュー設定画面は未使用のため隠す
      <right-panel v-if="showSettings">
        <settings />
      </right-panel>
      -->
    </div>
  </div>
</template>

<script>
/* -- @Training 使用するコンポーネントのみに整理  -- */
// import RightPanel from '@/components/RightPanel'
// import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
// import ResizeMixin from './mixin/ResizeHandler'
// import { mapState } from 'vuex'
import { AppMain, Navbar, Sidebar } from "./components";
import ResizeMixin from "./mixin/ResizeHandler";

export default {
  name: "Layout",
  // components: {
  //   AppMain,
  //   Navbar,
  //   RightPanel,
  //   Settings,
  //   Sidebar,
  //   TagsView
  // },
  components: {
    AppMain,
    Navbar,
    Sidebar,
  },
  mixins: [ResizeMixin],
  computed: {
    // ...mapState({
    //   sidebar: state => state.app.sidebar,
    //   device: state => state.app.device,
    //   showSettings: state => state.settings.showSettings,
    //   needTagsView: state => state.settings.tagsView,
    //   fixedHeader: state => state.settings.fixedHeader
    // }),
    sidebar() {
      return this.$store.state.app.sidebar;
    },
    device() {
      return this.$store.state.app.device;
    },
    fixedHeader() {
      return this.$store.state.settings.fixedHeader;
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === "mobile",
      };
    },
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch("app/closeSideBar", { withoutAnimation: false });
    },
  },
};
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";
  @import "~@/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px)
  }

  .mobile .fixed-header {
    width: 100%;
  }
</style>
