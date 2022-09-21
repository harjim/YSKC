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
          title="2.1项目建设的基本情况"
        >
          <a-anchor-link
            title="2.1.1建设背景"
          />
          <a-anchor-link
            title="2.1.2建设地点"
          />
          <a-anchor-link
            title="2.1.3主要建设内容和规模"
          />
          <a-anchor-link
            title="2.1.4产品和工程技术方案"
          >
            <a-anchor-link
              title="2.1.4.1 产品的技术方案"
            />
            <a-anchor-link
              title="2.1.4.2 工程技术方案"
            />
          </a-anchor-link>
          <a-anchor-link
            title="2.1.5投资规模和资金筹措方案"
          />
        </a-anchor-link>
        <a-anchor-link
          title="2.2主要设备选型和配套工程"
        >
          <a-anchor-link
            title="2.2.1 主要设备选型"
          />
          <a-anchor-link
            title="2.2.2 配套工程"
          />
        </a-anchor-link>
        <a-anchor-link
          title="2.3 项目负责人基本情况"
        />
        <a-anchor-link
          title="2.4 现有技术、装备等支持配套条件的落实情况"
        />
        <a-anchor-link
          title="2.5 项目建设目标及主要技术经济指标"
        />
        <a-anchor-link
          title="2.6 项目实施的必要性及可行性"
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
                  title="2.1 项目建设的基本情况"
                  :bordered="false"
                  id="项目建设的基本情况"
                >
                  <a-form-item>
                    <template slot="label">
                      <span>
                        2.1.1 建设背景
                        <a id="建设背景" />
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.1.1'].textItem.content"
                      placeholder="项目涉及产品的国内外行业现状，不足之处，可进行的改进之处，支持的政策、发展的前景等"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        2.1.2 建设地点
                        <a id="建设地点"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.1.2'].textItem.content"
                      placeholder="项目备案证上的地点"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        2.1.3 主要建设内容和规模
                        <a id="主要建设内容和规模"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.1.3'].textItem.content"
                      placeholder="项目备案证上的项目名称，备案证的内容具体到哪些设备投入，设备用途，解决的技术难点、预期达到效益目标"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        2.1.4.1 产品的技术方案
                        <a id="产品的技术方案"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.1.4.1'].textItem.content"
                      placeholder="1.概述项目涉及的总工艺流程，2.详细介绍项目里每台设备所在工序改造前后设备变化、人员数量变化、产品效率变化"
                    />
                    <div class="clearfix">
                      <a-upload
                        listType="picture-card"
                        :fileList="fileList['2.1.4.1']"
                        @preview="handlePreview"
                        @change="(item)=>handleChange(item,'2.1.4.1')"
                        :beforeUpload="(file)=>beforeUpload(file,'2.1.4.1')"
                      >
                        <div v-if="fileList['2.1.4.1'].length < 10">
                          <a-icon type="plus" />
                          <div class="ant-upload-text">Upload</div>
                        </div>
                      </a-upload>
                      <a-modal
                        :visible="previewVisible"
                        :footer="null"
                        @cancel="handleCancel"
                      >
                        <img
                          alt="example"
                          style="width: 100%"
                          :src="previewImage"
                        />
                      </a-modal>
                    </div>
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        2.1.4.2 工程技术方案
                        <a id="工程技术方案"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.1.4.2'].textItem.content"
                      placeholder="工程技术方案"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        2.1.5 投资规模和资金筹措方案
                        <a id="投资规模和资金筹措方案"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.1.5'].textItem.content"
                      placeholder="投资规模和资金筹措方案"
                    />
                    <a-table
                      size="small"
                      :pagination="false"
                      bordered
                      :columns="dataMap['2.1.5'].tableItem.column"
                      :dataSource="dataMap['2.1.5'].tableItem.data"
                      rowKey="index"
                    ></a-table>
                  </a-form-item>
                </a-card>
                <a-card
                  title="2.2 主要设备选型和配套工程"
                  id="主要设备选型和配套工程"
                  :bordered="false"
                >
                  <a-form-item>
                    <template slot="label">
                      <span>
                        2.2.1 主要设备选型
                        <a id="主要设备选型"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.2.1'].textItem.content"
                      placeholder="主要设备选型"
                    />
                    <a-table
                      size="small"
                      :pagination="false"
                      bordered
                      :columns="dataMap['2.2.1'].tableItem.column"
                      :dataSource="dataMap['2.2.1'].tableItem.data"
                      rowKey="id"
                    ></a-table>
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        2.2.2 配套工程
                        <a id="配套工程"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.2.2'].textItem.content"
                      placeholder="配套工程"
                    />
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      2.3 项目负责人基本情况
                      <a id="项目负责人基本情况"></a>
                    </span>
                  </template>
                  <a-form-item>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.3'].textItem.content"
                      placeholder="项目负责人基本情况"
                    />
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      2.4 现有技术、装备等支持配套条件的落实情况
                      <a id="现有技术、装备等支持配套条件的落实情况"></a>
                    </span>
                  </template>
                  <a-form-item>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.4'].textItem.content"
                      placeholder="现有技术、装备等支持配套条件的落实情况"
                    />
                    <div class="clearfix">
                      <a-upload
                        listType="picture-card"
                        :fileList="fileList['2.4']"
                        @preview="handlePreview"
                        @change="(item)=>handleChange(item,'2.4')"
                        :beforeUpload="(file)=>beforeUpload(file,'2.4')"
                      >
                        <div v-if="fileList['2.4'].length < 10">
                          <a-icon type="plus" />
                          <div class="ant-upload-text">Upload</div>
                        </div>
                      </a-upload>
                      <a-modal
                        :visible="previewVisible"
                        :footer="null"
                        @cancel="handleCancel"
                      >
                        <img
                          alt="example"
                          style="width: 100%"
                          :src="previewImage"
                        />
                      </a-modal>
                    </div>
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      2.5 项目建设目标及主要技术经济指标
                      <a id="项目建设目标及主要技术经济指标"></a>
                    </span>
                  </template>
                  <a-form-item>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.5'].textItem.content"
                      placeholder="项目建设目标及主要技术经济指标"
                    />
                  </a-form-item>
                </a-card>
                <a-card :bordered="false">
                  <template slot="title">
                    <span>
                      2.6 项目实施的必要性及可行性
                      <a id="项目实施的必要性及可行性"></a>
                    </span>
                  </template>
                  <a-form-item>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['2.6'].textItem.content"
                      placeholder="项目实施的必要性及可行性"
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
  name: 'ProjectSurveyPane',
  data () {
    return {
      previewVisible: false,
      previewImage: '',
      fileList: {
        '2.1.4.1': [],
        '2.4': []
      },
      form: this.$form.createForm(this),
      saveLoading: false,
      key: '2',
      defaultMap: {
        '2.1': { textItem: { type: 1, content: null } },
        '2.1.1': { textItem: { type: 1, content: null } },
        '2.1.2': { textItem: { type: 1, content: null } },
        '2.1.3': { textItem: { type: 1, content: null } },
        '2.1.4': { textItem: { type: 1, content: null } },
        '2.1.4.1': { textItem: { type: 1, content: null }, imageItem: { type: 3, imgPath: [] } },
        '2.1.4.2': { textItem: { type: 1, content: null } },
        '2.1.5': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '序号',
                dataIndex: 'index',
                width: '20%'
              },
              {
                title: '支出类别',
                dataIndex: 'type',
                width: '20%'
              },
              {
                title: '支出金额（万元',
                dataIndex: 'amount',
                width: '20%'
              }
            ],
            data: []
          }
        },
        '2.2': { textItem: { type: 1, content: null } },
        '2.2.1': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '新设备名称',
                dataIndex: 'cname',
                width: '20%'
              },
              {
                title: '型号',
                dataIndex: 'model',
                width: '20%'
              },
              {
                title: '台数',
                dataIndex: 'quantity',
                width: '20%'
              },
              {
                title: '购置金额（元）',
                dataIndex: 'auditAmount',
                width: '20%'
              }
            ],
            data: []
          }
        },
        '2.2.2': { textItem: { type: 1, content: null } },
        '2.3': { textItem: { type: 1, content: null } },
        '2.4': { textItem: { type: 1, content: null }, imageItem: { type: 3, imgPath: [] } },
        '2.5': { textItem: { type: 1, content: null } },
        '2.6': { textItem: { type: 1, content: null } }
      },
      dataMap: {
        '2.1': { textItem: { type: 1, content: null } },
        '2.1.1': { textItem: { type: 1, content: null } },
        '2.1.2': { textItem: { type: 1, content: null } },
        '2.1.3': { textItem: { type: 1, content: null } },
        '2.1.4': { textItem: { type: 1, content: null } },
        '2.1.4.1': { textItem: { type: 1, content: null }, imageItem: { type: 3, imgPath: [] } },
        '2.1.4.2': { textItem: { type: 1, content: null } },
        '2.1.5': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '序号',
                dataIndex: 'index',
                width: '20%'
              },
              {
                title: '支出类别',
                dataIndex: 'type',
                width: '20%'
              },
              {
                title: '支出金额（万元）',
                dataIndex: 'amount',
                width: '20%'
              }
            ],
            data: []
          }
        },
        '2.2': { textItem: { type: 1, content: null } },
        '2.2.1': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '新设备名称',
                dataIndex: 'cname',
                key: 'cname',
                width: '20%'
              },
              {
                title: '型号',
                dataIndex: 'model',
                key: 'model',
                width: '20%'
              },
              {
                title: '台数',
                dataIndex: 'quantity',
                key: 'quantity',
                width: '20%'
              },
              {
                title: '购置金额（元）',
                dataIndex: 'auditAmount',
                key: 'auditAmount',
                width: '20%'
              }
            ],
            data: []
          }
        },
        '2.2.2': { textItem: { type: 1, content: null } },
        '2.3': { textItem: { type: 1, content: null } },
        '2.4': { textItem: { type: 1, content: null }, imageItem: { type: 3, imgPath: [] } },
        '2.5': { textItem: { type: 1, content: null } },
        '2.6': { textItem: { type: 1, content: null } }
      }
    }
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    }
  },
  watch: {
    projectId (newId) {
      this.loadData()
    }
  },
  created () {
    this.loadData()
  },
  computed: {
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
    handleChange (item, key) {
      if (item.file.status === 'removed') {
        this.fileList[key] = item.fileList
      }
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
              if (typeof item.textItem === 'undefined' || item.textItem === null) {
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
          this.getProjectCost()
        }).catch(res => {
          self.dataMap = {}
        })
    },
    getProjectCost () {
      this.$http.get('/techProjectCost/getProjectCost', { params: { projectId: this.projectId } })
        .then(res => {
          var table215 = [{
            index: '1',
            type: '设备',
            amount: res.data.costArr[0]
          }, {
            index: '2',
            type: '建设费',
            amount: res.data.costArr[1]
          }, {
            index: '3',
            type: '铺底流动资金',
            amount: res.data.costArr[2]
          }, {
            index: '4',
            type: '合计',
            amount: res.data.costArr[3]
          }]
          var totalAmount = { id: -1, cname: '', model: '', quantity: '合计', auditAmount: 0 }
          res.data.datas.forEach(item => {
            totalAmount.auditAmount += item.auditAmount
          })
          res.data.datas.push(totalAmount)
          this.dataMap['2.1.5'].tableItem.data = table215
          this.dataMap['2.2.1'].tableItem.data = res.data.datas
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
