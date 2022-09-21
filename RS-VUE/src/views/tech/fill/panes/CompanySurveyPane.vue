<template>
  <a-layout
    id="components-layout-demo-side"
    style="background: #fff; padding: 0"
  >
    <a-layout-sider
      collapsible
      :trigger="null"
      style="flex:0 0 250px; max-width: 350px; min-width: 10px; width: 350px;background: rgb(255, 255, 255);"
    >
      <a-anchor :affix="false">
        <a-anchor-link
          href="#link_1"
          title="1.1 项目企业设立情况"
        />
        <a-anchor-link
          href="#link_2"
          title="1.2 股权结构"
        />
        <a-anchor-link
          href="#link_3"
          title="1.3 历史沿革"
        />
        <a-anchor-link
          href="#link_4"
          title="1.4 主要股东概况"
        />
        <a-anchor-link
          href="#link_5"
          title="1.5 主营业务情况"
        />
        <a-anchor-link
          href="#link_6"
          title="1.6 在行业中的地位和竞争力"
        />
        <a-anchor-link
          href="#link_7"
          title="1.7 公司现有生产、研发能力"
        />
        <a-anchor-link
          href="#link_8"
          title="1.8 近期财务状况"
        />
        <a-anchor-link
          href="#link_9"
          title="1.9 主要投资项目"
        />
        <a-anchor-link
          href="#link_10"
          title="1.10未来发展战略"
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
                <a-form-item>
                  <template slot="label">
                    <span>1.1项目企业设立情况<a id="link_1" /></span>
                  </template>
                  <a-textarea
                    rows="4"
                    v-model="dataMap['1.1'].textItem.content"
                    placeholder="公司成立时间，注册资金，地址，经营范围"
                  />
                </a-form-item>
                <a-form-item>
                  <template slot="label">
                    <span>1.2股权结构<a id="link_2"></a></span>
                  </template>
                  <a-textarea
                    rows="6"
                    v-model="dataMap['1.2'].textItem.content"
                    placeholder="股东构成，股东出资，股东持股比例"
                  />
                  <a-table
                    :pagination="false"
                    bordered
                    :columns="dataMap['1.2'].tableItem.column"
                    :dataSource="dataMap['1.2'].tableItem.data"
                    rowKey="id"
                  >
                    <template slot="title">
                      <a-button
                        type="primary"
                        size="small"
                        @click="refresh('1.2')"
                        :loading="loading['1.2']"
                      >刷新</a-button>
                    </template>
                  </a-table>
                </a-form-item>
                <a-form-item>
                  <template slot="label">
                    <span>1.3历史沿革<a id="link_3"></a></span>
                  </template>
                  <a-textarea
                    rows="6"
                    v-model="dataMap['1.3'].textItem.content"
                    placeholder="重大事件按年份罗列，可插图说明"
                  />
                </a-form-item>
                <a-form-item>
                  <template slot="label">
                    <span>1.4主要股东概况<a id="link_4"></a></span>
                  </template>
                  <a-textarea
                    rows="4"
                    v-model="dataMap['1.4'].textItem.content"
                    placeholder="单个介绍所列股东概况"
                  />
                </a-form-item>
                <a-form-item>
                  <template slot="label">
                    <span>1.5主营业务情况<a id="link_5"></a></span>
                  </template>
                  <a-textarea
                    rows="4"
                    v-model="dataMap['1.5'].textItem.content"
                    placeholder="主营产品、产品性能、产品用途"
                  />
                </a-form-item>
                <a-form-item>
                  <template slot="label">
                    <span>1.6在行业中的地位和竞争力<a id="link_6"></a></span>
                  </template>
                  <a-textarea
                    rows="8"
                    v-model="dataMap['1.6'].textItem.content"
                    placeholder="企业技术优势、产品的优越性、市场占有率、行业排名和地位、获得荣誉、品牌战略"
                  />
                  <div class="clearfix">
                    <a-upload
                      listType="picture-card"
                      :fileList="fileList['1.6']"
                      @preview="handlePreview"
                      @change="(fileList)=>handleChange(fileList,'1.6')"
                      :beforeUpload="(file)=>beforeUpload(file,'1.6')"
                    >
                      <div v-if="fileList['1.6'].length < 10">
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
                    <span>1.7公司现有生产、研发能力<a id="link_7"></a></span>
                  </template>
                  <a-textarea
                    rows="8"
                    v-model="dataMap['1.7'].textItem.content"
                    placeholder="现有生产人员人数、设备技术投入、设备技术先进性；现有产品种类、产品产能、产品优越性；现有研发机构、研发人员人数、学历、研发设备、研发成果、产学研合作情况"
                  />
                  <div class="clearfix">
                    <a-upload
                      listType="picture-card"
                      :fileList="fileList['1.7']"
                      @preview="handlePreview"
                      @change="(fileList)=>handleChange(fileList,'1.7')"
                      :beforeUpload="(file)=>beforeUpload(file,'1.7')"
                    >
                      <div v-if="fileList['1.7'].length < 10">
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
                    <span>1.8近期财务状况<a id="link_8"></a></span>
                  </template>
                  <a-textarea
                    rows="4"
                    v-model="dataMap['1.8'].textItem.content"
                    placeholder="企业近三年经济效益"
                  />
                  <a-table
                    :pagination="false"
                    bordered
                    :columns="dataMap['1.8'].tableItem.column"
                    :dataSource="dataMap['1.8'].tableItem.data"
                    rowKey="id"
                  >
                    <template slot="title">
                      <a-button
                        type="primary"
                        size="small"
                        @click="refresh('1.8')"
                        :loading="loading['1.8']"
                      >刷新</a-button>
                    </template>
                  </a-table>
                </a-form-item>
                <a-form-item>
                  <template slot="label">
                    <span>1.9主要投资项目<a id="link_9"></a></span>
                  </template>
                  <a-textarea
                    rows="8"
                    v-model="dataMap['1.9'].textItem.content"
                    placeholder="企业近年设备投资项目、研发投资费用"
                  />
                </a-form-item>
                <a-form-item>
                  <template slot="label">
                    <span>1.10未来发展战略<a id="link_10"></a></span>
                  </template>
                  <a-textarea
                    rows="12"
                    v-model="dataMap['1.10'].textItem.content"
                    placeholder="企业内部的发展规划和发展目标，如技术革新规划、人才培养计划、产品质量要求等等"
                  />
                </a-form-item>
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
  name: 'CompanySurveyPane',
  data () {
    return {
      previewVisible: false,
      previewImage: '',
      loading: {
        '1.2': false,
        '1.8': false
      },
      fileList: {
        '1.6': [],
        '1.7': []
      },
      useOwenData: false,
      useFinancial: false,
      form: this.$form.createForm(this),
      saveLoading: false,
      key: '1',
      imgMap: {},
      defaultMap: {
        '1.1': { textItem: { type: 1, content: null } },
        '1.2': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '股东',
                dataIndex: 'shareholder',
                width: '20%'
              },
              {
                title: '认缴出资额',
                dataIndex: 'capitalContribution',
                width: '20%'
              },
              {
                title: '持股比例',
                dataIndex: 'proportion',
                width: '20%'
              }
            ],
            data: []
          }
        },
        '1.3': { textItem: { type: 1, content: null } },
        '1.4': { textItem: { type: 1, content: null } },
        '1.5': { textItem: { type: 1, content: null } },
        '1.6': {
          textItem: { type: 1, content: null },
          imageItem: { type: 3, imgPath: [] }
        },
        '1.7': { textItem: { type: 1, content: null } },
        '1.8': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '',
                dataIndex: 'name',
                width: '20%'
              },
              {
                title: '2016',
                dataIndex: 'year1',
                width: '20%'
              },
              {
                title: '2017',
                dataIndex: 'year2',
                width: '20%'
              },
              {
                title: '2018',
                dataIndex: 'year3',
                width: '20%'
              }
            ],
            data: [
            ]
          }
        },
        '1.9': { textItem: { type: 1, content: null } },
        '1.10': { textItem: { type: 1, content: null } }
      },
      dataMap: {
        '1.1': { textItem: { type: 1, content: null } },
        '1.2': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '股东',
                dataIndex: 'shareholder',
                width: '20%'
              },
              {
                title: '认缴出资额',
                dataIndex: 'capitalContribution',
                width: '20%'
              },
              {
                title: '持股比例',
                dataIndex: 'proportion',
                width: '20%'
              }
            ],
            data: [
            ]
          }
        },
        '1.3': { textItem: { type: 1, content: null } },
        '1.4': { textItem: { type: 1, content: null } },
        '1.5': { textItem: { type: 1, content: null } },
        '1.6': {
          textItem: { type: 1, content: null },
          imageItem: { type: 3, imgPath: [] }
        },
        '1.7': { textItem: { type: 1, content: null } },
        '1.8': {
          textItem: { type: 1, content: null },
          tableItem: {
            type: 2,
            column: [
              {
                title: '',
                dataIndex: 'name',
                width: '20%'
              },
              {
                title: '2016',
                dataIndex: 'year1',
                width: '20%'
              },
              {
                title: '2017',
                dataIndex: 'year2',
                width: '20%'
              },
              {
                title: '2018',
                dataIndex: 'year3',
                width: '20%'
              }
            ],
            data: []
          }
        },
        '1.9': { textItem: { type: 1, content: null } },
        '1.10': { textItem: { type: 1, content: null } }
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
    this.getOwenData()
    this.queryFinancialCondList()
  },
  mounted () {
  },
  computed: {
  },
  watch: {
    projectId (newId) {
      this.loadData()
    }
  },
  methods: {
    refresh (key) {
      this.loading[key] = true
      this.$set(this.dataMap[key].tableItem, 'column', this.defaultMap[key].tableItem.column)
      this.$set(this.dataMap[key].tableItem, 'data', this.defaultMap[key].tableItem.data)
      this.loading[key] = false
    },
    queryFinancialCondList () {
      if (this.defaultMap['1.8'].tableItem.data.length === 0) {
        var todayDate = new Date()
        var todayYear = todayDate.getFullYear()
        var years = [todayYear - 1, todayYear - 2, todayYear - 3]
        this.$http.get('/financialCondition/getFinancialCondList', { params: { years: years } })
          .then(res => {
            if (res.data.length > 0) {
              var columns = []
              var datas = []
              var keys = { totalAssets: '企业总资产', netAssets: '企业净资产', mainBusinessIncome: '主营业务收入', totalProfit: '利润总额', netProfit: '净利润', totalTax: '纳税总额' }
              columns.push({
                title: '',
                dataIndex: 'name',
                width: '20%'
              }
              )
              if (res.success && res.data.length > 0) {
                for (var i = 0; i < 3; i++) {
                  var item = res.data[i]
                  var myYear = years[i]
                  var dataIndex = 'year' + (i + 1)
                  if (typeof item !== 'undefined' && item !== null && item.year === myYear) {
                    columns.push({
                      title: item.year,
                      dataIndex: dataIndex,
                      width: '20%'
                    })
                    Object.keys(item).map(key => {
                      if (Object.keys(keys).indexOf(key) !== -1) {
                        var itemData = datas.find(a => a.id === key)
                        if (typeof itemData === 'undefined' || itemData === null) {
                          itemData = {
                            id: key,
                            name: keys[key]
                          }
                          itemData[dataIndex] = item[key]
                          datas.push(itemData)
                        } else {
                          itemData[dataIndex] = item[key]
                        }
                      }
                    })
                  } else {
                    columns.push({
                      title: myYear,
                      dataIndex: dataIndex,
                      width: '20%'
                    })
                  }
                }
              } else {

              }
              this.$set(this.defaultMap['1.8'].tableItem, 'column', columns)
              this.$set(this.defaultMap['1.8'].tableItem, 'data', datas)
              if (this.useFinancial) {
                this.$set(this.dataMap['1.8'].tableItem, 'column', columns)
                this.$set(this.dataMap['1.8'].tableItem, 'data', datas)
              }
            }
            return res.data
          })
      } else {
        if (this.useFinancial) {
          this.$set(this.dataMap['1.8'].tableItem, 'column', this.defaultMap['1.8'].tableItem.column)
          this.$set(this.dataMap['1.8'].tableItem, 'data', this.defaultMap['1.8'].tableItem.data)
        }
      }
    },
    getOwenData () {
      if (this.defaultMap['1.2'].tableItem.data.length === 0) {
        this.$http.get('/ownership/queryOwnershipList')
          .then(res => {
            this.$set(this.defaultMap['1.2'].tableItem, 'data', res.data)
            if (this.useOwenData) {
              this.$set(this.dataMap['1.2'].tableItem, 'data', res.data)
            }
            return res.data
          })
      } else {
        if (this.useOwenData) {
          this.$set(this.dataMap['1.2'].tableItem, 'data', this.defaultMap['1.2'].tableItem.data)
        }
      }
    },
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
                if (item.key === '1.2') {
                  self.useOwenData = true
                }
                if (item.key === '1.8') {
                  self.useFinancial = true
                }
              } else {
                if (item.key === '1.2') {
                  self.useOwenData = false
                }
                if (item.key === '1.8') {
                  self.useFinancial = false
                }
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
            if (self.useOwenData) {
              self.getOwenData()
            }
            if (self.useFinancial) {
              self.queryFinancialCondList()
            }
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
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
