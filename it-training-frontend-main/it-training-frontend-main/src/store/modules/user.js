import { login, logout, getLoginUserInfo } from "@/api/session";
import { getToken, setToken, removeToken } from "@/utils/auth";
// @Training 不要なimportを削除
// import router, { resetRouter } from "@/router";
import { resetRouter } from "@/router";

const state = {
  token: getToken(),
  id: "",
  name: "",
  avatar: "",
  introduction: "",
  roles: [],
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction;
  },
  SET_ID: (state, id) => {
    state.id = id;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  },
};

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { userId, password } = userInfo;
    return new Promise((resolve, reject) => {
      login({ userId: userId.trim(), password: password }).then(response => {
        /* -- @Training トークンはヘッダより取得 -- */
        // const { data } = response
        // commit('SET_TOKEN', data.token)
        // setToken(data.token)
        /* -- @Training トークンはCookieで連携するため、SET_TOKENには認証状態を保持するように修正 -- */
        const token = response.request.getResponseHeader("X-Auth-Token");
        // const token = "Authenticated";
        commit("SET_TOKEN", token);
        setToken(token);
        const roles = ["admin"];
        /* -- @Training ユーザー情報もストアに設定 -- */
        commit("SET_ID", response.data.userId);
        commit("SET_NAME", response.data.userName);
        commit("SET_ROLES", roles);
        /* -- @Training 以下は今回は未使用のためデフォルト設定 -- */
        commit("SET_AVATAR", null);
        commit("SET_INTRODUCTION", null);
        // @Training セッション情報に保持する
        console.log(`set-state:${JSON.stringify(state)}`);
        sessionStorage.setItem("training-state", JSON.stringify(this.state));
        resolve();
      }).catch(error => {
        reject(error);
      });
    });
  },

  // get user info
  getSessionInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      console.log(`get-state:${JSON.stringify(state)}`);
      getLoginUserInfo().then(response => {
        const { data } = response;
        if (!data) {
          reject("Verification failed, please Login again.");
        }
        /* -- @Training ユーザー情報もストアに再設定 -- */
        data["roles"] = ["admin"];
        commit("SET_ID", data.userId);
        commit("SET_NAME", data.userName);
        commit("SET_ROLES", data.roles);
        /* -- @Training 以下は今回は未使用のためデフォルト設定 -- */
        commit("SET_AVATAR", null);
        commit("SET_INTRODUCTION", null);
        // @Training セッション情報に保持する
        sessionStorage.setItem("training-state", JSON.stringify(this.state));
        resolve(data);
      }).catch(error => {
        reject(error);
      });
    });
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      console.log("debug:logout", state.token);
      logout(state.token).then(() => {
        commit("SET_TOKEN", "");
        commit("SET_ROLES", []);
        removeToken();
        resetRouter();
        // reset visited views and cached views
        // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
        // @Training 未使用の為コメント化
        // dispatch("tagsView/delAllViews", null, { root: true });
        // @Training ストア情報をクリア
        sessionStorage.setItem("training-state", undefined);

        resolve();
      }).catch(error => {
        reject(error);
      });
    });
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit("SET_TOKEN", "");
      commit("SET_ROLES", []);
      removeToken();
      resolve();
    });
  },

  // dynamically modify permissions
  // @Training 未使用の為、コメント化
  // async changeRoles({ commit, dispatch }, role) {
  //   const token = role + "-token";

  //   commit("SET_TOKEN", token);
  //   setToken(token);

  //   const { roles } = await dispatch("getInfo");

  //   resetRouter();

  //   // generate accessible routes map based on roles
  //   const accessRoutes = await dispatch("permission/generateRoutes", roles, { root: true });
  //   // dynamically add accessible routes
  //   router.addRoutes(accessRoutes);

  //   // reset visited views and cached views
  //   dispatch("tagsView/delAllViews", null, { root: true });
  // },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
