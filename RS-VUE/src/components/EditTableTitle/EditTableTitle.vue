<template>
  <span v-if="editable">
    <a-row>
      <a-input v-model="myTitle" @change="change"></a-input>
      <a-icon type="check-circle" theme="twoTone" @click="e=>checkClick(e)" class="EditableTitle" />
      <a-icon type="close-circle" theme="twoTone" @click="e=>closeClick(e)" class="EditableTitle" />
    </a-row>
  </span>
  <span @dblclick="onClickColumn()" v-else>{{ myTitle }}</span>
</template>
<script>
export default {
  name: 'EditTableTitle',
  props: {
    fieldData: {
      type: Object,
      required: true
    }
  },
  computed: {
  },
  watch: {
    fieldData (v) {
      if (this.myTitle !== v.title) {
        this.myTitle = v.title
      }
    }
  },
  data () {
    return {
      editable: false,
      myTitle: this.fieldData.title,
      cacheData: this.fieldData
    }
  },
  methods: {
    change (e) {
      this.myTitle = e.target.value
    },
    checkClick (e) {
      // this.editable = false
      this.$emit('save', this.myTitle)
    },
    closeClick (e) {
      // this.editable = false
      this.myTitle = this.cacheData.title
    },
    onClickColumn () {
      // this.editable = true
      this.cacheData = { ...this.fieldData }
    }
  }
}
</script>

<style>
.EditableTitle svg {
  width: 20px;
  height: 20px;
  cursor: pointer;
}
</style>
