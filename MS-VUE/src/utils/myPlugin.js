import store from '@/store/index'
import { exportServe } from '@/utils/request'
import * as dd from 'dingtalk-jsapi'
import moment from 'moment'
// eslint-disable-next-line no-unused-vars
const Qs = require('qs')
const notInDingTalk = dd.env.platform === 'notInDingTalk'
const cachedCode = {}
const deptTree = []
var productList = []
const budgetDictionary = []
const fileTypes = []
const dailyReportCols = []
const dictionaryTypes = {
  1: '区域代码',
  2: '高新领域',
  3: '预算来源',
  4: '预算支出',
  5: '币种',
  6: '项目阶段',
  7: '申报阶段',
  8: '申报进度',
  9: '申报状态',
  10: '申报项目类型',
  11: '项目申报级别',
  12: '职称',
  13: '学科',
  14: '单位财务情况',
  15: '行业',
  16: '备案资产类型',
  17: '服务事项',
  18: '付款方式',
  19: '财务工作事项'
}
const patentFileTypes = [
  { value: 0, label: '交底书' },
  { value: 1, label: '初稿' },
  { value: 2, label: '定稿' },
  { value: 3, label: '受理通知书' },
  { value: 10, label: '受理缴费' },
  { value: 4, label: '实质审查资料' },
  { value: 5, label: '授权通知书' },
  { value: 11, label: '办登缴费' },
  { value: 6, label: '知识产权证书' },
  { value: 7, label: '缴费收据' },
  { value: 9, label: '说明书' },
  { value: 8, label: '其他' }
]
function download (res, fileName) {
  const link = document.createElement('a')
  const blob = new Blob([res.data], { type: 'application/vnd.ms-excel' })
  link.style.display = 'none'
  link.href = URL.createObjectURL(blob)
  let finallyFileName
  if (fileName) {
    finallyFileName = fileName
  } else {
    finallyFileName = res.headers['filename'] ? decodeURI(res.headers['filename'].replace(/\+/g, '%20')) : null
  }
  if (finallyFileName) {
    link.setAttribute('download', finallyFileName)
  }
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}
// 处理树型结构，添加fullPath
function formatTree (data, parentPath = '') {
  return data.map((node, i) => {
    const hasChildren = !!(node.children || []).length
    node.fullPath = `${parentPath}${node.key}${hasChildren ? '/' : ''}`
    if (hasChildren) {
      formatTree(node.children, node.fullPath)
    }
    return {
      ...node
    }
  })
}

