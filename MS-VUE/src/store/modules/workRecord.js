import { getStatusName, isEditStatus, statusColor } from '@/utils/processDoc/auditStatus'

const workRecord = {
  namespaced: true,
  state: {
    recordOrder: {},
    editing: false
  },

  getters: {
    getStatusColor (state) {
      return statusColor[state.recordOrder.status]
    },
    getStatusTitle (state) {
      return getStatusName(state.recordOrder.status)
    },
    // 可以:true ;不可:false
    getIsEditStatus (state) {
      return isEditStatus(state.recordOrder.status)
    }
  },

  mutations: {
    SET_ORDER: (state, recordOrder) => {
      const temp = JSON.parse(JSON.stringify(recordOrder))
      delete temp.lastUpdateTime
      state.recordOrder = temp
      state.editing = false
    },
    SET_EDITING: (state, editing) => {
      state.editing = editing
    },
    ADD_ITEM: (state, record) => {
      state.recordOrder.list.unshift(record)
    },
    DEL_ITEM: (state, index) => {
      if (index === undefined) {
        state.recordOrder.list.shift()
      } else {
        state.recordOrder.list.splice(index, 1)
      }
    },
    CHANGE_PERMISSION: (state, flag) => {
      state.recordOrder.hasPermission = flag
    },
    CHANGE_OWNER: (state, value) => {
      state.recordOrder.deptId = value.deptId
      state.recordOrder.deptName = value.deptName
      state.recordOrder.ownerId = value.id
      state.recordOrder.ownerName = value.realName
    },
    CHANGE_DATE: (state, dateStr) => {
      state.recordOrder.list[0].begin = dateStr[0]
      state.recordOrder.list[0].end = dateStr[1]
      state.recordOrder.list[0].date = `${dateStr[0]} - ${dateStr[1]}`
    },
    CHANGE_SERVICE: (state, row) => {
      state.recordOrder.serviceNo = row.serviceNo
    }
  },

  actions: {}
}

export default workRecord
