import PreviewModal from './PreviewModal.js'

let previewInstance

const getPreviewInstance = (props) => {
  previewInstance = previewInstance || PreviewModal.newInstance(props)
  return previewInstance
}

function preview (props) {
  getPreviewInstance(props)
}

export default preview