const myPlugin = {
  install: Vue => {
    Vue.prototype.$getDictionary = function (type, isFormat = false) {
      return new Promise((resolve, reject) => {
        if (cachedCode[type] && cachedCode[type].length > 0) {
          resolve(cachedCode[type])
        } else {
          return this.$http.get(type === 6 ? '/RsStage/searchStage' : '/sysDictionary/getDictionary', { params: { type: type } }).then(res => {
            if (res.success) {
              cachedCode[type] = isFormat ? formatTree(res.data) : res.data
              resolve(res.data)
            } else {
              reject(res)
            }
          })
        }
      })
    }
    Vue.prototype.$getCostTypes = () => {
      return [
        { value: 100, title: '工资', type: 10000 },
        { value: 101, title: '五险一金', type: 10100 },
        { value: 300, title: '设备折旧', type: 30000 },
        { value: 301, title: '仪器折旧', type: 30100 },
        { value: 302, title: '房屋建筑', type: 30200 },
        { value: 200, title: '材料', type: 20000 },
        { value: 201, title: '动力', type: 20100 },
        { value: 202, title: '燃料', type: 20200 },
        { value: 203, title: '试制', type: 20300 },
        { value: 205, title: '检测', type: 20500 },
        { value: 206, title: '修理', type: 20600 },
        { value: 207, title: '样机', type: 20700 },
        { value: 500, title: '设计', type: 50000 },
        { value: 400, title: '软件摊销', type: 40000 },
        { value: 401, title: '专利摊销', type: 40100 },
        { value: 402, title: '其他摊销', type: 40200 },
        { value: 604, title: '差旅费', type: 60400 },
        { value: 699, title: '其他', type: 69900 }
      ]
    }
    Vue.prototype.$getPatentFileTyps = () => {
      return patentFileTypes
    }
    Vue.prototype.$getFinanceDictionary = function () {
      const type = 14
      return new Promise((resolve, reject) => {
        if (cachedCode[type] && cachedCode[type].length > 0) {
          resolve(cachedCode[type])
        } else {
          return this.$http.get('/sysDictionary/getFinanceDictionary').then(res => {
            if (res.success) {
              cachedCode[type] = res.data
              resolve(res.data)
            } else {
              reject(res)
            }
          })
        }
      })
    }
    Vue.prototype.$getDictionaryTypes = () => {
      return dictionaryTypes
    }
    Vue.prototype.$deepClone = function (values) {
      var copy
      if (values === null || typeof values !== 'object') return values
      if (values instanceof Date) {
        copy = new Date()
        copy.setTime(values.getTime())
        return copy
      }
      if (values instanceof Array) {
        copy = []
        for (var i = 0, len = values.length; i < len; i++) {
          copy[i] = this.$deepClone(values[i])
        }
        return copy
      }
      if (values instanceof Object) {
        copy = {}
        for (var attr in values) {
          if (values.hasOwnProperty(attr)) copy[attr] = this.$deepClone(values[attr])
        }
        return copy
      }
      throw new Error("Unable to copy values! Its type isn't supported.")
    }
    Vue.prototype.$getDeptTree = function () {
      return new Promise((resolve, reject) => {
        if (deptTree.length > 0) {
          resolve(deptTree)
        } else {
          return this.$http.get('/dept/tree').then(res => {
            if (res.success) {
              if (deptTree.length === 0) {
                deptTree.push(...res.data)
              }
              resolve(deptTree)
            } else {
              reject(res)
            }
          })
        }
      })
    }
    Vue.prototype.$getProductList = function (reset) {
      return new Promise((resolve, reject) => {
        if (reset) {
          productList = []
          resolve(productList)
        }
        if (productList.length > 0) {
          resolve(productList)
        } else {
          return this.$http.get('/product/productForSelect').then(res => {
            if (res.success) {
              if (productList.length === 0) {
                productList.push(...res.data)
              }
              resolve(productList)
            } else {
              reject(res)
            }
          })
        }
      })
    }

    /**
     * 初始化表单
     * @param {Form} frm create的form
     * @param {Object} data 表单初始化内容
     * @param {Array} dateKeys 日期组件
     */
    Vue.prototype.$initForm = (frm, data, dateKeys) => {
      const fieldsVal = frm.getFieldsValue()
      for (const key in fieldsVal) {
        if (typeof data[key] !== 'undefined') {
          if (Number.isFinite(data[key])) {
            fieldsVal[key] = data[key].toString()
          } else if (dateKeys && dateKeys.includes(key) && data[key]) {
            fieldsVal[key] = moment(data[key], 'YYYY-MM-DD HH:mm:ss')
          } else {
            fieldsVal[key] = data[key]
          }
        }
      }
      frm.setFieldsValue(fieldsVal)
    }

    Vue.prototype.$exportData = (url, param, fileName, message) => {
      return exportServe
        .get(url, {
          params: param,
          responseType: 'blob'
        })
        .then(res => {
          if (res.data.size < 1024) {
            var reader = new FileReader()
            reader.readAsText(res.data)
            reader.onload = function () {
              if (reader.result && reader.result.indexOf('errorMessage') >= 0) {
                const result = JSON.parse(reader.result)
                message.error(result.errorMessage ? result.errorMessage : '异常错误，请稍后再试')
              } else {
                download(res, fileName)
              }
            }
          } else {
            download(res, fileName)
          }
          return Promise.resolve(res)
          // return new Promise((resolve, reject) => {
          //   resolve(res)
          // })
        })
    }

    Vue.prototype.$getBudgetDictionary = function () {
      return new Promise((resolve, reject) => {
        if (budgetDictionary.length > 0) {
          resolve(budgetDictionary)
        } else {
          return this.$http.get('/budget/getSysDictionaryModelist').then(res => {
            if (res.success && res.data) {
              budgetDictionary.push(...res.data)
              resolve(budgetDictionary)
            } else {
              reject(res)
            }
          })
        }
      })
    }

    Vue.prototype.$getFileTypes = function () {
      return new Promise((resolve, reject) => {
        if (fileTypes.length > 0) {
          resolve(fileTypes)
        } else {
          return this.$http.get('/rsProduct/getFileTypes').then(res => {
            if (res.success && res.data) {
              fileTypes.push(...res.data)
              resolve(fileTypes)
            } else {
              reject(res)
            }
          })
        }
      })
    }
    Vue.prototype.$getDailyReportCols = function () {
      return new Promise((resolve, reject) => {
        if (dailyReportCols.length > 0) {
          resolve(dailyReportCols)
        } else {
          return this.$http.get('/dailyReport/getCols').then(res => {
            if (res.success && res.data) {
              dailyReportCols.push(...res.data)
              resolve(dailyReportCols)
            } else {
              reject(res)
            }
          })
        }
      })
    }
    Vue.prototype.$checkFileSize = (file, message, maxSize = 10485760) => {
      if (file && file.size > maxSize) {
        message.error(`上传失败，您上传的附件大于${parseInt(maxSize / 1024 / 1024)}M！`)
        return false
      }
      return true
    }
    Vue.prototype.$notInDingTalk = notInDingTalk
    Vue.prototype.$companyJump = (self, params) => {
      self.spinning = true
      let tempwindow = null
      if (notInDingTalk) {
        tempwindow = window.open('about:blank')
      }
      self.$http.post('/company/jump', params).then(res => {
        let href
        if (res.success) {
          href = res.data
        } else {
          self.$message.error(res.errorMessage)
          href = `javascript:alert('${res.errorMessage}');`
        }
        if (tempwindow) {
          tempwindow.location.href = href
        } else {
          dd.biz.util.openLink({
            url: href, // 要打开链接的地址
            onSuccess: function (result) {
            /**/
            },
            onFail: function () {
            }
          })
        }
      }).finally(() => {
        self.spinning = false
      })
    }
    Vue.prototype.$getEnums = (enumName) => {
      return store.state.enums[enumName]
    }
  }
}
export default myPlugin
