import request from "@/utils/request";

/**
 *  B011 ログイン
 * @module login
 * @param {Object} data ユーザ/パスワード情報
 * @returns {Object} - ログインユーザーデータ
 */
export function login(data) {
  return request({
    url: "/user/login",
    method: "post",
    data,
  });
}

/**
 * B012 セッションユーザー情報取得
 * @module getLoginUserInfo
 * @param {Object} params ユーザーキー情報（ユーザーID）
 * @returns {Object} - ユーザー詳細データ
 */
export function getLoginUserInfo() {
  return request({
    url: "/user/session",
    method: "get",
    params: { },
  });
}

/**
 * B013 ログアウト
 * @module logout
 * @returns {*} - なし
 */
export function logout() {
  return request({
    url: "/user/logout",
    method: "post",
  });
}
