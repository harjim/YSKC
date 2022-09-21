<!--
 * @Author: ldx
 * @Date: 2020-10-13 09:14:38
 * @LastEditTime: 2020-11-03 10:37:02
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\TechProject.vue
-->
<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spin">
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input style="width:160px;" v-model="queryParams.companyName" placeholder="客户名称" />
        </a-form-item>
        <a-form-item label="项目名称">
          <a-input style="width:160px;" v-model="queryParams.ptName" placeholder="项目名称" />
        </a-form-item>
        <a-form-item label="年份">
          <year-select style="width:160px;" v-model="queryParams.year" placeholder="项目年份" />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select-only
            ref="deptSelect"
            @deptFullPath="path=>queryParams.fullPath = path"
            style="width:160px;"
          />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="refresh(true)" v-if="$auth('project:techProject:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <ys-table
        ref="yst"
        :resizable="true"
        auto-resize
        highlight-hover-row
        show-overflow
        show-header-overflow
        :loading="loading"
        queryUrl="/tProject/getList"
        :params="queryParams"
        export-config
        :toolbar="{
          custom: true,
          zoom:true,
          refresh:true
        }"
      >
        <template v-slot:buttons>
        </template>
        <vxe-table-column type="checkbox" width="40" />
        <vxe-table-column type="seq" title="序号" :width="70" align="center"></vxe-table-column>
        <vxe-table-column title="客户名称" field="companyName" :min-width="150" align="left" remoteSort ></vxe-table-column>
        <vxe-table-column title="项目名称" field="productName" align="left" :min-width="150" remoteSort ></vxe-table-column>
        <vxe-table-column title="申报项目名称" field="productName" align="left" :min-width="150" ></vxe-table-column>
        <vxe-table-column title="方向" field="direction" :width="120" align="left" ></vxe-table-column>
        <vxe-table-column title="年份" field="pyear" :width="100" align="center" ></vxe-table-column>
        <vxe-table-column
          title="地区"
          field="addressCode"
          :width="120"
        >
          <template v-slot="{ row }">{{ getAddres(row.addressCode) }}</template>
        </vxe-table-column>
        <vxe-table-column title="所属部门" field="deptName" :width="120" align="left" remoteSort ></vxe-table-column>
        <vxe-table-column title="操作" :width="200" align="center" fixed="right">
          <template v-slot="{row}">
            <span v-if="$auth('project:techProject:relatedProject')">
              <a @click="relevanceDeclareModal(row)">关联申报项目</a>
              <a-divider type="vertical" v-if="$auth('project:techProject:disconnect') && row.tproductId" />
            </span>
            <a-popconfirm title="是否确定取消关联?" @confirm="disassociation(row.tProjectId,row.beian)" v-if="row.tproductId">
              <a v-if="row.tproductId && $auth('project:techProject:disconnect')">取消关联</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ys-table>
    </a-spin>
    <relevance-declare-modal ref="relevanceDeclareModal" @ok="refresh"></relevance-declare-modal>
  </a-card>
</template>

<script>
import ysTable from '@/components/Table/ystable'
import { YearSelect } from '@/components/Selects'
import { DeptSelectOnly } from '@/components'
import relevanceDeclareModal from './modules/RelevanceDeclareModal'
export default {
  name: 'DeclareProject',
  components: {
    ysTable,
    YearSelect,
    DeptSelectOnly,
    relevanceDeclareModal
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
    this.$getDictionary(10).then(res => {
      for (const type of res) {
        this.typeLists.push({ value: type.key, label: type.value })
        this.typeAry[type.key] = type.value
      }
    })
  },
  data () {
    return {
      spin: false,
      loading: false,
      queryParams: {},
      selectRowIds: [],
      form: this.$form.createForm(this),
      addressCode: [],
      typeLists: [],
      typeAry: {},
      leveLists: [{ value: 0, label: '省' }, { value: 1, label: '市' }],
      levelAry: ['省', '市']
    }
  },
  methods: {
    // 表格复选框事件
    selectChange ({ records }) {
      this.selectRowIds = records.map(item => {
        return item.id
      })
    },
    relevanceDeclareModal (row) {
      this.$refs.relevanceDeclareModal.show(row)
    },
    refresh (refresh) {
      this.$refs.yst.refresh(refresh)
    },
    disassociation (tProjectId, beian) {
      this.$http.post('tProject/disassociation', { tProjectId, beian }).then((res) => {
        if (res.data && res.success) {
          this.$message.success('操作成功')
          this.refresh()
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '操作失败')
        }
      }).catch((error) => {
        if (error.message.includes('403')) {
          this.$message.error('无此权限，请跟管理员联系！')
        } else {
          this.$message.error('操作失败：' + error.message)
        }
      })
    },
    getAddres (code) {
      if (!code) {
        return ''
      }
      const arr = code.split('/')
      var currentIndex = 0
      var result = ''
      var tempChildren = this.addressCode
      while (currentIndex < arr.length) {
        for (let i = 0; i < tempChildren.length; i++) {
          if (arr[currentIndex] === tempChildren[i].key) {
            result += tempChildren[i].value + '/'
            if (tempChildren[i].children && tempChildren[i].children.length > 0) {
              tempChildren = tempChildren[i].children
            } else {
              tempChildren = []
            }
            break
          }
        }
        currentIndex++
      }
      return result ? result.substr(0, result.length - 1) : result
    }
  }
}
</script>

<style lang="less" scpoed>

</style>
