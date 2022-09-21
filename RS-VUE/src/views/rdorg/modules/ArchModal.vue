<template>
  <div>
    <a-modal
      :title="title"
      :visible="visible"
      @ok="handleOk"
      width="300px"
      :maskClosable="false"
      :confirmLoading="confirmLoading"
      @cancel="closeModal"
    >
      <a-form :form="form">
        <a-form-item label="部门" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
          <!-- <a-input
            v-decorator="['deptName',{rules: [{ required: true, message: '请输入部门' }]}]"
            placeholder="请输入部门"
          /> -->
          <a-textarea :rows="4" v-decorator="['deptName',{rules: [{ required: true, message: '请输入部门' }]}]" placeholder="请输入部门"></a-textarea>
        </a-form-item>
        <a-form-item v-if="nodeType === 0 && level" label="方向" :label-col="{ span: 6}" :wrapper-col="{ span: 16 }">
          <a-radio-group
            v-decorator="['textDirection']"
            button-style="solid"
          >
            <a-radio-button :value="1">
              横向
            </a-radio-button>
            <a-radio-button :value="0">
              纵向
            </a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item v-if="nodeType === 0 && level" label="文本对齐" :label-col="{ span: 6}" :wrapper-col="{ span: 16 }">
          <!-- 对齐方式，0 左 1中 2右 -->
          <a-radio-group
            v-decorator="['align']"
            button-style="solid"
          >
            <a-radio-button :value="0">
              左
            </a-radio-button>
            <a-radio-button :value="1">
              中
            </a-radio-button>
            <a-radio-button :value="2">
              右
            </a-radio-button>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
export default {
  name: 'ArchModal',
  data () {
    return {
      title: '',
      visible: false,
      confirmLoading: false,
      params: {
        id: -1,
        parent: -1,
        level: 0,
        thisIdentity: ''
      },
      form: this.$form.createForm(this),
      nodeType: 0,
      level: 0
    }
  },
  methods: {
    showModal (key, data, year, nodeType = 0) {
      this.visible = true
      this.nodeType = nodeType
      this.level = data.level
      key === 'addChild' ? this.level = data.level + 1 : this.level = data.level
      this.$nextTick(() => {
        switch (key) {
          case 'edit':
            let textDirection = 1
            if (Number(data.textDirection) === 0) { textDirection = 0 }
            this.setDept(data.title, data.value, data.parentId, data.level, `编辑[${data.title}]`, data.key, year, textDirection, data.align, nodeType)
            break
          case 'add':
            var identityArr = data.key.split('/')
            identityArr[identityArr.length - 1] = ''
            identityArr[identityArr.length - 2] = ''
            var identity = identityArr.join('/')
            this.setDept('', -1, data.parentId, data.level, `添加同级部门[${data.title}]`, identity.substr(0, identity.length - 1), year, data.level <= 1 ? 1 : 0, data.level + 1 <= 1 ? 1 : 0, nodeType)
            break
          case 'addChild':
            this.setDept('', -1, data.value, data.level + 1, `添加下级部门[${data.title}]`, data.key, year, data.level + 1 <= 1 ? 1 : 0, data.level + 1 <= 1 ? 1 : 0, nodeType)
            break
          case 'addCommittee':
            this.setDept('', -1, data.value, data.level + 1, `委员会[${data.title}]`, data.key, year, data.level + 1 <= 1 ? 1 : 0, data.level + 1 <= 1 ? 1 : 0, nodeType)
            break
          default:
            this.setDept('', -1, -1, 0, '添加研发部门', '', year, 1, data.align, nodeType)
            break
        }
      })
    },
    setDept (name, id, parentId, level, title, identity, year, textDirection, align, nodeType = 0) {
      if (nodeType === 0 && level > 0) {
        this.form.setFieldsValue({ deptName: name, align, textDirection })
      } else {
        this.form.setFieldsValue({ deptName: name })
      }
      this.params.id = id
      this.params.parentId = parentId
      this.params.level = level
      this.params.thisIdentity = identity
      this.params.year = year
      this.params.nodeType = nodeType
      this.title = title
    },
    handleOk () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          this.params.deptName = values.deptName
          this.params.textDirection = this.nodeType || values.textDirection
          if (values.align !== undefined) {
            this.params.align = values.align
          } else {
            this.params.align = 1
          }
          if (this.level === 0) {
            this.params.textDirection = 1
          }
          this.confirmLoading = false
          this.$http.post('/rdDept/modifyRdDept', this.params)
            .then(res => {
              if (res.success && res.data) {
                this.$emit('ok', true)
                this.$message.success(this.params.id === -1 ? '添加成功' : '更新成功')
                this.$getTree('rdDept', true, this.params.year)
                this.closeModal()
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : this.params.id === -1 ? '添加失败' : '更新失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        }
      })
    },
    closeModal () {
      this.params = {
        id: -1,
        parent: -1,
        level: 0,
        thisIdentity: ''
      }
      this.nodeType = 0
      this.title = ''
      this.visible = false
      this.confirmloading = false
      this.parentId = -1
      this.id = -1
    }
  }
}
</script>

<style>
</style>
