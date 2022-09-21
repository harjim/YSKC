export default {
  namespaced: true,
  state: {
    payStage: {
      0: '预付款',
      1: '中期款',
      2: '验收款',
      3: '质保金'
    },
    payType: {
      0: '银行转账',
      1: '承兑汇票',
      2: '现金'
    },
    address: []
  },
  getters: {
    getPayStageArr(state) {
      return Object.keys(state.payStage).map(k => ({ value: k, label: state.payStage[k] }))
    },
    getPayTypeArr(state) {
      return Object.keys(state.payType).map(k => ({ value: k, label: state.payType[k] }))
    }
  },
  mutations: {
    SET_ADDRESS: (state, arr) => {
      state.address = arr
    }
  }
}
