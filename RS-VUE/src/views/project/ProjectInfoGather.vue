<!--
 * @Author: ldx
 * @Date: 2021-03-05 14:31:03
 * @LastEditTime: 2022-07-22 08:23:25
 * @LastEditors: zdf
 * @Description: 项目信息汇总
 * @FilePath: \RS-VUE\src\views\project\ProjectInfoGather.vue
-->
<template>
  <a-card :bodyStyle="{height: '100%', overflow: 'auto'}" class="contentPage">
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <vxe-grid
        id="ProjectInfoGather"
        ref="table"
        :columns="columns"
        :data="tableDatas"
        :toolbar="{ refresh: { query: getData }, zoom: true, custom: true }"
        :tree-config="{lazy: true, children: 'children', hasChild: 'hasChild', loadMethod: loadChildrenMethod}"
        auto-resize
        border
        highlight-current-row
        highlight-hover-row
        resizable
        show-header-overflow
        show-overflow
        size="small"
      >
        <template #buttons>
        </template>
        <template v-slot:headerRender="{column}">
          <div :title="column.own.pname">{{ column.title }}</div>
        </template>
        <template v-slot:getContent="{row,column}">
          <a
            v-if="isOnClick(row,row[column.property])"
            title="点击查看详情"
            @click="showModal(row,column.property)">{{ row[column.property] ? row[column.property] : '-'
            }}</a>
          <span v-else>{{ row[column.property] ? row[column.property] : '-'
          }}</span>
        </template>
        <template v-slot:getTotal="{row,column}">
          <a
            v-if="row.parent === 'staffRdHour' || row.parent === 'prodRdHour'"
            title="点击查看详情"
            @click="showModal(row)">{{ row[column.property] ? row[column.property] : '-'
            }}</a>
          <span v-else>{{ row[column.property] ? row[column.property] : '-'
          }}</span>
        </template>
      </vxe-grid>
      <project-data-modal ref="projectDataModal"></project-data-modal>
    </a-spin>
  </a-card>
</template>

<script>
import { LAZY_URLS, projectSummary } from '@/api/project/ProjectInfoGather'
import ProjectDataModal from './modules/ProjectDataModal'
import { mapGetters } from 'vuex'
import moment from 'moment'

