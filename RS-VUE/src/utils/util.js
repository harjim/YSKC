import moment from 'moment'
import store from '../store'
import { isUndefined } from 'lodash'
import message from 'ant-design-vue/es/message'

const handlerMap = {}
window.addEventListener('message', (e) => {
  if (e.data && e.data.source) {
    if (handlerMap[e.data.source]) {
      handlerMap[e.data.source](e.data)
    }
  }
})

let patentWindow
export function readPatent (source, type, params) {
  if (patentWindow && patentWindow.closed === false) {
    patentWindow.postMessage({ origin: location.origin, source, type, params }, 'https://www.baiten.cn/')
  } else {
    message.info('检测到未从当前界面打开佰腾网,待打开佰腾后再点击获取数据', 3).then(() => {
      patentWindow = window.open('https://www.baiten.cn/')
    })
  }
}

export function addHandler (source, handler) {
  handlerMap[source] = handler
}

export function timeFix () {
  const time = new Date()
  const hour = time.getHours()
  return hour < 9 ? '早上好' : hour <= 11 ? '上午好' : hour <= 13 ? '中午好' : hour < 20 ? '下午好' : '晚上好'
}

export function welcome () {
  const arr = ['休息一会儿吧', '准备吃什么呢?', '要不要打一把 DOTA', '我猜你可能累了']
  const index = Math.floor(Math.random() * arr.length)
  return arr[index]
}

/**
 * 触发 window.resize
 */
export function triggerWindowResizeEvent () {
  const event = document.createEvent('HTMLEvents')
  event.initEvent('resize', true, true)
  event.eventType = 'message'
  window.dispatchEvent(event)
}

export function handleScrollHeader (callback) {
  let timer = 0

  let beforeScrollTop = window.pageYOffset
  callback = callback || function () { }
  window.addEventListener(
    'scroll',
    event => {
      clearTimeout(timer)
      timer = setTimeout(() => {
        let direction = 'up'
        const afterScrollTop = window.pageYOffset
        const delta = afterScrollTop - beforeScrollTop
        if (delta === 0) {
          return false
        }
        direction = delta > 0 ? 'down' : 'up'
        callback(direction)
        beforeScrollTop = afterScrollTop
      }, 50)
    },
    false
  )
}

/**
 * Remove loading animate
 * @param id parent element id or class
 * @param timeout
 */
export function removeLoadingAnimate (id = '', timeout = 1500) {
  if (id === '') {
    return
  }
  setTimeout(() => {
    document.body.removeChild(document.getElementById(id))
  }, timeout)
}

/**
 * @description  字符串里面的（字符|字符串）替换为指定（字符|字符串）
 * @param char 替换源字符, type: string|char
 * @param targetChar  替换目标字符, type: string|char, default: ''
 * @param sourceString  操作字符串, type: string
 * @param location  替换方式, value: [ 'first', 'last', 'firstAndLast', 'firstOrLast', 'all' ], type: string, default: 'first'
 * @return string  没有char或sourceString放回源字符串
 */
export function replaceChar (char, targetChar = '', sourceString, location = 'first') {
  if (!(char || sourceString)) {
    return sourceString
  }
  sourceString.trim()
  let regExp = `^${char}.*`
  let handleRegExp = `^${char}`
  switch (location) {
    case 'last':
      regExp = `.*${char}$`
      handleRegExp = `${char}$`
      break
    case 'firstAndLast':
      regExp = `^${char}.*${char}$`
      handleRegExp = `^${char}|${char}$`
      break
    case 'firstOrLast':
      regExp = `^${char}|${char}$`
      handleRegExp = `^${char}|${char}$`
      break
    case 'all':
      regExp = `${char}`
      handleRegExp = `${char}`
      break
    default:
      regExp = `^${char}.*`
      break
  }
  const reg = new RegExp(regExp, 'ig')
  const handleReg = new RegExp(handleRegExp, 'ig')
  if (reg.test(sourceString)) {
    return sourceString.replace(handleReg, targetChar)
  } else {
    return sourceString
  }
}
/**
 * @description: 补零
 * @param {*} value
 * @param {*} length
 * @return {*}
 */
export function zeroFormat (value, length = 2) {
  return value <= 9 ? (Array(length).join('0') + value).slice(-length) : value
}
/**
 * @description: 给表单赋值
 * @param {*} form 表单对象
 * @param {*} data 数据
 * @param {*} dateKeys ary 日期的可以
 * @return {*}
 */
export function initForm (form, data, dateKeys) {
  const fieldsVal = form.getFieldsValue()
  deepInitData(fieldsVal, data, dateKeys)
  form.setFieldsValue(fieldsVal)
}
function deepInitData (formObj, obj, dateKeys) {
  if (formObj !== null && typeof formObj === 'object' && !Array.isArray(formObj)) {
    for (const key in formObj) {
      if (Object.hasOwnProperty.call(formObj, key)) {
        const value = obj[key]
        if (!isUndefined(value)) {
          if (value !== null && typeof value === 'object' && !Array.isArray(value)) {
            deepInitData(formObj[key], value, dateKeys)
          } else if (Number.isFinite(value)) {
            formObj[key] = value.toString()
          } else if (dateKeys && dateKeys.includes(key)) {
            formObj[key] = value ? moment(value, 'YYYY-MM-DD HH:mm:ss') : undefined
          } else {
            formObj[key] = value
          }
        }
      }
    }
  }
}
export function getAuth (prefix, ary) {
  if (!ary.length) return
  const returnObject = {}
  const authMap = store.getters.userInfo.permDataMap
  ary.forEach((key) => {
    const authValue = !!authMap[`${prefix}${key}`]
    returnObject[key] = authValue
  })
  return returnObject
}
// 判断值是否为空
export function isEmpty (value) {
  const type = Object.prototype.toString.apply(value).slice(8, -1)
  switch (type) {
    case 'String':
    case 'Array':
    case 'Object':
      return Object.keys(value).length === 0
    default:
      return value == null
  }
}
// 动态加载css
export function loadStyle (url) {
  var link = document.createElement('link')
  link.type = 'text/css'
  link.rel = 'stylesheet'
  link.href = url // css链接
  var head = document.getElementsByTagName('head')[0]
  head.appendChild(link)
}
