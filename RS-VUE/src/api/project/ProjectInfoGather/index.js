/*
 * @Author: ldx
 * @Date: 2021-03-05 16:11:20
 * @LastEditTime: 2021-07-06 15:20:32
 * @LastEditors: ldx
 * @Description: 项目信息汇总
 * @FilePath: \RS-VUE\src\api\project\ProjectInfoGather\index.js
 */
import { axios } from '@/utils/request'
const PREFIX_URL = '/situationSummary'
const GET_PROJECT_SUMMARY = '/getProjectSummary'
export const LAZY_URLS = {
  // budget: PREFIX_URL + '/getBudgetInfo', // 获取项目预算信息
  budget: '/budget/queryBudgetList', // 获取项目预算信息
  staffRdHour: PREFIX_URL + '/getStaffHour', // 获取研发人员工时(按月)
  prodRdHour: PREFIX_URL + '/getEquipmentHour', // 获取仪器设备工时(按月)
  materialRaw: PREFIX_URL + '/getMaterialRaw', // 获取原材料用料
  materialAuxiliary: PREFIX_URL + '/getMaterialAuxiliary', // 获取辅料
  materialReserve: PREFIX_URL + '/getMaterialReserve', // 获取工艺装备用料
  materialTotal: PREFIX_URL + '/getMaterialSummary?type=0', // 获取研发材料归集费用
  trialTotal: PREFIX_URL + '/getMaterialSummary?type=1', // 获取中间试制材料归集费用
  repairTotal: PREFIX_URL + '/getMaterialSummary?type=2', // 获取修理费用材料归集费用
  yieldAmount: PREFIX_URL + '/getYieldAmount', // 获取排期表
  summary: PREFIX_URL + '/getSummaryInfo', // 获取项目归集信息
  budgetCost: PREFIX_URL + '/getBudgetCost' // 获取项目决算信息
}
export const DETAIL_URLS = {
  staffRdHour: '/projectRdEmployee/getListByMonth', // 根据月份获取项目人员列表
  prodRdHour: '/projectRdEquipment/queryList', // 根据月份获取项目设备使用情况
  materials: '/situationSummary/getMaterials', // 获取指定月份项目的原料 type: 1:原料详情; 2:辅料详情; 3:备品详情;
  yieldAmount: '/situationSummary/getYieldInfo', // 获取排期表详情
  totalStaff: '/projectRdEmployee/getMonthTotalStaff', // 根据月份获取所有人员工时
  totalProd: '/projectRdEquipment/getMonthTotalProd' // 根据月份获取所有设备工时
}
/**
 * @description: 查询项目信息汇总
 * @param {*} parameter
 * @return {*} promise
 */
export function projectSummary (parameter) {
  return axios({
    url: PREFIX_URL + GET_PROJECT_SUMMARY,
    method: 'get',
    params: parameter
  })
}
