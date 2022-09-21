import Mock from 'mockjs2'
import { builder } from '../util'

const info = (options) => {
  const userInfo = {
    'user': {
      'id': '4291d7da9005377ec9aec4a71ea837f',
      'companyId': '123456',
      'companyName': '测试公司',
      'logo': 'logo.png',
      'realName': '超级管理员',
      'username': 'admin',
      'avatar': '/avatar.jpg'
    },
    'menus': [],
    'permissions': []
  }

  userInfo.menus = [
    { id: 2, parentId: -1, name: '申报管理', url: '/project', type: 0, icon: 'solution' },
    { id: 3, parentId: -1, name: '数据录入', url: '/dataentry', type: 0, icon: 'table' },
    { id: 4, parentId: -1, name: '企业资料', url: '/company', type: 0, icon: 'team' },
    // { id: 21, parentId: -1, name: '基本信息', url: '/company', type: 0, icon: '' },

    { id: 5, parentId: 2, name: '项目管理', url: '/project/Manage', type: 1 },
    { id: 6, parentId: 2, name: '阶段管理', url: '/project/Stage', type: 1 },
    { id: 7, parentId: 2, name: '数据归集', url: '/project/Data', type: 1 },
    { id: 8, parentId: 2, name: '文档管理', url: '/project/Coc', type: 1 },

    { id: 9, parentId: 3, name: '人员考勤', url: '/dataentry/Attendance', type: 1 },
    { id: 10, parentId: 3, name: '员工薪资', url: '/dataentry/Salary', type: 1 },
    { id: 12, parentId: 3, name: '设备使用', url: '/dataentry/Equipment', type: 1 },
    { id: 13, parentId: 3, name: '物料清单', url: '/dataentry/Material', type: 1 },
    { id: 14, parentId: 3, name: '能源损耗', url: '/dataentry/Energy', type: 1 },
    { id: 11, parentId: 3, name: '研发设计', url: '/dataentry/Design', type: 1 },
    { id: 15, parentId: 3, name: '检测修理', url: '/dataentry/Inspection', type: 1 },

    { id: 16, parentId: 4, name: '花名册', url: '/company/Employee', type: 1 },
    { id: 17, parentId: 4, name: '设备列表', url: '/company/Equipment', type: 1 },
    { id: 18, parentId: 4, name: '科目管理', url: '/company/Account', type: 1 },
    { id: 21, parentId: 4, name: '组织架构', url: '/company/Org', type: 1 },
    { id: 19, parentId: 4, name: '基本信息', url: '/company/Info', type: 1 }
  ]
  return builder(userInfo)
}

Mock.mock(/\/api\/user\/info/, 'get', info)
