<!--
 * @Author: ldx
 * @Date: 2020-08-03 18:57:10
 * @LastEditTime: 2021-02-24 14:09:00
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\tech\modules\ProjectModal.vue
-->
<template>
  <a-modal
    :width="1024"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    :destroyOnClose=" true"
    :afterClose="afterClose"
  >
    <a-tabs default-active-key="1" :activeKey="activeKey" @change="onTabsChange" >
      <a-tab-pane key="1" tab="基本信息">
        <base-tab :record="record" ref="baseTab" @ok="updataData"/>
      </a-tab-pane>
      <!-- <a-tab-pane key="2" tab="设备明细" v-if="isShowTabs">
        设备明细
      </a-tab-pane>
      <a-tab-pane key="3" tab="合同发票" v-if="isShowTabs">
        合同发票
      </a-tab-pane> -->
      <a-tab-pane key="4" tab="资料清单" v-if="isShowTabs && $auth('tech:pro:attachment:search')">
        <dataList-tab :directionId="record.directionId" :projectId="record.id" />
      </a-tab-pane>
    </a-tabs>
  </a-modal>
</template>

<script>
import moment from 'moment'
import BaseTab from './tabs/BaseTab'
import DataListTab from './tabs/DataListTab'
export default {
  components: {
    BaseTab,
    DataListTab
  },
  data () {
    return {
      years: [],
      title: '',
      id: -1,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      record: {},
      tempFileDatas: [
        { settingId: 1, name: '申请列表', files: [ ], id: undefined },
        { settingId: 2, name: '申请报告', files: [], id: undefined },
        { settingId: 3, name: '备案证', files: [], id: undefined },
        { settingId: 4, name: '营业执照/法人身份', files: [], id: undefined },
        { settingId: 5, name: '审计报告', files: [], id: undefined },
        { settingId: 6, name: '真实声明', files: [], id: undefined },
        { settingId: 7, name: '固定投资', files: [], id: undefined },
        { settingId: 8, name: '设备明细表', files: [], id: undefined },
        { settingId: 9, name: '合同发票', files: [], id: undefined },
        { settingId: 10, name: '设备照片', files: [], id: undefined },
        { settingId: 11, name: '其他', files: [], id: undefined }
      ],
      fileDatas: [],
      stageDatas: [],
      stageMap: {},
      fileMap: {
        '1': '申请列表',
        '2': '申请报告',
        '3': '备案证',
        '4': '营业执照/法人身份',
        '5': '审计报告',
        '6': '真实声明',
        '7': '固定投资',
        '8': '设备明细表',
        '9': '合同发票',
        '10': '设备照片',
        '11': '其他'
      },
      activeKey: '1',
      isShowTabs: false,
      self: this
    }
  },
  computed: {
    // ...mapState(['techProjects'])
  },
  mounted () {
    const year = new Date().getFullYear() + 3
    this.years = []
    for (var i = 6; i > 0; i--) {
      var node = { key: (year - i), label: (year - i) }
      this.years.push(node)
    }
  },
  methods: {
    moment,
    show (record) {
      this.confirmLoading = false
      this.visible = true
      this.activeKey = '1'
      if (record) {
        this.title = `编辑项目[${record.pname}]`
        this.record = record
        this.id = record.id
        this.isShowTabs = true
      } else {
        this.title = '添加项目'
        this.isShowTabs = false
        this.record = {}
      }
    },
    updataData (isRefresh, record) {
      if (isRefresh) {
        this.visible = false
        this.$emit('ok', true)
      } else {
        this.$emit('ok', false, record)
      }
    },
    handleSubmit () {
      switch (this.activeKey) {
        case '1':
          if (!this.$auth('tech:pro:edit')) {
            this.visible = false
            return
          }
          this.$refs.baseTab.baseSubmit()
          break
        case '2':
          this.visible = false
          break
        case '3':
          this.visible = false
          break
        case '4':
          this.visible = false
          break

        default:
          this.visible = false
          break
      }
    },
    onTabsChange (activeKey) {
      this.activeKey = activeKey
      if (activeKey === '4') {
        // 请求方向里面的阶段数据
        // this.getDirectionStage()
      }
    },
    // // 获取申报项目方向阶段
    // getDirectionStage () {
    //   this.$http.get('/techAttachments/getDirectionStage', { params: { directionId: this.record.directionId } }).then((res) => {
    //     if (res.success && res.data) {
    //       this.stageDatas = res.data.sort((a, b) => a.seq - b.seq)
    //       for (const stage of this.stageDatas) {
    //         this.stageMap[stage.stageKey] = stage
    //       }
    //     } else {
    //       this.$message.error(res.errorMessage)
    //     }
    //   }).catch((error) => {
    //     this.$message.error(error.message)
    //   })
    // },
    getDataListing (projectId) {
      this.$http.get('/techAttachments/getList', { params: { projectId } }).then((res) => {
        if (res.success && res.data) {
          res.data.forEach(item => {
            item['files'] = this.getfiles(item.filePath)
          })
          const datsString = JSON.stringify(this.tempFileDatas)
          this.fileDatas = JSON.parse(datsString)
          this.fileDatas.forEach(item => {
            res.data.forEach(subItem => {
              if (item.settingId === subItem.settingId) {
                Object.assign(item, subItem)
              }
            })
          })
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    getfiles (filePathStr) {
      if (!filePathStr) {
        return []
      }
      const filePaths = filePathStr.split(',')
      const files = []
      filePaths.forEach(item => {
        const index = item.lastIndexOf('/') + 1
        const fileName = item.substring(index + 13, item.length)
        files.push({ name: fileName, path: item })
      })
      return files
    },
    afterClose () {

    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
