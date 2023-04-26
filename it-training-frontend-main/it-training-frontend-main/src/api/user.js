import request from "@/utils/request";

/**
 * B501 ユーザー検索一覧取得
 * @module b501GetUserList
 * @param {Object} params 抽出条件オブジェクト
 * @returns {Object} - ユーザー一覧データ
 */
export function b501GetUserList(params) {
  return request({
    url: "/user/list",
    method: "get",
    params: params,
  });
}

/**
 * B502 ユーザー削除
 * @module b502DeleteUser
 * @param {Object} params ユーザー削除条件（ユーザーID）
 * @returns {*} - なし
 */
export function b502DeleteUser(params) {
  return request({
    url: "/user",
    method: "delete",
    params: params,
  });
}

/**
 * B511 ユーザー情報取得
 * @module b511GetUserDetail
 * @param {Object} params ユーザーキー情報（ユーザーID）
 * @returns {Object} - ユーザー詳細データ
 */
export function b511GetUserDetail(params) {
  return request({
    url: "/user",
    method: "get",
    params: params,
  });
}

/**
 * B512/ B513 ユーザー登録/更新
 * @module b51xRegistUser
 * @param {String} mode 処理モード（ADD:新規、UPD:更新）
 * @param {Object} data 登録/更新するユーザー詳細データ
 * @returns {*} - なし
 */
export function b51xRegistUser(mode, data) {
  let method = "post";
  if (mode === "ADD") {
    method = "put";
  }
  return request({
    url: "/user",
    method: method,
    data,
  });
}
