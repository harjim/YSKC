<!--
    * @Author: hm
    * @Date: 2022-09-06 18:32:30
    * @Description: 财务日志抽屉
-->
<template>
  <a-drawer
    :visible="visible"
    :title="title"
    @close="closeModal"
    width="960"
    destroyOnClose
  >
    <!-- currentOrder.instanceId -->
    <tab-layout :class="!showLog ? 'defaultCss' : 'not-log'">
      <template #up>
        <div style="height: 100%; overflow: auto;">
          <!-- 基本信息部分 -->
          <EditBaseDaily ref="editBaseDaily" :form="form" @handleOk="handleOk" :reasons="$attrs.reasons" />
        </div>
      </template>
      <template #down>
        <template
          v-if="showLog"
        >
          <!-- 审核部分 -->
          <FinaDailyDetailAudit :record="form" @refresh="handleOk" />
        </template>
      </template>
    </tab-layout>

    <!-- 底部按钮 handleSubmit -->
    <div id="bottom_button" v-if="(isAdd || isEditStatus(form.status)) && $auth('innovation:finaDaily:edit')" >
      <a-popconfirm
        title="是否确认暂存？"
        @confirm="handleSave()"
        @cancel="saveVisible = false"
        :visible="saveVisible"
      >
        <a-button :style="{ marginRight: '8px' }" @click="validate(false)" :loading="saveLoading">暂存</a-button>
      </a-popconfirm>
      <a-popconfirm
        title="是否确认提交？"
        @confirm="handleSubmit()"
        @cancel="submitVisible = false"
        :visible="submitVisible"
        placement="topRight"
      >
        <a-button type="primary" @click="validate(true)" :loading="submitLoading">提交</a-button>
      </a-popconfirm>
    </div>
  </a-drawer>
</template>

<script>
import EditBaseDaily from './finaDailyModules/EditBaseDaily'
import TabLayout from '@/components/LayoutAll/TabLayout'
import FinaDailyDetailAudit from './finaDailyModules/FinaDailyDetailAudit'
import { isEditStatus } from '@/utils/processDoc/auditStatus'
export default {
  data () {
    return {
      visible: false,
      isAdd: true,
      form: {},
      saveLoading: false,
      submitLoading: false,
      saveVisible: false,
      submitVisible: false,
      title: null,
      auth: {
        audit: this.$auth('innovation:finaDaily:audit'),
        review: this.$auth('innovation:finaDaily:review')
      }
    }
  },
  components: {
    TabLayout,
    EditBaseDaily,
    FinaDailyDetailAudit
  },
  computed: {
    showLog () {
      return (this.auth.audit || this.auth.review) && !this.isAdd && this.submitStatus(this.form.status)
    }
  },
  methods: {
    isEditStatus,
    /**
     * 判断当前财务日志是否为未提交状态
     * @param {Number} status 当前财务日志状态
     */
    submitStatus (status) {
      return status !== null && status !== undefined && status !== 5
    },
    /** 判断当前状态
     * @status row===null 添加
     * @status isEditStatus(row.status) === true 添加
     * @status isEditStatus(row.status) !== true 查看
     */
    open (row) {
      this.isAdd = row === null
      this.form = row
      this.title = `${this.isAdd ? '添加' : this.isEditStatus(row.status) ? '编辑' : '查看'}财务日报`
      this.visible = true
      if (row) {
        // 初始化
        this.$nextTick(() => {
          this.$refs.editBaseDaily.fromInit(row)
        })
      }
    },
    /** 表单校验 */
    validate (submit) {
      const error = this.$refs.editBaseDaily.validate()
      if (!error) {
        if (submit) {
          this.submitVisible = true
        } else {
          this.saveVisible = true
        }
      }
    },
    /** 提交 */
    handleSubmit (flag) {
      // 提交
      this.submitVisible = false
      this.submitLoading = true
      this.$refs.editBaseDaily.handleSubmit('/finaDaily/submitFinaDaily').finally(() => {
        this.submitLoading = false
      })
    },
    /** 暂存 */
    handleSave () {
      /** 添加或编辑 */
      this.saveVisible = false
      const url = `/finaDaily/${this.isAdd ? 'addFinaDaily' : 'editFinaDaily'}`
      this.saveLoading = true
      this.$refs.editBaseDaily.handleSubmit(url).finally(() => {
        this.saveLoading = false
      })
    },
    closeModal () {
      this.visible = false
      // this.$refs.editBaseDaily.handleClose()
    },
    /** 提交信息后关闭抽屉并刷新界面, 添加刷新至第一页，编辑刷新当前页, 审核与刷新相同 */
    handleOk (flag = this.isAdd) {
      this.closeModal()
      this.$emit('submit', flag)
    }
  }
}
</script>

<style lang='less' scoped>
  /deep/ .showEdit {
    row-gap: 29px;
  }
  /deep/ .onlyShow {
    row-gap: 0;
  }
/deep/ .ant-form-inline {
  // 网格布局
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  // row-gap: 19px;
  justify-items: stretch;

  .ant-form-item-control-wrapper {
    width: 79%;
  }

  // 跨行内容样式
  .ant-form-item:first-child, .ant-form-item:last-child, .ant-form-item:nth-last-child(2) {
    grid-column: span 2/ span 2;
    margin-left: -119px;

    .ant-form-item-control-wrapper {

      .ant-form-item-children {
        width: 100%;

        .ant-select-enabled {
          width: 100% !important;
        }

      }
    }
  }

  // 下拉选择器样式
  .ant-select-enabled {
    width: 100% !important;
  }
  // 日期选择器样式
  .ant-calendar-picker {
    width: 100% !important;
  }
}

/deep/ #bottom_button {
  position: absolute;
  right: 0;
  bottom: 0;
  width: 100%;
  border-top: 1px solid #e9e9e9;
  padding: 10px 16px;
  background: #fff;
  text-align: right;
  z-index: 1;
}

// /deep/ .ant-drawer-body {
//   height: calc(100vh - 83px);
// }
/deep/ .defaultCss .up {
  height: calc(100vh - 136px);
}
/deep/ .not-log .center_wrap {
  height: calc(100vh - 134px) !important;
}
/deep/ .defaultCss .midden {
  display: none;
}
</style>
