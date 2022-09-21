<template>
  <a-modal
    title="操作"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    destroyOnClose
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item label="上级菜单:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-tree-select
          showSearch
          v-decorator="['parentId',{rules:[{required: true, message: '请选择菜单'}]}]"
          :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
          :treeData="menuTree"
          treeNodeFilterProp="title"
          placeholder="Please select"
          treeDefaultExpandAll
          @change="changeParentId"
        ></a-tree-select>
        <a-input v-decorator="['level']" style="display:none"/>
      </a-form-item>

      <a-form-item label="菜单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-radio-group
          v-decorator="['type', {initialValue: 0, rules: [{required: true}]}]"
          style="width: 100%"
        >
          <a-radio :value="0">目录</a-radio>
          <a-radio :value="1">菜单</a-radio>
          <a-radio :value="3">模块</a-radio>
          <a-radio :value="2">按钮</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="菜单名称:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="['name', {rules:[{required: true, message: '请输入菜单名称或按钮名称'}]}]"/>
      </a-form-item>

      <a-form-item label="请求地址:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="['url', {rules:[{required: true, message: '请输入请求地址'}]}]" @blur="wrapperPerms"/>
      </a-form-item>

      <a-form-item label="授权标识:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input
          v-decorator="['perms', {rules:[{required: true, message: '多个用逗号分隔,如：user:list,user:create'}]}]"
        />
      </a-form-item>

      <a-form-item label="显示排序:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input-number
          :min="1"
          v-decorator="['seq', {rules:[{required: true, message: '请输入显示排序'}]}]"
        />
      </a-form-item>

      <a-form-item label="图标:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="['icon', {rules:[{required: true, message: '请输入图标代码'}]}]"/>
      </a-form-item>

      <a-form-item v-if="msType === 1" label="平台" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-radio-group
          v-decorator="['platform', {initialValue: 0, rules: [{required: true}]}]"
          style="width: 100%"
        >
          <a-radio :value="0">电脑端</a-radio>
          <a-radio :value="1">移动端</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label="菜单状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-radio-group
          v-decorator="['status', {initialValue: 1, rules: [{required: true}]}]"
          style="width: 100%"
        >
          <a-radio :value="1">显示</a-radio>
          <a-radio :value="0">隐藏</a-radio>
        </a-radio-group>
      </a-form-item>
      <template
        v-if="msType===2">
        <a-form-item
          label="菜单类别"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select
            placeHolder="请选择菜单类别"
            v-decorator="['menuType',{initialValue: 0,rules:[{required: true, message: '请选择菜单类别'}]}]"
            style="width: 100%">
            <a-select-option :value="0">普通菜单</a-select-option>
            <a-select-option :value="1">集团菜单</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="有无年份"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-checkbox
            v-decorator="['hasYearSelect']"
            style="width: 100%"
            @change="isHasYearChange"
            :checked="isHasYearfun"
          />
        </a-form-item>
      </template>
      <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-textarea v-decorator="['remark']"></a-textarea>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'MenuModal',
  computed: {
    isHasYearfun: function () {
      return this.isHasYear
    }
  },
  data () {
    return {
      menuTree: [],
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      msType: 0,
      isHasYear: false,
      form: this.$form.createForm(this)
    }
  },
  created () {
  },
  methods: {
    isHasYearChange (e) {
      this.isHasYear = e.target.checked
    },
    add (msType, menuTree) {
      this.isHasYear = false
      this.title = '操作'
      this.visible = true
      this.confirmLoading = false
      this.msType = msType
      this.form.resetFields()
      this.id = -1
      this.menuTree = menuTree
    },
    edit (record, menuTree, msType) {
      this.title = '修改菜单'
      this.visible = true
      this.msType = msType
      this.form.resetFields()
      this.id = record.id
      this.menuTree = menuTree
      this.isHasYear = record.hasYearSelect
      this.$nextTick(() => {
        const data = {
          parentId: record.parentId.toString(),
          level: record.level.toString(),
          type: record.type,
          name: record.name,
          url: record.url,
          perms: record.perms,
          seq: record.seq.toString(),
          icon: record.icon,
          status: record.status,
          remark: record.remark
        }
        if (this.msType === 2) {
          data.menuType = record.menuType
        }
        this.form.setFieldsValue(data)
      })
    },
    changeParentId (v) {
      const deepSearch = (tree, v) => {
        for (let i = 0; i < tree.length; i++) {
          if (tree[i].children && tree[i].children.length > 0) {
            deepSearch(tree[i].children, v)
          }
          if (v === tree[i].value) {
            this.form.setFieldsValue({ seq: tree[i].children.length + 1 })
            return
          }
        }
      }

      deepSearch(this.menuTree, v)
    },
    // 根据请求地址生成标识和icon
    wrapperPerms (e) {
      if (this.form.getFieldValue('perms')) return
      const arr = e.target.value.split('/')
      arr.splice(0, 1)
      const result = arr.map(i => i.replace(i[0], i[0].toLowerCase())).join(':')
      let iconTitle = ''
      if (arr.length > 2) {
        iconTitle = arr[arr.length - 1].replace(arr[arr.length - 1][0], arr[arr.length - 1][0].toLowerCase())
      } else {
        iconTitle = 'solution'
      }
      this.form.setFieldsValue({ perms: result, icon: iconTitle })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            values.menuManageType = this.msType
            this.$http.post('/menu/add', {
              name: values.name,
              remark: values.remark,
              type: values.type,
              parentId: values.parentId,
              url: values.url,
              perms: values.perms,
              seq: values.seq,
              icon: values.icon,
              platform: values.platform,
              status: values.status,
              menuManageType: this.msType,
              hasYearSelect: values.hasYearSelect,
              menyTupe: values.menuType
            })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.menuManageType = this.msType
            this.$http.post('/menu/update', {
              id: this.id,
              name: values.name,
              remark: values.remark,
              type: values.type,
              parentId: values.parentId,
              url: values.url,
              perms: values.perms,
              seq: values.seq,
              icon: values.icon,
              platform: values.platform,
              status: values.status,
              menuManageType: this.msType,
              hasYearSelect: this.isHasYear,
              menuType: values.menuType
            })
              .finally(res => {
                this.confirmLoading = false
                this.visible = false
                this.$emit('ok', false)
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>
