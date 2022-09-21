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
    :getPopupContainer="getPopupContainer"
  >
    <template v-if="isJoinPrjectName">
      <a-select-option
        v-for="item in projects"
        :title="item.pname"
        :key="item.id"
        :token-separators="[',']"
        :label="`${item.rdTitle} - ${item.pname}`"
      >
        <div class="item">
          <span class="item-content"> {{ `${item.rdTitle} - ${item.pname}` }}</span>
          <span v-if="auditCount">
            <span class="item-red-color">{{ item.auditDocNum ? item.auditDocNum : '-' }} </span>|
            <span class="item-green-color"> {{ item.passNum ? item.passNum : '-' }} </span>|
            <span class="item-blue-color"> {{ item.submitNum ? item.submitNum : '-' }} </span>
          </span>
        </div>
      </a-select-option>
    </template>
    <template v-else>
      <a-select-option
        v-for="item in projects"
        :title="item.pname"
        :key="item.id"
        :token-separators="[',']"
        :label="item.rdTitle" >
        {{ item.rdTitle }} - {{ item.pname }}
      </a-select-option>
    </template>
  </a-select>
</template>

<script>
import { popupContainer } from '@/views/customer/modules/AuditProgress/modules/js/screenFullMountDom'
export default {
  props: {
    year: {
      type: Number,
      required: true
    },
    companyId: {
      type: [Number, String],
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
      type: Array,
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
    mountDom: { // 弹出选项挂载在那个DOM显示，默认body
      type: String,
      default: ''
    },
    // auditCount: { // 是否获取文档状态统计数量 1: 过程文件，2： 备查资料
    //   type: Boolean,
    //   default: false
    // }
    auditCount: { // 是否获取文档状态统计数量 0: 不统计 1: 过程文件，2： 备查资料
      type: Number,
      default: 0
    }
  },
  mounted () {
    this.loadProject().then(() => {
      this.$emit('getPrjectIds', this.ids, this.currentProject)
    })
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
  watch: {
    year (value) {
      this.loadProject().then(() => {
        this.$emit('getPrjectIds', this.ids, this.currentProject)
      })
    },
    companyId (value) {
      this.loadProject().then(() => {
        this.$emit('getPrjectIds', this.ids, this.currentProject)
      })
    },
    selected (value) {
      this.refreshSelectde(value)
    },
    value (v) {
      this.refreshSelectde(v)
    }
  },

  methods: {
    getPopupContainer (triggerNode) {
      return popupContainer(this.mountDom)
    },
    loadProject () {
      if (!this.year || this.year <= 0) { return Promise.resolve(true) }
      return this.$http.get('/projectProgress/getListProject', { params: { year: this.year, companyId: this.companyId, auditCount: this.auditCount } })
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.projects = res.data
            if (this.isDefault) {
              this.ids = res.data[0].id
              this.currentProject = res.data[0]
            }
            res.data.forEach((item, index) => {
              this.projectMap[item.id] = item
            })
          } else {
            this.projects = []
          }
          Promise.resolve(true)
        }).catch((error) => {
          this.$message.error('初始化项目列表出错了Message:' + error)
          this.projects = []
        })
    },
    filterOption (input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    },
    ZeroFormat (value, length = 2) {
      return value < 9 ? (Array(length).join('0') + value).slice(-length) : value
    },
    onSelect (value, option) {},
    onDeSelect (value, option) {},
    onChange (value, option) {
      if (Array.isArray(value)) {
        value.forEach((item, index) => {
          this.currentProject[item] = this.projectMap[item]
        })
      } else {
        this.currentProject = this.projectMap[this.ids]
      }
      this.$emit('getPrjectIds', this.ids, this.currentProject)
      this.$emit('input', this.ids, this.currentProject)
      this.$emit('change', this.ids, this.currentProject)
    },
    refreshSelectde (value) {
      this.$nextTick(() => {
        this.ids = value
        this.$emit('getPrjectIds', this.ids, this.currentProject)
      })
    }
  }
}
</script>
<style lang="less" scoped>
.item {
  display:flex;
  justify-content:space-between;
  // background-color: red;
  .item-content {
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  .item-red-color {
    font-weight: 600;
    color: red;
  }
  .item-green-color {
    font-weight: 600;
    color: #008000;
  }
  .item-blue-color {
    font-weight: 600;
    color: #1890ff;
  }
}
</style>
