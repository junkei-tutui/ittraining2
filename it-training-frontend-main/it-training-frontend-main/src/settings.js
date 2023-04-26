module.exports = {
  /* -- @training 研修用に初期設定する-- */
  /**
   * アプリケーション名を設定
   * @type {String}
   * @description
   */
  title: "注文確認システム",

  /**
   * ※設定画面は未使用
   * @type {boolean} true | false
   * @description Whether show the settings right-panel
   */
  showSettings: false,

  /**
   * ※タグ表示は未使用
   * @type {boolean} true | false
   * @description Whether need tagsView
   */
  tagsView: false,

  /**
   * ※ ヘッダーは固定にする
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: true,

  /**
   * ※ サイドバーのロゴは表示する
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: true,

  /**
   * ※ エラーログは本番運用のみ出力
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: "production",
};
