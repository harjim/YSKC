<template>
  <span style="display: flex;align-items: center;">
    <i v-if="required" class="required-field" />{{ text }}
    <a-tooltip :getPopupContainer="getPopupContainer" :placement="placement">
      <a-icon style="font-size: 12px;margin-left: 2px" type="info-circle" />
      <template slot="title"><pre style="white-space: pre-line;">{{ title || text }}</pre></template>
    </a-tooltip>
  </span>
</template>

<script>
export default {
  name: 'YsTooltip',
  props: {
    title: { // 提示内容
      type: String,
      default: ''
    },
    text: { // 文本内容
      type: String,
      default: ''
    },
    required: { // 是否必填
      type: Boolean,
      default: false
    },
    getPopupContainer: {
      type: Function,
      default: () => document.body
    },
    placement: {
      type: String,
      default: 'top'
    }
  }
}
</script>

<style scoped>

</style>
