<!--
 * @Author: ldx
 * @Date: 2021-02-03 11:20:44
 * @LastEditTime: 2021-07-15 10:25:21
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\modules\AuditFooter.vue
-->
<template>
  <a-row >
    <a-col :span="8">
      <a-form-item label="编制" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
        <select-employee
          :mountDom="domName"
          :selected="footer.toCompile"
          :year="year"
          :isRdAndCommittee="true"
          @select="toCompile=> OnSelect('toCompile',toCompile)"
        />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item label="审核" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
        <select-employee
          :mountDom="domName"
          :selected="footer.audit"
          :year="year"
          :isRdAndCommittee="true"
          @select="audit=> OnSelect('audit',audit)"
        />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item label="批准" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
        <select-employee
          :mountDom="domName"
          :selected="footer.approval"
          :year="year"
          :isRdAndCommittee="true"
          @select="approval=> OnSelect('approval',approval)"
        />
      </a-form-item>
    </a-col>
  </a-row>
</template>

<script>
import SelectEmployee from '@/components/SelectEmployee'
import { domName } from '@/docTemplate/Templates/js/screenFullMountDom'
// import { setDocFooter } from '@/api/docTemplate/index'
import { setDocFooter, getDocFooter } from '@/api/docTemplate/index'
export default {
  name: 'AuditFooter',
  components: {
    SelectEmployee
  },
  props: {
    year: {
      type: Number,
      default: 0
    },
    projectId: {
      type: Number,
      default: undefined
    },
    docId: {
      type: Number,
      default: undefined
    },
    employeeMap: {
      type: Object,
      default: () => {}
    }
  },
  watch: {
    docId (val) {
      if (val > 0) {
        // this.handleGetDocFooter(this.projectId, this.year)
        Object.assign(this.footer, this.employeeMap)
      }
    },
    employeeMap: {
      deep: true,
      immediate: true,
      handler (val) {
        Object.assign(this.footer, val)
      }
    }
  },
  mounted () {
    // this.handleGetDocFooter(this.projectId)
    Object.assign(this.footer, this.employeeMap)
    // this.$emit('refresh')
  },
  data () {
    return {
      domName,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      footer: {
        toCompile: undefined,
        audit: undefined,
        approval: undefined
      },
      postContent: {
        projectId: this.projectId
      }
    }
  },
  methods: {
    OnSelect (field, selectedObject) {
      if (selectedObject) {
        this.footer[field] = selectedObject
        this.postContent[field + 'Enumber'] = selectedObject.enumber
        this.postContent[field] = selectedObject.ename
      } else {
        this.footer[field] = undefined
        this.postContent[field + 'Enumber'] = ''
        this.postContent[field] = ''
      }
      this.postContent['year'] = this.year
      setDocFooter(this.postContent).then((resolve, reject) => {
        if (resolve.success && resolve.data) {}
      }).catch((error) => {
        this.$message.error('操作失败')
        console.log('接口：/projectDocFileData/setDocFooter调用失败：' + error)
      })
    },
    handleGetDocFooter (projectId, year) {
      this.footer = {
        toCompile: undefined,
        audit: undefined,
        approval: undefined
      }
      if (!projectId || !year) {
        return
      }
      getDocFooter({ projectId, year }).then((resolve, reject) => {
        if (resolve.success && resolve.data) {
          Object.assign(this.footer, resolve.data)
        }
      }).catch((error) => {
        this.$message.error(error)
        console.log('Interface:/projectDocFileData/getDocFooter, error:' + error)
      })
    }
  }
}
</script>

<style lang='less' scoped>

</style>
