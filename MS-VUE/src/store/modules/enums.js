/*
 * @Author: your name
 * @Date: 2022-02-21 08:33:31
 * @LastEditors: lzh
 * @LastEditTime: 2022-03-07 10:15:06
 * @Description: In User Settings Edit
 * @FilePath: \MS-VUE\src\store\modules\enums.js
 */
const enums = {
  state: {
    // 专利申请流程节点类型
    patentPlanProcessType: [
      { value: 0, label: '技术交底', color: 'pink' },
      { value: 1, label: '交底审批', color: 'orange' },
      { value: 2, label: '代理人分配', color: 'cyan' },
      { value: 3, label: '定稿', color: 'blue' },
      { value: 4, label: '受理', color: 'purple' },
      { value: 5, label: '受理缴费', color: 'red' },
      { value: 6, label: '授权', color: 'yellow' },
      { value: 7, label: '授权缴费', color: 'green' }
    ],
    // 专利申请状态
    patentPlanStatus: [
      { value: 0, label: '进行中' },
      { value: 1, label: '通过' },
      { value: 2, label: '驳回' },
      { value: 3, label: '激活' },
      { value: 8, label: '取消' }
    ],
    // 专利类型
    patentType: [
      { value: 1, label: '发明专利' },
      { value: 2, label: '实用新型' },
      { value: 3, label: '外观设计' }
    ],
    // 专利版本类型
    patentPlanType: [
      { value: 0, label: '高新版' },
      { value: 1, label: '保护版' }
    ],
    // 客户级别
    companyLevelOptions: [
      { value: 0, label: 'A+' },
      { value: 1, label: 'A' },
      { value: 2, label: 'B' },
      { value: 3, label: 'C' }
    ],
    // 研究人员类型
    rdEmployeeEnum: [
      { value: 1, label: '研究人员' },
      { value: 2, label: '技术人员' },
      { value: 3, label: '辅助人员' }
    ],
    // 设备类型
    equipmentEnum: [
      { value: 30000, label: '设备' },
      { value: 30100, label: '仪器' },
      { value: 30200, label: '房屋建筑' },
      { value: 40001, label: '软件摊销' }
    ]
  }
}

export default enums
