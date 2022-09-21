const getters = {
  maxOrder: state => state.maxOrder,
  totalMax: state => state.totalMax,
  currentYear: state => state.currentYear,
  device: state => state.app.device,
  theme: state => state.app.theme,
  color: state => state.app.color,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  nickname: state => state.user.info.realName,
  userInfo: state => state.user.info,
  addRouters: state => state.permission.addRouters,
  multiTab: state => state.app.multiTab,
  companyId: state => state.user.companyId,
  auditStatus: state => state.auditStatus
}

export default getters
