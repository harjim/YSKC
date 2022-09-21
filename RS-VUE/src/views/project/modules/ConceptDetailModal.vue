<!--
 * @Author: your name
 * @Date: 2022-02-16 15:46:45
 * @LastEditors: lzh
 * @LastEditTime: 2022-03-01 08:21:29
 * @Description: In User Settings Edit
 * @FilePath: \RS-VUE\src\views\project\modules\ConceptDetailModal.vue
-->
<template>
  <a-modal :width="800" :title="title" :visible="visible" @cancel="visible = false">
    <template slot="footer">
      <a-button type="primary" @click="visible=false">关闭</a-button>
    </template>
    <a-form style="height: 600px; overflow: auto;">
      <h3 style="text-align: center;">{{ record.rd }}-{{ record.checkedList && record.checkedList.map(v => v.trim()).join('，') }}</h3>
      <a-form-item label="会议主题">{{ record.theme }}</a-form-item>
      <a-form-item label="会议内容">
        <div v-html="record.mattersInvolved"></div>
      </a-form-item>
      <a-form-item>
        <img style="display:block; max-width: 100%; margin-bottom: 8px;" v-for="(item, index) in record.files" :key="index" :src="item.filePath" alt="">
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {

  data () {
    return {
      title: '',
      visible: false,
      labelCol: { span: 2 },
      wrapperCol: { span: 22 },
      record: {}
    }
  },
  methods: {
    show (record, row) {
      this.record = record
      this.title = `详情-【${record.rd}】${row.label}份`
      this.visible = true
    }
  }
}
</script>
