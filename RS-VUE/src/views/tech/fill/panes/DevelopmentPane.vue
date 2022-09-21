<template>
  <a-layout
    id="components-layout-demo-side"
    style="background: #fff; padding: 0"
  >
    <a-layout-sider
      collapsible
      :trigger="null"
      style="flex:0 0 340px; max-width: 450px; min-width: 10px; width: 450px;background: rgb(255, 255, 255);"
    >
      <a-anchor :affix="false">
        <a-anchor-link
          title="5.1项目对企业的发展作用"
        >
          <a-anchor-link
            title="5.1.1提高产品质量"
          />
          <a-anchor-link
            title="5.1.2提高人力资源管理"
          />
          <a-anchor-link
            title="5.1.3提高企业的竞争力"
          />
        </a-anchor-link>
        <a-anchor-link
          title="5.2项目可持续发展的情况"
        />
        <a-anchor-link
          title="5.3预期的经济效益"
        />
      </a-anchor>
    </a-layout-sider>
    <a-layout>
      <a-layout-content style="margin: 0 16px">
        <a-card style="width:100%">
          <a-row :gutter="16">
            <a-col
              :md="24"
              :lg="16"
            >
              <a-form
                layout="vertical"
                @submit="handleSubmit"
                :form="form"
              >
                <a-card
                  title="5.1项目对企业的发展作用"
                  :bordered="false"
                  id="workDev"
                >
                  <a-form-item>
                    <template slot="label">
                      <span>
                        5.1.1提高产品质量
                        <a id="workDev_1" />
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['5.1.1'].textItem.content"
                      placeholder="提高产品质量"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        5.1.2提高人力资源管理
                        <a nidame="workDev_2"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['5.1.2'].textItem.content"
                      placeholder="提高人力资源管理"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        5.1.3提高企业的竞争力
                        <a id="workDev_3"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['5.1.3'].textItem.content"
                      placeholder="提高企业的竞争力"
                    />
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      5.2项目可持续发展的情况
                      <a id="continueDev"></a>
                    </span>
                  </template>
                  <a-form-item>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['5.2'].textItem.content"
                      placeholder="项目可持续发展的情况"
                    />
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      5.3预期的经济效益
                      <a id="planMoney"></a>
                    </span>
                  </template>
                  <a-form-item>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['5.3'].textItem.content"
                      placeholder="预期的经济效益"
                    />
                  </a-form-item>
                </a-card>
                <a-form-item>
                  <a-button
                    type="primary"
                    html-type="submit"
                    :loading="saveLoading"
                  >
                    提交
                  </a-button>
                </a-form-item>
              </a-form>
            </a-col>
          </a-row>
        </a-card>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script>
