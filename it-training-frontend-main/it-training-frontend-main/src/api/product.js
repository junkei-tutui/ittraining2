import request from "@/utils/request";

/**
 * B301 商品一覧取得
 * @module b301GetProductItemList
 * @param {Object} params 抽出条件オブジェクト
 * @returns {Object} - 商品一覧データ
 */
export function b301GetProductItemList(params) {
  return request({
    url: "/product/list",
    method: "get",
    params: params,
  });
}

/**
 * B302 商品（SKU）削除
 * @module b302DeleteProductItem
 * @param {Object} params 商品削除条件（商品コード）
 * @returns {*} - なし
 */
export function b302DeleteProductItem(params) {
  return request({
    url: "/product",
    method: "delete",
    params: params,
  });
}

/**
 * B311 商品（SKU）取得
 * @module b311GetProductDetail
 * @param {Object} params 取引先削除条件（品目コード）
 * @returns {Object} - 商品（SKU）詳細データ
 */
export function b311GetProductDetail(params) {
  return request({
    url: "/product",
    method: "get",
    params: params,
  });
}

/**
 * B321/ B322 商品（SKU）登録/更新
 * @module b32xRegistProductDetail
 * @param {String} mode 処理モード（ADD:新規、UPD:更新）
 * @param {Object} data 商品（SKU）詳細情報
 * @returns {*} - なし
 */
export function b32xRegistProductDetail(mode, data) {
  let method = "post";
  if (mode === "ADD") {
    method = "put";
  }
  return request({
    url: "/product",
    method: method,
    data,
  });
}

