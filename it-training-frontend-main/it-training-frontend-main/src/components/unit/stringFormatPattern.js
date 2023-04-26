/**
 * 文字チェック用正規表現パターン
 */
const stringFormatPattern = {
  // パスワード用
  "password": /^[a-zA-Z0-9!-/:-@¥[-`{-~]*$/,
  // without-mark：全半角（半角記号NG）
  "without-mark": /^[^"'()*/?[\]^`{|}~]{1,}$/g,
  // full-width：全角のみ
  "full-width": /^[^\x20-\x7e]*$/,
  // full-width-kana：全角カナのみ
  "full-width-kana": /^[ァ-ンヴー]*$/,
  // full-width-number：全角数字
  "full-width-number": /^[０-９－]+$/,
  // half-width：半角英数字＋記号＋半角ｶﾅ
  "half-width": /^[^"'*[\]^`{\}~]{1,}$/,
  // half-width-number：半角数字（カンマ、小数点可）
  "half-width-number": /^[0-9,.]+$/,
  // half-width-integer：半角数字（カンマのみ可）
  "half-width-integer": /^[0-9,]+$/,
  // half-width-alphabet：半角英字のみ
  "half-width-alphabet": /^[a-zA-Z]*$/,
  // half-width-alphabet-number：半角英数字（半角ｶﾅ、記号NG）
  "half-width-alphabet-number": /^[0-9a-zA-Z]*$/,
  // half-width-without-kana：半角英数字＋記号（半角ｶﾅNG）
  "half-width-without-kana": /[^ｦ-ﾟ ]*/,
  // half-width-kana：半角ｶﾅのみ
  "half-width-kana": /^[ｦ-ﾟ ]*$/,
};
export default stringFormatPattern;
