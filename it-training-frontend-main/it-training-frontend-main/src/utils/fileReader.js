export default blob => {
  const fileReader = new FileReader();
  return new Promise((resolve, reject) => {
    fileReader.onerror = () => {
      fileReader.abort();
      reject();
    };

    fileReader.onload = () => {
      resolve(fileReader.result);
    };

    fileReader.readAsText(blob);
  });
};

/**
 * 保存や変更の時の正常終了メッセージ
 *
 * @param {Object} response レスポンスオブジェクト
 * @returns {String} ファイル名
 */
export function getFileNameFromResponseHeader(response) {
  // ヘッダーより
  const contentDisposition = response.headers["content-disposition"];
  // ファイル名のみを抜き出す。
  let fileName = contentDisposition.substring(
    contentDisposition.indexOf("''") + 2,
    contentDisposition.length,
  );
  // デコードするとスペースが"+"になるのでスペースへ置換
  fileName = decodeURI(fileName).replace(/\+/g, " ");
  return fileName;
}
