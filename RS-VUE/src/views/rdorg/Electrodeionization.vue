<!--
 * @Author: -
 * @Date: -
 * @LastEditors: zdf
 * @LastEditTime: 2022-01-12 10:42:05
 * @Description: 技术中心简介
 * @FilePath: \RS-VUE\src\views\rdorg\Electrodeionization.vue
-->
<template>
  <!-- <div style="height: calc(100% - 60px); overflow-y: auto; overflow-x: hidden"> -->
  <div style="height: calc(100% - 60px); overflow: auto;" v-if="$auth('rdorg:arch:techDesc:view')">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form-item
        class="zoneSpacing"
        :labelCol="{span: 1}"
        :wrapperCol="{span:20}"
        :help="()=>{return `(${!techIntro? 0 : techIntro.length > 2000 ? 2000 : techIntro.length}/2000)`}"
      >
        <a-textarea rows="20" v-model="techIntro" :maxLength="2000" placeholder="技术中心简介" :readOnly="!modify"></a-textarea>
      </a-form-item>
      <div class="table-operator" v-if="$auth('rdorg:arch:techDesc:save')">
        <a-button type="primary" @click="handleSubmit" v-if="modify">保存</a-button>
        <a-button type="primary" @click="exportData" v-if="$auth('rdorg:arch:techDesc:export') && techIntro != null">导出</a-button>
      </div>
    </a-spin>
  </div>
</template>
<script>
import yearMixin from '@/utils/yearMixin'
import { isEditStatus } from '@/utils/processDoc/auditStatus'
import { mapGetters } from 'vuex'
export default {
  mixins: [yearMixin],
  data () {
    return {
      spinning: false,
      techIntro: '',
      modify: false
    }
  },
  created () {
    this.search()
  },
  watch: {
    auditStatus: {
      // immediate: true,
      handler (val) {
        this.modify = this.isEditStatus(val) || !this.userInfo.userSource
      }
    }
  },
  computed: {
    ...mapGetters(['auditStatus', 'userInfo'])
  },
  mounted () {
    this.modify = this.isEditStatus(this.auditStatus) || !this.userInfo.userSource
  },
  methods: {
    isEditStatus,
    search () {
      this.$nextTick(() => {
        this.$http.get('/report/getTechIntro', { params: { year: this.currentYear } })
          .then(res => {
            this.techIntro = res.data
          })
      })
    },
    handleSubmit () {
      this.spinning = true
      if (this.techIntro === null || this.techIntro === '') {
        this.$message.info('请填写数据')
        this.spinning = false
        return
      }
      this.$http.post('/report/saveTechIntro', { techIntro: this.techIntro, ryear: this.currentYear })
        .then(res => {
          if (res.success && res.data) {
            this.$emit('ok', true)
            this.$message.success('保存成功')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
        }).finally(res => {
          this.spinning = false
        })
    },
    exportData (row) {
      this.$exportData('/report/exportData', { ryear: this.currentYear }, undefined, this.$message)
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
    }
  }
}
</script>
<style lang="less" scoped>
.zoneSpacing {
  margin-bottom: 12px;
}
</style>
