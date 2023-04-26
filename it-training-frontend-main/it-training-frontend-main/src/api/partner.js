import request from "@/utils/request";

/**
 * B201 取引先検索一覧取得
 * @module b201GetPartnerList
 * @param {Object} params 抽出条件オブジェクト
 * @returns {Object} - 取引先一覧データ
 */
export function b201GetPartnerList(params) {
  return request({
    url: "/partner/list",
    method: "get",
    params: params,
  });
}

/**
 * B202 取引先削除
 * @module b202DeletePartner
 * @param {Object} params 取引先削除条件（取引先ID）
 * @returns {*} - なし
 */
export function b202DeletePartner(params) {
  return request({
    url: "/partner",
    method: "delete",
    params: params,
  });
}

/**
 * B211 取引先詳細取得
 * @module b211GetPartnerDetail
 * @param {Object} params 取引先キー情報（取引先ID）
 * @returns {Object} - 取引先詳細データ
 */
export function b211GetPartnerDetail(params) {
  return request({
    url: "/partner",
    method: "get",
    params: params,
  });
}

/**
 * B221/ B222 取引先登録/更新
 * @module b22xRegistPartner
 * @param {String} mode 処理モード（ADD:新規、UPD:更新）
 * @param {Object} data 登録/更新する取引先詳細データ
 * @returns {*} - なし
 */
export function b22xRegistPartner(mode, data) {
  let method = "post";
  if (mode === "ADD") {
    method = "put";
  }
  return request({
    url: "/partner",
    method: method,
    data,
  });
}

/**
 * B291 仕入先一覧取得
 * @module b291GetSupplierList
 * @param {Object} params 抽出条件オブジェクト
 * @returns {Object} - 仕入先一覧データ
 */
export function b291GetSupplierList(params) {
  return request({
    url: "/supplier/list",
    method: "get",
    params: params,
  });
}
