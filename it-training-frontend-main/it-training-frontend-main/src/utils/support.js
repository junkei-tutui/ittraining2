import store from "@/store";
import { Message } from "element-ui";

/**
 * 保存や変更の時の正常終了メッセージ
 *
 * @param {array} meg 出力メッセージ
 * @returns {null} なし
 */
export function messageSuccess(meg) {
  Message({
    message: meg,
    type: "success",
    customClass: "top-toast-message-box",
    duration: 5 * 1000,
  });
}

/**
 * 保存や変更の時の警告メッセージ
 *
 * @param {string} meg 出力メッセージ
 * @returns {null} なし
 */
// バリデーションエラー等の警告時のメッセージ
export function messageWarning(meg) {
  Message({
    message: meg,
    type: "warning",
    customClass: "top-toast-message-box",
    duration: 5 * 1000,
  });
}

/**
 *  エラーの時のメッセージ
 *
 * @param {string} meg 出力メッセージ
 * @returns {null} なし
 */
export function messageError(meg) {
  Message({
    message: meg,
    type: "error",
    customClass: "top-toast-message-box",
    duration: 5 * 1000,
  });
}

// 画面転送パラメータの共通化
export function setParameter(keyId, data) {
  // const parameterData = { keyId: keyId, data }
  // store.dispatch('user/setParameter', parameterData)
  const parameterData = store.getters.parameter;
  parameterData[keyId] = data;
  store.dispatch("viewSession/setParameter", parameterData);
}

// 画面転送パラメータの共通化
export function getParameter(keyId) {
  if (store.getters.parameter[keyId]) {
    return store.getters.parameter[keyId];
  } else {
    return null;
  }
}

// 画面保持情報登録
export function setViewStatus(keyId, data) {
  const viewStatus = store.getters.viewStatus;
  // console.log("keyId:", keyId);
  // console.log("viewStatus:", JSON.stringify(viewStatus));
  viewStatus[keyId] = data;
  store.dispatch("viewSession/setViewStatus", viewStatus);
}

// 画面保持情報取得
export function getViewStatus(keyId) {
  if (store.getters.viewStatus[keyId]) {
    return store.getters.viewStatus[keyId];
  }
  return undefined;
}