export default {
  name: 'ProjectSurveyPane',
  data () {
    return {
      previewVisible: false,
      previewImage: '',
      fileList: {
      },
      form: this.$form.createForm(this),
      saveLoading: false,
      key: '5',
      imgMap: {},
      defaultMap: {
        '5.1': { textItem: { type: 1, content: null } },
        '5.1.1': { textItem: { type: 1, content: null } },
        '5.1.2': { textItem: { type: 1, content: null } },
        '5.1.3': { textItem: { type: 1, content: null } },
        '5.2': { textItem: { type: 1, content: null } },
        '5.3': { textItem: { type: 1, content: null } }
      },
      dataMap: {
        '5.1': { textItem: { type: 1, content: null } },
        '5.1.1': { textItem: { type: 1, content: null } },
        '5.1.2': { textItem: { type: 1, content: null } },
        '5.1.3': { textItem: { type: 1, content: null } },
        '5.2': { textItem: { type: 1, content: null } },
        '5.3': { textItem: { type: 1, content: null } }
      }
    }
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    }
  },
  created () {
    this.loadData()
  },
  computed: {
  },
  watch: {
    projectId (newId) {
      this.loadData()
    }
  },
  methods: {
    uploadImgs (file, rowKey) {
      const self = this
      const param = new FormData()
      param.append('file', file)
      param.append('projectId', self.projectId)
      param.append('key', rowKey)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/import/importImages', param, config).then(res => {
        if (res.success) {
          if (typeof self.fileList[res.data.key] === 'undefined') {
            self.fileList[res.data.key] = []
          }
          self.fileList[res.data.key] = [...self.fileList[res.data.key], {
            uid: res.data.newFileName,
            name: res.data.newFileName,
            status: 'done',
            url: res.data.filePath
          }]
        }
        return res
      }).catch(res => {
      }).finally(res => {
      })
    },
    beforeUpload (file, key) {
      this.uploadImgs(file, key)
      return false
    },
    handleCancel () {
      this.previewVisible = false
    },
    handlePreview (file) {
      this.previewImage = file.url || file.thumbUrl
      this.previewVisible = true
    },
    handleChange ({ fileList }) {
      // this.fileList = fileList
    },
    handleClick (e, link) {
      e.preventDefault()
    },
    loadData () {
      const self = this
      this.$http.get('/techProject/getDeclarationList', { params: { projectId: this.projectId, key: this.key } })
        .then(res => {
          if (res.success && res.data !== null) {
            res.data.map(function (item) {
              if (typeof self.dataMap[item.key] === 'undefined') {
                self.dataMap[item.key] = {
                  key: item.key
                }
              }
              self.dataMap[item.key] = Object.assign({}, item)
              self.$set(self.dataMap[item.key], 'key', item.key)
              if (item.textItem === null) {
                item.textItem = self.defaultMap[item.key].textItem
              }
              self.$set(self.dataMap[item.key], 'textItem', item.textItem)
              if (typeof item.tableItem === 'undefined' || item.tableItem === null) {
                self.$set(self.dataMap[item.key], 'tableItem', self.defaultMap[item.key].tableItem)
              }
              if (typeof item.tableItem !== 'undefined' && item.tableItem !== null) {
                self.$set(item.tableItem, 'data', item.tableItem.datas)
                self.$set(item.tableItem, 'column', item.tableItem.columns)
                self.$set(self.dataMap[item.key], 'tableItem', item.tableItem)
              }
              if (typeof self.fileList[item.key] === 'undefined') {
                self.fileList[item.key] = []
              }
              self.$set(self.fileList, item.key, [])
              if (typeof item.imageItem === 'undefined' || item.imageItem === null) {
                self.$set(self.dataMap[item.key], 'imageItem', self.defaultMap[item.key].imageItem)
              }
              if (typeof item.imageItem !== 'undefined' && item.imageItem != null) {
                if (typeof item.imageItem.imgPath !== 'undefined' && item.imageItem.imgPath != null) {
                  const arr = item.imageItem.imgPath.split(',')
                  arr.forEach(element => {
                    self.fileList[item.key] = [...self.fileList[item.key], {
                      uid: element,
                      name: element,
                      status: 'done',
                      url: element
                    }]
                  })
                }
              }
              return item
            })
          }
        }).catch(res => {
          self.dataMap = {}
        })
    },
    handleSubmit (e) {
      const self = this
      e.preventDefault()
      this.saveLoading = true
      const values = Object.values(this.dataMap)
      values.map(item => {
        if (typeof item.tableItem !== 'undefined') {
          item.tableItem.columns = item.tableItem.column
          item.tableItem.datas = item.tableItem.data
        }
        if (typeof self.fileList[item.key] !== 'undefined' && self.fileList[item.key].length > 0) {
          var urls = []
          self.fileList[item.key].forEach(element => {
            urls.push(element.url)
          })
          item.imageItem = { type: 3, imgPath: urls.join(',') }
        } else {
          if (typeof item.imageItem !== 'undefined') {
            item.imageItem = { type: 3, imgPath: '' }
          }
        }
      })
      this.$emit('handleSubmit', self, values)
    }
  }
}
</script>
<style scoped>
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>
