<!--
 * @Author: ldx
 * @Date: 2021-01-22 14:17:53
 * @LastEditTime: 2021-09-30 14:38:26
 * @LastEditors: lzh
 * @Description: 研发组织架构审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\FrameworkTab.vue
-->
<template>
  <tab-layout>
    <template #up>
      <div style="padding-top:10px; height: 100%; overflow: auto;">
        <a-tabs default-active-key="1" tab-position="left">
          <a-tab-pane key="1" tab="研发架构">
            <div style="overflow: auto;">
              <img v-if="orgPath" :src="`${orgPath}?v=${now}`" alt="研发组织架构"/>
              <a-alert
                v-else
                message="组织架构图片未生成,请到企业端研发组织架构界面生成组织架构图片"
                type="info"
                show-icon
                style="color: red; margin: 15px  0px;"/>
            </div>
          </a-tab-pane>
          <a-tab-pane key="2" tab="技术中心简介">
            <p v-if="desc">{{ desc }}</p>
            <a-empty v-else description="暂无内容"/>
          </a-tab-pane>
        </a-tabs>
      </div>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" title="研发组织架构"></audit-log>
    </template>
  </tab-layout>
</template>
<script>
import TabLayout from './TabLayout'
import AuditLog from './AuditLog'
import { now } from 'lodash'

export default {
  name: 'FrameworkTab',
  components: {
    AuditLog,
    TabLayout
  },
  props: {
    record: {
      type: Object,
      default: () => {
        return {}
      }
    },
    mode: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  mounted () {
    this.logGetParams.moduleId = this.mode.moduleId
    this.logGetParams.companyId = this.record.companyId
    this.logGetParams.year = this.record.year
    this.loadData()
    // this.callBack()
  },
  data () {
    return {
      now: now(),
      orgPath: undefined,
      desc: undefined,
      spinningLog: false,
      logGetParams: {
        companyId: undefined,
        year: undefined,
        moduleId: undefined
      }
    }
  },
  methods: {
    loadData () {
      const year = this.record.year
      const companyId = this.record.companyId
      this.orgPath = undefined
      this.desc = undefined
      return new Promise(resolve => {
        this.$http.get('/projectProgress/getOrgPath', { params: { year, companyId } })
          .then(res => {
            if (res.success) {
              if (res.data && res.data.length > 0) {
                this.orgPath = res.data[0]
                this.desc = res.data[1]
              }
            }
          }).catch(() => {
            this.$message.error(`研发架构加载失败，请联系管理员。`)
          })
      })
    },
    callBack () {
      if (this.$refs.auditLog && this.$refs.auditLog.handleGetAuditLog) {
        this.$refs.auditLog.handleGetAuditLog()
      }
    },
    updataStatus () {
      this.$emit('updataStatus')
    }
  }
}
</script>

<style lang="less" scoped>
* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis > .vxe-body--expanded-cell {
  overflow: auto;
}
</style>
