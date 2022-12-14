import store from '@/store/index'
import ExportJsonExcel from '@/utils/js-export-excel'
import { exportServe } from '@/utils/request'
import moment from 'moment'
import { debounce } from 'lodash'
const cachedCode = {}
const highMap = {}
const salaryConfig = {}
const treeObj = {
  dept: { tree: undefined, keyMap: undefined, url: '/dept/tree' },
  rdDept: { url: '/rdDept/queryRdDeptTree' }, //  year: { tree: undefined, keyMap: undefined }
  workshop: { tree: undefined, keyMap: undefined, url: '/workshop/getWorkshopTree' },
  account: { tree: undefined, keyMap: undefined, url: '/accountTitle/accountTrees' }
}
const fileTypes = []
function setKeyMap (tree, allKey, fullPath, minValue) {
  tree.forEach(t => {
    if (t.parentId < minValue) {
      allKey[t.value] = ''
    } else {
      allKey[t.value] = `${fullPath ? fullPath + '/' : ''}${t.title}`
    }
    if (t.children && t.children.length > 0) {
      return setKeyMap(t.children, allKey, allKey[t.value], minValue)
    }
  })
  return allKey
}
function setHighMap (tree) {
  tree.forEach(t => {
    highMap[t.key] = `${t.value}`
    if (t.children && t.children.length > 0) {
      return setHighMap(t.children)
    }
  })
}
function setSalaryConfig (configs) {
  salaryConfig.salary = []
  salaryConfig.insurance = []
  Object.keys(configs).forEach(key => {
    salaryConfig[key] = JSON.parse(configs[key].config)
  })
}

