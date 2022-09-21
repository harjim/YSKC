import { startDrag } from '@/utils/drag'

/**
 * 给 antd modal 增加拖拽功能
 * @param el 指定 DOM
 * @param binding 绑定对象
 * 使用了 a-modal 的组件都可通过 v-drag 来添加拖拽功能
 */
const drag = (el, binding) => {
  startDrag(el.querySelector('.ant-modal-header'), el.querySelector('.ant-modal-content'), binding.value)
}

export default {
  drag
}
