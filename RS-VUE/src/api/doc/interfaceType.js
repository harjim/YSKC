/*
 * @Author: ldx
 * @Date: 2020-12-30 08:58:10
 * @LastEditTime: 2021-02-24 14:44:10
 * @LastEditors: ldx
 * @Description: 过程文档接口
 * @FilePath: \RS-VUE\src\api\doc\interfaceType.js
 */
/**
 * @description: 获取过程文档数据
 */
export const GET_DOC_DATA = '/projectDocFileData/getData'
/**
 * @description: 获取项目阶段
 */
export const GET_ALL_STAGE = '/projectDocFile/queryStage'
/**
 * @description: 保存文档数据
 */
export const SAVE_DATA = '/projectDocFileData/saveData'
/**
 * @description:  获取预览文档数据
 */
export const GET_PREVIEW_DOC_DATA = '/projectDocFileData/previewFile'
/**
 * @description: 导出PDF
 */
export const EXPORT_PDF = '/projectDocFileData/exportPdf'
/**
 * @description: 提交文档审核
 */
export const SUBMIT_DOC_AUDIT = '/projectAudit/submitDocAudit'
/**
 * @description: 获取文档审核状态的统计数据
 */
export const GET_COUNT_DOC_AUDIT = '/projectAudit/countDocAuditData'

export const ADJUST_FILE_STAGE = '/projectDocFileData/changeDocStage'
