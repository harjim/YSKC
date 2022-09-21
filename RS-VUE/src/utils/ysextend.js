import Dialog from 'ant-design-vue/es/vc-dialog/Dialog'
const dm = Dialog.mounted
Dialog.mounted = function () {
  dm.apply(this)
  if (typeof this.$attrs.drag === 'undefined' || this.$attrs.drag) {
    const el = this.$el
    const modal = el.getElementsByClassName('ant-modal')[0]
    const header = el.getElementsByClassName('ant-modal-header')[0]
    let left = 0
    let top = 0
    // top 初始值为 offsetTop
    top = top || modal.offsetTop
    header.onmousedown = e => {
      const startX = e.clientX
      const startY = e.clientY
      header.left = header.offsetLeft
      header.top = header.offsetTop
      el.onmousemove = event => {
        const endX = event.clientX
        const endY = event.clientY
        modal.left = header.left + (endX - startX) + left
        modal.top = header.top + (endY - startY) + top
        modal.style.left = modal.left + 'px'
        modal.style.top = modal.top + 'px'
      }
      el.onmouseup = event => {
        left = modal.left
        top = modal.top
        el.onmousemove = null
        el.onmouseup = null
        header.releaseCapture && header.releaseCapture()
      }
      header.setCapture && header.setCapture()
    }
  }
}
