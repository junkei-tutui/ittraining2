import Cookies from "js-cookie";

// プロジェクト毎にセッションを保持するcookie名を設定する。
// const TokenKey = "Admin-Token";
const TokenKey = "Training-Token";

export function getToken() {
  return Cookies.get(TokenKey);
}

export function setToken(token) {
  return Cookies.set(TokenKey, token);
}

export function removeToken() {
  return Cookies.remove(TokenKey);
}
