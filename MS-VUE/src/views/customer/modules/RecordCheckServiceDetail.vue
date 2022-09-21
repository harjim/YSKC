<template>
  <a-modal
    v-drag
    :visible="visible"
    :title="`服务单号：${serviceNo}`"
    :footer="null"
    :mask="false"
    destroyOnClose
    :width="600"
    @cancel="close">
    <a-descriptions :column="2" layout="horizontal">
      <a-descriptions-item label="技术人员">
        {{ detail.techList && detail.techList.map(i => i.userName).join(', ') }}
      </a-descriptions-item>
      <a-descriptions-item label="财务人员">
        {{ detail.finaList && detail.finaList.map(i => i.userName).join(', ') }}
      </a-descriptions-item>
      <a-descriptions-item label="技术经理">
        {{ detail.techManagerName || '-' }}
      </a-descriptions-item>
      <a-descriptions-item label="财务经理">
        {{ detail.finaManagerName || '-' }}
      </a-descriptions-item>
      <a-descriptions-item label="技术总监">
        {{ detail.techDirectorName || '-' }}
      </a-descriptions-item>
      <a-descriptions-item label="财务总监">
        {{ detail.finaDirectorName || '-' }}
      </a-descriptions-item>
      <a-descriptions-item label="其他人员">
        {{ detail.otherList && detail.otherList.map(i => i.userName).join(', ') }}
      </a-descriptions-item>
      <a-descriptions-item label="预计起止日期">
        {{ detail.date }}
      </a-descriptions-item>
      <a-descriptions-item :span="2" label="服务事项">
        <div style="width: 460px;">
          <vxe-table
            resizable
            stripe
            size="small"
            auto-resize
            :data="detail.customerList"
            show-overflow>
            <vxe-table-column type="seq" width="60" title="序号"/>
            <vxe-table-column field="companyName" title="客户名称"/>
            <vxe-table-column field="causes" title="事由"/>
          </vxe-table>
        </div>
      </a-descriptions-item>
      <a-descriptions-item label="备注">
        <span v-if="detail.remark" v-html="detail.remark.replace(/\n/g, '<br/>')"></span>
        <span v-else>-</span>
      </a-descriptions-item>
    </a-descriptions>
  </a-modal>
</template>

<script>
export default {
  name: 'RecordCheckServiceDetail',
  components: {},
  props: {
    serviceNo: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      visible: false,
      detail: {}
    }
  },
  methods: {
    open () {
      this.visible = true
      this.getDetail()
    },
    getDetail () {
      this.$http.get('serviceRecord/getApplyInfo', {
        params: {
          serviceNo: this.serviceNo
        }
      }).then(({ success, data, errorMessage }) => {
        if (success) {
          this.detail = data
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    close () {
      Object.assign(this.$data, this.$options.data())
    }
  }
}
</script>
