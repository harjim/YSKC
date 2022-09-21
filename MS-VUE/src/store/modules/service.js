import { axios } from '@/utils/request'
import { getStatusName, isEditStatus, statusColor } from '@/utils/processDoc/auditStatus'

const service = {
  namespaced: true,
  state: {
    currentOrder: {},
    editing: false,
    tableEdit: false
  },

  getters: {
    getStatusColor (state) {
      return statusColor[state.currentOrder.status]
    },
    getStatusTitle (state) {
      return getStatusName(state.currentOrder.status)
    },
    // 可以:true ;不可:false
    getIsEditStatus (state) {
      return isEditStatus(state.currentOrder.status)
    }
  },

  mutations: {
    SET_DETAIL: (state, currentOrder) => {
      const temp = JSON.parse(JSON.stringify(currentOrder))
      delete temp.createTime
      delete temp.lastUpdateTime
      state.currentOrder = temp
    },
    SET_TABLE_EDIT: (state, flag) => {
      state.tableEdit = flag
    },
    CHANGE_USER: (state, { key, data }) => {
      console.log(data)
      state.currentOrder[key] = data
    },
    CHANGE_BOSS: (state, { key, data }) => {
      state.currentOrder[`${key}DirectorId`] = data[`${key}DirectorId`]
      state.currentOrder[`${key}DirectorName`] = data[`${key}DirectorName`]
      state.currentOrder[`${key}ManagerId`] = data[`${key}ManagerId`]
      state.currentOrder[`${key}ManagerName`] = data[`${key}ManagerName`]
    },
    CHANGE_DATE: (state, dates) => {
      state.currentOrder.begin = dates[0]
      state.currentOrder.end = dates[1]
      state.currentOrder.date = `${dates[0]}-${dates[1]}`
    },
    CHANGE_REMARK: (state, v) => {
      state.currentOrder.remark = v
    },
    ADD_CUSTOMER_ITEM: (state, record) => {
      state.currentOrder.customerList.unshift(record)
    },
    DELETE_CUSTOMER_ITEM: (state, index) => {
      state.currentOrder.customerList.splice(index, 1)
    },
    CLOSE: (state, editing) => {
      state.editing = editing
      state.currentOrder = {}
      state.tableEdit = false
    },
    CHANGE_PERMISSION: (state, flag) => {
      state.currentOrder.hasPermission = flag
    }
  },

  actions: {
    changeBoss (ctx, { key, userIds, types }) {
      const params = {
        userIds,
        userName: '',
        type: types
      }
      axios.get('/serviceApply/getMemberList', { params }).then(({ success, data, errorMessage }) => {
        if (success) {
          ctx.commit('CHANGE_BOSS', { key, data })
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    closeDrawer (ctx, { edit }) {
      ctx.commit('CLOSE', edit)
    },
    addCustomerItem (ctx, { record }) {
      ctx.commit('SET_TABLE_EDIT', true)
      ctx.commit('ADD_CUSTOMER_ITEM', record)
    },
    delCustomerItem (ctx, { index }) {
      ctx.commit('DELETE_CUSTOMER_ITEM', index)
    }
  }
}

export default service
