/*
 * @Author: ldx
 * @Date: 2021-01-05 11:48:40
 * @LastEditTime: 2022-08-22 15:48:12
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\js\templateContentType.js
 */
import moment from 'moment'
import { cloneDeep } from 'lodash'
// 科技成果转化报告
const achievementReportForm = {
  resultName: undefined,
  source: undefined,
  transformForm: undefined,
  technologyCore: undefined, // 技术核心
  innovation: undefined, // 技术创新点
  result: undefined, // 转化成果
  technicalIndex: undefined, // 技术指标
  benefit: undefined, // 经济效益分析
  transformYear: undefined,
  liaisonMatters: undefined,
  opinion: undefined,
  checkedList: [],
  checkedProveList: [],
  reviewTime: undefined,
  list: undefined,
  host: { enumber: undefined, ename: undefined },
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  checkedOptionArr: [],
  proveOptionArr: [],
  fileList: []
}
// 项目建议书
const adviceForm = {
  _pro: 0,
  rdDept: { id: undefined, name: undefined },
  emp: { enumber: undefined, ename: undefined },
  d: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  basic: '',
  advice: '',
  target: '',
  suggestion: ''
}
// 记录及附件与查新报告
const appendixForm = {
  _pro: 0,
  month: undefined,
  list: [],
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
// 阶段人员工作安排
const attendanceAggForm = {
  _pro: 0,
  _emp: undefined,
  employeeMonth: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  confirmTime: undefined,
  list: []
}
// 费用预算表
const costBudgetForm = {
  _pro: 0,
  _emp: undefined,
  costBudget: '费用预算',
  tablDatas: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
// 验证报告
const designReportForm = {
  _pro: 0,
  _dept: undefined,
  _emp: undefined,
  route: undefined,
  kPI: undefined,
  odp: undefined,
  principle: undefined,
  objectives: undefined, // 试验试制目的
  ename: undefined,
  beginDate: '',
  endDate: '',
  month: undefined,
  toCompile: undefined,
  audit: undefined,
  approval: undefined,
  projectPhase: undefined,
  projectDesign: undefined,
  usageSituation: undefined, // 试验试制研发资源使用情况
  existingProblems: undefined, // 试验试制情况及存在的问题
  proposal: undefined, // 分析意见和建议
  dataList: [],
  // trialProdIds: [],
  activeIds: [],
  inactiveIds: []
}
// 设计开发方案及附件
const devForm = {
  _pro: 0,
  _dept: undefined,
  _emp: undefined,
  kPI: undefined, // 关键指标
  odp: undefined, // 开发方案
  objectives: undefined, // 研发目的
  ename: undefined,
  beginDate: '',
  endDate: '',
  toCompile: undefined,
  audit: undefined,
  approval: undefined
}
// 仪器设备使用记录
const equipmentAggForm = {
  _pro: 0,
  _emp: undefined,
  equipmentMonth: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  confirmTime: undefined
}
// 实验验证报告
const experimentReportForm = {
  _pro: 0,
  _dept: undefined,
  _emp: undefined,
  route: undefined,
  kPI: undefined,
  odp: undefined,
  principle: undefined,
  objectives: undefined,
  ename: undefined,
  beginDate: '',
  endDate: '',
  toCompile: undefined,
  audit: undefined,
  approval: undefined
}
// 项目费用决算报告
const finalProjectCostForm = {
  case: undefined, // 核算总体情况
  analyze: undefined, // 项目费用总体完成情况分析
  issue: undefined, // 项目经费投入及核算存在的问题
  suggest: undefined, // 建议
  liaisonMatters: undefined,
  opinion: undefined,
  list: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  costBudget: '费用预算'
}
// 项目评级表
const gradeForm = {
  advice: { ename: undefined, enumber: undefined },
  master: { ename: undefined, enumber: undefined },
  manager: { ename: undefined, enumber: undefined },
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  d: undefined,
  grade: 'D',
  total: 0,
  score1: '',
  score2: '',
  score3: '',
  score4: '',
  score5: '',
  score6: '',
  score7: '',
  score8: '',
  score9: '',
  emp1: { ename: undefined, enumber: undefined },
  emp2: { ename: undefined, enumber: undefined },
  emp3: { ename: undefined, enumber: undefined },
  emp4: { ename: undefined, enumber: undefined },
  emp5: { ename: undefined, enumber: undefined },
  emp6: { ename: undefined, enumber: undefined },
  emp7: { ename: undefined, enumber: undefined },
  emp8: { ename: undefined, enumber: undefined },
  emp9: { ename: undefined, enumber: undefined },
  emp10: { ename: undefined, enumber: undefined }
}
// 用料计划表
const materialPlanForm = {
  deptName: {},
  _pro: 0,
  _emp: undefined,
  month: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  confirmTime: undefined,
  situation: undefined,
  list: []
}
// 会议纪要
const meetingForm = {
  refreshFileName: true,
  checkedList: [],
  readList: [],
  hostTime: undefined,
  _pro: 0,
  theme: undefined,
  meetingTitle: undefined,
  host: { enumber: undefined, ename: undefined },
  sendDeptName: undefined,
  checkedOptionAry: [],
  readOptionAry: [],
  _emp: undefined,
  place: undefined,
  members: undefined,
  mattersInvolved: undefined,
  rdDept: { id: undefined, name: undefined },
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
// 项目年度技术总结
const pAnnualReportForm = {
  _pro: 0,
  _dept: undefined,
  _emp: undefined,
  route: undefined,
  kPI: undefined,
  odp: undefined,
  principle: undefined,
  objectives: undefined,
  ename: undefined,
  beginDate: '',
  endDate: '',
  developmentResults: '',
  evaluate: '',
  experience: '',
  toCompile: undefined,
  audit: undefined,
  approval: undefined
}
// 研发项目资料清单
const pDataListForm = {
  approveName: undefined,
  verifyName: undefined,
  compilingName: undefined,
  checkedTypeList: [],
  liaisonMatters: undefined,
  opinion: undefined,
  checkedList: [],
  sendTime: moment().format('YYYY-MM-DD'),
  _pro: 0,
  _dept: undefined,
  _emp: undefined,
  list: [],
  sendEname: undefined,
  sendEnumber: undefined,
  sendDeptName: undefined,
  checkedOptionStr: undefined,
  checkedTypeOptionStr: undefined
}
// RD人员设备折旧分配说明
const pDistributionReportForm = {
  distributionReport: undefined, // RD人员设备折旧分配说明
  toCompile: undefined,
  audit: undefined,
  approval: undefined
}
// 项目验收报告
const projectCheckReportForm = {
  list: undefined,
  // rdDept: { id: undefined, name: undefined },
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  explain: undefined, // 验收工作说明
  verification: undefined, // 验收指标
  result: undefined, // 项目研发结果
  fileName: undefined, // 提交验收文档名称
  financialAudit: undefined, // 财务审核
  expertOpinion: undefined, // 专家委员会意见及验收结论
  auditOpinion: undefined, // 验收专家委员会成员名单
  plan: [],
  reality: [],
  planStr: undefined,
  realityStr: undefined,
  // planStartTime: undefined,
  // planEndTime: undefined,
  actualStartTime: undefined,
  actualEndTime: undefined,
  acceptance: undefined,
  auditSign: '总经理'
}
// 项目立项报告
const projectReportForm = {
  // dept: { id: undefined, name: undefined },
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  deptIdsArr: [],
  background: undefined, // 项目立项背景
  meaning: undefined, // 项目立项目的与意义
  researchContents: undefined, // 项目主要研究内容
  innovation: undefined, // 项目关键技术及创新点
  economicIndicators: undefined, // 项目技术及经济指标
  developmentMethod: undefined, // 研究开发方法
  technicalRoute: undefined, // 技术路线
  institutionBuilding: undefined, // 企业研发机构建设
  projectStatus: undefined, // 近年来研发项目情况
  scienceResult: undefined, // 近年来取得的科技成果
  costBudget: '费用预算',
  chapter5List: [],
  chapter7List: [],
  memberList: [],
  delMemberListids: [],
  tableDatas: [
    { name: '项目投资合计', cost1: 0, nameOut: '支出预算合计', value1: 0, value2: 0, value3: 0, inputStatus: true },
    {
      name: '一、申请市科技局拨款',
      cost1: 0,
      nameOut: '一、人员费',
      value1: 0,
      value2: 0,
      value3: 0,
      inputStatus: true
    },
    {
      name: '二、其他拨款',
      cost1: 0,
      nameOut: '其中：项目负责人',
      value1: 0,
      value2: 0,
      value3: 0,
      inputStatus: false
    },
    { name: '三、单位自筹', cost1: 0, nameOut: '项目人员', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '四、其他', cost1: 0, nameOut: '二、设备费', value1: 0, value2: 0, value3: 0, inputStatus: true },
    { name: '', cost1: undefined, nameOut: '1．购置费', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '2．试制费', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '3.折旧及摊销', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '三、能源材料费', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '四、试验外协费', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '五、资料及印刷费', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '六、会议差旅费', value1: 0, value2: 0, value3: 0, inputStatus: true },
    { name: '', cost1: undefined, nameOut: '1．会议费', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '2．差旅费', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '七、项目管理费', value1: 0, value2: 0, value3: 0, inputStatus: false },
    { name: '', cost1: undefined, nameOut: '八、其他费用', value1: 0, value2: 0, value3: 0, inputStatus: false }
  ],
  img: {}
}
// 研发人员参与项目汇总表
const rdEmploeeSummaryForm = {
  _pro: 0,
  _emp: undefined,
  employeeYear: undefined,
  lists: undefined
}
// 研发仪器设备参与项目汇总表
const rdEquipmentSummaryForm = {
  _pro: 0,
  _emp: undefined,
  equipmentYear: undefined,
  statisticalTime: undefined,
  lists: undefined
}
// 立项决议
const resolutionForm = {
  resolution: '',
  meetingDate: '',
  meetingMember: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
const resolutionForm1 = {
  resolution: '',
  meetingDate: '',
  meetingMember: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  meetingName1: undefined,
  meetingName2: undefined,
  meetingName3: undefined
}
const resolutionForm2 = {
  resolution: '',
  meetingDate: '',
  meetingMember: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
// 立项评审报告
const reviewForm = {
  opposeCount: undefined,
  agreeCount: undefined,
  checkedAgree: undefined,
  liaisonMatters: undefined, // 评审内容
  opinion: undefined, // 评审意见
  checkedList: [],
  reviewAddress: undefined,
  reviewTime: undefined,
  list: undefined, // 评审人员
  host: { enumber: undefined, ename: undefined },
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  checkedOptionArr: [],
  checkedAgreeOptionStr: undefined
}
// 阶段性验证验收评价
const stageReportForm = {
  d: undefined,
  TRLs: undefined,
  total: 0,
  passScore: 7.1,
  appraise: [],
  master: { enumber: undefined, ename: undefined },
  pMaster: { enumber: undefined, ename: undefined },
  rdMaster: { enumber: undefined, ename: undefined },
  members: { enumber: undefined, ename: undefined },
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  stageName: undefined
}
// 研发项目任务书
const taskForm = {
  beginDate: '', // 开发周期-起始
  endDate: '', // 开发周期-结束
  masterName: '',
  hasMemberStr: true,
  description: '', // 项目简介
  development: '', // 开发要求
  propose: '', // 研发负责人建议
  period: '',
  memberStr: '',
  allData: undefined, // 项目进度计划要求
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  index: 1,
  projectStage: '',
  completionTimeNode: ''
}
// 问题记录日志
const tIssueForm = {
  _pro: 0,
  _dept: undefined,
  _emp: undefined,
  list: []
}
// 阶段评审报告
const tReviewForm = {
  liaisonMatters: undefined,
  opinion: undefined,
  checkedList: [],
  stage: undefined,
  reviewTime: undefined,
  list: undefined,
  host: { enumber: undefined, ename: undefined },
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  checkedOptionStr: undefined,
  reviewConclusion: undefined,
  details: undefined
}
// 试制材料计划表
const trialMaterialForm = {
  deptName: {},
  _pro: 0,
  _emp: undefined,
  month: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined },
  confirmTime: undefined,
  list: []
}
// 试验试制研发工时记录表
const trialProductionDetailForm = {
  _pro: 0,
  _emp: undefined,
  environment: undefined,
  equipmentYear: undefined,
  statisticalTime: undefined,
  fileList: undefined
}
// 试验试制通知单
const trialProductionNoticeForm = {
  pilotStage: undefined,
  pilotTime: undefined,
  endPilotTime: undefined,
  trialDates: [],
  importDates: [],
  stage: undefined,
  require: undefined, // 要求
  purpose: undefined, // 目的
  changeConetnt: undefined, // 变更内容
  liaisonMatters: undefined,
  opinion: undefined,
  receiveList: [], // 接收
  emitList: [{ emitDep: { id: undefined, name: undefined }, emitPerson: undefined, emitTime: undefined }], // 发出
  emitDep: { id: undefined, name: undefined },
  emitPerson: { enumber: undefined, ename: undefined },
  emitTime: undefined,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
// 工作分解结构
const wBsForm = {
  _pro: 0,
  _dept: undefined,
  _emp: undefined,
  list: [],
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
const stageSummaryForm = {
  list: [], // 问题列表
  evaluate: undefined, // 开发工作评价
  problem: undefined, // 项目后续可能存在的问题
  measuredParameters: undefined, // 项目现阶段实测参数
  innovation: undefined, // 项目主要创新点
  researchContents: undefined, // 现阶段研究内容
  purpose: undefined, // 项目背景及目的
  member: undefined, // 项目成员
  summaryDate: undefined, // 总结日期
  trialManufactureQuantity: undefined, // 现阶段试制量
  way: undefined // 样品处置方式
}
const NewReportForm = {
  list: []
}
const monthSummaryReportForm = {
  dateMonth: undefined,
  summary: undefined,
  inCase: undefined,
  plan: undefined
}
const projectBeianForm = {
  projectUnits: undefined,
  projectNo: undefined,
  primaryContent: undefined,
  techLine: undefined,
  innovation: undefined,
  advice: undefined,
  putInto: undefined,
  planPutInto: undefined,
  progressArrange: undefined,
  target: undefined,
  unitOpinion: undefined,
  devCenterOpinion: undefined
}
// eslint-disable-next-line camelcase
const projectReportForm_1 = {
  trend: undefined,
  purpose: undefined,
  prospects: undefined,
  primaryContent: undefined,
  innovation: undefined,
  indicators: undefined,
  method: undefined,
  route: undefined,
  conditionsAndBasis: undefined,
  stageList: [],
  tableDatas: [
    { subjectName: '新产品设计费', key: 1 },
    { subjectName: '动力费用', key: 2 },
    { subjectName: '设备调试费', key: 3 },
    { subjectName: '用于研究开发的原材料、半成品试制费', key: 4 },
    { subjectName: '研发用辅助材料', key: 5 },
    { subjectName: '中间实验费', key: 6 },
    { subjectName: '研究设备及设施折旧额', key: 7 },
    { subjectName: '30万元以下一次性进费用的用于研究开发的仪器和设备', key: 8 },
    { subjectName: '研究人员工资', key: 9 },
    { subjectName: '委托其他单位进行科研试制的费用', key: 10 },
    { subjectName: '与技术开发有关的其他费用', key: 11 }
    // { subjectName: '合计', key: 12 },
    // { subjectName: '总计', key: 13 }
  ],
  memberList: [],
  sumBudget: {},
  totalBuget: 0,
  // chapter5List: [],
  chapter7List: [],
  audit: undefined,
  auditOpinion: undefined
}
// eslint-disable-next-line camelcase
const projectReportForm_3 = {
  trend: undefined, // 立项背景
  purpose: undefined, // 项目开发的目的、意义
  prospects: undefined, // 本项目达到的技术水平
  primaryContent: undefined, // 项目主要研发内容
  innovation: undefined, // 主要技术创新之处
  indicators: undefined, // 技术指标
  benefits: undefined, // 经济指标
  methodAndRoute: undefined, // 研究开发方法及技术路线
  orgAndDivision: undefined, // 研究开发工作组织和分工情况
  conditionsAndBasis: undefined, // 现有开发条件和工作基础
  stageList: [], // 引入项目阶段
  tableDatas: [
    { subjectName: '1.人员人工费用', key: 1 },
    { subjectName: '2.直接投入费用', key: 2 },
    { subjectName: '3.折旧费用与长期待摊费用', key: 3 },
    { subjectName: '4.无形资产摊销费用', key: 4 },
    { subjectName: '5.设计费用', key: 5 },
    { subjectName: '6.装备调试费用与试验费用', key: 6 },
    { subjectName: '7.委托外部研究开发费用', key: 7 },
    { subjectName: '　①委托境内研究机构', key: 8 },
    { subjectName: '　②委托境内高等学校', key: 9 },
    { subjectName: '　③委托境内企业', key: 10 },
    { subjectName: '　④委托境外机构', key: 11 },
    { subjectName: '8.其他费用', key: 12 }
  ],
  sumBudget: {}, // 合计
  totalBuget: 0 // 总计
}
// eslint-disable-next-line camelcase
const projectReportForm_4 = {
  mobile: undefined, // 电话
  fillDate: undefined, // 填报日期
  trend: undefined, // 国内外现状、水平和发展趋势
  purpose: undefined, // 项目开发的目的、意义
  prospects: undefined, // 本项目达到的技术水平
  primaryContent: undefined, // 项目主要内容、目标及关键技术
  innovation: undefined, // 技术创新之处
  indicatorsOrBenefits: undefined, // 主要技术指标或经济指标
  methodAndRoute: undefined, // 开发试验方法及技术路线（工艺路线）
  advantage: undefined, // 承担单位开展本项目的优势（人才、设施条件）
  workBasis: undefined, // 已有的工作基础，如预试及小试成果等
  stageList: [], // 引入项目阶段
  tableDatas: [
    { subjectName: '新产品设计费', key: 1 },
    { subjectName: '工艺规程制定费', key: 2 },
    { subjectName: '设备调试费', key: 3 },
    { subjectName: '用于研究开发的原材料、半成品试制费', key: 4 },
    { subjectName: '技术图书资料费', key: 5 },
    { subjectName: '中间实验费', key: 6 },
    { subjectName: '研究设备及设施折旧额', key: 7 },
    { subjectName: '30万元以下一次性进费用的用于研究开发的仪器和设备', key: 8 },
    { subjectName: '研究人员工资', key: 9 },
    { subjectName: '委托其他单位进行科研试制的费用', key: 10 },
    { subjectName: '与技术开发有关的其他费用', key: 11 }
  ],
  sumBudget: {}, // 合计
  totalBuget: 0, // 总计
  devInstitution: undefined
  // equList: [] // 仪器设备清单
}
// eslint-disable-next-line camelcase
const projectReportForm_5 = {
  tel: undefined, // 联系电话
  mobile: undefined, // 手机
  email: undefined, // 邮箱
  fillDate: undefined, // 填报日期
  ProjectBasis: undefined, // 立项依据
  rdContentAndTarget: undefined, // 研发内容和目标
  advantage: undefined, // 现有研发条件和工作基础
  schedule: undefined, // 计划进度
  orgDesc: undefined, // 描述组织架构
  achievements: undefined, // 预期成果归属
  budgetDesc: undefined, // 经费概算
  tableDatas: [
    { subjectName: '1.人员人工费用', key: 1 },
    { subjectName: '2.直接投入费用', key: 2 },
    { subjectName: '3.折旧费用与长期待摊费用', key: 3 },
    { subjectName: '4.无形资产摊销费用', key: 4 },
    { subjectName: '5.设计费用', key: 5 },
    { subjectName: '6.装备调试费用与试验费用', key: 6 },
    { subjectName: '7.委托外部研究开发费用', key: 7 },
    { subjectName: '　①委托境内研究机构', key: 8 },
    { subjectName: '　②委托境内高等学校', key: 9 },
    { subjectName: '　③委托境内企业', key: 10 },
    { subjectName: '　④委托境外机构', key: 11 },
    { subjectName: '8.其他费用', key: 12 }
  ],
  sumBudget: {}, // 合计
  totalBuget: 0 // 总计
}
// eslint-disable-next-line camelcase
const projectReportForm_6 = {
  trend: undefined, // 国内外现状、水平和发展趋势
  purpose: undefined, // 本项目研究的目的、意义
  startPointAndIP: undefined, // 本项目研究现有起点及已存在的知识产权情况
  prospects: undefined, // 本项目研究国内外竞争情况及产业化前景
  researchContents: undefined, // 研究开发内容
  keyTechnology: undefined, // 关键技术
  innovation: undefined, // 项目的特色和创新之处
  mainTechnology: undefined, // 要达到的主要技术
  indicatorsAndBenefits: undefined, // 要达到的经济指标及社会、经济效益
  method: undefined, // 研究试验方法
  route: undefined, // 技术路线及方案布置设计
  unitOverview: undefined, // 承担单位概况
  workBasis: undefined, // 本项目现有的研究工作基础
  orgManageAndMeasures: undefined, // 项目的组织管理及相关保障措施
  serviceManage: undefined, // 项目实施具备的人才队伍、经费配套投入能力及科技服务管理能力
  environment: undefined, // 本项目实施可能对环境的影响及预防治理方案
  achievements: undefined, // 预期成果
  benefits: undefined, // 预期效益
  stageList: [], // 项目阶段
  unitFunding: undefined, // 单位自筹
  govFunding: undefined, // 政府资助
  otherFunding: undefined, // 其他
  tableDatas: [
    { subjectName_2: '支出预算合计', key: 1, subjectName_1: '项目投资合计' },
    { subjectName_2: '一、人员费', key: 2, subjectName_1: '一、申请市科技局拨款' },
    { subjectName_2: '其中：项目负责人', key: 3, subjectName_1: '二、其他拨款' },
    { subjectName_2: '　　　项目人员', key: 4, subjectName_1: '三、单位自筹' },
    { subjectName_2: '二、设备费', key: 5, subjectName_1: '四、其他' },
    { subjectName_2: '　1．购置费', key: 6 },
    { subjectName_2: '　2．试制费', key: 7 },
    { subjectName_2: '　3. 折旧及摊销', key: 8 },
    { subjectName_2: '三、能源材料费', key: 9 },
    { subjectName_2: '四、试验外协费', key: 10 },
    { subjectName_2: '五、资料及印刷费', key: 11 },
    { subjectName_2: '六、会议差旅费', key: 12 },
    { subjectName_2: '　1．会议费', key: 13 },
    { subjectName_2: '　2．差旅费', key: 14 },
    { subjectName_2: '七、项目管理费', key: 15 },
    { subjectName_2: '八、其他费用', key: 16 }
  ],
  sumBudget: {}, // 合计
  totalBuget: 0 // 总计
}
const TrustOrgForm = {
  memberData: undefined,
  list: []
}
// 委托项目记录及附件
const TrustAppendixForm = {
  _pro: 0,
  month: undefined,
  list: [],
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
const TrustContractForm = {
  _pro: 0,
  month: undefined,
  list: [],
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
const TrustProjectSummaryForm = {
  _pro: 0,
  month: undefined,
  list: [],
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}

// 项目进度分析表
const ProjectAnalysisForm = {
  fillDate: undefined, // 填写时间
  expectation11: 5,
  expectation12: 1,
  expectation13: 1,
  expectation14: 1,
  approval: { enumber: undefined, ename: undefined },
  audit: { enumber: undefined, ename: undefined },
  toCompile: { enumber: undefined, ename: undefined }
}
// 变更管理报表
const ChangeManagementFrom = {
  tableData: [],
  toCompile: undefined,
  audit: undefined,
  approval: undefined
}
// 效用报告
const UtilityReportForm = {
  month: undefined,
  overview: undefined,
  target: undefined,
  innovation: undefined,
  toCompile: undefined,
  audit: undefined,
  approval: undefined
}
// 会议签到表
const MeetingSignInForm = {
  hostTime: null,
  theme: null,
  shouldArrive: 0,
  actuallyArrive: 0,
  list: []
}
const contents = {
  attendanceAggForm,
  devForm,
  designReportForm,
  costBudgetForm,
  achievementReportForm,
  adviceForm,
  appendixForm,
  equipmentAggForm,
  experimentReportForm,
  finalProjectCostForm,
  gradeForm,
  materialPlanForm,
  meetingForm,
  pAnnualReportForm,
  pDataListForm,
  pDistributionReportForm,
  projectCheckReportForm,
  projectReportForm,
  rdEmploeeSummaryForm,
  rdEquipmentSummaryForm,
  resolutionForm,
  reviewForm,
  stageReportForm,
  taskForm,
  tIssueForm,
  tReviewForm,
  trialMaterialForm,
  trialProductionDetailForm,
  trialProductionNoticeForm,
  wBsForm,
  stageSummaryForm,
  resolutionForm1,
  resolutionForm2,
  NewReportForm,
  monthSummaryReportForm,
  projectBeianForm,
  projectReportForm_1,
  projectReportForm_3,
  projectReportForm_4,
  projectReportForm_5,
  projectReportForm_6,
  TrustOrgForm,
  TrustAppendixForm,
  TrustContractForm,
  TrustProjectSummaryForm,
  ProjectAnalysisForm,
  ChangeManagementFrom,
  UtilityReportForm,
  MeetingSignInForm
}
export function getTemplateContent (name) {
  return cloneDeep(contents[name])
}
export function publicInfoObj () {
  const publicInfo = new PublicInfo()
  publicInfo.beginYear = undefined
  publicInfo.data = undefined
  publicInfo.defaultTitle = undefined
  publicInfo.docDate = undefined
  publicInfo.docFileId = undefined
  publicInfo.docFileName = undefined
  publicInfo.docFileTemplateId = undefined
  publicInfo.employeeMap = undefined
  publicInfo.id = undefined
  publicInfo.month = undefined
  publicInfo.pDocFileId = undefined
  publicInfo.pname = undefined
  publicInfo.projectId = undefined
  publicInfo.rdIndex = undefined
  publicInfo.templateName = undefined
  publicInfo.wordLength = undefined
  publicInfo.commonMap = {
    approval: undefined,
    audit: undefined,
    beginAndEnd: undefined,
    beginAndEndMonth: undefined,
    beginYear: undefined,
    cname: undefined,
    currentStage: undefined,
    parentRdDept: undefined,
    pname: undefined,
    projectMasterName: undefined,
    rdIndex: undefined,
    stageBeginMonth: undefined,
    stageEndMonth: undefined,
    toCompile: undefined,
    totalBudget: undefined
  }
  publicInfo.dataMap = undefined
  publicInfo.deleted = undefined
  publicInfo.footerMap = undefined
  publicInfo.hasSubmit = undefined
  publicInfo.owner = undefined
  publicInfo.stageKey = undefined
  publicInfo.status = undefined
  publicInfo.templateId = undefined
  return publicInfo
}

function PublicInfo () {}
