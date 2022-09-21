<template>
  <a-drawer :visible="visible" destroyOnClose title="添加服务申请" :width="1184" @close="close">
    <a-form-model ref="form" :model="form" :label-col="{ span: 6 }">
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="申请人" :label-col="{ span: 4 }">
            {{ userInfo.realName }}
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="所属部门">
            {{ deptTitle }}
          </a-form-model-item>
        </a-col>
      </a-row>

      <a-row>
        <a-col :span="12">
          <a-form-model-item label="技术人员" required :label-col="{ span: 4 }">
            <select-man
              placeholder="请输入技术人员"
              style="width:300px;"
              :type="1"
              keyField="tech"
              @blur="getBoss"
              @change="changeStateUser"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="财务人员" required>
            <select-man
              placeholder="请输入财务人员"
              style="width:300px;"
              :type="2"
              keyField="fina"
              @blur="getBoss"
              @change="changeStateUser"
            />
          </a-form-model-item>
        </a-col>
      </a-row>

      <a-row>
        <a-col :span="12">
          <a-form-model-item label="技术经理" :label-col="{ span: 4 }">
            {{ boss.techManagerName || '-' }}
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="财务经理">
            {{ boss.finaManagerName || '-' }}
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="技术总监" :label-col="{ span: 4 }">
            {{ boss.techDirectorName || '-' }}
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="财务总监">
            {{ boss.finaDirectorName || '-' }}
          </a-form-model-item>
        </a-col>
      </a-row>

      <a-row>
        <a-col :span="12">
          <a-form-model-item label="其他人员" :label-col="{ span: 4 }">
            <select-man
              placeholder="请输入其他人员"
              style="width:300px;"
              :type="5"
              keyField="other"
              @change="changeStateUser"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="预计起止日期" required>
            <date-range style="width: 300px;" @onChange="changeDateRange"/>
          </a-form-model-item>
        </a-col>
      </a-row>

      <a-form-model-item
        style="margin-top: 32px;"
        label="服务事项"
        :label-col="{ span: 2 }"
        :wrapper-col="{ span: 20 }">
        <ServiceEditTable ref="ServiceEditTable" :valid-rules="validRules">
          <vxe-table-column
            field="companyName"
            title="客户名称"
            minWidth="200"
            :edit-render="{ immediate: true }"
          >
            <template v-slot="{ row }">
              <select-company
                style="width: 100%"
                prop="companyName"
                @changeCompany="(data, option) => {
                  row.companyName = data
                  row.customerId = option.key
                }"
              />
            </template>
          </vxe-table-column>
          <vxe-table-column
            field="causes"
            title="事由"
            minWidth="200"
            :edit-render="{ immediate: true }"
          >
            <template v-slot="{ row }">
              <a-textarea
                placeholder="请输入事由"
                style="width: 100%;"
                v-model="row.causes"
                :auto-size="{ minRows: 1, maxRows: 5 }"
              />
            </template>
          </vxe-table-column>
        </ServiceEditTable>
      </a-form-model-item>

      <a-form-model-item label="备注" :label-col="{ span: 2 }">
        <a-textarea
          style="width: 1040px;margin-top: 8px;"
          placeholder="请输入"
          v-model="form.remark"
          :auto-size="{ minRows: 4, maxRows: 6 }"
        />
      </a-form-model-item>
    </a-form-model>

    <div
      :style="{
        position: 'absolute',
        right: 0,
        bottom: 0,
        width: '100%',
        height: '56px',
        borderTop: '1px solid #e9e9e9',
        padding: '20px 20px',
        background: '#fff',
        textAlign: 'right',
        zIndex: 1,
      }"
    >
      <a-button
        :style="{ marginRight: '8px' }"
        @click="handleSaveForm('/serviceApply/addServiceApply')"
      >
        暂存
      </a-button>
      <a-popconfirm
        title="是否确认提交?"
        :autoAdjustOverflow="false"
        placement="topRight"
        @confirm="handleSaveForm('/serviceApply/submit')"
      >
        <a-button type="primary">
          提交
        </a-button>
      </a-popconfirm>
    </div>
  </a-drawer>
