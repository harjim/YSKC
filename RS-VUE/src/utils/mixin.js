/*
 * @Author: ldx
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2021-07-05 16:26:54
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\utils\mixin.js
 */
// import Vue from 'vue'
import { deviceEnquire, DEVICE_TYPE } from '@/utils/device'
import { mapState } from 'vuex'

// const mixinsComputed = Vue.config.optionMergeStrategies.computed
// const mixinsMethods = Vue.config.optionMergeStrategies.methods

const mixin = {
  computed: {
    ...mapState({
      layoutMode: state => state.app.layout,
      navTheme: state => state.app.theme,
      primaryColor: state => state.app.color,
      colorWeak: state => state.app.weak,
      fixedHeader: state => state.app.fixedHeader,
      fixSiderbar: state => state.app.fixSiderbar,
      fixSidebar: state => state.app.fixSiderbar,
      contentWidth: state => state.app.contentWidth,
      autoHideHeader: state => state.app.autoHideHeader,
      sidebarOpened: state => state.app.sidebar,
      multiTab: state => state.app.multiTab
    })
  },
  methods: {
    isTopMenu () {
      return this.layoutMode === 'topmenu'
    },
    isSideMenu () {
      return !this.isTopMenu()
    }
  }
}

const mixinDevice = {
  computed: {
    ...mapState({ device: state => state.app.device })
  },
  methods: {
    isMobile () {
      return this.device === DEVICE_TYPE.MOBILE
    },
    isDesktop () {
      return this.device === DEVICE_TYPE.DESKTOP
    },
    isMinDesktop () {
      return this.device === DEVICE_TYPE.MINDESKTOP
    },
    isTablet () {
      return this.device === DEVICE_TYPE.TABLET
    }
  }
}

const AppDeviceEnquire = {
  mounted () {
    const { $store } = this
    deviceEnquire(deviceType => {
      switch (deviceType) {
        case DEVICE_TYPE.DESKTOP:
          $store.commit('TOGGLE_DEVICE', 'desktop')
          $store.dispatch('setSidebar', true)
          break
        case DEVICE_TYPE.TABLET:
          $store.commit('TOGGLE_DEVICE', 'tablet')
          $store.dispatch('setSidebar', false)
          break
        case DEVICE_TYPE.MINDESKTOP:
          $store.commit('TOGGLE_DEVICE', 'minDesktop')
          $store.dispatch('setSidebar', false)
          break
        case DEVICE_TYPE.MOBILE:
        default:
          $store.commit('TOGGLE_DEVICE', 'mobile')
          $store.dispatch('setSidebar', true)
          break
      }
    })
  }
}

const mixinLoadTitleObject = {
  methods: {
    /**
     * @function 获取当前表的表头信息
     * @param beforeCol {String} 自定义列插入位置，在参数列前
     * @param fieldColArr {[{}]} 自定义列
     * @param defaultTitleField {} 默认列，包含 tableId、 fieldTitleObject
     * @param defaultTitleConfig {[{}]} 默认列格式信息
     * @return {{}} 返回配置信息 sampleData: [{}] 列格式信息 ; tableField: {Object} fieldTitleObject 列数组、 hasCol 是否存在列, tableId 对应表ID
     *
     */
    loadTitleObject (beforeCol, fieldColArr, defaultTitleField, defaultTitleConfig) {
      let sampleData = this.$deepClone(defaultTitleConfig)
      const tableField = this.$deepClone(defaultTitleField)
      var titleObj = {}
      var fieldObj = {}
      const tempObj = sampleData[0]
      const fieldTitleObject = tableField.fieldTitleObject
      for (const key in fieldTitleObject) {
        if (key === beforeCol) {
          this.insertCols(titleObj, fieldObj, fieldColArr)
        }
        fieldObj[key] = fieldTitleObject[key]
        titleObj[key] = tempObj[key]
      }
      sampleData = [titleObj]
      tableField.hasCol = fieldColArr.length > 0
      tableField.fieldTitleObject = fieldObj
      return { sampleData, tableField }
    },
    insertCols (titleObj, fieldObj, arr) {
      for (let i = 0; i < arr.length; i++) {
        titleObj[arr[i].name] = 'xxx'
        fieldObj[arr[i].name] = { title: arr[i].alias, defaultTitle: arr[i].alias, importField: true }
      }
    }
  }
}

export { mixin, AppDeviceEnquire, mixinDevice, mixinLoadTitleObject }
