
const state = {
  //  @Customize 画面間パラメータ情報の追加
  parameter: {},
  //  @training 画面保持情報の追加
  viewStatus: {},
};

const mutations = {
  //  @Customize 画面間パラメータ情報の追加
  SET_PARAMETER: (state, parameter) => {
    state.parameter = parameter;
  },
  //  @training 画面保持情報の追加
  SET_VIEW_STATUS: (state, viewStatus) => {
    state.viewStatus = viewStatus;
  },
};

const actions = {
  // @Customize 画面間パラメータ情報の設定追加
  setParameter({ commit }, data) {
    // console.log("debug:setParameter", JSON.stringify(data));
    return new Promise(resolve => {
      commit("SET_PARAMETER", data);
      // セッション情報に保持する
      sessionStorage.setItem("training-state", JSON.stringify(this.state));
      resolve();
    });
  },

  // @training 画面保持情報を設定する
  setViewStatus({ commit }, data) {
    return new Promise(resolve => {
      // console.log("debug:setViewStatus", JSON.stringify(data));
      commit("SET_VIEW_STATUS", data);
      // セッション情報に保持する
      sessionStorage.setItem("training-state", JSON.stringify(this.state));
      resolve();
    });
  },

};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
