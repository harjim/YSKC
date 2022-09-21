<template>
  <a-modal v-model="visible" title="引入项目" :width="600" @ok="ok">
    <vxe-grid
      auto-resize
      stripe
      :max-height="400"
      :data="list"
      :loading="loading"
      :columns="columns"
      @checkbox-all="selectAllEvent"
      @checkbox-change="selectChangeEvent"
    />
  </a-modal>
</template>

<script>
export default {
  name: 'QuickAddCheckDrawer',
  data() {
    const columns = [
      { type: 'checkbox', width: '60', align: 'center' },
      { type: 'seq', width: '60', align: 'center' },
      { title: '项目编号', field: 'rdTitle', width: '100' },
      { title: '项目名称', field: 'pname' }
    ]
    return {
      columns,
      visible: false,
      list: [],
      loading: false,
      selectedRow: []
    }
  },
  methods: {
    ok() {
      this.$emit('select', this.selectedRow)
      this.visible = false
    },
    open(params) {
      this.visible = true
      this.getProjectList(params)
    },
    getProjectList(params) {
      this.loading = true
      this.$http
        .get('checkPayment/getProjectList', { params })
        .then(({ success, data, errorMessage }) => {
          if (success) {
            this.list = data
          } else {
            this.$message.error(errorMessage)
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
    selectChangeEvent({ records }) {
      this.selectedRow = records
    },
    selectAllEvent({ records }) {
      this.selectedRow = records
    }
  }
}
</script>

<style lang="scss" scoped></style>
