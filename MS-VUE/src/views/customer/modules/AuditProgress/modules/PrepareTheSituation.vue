<!--
 * @Author: ldx
 * @Date: 2021-03-15 14:06:43
 * @LastEditTime: 2021-05-26 14:09:48
 * @LastEditors: ldx
 * @Description: 项目组编制情况 （停用）
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\PrepareTheSituation.vue
-->
<template>
  <div class="container">
    <div class="title">一、研发组织架构</div>
    <div class="architecture">
      <img v-if="imagePath" :src="imagePath" alt="研发组织架构" >
      <a-alert v-else message="组织架构图片未生成,请到企业端研发组织架构界面生成组织架构图片" type="info" show-icon style="color: red; margin: 15px  0px;"/>
    </div>
    <div class="title">二、项目组研发人员</div>
    <div class="table-wrap">
      <ystable
        ref="table"
        size="small"
        queryUrl="/backupData/getList"
        :params="queryParam"
        border
        resizable
        auto-resize
        highlight-hover-row
        show-overflow
        show-header-overflow
        highlight-current-row
        @loading="loading"
        @completed="({data})=>completed(data)"
        :toolbar="{ refresh: true, zoom: true, custom: true }"
      >
        <vxe-table-column type="seq" title="序号" width="100" fixed="left" remoteSort></vxe-table-column>
        <vxe-table-column
          field="ename"
          title="姓名"
          min-width="120"
          fixed="left"
          header-align="center"
          align="left"
          remoteSort />
        <vxe-table-column
          field="deptName"
          title="部门"
          min-width="150"
          header-align="center"
          align="left" >
        </vxe-table-column>
        <vxe-table-column
          field="role"
          title="项目角色"
          min-width="150"
          header-align="center"
          align="left"
          remoteSort></vxe-table-column>
      </ystable>
    </div>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import { now } from 'lodash'
export default {
  name: 'PrepareTheSituation',
  components: {
    ystable
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    projectInfo: {
      type: Object,
      default: () => { return {} }
    }
  },
  watch: {
    projectId (val) {
      this.queryParam.projectId = val
      this.queryParam.year = this.projectInfo.beginYear
      this.$refs.table.refresh(true)
    }
  },
  mounted () {
    this.queryParam.projectId = this.projectId
    this.queryParam.year = this.projectInfo.beginYear
  },
  filters: {
    formatDate (value) {
      if (!value) return ''
      return moment(value).format('YYYY-MM-DD')
    }
  },
  data () {
    return {
      queryParam: {},
      imagePath: undefined
    }
  },
  methods: {
    loading (spinning) {
      this.$emit('loading', spinning)
    },
    completed (data) {
      if (data.footer) {
        this.imagePath = data.footer + '?time=' + now()
      } else {
        this.imagePath = null
      }
    }
  }
}
</script>

<style lang="less" scoped>
.container{
  height: 100%;
  overflow: auto;
  .title{
    font-size: 18px;
    font-weight: 600;
  }
  .architecture {
    width: 100%;
    overflow: auto;
  }
}
</style>
