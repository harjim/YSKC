<!--
 * @Author: ldx
 * @Date: 2021-03-18 09:01:33
 * @LastEditTime: 2021-10-20 09:18:59
 * @LastEditors: zdf
 * @Description: 备份证明细单
 * @FilePath: \RS-VUE\src\views\tech\Beian\Index.vue
-->
<template>
  <div class="container">
    <div>
      <a-button type="primary" size="small" @click="onReturn">返回</a-button>
      <span style="margin-left: 8px;">
        【{{ $attrs['record'].productName }} - {{ baseInfo.pname }}】
      </span>
    </div>
    <a-tabs default-active-key="1" id="tabs-container" @change="callback">
      <a-tab-pane key="1" tab="基本信息">
        <base-info v-bind="$attrs" ref="baseInfo" @getBaseInfo="getBaseInfo"></base-info>
      </a-tab-pane>
      <a-tab-pane key="2" tab="备案清单" :forceRender="false" v-if="$auth('tech:beian:beianList:search')" >
        <equipment-list v-bind="$attrs" ref="equipmentList"></equipment-list>
      </a-tab-pane>
      <a-tab-pane key="3" tab="投资清单" :forceRender="false" v-if="$auth('tech:beian:investments:search')">
        <invest-detail v-bind="$attrs" ref="investDetail"></invest-detail>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import BaseInfo from './BaseInfo.vue'
import EquipmentList from './EquipmentList'
import InvestDetail from './InvestDetail'
export default {
  name: 'Index',
  components: {
    BaseInfo,
    EquipmentList,
    InvestDetail
  },
  data () {
    return {
      baseInfo: {}
    }
  },
  methods: {
    callback (activeKey) {
      if (activeKey * 1 === 1 && this.$refs.baseInfo && this.$refs.baseInfo.refresh) {
        this.$refs.baseInfo.refresh()
      }
      if (activeKey * 1 === 2 && this.$refs.equipmentList && this.$refs.equipmentList.search) {
        this.$refs.equipmentList.search(true)
      }
      if (activeKey * 1 === 3 && this.$refs.investDetail && this.$refs.investDetail.refresh) {
        this.$refs.investDetail.refresh()
      }
    },
    getBaseInfo (baseInfo) {
      this.baseInfo = baseInfo
      this.$emit('getBaseInfo', baseInfo)
    },
    onReturn () {
      this.$emit('showDetail')
    }
  }
}
</script>
<style lang="less" scoped>
.container {
  height: inherit;
  #tabs-container {
    height: calc(100% - 24px);
    /deep/ .ant-tabs-content {
      height: calc(100% - 61px);
      & > .ant-tabs-tabpane {
        height: 100%;
      }
      & > .ant-tabs-tabpane-active {
        height: 100%;
      }
    }
  }
}

</style>
