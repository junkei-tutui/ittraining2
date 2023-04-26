// @training 固有定数値宣言
export default {
  /* -- 汎用コードタイプ -- */
  GenericCodeType: {
    // カテゴリ
    category: "001",
    // 分類
    classification: "002",
    // シーズン
    season: "003",
  },
  /* -- 注文受付ステータス-- */
  OrderStatus: {
    // 未確認
    unconfirmed: "01",
    // 確認済
    confirmed: "02",
    // 引当済
    reserved: "03",
    // 一部引当不可
    somenotreserved: "04",
    // 引当不可
    notreserved: "05",
    // 受注取消
    cancel: "06",
  },
  /* -- 受注明細ステータス-- */
  OrderDetailStatus: {
    // 引当前
    beforereserved: "01",
    // 引当済
    reserved: "02",
    // 引当不可
    notreserved: "03",
    // 取消
    cancel: "04",
  },
};
