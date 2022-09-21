<!--
 * @Author: ldx
 * @Date: 2020-12-07 09:01:25
 * @LastEditTime: 2020-12-07 13:55:26
 * @LastEditors: ldx
 * @Description: 补充财务状况
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\SupplonentFinace.vue
-->
<template>
  <div>
    <!-- <a-divider orientation="left">
      <span class="mode-title">补充财务状况</span>
    </a-divider> -->
    <custom-title title="补充财务状况" @switchEdit="switchEdit" ref="title"></custom-title>
    <div>
      <div class="action-buttons">
        <a-button class="save-btn" type="primary" @click="saveBtn">保存</a-button>
        <a-button @click="cancelBtn">取消</a-button>
      </div>
    </div>
  </div>
</template>

<script>
import CustomTitle from './CustomTitle'
export default {
  name: 'SupplonentFinace',
  components: {
    CustomTitle
  },
  data () {
    return {
      isEdit: false
    }
  },
  methods: {
    saveBtn () {},
    cancelBtn () {
      this.$refs.title.switchEdit()
    },
    switchEdit (isEdit) {
      this.isEdit = isEdit
    }
  }
}
</script>
<style lang="less" scoped>
.mode-title {
  font-size: 18px;
  font-weight: 600;
  color: #151515;
}
.action-buttons {
  padding: 12px 0;
  text-align: center;
}
.save-btn {
  margin-right: 40px;
}
</style>
