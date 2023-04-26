import request from "@/utils/request";

/**
 * B101 受注一覧取得
 * @module b101GetOrderList
 * @param {Object} params 抽出条件オブジェクト
 * @returns {Object} - 受注一覧データ
 */
export function b101GetOrderList(params) {
  console.log(`b101GetOrderList:${JSON.stringify(params)}`);
  return request({
    url: "/order/list",
    method: "get",
    params: params,
  });
}

/**
 * B102 受注データ取込
 * @module b102UploadOrderFile
 * @param {Object} formData 受注データアップロードフォーム
 * @returns {Promise} - エラーCSVファイル（Promiseオブジェクト）
 */
export function b102UploadOrderFile(data) {
  return request({
    url: "/order/upload",
    method: "post",
    data,
    timeout: 600000,
    headers: {
      "content-type": "multipart/form-data",
    },
    // headers: formData.getHeaders(),
    responseType: "blob",
  });
}

/**
 * B111 受注詳細取得
 * @module b111GetOrderDetail
 * @param {Object} params 受注詳細取得条件（受注番号）
 * @returns {Promise} - 受注詳細データ
 */
export function b111GetOrderDetail(params) {
  return request({
    url: "/order/info",
    method: "get",
    params: params,
  });
}

/**
 * B112 受注確認済更新
 * @module b112ConfirmedOrder
 * @param {Object} data 受注詳細取得条件（受注番号）
 * @returns {*} - なし
 */
export function b112ConfirmedOrder(data) {
  return request({
    url: "/order/confirmed",
    method: "post",
    data,
  });
}

/**
 * B113 受注取消
 * @module b113CancelOrder
 * @param {Object} data 受注詳細取得条件（受注番号）
 * @returns {*} - なし
 */
export function b113CancelOrder(data) {
  return request({
    url: "/order/cancel",
    method: "post",
    data,
  });
}

/**
 * B114 受注データ削除
 * @module b114DeleteOrder
 * @param {Object} data 取消する受注データのキー情報（受注番号）
 * @returns {*} - なし
 */
export function b114DeleteOrder(data) {
  return request({
    url: "/order/delete",
    method: "post",
    data,
  });
}

/**
 * B115 受注明細取消
 * @module b115CancelOrderLine
 * @param {Object} data 取消する受注明細データのキー情報
 * @returns {*} - なし
 */
export function b115CancelOrderLine(data) {
  return request({
    url: "/order/detail/cancel",
    method: "post",
    data,
  });
}
