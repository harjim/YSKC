<template>
  <div class="editable-cell">
    <div v-if="editable" class="editable-cell-input-wrapper">
      <a-input class="input-width" :value="value" @change="handleChange" @pressEnter="check" />
      <a-tooltip title="提交">
        <a-icon type="check" class="editable-cell-icon-check" @click="check" />
      </a-tooltip>
    </div>
    <div v-else class="editable-cell-text-wrapper">
      {{ value || ' ' }}
      <a-tooltip title="添加子节点">
        <a-icon type="plus" class="editable-cell-icon" @click="plus" />
      </a-tooltip>
      <a-tooltip title="编辑">
        <a-icon type="edit" class="editable-cell-icon" @click="edit" />
      </a-tooltip>
      <a-popconfirm title="是否确定删除?" @confirm="del" >
        <a-tooltip title="删除">
          <a-icon type="delete" class="editable-cell-icon" />
          <!-- <a-icon type="delete" class="editable-cell-icon" @click="del" /> -->
        </a-tooltip>
      </a-popconfirm>
      <a-tooltip title="提升排序">
        <a-icon type="caret-up" class="editable-cell-icon" @click="up" />
      </a-tooltip>
      <a-tooltip title="降低排序">
        <a-icon type="caret-down" class="editable-cell-icon" @click="down" />
      </a-tooltip>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    text: {
      type: String,
      default: ''
    },
    oldText: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      value: this.text,
      editable: false,
      stepName: '',
      clicked: false
    }
  },
  methods: {
    handleChange (e) {
      const value = e.target.value
      this.value = value
    },
    backOldText (text) {
      this.value = text
    },
    check () {
      this.editable = false
      this.$emit('change', this.value)
    },
    edit () {
      this.editable = true
    },
    plus () {
      this.$emit('plus', this.value)
    },
    del () {
      this.$emit('del', this.value)
    },
    onSave () {

    },
    handleClickChange (visible) {
      this.clicked = visible
    },
    up () {
      this.$emit('up', this.value)
    },
    down () {
      this.$emit('down', this.value)
    }
  }
}
</script>
<style lang="less" scoped>
.tree-node-icon {
  width: 24px;
  text-align: center;
}
.editable-cell {
  display:inline-block;
}
.editable-cell-icon,
.editable-cell-icon-check {
  margin-left: 12px;
  cursor: pointer;
}
.input-width {
  width: 87%;
}
.editable-cell-icon {
  line-height: 18px;
  display: none;
}

.editable-cell-icon-check {
  line-height: 28px;
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-cell-icon:hover,
.editable-cell-icon-check:hover {
  color: #108ee9;
}
</style>
