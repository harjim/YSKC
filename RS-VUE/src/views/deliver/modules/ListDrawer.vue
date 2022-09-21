<template>
  <a-row :gutter="[0, 16]">
    <a-col :span="6">
      <a-tabs
        :activeKey="fileTabKey"
        @change="handleTabChange"
        tabPosition="left"
        size="small"
        setNextPrev="false"
      >
        <a-tab-pane
          v-for="(item, index) in filesList"
          :key="item.id"
          :tab="`${index+1}.${item.docName}`"
        />
      </a-tabs>
    </a-col>
    <a-col :span="18">
      <div v-if="isTech" class="preview_doc" v-html="previewHtml" />
      <vxe-grid
        v-else
        border
        stripe
        highlight-hover-row
        header-align="center"
        show-overflow="title"
        class="preview_doc"
        size="small"
        :data="tableData"
        :columns="tableColumns"
        :pager-config="{
          pageSize: tablePager.pageSize,
          currentPage: tablePager.currentPage,
          total: tablePager.total,
          layouts: ['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total'],
          autoHidden: true
        }"
        @page-change="handleChangePager"
      />
    </a-col>
  </a-row>
</template>

<script>
export default {
  name: 'ListDrawer',
  props: {
    fileTabKey: {
      type: Number,
      default: undefined
    },
    filesList: {
      type: Array,
      default: () => []
    },
    isTech: {
      type: Boolean,
      default: true
    },
    tableData: {
      type: Array,
      default: () => []
    },
    tableColumns: {
      type: Array,
      default: () => []
    },
    tablePager: {
      type: Object,
      default: () => ({
        pageSize: 10,
        currentPage: 1,
        total: 0
      })
    },
    previewHtml: {
      type: String,
      default: ''
    }
  },
  methods: {
    handleTabChange (key) {
      this.$emit('handleTabChange', key)
    },
    handleChangePager (page) {
      this.$emit('handleChangePager', page)
    }
  }
}
</script>

<style lang="less" scoped>
.preview_doc {
  height: calc(100vh - 354px);
  overflow: auto;
}

& /deep/ .ant-tabs .ant-tabs-left-bar .ant-tabs-tab {
  text-align: left;
}

& /deep/ .ant-tabs-ink-bar {
  background: rgba(0, 0, 0, 0);
}

& /deep/ .ant-tabs .ant-tabs-left-bar .ant-tabs-nav-wrap {
  height: 100%;
  overflow: auto;
}

& /deep/ .ant-tabs .ant-tabs-left-bar .ant-tabs-tab-prev, .ant-tabs .ant-tabs-right-bar
.ant-tabs-tab-prev {
  display: none;
}

& /deep/ .ant-tabs .ant-tabs-left-bar .ant-tabs-tab-next, .ant-tabs .ant-tabs-right-bar
.ant-tabs-tab-next {
  display: none;
}

& /deep/ .ant-tabs .ant-tabs-left-bar .ant-tabs-nav-container.ant-tabs-nav-container-scrolling,
.ant-tabs .ant-tabs-right-bar .ant-tabs-nav-container.ant-tabs-nav-container-scrolling {
  padding: 0;
}

& /deep/ .ant-tabs .ant-tabs-left-bar {
  border-right: none;
}
</style>
