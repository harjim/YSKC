<template>
  <a-drawer
    :visible="visible"
    :title="title"
    :bodyStyle="{
      height: 'calc(100% - 55px)',
      padding: 12,
      overflow: 'auto',
    }"
    @close="handleClose"
    width="1000"
    destroyOnClose
  >
    <a-spin :spinning="spinning">
      <div style="height: 100%">
        <a-row :gutter="[0, 16]">
          <a-col :span="6">
            <a-tabs
              :activeKey="fileTabKey"
              @change="handleTabChange"
              tabPosition="left"
              size="small"
              class="drawerSideBar"
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
      </div>

      <a-row :gutter="[0, 16]">
        <a-col :span="24">
          <a-tabs
            id="audit-tabs"
            size="small"
            style="overflow: auto;"
            :animated="false"
            :tabBarStyle="{ marginBottom: '10px' }"
            :activeKey="auditTabKey"
            @change="handleChangeAuditTab"
          >
            <a-tab-pane v-if="isShowAudit && filesList.length > 0" key="audit" tab="??????">
              <a-form :form="form">
                <a-form-item>
                  <a-textarea
                    v-decorator="['suggestion', {
                      rules: [{ required: true, whitespace: true, min: 5, message: '?????????5????????????????????????'}],
                      initialValue: ''
                    }]"
                    placeholder="?????????????????????"
                  />
                </a-form-item>
                <a-form-item :label-col="{ span: 24 }" :wrapper-col="{ span: 8, offset: 11 }">
                  <a-popconfirm
                    title="??????????????????????????????"
                    ok-text="??????"
                    cancel-text="??????"
                    @confirm="handleSubmit(false)"
                  >
                    <a-button size="small" style="margin-right: 20px">??????</a-button>
                  </a-popconfirm>
                  <a-popconfirm
                    title="??????????????????????????????"
                    ok-text="??????"
                    cancel-text="??????"
                    @confirm="handleSubmit(true)"
                  >
                    <a-button size="small">??????</a-button>
                  </a-popconfirm>
                </a-form-item>
              </a-form>
            </a-tab-pane>
            <a-tab-pane key="log" tab="??????">
              <a-table
                size="small"
                :pagination="false"
                :columns="columns"
                :data-source="logList"
                :rowKey="record => record.logTime"
              />
            </a-tab-pane>
          </a-tabs>
        </a-col>
      </a-row>
    </a-spin>
  </a-drawer>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'DetailDrawer',
  props: {
    title: {
      type: String,
      default: ''
    },
    visible: {
      type: Boolean,
      default: false
    },
    spinning: {
      type: Boolean,
      default: false
    },
    previewHtml: {
      type: String,
      default: ''
    },
    filesList: {
      type: Array,
      default: () => []
    },
    logList: {
      type: Array,
      default: () => []
    },
    fileTabKey: {
      type: Number,
      default: undefined
    },
    auditTabKey: {
      type: String,
      default: ''
    },
    isShowAudit: {
      type: Boolean,
      default: false
    },
    isTech: {
      type: Boolean,
      default: true
    },
    tableColumns: {
      type: Array,
      default: () => []
    },
    tableData: {
      type: Array,
      default: () => []
    },
    tablePager: {
      type: Object,
      default: () => { }
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  data () {
    return {
      columns: [
        { title: '????????????', dataIndex: 'companyName', align: 'center' },
        { title: '??????', dataIndex: 'node', align: 'center', customRender: txt => txt === 0 ? '???????????????' : (txt === 1 ? '?????????' : (txt === 10 ? '???????????????' : '???????????????')) },
        { title: '??????', dataIndex: 'status', align: 'center', customRender: txt => txt === 1 ? '??????' : '??????' },
        { title: '?????????', dataIndex: 'auditUser', align: 'center' },
        { title: '????????????', dataIndex: 'suggestion', align: 'center' },
        { title: '????????????', dataIndex: 'logTime', align: 'center' }
      ],
      form: this.$form.createForm(this, { name: 'audit_form' })
    }
  },
  methods: {
    handleTabChange (key) {
      this.$emit('tabChange', key)
    },
    handleSubmit (status) {
      this.form.validateFields((err, val) => {
        if (!err) {
          this.$emit('submitAudit', {
            status: status ? 1 : 2,
            suggestion: val.suggestion
          })
        }
      })
    },
    handleChangeAuditTab (key) {
      this.form.resetFields()
      this.$emit('changeAuditTabKey', key)
    },
    handleClose () {
      this.form.resetFields()
      this.$emit('close')
    },
    handleChangePager ({ currentPage, pageSize }) {
      this.$emit('pagerChange', { currentPage, pageSize })
    }
  }
}
</script>

<style lang="less" scoped>
.drawerSideBar {
 height: calc(100vh - 372px);
 max-height: 424px;
//  min-height: 37px;
}
.preview_doc {
  height: calc(100vh - 372px);
  overflow: auto;
  // min-height: 424px;
}

& /deep/ .ant-tabs .ant-tabs-left-bar .ant-tabs-tab {
  text-align: left;
}
& /deep/ .ant-tabs-ink-bar {
  background: rgba(0, 0, 0, 0);
}

#audit-tabs {
  /deep/ .ant-tabs-extra-content {
    float: left !important;
  }
  /deep/ .ant-tabs-nav-container {
    display: flex;
    flex-direction: row-reverse;
  }
}
</style>