const titleKV = {
  budget: '预算(万元)',
  staffRdHour: '研发人员工时(小时)',
  prodRdHour: '仪器设备工时(小时)',
  materialTotal: '研发材料(元)',
  trialTotal: '中间试制(元)',
  repairTotal: '修理费用(元)',
  yieldAmount: '排期表(试制量)',
  summary: '归集总表(万元)',
  budgetCost: '费用决算(万元)',
  materialRaw: '原材料(元)',
  materialAuxiliary: '辅料(元)',
  materialReserve: '工艺装备用料(元)'
}
export default {
  components: { ProjectDataModal },
  name: 'ProjectInfoGather',
  computed: {
    ...mapGetters(['currentYear'])
  },
  watch: {
    currentYear (val) {
      this.getData()
    }
  },
  mounted () {
    this.getData()
  },
  data () {
    return {
      spinning: false,
      tableDatas: [],
      columns: [],
      titleKV,
      // projectKV: {},
      projectMap: {}
      // lazyUrls
    }
  },
  methods: {
    moment,
    /**
     * @description: 判断是否时数值
     * @param {*} value
     * @return {*} boolean true:数值型的，false：非数值型
     */
    myIsNaN (value) {
      return typeof value === 'number' && !isNaN(value)
    },
    /**
     * @description: 懒加载方法
     * @param {*} row
     * @return {*}
     */
    loadChildrenMethod ({ row }) {
      const url = row.url
      return this.$http.get(url, { params: { year: this.currentYear } }).then((res) => {
        if (res.data && res.success) {
          // 【研发材料，中间试制，修理费用】展开
          if (url.indexOf('getMaterialSummary') >= 0) {
            res.data.forEach((item, index) => {
              item['noClick'] = true
              item['children'] = []
              item['hasChild'] = true
              item['rdType'] = item.type === 0 ? 200 : item.type === 1 ? 203 : 206
              if (index === 0) {
                item['url'] = `${LAZY_URLS['materialRaw']}?type=${item.type}`
                item['rdTitle'] = 'materialRaw'
              } else if (index === 1) {
                item['url'] = `${LAZY_URLS['materialAuxiliary']}?type=${item.type}`
                item['rdTitle'] = 'materialAuxiliary'
              } else if (index === 2) {
                item['url'] = `${LAZY_URLS['materialReserve']}?type=${item.type}`
                item['rdTitle'] = 'materialReserve'
              }
            })
            return res.data
          }
          // type: 1:原料详情; 2:辅料详情; 3:备品详情;
          const materialsType = {
            materialRaw: 1,
            materialAuxiliary: 2,
            materialReserve: 3
          }
          const data = res.data
          if (Array.isArray(data)) {
            const map = {}
            data.forEach((item, index) => {
              for (const key in item) {
                if (key === 'month') {
                  item['RD'] = moment(item[key]).format('YYYY年MM月')
                }
              }
              item['parent'] = row.rdTitle
              if (materialsType[row.rdTitle]) {
                item['type'] = materialsType[row.rdTitle]
                item['rdType'] = row.rdType
                item['materials'] = 'materials'
              }
              map[item['RD']] = item
            })
            const result = []
            for (let i = 1; i <= 12; i++) {
              const key = `${this.currentYear}年${i > 9 ? i : '0' + i}月`
              if (map[key]) {
                result.push(map[key])
              } else {
                result.push({ RD: key, total: 0 })
              }
            }
            // console.log('result', result)
            return result
          } else {
            // const kv = {}
            const resultData = []
            this.$getCostTypes().forEach((item) => {
              if (data[item.value]) {
                const newRow = { RD: item.title, parent: row.rdTitle }
                Object.assign(newRow, data[item.value])
                resultData.push(newRow)
              } else {
                resultData.push({ RD: item.title, total: 0 })
              }
            })
            return resultData
          }
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
      })
    },
    /**
     * @description: 打开modal
     * @param {*} row
     * @param {*} projectId
     * @return {*}
     */
    showModal (row, projectId) {
      let title = ''
      if (row.rdTitle) {
        title = `${this.projectMap[projectId].rdTitle} - ${this.titleKV[row.rdTitle]}`
      } else if (!projectId) {
        title = `${this.titleKV[row.parent]}【${moment(row.month).format('YYYY年MM月')}】`
      } else {
        title = `${this.projectMap[projectId].rdTitle} - ${this.titleKV[row.parent]}【${moment(row.month).format('YYYY年MM月')}】`
      }
      this.$refs.projectDataModal.show(title, row, projectId, this.projectMap, this.currentYear)
    },
    /**
     * @description:  加载数据
     * @param {*}
     * @return {*}
     */
    getData () {
      this.spinning = true
      this.projectMap = {}
      projectSummary({ year: this.currentYear }).then((resolve, reject) => {
        if (resolve.data && resolve.success) {
          this.transformData(resolve.data).then((resolve) => {
            this.spinning = false
          })
        } else {
          this.$message.error(resolve.errorMessage)
          this.tableDatas = []
          this.columns = []
          this.projectMap = {}
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.tableDatas = []
        this.columns = []
        this.spinning = false
        this.projectMap = {}
      })
    },
    /**
     * @description:  数据转换
     * @param {*} lists
     * @return {*} null
     */
    transformData (lists) {
      // this.spinning = true
      if (lists && !lists.length) {
        this.spinning = false
        this.tableDatas = []
        this.columns = []
        this.projectMap = {}
        return Promise.resolve(true)
      }
      const rowTitle = ['budget', 'staffRdHour', 'prodRdHour', 'materialTotal', 'trialTotal', 'repairTotal', 'yieldAmount', 'summary', 'budgetCost']
      const columns = [{
        field: 'RD',
        title: 'RD',
        minWidth: 140,
        headerAlign: 'center',
        align: 'left',
        treeNode: true,
        fixed: 'left'
      }]
      const dataObje = {}
      const tableData = []
      lists.forEach((item, index) => {
        if (item.projectId) {
          // this.projectKV[item.projectId] = item.rdTitle
          this.projectMap[item.projectId] = item
          const column = {
            field: item.projectId,
            title: item.rdTitle,
            minWidth: 120,
            headerAlign: 'center',
            align: 'right',
            slots: { default: 'getContent', header: 'headerRender' },
            pname: item.pname
          }
          columns.push(column)
          dataObje[item.projectId] = {}
        } else {
          dataObje['total'] = {}
        }
        rowTitle.forEach((subItem, subIndex) => {
          if (item.projectId) {
            dataObje[item.projectId][subItem] = item[subItem]
          } else {
            dataObje['total'][subItem] = item[subItem]
          }
        })
      })
      rowTitle.forEach((item) => {
        const row = { 'children': [] }
        for (const key in dataObje) {
          row[key] = dataObje[key][item]
          row['RD'] = titleKV[item]
          row['rdTitle'] = item
          row['url'] = LAZY_URLS[item]
          if (titleKV[item]) {
            row['hasChild'] = true
          }
          // 预算和决算没有懒加载
          if (item === 'budget' || item === 'budgetCost') {
            row['hasChild'] = false
          }
        }
        tableData.push(row)
      })
      columns.push({
        field: 'total',
        title: '合计',
        minWidth: 120,
        headerAlign: 'center',
        align: 'right',
        fixed: 'right',
        slots: { default: 'getTotal' }
      })
      this.columns = columns
      this.tableDatas = tableData
      return Promise.resolve(true)
      // this.spinning = false
    },
    /**
     * @description: 判断是否可以点击
     * @param {*} row
     * @param {*} currentVal
     * @return {*}
     */
    isOnClick (row, currentVal) {
      if (!currentVal || currentVal === '-') {
        return false
      }
      if (row.noClick) {
        return false
      }
      const rowTitle = ['staffRdHour', 'prodRdHour', 'materialTotal', 'trialTotal', 'repairTotal', 'yieldAmount', 'summary']
      const subRowTitle = ['summary']
      if (row.parent && subRowTitle.includes(row.parent)) {
        return false
      }
      if (row.rdTitle && rowTitle.includes(row.rdTitle)) {
        return false
      }
      return true
    }
  }
}
</script>

<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
}

#spin {
  height: 100%;
  width: 100%;

  & /deep/ .ant-spin-nested-loading {
    height: 100%;
  }

  & /deep/ .ant-spin-container {
    height: 100%;
  }
}
</style>
