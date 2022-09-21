<template>
  <a-tooltip
    placement="top"
    :overlayStyle="{maxWidth: '1000px'}"
  >
    <template slot="title">
      <a-card :title="title">
        <a-table
          size="middle"
          :columns="columns"
          bordered
          :rowKey="rowKey"
          :pagination="false"
          :dataSource="dataSource"
        >
        </a-table>
      </a-card>
    </template>
    <span>{{ text }}</span>
  </a-tooltip>
</template>
<script>
export default {
  name: 'TooltipTable',
  props: {
    title: {
      type: String,
      required: true
    },
    rowKey: {
      type: String,
      required: true
    },
    columns: {
      type: Array,
      required: true
    },
    dataSource: {
      type: Array,
      required: true
    },
    text: {
      type: String,
      required: true
    }
  },
  computed: {
  },
  data () {
    return {
      form: this.$form.createForm(this)
    }
  }
}
</script>

<style>
</style>
