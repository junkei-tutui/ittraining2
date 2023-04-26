const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  // @training 未使用のためコメント化
  // visitedViews: state => state.tagsView.visitedViews,
  // cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  id: state => state.user.id,
  name: state => state.user.name,
  // @training 未使用のためコメント化
  // introduction: state => state.user.introduction,
  roles: state => state.user.roles,
  // @training 未使用のためコメント化
  // permission_routes: state => state.permission.routes,
  errorLogs: state => state.errorLog.logs,
  // @training 画面間パラメータ保持の追加
  parameter: state => state.viewSession.parameter,
  // @training 画面保持情報の管理追加
  viewStatus: state => state.viewSession.viewStatus,
};
export default getters;
