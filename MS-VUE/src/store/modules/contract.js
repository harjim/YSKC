import { isEditStatus } from '@/utils/processDoc/auditStatus'

const contract = {
  namespaced: true,

  state: {
    contractProcessType: [
      { value: 0, label: '业务员' },
      { value: 1, label: '分公司总经理' },
      { value: 2, label: '合同审批' },
      { value: 3, label: '用印审批' }
    ],
    sealType: [
      { label: '公章', value: 0 },
      { label: '法人章', value: 1 },
      { label: '财务章', value: 2 },
      { label: '合同章', value: 3 }
    ],
    projectListValidRules: {
      productId: [{ required: true, message: '项目类型必须选择' }],
      ratio: [{ required: true, message: '收费比例必须填写' }],
      years: [{ required: true, message: '年份必须选择' }],
      signCnt: [{ required: true, message: '签约次数必须填写' }]
    },
    product: [],
    customerProductMap: {}
  },

  getters: {
    getIsEditStatus: () => (status) => {
      return isEditStatus(status)
    }
  },

  mutations: {
    CHANGE_PRODUCT: (state, data) => {
      state.product = [...data]
    },
    ADD_CUSTOMER_SIGN_CNT_MIN: (state, { customerId, productId, cnt, first }) => {
      if (!state.customerProductMap[customerId]) state.customerProductMap[customerId] = {}
      if (!state.customerProductMap[customerId][productId]) state.customerProductMap[customerId][productId] = {}
      if (first) state.customerProductMap[customerId][productId].min = cnt
      state.customerProductMap[customerId][productId].cnt = cnt
    },
    RESET_SIGN_CNT: (state) => {
      state.customerProductMap = {}
    }
  },

  actions: {}
}

export default contract
