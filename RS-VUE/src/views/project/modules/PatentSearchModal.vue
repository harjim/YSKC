<template>
  <a-modal
    :width="1480"
    :visible="visible"
    title="查询专利"
    :maskClosable="false"
    @cancel="close"
    @ok="addPatents"
    :confirm-loading="confirmLoading"
    :cancelButtonProps="{ disabled: cancelButtonDisabled }"
  >
    <a-form layout="inline" :form="form">
      <a-form-item label="申请人">
        <a-input style="width: 280px;" placeholder="请输入申请人" v-decorator="['companyName', {rules:[{required: true, message: '请输入申请人'}]}]" />
      </a-form-item>
      <a-form-item label="申请日期">
        <a-range-picker
          style="width: 248px;"
          format="YYYY-MM-DD"
          v-decorator="['applyDateRange', {rules:[{required: true, message: '请输入申请日期'}]}]"
        />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          @click="search"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <vxe-grid
      :data="records"
      resizable
      ref="pTable"
      height="530"
      @checkbox-all="selectCheckBoxChange"
      @checkbox-change="selectCheckBoxChange">
      <vxe-table-column type="checkbox" width="30" fixed="left"></vxe-table-column>
      <vxe-table-column type="seq" width="48" fixed="left"></vxe-table-column>
      <vxe-table-column field="patentNo" title="申请号" width="153" align="left" fixed="left">
        <template slot-scope="{row}"><span :title="row.an">{{ row.patentNo }}</span></template>
      </vxe-table-column>
      <vxe-table-column field="mainType" title="专利类型" width="88" align="left"></vxe-table-column>
      <vxe-table-column field="patentName" title="专利名称" width="305" align="left"></vxe-table-column>
      <vxe-table-column field="applyDateTime" title="申请日" width="100" align="left">
        <template slot-scope="{row}">{{ row.applyDateTime.replace('00:00:00','') }}</template>
      </vxe-table-column>
      <vxe-table-column field="authDate" title="授权日" width="100" align="left">
        <template slot-scope="{row}">{{ row.authDate.replace('00:00:00','') }}</template>
      </vxe-table-column>
      <vxe-table-column field="apply" title="申请(专利权)人" width="229" align="left"></vxe-table-column>
      <vxe-table-column field="inventor" title="发明人" width="210" align="left"></vxe-table-column>
      <vxe-table-column field="lsnt" title="法律信息" width="120" align="left"></vxe-table-column>
    </vxe-grid>
  </a-modal>
</template>
<script>
import { mapGetters, mapState } from 'vuex'
import { addHandler, readPatent } from '@/utils/util'
import moment from 'moment'
const source = 'search'
export default {
  data () {
    return {
      visible: false,
      records: [],
      patentWindow: undefined,
      form: this.$form.createForm(this),
      dateFormat: 'YYYY-MM-DD',
      confirmLoading: false,
      selectRecords: []
    }
  },
  mounted () {
    addHandler(source, d => {
      if (!d.hasError) {
        if (d.type === 'list') {
          this.records = d.result
          this.confirmLoading = false
        } else if (d.type === 'detail') {
          this.$http.post('/patentDetail/batchUpdate', d.result).then((res) => {
            if (res.success) {
              // this.confirmLoading = false
              this.visible = false
              this.$emit('ok', true)
            } else {
              this.$message.error(res.errorMessage)
            }
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      } else {
        this.$message.error(d.msg)
      }
    })
  },
  computed: {
    ...mapState(['currentYear']),
    ...mapGetters(['userInfo']),
    cancelButtonDisabled () {
      return this.selectRecords.length
    }
  },
  methods: {
    show () {
      this.records = []
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue({ companyName: this.userInfo.companyName, applyDateRange: [moment(`${this.currentYear}-01-01`, this.dateFormat), moment(`${this.currentYear}-12-31`, this.dateFormat)] })
      })
    },
    close () {
      this.visible = false
    },
    getWinStatus () {
      return this.patentWindow && this.patentWindow.closed === false
    },
    search () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          readPatent(source, 'list', this.getParams(values))
        }
      })
    },
    addPatents () {
      if (this.selectRecords.length > 10) {
        this.$message.warning('最多只能选择10项')
        return
      }
      const params = this.getParams(this.form.getFieldsValue())
      params.patentList = this.selectRecords
      this.confirmLoading = true
      readPatent(source, 'detail', params)
    },
    getParams (v) {
      return { companyName: v.companyName, applyDates: v.applyDateRange.map(a => a.format('YYYYMMDD')) }
    },
    selectCheckBoxChange ({ records }) {
      this.selectRecords = records
    }
  }
}
</script>