</template>

<script>
import { mapGetters } from 'vuex'
import SelectMan from '@/components/Selects/SelectMan'
import moment from 'moment/moment'
import SelectCompany from '@/components/Selects/SelectCompany'
import { deepTree } from '@/utils/util'
import DateRange from '@/components/DateRange/DateRange'
import ServiceEditTable from '@/views/customer/modules/ServiceEditTable'

export default {
  name: 'ServiceOrderAdd',
  components: { ServiceEditTable, DateRange, SelectCompany, SelectMan },
  data () {
    return {
      visible: false,
      deptTitle: '',
      boss: {
        techManagerName: '',
        techDirectorName: '',
        finaManagerName: '',
        finaDirectorName: ''
      },
      form: {},
      customerList: [],
      validRules: {
        companyName: [{ required: true, message: '公司必须选择' }],
        causes: [{ required: true, message: '事由必须填写' }]
      }
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    moment,
    open () {
      this.getDeptTitle()
      this.visible = true
    },
    close () {
      Object.assign(this.$data, this.$options.data())
    },
    getDeptTitle () {
      if (this.userInfo.deptIds.length > 0) {
        this.$http.get('/dept/tree').then(({ success, data }) => {
          if (success) {
            this.deptTitle = this.deepTreeTitle(data, this.userInfo.deptIds[0])
          }
        })
      }
    },
    deepTreeTitle (data, value) {
      const temp = deepTree(data, value)
      return temp.title
    },
    changeStateUser (k, v) {
      if (v.length === 0) {
        this.boss[`${k}DirectorId`] = ''
        this.boss[`${k}DirectorName`] = ''
        this.boss[`${k}ManagerId`] = ''
        this.boss[`${k}ManagerName`] = ''
      }
      this.form[`${k}List`] = v.map(item => ({
        userId: item.key,
        userName: item.label
      }))
    },
    getBoss (k, v, t) {
      if (v === null) {
        this.boss[`${k}DirectorId`] = ''
        this.boss[`${k}DirectorName`] = ''
        this.boss[`${k}ManagerId`] = ''
        this.boss[`${k}ManagerName`] = ''
        return
      }
      const params = {
        userIds: v.map(i => i.key),
        userName: '',
        type: t
      }
      this.$http.get('/serviceApply/getMemberList', { params }).then(({ success, data, errorMessage }) => {
        if (success) {
          this.boss[`${k}DirectorId`] = data[`${k}DirectorId`]
          this.boss[`${k}DirectorName`] = data[`${k}DirectorName`]
          this.boss[`${k}ManagerId`] = data[`${k}ManagerId`]
          this.boss[`${k}ManagerName`] = data[`${k}ManagerName`]
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    changeDateRange (dates, dateStr) {
      this.form.begin = dateStr[0]
      this.form.end = dateStr[1]
      this.form.date = `${dateStr[0]}-${dateStr[1]}`
    },
    handleSaveForm (url) {
      if (!this.form.techList && !this.form.finaList) {
        this.$message.error('技术人员与财务人员至少选择一个')
        return
      }
      if (!this.form.begin || !this.form.end) {
        this.$message.error('请选择预计起止日期')
        return
      }
      const [flag, data] = this.$refs.ServiceEditTable.validAllEvent()
      if (!flag) return
      const params = {
        ...this.boss,
        ...this.form,
        customerList: data
      }
      this.$http.post(url, params).then(({ success, errorMessage }) => {
        if (success) {
          this.$message.success('操作成功')
          this.close()
          this.$emit('refresh')
        } else {
          this.$message.error(errorMessage)
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
& /deep/ .ant-form-item {
  margin-bottom: 0;
}
</style>
