<template>
  <a-layout
    id="components-layout-demo-side"
    style="background: #fff; padding: 0"
  >
    <a-layout-sider
      collapsible
      :trigger="null"
      style="flex:0 0 350px; max-width: 350px; min-width: 10px; width: 350px;background: rgb(255, 255, 255);"
    >
      <a-anchor :affix="false">
        <a-anchor-link
          title="3.1 项目建设招投标方案"
        />
        <a-anchor-link
          title="3.2 节能方案分析"
        >
          <a-anchor-link
            title="3.2.1 项目建设和生产过程所遵循的标准和规范"
          />
          <a-anchor-link
            title="3.2.2 项目生产过程中的能源消耗种类和数量分析"
          />
          <a-anchor-link
            title="3.2.3 项目建设和生产过程中采取的节措施"
          />
          <a-anchor-link
            title="3.2.4 项目节能效果分析"
          />
        </a-anchor-link>
        <a-anchor-link
          title="3.3 建设用地、环境保护和生态影响情况分析"
        >
          <a-anchor-link
            title="3.3.1 水环境影响分析"
          />
          <a-anchor-link
            title="3.3.2 大气环境影响分析"
          />
          <a-anchor-link
            title="3.3.3 噪声环境影响分析"
          />
          <a-anchor-link
            title="3.3.4 固体废物的环境影响分析"
          />
          <a-anchor-link
            title="3.3.5 危险废物影响分析"
          />
        </a-anchor-link>
        <a-anchor-link
          title="3.4 安全生产和消防等措施方案"
        >
          <a-anchor-link
            title="3.4.1 安全措施方案"
          />
          <a-anchor-link
            title="3.4.2 消防设施"
          />
        </a-anchor-link>
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
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      3.1 项目建设招投标方案
                      <a id="link_q" />
                    </span>
                  </template>
                  <a-form-item>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.1'].textItem.content"
                      placeholder="项目建设招投标方案"
                    />
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      3.2 节能方案分析
                      <a id="link_w" />
                    </span>
                  </template>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.2.1 项目建设和生产过程所遵循的标准和规范
                        <a id="link_e"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.2.1'].textItem.content"
                      placeholder="项目建设和生产过程所遵循的标准和规范"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.2.2 项目生产过程中的能源消耗种类和数量分析
                        <a id="link_r"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.2.2'].textItem.content"
                      placeholder="项目生产过程中的能源消耗种类和数量分析"
                    />
                    <div style="padding:10px">
                      <a-button
                        type="primary"
                        @click="addRow"
                      >添加</a-button>
                      <a-table
                        size="small"
                        :pagination="false"
                        bordered
                        :columns="dataMap['3.2.2'].tableItem.column"
                        :dataSource="dataMap['3.2.2'].tableItem.data"
                        rowKey="num"
                      >
                        <template
                          slot="cname"
                          slot-scope="text,row"
                        >
                          <a-input v-model="row.cname" />
                        </template>
                        <template
                          slot="volume"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.volume"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="quantity"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.quantity"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="ratio"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.ratio"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="hour"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.hour"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="loss"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.loss"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="action"
                          slot-scope="text,row"
                        >
                          <a @click="removeRow(row)">移除</a>
                        </template>
                      </a-table>
                    </div>
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.2.3 项目建设和生产过程中采取的节措施
                        <a id="link_t"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.2.3'].textItem.content"
                      placeholder="项目建设和生产过程中采取的节措施"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.2.4 项目节能效果分析
                        <a id="link_y"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.2.4'].textItem.content"
                      placeholder="项目节能效果分析"
                    />
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      3.3 建设用地、环境保护和生态影响情况分析
                      <a id="link_u" />
                    </span>
                  </template>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.3.1 水环境影响分析
                        <a id="link_i"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.3.1'].textItem.content"
                      placeholder="水环境影响分析"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.3.2 大气环境影响分析
                        <a id="link_o"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.3.2'].textItem.content"
                      placeholder="大气环境影响分析"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.3.3 噪声环境影响分析
                        <a id="link_p"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.3.3'].textItem.content"
                      placeholder="噪声环境影响分析"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.3.4 固体废物的环境影响分析
                        <a id="link_a"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.3.4'].textItem.content"
                      placeholder="固体废物的环境影响分析"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.3.5 危险废物影响分析
                        <a id="link_s"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.3.5'].textItem.content"
                      placeholder="危险废物影响分析"
                    />
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      3.4 安全生产和消防等措施方案
                      <a id="link_d" />
                    </span>
                  </template>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.4.1 安全措施方案
                        <a id="link_f"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.4.1'].textItem.content"
                      placeholder="安全措施方案"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        3.4.2 消防设施
                        <a id="link_g"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['3.4.2'].textItem.content"
                      placeholder="消防设施"
                    />
                  </a-form-item>
                </a-card>
                <a-form-item>
                  <a-button
                    type="primary"
                    html-type="submit"
                    :loading="saveLoading"
                  >提交</a-button>
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
  name: 'ConditionsPane',
  data () {
    return {
      defaultData: { num: 1, cname: '', volume: '', quantity: '', ratio: '', hour: '', loss: '' },
      previewVisible: false,
      previewImage: '',
      fileList: {
      },
      form: this.$form.createForm(this),
      saveLoading: false,
      key: '3',
      imgMap: {},
      defaultMap: {
        '3.1': { textItem: { type: 1, content: null } },
        '3.2': { textItem: { type: 1, content: null } },
        '3.2.1': { textItem: { type: 1, content: null } },
        '3.2.2': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '序号',
                dataIndex: 'num',
                width: '4%'
              },
              {
                title: '设备名称',
                dataIndex: 'cname',
                width: '22%',
                scopedSlots: { customRender: 'cname' }
              },
              {
                title: '单台/套容量（KW)',
                dataIndex: 'volume',
                width: '14%',
                scopedSlots: { customRender: 'volume' }
              },
              {
                title: '台/套',
                dataIndex: 'quantity',
                width: '14%',
                scopedSlots: { customRender: 'quantity' }
              },
              {
                title: '需用系数(kx)',
                dataIndex: 'ratio',
                width: '14%',
                scopedSlots: { customRender: 'ratio' }
              },
              {
                title: '年最大负荷利用时间 (H)',
                dataIndex: 'hour',
                width: '14%',
                scopedSlots: { customRender: 'hour' }
              },
              {
                title: '估算能源消耗量（KW·H）',
                dataIndex: 'loss',
                width: '10%',
                scopedSlots: { customRender: 'loss' }
              }, {
                title: '操作',
                dataIndex: 'action',
                scopedSlots: { customRender: 'action' }
              }
            ],
            data: [
            ]
          }
        },
        '3.2.3': { textItem: { type: 1, content: null } },
        '3.2.4': { textItem: { type: 1, content: null } },
        '3.3': { textItem: { type: 1, content: null } },
        '3.3.1': { textItem: { type: 1, content: null } },
        '3.3.2': { textItem: { type: 1, content: null } },
        '3.3.3': { textItem: { type: 1, content: null } },
        '3.3.4': { textItem: { type: 1, content: null } },
        '3.3.5': { textItem: { type: 1, content: null } },
        '3.4': { textItem: { type: 1, content: null } },
        '3.4.1': { textItem: { type: 1, content: null } },
        '3.4.2': { textItem: { type: 1, content: null } }
      },
      dataMap: {
        '3.1': { textItem: { type: 1, content: null } },
        '3.2': { textItem: { type: 1, content: null } },
        '3.2.1': { textItem: { type: 1, content: null } },
        '3.2.2': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '序号',
                dataIndex: 'num',
                width: '4%'
              },
              {
                title: '设备名称',
                dataIndex: 'cname',
                width: '22%',
                scopedSlots: { customRender: 'cname' }
              },
              {
                title: '单台/套容量（KW)',
                dataIndex: 'volume',
                width: '14%',
                scopedSlots: { customRender: 'volume' }
              },
              {
                title: '台/套',
                dataIndex: 'quantity',
                width: '14%',
                scopedSlots: { customRender: 'quantity' }
              },
              {
                title: '需用系数(kx)',
                dataIndex: 'ratio',
                width: '14%',
                scopedSlots: { customRender: 'ratio' }
              },
              {
                title: '年最大负荷利用时间 (H)',
                dataIndex: 'hour',
                width: '14%',
                scopedSlots: { customRender: 'hour' }
              },
              {
                title: '估算能源消耗量（KW·H）',
                dataIndex: 'loss',
                width: '10%',
                scopedSlots: { customRender: 'loss' }
              }, {
                title: '操作',
                dataIndex: 'action',
                scopedSlots: { customRender: 'action' }
              }
            ],
            data: [
            ]
          }
        },
        '3.2.3': { textItem: { type: 1, content: null } },
        '3.2.4': { textItem: { type: 1, content: null } },
        '3.3': { textItem: { type: 1, content: null } },
        '3.3.1': { textItem: { type: 1, content: null } },
        '3.3.2': { textItem: { type: 1, content: null } },
        '3.3.3': { textItem: { type: 1, content: null } },
        '3.3.4': { textItem: { type: 1, content: null } },
        '3.3.5': { textItem: { type: 1, content: null } },
        '3.4': { textItem: { type: 1, content: null } },
        '3.4.1': { textItem: { type: 1, content: null } },
        '3.4.2': { textItem: { type: 1, content: null } }
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
    addRow () {
      var copy = { ...this.defaultData }
      if (this.dataMap['3.2.2'].tableItem.data) {
        copy.num = this.dataMap['3.2.2'].tableItem.data.length + 1
      } else {
        this.dataMap['3.2.2'].tableItem.data = []
      }
      this.dataMap['3.2.2'].tableItem.data.push(copy)
    },
    removeRow (row) {
      var tempArr = []
      var num = 0
      const tableData = [...this.dataMap['3.2.2'].tableItem.data]
      tableData.forEach(item => {
        if (row.num !== item.num) {
          item.num = ++num
          tempArr.push(item)
        }
      })
      this.dataMap['3.2.2'].tableItem.data = tempArr
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
