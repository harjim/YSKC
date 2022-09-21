/**
 * 拖拽移动
 * @param {HTMLElement} bar 鼠标按下的元素
 * @param {HTMLElement} target 拖拽的目标元素
 * @param {function} callback 移动后回调
 */
export const startDrag = (bar, target, callback) => {
  let params = {
    top: 0,
    left: 0,
    currentX: 0,
    currentY: 0,
    flag: false,
    cWidth: 0,
    cHeight: 0,
    tWidth: 0,
    tHeight: 0
  }
  bar.style.cursor = 'move'// 给拖块添加样式

  bar.onmousedown = event => {
    const e = event || window.event
    if (e.path[0].nodeName === 'BUTTON') return
    params = {
      top: target.offsetTop,
      left: target.offsetLeft,
      currentX: e.clientX,
      currentY: e.clientY,
      flag: true,
      cWidth: document.body.clientWidth,
      cHeight: document.body.clientHeight,
      tWidth: target.offsetWidth,
      tHeight: target.offsetHeight
    }

    target.style.margin = '0'
    target.style.top = params.top + 'px'
    target.style.left = params.left + 'px'

    document.onmousemove = event => {
      // 防止文字选中
      window.getSelection() ? window.getSelection().removeAllRanges() : document.selection.empty()

      const e = event || window.event
      if (params.flag) {
        const nowX = e.clientX
        const nowY = e.clientY
        // 差异距离
        const disX = nowX - params.currentX
        const disY = nowY - params.currentY
        // 最终移动位置
        let zLeft = 0
        let zTop = 0

        zLeft = parseInt(params.left) + disX
        // 限制 X 轴范围
        // if (zLeft <= -parseInt(params.tWidth) / 2) {
        //   zLeft = -parseInt(params.tWidth) / 2
        // }
        if (zLeft >= params.cWidth - parseInt(params.tWidth) * 0.5) {
          zLeft = params.cWidth - parseInt(params.tWidth) * 0.5
        }

        zTop = parseInt(params.top) + disY
        // // 限制 Y 轴范围
        // if (zTop <= -parseInt(params.tHeight) / 2) {
        //   zTop = -parseInt(params.tHeight) / 2
        // }
        if (zTop >= params.cHeight - parseInt(params.tHeight) * 0.5) {
          zTop = params.cHeight - parseInt(params.tHeight) * 0.5
        }

        // 执行移动
        target.style.left = zLeft + 'px'
        target.style.top = zTop + 'px'
        if (typeof callback === 'function') {
          callback(zLeft, zTop)
        }
      }
      document.onmouseup = () => {
        params.flag = false
        document.onmousemove = null
        document.onmouseup = null
      }
    }
  }
}
