<template>
  <div class="list">
    <div v-for="(item, index) in list" :key="index" class="list__item">
      <a-input :value="item" placeholder="请输入" @change="e => handleChange(e.target.value, index)" />
      <div class="list__item__icon">
        <a-icon :style="{ fontSize: '24px', color: list.length === 1 ? '' : 'red' }" type="minus-circle" @click="delItem(index)" />
        <a-icon v-if="index === 0" style="font-size: 24px; color: #1890ff;" type="plus-circle" @click="addItem" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MultipleAddress',
  props: {
    value: [String]
  },
  data () {
    return {
      list: this.value || ['']
    }
  },
  watch: {
    value (newValue) {
      this.list = JSON.parse(newValue[0] === '[' ? newValue : '[""]')
    }
  },
  methods: {
    handleChange (v, i) {
      this.list[i] = v
      this.list = [...this.list]
      this.triggerChange(this.list)
    },
    addItem () {
      this.list.unshift('')
      this.triggerChange(this.list)
    },
    delItem (i) {
      if (this.list.length === 1) return
      this.list.splice(i, 1)
      this.triggerChange(this.list)
    },
    triggerChange (changeValue) {
      this.$emit('change', JSON.stringify(changeValue))
    }
  }
}
</script>

<style lang="less" scoped>
.list {
  display: flex;
  flex-direction: column;
  row-gap: 6px;
  &__item {
    display: flex;
    align-items: center;
    column-gap: 12px;

    & input:first-of-type {
      flex: 2;
    }
    & div:first-of-type {
      flex: 1;
    }

    &__icon {
      display: flex;
      column-gap: 4px;
    }
  }
}
</style>
