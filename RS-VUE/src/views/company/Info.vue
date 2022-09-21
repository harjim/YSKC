<template>
  <a-card style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}">
    <a-tabs v-model="actvieKey" @change="callback" id="tabs">
      <a-tab-pane :tab="`基本信息`" key="1" v-if="$auth('company:info:base')">
        <info-basic ref="baseTab" @ok="saveCapitalUnit"></info-basic>
      </a-tab-pane>
      <a-tab-pane :tab="`股权架构`" key="2" v-if="$auth('company:info:equity')">
        <info-ownership ref="ownershipTab" :capitalContributionTitle="capitalContributionTitle"></info-ownership>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
import InfoBasic from './InfoBasic'
import InfoOwnership from './InfoOwnership'

export default {
  components: {
    InfoBasic,
    InfoOwnership
  },
  data () {
    return {
      title: '',
      id: 0,
      projectData: {},
      visible: false,
      actvieKey: undefined,
      capitalContributionTitle: '出资额(万人民币)'
    }
  },
  created () {
    if (this.$auth('company:info:base')) {
      this.actvieKey = '1'
      return
    }
    if (this.$auth('company:info:equity')) {
      this.actvieKey = '2'
    }
  },
  methods: {
    callback (activeKey) { // 切换tab调用
      this.actvieKey = activeKey
    },
    saveCapitalUnit (capitalUnit) {
      this.capitalContributionTitle = capitalUnit != null ? '出资额(万' + capitalUnit + ')' : '出资额(万人民币)'
    }
  }
}
</script>

<style lang="less" scoped>
//parentHeight
@FH: 100%;
#tabs /deep/ .ant-tabs-content {
  height: @FH;
}
#tabs /deep/ .ant-tabs-tabpane-active {
  height: calc(@FH - 60px);
  overflow: auto;
}
#tabs {
  height: @FH;
}
</style>
