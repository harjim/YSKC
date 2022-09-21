<template>
  <div>
    <a-row>
      <a-col :span="10">
        <p>默认名称</p>
        <a-input
          v-for="item in defaultStageList"
          :key="item.stageKey"
          :defaultValue="item.stageName"
          :disabled="true"
          style="margin-bottom: 10px"
        />
      </a-col>
      <a-col :span="4"></a-col>
      <a-col :span="10">
        <p>自定义名称</p>
        <a-input
          v-for="(item, idx) in companyStageList"
          :key="idx"
          v-model="item.stageName"
          @change="() => (isChange = true)"
          style="margin-bottom: 10px"
        />
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  name: 'EditStage',
  data () {
    return {
      isChange: false
    }
  },
  props: {
    defaultStageList: {
      type: Array,
      required: true
    },
    companyStageList: {
      type: Array,
      required: true
    },
    projectObjId: {
      type: Number,
      required: true
    }
  },
  created () {
    console.log('EditStageModal run')
  },
  methods: {
    onEditStage (flag) {
      this.$emit('loadingStatus', true)
      this.saveStage(flag)
    },
    saveStage (flag) {
      // flag: true 表示保存，false表示应用
      this.$http.post('stage/saveStage', { list: this.companyStageList, projectId: flag ? 0 : this.projectObjId, changeCStage: this.isChange }).then(({ data }) => {
        if (data === true) {
          this.$message.success(flag ? '保存成功' : '应用成功')
          // 暂定，可能要改
          // this.defaultStageList = []
          // this.companyStageList = []
          this.$emit('showEditClose')
          this.$emit('loadingStatus', false)
        }
      }).finally(() => {
        this.$emit('changeRefresh')
      })
    }
  }
}
</script>
