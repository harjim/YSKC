<!--
 * @Author: ldx
 * @Date: 2021-03-17 17:33:51
 * @LastEditTime: 2021-10-20 09:20:02
 * @LastEditors: zdf
 * @Description: 备案管理
 * @FilePath: \RS-VUE\src\views\tech\Beian.vue
-->
<template>
  <a-card class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto'}">
    <template v-if="$auth('tech:beian:basicInfo:view')">
      <a-spin id="spin" :spinning="spinning" tip="加载中...">
        <div class="table-container" v-if="!isShowDetail">
          <div>
            <a-form layout="inline">
              <a-form-item label="申报项目">
                <a-input v-model="queryParams.name"></a-input>
              </a-form-item>
              <a-form-item label="项目名称">
                <a-input v-model="queryParams.name"></a-input>
              </a-form-item>
              <a-form-item label="年份">
                <year-select v-model="queryParams.year" style="width:164px;"/>
              </a-form-item>
              <a-form-item>
                <a-button type="primary" @click="query">查询</a-button>
              </a-form-item>
            </a-form>
          </div>
          <ystable
            ref="table"
            :columns="columns"
            queryUrl="/beian/getList"
            :params="queryParams"
            border
            resizable
            auto-resize
            highlight-hover-row
            show-overflow="title"
            show-header-overflow
            highlight-current-row>
            <template #productName="{row,column}">
              <a @click="showDetail(row)">{{ row[column.property] }}</a>
            </template>
            <template #beianFile="{row}">
              <template v-if="row.scanFilePath">
                <a @click="download(row.scanFilePath)">下载</a>
              </template>
            </template>
          </ystable>
        </div>
        <ystabs v-if="isShowDetail" @showDetail="showDetail" :record="record" @getBaseInfo="getBaseInfo"></ystabs>
      </a-spin>
    </template>
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import { ystable, YearSelect } from '@/components'
import ystabs from './Beian/Index.vue'
import { get } from 'lodash'

const economyTypes = ['有限责任公司', '股份有限公司']
function getBeginAndEnd ({ row }) {
  var result = '~'
  if (row.beginDate) {
    result = row.beginDate + result
  }
  if (row.endDate) {
    result += row.endDate
  }
  return result
}

export default {
  name: 'Beian',
  components: {
    ystable,
    ystabs,
    YearSelect
  },
  data () {
    return {
      record: {},
      spinning: false,
      isShowDetail: false,
      queryParams: {},
      columns: [
        { type: 'seq', title: '序号', width: 60, headerAlign: 'center', align: 'center', fixed: 'left' },
        { title: '申报项目', field: 'productName', width: 220, headerAlign: 'center', align: 'left', fixed: 'left', slots: { default: 'productName' } },
        { title: '年份', field: 'year', width: 80, headerAlign: 'center', align: 'center' },
        { title: '项目名称', field: 'pname', width: 220, headerAlign: 'center', align: 'left' },
        { title: '备案单位', field: 'applyName', width: 220, headerAlign: 'center', align: 'left', slots: { default: ({ row }) => row.applyName ? row.applyName : this.userInfo().companyName } },
        { title: '项目建设地点', field: 'constructionPlace', width: 200, headerAlign: 'center', align: 'left', formatter: ({ cellValue }) => JSON.parse(cellValue || '[]').join(', ') },
        { title: '经济类型', field: 'economyType', width: 110, headerAlign: 'center', align: 'left', slots: { default: ({ row }) => economyTypes[row.economyType] } },
        { title: '项目总投资', field: 'totalAmount', width: 100, headerAlign: 'center', align: 'right' },
        { title: '项目起止年限', field: 'beginAndEnd', width: 200, headerAlign: 'center', align: 'center', slots: { default: getBeginAndEnd } },
        { title: '备案编号', field: 'beianNo', width: 140, headerAlign: 'center', align: 'center' },
        { title: '备案证', field: 'filePath', width: 90, headerAlign: 'center', align: 'center', slots: { default: 'beianFile' } },
        { title: '主要内容', field: 'content', headerAlign: 'center', align: 'left', minWidth: 260 }
      ]
    }
  },
  methods: {
    getBaseInfo (record) {
      this.record = Object.assign(this.record, record)
    },
    showDetail (record) {
      this.record = record
      this.$store.commit('common/SET_ADDRESS', JSON.parse(get(record, 'constructionPlace', '[]')))
      this.isShowDetail = !this.isShowDetail
    },
    ...mapGetters(['userInfo']),
    query () {
      this.$refs.table.refresh()
    },
    download (filePath) {
      const arr = filePath.split('/')
      const fileName = arr[arr.length - 1]
      this.$exportData('/beian/download', { filePath }, fileName.length > 13 ? fileName.substr(13) : fileName, this.$message)
    }
  }
}
</script>

<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
  #spin {
    height: 100%;
    width: 100%;
    & /deep/ .ant-spin-nested-loading {
      height: 100%;
    }
    & /deep/ .ant-spin-container {
      height:  100%;
    }
  }
}
</style>
