import PreviewModal from './PreviewModal.vue'
import Vue from 'vue'

PreviewModal.newInstance = properties => {
  const props = properties || {}
  const Instance = new Vue({
    data: props,
    render (h) {
      return h(PreviewModal, { props })
    }
  })

  const component = Instance.$mount()
  document.body.appendChild(component.$el)
}

export default PreviewModal
