/*
 * @Author: your name
 * @Date: 2022-03-24 16:24:11
 * @LastEditors: lzh
 * @LastEditTime: 2022-03-24 16:27:59
 * @Description: In User Settings Edit
 * @FilePath: \RS-VUE\src\store\modules\enums.js
 */
const enums = {
  state: {
    // 研发类型
    rdTypeOptions: [
      { value: 10, label: '自主完成' },
      { value: 21, label: '与境内研究机构合作' },
      { value: 22, label: '与境内高等学校合作' },
      { value: 23, label: '与境内其他企业或单位合作' },
      { value: 24, label: '与境外机构合作' },
      { value: 30, label: '委托其他企业或单位' },
      { value: 40, label: '其他形式' }
    ],
    equipmentEnum: [
      { value: 30000, label: '设备' },
      { value: 30100, label: '仪器' },
      { value: 30200, label: '房屋建筑' },
      { value: 40001, label: '软件摊销' }
    ],
    rdEmployeeEnum: [
      { value: 1, label: '研究人员' },
      { value: 2, label: '技术人员' },
      { value: 3, label: '辅助人员' }
    ]
  }
}

export default enums
