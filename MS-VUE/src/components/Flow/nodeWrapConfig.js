/*
 * @Author: ldx
 * @Date: 2021-04-15 11:44:37
 * @LastEditTime: 2022-02-19 10:56:29
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \MS-VUE\src\components\Flow\nodeWrapConfig.js
 */

const selectLabel = {
  1: '一级主管',
  2: '二级主管',
  3: '三级主管',
  4: '四级主管',
  5: '五级主管',
  6: '六级主管',
  7: '七级主管',
  8: '八级主管',
  9: '九级主管',
  10: '十级主管'
}
/**
 * @description: 主管级别集合
 */
const selectOptions = [
  { label: '一级主管', value: 1 },
  { label: '二级主管', value: 2 },
  { label: '三级主管', value: 3 },
  { label: '四级主管', value: 4 },
  { label: '五级主管', value: 5 },
  { label: '六级主管', value: 6 },
  { label: '七级主管', value: 7 },
  { label: '八级主管', value: 8 },
  { label: '九级主管', value: 9 },
  { label: '十级主管', value: 10 }
]
/**
 * @description: 主管审核模式 1：会签 0：或签
 */
const auditTypeOptions = [
  { label: '或签', value: 0 },
  { label: '会签', value: 1 }
]
/**
 * @description: 审核单选集合 0成员1角色2主管
 */
const auditOptions = [
  { label: '指定成员', value: 0 },
  { label: '主管', value: 2 },
  { label: '指定角色', value: 1 },
  { label: '连续多级主管', value: 3 },
  { label: '表单用户', value: 4 }
]
export {
  selectLabel,
  selectOptions,
  auditTypeOptions,
  auditOptions
}
