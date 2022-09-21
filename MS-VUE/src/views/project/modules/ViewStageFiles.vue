<template>
  <a-modal
    :width="850"
    :visible="visible"
    :maskClosable="false"
    :title="title"
    @cancel="closeModal"
  >
    <a-spin :spinning="spinning">
      <a-layout>
        <a-layout-sider
          class="bkg"
          :width="120"
        >
          <a-menu
            mode="inline"
            v-model="activeKey"
            @click="menuClick"
          >
            <a-menu-item
              v-for="(v,k,i) in stageDatas"
              :key="k"
            >
              <span>{{ i+1 }}、{{ v }}</span>
            </a-menu-item>
          </a-menu>
        </a-layout-sider>
        <a-layout>
          <a-layout-content class="bkg">

            <a-tabs
              type="card"
              v-model="tabKey"
            >
              <a-tab-pane
                key="1"
                tab="对接"
              >
                <vxe-grid
                  id="DataListTab-deliverData"
                  ref="yst"
                  auto-resize
                  :resizable="true"
                  highlight-hover-row
                  show-overflow="title"
                  show-header-overflow
                  export-config
                  :data="dockingData"
                  height="400px"
                >
                  <vxe-table-column
                    type="seq"
                    title="序号"
                    width="50"
                    header-align="center"
                  ></vxe-table-column>
                  :show-header="false">
                  <vxe-table-column
                    title="资料名"
                    field="itemName"
                    width="150"
                    header-align="center"
                  >
                    <template v-slot="{row}">
                      <span
                        v-if="row.required"
                        style="color: red;"
                      >*</span>{{ row.itemName }}
                    </template>
                  </vxe-table-column>
                  <vxe-table-column
                    title="文件格式"
                    field="pattern"
                    width="100"
                    header-align="center"
                  ></vxe-table-column>
                  <vxe-table-column
                    title="附件"
                    :min-width="400"
                  >
                    <template v-slot="{row}">
                      <span v-if="row.fileNames">
                        {{ row.fileNames.join('，') }}
                      </span>
                    </template>
                  </vxe-table-column>
                </vxe-grid>
              </a-tab-pane>
              <a-tab-pane
                key="0"
                tab="交付"
              >
                <vxe-grid
                  id="DataListTab-DockingData"
                  ref="yst"
                  auto-resize
                  :resizable="true"
                  highlight-hover-row
                  show-overflow="title"
                  show-header-overflow
                  export-config
                  :data="deliverData"
                  height="400px"
                >
                  <vxe-table-column
                    type="seq"
                    title="序号"
                    width="50"
                    header-align="center"
                  ></vxe-table-column>
                  :show-header="false">
                  <vxe-table-column
                    title="资料名"
                    field="itemName"
                    width="150"
                    header-align="center"
                  >
                    <template v-slot="{row}">
                      <span
                        v-if="row.required"
                        style="color: red;"
                      >*</span>{{ row.itemName }}
                    </template>
                  </vxe-table-column>
                  <vxe-table-column
                    title="文件格式"
                    field="pattern"
                    width="100"
                    header-align="center"
                  ></vxe-table-column>
                  <vxe-table-column
                    title="附件"
                    :min-width="400"
                  >
                    <template v-slot="{row}">
                      <span>
                        {{ row.fileName }}
                      </span>
                    </template>
                  </vxe-table-column>
                </vxe-grid>
              </a-tab-pane>
            </a-tabs>
          </a-layout-content>
        </a-layout>
      </a-layout>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      spinning: false,
      projectId: undefined,
      activeKey: [],
      tableData: {},
      sourceStages: {},
      stageDatas: [],
      deliverData: [],
      dockingData: [],
      tabKey: '1',
      title: ''
    }
  },
  created () {
    this.$getDictionary(7).then(res => {
      res.forEach((item, index) => {
        this.sourceStages[item.key] = item.value
      })
    })
  },
  methods: {
    show (projectId, companyName, year) {
      this.title = `[${companyName}]-[${year}年]资料清单`
      if (projectId !== this.projectId) {
        this.tabKey = '1'
        this.projectId = projectId
        this.loadTableData()
      }
      this.visible = true
    },
    closeModal () {
      this.visible = false
    },
    menuClick ({ item, key, keyPath }) {
      this.activeKey = [key]
      this.loadData(key)
    },
    loadTableData () {
      this.spinning = true
      this.$http.get('/techSummary/getStageFiles', { params: { projectId: this.projectId } }).then(res => {
        this.tableData = {}
        if (res.success && res.data) {
          this.tableData = res.data
        }
        const stageDatas = {}
        var first = true
        var firstKey = ''
        for (const key in this.tableData) {
          if (first) {
            first = false
            firstKey = key
          }
          stageDatas[key] = this.sourceStages[key]
        }
        this.activeKey = [firstKey]
        this.activeData = this.tableData[firstKey]
        this.loadData(firstKey)
        this.stageDatas = stageDatas
        this.spinning = false
      })
    },
    loadData (key) {
      this.deliverData = []
      this.dockingData = []
      if (this.tableData[key].deliverData) {
        this.deliverData = this.tableData[key].deliverData
      }
      if (this.tableData[key].dockingData) {
        this.dockingData = this.tableData[key].dockingData
      }
    }
  }
}
</script>
<style lang='less' scoped>
.bkg {
  background-color: #fff;
}
.layout {
  padding-left: 4px;
}
.saveDiv {
  display: flex;
}
* /deep/ .ant-tabs-card-content {
  height: calc(100% - 57px);
  overflow-y: auto;
}
* /deep/ .ant-tabs-tabpane-active {
  overflow-y: auto;
}
#tabs /deep/ .ant-tabs-left-bar {
  margin-bottom: 4px;
}
.customAddBtn {
  // margin-top: 25px;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
  color: #fff;
  font-size: 18px;
  font-weight: bolder;
  text-align: center;
  background-color: #1890ff;
}
.customAddBtn:hover {
  background-color: #40a9ff;
  cursor: pointer;
}
</style>
