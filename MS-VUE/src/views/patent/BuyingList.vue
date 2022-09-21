<!--
 * @Author: ldx
 * @Date: 2021-07-08 09:39:17
 * @LastEditTime: 2021-09-23 15:30:17
 * @LastEditors: zdf
 * @Description: 购买列表
 * @FilePath: \MS-VUE\src\views\patent\BuyingList.vue
-->
<template>
  <a-card v-if="auth.search">
    <div class="form_wrap">
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input placeholder="请输入客户名称" v-model="queryParams.companyName" />
        </a-form-item>
        <a-form-item label="业务员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="请输入业务员"
            style="width:165px;"
            v-model="queryParams.ownerId"
          />
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            :allowClear="true"
            style="width: 120px"
            v-model="queryParams.status"
            placeholder="请选择状态"
            :options="statusList"
          >
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="success(true)" :disabled="btnDisable">查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <div class="table_wrap">
      <ystable
        ref="table"
        queryUrl="/patentBuying/getBuyDemandList"
        :params="getParams()"
        border="full"
        header-align="center"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :toolbar="{ custom: true, zoom:true, refresh:true }"
      >
        <!-- @completed="({data:{data}})=>completed(data)" :checkbox-config="{ checkMethod: checCheckboxkMethod,showHeader: showHeaderChk}" -->
        <template #buttons>
          <!-- <a-popconfirm
            title="您确定要删除？"
            @confirm="onDelDemand"
            v-if="auth.delDemand"
          >
            <a-button
              type="primary"
              style="margin-right: 10px;"
              :disabled="selectedRows.length <= 0 || btnDisable"
            >删除</a-button>
          </a-popconfirm> -->
        </template>
        <!-- <vxe-table-column type="checkbox" width="40" align="center" header-align="center"></vxe-table-column> -->
        <vxe-table-column type="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column
          field="companyName"
          title="客户名称"
          width="150"
          align="left"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            <a @click="onEdit(row)" >{{ row.companyName }}</a>
          </template>
        </vxe-table-column>
        <vxe-table-column field="year" title="年份" width="100" align="center" remoteSort></vxe-table-column>
        <vxe-table-column
          field="realName"
          title="业务员"
          width="100"
          align="left"
          header-align="center"
          remoteSort></vxe-table-column>
        <vxe-table-column width="180" align="left" header-align="center">
          <template #header>类型(<span style="color:blue;">需求</span>/<span style="color:green;">购买</span>)</template>
          <template #default="{row}">
            <span v-for="(item,index) in getTypeName(row)" :key="index" :title="`${item.name}：需求(${item.demand}),已购买(${item.buy})`" style="margin-right:5px;">
              {{ item.name }}:(<span style="color:blue;">{{ item.demand }}</span>/<span style="color:green;">{{ item.buy }}</span>)
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="status" title="状态" width="100" align="center" remoteSort>
          <template #default="{row}">
            {{ statusMap[row.status] }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="需求文档" minWidth="200" align="left" header-align="center">
          <template #default="{row}">
            <span v-for="(file,index) in transFormStrToAry(row.filePath)" :key="index">
              <a
                v-if="auth.download"
                @click="onDownloadFile({path: file.url,name: file.name })"
                title="点击下载需求文档"
              >{{ file.name }}</a>
              <span v-else>{{ file.name }}</span>
              <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.url,name: file.name })" v-if="file.url && auth.preview" >
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin:0 5px" />
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="购买详情" width="90" align="center" header-align="center">
          <template #default="{row}">
            <a @click="showDetail(row)" v-if="row.status >= 2 && auth.detailed">详情</a>
            <span v-else>--</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="发明人信息" minWidth="200" align="left" header-align="center">
          <template #default="{row}">
            <span v-for="(file,index) in transFormStrToAry(row.inventorInfo)" :key="index">
              <a
                v-if="auth.download"
                @click="onDownloadFile({path: file.url,name: file.name })"
                title="点击下载发明人信息"
              >{{ file.name }}</a>
              <span v-else>{{ file.name }}</span>
              <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.url,name: file.name })" v-if="file.url && auth.preview" >
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin:0 5px" />
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="createTime"
          title="创建时间"
          width="120"
          align="center"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ row.createTime | DateFormat }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="remark" title="备注" width="120" align="left" header-align="center"></vxe-table-column>
      </ystable>
    </div>
    <demand-form-modal ref="demandFormModal" :status="0" @success="success" :previewCB="onPreview" :auth="auth"></demand-form-modal>
    <preview-modal ref="previewModal"/>
    <purchase-list-drawer ref="purchaseListDrawer" :previewCB="onPreview" :auth="auth" @ok="success"></purchase-list-drawer>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import demandFormModal from './modules/DemandFormModal'
