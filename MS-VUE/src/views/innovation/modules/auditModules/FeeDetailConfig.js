import store from '@/store/index'

/*
 * @Author: zdf
 * @Date: 2022-04-26 17:00:18
 * @LastEditTime: 2022-05-06 19:27:09
 * @LastEditors: zdf
 * @Description: 费用明细配置
 * @FilePath: \MS-VUE\src\views\innovation\modules\auditModules\FeeDetailConfig.js
 */
function toBit (v, bit) {
  if (v !== 0 && !v) {
    return '0.00'
  }
  let b = 2
  if (bit || bit >= 0) {
    b = bit
  }
  let div = 1
  for (let i = 0; i < b; i++) {
    div = div * 10
  }
  // 保留双倍小数位，确保四舍五入不会丢失精度
  v = Number(v).toFixed(b * 2)
  // if (sub) {
  //   return (parseInt(v * div) / div).toFixed(b)
  // }
  return (Math.round(v * div) / div).toFixed(b).toString().replace(/\d(?=(?:\d{3})+\b)/g, '$&,')
}
// const employeeTypeMap = { 1: '研究人员', 2: '技术人员', 3: '辅助人员' }
const employeeTypeMap = store.state.enums.rdEmployeeEnum
// const equipmentTypeMap = { 30000: '设备', 30100: '仪器' }
const equipmentTypeMap = store.state.enums.equipmentEnum
const InspectiontypeMap = {
  '20500': '检测',
  '20600': '修理',
  '40000': '软件摊销',
  '40100': '专利摊销',
  '40200': '其他摊销',
  '20300': '其他试制',
  '69900': '其他',
  '60400': '差旅费',
  '60000': '资料',
  '60100': '研发成果',
  '60200': '知识产权',
  '60300': '福利'
}
const employeeCols = [
  {
    title: '工号',
    field: 'enumber',
    align: 'left',
    width: 120,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '姓名',
    field: 'ename',
    align: 'left',
    width: 120,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '人员类型',
    field: 'etype',
    align: 'center',
    width: 100,
    remoteSort: true,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => cellValue && cellValue !== -1 ? employeeTypeMap.find(item => item.value === Number(cellValue)).label : ''
  },
  {
    title: '总工时',
    field: 'workHours',
    align: 'right',
    width: 100,
    showHeaderOverflow: true
  },
  {
    title: '应发工资',
    field: 'pay',
    align: 'right',
    width: 120,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '五险一金',
    field: 'insuranceFund',
    align: 'right',
    width: 120,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '研发工时',
    field: 'rdHour',
    align: 'right',
    width: 110,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '研发工资',
    field: 'rdPay',
    align: 'right',
    width: 120,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '研发五险一金',
    field: 'rdInsuranceFund',
    align: 'right',
    width: 130,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '研发合计',
    align: 'right',
    width: 110,
    showHeaderOverflow: true,
    formatter: ({ row }) => toBit(row.rdPay ? Number(row.rdPay) + Number(row.rdInsuranceFund) : null)
  }
]

