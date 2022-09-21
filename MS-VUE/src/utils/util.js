import store from '../store'

export function timeFix () {
  const time = new Date()
  const hour = time.getHours()
  return hour < 9 ? '早上好' : hour <= 11 ? '上午好' : hour <= 13 ? '中午好' : hour < 20 ? '下午好' : '晚上好'
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
  callback = callback || function () {
  }
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
 * @description  description: 字符串里面的（字符|字符串）替换为指定（字符|字符串）
 * @param char  description: 替换源字符, type: string|char
 * @param targetChar  description: 替换目标字符, type: string|char, default: ''
 * @param sourceString  description: 操作字符串, type: string
 * @param location  description：替换方式, value: [ 'first', 'last', 'firstAndLast', 'firstOrLast', 'all' ], type: string, default: 'first'
 * @return string  description：没有char或sourceString放回源字符串, success: 成功的字符串, error: 'error'字符串
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

export function deepClone (target) {
  /**
   * 遍历数据处理函数
   * @array 要处理的数据
   * @callback 回调函数，接收两个参数 value 每一项的值 index 每一项的下标或者key。
   */
  function handleWhile (array, callback) {
    const length = array.length
    let index = -1
    while (++index < length) {
      callback(array[index], index)
    }
  }

  function clone (target, map) {
    if (target !== null && typeof target === 'object') {
      const result = Object.prototype.toString.call(target) === '[object Array]' ? [] : {}
      // 解决循环引用
      if (map.has(target)) {
        return map.get(target)
      }
      map.set(target, result)
      const keys = Object.prototype.toString.call(target) === '[object Array]' ? undefined : Object.keys(target)

      // eslint-disable-next-line no-inner-declarations
      function callback (value, key) {
        if (keys) {
          key = value// 如果keys存在则说明value是一个对象的key，不存在则说明key就是数组的下标。
        }
        result[key] = clone(target[key], map)
      }

      handleWhile(keys || target, callback)
      return result
    } else {
      return target
    }
  }

  let map = new WeakMap()
  const result = clone(target, map)
  map = null
  return result
}

/**
 * @description: 防抖
 * @param {*} fun
 * @param {*} delay
 * @return {*}
 */
export function debounce (fun, delay) {
  return function (args) {
    const that = this
    const _args = args
    clearTimeout(fun.id)
    fun.id = setTimeout(function () {
      fun.call(that, _args)
    }, delay)
  }
}

/**
 * @description: 节流
 * @param {*} fun
 * @param {*} delay
 * @return {*}
 */
export function throttle (fun, delay) {
  let last, deferTimer
  return function (args) {
    const that = this
    const _args = args
    const now = +new Date()
    if (last && now < last + delay) {
      clearTimeout(deferTimer)
      deferTimer = setTimeout(function () {
        last = now
        fun.apply(that, _args)
      }, delay)
    } else {
      last = now
      fun.apply(that, _args)
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

export const deepTree = (data, value) => {
  for (let i = 0; i < data.length; i++) {
    const item = data[i]
    if (+item.value === value) return item
    if (item.children && item.children.length) {
      const temp = deepTree(item.children, value)
      if (temp) return temp
    }
  }
}
