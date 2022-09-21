<template>
  <a-layout
    id="components-layout-demo-side1"
    style="background: #fff; padding: 0"
  >
    <a-layout-sider
      collapsible
      :trigger="null"
      style="flex:0 0 300px; max-width: 350px; min-width: 10px; width: 350px;background: rgb(255, 255, 255);"
    >
      <a-anchor :affix="false">
        <a-anchor-link
          title="4.1 经济效益或效果分析"
        >
          <a-anchor-link
            title="4.1.1 产能规模"
          />
          <a-anchor-link
            title="4.1.2 财务、风险分析"
          >
            <a-anchor-link
              title="4.1.2.1 分析依据"
            />
            <a-anchor-link
              title="4.1.2.2 生产成本与费用"
            />
            <a-anchor-link
              title="4.1.2.3 销售收入、税金和利润"
            />
            <a-anchor-link
              title="4.1.2.4 财务评价"
            />
            <a-anchor-link
              title="4.1.2.5 不确定分析"
            />
            <a-anchor-link
              title="4.1.2.6 结论与建议"
            />
          </a-anchor-link>
        </a-anchor-link>
        <a-anchor-link
          title="4.2 行业影响分析"
        />
        <a-anchor-link
          title="4.3 社会影响效果分析"
        >
          <a-anchor-link
            title="4.3.1 就业和收入分配情况"
          />
          <a-anchor-link
            title="4.3.2 项目对所在地区不同利益群体的影响"
          />
          <a-anchor-link
            title="4.3.3 社会适应性分析"
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
                <a-card
                  title="4.1 经济效益或效果分析"
                  :bordered="false"
                  id="link_4_1"
                >
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.1.1 产能规模
                        <a id="link_4_1_1" />
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.1.1'].textItem.content"
                      placeholder="产能规模"
                    />
                    <div style="padding:10px">
                      <a-button
                        type="primary"
                        @click="addRow('t1')"
                      >添加</a-button>
                      <a-table
                        size="small"
                        :pagination="false"
                        bordered
                        :columns="dataMap['4.1.1'].tableItem.column"
                        :dataSource="dataMap['4.1.1'].tableItem.data"
                        rowKey="id"
                      >
                        <template
                          slot="time"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.time"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="t1Content"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.content"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="prod"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.prod"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="price"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.price"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="total"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.total"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="t1Action"
                          slot-scope="text,row"
                        >
                          <a @click="removeRow(row,'t1')">移除</a>
                        </template>
                      </a-table>
                    </div>
                  </a-form-item>
                  <span>
                    4.1.2财务、风险分析
                    <a id="link_4_1_2"></a>
                  </span>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.1.2.1分析依据
                        <a id="link_4_1_2_1"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.1.2.1'].textItem.content"
                      placeholder="分析依据"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.1.2.2生产成本与费用
                        <a id="link_4_1_2_2"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.1.2.2'].textItem.content"
                      placeholder="生产成本与费用"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.1.2.3销售收入、税金和利润
                        <a id="link_4_1_2_3"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.1.2.3'].textItem.content"
                      placeholder="3销售收入、税金和利润"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.1.2.4财务评价
                        <a id="link_4_1_2_4"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.1.2.4'].textItem.content"
                      placeholder="4财务评价"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.1.2.5不确定分析
                        <a id="link_4_1_2_5"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.1.2.5'].textItem.content"
                      placeholder="5不确定分析"
                    />
                    <div style="padding:10px">
                      <a-button
                        type="primary"
                        @click="addRow('t2')"
                      >添加</a-button>
                      <a-table
                        size="small"
                        :pagination="false"
                        bordered
                        :columns="dataMap['4.1.2.5'].tableItem.column"
                        :dataSource="dataMap['4.1.2.5'].tableItem.data"
                        rowKey="id"
                      >
                        <template
                          slot="col1"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.col1"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="col2"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.col2"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="col3"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.col3"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="col4"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.col4"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="col5"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.col5"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="col6"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.col6"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="col7"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.col7"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="col8"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.col8"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="t2Action"
                          slot-scope="text,row"
                        >
                          <a @click="removeRow(row,'t2')">移除</a>
                        </template>
                      </a-table>
                    </div>
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.1.2.6结论与建议
                        <a id="link_4_1_2_6"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.1.2.6'].textItem.content"
                      placeholder="6结论与建议"
                    />
                    <div style="padding:10px">
                      <a-button
                        type="primary"
                        @click="addRow('t3')"
                      >添加</a-button>
                      <a-table
                        size="small"
                        :pagination="false"
                        bordered
                        :columns="dataMap['4.1.2.6'].tableItem.column"
                        :dataSource="dataMap['4.1.2.6'].tableItem.data"
                        rowKey="id"
                      >
                        <template
                          slot="t3Content"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.content"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="amount"
                          slot-scope="text,row"
                        >
                          <a-input
                            v-model="row.amount"
                            style="width:100%"
                          />
                        </template>
                        <template
                          slot="t3Action"
                          slot-scope="text,row"
                        >
                          <a @click="removeRow(row,'t3')">移除</a>
                        </template>
                      </a-table>
                    </div>
                  </a-form-item>
                </a-card>
                <a-card
                  title="4.2 行业影响分析"
                  :bordered="false"
                  id="link_4_2"
                >
                  <a-form-item>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.2'].textItem.content"
                      placeholder="行业影响分析"
                    />
                  </a-form-item>
                </a-card>
                <a-card
                  title="4.3 社会影响效果分析"
                  :bordered="false"
                  id="link_4_3"
                >
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.3.1 就业和收入分配情况
                        <a id="link_4_3_1"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.3.1'].textItem.content"
                      placeholder="就业和收入分配情况"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.3.2 项目对所在地区不同利益群体的影响
                        <a id="link_4_3_2"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.3.2'].textItem.content"
                      placeholder="项目对所在地区不同利益群体的影响"
                    />
                  </a-form-item>
                  <a-form-item>
                    <template slot="label">
                      <span>
                        4.3.3 社会适应性分析
                        <a id="link_4_3_3"></a>
                      </span>
                    </template>
                    <a-textarea
                      rows="4"
                      v-model="dataMap['4.3.3'].textItem.content"
                      placeholder="社会适应性分析"
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
  name: 'ImpactAnalysisPane',
  data () {
    return {
      defaultT1: { id: 1, time: '', content: '', prod: '', price: '', total: '' },
      defaultT3: { id: 1, content: '', amount: '' },
      defaultT2: { id: 1, col1: '', col2: '', col3: '', col4: '', col5: '', col6: '', col7: '', col8: '' },
      previewVisible: false,
      previewImage: '',
      fileList: {
      },
      form: this.$form.createForm(this),
      saveLoading: false,
      key: '4',
      imgMap: {},
      defaultMap: {
        '4.1': { textItem: { type: 1, content: null } },
        '4.1.1': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '时间',
                dataIndex: 'time',
                width: '20%',
                scopedSlots: { customRender: 'time' }
              },
              {
                title: '内容',
                dataIndex: 'content',
                width: '20%',
                scopedSlots: { customRender: 't1Content' }
              },
              {
                title: '产品年产量（吨）',
                dataIndex: 'prod',
                width: '20%',
                scopedSlots: { customRender: 'prod' }
              },
              {
                title: '产品单价（元/千克）',
                dataIndex: 'price',
                width: '20%',
                scopedSlots: { customRender: 'price' }
              },
              {
                title: '产品年产值（万元）',
                dataIndex: 'total',
                width: '20%',
                scopedSlots: { customRender: 'total' }
              },
              {
                title: '操作',
                dataIndex: 'action',
                width: '20%',
                scopedSlots: { customRender: 't1Action' }
              }
            ],
            data: [
            ]
          }
        },
        '4.1.2': { textItem: { type: 1, content: null } },
        '4.1.2.1': { textItem: { type: 1, content: null } },
        '4.1.2.2': { textItem: { type: 1, content: null } },
        '4.1.2.3': { textItem: { type: 1, content: null } },
        '4.1.2.4': { textItem: { type: 1, content: null } },
        '4.1.2.6': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '内容',
                dataIndex: 'content',
                width: '20%',
                scopedSlots: { customRender: 't3Content' }
              },
              {
                title: '数值',
                dataIndex: 'amount',
                width: '20%',
                scopedSlots: { customRender: 'amount' }
              },
              {
                title: '操作',
                dataIndex: 'action',
                width: '20%',
                scopedSlots: { customRender: 't3Action' }
              }
            ],
            data: [
            ]
          }
        },
        '4.1.2.5': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '',
                dataIndex: 'col1',
                width: '12%',
                scopedSlots: { customRender: 'col1' }
              },
              {
                title: '15%',
                dataIndex: 'col2',
                width: '13%',
                scopedSlots: { customRender: 'col2' }
              },
              {
                title: '10%',
                dataIndex: 'col3',
                width: '12%',
                scopedSlots: { customRender: 'col3' }
              },
              {
                title: '0',
                dataIndex: 'col4',
                width: '13%',
                scopedSlots: { customRender: 'col4' }
              },
              {
                title: '-10%',
                dataIndex: 'col5',
                width: '12%',
                scopedSlots: { customRender: 'col5' }
              },
              {
                title: '-15%',
                dataIndex: 'col6',
                width: '13%',
                scopedSlots: { customRender: 'col6' }
              },
              {
                title: '平均+1%',
                dataIndex: 'col7',
                width: '12%',
                scopedSlots: { customRender: 'col7' }
              },
              {
                title: '平均-1%',
                dataIndex: 'col8',
                width: '13%',
                scopedSlots: { customRender: 'col8' }
              },
              {
                title: '操作',
                dataIndex: 'action',
                scopedSlots: { customRender: 't2Action' }
              }
            ],
            data: [
            ]
          }
        },
        '4.2': { textItem: { type: 1, content: null } },
        '4.3': { textItem: { type: 1, content: null } },
        '4.3.1': { textItem: { type: 1, content: null } },
        '4.3.2': { textItem: { type: 1, content: null } },
        '4.3.3': { textItem: { type: 1, content: null } }
      },
      dataMap: {
        '4.1': { textItem: { type: 1, content: null } },
        '4.1.1': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '时间',
                dataIndex: 'time',
                width: '20%',
                scopedSlots: { customRender: 'time' }
              },
              {
                title: '内容',
                dataIndex: 'content',
                width: '20%',
                scopedSlots: { customRender: 't1Content' }
              },
              {
                title: '产品年产量（吨）',
                dataIndex: 'prod',
                width: '20%',
                scopedSlots: { customRender: 'prod' }
              },
              {
                title: '产品单价（元/千克）',
                dataIndex: 'price',
                width: '20%',
                scopedSlots: { customRender: 'price' }
              },
              {
                title: '产品年产值（万元）',
                dataIndex: 'total',
                width: '20%',
                scopedSlots: { customRender: 'total' }
              },
              {
                title: '操作',
                dataIndex: 'action',
                width: '20%',
                scopedSlots: { customRender: 't1Action' }
              }
            ],
            data: [
            ]
          }
        },
        '4.1.2': { textItem: { type: 1, content: null } },
        '4.1.2.1': { textItem: { type: 1, content: null } },
        '4.1.2.2': { textItem: { type: 1, content: null } },
        '4.1.2.3': { textItem: { type: 1, content: null } },
        '4.1.2.4': { textItem: { type: 1, content: null } },
        '4.1.2.6': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '内容',
                dataIndex: 'content',
                width: '20%',
                scopedSlots: { customRender: 't3Content' }
              },
              {
                title: '数值',
                dataIndex: 'amount',
                width: '20%',
                scopedSlots: { customRender: 'amount' }
              },
              {
                title: '操作',
                dataIndex: 'action',
                width: '20%',
                scopedSlots: { customRender: 't3Action' }
              }
            ],
            data: [
            ]
          }
        },
        '4.1.2.5': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '',
                dataIndex: 'col1',
                width: '12%',
                scopedSlots: { customRender: 'col1' }
              },
              {
                title: '15%',
                dataIndex: 'col2',
                width: '13%',
                scopedSlots: { customRender: 'col2' }
              },
              {
                title: '10%',
                dataIndex: 'col3',
                width: '12%',
                scopedSlots: { customRender: 'col3' }
              },
              {
                title: '0',
                dataIndex: 'col4',
                width: '13%',
                scopedSlots: { customRender: 'col4' }
              },
              {
                title: '-10%',
                dataIndex: 'col5',
                width: '12%',
                scopedSlots: { customRender: 'col5' }
              },
              {
                title: '-15%',
                dataIndex: 'col6',
                width: '13%',
                scopedSlots: { customRender: 'col6' }
              },
              {
                title: '平均+1%',
                dataIndex: 'col7',
                width: '12%',
                scopedSlots: { customRender: 'col7' }
              },
              {
                title: '平均-1%',
                dataIndex: 'col8',
                width: '13%',
                scopedSlots: { customRender: 'col8' }
              },
              {
                title: '操作',
                dataIndex: 'action',
                width: '20%',
                scopedSlots: { customRender: 't2Action' }
              }
            ],
            data: [
            ]
          }
        },
        '4.2': { textItem: { type: 1, content: null } },
        '4.3': { textItem: { type: 1, content: null } },
        '4.3.1': { textItem: { type: 1, content: null } },
        '4.3.2': { textItem: { type: 1, content: null } },
        '4.3.3': { textItem: { type: 1, content: null } }
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
    },
    removeRow (row, t) {
      switch (t) {
        case 't1':
          this.dataMap['4.1.1'].tableItem.data = this.tRemoveRow([...this.dataMap['4.1.1'].tableItem.data], row)
          break
        case 't2':
          this.dataMap['4.1.2.5'].tableItem.data = this.tRemoveRow([...this.dataMap['4.1.2.5'].tableItem.data], row)
          break
        case 't3':
          this.dataMap['4.1.2.6'].tableItem.data = this.tRemoveRow([...this.dataMap['4.1.2.6'].tableItem.data], row)
          break
      }
    },
    tRemoveRow (table, delRow) {
      var result = []
      var id = 0
      table.forEach(item => {
        if (item.id !== delRow.id) {
          item.id = ++id
          result.push(item)
        }
      })
      return result
    },
    addRow (t) {
      switch (t) {
        case 't1':
          this.dataMap['4.1.1'].tableItem.data = this.tAddRow([...this.dataMap['4.1.1'].tableItem.data], this.defaultT1)
          break
        case 't2':
          this.dataMap['4.1.2.5'].tableItem.data = this.tAddRow([...this.dataMap['4.1.2.5'].tableItem.data], this.defaultT2)
          break
        case 't3':
          this.dataMap['4.1.2.6'].tableItem.data = this.tAddRow([...this.dataMap['4.1.2.6'].tableItem.data], this.defaultT3)
          break
      }
    },
    tAddRow (table, defaultRow) {
      var copy = { ...defaultRow }
      if (table) {
        copy.id = table.length + 1
      } else {
        table = []
      }
      table.push(copy)
      return table
    }
  }
}
</script>
<style scoped>
</style>
