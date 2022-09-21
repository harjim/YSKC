<!--
 * @Author: ldx
 * @Date: 2021-06-22 11:13:45
 * @LastEditTime: 2021-10-09 08:10:50
 * @LastEditors: zdf
 * @Description: 项目明细
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\ProjectInfo.vue
-->
<template>
  <a-descriptions :title="record.rdTitle + '-' + record.pname" size="small">
    <a-descriptions-item label="RD">
      {{ record.rdTitle ? record.rdTitle : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="内部编号">
      {{ record.rdNumber ? record.rdNumber : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="部门">
      <span v-if="record.fullname && record.fullname !== null">
        {{ record.fullname }}
      </span>
      <a-tooltip
        v-else
        :align="{offset: [0, 5]}"
        :auto-adjust-overflow="true"
      >
        <template slot="title">
          {{ `${record.deptName || '' }` }}{{ record.deptName && record.workshop ? '/' : '' }}{{
            `${record.workshop || ''}` }}{{ (record.deptName || record.workshop) && record.productLine ? '/' : '' }}{{
            `${record.productLine || ''}` }}{{ (record.deptName || record.workshop || record.productLine) && record.processSection ? '/' : '' }}{{
            `${record.processSection || ''}` }}
        </template>
        <span style="overflow: hidden; display: inline-block; text-overflow: ellipsis; max-width: 86%; line-height: 20px; vertical-align: bottom; cursor: pointer;">
          {{ `${record.deptName || '' }` }}{{ record.deptName && record.workshop ? '/' : '' }}{{
            `${record.workshop || ''}` }}{{ (record.deptName || record.workshop) && record.productLine ? '/' : '' }}{{
            `${record.productLine || ''}` }}{{ (record.deptName || record.workshop || record.productLine) && record.processSection ? '/' : '' }}{{
            `${record.processSection || ''}` }}
        </span>
      </a-tooltip>
    </a-descriptions-item>
    <a-descriptions-item label="研发部门">
      {{ record.rdDeptName ? record.rdDeptName : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="负责人">
      {{ record.ename ? record.ename: '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="起止日期">
      {{ record.beginDate ? record.beginDate : '-' }} ~ {{ record.endDate ? record.endDate : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="产品">
      {{ record.involvedProduct ? record.involvedProduct : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="试制">
      {{ record.trialProd ? '有' : '无' }}
    </a-descriptions-item>
    <a-descriptions-item label="试制日期">
      {{ record.trialProd ? record.tBeginDate + '~' + record.tEndDate : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="项目展开形式">
      {{ record.formula ? formulaMap[record.formula] : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="项目来源" :span="2">
      {{ record.projectSource ? projectSourceMap[record.projectSource] : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="项目当年成果形式" :span="3">
      {{ record.result ? resultMap[record.result] : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="项目技术经济目标" :span="3">
      {{ record.targets ? targetsMap[record.targets] : '-' }}
    </a-descriptions-item>
    <a-descriptions-item label="高新领域" :span="3">
      {{ record.tecIndustry ? getHighTecIndustry(record.tecIndustry) : '-' }}
    </a-descriptions-item>
  </a-descriptions>
  </a-descriptions></template>

<script>
import highTecIndustry from '@/assets/tecIndustry'
function loadHighTecIndustryMap (source, data) {
  if (!source) { return }
  source.forEach(item => {
    data[item.key] = item.title
    if (item.children && item.children.length) {
      loadHighTecIndustryMap(item.children, data)
    }
  })
  return data
}
const formulaMap = {
  10: '自主完成',
  21: '与境内研究机构合作',
  22: '与境内高等学校合作',
  23: '与境内其他企业或单位合作',
  24: '与境外机构合作',
  30: '委托其他企业或单位',
  40: '其他形式'
}
const resultMap = {
  '01': '论文或专著',
  '02': '新产品、新工艺等推广与示范活动',
  '03': '对已有产品、工艺等进行一般性改进',
  '04': '对已有产品、工艺等实现突破性变革',
  '05': '软件著作权',
  '06': '应用软件',
  '07': '中间件或新算法',
  '08': '基础软件',
  '09': '发明专利',
  '10': '实用新型专利或外观设计专利',
  '11': '带有技术、工艺参数的图纸、技术标准、操作规范、技术论证、研究报告、咨询评价',
  '12': '自主研制的新产品原型或样机、样件、样品、配方、新装置',
  '13': '自主开发的新技术或新工艺、新工法、新服务',
  '14': '其他'
}
const targetsMap = {
  1: '科学原理的探索、发现',
  2: '技术原理的研究',
  3: '开发全新产品',
  4: '增加产品功能或提高性能',
  5: '提高劳动生产率',
  6: '减少能源消耗或提高能源使用效率',
  7: '节约原材料',
  8: '减少环境污染',
  9: '其他'
}
const projectSourceMap = {
  1: '本企业自选项目',
  2: '政府部门科技项目',
  3: '其他企业（单位）委托项目',
  4: '境外项目',
  5: '其他项目'
}
export default {
  name: 'ProjectDetail',
  props: {
    record: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      highTecIndustry,
      highTecIndustryMap: {},
      projectSourceMap,
      targetsMap,
      resultMap,
      formulaMap
    }
  },
  mounted () {
    this.highTecIndustryMap = this.loadHighTecIndustryMap(this.highTecIndustry, {})
  },
  methods: {
    loadHighTecIndustryMap,
    getHighTecIndustry (str) {
      const ary = str.split(',')
      let result = ''
      ary.forEach(item => {
        result += this.highTecIndustryMap[item] + '/'
      })
      return result.substring(0, result.length - 1)
    }
  }
}
</script>

<style>
</style>
<style lang="less">
.ant-descriptions-item-content {
    vertical-align: bottom;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    cursor: pointer;
  }
</style>
