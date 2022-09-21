<!--
 * @Author: ldx
 * @Date: 2020-12-07 10:15:02
 * @LastEditTime: 2020-12-07 11:04:57
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\CustomTitle.vue
-->
<template>
  <div class="center-form-title">
    <div class="title">{{ title }}</div>
    <a
      class="edit"
      @click="switchEdit"
      v-if="$auth('customer:all:edit') && !isEdit"
    >编辑</a>
    <div class="line"></div>
  </div>
</template>

<script>
export default {
  name: 'CustomTitle',
  props: {
    title: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      isEdit: false
    }
  },
  methods: {
    switchEdit () {
      this.isEdit = !this.isEdit
      this.$emit('switchEdit', this.isEdit)
    },
    changeEdit (edit) {
      this.isEdit = edit
      this.$emit('switchEdit', this.isEdit)
    }
  }
}
</script>

<style lang="less" scoped>
.center-form-title {
  width: 100%;
  height: 38px;
  margin-bottom: 20px;
  line-height: 38px;
  font-size: 16px;
  font-weight: 700;
}
.title {
  float: left;
  padding: 0 10px 0 5px;
  background-color: #fff;
  color: #333;
}
.edit {
  font-size: 14px;
  float: right;
  color: #3296fa;
  cursor: pointer;
  background-color: #fff;
  padding: 0 0 0 10px;
}
.line {
  padding-top: 18px;
  border-bottom: 1px solid #ebebeb;
}
</style>
