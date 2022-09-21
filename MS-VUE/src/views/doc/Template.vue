<!--
 * @Author: hck
 * @Date: 2021-05-11 15:29:45
 * @LastEditTime: 2021-08-24 17:19:07
 * @LastEditors: zdf
 * @Description: 添加是否需要编辑字段
 * @FilePath: \MS-VUE\src\views\doc\Template.vue
-->
<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="文件名">
        <a-input v-model="docName" placeholder="请输入文件名" style="width:180px" />
      </a-form-item>
      <a-form-item>
        <span v-if="$auth('doc:tempt:search')">
          <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
        </span>
      </a-form-item>
    </a-form>

    <div>
      <ystable
        ref="table"
        size="default"
        queryUrl="/docFileTemplate/getDataList"
        :params="{ docName: docName }"
        :toolbar="{
          custom: true,
          zoom: true,
          refresh: true
        }"
      >
        <!-- <template v-slot:buttons>
          <a-button
            type="primary"
            @click="$refs.addModal.add(record.id)"
            style="margin-right:10px"
            v-if="$auth('patent:patentMaster:add')"
          >添加</a-button
          >
        </template> -->
        <vxe-table-column title="文档名" field="docName" align="left"></vxe-table-column>
        <vxe-table-column title="多个" align="left">
          <template v-slot="{ row }">{{ row.multiple ? '是' : '否' }}</template>
        </vxe-table-column>
        <vxe-table-column title="启用" align="left">
          <template v-slot="{ row }">{{ row.enabled ? '是' : '否' }}</template>
        </vxe-table-column>
        <vxe-table-column title="必须" align="left">
          <template v-slot="{ row }">{{ row.required ? '是' : '否' }}</template>
        </vxe-table-column>
        <vxe-table-column title="编辑" align="left">
          <template v-slot="{ row }">{{ row.needEdit ? '是' : '否' }}</template>
        </vxe-table-column>
        <vxe-table-column title="所属" align="left">
          <template v-slot="{ row }">{{ row.owner ? '财务' : '技术' }} </template>
        </vxe-table-column>
        <vxe-table-column title="唯一" align="left">
          <template v-slot="{ row }">{{ uniqueType[row.uniqueness] }} </template>
        </vxe-table-column>
        <vxe-table-column title="操作" field="action" align="center" :width="200">
          <template v-slot="{ row }">
            <a v-if="$auth('doc:tempt:add')" @click="$refs.addModal.add(row.id)">添加模板</a>
            <a-divider type="vertical" />
            <a v-if="$auth('doc:tempt:edit')" @click="$refs.editModal.edit(row)">编辑</a>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
    <template-modal ref="addModal" @ok="handleOk" />
    <edit-doc-file-modal ref="editModal" @ok="handleOk" />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import TemplateModal from './modules/TemplateModal'
import EditDocFileModal from './modules/EditDocFileModal'
export default {
  name: 'Doc',
  components: {
    ystable,
    TemplateModal,
    EditDocFileModal
  },
  data () {
    return {
      uniqueType: ['不限制', '阶段', '月份', '年', '项目'],
      form: this.$form.createForm(this),
      docName: ''
    }
  },
  methods: {
    handleOk (flag) {
      if (flag === 0) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(flag)
    },
    openUploadModal () {
      this.$refs.uploadModal.show()
    }
  }
}
</script>
<style scoped></style>
