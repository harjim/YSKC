<!--
 * @Author: ldx
 * @Date: 2021-05-27 11:07:33
 * @LastEditTime: 2021-05-27 15:42:12
 * @LastEditors: ldx
 * @Description: 添加多个文档确认框
 * @FilePath: \RS-VUE\src\views\project\modules\ProcessDoc\modules\AddMultipleFileConfirmModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="550"
    :visible="isVisible"
    :afterClose="OnAfterClose"
    :maskClosable="false"
    @cancel="onCancel"
    @ok="onHandleSubmit">
    <a-alert
      :message="message"
      type="info"
      show-icon
    >
      <template #description>
        <div class="all_checkbox_wrap">
          <a-checkbox :checked="allChecked" :indeterminate="indeterminate" @change="onAllChange">全选</a-checkbox>
        </div>
        <ul>
          <li v-for="(file,index) in chooseFiles" :key="index">
            <a-checkbox :checked="file.checked" @change="(e) => onChange(e,file)">  {{ file.name }} </a-checkbox>
          </li>
        </ul>
      </template>
    </a-alert>
  </a-modal>
</template>
<script>
export default {
  name: 'AddMultipleFileConfirmModal',
  watch: {
    chooseFiles: {
      deep: true,
      handler (val) {
        const checkedFiles = val.filter(item => { return item.checked })
        if (!checkedFiles.length) {
          this.allChecked = false
          this.indeterminate = undefined
          return
        }
        if (val.length === checkedFiles.length) {
          this.allChecked = true
          this.indeterminate = undefined
        } else {
          this.indeterminate = true
        }
      }
    }
  },
  data () {
    return {
      isVisible: false,
      title: '',
      message: '您确定要在以下每一个文档后添加新文档吗？（全部不选择，默认会在当前阶段最后添加新文档）',
      chooseFiles: [],
      stage: undefined,
      allChecked: true,
      indeterminate: undefined
    }
  },
  methods: {
    show (chooseFiles, stage, title = '文档后添加新文档') {
      this.isVisible = true
      this.title = `[${stage.stageType}]${title}`
      this.chooseFiles = chooseFiles
      this.stage = stage
    },
    OnAfterClose () {
      this.isVisible = false
      this.chooseFiles = []
      this.allChecked = true
      this.indeterminate = undefined
      this.stage = undefined
    },
    onHandleSubmit () {
      this.$emit('onAddMultipleFile', this.chooseFiles, this.stage)
      this.isVisible = false
    },
    onAllChange (e) {
      this.allChecked = e.target.checked
      this.indeterminate = undefined
      this.chooseFiles.forEach(file => {
        file.checked = this.allChecked
      })
    },
    onChange (e, file) {
      file.checked = e.target.checked
    },
    onCancel () {
      this.isVisible = false
    }

  }
}
</script>
<style lang='less' scoped>
.all_checkbox_wrap {
  padding-bottom: 8px;
}
ul {
  margin: 0;
  padding: 0;
  max-height: 250px;
  overflow: auto;
  li {
    list-style: none;
    padding-bottom: 8px;
    &:last-child {
      padding-bottom: 0;
    }
  }
}
.tip {
  color: red;
  font-size: 16px;
  line-height: 18px;
  margin-top: 10px;
}
</style>
