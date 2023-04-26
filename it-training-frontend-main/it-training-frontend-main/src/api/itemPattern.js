import request from "@/utils/request";

/**
 * B391 カラーコード一覧取得
 * @module b391GetColorList
 * @returns {Object} - カラーコード一覧データ
 */
export function b391GetColorList() {
  return request({
    url: "/item/pattern/color/list",
    method: "get",
  });
}

/**
 * B392 サイズコード一覧取得
 * @module b392GetSizeList
 * @returns {Object} - サイズコード一覧データ
 */
export function b392GetSizeList() {
  return request({
    url: "/item/pattern/size/list",
    method: "get",
  });
}
