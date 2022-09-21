<template>
  <a-select
    :style="{ width }"
    :mode=" isMul ? 'multiple' : 'default'"
    :placeholder="placeholder"
    :filter-option="filterOption"
    option-label-prop="label"
    @select="onSelect"
    @deselect="onDeSelect"
    @change="onChange"
    v-model="ids"
    :allowClear="allowClear"
    :disabled="disabled"
  >
    <template v-if="isJoinPrjectName">
      <a-select-option
        v-for="item in projects"
        :title="item.pname"
        :key="item.id"
        :token-separators="[',']"
        :label="item.rdTitle + ' - ' + item.pname"
      >{{ item.rdTitle }} - {{ item.pname }}</a-select-option>
    </template>
    <template v-else>
      <a-select-option
        v-for="item in projects"
        :title="item.pname"
        :key="item.id"
        :token-separators="[',']"
        :label="item.rdTitle"
      >{{ item.rdTitle }} - {{ item.pname }}</a-select-option>
    </template>
  </a-select>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  props: {
    year: {
      type: Number,
      required: true
    },
    isMul: {
      type: Boolean,
      default: false
    },
    width: {
      type: String,
      default: '220px'
    },
    placeholder: {
      type: String,
      default: '请选择项目'
    },
    selected: {
      type: [Array, String, Number],
      default: undefined
    },
    value: {
      type: [Array, String, Number],
      default: undefined
    },
    sign: { // 0:默认所有项目 1:RD项目 2:子项目
      type: Number,
      required: true,
      default: 0
    },
    isDefault: { // 是否默认显示第一个
      type: Boolean,
      default: false
    },
    isJoinPrjectName: { // 显示是否加上项目名称
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    allowClear: {
      type: Boolean,
      default: false
    },
    isFilter: {
      type: Boolean,
      default: false
    },
    filterField: {
      type: Object,
      default: () => undefined
    }
  },
  data () {
    return {
      projects: [],
      model: 'multiple',
      errorMsg: '',
      ids: [],
      currentProject: {},
      projectMap: {}
    }
  },
  mounted () { this.loadProject(this.year).then(this.initProjectData) },
  destroyed () { this.setProjectInfo(undefined) },
  watch: {
    year (value) { this.loadProject(value).then(this.initProjectData) },
    selected (value) { this.refreshSelectde(value) },
    value (v) { this.refreshSelectde(v) },
    filterField: {
      deep: true,
      handler () {
        this.loadProject(this.year).then(this.initProjectData)
      }
    }
  },
  methods: {
    ...mapActions(['setProjectInfo']),
    loadProject (year) {
      if (!year || year <= 0) {
        console.log('loadProject function error : year is not undefined')
        this.resetValue()
        return Promise.resolve(false)
      }
      this.projectMap = {}
      return this.$http.get('/project/getSelectList', { params: { year: this.year, sign: this.sign } })
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.projects = res.data
            if (this.isFilter) {
              this.projects = this.handlerFilter(res.data)
            }
            this.projects.map(item => { this.projectMap[item.id] = item })
            if (this.isDefault) {
              this.ids = this.projects[0].id
              this.currentProject = this.projects[0]
            } else {
              this.resetValue()
            }
          } else {
            this.projects = []
            this.resetValue()
          }
          return Promise.resolve(true)
        }).catch((error) => {
          this.$message.error('初始化项目列表出错了Message:' + error)
          console.log('loadProject function error : 初始化项目列表出错了Message:' + error)
          this.resetValue()
          return Promise.resolve(false)
        })
    },
    filterOption (input, option) { return (option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0) },
    resetValue () {
      this.currentProject = {}
      this.ids = undefined
    },
    onSelect (value, option) {},
    onDeSelect (value, option) {},
    onChange (value) {
      if (value) {
        if (Array.isArray(value)) {
          value.forEach((item) => {
            this.currentProject[item] = this.projectMap[item]
          })
        } else {
          this.currentProject = this.projectMap[value]
        }
      } else {
        this.currentProject = undefined
      }
      this.setProjectData(value, this.currentProject)
      this.$emit('change', value, this.currentProject)
    },
    refreshSelectde (value) {
      this.$nextTick(() => {
        this.ids = value
      })
    },
    initProjectData (response) {
      if (!response) {
        this.setProjectData(this.isMul ? [] : undefined)
        return
      }
      if (this.isDefault) {
        this.setProjectData(this.ids, this.currentProject)
        return
      }
      this.ids = this.selected || this.value
      if (!this.ids) {
        const ids = this.isMul ? [] : undefined
        this.setProjectData(ids, undefined)
        return
      }
      if (Array.isArray(this.ids)) {
        this.ids.forEach((item) => {
          this.currentProject[item] = this.projectMap[item]
        })
      } else {
        this.currentProject = this.projectMap[this.ids]
        if (this.currentProject) {
          this.setProjectData(this.ids, this.currentProject)
        } else {
          this.setProjectData(undefined, this.currentProject)
        }
      }
    },
    setProjectData (ids, currentProject) {
      this.$emit('getPrjectIds', ids, currentProject)
      this.$emit('input', ids, currentProject)
      this.setProjectInfo(currentProject)
    },
    handlerFilter (data) {
      if (!this.filterField) { return data }
      const expressions = []
      Object.entries(this.filterField).forEach(([key, value]) => {
        expressions.push((item) => {
          const objValue = item[key]
          return objValue === value
        })
      })
      const result = data.filter(item => {
        let isFit = true
        for (let index = 0; index < expressions.length; index++) {
          const expression = expressions[index]
          const result = expression(item)
          if (!result) {
            isFit = false
            break
          }
        }
        return isFit
      })
      return result
    }
  }
}
</script>

<style>
</style>
