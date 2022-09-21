<!-- 企业产品管理 -->
<template>
  <a-card :bodyStyle="{ height: '100%', overflow: 'auto' }" :bordered="false">
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form layout="inline">
        <a-form-item label="产品名称">
          <a-input
            v-model="queryParams.pname"
            placeholder="请输入产品名称"
            style="width:200px" />
        </a-form-item>
        <a-form-item label="产品编码">
          <a-input
            v-model="queryParams.pcode"
            placeholder="请输入产品编码"
            style="width:200px" />
        </a-form-item>
        <a-form-item label="产品型号">
          <a-input
            v-model="queryParams.model"
            placeholder="请输入产品型号"
            style="width:200px" />
        </a-form-item>
        <a-form-item>
          <a-button
            v-if="$auth('company:product:search')"
            style="margin-right: 10px;"
            type="primary"
            @click="tableRefresh(true)"
          >查询
          </a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          :columns="columns"
          :params="queryParams"
          :seq-config="{startIndex: 1}"
          :toolbar="tableToolbar"
          auto-resize
          highlight-hover-row
          queryUrl="/product/getList"
          resizable
          show-overflow
          @completed="({data: {footer: outputValue}}) => { count.outputValue = outputValue }"
        >
          <template v-slot:buttons>
            <a-button
              v-if="$auth('company:product:add')"
              style="margin-right:10px"
              type="primary"
              @click="$refs.addProduct.showModal()"
            >添加
            </a-button>
            <span>总产值：<b style="color: #1890ff">{{ count.outputValue
            }}万元</b></span>
          </template>
          <template v-slot:output="{ row }">
            <show-product-message
              :pid="row.id"
              :pname="row.pname"
              :poutput="row.output"
              :unit="row.unit"
              :ref="row.id"
            ></show-product-message>
          </template>
          <template v-slot="{ row }">
            --
          </template>
          <template v-slot:operate="{ row }">
            <span v-if="$auth('company:product:outputManage')">
              <a
                @click="$refs.productManage.showModal(row.pname, row.id, row.creationDate, row.unit)">产值管理</a>
              <a-divider
                v-if="$auth('company:product:edit') || $auth('company:product:delete')"
                type="vertical" />
            </span>
            <span v-if="$auth('company:product:edit') || $auth('company:product:finaEdit')">
              <a @click="$refs.editProduct.showModal(row)">编辑</a>
              <a-divider
                v-if="$auth('company:product:delete')"
                type="vertical" />
            </span>
            <span v-if="$auth('company:product:delete')">
              <a-popconfirm
                cancel-text="取消"
                ok-text="确定"
                placement="topRight"
                @confirm="handleDel(row.id, row.pname)">
                <template slot="title">
                  <p>是否确定删除</p>
                </template>
                <a>删除</a>
              </a-popconfirm>
            </span>
          </template>
        </ystable>
      </div>
      <edit-product-modal
        ref="editProduct"
        @tableRefresh="tableRefresh"></edit-product-modal>
      <product-manage-modal
        ref="productManage"
        @tableRefresh="tableRefresh"></product-manage-modal>
      <add-product-modal
        ref="addProduct"
        @tableRefresh="tableRefresh"></add-product-modal>
    </a-spin>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import EditProductModal from './modules/EditProductModal'
import ProductManageModal from './modules/ProductManageModal'
import ShowProductMessage from './modules/ShowProductMessage'
import AddProductModal from './modules/AddProductModal'

export default {
  name: 'Employee',
  components: {
    ystable,
    EditProductModal,
    ProductManageModal,
    ShowProductMessage,
    AddProductModal
  },
  data () {
    return {
      tableToolbar: {
        refresh: true,
        zoom: true,
        custom: true
      },
      columns: [
        {
          title: '序号',
          type: 'seq',
          fixed: 'left',
          width: 60,
          headerAlign: 'center',
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          field: 'pname',
          title: '产品名称',
          fixed: 'left',
          minWidth: 150,
          headerAlign: 'center',
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'pcode',
          title: '产品编码',
          minWidth: 150,
          headerAlign: 'center',
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'creationDate',
          title: '产品创建时间',
          minWidth: 150,
          headerAlign: 'center',
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'model',
          title: '产品型号',
          minWidth: 150,
          headerAlign: 'center',
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'unit',
          title: '单位',
          minWidth: 150,
          headerAlign: 'center',
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          title: '总产量',
          slots: { default: 'output' },
          minWidth: 150,
          headerAlign: 'center',
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'outputValue',
          title: '总产值(万元)',
          minWidth: 150,
          headerAlign: 'center',
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'parameter',
          title: '技术性能参数',
          minWidth: 150,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        },
        {
          field: 'features',
          title: '特征及用途',
          minWidth: 150,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        },
        {
          field: 'mainRaw',
          title: '主要材料',
          minWidth: 150,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        },
        {
          field: 'comparison',
          title: '同行对比优势劣势',
          minWidth: 150,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        },
        {
          title: '成果/检测证书',
          slots: { default: 'default' },
          minWidth: 150,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        },
        {
          title: '高品关系',
          slots: { default: 'default' },
          minWidth: 150,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        },
        {
          title: '操作',
          slots: { default: 'operate' },
          fixed: 'right',
          minWidth: 170,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        }
      ],
      spinning: false,
      form: this.$form.createForm(this),
      queryParams: {},
      count: {}
    }
  },
  methods: {
    handleDel (id, pname) {
      this.$http.post('/product/del', { id }).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.tableRefresh(false)
        } else {
          this.$message.error(`[${pname}]${res.errorMessage ? res.errorMessage : '删除失败'}`)
        }
      })
    },
    tableRefresh (refresh, productId = null) {
      this.$refs.table.refresh(refresh)
      if (productId) {
        this.$refs[productId].selectData(true)
      }
    }
  }
}
</script>

<style scoped>
</style>
