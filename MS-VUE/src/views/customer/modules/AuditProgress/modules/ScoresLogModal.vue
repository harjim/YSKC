<template>
  <a-modal
    v-drag
    :width="params.type === 6 ? '80%' : '50%'"
    :title="title"
    :zIndex="1100"
    :mask="false"
    :getContainer="popupContainer"
    :footer="null"
    :maskClosable="false"
    :keyboard="false"
    :visible.sync="visible"
    @cancel="$emit('close', isRefresh)"
    :style="{ top: `${top}px`, left: `${left}px` }">
    <scores-log :params="params" :visible="visible" :is-refresh.sync="isRefresh" />
  </a-modal>
</template>

<script>
import { popupContainer } from '@/views/customer/modules/AuditProgress/modules/js/screenFullMountDom'
import ScoresLog from '@/views/customer/modules/AuditProgress/modules/ScoresLog'

export default {
  name: 'ScoresLogModal',
  components: { ScoresLog },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    params: {
      type: Object,
      default: () => ({})
    },
    title: {
      type: String,
      default: ''
    }
  },
  watch: {},
  data () {
    return {
      isRefresh: false,
      top: 0,
      left: 0
    }
  },
  methods: { popupContainer }
}
</script>

<style scoped>
</style>
