export const genderMap = {
  1: '男',
  2: '女'
}
export const statusMap = {
  0: {
    text: '待审核',
    color: 'orange'
  },
  1: {
    text: '通过',
    color: 'green'
  },
  2: {
    text: '不通过',
    color: 'red'
  }
}
export const requirementStatusMap = {
  0: {
    text: '草稿',
    status: 'warning'
  },
  1: {
    text: '发布中',
    status: 'success'
  },
  2: {
    text: '服务中',
    status: 'success'
  },
  3: {
    text: '已完成',
    status: 'processing'
  },
  4: {
    text: '终止',
    status: 'error'
  },
  5: {
    text: '已过期',
    status: 'default'
  }
}
export const workYearMap = {
  0: '1-3年',
  1: '3-5年',
  2: '5-10年',
  3: '10年以上',
  4: '经验不限'
}
export const jobTypeMap = {
  0: '技术',
  1: '产品',
  2: '设计',
  3: '运营',
  4: '市场',
  5: '人事/财务/行政',
  6: '高级管理',
  7: '销售',
  8: '传媒',
  9: '金融',
  10: '教育培训',
  11: '医疗健康',
  12: '采购/贸易',
  13: '供应链/物流',
  14: '房地产/建筑',
  15: '农/林/牧/渔',
  16: '咨询/翻译/法律',
  17: '旅游业',
  18: '服务业',
  19: '生产制造',
  99: '不限'
}
export const recruitmentMap = {
  0: '社会招聘',
  1: '校园招聘'
}
export const cooperationStatusMap = {
  0: {
    text: '未沟通',
    color: 'orange'
  },
  1: {
    text: '已合作',
    color: 'green'
  }
}
export const idTypeMap = {
  0: '身份证',
  1: '护照',
  2: '港澳通行证'
}
export const eduLevelMap = {
  0: '研究生',
  1: '本科',
  2: '大专',
  3: '其他'
}
export const degreeMap = {
  0: '博士',
  1: '硕士',
  2: '学士',
  3: '无'
}
export const typesMap = {
  0: '技术专家',
  1: '财务专家',
  2: '管理专家',
  3: '风险评估专家',
  4: '知识产权专家'
}
export const cooperateTypeMap = {
  0: '技术转让',
  1: '技术服务',
  2: '技术许可',
  3: '技术融资',
  4: '技术授权',
  5: '其他'
}
export const urgencyMap = {
  0: '一周以内',
  1: '一月以内',
  2: '三个月以内',
  3: '六个月以内',
  4: '一年以内',
  5: '两年以内'
}
export const filesTypeMap = {
  1: '身份证正反面',
  2: '学历证书',
  3: '职称证书',
  4: '其它证明材料'
}
// 单位类型 前端维护
export const unitTypeMap = [
  {
    key: '1000',
    value: '高等院校',
    children: [
      { key: '1010', value: '985工程院校' },
      { key: '1011', value: '211工程院校' },
      { key: '1012', value: '中央部属本科院校' },
      { key: '1013', value: '省属本科院校' },
      { key: '1014', value: '高职(高专)院校' }
    ]
  },
  {
    key: '1100',
    value: '企业',
    children: [
      { key: '1110', value: '中小型企业' },
      { key: '1111', value: '大型企业' },
      { key: '1112', value: '上市企业' },
      { key: '1113', value: '国有企业' }
    ]
  },
  {
    key: '1200',
    value: '科研院所',
    children: []
  },
  {
    key: '1300',
    value: '社团',
    children: []
  },
  {
    key: '1400',
    value: '政府部门',
    children: []
  },
  {
    key: '1500',
    value: '医疗单位',
    children: []
  },
  {
    key: '9900',
    value: '其他',
    children: []
  }
]
// 获取最后的层级数据
export function getLastDataByTree (pathStr, treeData) {
  if (pathStr == null || treeData == null) return
  const pathList = pathStr.split('/')
  while (pathList.length > 0) {
    const key = pathList.shift()
    if (treeData instanceof Array) {
      const index = treeData.findIndex(item => { return item.key === key })
      if (index === -1) {
        return null
      }
      if (pathList.length > 0) {
        treeData = treeData[index].children
      } else {
        treeData = treeData[index].value
      }
    }
  }
  return treeData
}
// 获取完整层级数据
export function getFullPathDataByTree (pathStr, treeData, separator = '/') {
  if (pathStr == null || treeData == null) return
  const pathList = pathStr.split('/')
  const result = []
  while (pathList.length > 0) {
    const key = pathList.shift()
    if (treeData instanceof Array) {
      const index = treeData.findIndex(item => {
        return item.key === key
      })
      if (index === -1) {
        return null
      }
      result.push(treeData[index].value)
      if (pathList.length > 0) {
        treeData = treeData[index].children
      } else {
        treeData = treeData[index].value
      }
    }
  }
  return result.join(separator)
}
// 获取多个数据中的第一个层级（去重）
export function getFirstDataByTree (pathStr, treeData) {
  if (pathStr == null || treeData == null) return
  const firstPathList = pathStr.split(',').map(item => {
    return item.split('/')[0]
  })
  const arr = [...new Set(firstPathList)]
  return arr.map(d => {
    const findIndex = treeData.findIndex(item => {
      return item.key === d
    })
    if (findIndex === -1) {
      return null
    }
    return treeData[findIndex].value
  })
}