function download (res, fileName) {
  const link = document.createElement('a')
  const blob = new Blob([res.data], { type: 'application/vnd.ms-excel' })
  link.style.display = 'none'
  link.href = URL.createObjectURL(blob)
  let finallyFilename
  if (fileName) {
    finallyFilename = fileName
  } else {
    finallyFilename = res.headers['filename'] ? decodeURI(res.headers['filename'].replace(/\+/g, '%20')) : null
  }
  if (finallyFilename) {
    link.setAttribute('download', finallyFilename)
  }
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}
const myPlugin = {
  install: Vue => {
    Vue.prototype.$initForm = (frm, data, dateKeys) => {
      const fieldsVal = frm.getFieldsValue()
      for (const key in fieldsVal) {
        if (typeof data[key] !== 'undefined') {
          if (Number.isFinite(data[key])) {
            fieldsVal[key] = data[key].toString()
          } else if (dateKeys && dateKeys.includes(key)) {
            fieldsVal[key] = data[key] ? moment(data[key], 'YYYY-MM-DD HH:mm:ss') : undefined
          } else {
            fieldsVal[key] = data[key]
          }
        }
      }
      frm.setFieldsValue(fieldsVal)
    }
    Vue.prototype.$getDictionary = function (type) {
      return new Promise((resolve, reject) => {
        if (cachedCode[type] && cachedCode[type].length > 0) {
          resolve(cachedCode[type])
        } else {
          return this.$http
            .get(type === 6 ? '/stage/getCStage' : '/sysDictionary/getDictionary', { params: { type: type } })
            .then(res => {
              if (res.success) {
                const { data } = res
                if (type === 6) {
                  for (let i = 0; i < data.length; i++) {
                    data[i].key = data[i].stageKey
                    data[i].value = data[i].stageName
                  }
                }
                cachedCode[type] = res.data
                resolve(res.data)
              } else {
                reject(res)
              }
            })
        }
      })
    }
    Vue.prototype.$addressCode = self => {
      return new Promise((resolve, reject) => {
        if (self.$store.state.sysDictionary.addressCode.length === 0) {
          self.$store.dispatch('GetAddressCode').then(res => {
            resolve(res)
          })
        } else {
          resolve(self.$store.state.sysDictionary.addressCode)
        }
      })
    }
    Vue.prototype.$highTecIndustry = self => {
      return new Promise((resolve, reject) => {
        if (self.$store.state.sysDictionary.highTecIndustry.length === 0) {
          self.$store.dispatch('GetHighTecIndustry').then(res => {
            resolve(res)
          })
        } else {
          resolve(self.$store.state.sysDictionary.highTecIndustry)
        }
      })
    }
    Vue.prototype.$highTecIndustryTree = self => {
      return new Promise((resolve, reject) => {
        if (self.$store.state.sysDictionary.highTecIndustryTree.length === 0) {
          self.$store.dispatch('GetHighTecIndustryTree').then(res => {
            resolve(res)
          })
        } else {
          resolve(self.$store.state.sysDictionary.highTecIndustryTree)
        }
      })
    }
    Vue.prototype.$highTecIndustryTreeMap = self => {
      return new Promise((resolve, reject) => {
        if (Object.keys(highMap).length) {
          return resolve(highMap)
        }
        if (self.$store.state.sysDictionary.highTecIndustryTree.length) {
          setHighMap(self.$store.state.sysDictionary.highTecIndustryTree)
          resolve(highMap)
        } else {
          self.$store.dispatch('GetHighTecIndustryTree').then(res => {
            setHighMap(res)
            resolve(highMap)
          })
        }
      })
    }
    /**
     * @description ???????????????
     * @param {Function} func ?????????????????????
     * @param {Number} time ?????????????????????
     */
    Vue.prototype.$debounce = (func, time = 500) => {
      return debounce(func, time, {
        leading: true,
        trailing: false
      })
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
                message.error(result.errorMessage ? result.errorMessage : '??????????????????????????????')
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
    Vue.prototype.$exportJson = (fileName, datas) => {
      var option = {}
      option.fileName = fileName
      option.datas = datas
      var toExcel = new ExportJsonExcel(option)
      toExcel.saveExcel()
    }
    Vue.prototype.$exportJsonData = (
      fileName,
      sheetHeader,
      sheetFilter,
      sheetData,
      columnWidths,
      merges,
      multiHeader,
      sheetHeaderList
    ) => {
      var option = {}
      option.fileName = fileName
      option.datas = [
        {
          sheetHeaderList: sheetHeaderList || [],
          sheetData: sheetData,
          sheetFilter: sheetFilter,
          sheetName: fileName,
          sheetHeader: sheetHeader,
          columnWidths: columnWidths || [],
          merges: merges || [],
          multiHeader: multiHeader || []
        }
      ]
      var toExcel = new ExportJsonExcel(option)
      toExcel.saveExcel()
    }
    // ??????????????????????????????????????????????????????
    Vue.prototype.$getMonthDay = monthStr => {
      var month = moment(monthStr + '-01')
      var returnDays = []
      var currentMonthDays = month.daysInMonth() // ??????????????????
      for (var i = 0; i < currentMonthDays; i++) {
        var item = {}
        item.index = i + 1 // ???????????????
        item.length = currentMonthDays // ?????????????????????
        item.date = moment(monthStr + '-' + (item.index < 10 ? '0' + item.index : item.index)) // ????????????
        var weekDay = item.date.format('E')
        switch (weekDay) {
          case '1':
            item.weekDayStr = '??????' // ?????????????????????
            break
          case '2':
            item.weekDayStr = '??????'
            break
          case '3':
            item.weekDayStr = '??????'
            break
          case '4':
            item.weekDayStr = '??????'
            break
          case '5':
            item.weekDayStr = '??????'
            break
          case '6':
            item.weekDayStr = '??????'
            break
          default:
            item.weekDayStr = '??????'
            break
        }
        item.weekDay = weekDay
        if (weekDay === '6' || weekDay === '7') {
          item.isWeekDay = true // ????????????
        } else {
          item.isWeekDay = false
        }
        returnDays.push(item)
      }
      return returnDays
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
    Vue.prototype.$getExcelHeadTitle = function (i) {
      if (i < 26) {
        return String.fromCharCode(65 + i)
      } else {
        var a = parseInt(i / 26)
        var b = i % 26
        return this.$getExcelHeadTitle(a - 1) + this.$getExcelHeadTitle(b)
      }
    }
    Vue.prototype.$getTree = function (type, refresh, year) {
      return new Promise((resolve, reject) => {
        var obj = treeObj[type]
        const isRdDept = type === 'rdDept'
        if (isRdDept) {
          if (!year) {
            year = this.$store.state.currentYear
          }
          obj = obj[year]
        }
        if (!refresh && obj && obj.tree) {
          resolve(obj)
        } else {
          obj = {}
          let url = treeObj[type].url
          if (isRdDept) {
            url += `?year=${year}`
          }
          this.$http.get(url).then(res => {
            if (res.success) {
              if (res.data && res.data.length > 0) {
                obj.tree = res.data
                var keyMap = {}
                setKeyMap(res.data, keyMap, '', type === 'dept' ? 0 : -1)
                obj.keyMap = keyMap
              } else {
                obj.tree = []
                obj.keyMap = {}
              }
              if (isRdDept) {
                const temp = treeObj[type]
                temp[year] = obj
                treeObj[type] = temp
              } else {
                obj.url = url
                treeObj[type] = obj
              }
              resolve(obj)
            } else {
              reject(res)
            }
          })
        }
      })
    }
    Vue.prototype.$getCostTypes = () => {
      return [
        { value: 100, title: '??????', type: 10000 },
        { value: 101, title: '????????????', type: 10100 },
        { value: 300, title: '????????????', type: 30000 },
        { value: 301, title: '????????????', type: 30100 },
        // { value: 302, title: '????????????', type: 30200 },
        { value: 200, title: '??????', type: 20000 },
        { value: 201, title: '??????', type: 20100 },
        { value: 202, title: '??????', type: 20200 },
        { value: 203, title: '??????', type: 20300 },
        { value: 205, title: '??????', type: 20500 },
        { value: 206, title: '??????', type: 20600 },
        { value: 207, title: '??????', type: 20700 },
        { value: 500, title: '??????', type: 50000 },
        { value: 400, title: '????????????', type: 40000 },
        { value: 401, title: '????????????', type: 40100 },
        { value: 402, title: '????????????', type: 40200 },
        { value: 604, title: '?????????', type: 60400 },
        { value: 699, title: '??????', type: 69900 }
      ]
    }
    Vue.prototype.$getYearCostTypes = () => {
      return [
        { value: 100, title: '??????', type: 10000 },
        { value: 101, title: '????????????', type: 10100 },
        { value: 200, title: '??????', type: 20000 },
        { value: 201, title: '??????', type: 20100 },
        { value: 202, title: '??????', type: 20200 },
        { value: 203, title: '??????', type: 20300 },
        { value: 205, title: '??????', type: 20500 },
        { value: 206, title: '??????', type: 20600 },
        { value: 207, title: '??????', type: 20700 },
        { value: 300, title: '????????????', type: 30000 },
        { value: 400, title: '????????????', type: 40000 },
        { value: 402, title: '????????????', type: 40200 },
        { value: 500, title: '??????', type: 50000 },
        { value: 699, title: '??????', type: 69900 }
      ]
    }
    Vue.prototype.$getFileTypes = function () {
      return new Promise((resolve, reject) => {
        if (fileTypes.length > 0) {
          resolve(fileTypes)
        } else {
          return this.$http.get('/techProject/getFileTypes').then(res => {
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
    Vue.prototype.$checkFileSize = (file, message, maxSize = 10485760) => {
      if (file && file.size > maxSize) {
        message.error(`???????????????????????????????????????${parseInt(maxSize / 1024 / 1024)}M???`)
        return false
      }
      return true
    }
    Vue.prototype.$getSalaryConfig = (self, isRefresh = false) => {
      return new Promise((resolve) => {
        if (Object.keys(salaryConfig).length && !isRefresh) {
          return resolve(salaryConfig)
        } else {
          self.$http.get('/salary/getSalaryConfig').then(res => {
            if (res.success) {
              setSalaryConfig(res.data)
            } else {
              setSalaryConfig({ insurance: [], salary: [] })
            }
          }).finally(() => {
            resolve(salaryConfig)
          })
        }
      })
    }
    Vue.prototype.$getEnums = (name) => {
      return store.state.enums[name]
    }
  }
}
export default myPlugin
