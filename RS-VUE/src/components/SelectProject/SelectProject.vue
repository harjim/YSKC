<!--
 * @Author: zdf
 * @Date: 2020-07-23 14:23:08
 * @LastEditTime: 2022-05-21 09:52:13
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \RS-VUE\src\components\SelectProject\SelectProject.vue
-->
<template>
  <div id="wrap">
    <a-form-item label="项目" >
      <a-select style="float:left" :style="{width:width+'px' }" :value="projectId" @select="projectSelect">
        <a-select-option v-for="item in projectList" :key="item.id">
          <span
            v-if="item!=null"
          >{{ item.rdTitle }} - {{ item.pname }}</span>
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-alert
      v-if="errorMsg!==''"
      :message="errorMsg"
      type="warning"
      style="margin-left:41px"
      :style="{width:width+'px' }"
    />
  </div>
</template>

<script>
export default {
  props: {
    year: {
      type: Number,
      required: true
    },
    sign: { // （默认：所有项目 1：rd项目（parentId=0） 2：子项目（hasChild=false）
      type: Number,
      default: 0,
      required: true
    },
    width: {
      type: Number,
      default: 600
    }
  },
  name: 'SelectProject',
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 2 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 22 }
      },
      projectList: [],
      projectId: undefined,
      projectMap: {},
      errorMsg: ''
    }
  },
  watch: {
    year (newYear) {
      this.projectId = undefined
      this.year = newYear
      this.loadProject()
    }
  },
  mounted () {
    this.loadProject()
  },
  methods: {
    projectSelect (value) {
      this.projectId = value
      this.$emit('projectSelect', value, this.projectMap[this.projectId])
    },
    loadProject () {
      if (!this.year) {
        return
      }
      return this.$http.get('/project/getSelectList', { params: { year: this.year, sign: this.sign } })
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.projectList = res.data
            this.projectId = res.data[0].id
            for (let index = 0; index < res.data.length; index++) {
              const element = res.data[index]
              this.$set(this.projectMap, element.id, element)
            }
            this.$emit('projectSelect', this.projectId, this.projectMap[this.projectId])
            this.errorMsg = ''
            return this.projectList
          } else {
            // this.$message.warning('当前年份：' + this.year + '没有项目，请重新选择年份')
            this.errorMsg = '当前年份：' + this.year + '没有项目，请重新选择年份'
            this.projectList = []
            this.projectId = undefined
            this.$emit('projectSelect', 0, '')
          }
        })
    }
  }
}
</script>
<style lang="less" scoped>
  #wrap /deep/ .ant-form-item {
    display: flex;
    align-items: center;
  }
</style>
