import request from "@/utils/request";

/**
 * B401 在庫取込結果一覧取得
 * @module b401GetStockList
 * @returns {Object} - 在庫取込結果一覧データ
 */
export function b401GetUploadedStockList() {
  return request({
    url: "/stock/list",
    method: "get",
  });
}

/**
 * B402 在庫データ取込
 * @module b402UploadStockFile
 * @param {Object} data 在庫データアップロードフォーム
 * @returns {Promise} - エラーCSVファイル（Promiseオブジェクト）
 */
export function b402UploadStockFile(data) {
  return request({
    url: "/stock/upload",
    method: "post",
    responseType: "blob",
    data,
    timeout: 600000,
    headers: {
      "content-type": "multipart/form-data",
    },
  });
}

// 未使用の為、コメント化
// /**
//  * B403 在庫データ出力
//  * @module b403DownloadStockFile
//  * @param {Object} params 在庫ファイル取得条件（在庫取込管理ID）
//  * @returns {Promise} - 在庫ファイルCSVファイル（Promiseオブジェクト）
//  */
// export function b403DownloadStockFile(params) {
//   return request({
//     url: "/stock/upstock/download",
//     method: "get",
//     responseType: "blob",
//     params: params,
//     timeout: 600000,
//   });
// }

/**
 * B404 引当後在庫ファイル取得
 * @module b404DownloadStockResultFile
 * @param {Object} params 引当後在庫ファイル取得条件（在庫取込管理ID）
 * @returns {Promise} - 引当後在庫ファイルCSVファイル（Promiseオブジェクト）
 */
export function b404DownloadStockResultFile(params) {
  return request({
    url: "/stock/result/download",
    method: "get",
    responseType: "blob",
    params: params,
    timeout: 600000,
  });
}

/**
 * B405 引当不可リストファイル取得
 * @module b405DownloadNonStockOrderFile
 * @param {Object} params 引当不可リストファイル取得条件（在庫取込管理ID）
 * @returns {Promise} - 引当不可リストファイルCSVファイル（Promiseオブジェクト）
 */
export function b405DownloadNonStockOrderFile(params) {
  return request({
    url: "/stock/nostockorder/download",
    method: "get",
    responseType: "blob",
    params: params,
    timeout: 600000,
  });
}
