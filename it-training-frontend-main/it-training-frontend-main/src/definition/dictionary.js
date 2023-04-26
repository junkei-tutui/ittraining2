// @training 固有定数値宣言
export default {
  /**
   * プルダウン用オブジェクト変換
   * @param {Object} dictionaryData 辞書データ
   * @returns {Array} - プルダウン用オブジェクト配列
   */
  createSelectList: (dictionaryData) => {
    const selectList = [];
    for (const key in dictionaryData) {
      selectList.push({
        code: key,
        value: dictionaryData[key],
      });
    }
    return selectList;
  },
  /* -- 注文受付ステータス-- */
  OrderStatus: {
    "01": "未確認",
    "02": "確認済",
    "03": "引当済",
    "04": "一部引当不可",
    "05": "引当不可",
    "06": "受注取消",
  },
  /* -- 受注明細ステータス-- */
  OrderDetailStatus: {
    "01": "引当前",
    "02": "引当済",
    "03": "引当不可",
    "04": "取消",
  },
};