const equipmentCols = [
  {
    title: '资产代码',
    field: 'ecode',
    align: 'left',
    width: 120,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '设备名称',
    field: 'ename',
    align: 'left',
    width: 120,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '类型',
    field: 'etype',
    align: 'left',
    width: 120,
    remoteSort: true,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => cellValue ? equipmentTypeMap.find(item => item.value === Number(cellValue)).label : ''
  },
  {
    title: '运行工时',
    field: 'workHours',
    align: 'right',
    width: 100,
    showHeaderOverflow: true
  },
  {
    title: '运行折旧',
    field: 'depreciation',
    align: 'right',
    width: 120,
    showHeaderOverflow: true
  },
  {
    title: '研发工时',
    field: 'rdHour',
    align: 'right',
    width: 100,
    showHeaderOverflow: true
  },
  {
    title: '研发折旧',
    field: 'rdDepreciation',
    align: 'right',
    width: 120,
    showHeaderOverflow: true
  }
]
const materialCols = [
  {
    title: '物料名称',
    field: 'mname',
    align: 'left',
    width: 180,
    showHeaderOverflow: true
  },
  {
    title: '物料编码',
    field: 'mcode',
    align: 'left',
    width: 130,
    showHeaderOverflow: true
  },
  {
    title: '领用日期',
    field: 'acqDate',
    align: 'center',
    width: 130,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '单价',
    field: 'unitPrice',
    align: 'right',
    width: 100,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '数量',
    field: 'quantity',
    align: 'right',
    width: 90,
    showHeaderOverflow: true
  },
  {
    title: '单位',
    field: 'unit',
    align: 'left',
    width: 90,
    showHeaderOverflow: true
  },
  {
    title: '金额',
    field: 'totalAmount',
    align: 'right',
    width: 100,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '研发数量',
    field: 'used',
    align: 'right',
    width: 100,
    showHeaderOverflow: true
  },
  {
    title: '研发费用',
    field: 'rdAmount',
    align: 'right',
    width: 110,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  }
]
const energyCols = [
  {
    title: '能源名称',
    field: 'ename',
    align: 'left',
    width: 150,
    showHeaderOverflow: true
  },
  {
    title: '凭证号',
    field: 'accNumber',
    align: 'left',
    width: 130,
    showHeaderOverflow: true
  },
  {
    title: '发生日期',
    field: 'occDate',
    align: 'center',
    width: 130,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '单价',
    field: 'unitPrice',
    align: 'right',
    width: 100,
    showHeaderOverflow: true
  },
  {
    title: '数量',
    field: 'quantity',
    align: 'right',
    width: 90,
    showHeaderOverflow: true
  },
  {
    title: '单位',
    field: 'unit',
    align: 'left',
    width: 90,
    showHeaderOverflow: true
  },
  {
    title: '金额',
    field: 'totalAmount',
    align: 'right',
    width: 120,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '分配金额',
    field: 'amount',
    align: 'right',
    width: 120,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '研发数量',
    field: 'rdQuantity',
    align: 'right',
    width: 100,
    showHeaderOverflow: true
  },
  {
    title: '研发费用',
    field: 'rdAmount',
    align: 'right',
    width: 110,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  }
]
const equipmentPowerCols = [
  {
    title: '资产代码',
    field: 'ecode',
    align: 'left',
    width: 120,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '设备名称',
    field: 'ename',
    align: 'left',
    width: 120,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '类型',
    field: 'etype',
    align: 'left',
    width: 120,
    remoteSort: true,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => cellValue ? equipmentTypeMap.find(item => item.value === Number(cellValue)).label : ''
  },
  {
    title: '研发工时',
    field: 'rdHour',
    align: 'right',
    width: 100,
    showHeaderOverflow: true
  },
  {
    title: '功率(kWh)',
    field: 'usagePower',
    align: 'right',
    width: 110,
    showHeaderOverflow: true
  },
  {
    title: '电费单价',
    field: 'powerUnitPrice',
    align: 'right',
    width: 110,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '研发电费',
    field: 'powerRate',
    align: 'right',
    width: 120,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  }
]
const designCols = [
  {
    title: '设计名称',
    field: 'dname',
    align: 'left',
    width: 260,
    showHeaderOverflow: true
  },
  {
    title: '设计日期',
    field: 'designDate',
    align: 'center',
    width: 130,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '设计费用',
    field: 'dFee',
    align: 'right',
    width: 130,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '研发费用',
    field: 'rdAmount',
    align: 'right',
    width: 130,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  }
]
const inspectionCols = [
  {
    title: '摘要',
    field: 'summary',
    align: 'left',
    width: 260,
    showHeaderOverflow: true
  },
  {
    title: '记账日期',
    field: 'accDate',
    align: 'center',
    width: 130,
    remoteSort: true,
    showHeaderOverflow: true
  },
  {
    title: '凭证号',
    field: 'accNumber',
    align: 'left',
    width: 180,
    showHeaderOverflow: true
  },
  {
    title: '费用类型',
    field: 'type',
    align: 'right',
    width: 120,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => InspectiontypeMap[cellValue]
  },
  {
    title: '费用',
    field: 'expense',
    align: 'right',
    width: 130,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  },
  {
    title: '研发费用',
    field: 'rdAmount',
    align: 'right',
    width: 130,
    showHeaderOverflow: true,
    formatter: ({ cellValue }) => NumberFormatHasNull(cellValue)
  }
]
const NumberFormatHasNull = (value) => {
  if (!(value !== null && /^[0-9]+.?[0-9]*/.test(value))) {
    return '--'
  }
  const intPartFormat = (+value).toFixed(2).toString().replace(/\d(?=(?:\d{3})+\b)/g, '$&,') // 将整数部分逢三一断
  return intPartFormat
}
const config = {
  employee: { columns: employeeCols, url: '/rdFeeAudit/getEmployeeFees' },
  equipment: { columns: equipmentCols, url: '/rdFeeAudit/getEquipmentFees' },
  material: { columns: materialCols, url: '/rdFeeAudit/getMaterialFees' },
  energy: { columns: energyCols, url: '/rdFeeAudit/getEnergyFees' },
  equipmentPower: { columns: equipmentPowerCols, url: '/rdFeeAudit/getEquipmentPowerFees' },
  design: { columns: designCols, url: '/rdFeeAudit/getDesignFees' },
  inspection: { columns: inspectionCols, url: '/rdFeeAudit/getInspectionFees' }
}
const rdTypeMap = {
  10000: 'employee',
  30000: 'equipment',
  20000: 'material',
  20001: 'material',
  20002: 'material',
  20301: 'material',
  20303: 'material',
  20304: 'material',
  20302: 'energy',
  20300: 'inspection',
  20601: 'material',
  50000: 'design',
  20600: 'inspection',
  20500: 'inspection',
  20700: 'inspection',
  20100: 'energy',
  20102: 'energy',
  20101: 'equipmentPower',
  20200: 'energy',
  20201: 'energy',
  60400: 'inspection',
  40000: 'inspection',
  40001: 'equipment',
  69900: 'inspection'
}
const auditCostTypes = [
  { rdType: 10000, title: '人员费用', children: [10000, 10100] },
  { rdType: 30000, title: '设备折旧', children: [30000, 30100, 30200] },
  { rdType: 20000, title: '研发材料', children: [20000, 20001, 20002], tabs: { 20000: '研发材料', 20001: '造纸材料', 20002: '流程型' } },
  { rdType: 20300, title: '中间试制', children: [20301, 20303, 20304, 20302, 20300], tabs: { 20301: '试制材料', 20303: '造纸试制', 20304: '流程型', 20302: '试制动力', 20300: '其他试制' } },
  { rdType: 20600, title: '修理费用', children: [20601, 20600], tabs: { 20601: '修理材料', 20600: '凭证费用' } },
  { rdType: 20700, title: '样机费用', children: [20700] },
  { rdType: 20100, title: '动力损耗', children: [20100, 20102, 20101], tabs: { 20100: '研发动力', 20102: '流程型', 20101: '设备动力' } },
  { rdType: 20200, title: '燃料损耗', children: [20200, 20201], tabs: { 20200: '研发燃料', 20201: '流程型' } },
  { rdType: 50000, title: '设计费用', children: [50000] },
  { rdType: 20500, title: '检测费用', children: [20500] },
  { rdType: 60400, title: '差旅费用', children: [60400] },
  { rdType: 40000, title: '摊销费用', children: [40000, 40100, 40200, 40001], tabs: { 40000: '凭证费用', 40001: '资产摊销' } },
  { rdType: 69900, title: '其他费用', children: [60000, 60100, 60200, 60300, 69900] }
]
const auditCostTypesMap = {}
auditCostTypes.forEach(item => { auditCostTypesMap[item.rdType] = item })
function getConfigParams (projectId, rdType) {
  const type = rdTypeMap[rdType]
  const temp = type ? config[type] : {}
  const result = { projectId }
  for (const k in temp) {
    result[k] = temp[k]
  }
  let rdTypes
  switch (Number(rdType)) {
    case 40000:
      rdTypes = [40000, 40100, 40200]
      break
    case 69900:
      rdTypes = [60000, 60100, 60200, 60300, 69900]
      break
    case 30000:
      rdTypes = [30000, 30100, 30200]
      break
    default:
      rdTypes = [rdType]
      break
  }
  result.rdTypes = rdTypes
  return result
}
export { getConfigParams, toBit, auditCostTypes, auditCostTypesMap }