import PreviewModal from '@/components/PreviewModal'
import { SearchSelect } from '@/components/Selects'
// import { delDemand } from '@/api/patent/demandList'
import { pick } from 'lodash'
import { getAuth } from '@/utils/util'
import PurchaseListDrawer from './modules/PurchaseListDrawer'
export default {
  name: 'DemandList',
  components: {
    ystable,
    demandFormModal,
    PreviewModal,
    SearchSelect,
    PurchaseListDrawer
  },
  data () {
    return {
      typeStr: {
        invention: '发明',
        utility: '实用',
        appearanceDesign: '外观'
      },
      queryParams: {},
      auth: {},
      // selectedRows: [],
      statusList: [
        { value: 0, label: '未提交' },
        { value: 1, label: '提交' },
        { value: 2, label: '确认' },
        { value: 3, label: '完成' }
      ],
      statusMap: {
        0: '未提交',
        1: '提交',
        2: '确认',
        3: '完成'
      },
      showHeaderChk: true,
      btnDisable: false
    }
  },
  mounted () {
    const keys = [
      `search`,
      // `addDemand`,
      `editDemand`,
      `submit`,
      `confirm`,
      `completed`,
      `selected`,
      `addPatent`,
      `editPatent`,
      `delPatent`,
      `chooseBuy`,
      `buy`,
      `detailed`,
      `download`,
      `preview`,
      // `delDemand`,
      'buyBack',
      'exportCanBuy'
    ]
    this.auth = getAuth('patent:buyingList:', keys)
  },
  methods: {
    getParams () {
      const params = Object.assign({}, this.queryParams)
      if (params.ownerId && params.ownerId.id) {
        params.ownerId = params.ownerId.id
      }
      return params
    },
    onAddDemand () {
      this.$refs.demandFormModal.show('添加需求')
    },
    onEdit (record) {
      record.type = 0
      this.$refs.demandFormModal.edit(record, '编辑需求')
    },
    transFormStrToAry (filePaths) {
      const files = []
      filePaths.split(',').forEach(path => {
        const name = path.substring(path.lastIndexOf('/') + 14)
        files.push({
          uid: path,
          name,
          status: 'done',
          url: path
        })
      })
      return files
    },
    success () {
      this.$refs.table.refresh()
    },
    onDownloadFile ({ path, name }) {
      this.$exportData('/patentBuying/download', { fileName: name, filePath: path }, name, this.$message)
    },
    onPreview ({ path, name }) {
      if (path === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(path, name)
    },
    getTypeName (record) {
      const data = pick(record, ['inventionNum', 'utilityNum', 'appearanceDesignNum', 'inventionCnt', 'utilityCnt', 'appearanceDesignCnt'])
      const result = []
      for (const key in this.typeStr) {
        const num = data[`${key}Num`]
        const cnt = data[`${key}Cnt`]
        if (num || cnt) {
          result.push({ name: this.typeStr[key], demand: num || '-', buy: cnt || '-' })
        }
      }
      return result
    },
    showDetail (record) {
      this.$refs.purchaseListDrawer.show(record)
    }
    // checCheckboxkMethod ({ row }) {
    //   return row.status <= 0
    // }
    // completed (data) {
    //   this.selectedRows = []
    //   if (data && data.length) {
    //     this.showHeaderChk = data.some(item => { return item.status <= 0 })
    //   } else {
    //     this.showHeaderChk = false
    //   }
    // }
    // onDelDemand () {
    //   const postParam = this.selectedRows.map(record => {
    //     return { id: record.id }
    //   })
    //   this.btnDisable = true
    //   delDemand(postParam).then(data => {
    //     if (data) {
    //       this.success()
    //       this.$message.success('操作成功！')
    //     }
    //   }).catch(error => {
    //     this.$message.error(error.message || '系统异常，请联系管理员！')
    //   }).finally(() => {
    //     this.btnDisable = false
    //   })
    // }
  }
}
</script>

<style lang="less" scoped>

</style>
