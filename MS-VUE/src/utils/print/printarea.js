export default class {
  constructor (option) {
    this.standards = {
      strict: 'strict',
      loose: 'loose',
      html5: 'html5'
    }
    this.selectArray = [] // 存储select的
    this.counter = 0
    this.settings = {
      standard: this.standards.html5,
      extraHead: '', // 附加在head标签上的额外元素,使用逗号分隔
      extraCss: '', // 额外的css逗号分隔
      popTitle: '', // 标题
      endCallback: null, // 成功打开后的回调函数
      ids: '' // 局部打印的id
    }
    Object.assign(this.settings, option)
    this.init()
  }
  // TODO 初始化
  init () {
    // console.log('init')
    this.counter++
    this.settings.id = `printArea_${this.counter}`
    const PrintAreaWindow = this.getPrintWindow() // 创建iframe
    this.write(PrintAreaWindow.doc) // 写入内容
    this.print(PrintAreaWindow)
    this.settings.endCallback()
  }
  print (PAWindow) {
    const paWindow = PAWindow.win
    const _loaded = () => {
      paWindow.focus()
      paWindow.print()
      try {
        const box = document.getElementById(this.settings.id)
        const canvasList = this.elsdom.querySelectorAll('.canvasImg')
        // console.log(this.elsdom)
        for (let i = 0; i < canvasList.length; i++) {
          const _parent = canvasList[i].parentNode
          _parent.removeChild(canvasList[i])
        }
        box.parentNode.removeChild(box)
      } catch (e) {
        console.log(e)
      }
    }
    if (window.ActiveXObject) {
      paWindow.onload = _loaded()
      return false
    }
    paWindow.onload = () => {
      _loaded()
    }
  }
  write (PADocument, $ele) {
    PADocument.open()
    PADocument.write(`${this.docType()}<html>${this.getHead()}${this.getBody()}</html>`)
    PADocument.close()
  }
  // TODO 获取样式
  docType () {
    if (this.settings.standard === this.standards.html5) {
      return '<!DOCTYPE html>'
    }
    var transitional = this.settings.standard === this.standards.loose ? ' Transitional' : ''
    var dtd = this.settings.standard === this.standards.loose ? 'loose' : 'strict'

    return `<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01${transitional}//EN" "http://www.w3.org/TR/html4/${dtd}.dtd">`
  }
  // TODO 获取头Head
  getHead () {
    let extraHead = ''
    let links = ''
    let style = ''
    if (this.settings.extraHead) {
      this.settings.extraHead.replace(/([^,]+)/g, (m) => {
        extraHead += m
      })
    }
    // TODO 复制所有link标签
    const domLink = document.querySelectorAll('link')
    // console.log(domLink)
    Array.from(domLink).forEach((item, i) => {
      if (item.href.indexOf('.css') > 0) {
        links += `<link type="text/css" rel="stylesheet" href="${item.href}" >`
      }
    })
    // TODO 循环获取style标签的样式
    const domStyle = document.styleSheets
    // console.log(domStyle)
    if (domStyle && domStyle.length > 0) {
      for (let i = 0; i < domStyle.length; i++) {
        try {
          if (domStyle[i].cssRules || domStyle[i].rules) {
            const rules = domStyle[i].cssRules || domStyle[i].rules
            for (let b = 0; b < rules.length; b++) {
              style += rules[b].cssText
            }
          }
        } catch (e) {
          console.log(domStyle[i].href + e)
        }
      }
    }

    if (this.settings.extraCss) {
      this.settings.extraCss.replace(/([^,\s]+)/g, (m) => {
        links += `<link type="text/css" rel="stylesheet" href="${m}">`
      })
    }

    return `<head><title>${this.settings.popTitle}</title>${extraHead}${links}<style type="text/css">${style}</style></head>`
  }
  // TODO 获取body
  getBody () {
    let ids = this.settings.ids
    ids = ids.replace(new RegExp('#', 'g'), '')
    this.elsdom = this.beforeHanler(document.getElementById(ids))
    // console.log('starts', this.elsdom)
    this.elsdom = this.handlePagingFun(this.elsdom)
    const ele = this.getFormData(this.elsdom)
    const htm = ele.outerHTML
    // console.log('htm', htm)
    // console.log('body', '<body>' + htm + '</body>')
    return '<body>' + htm + '</body>'
  }
  // 克隆节点之前做的操作
  beforeHanler (elsdom) {
    const canvasList = elsdom.querySelectorAll('canvas')
    // canvas转换png图片
    for (let i = 0; i < canvasList.length; i++) {
      if (!canvasList[i].style.display) {
        const _parent = canvasList[i].parentNode
        const _canvasUrl = canvasList[i].toDataURL('image/png')
        const _img = new Image()
        _img.className = 'canvasImg'
        _img.style.display = 'none'
        _img.src = _canvasUrl
        // _parent.replaceChild(_img, canvasList[i])
        _parent.appendChild(_img)
      }
    }
    return elsdom
  }
  // 根据type去处理form表单
  getFormData (ele) {
    const copy = ele.cloneNode(true)
    const copiedInputs = copy.querySelectorAll('input,select,textarea')
    const canvasImgList = copy.querySelectorAll('.canvasImg,canvas')
    let selectCount = -1
    // 处理所有canvas
    for (let i = 0; i < canvasImgList.length; i++) {
      const _parent = canvasImgList[i].parentNode
      const item = canvasImgList[i]
      // 删除克隆后的canvas节点
      if (item.tagName.toLowerCase() === 'canvas') {
        _parent.removeChild(item)
      } else {
        item.style.display = 'block'
      }
    }
    // 处理所有输入框
    for (let i = 0; i < copiedInputs.length; i++) {
      const item = copiedInputs[i]
      let typeInput = item.getAttribute('type')
      const copiedInput = copiedInputs[i]
      // 获取select标签
      if (!typeInput) {
        typeInput = item.tagName === 'SELECT' ? 'select' : item.tagName === 'TEXTAREA' ? 'textarea' : ''
      }
      // 处理input框
      if (item.tagName === 'INPUT') {
        // 除了单选框 多选框比较特别
        if (typeInput === 'radio' || typeInput === 'checkbox') {
          copiedInput.setAttribute('checked', item.checked)
          //
        } else {
          copiedInput.value = item.value
          copiedInput.setAttribute('value', item.value)
        }
        // 处理select
      } else if (typeInput === 'select') {
        selectCount++
        for (let b = 0; b < ele.querySelectorAll('select').length; b++) {
          const select = ele.querySelectorAll('select')[b] // 获取原始层每一个select
          !select.getAttribute('newbs') && select.setAttribute('newbs', b) // 添加标识
          if (select.getAttribute('newbs') === selectCount) {
            const opSelectedIndex = ele.querySelectorAll('select')[selectCount].selectedIndex
            item.options[opSelectedIndex].setAttribute('selected', true)
          }
        }
        // 处理textarea
      } else {
        copiedInput.innerHTML = item.value
        copiedInput.setAttribute('html', item.value)
      }
    }

    return copy
  }
  getPrintWindow () {
    var f = this.Iframe()
    return {
      f: f,
      win: f.contentWindow || f,
      doc: f.doc
    }
  }
  // TODO 创建iframe
  Iframe () {
    const frameId = this.settings.id
    let iframe
    const that = this
    try {
      iframe = document.createElement('iframe')
      document.body.appendChild(iframe)
      iframe.style.border = '0px'
      iframe.style.position = 'absolute'
      iframe.style.width = '0px'
      iframe.style.height = '0px'
      iframe.style.right = '0px'
      iframe.style.top = '0px'
      iframe.setAttribute('id', frameId)
      iframe.setAttribute('src', new Date().getTime())
      iframe.doc = null
      iframe.doc = iframe.contentDocument ? iframe.contentDocument : (iframe.contentWindow ? iframe.contentWindow.document : iframe.document)
      iframe.onload = function () {
        var win = iframe.contentWindow || iframe
        that.print(win)
      }
    } catch (e) {
      throw new Error(e + '. iframes may not be supported in this browser.')
    }

    if (iframe.doc == null) {
      throw new Error('Cannot find document.')
    }
    return iframe
  }
  // TODO 处理每页数据函数
  handlePagingFun (wrapperContent) {
    const pageHeader = wrapperContent.querySelector('#pageHeader')
    const pageFooter = wrapperContent.querySelector('#pageFooter')
    const printContent = wrapperContent.querySelector('#printContent')
    if (!pageHeader || !pageFooter || !printContent) {
      return wrapperContent
    }
    const printContentBK = printContent.cloneNode(true)
    // TODO 测试虚拟DOM打印高度  start
    // console.log('printContentBK', printContentBK)
    // const el = printContentBK.children
    // Array.from(el).forEach(item => {
    //   console.log(item.clientHeight)
    // })
    // end
    printContent.style.width = '194mm'
    const ElementList = printContent.children // 获取元素集合
    let computedHeight = 0 // 用于计算高度
    let contentHeight = 1015 // 内容最大高度
    let count = 0 // 计算次数
    Array.from(ElementList).forEach((el, index) => {
      if (el.getAttribute('cover')) {
        const tbody = el.querySelector('tbody')
        const tr = tbody.insertRow(0)
        tr.style.height = '200px'
        count += 1
        computedHeight = 0
      } else {
        count === 0 ? contentHeight = 1065 : contentHeight = 1015 // 第一页高度，要高一点，具体问题待查
        const tempHeight = computedHeight // 临时存储总高度，用于计算要补的高度
        computedHeight += el.clientHeight // 页面累加总高度
        if (computedHeight > contentHeight) { // 累加高度大于页面高度，就上一个元素的高度，填补当页页面的剩余高度
          count++
          const buGao = contentHeight - tempHeight // 计算出要补的高度
          // TODO表格打印
          const isSpliceTable = el.getAttribute('spliceTable')
          if (el.nodeName === 'TABLE' && isSpliceTable) {
            const obj = this.spliceTable(el, buGao)
            el.replaceWith(obj.el)
            computedHeight = obj.remanent
          } else {
            const previousEl = el.previousElementSibling // 获取当前元素的上一个元素
            previousEl.style.height = (previousEl.clientHeight + buGao) + 'px' // 设置高度
            previousEl.style.pageBreakAfter = 'always' // 给上一个元素添加换行符
            this.insertBefore(printContent, el) // 插入一个页面的空白位置
            computedHeight = el.clientHeight // 将当前的元素高度，保存累加高度
          }
          // const previousEl = el.previousElementSibling // 获取当前元素的上一个元素
          // previousEl.style.height = (previousEl.clientHeight + buGao) + 'px' // 设置高度
          // previousEl.style.pageBreakAfter = 'always' // 给上一个元素添加换行符
          // this.insertBefore(printContent, el) // 插入一个页面的空白位置
          // computedHeight = el.clientHeight // 将当前的元素高度，保存累加高度
        }
      }
    })
    // 还原显示页面样式
    const resultDom = printContent.cloneNode(true)
    printContent.replaceWith(printContentBK)
    // 重新创建一个Dom,用于打印
    const wrapperDiv = document.createElement('div')
    wrapperDiv.appendChild(pageHeader.cloneNode(true))
    // console.log(this.createDiv())
    wrapperDiv.appendChild(this.createDiv())
    wrapperDiv.appendChild(resultDom)
    wrapperDiv.appendChild(pageFooter.cloneNode(true))
    // console.log('wrapperDiv', wrapperDiv)
    return wrapperDiv
  }
  // 创建一个div
  createDiv (height = 55) {
    const div = document.createElement('div')
    div.style.height = height + 'px'
    return div
  }
  // 插入
  insertBefore (parentEl, targetEl) {
    parentEl.insertBefore(this.createDiv(), targetEl)
  }
  // 判断时表格元素
  isTable (el) {
    return el.nodeName === 'TABLE'
  }
  crateNewTable (el, buGaoHeight, contentHeight) {
    const copyRootTable = el.cloneNode(false)
    const tbody = document.createElement('tbody')
    const trs = el.getElementsByTagName('tr')
    let computedHeight = 0 // 用于计算高度
    // let height = crateHeight
    Array.from(trs).forEach((tr, index) => {
      computedHeight += tr.clientHeight // 页面累加总高度
      if (computedHeight > buGaoHeight) {
        return copyRootTable.appendChild(tbody)
      }
      tbody.appendChild(tr)
    })
    // table.appendChild(tbody)
    // return table
  }
  // TODO 分割表格
  /**
   * @description:  分割表格
   * @param {Element} el
   * @param {Number} buGaoHeight
   * @return {Object} obj
   */
  spliceTable (el, buGaoHeight) {
    const returnObj = {}
    const contentHeight = 1015
    const tableHeight = el.clientHeight // 表格高度
    const TableRemanentHeight = tableHeight - buGaoHeight // 表格剩余高度
    const remanent = TableRemanentHeight % contentHeight // 最后一页表格余出来的
    const pageRemanentHeight = contentHeight - remanent // 最后页剩余高度
    returnObj.pageRemanentHeight = pageRemanentHeight // 保存页高度
    returnObj.remanent = remanent // 最后使用的内容高度
    returnObj.el = document.createElement('div')
    let copyRootTable = el.cloneNode(false)
    let tbody = document.createElement('tbody')
    const trs = el.getElementsByTagName('tr')
    let computedHeight = 0 // 用于计算高度
    Array.from(trs).forEach((tr, index) => {
      computedHeight += tr.clientHeight // 页面累加总高度
      if (computedHeight > buGaoHeight) {
        computedHeight = 0
        buGaoHeight = contentHeight
        copyRootTable.appendChild(tbody)
        copyRootTable.style.pageBreakAfter = 'always'
        returnObj.el.appendChild(copyRootTable)
        returnObj.el.appendChild(this.createDiv())
        copyRootTable = el.cloneNode(false)
        tbody = document.createElement('tbody')
      }
      tbody.appendChild(tr)
    })
    if (tbody.children.length) {
      // console.log(copyRootTable)
      copyRootTable.appendChild(tbody)
      returnObj.el.appendChild(copyRootTable)
    }
    return returnObj
  }
}
