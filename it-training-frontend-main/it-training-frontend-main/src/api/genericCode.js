import request from "@/utils/request";

/**
 * B001 汎用コード一覧取得
 * @module b001GetGenericCodeList
 * @param {Object} params 抽出条件オブジェクト
 * @returns {Object} - 汎用コード一覧データ
 */
export function b001GetGenericCodeList(params) {
  console.log(`b001GetGenericCodeList:${JSON.stringify(params)}`);
  return request({
    url: "/genericcode/list",
    method: "get",
    params: params,
  });
}
