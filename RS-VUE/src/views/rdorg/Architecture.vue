<template>
  <a-spin :spinning="spinning" tip="请稍后...">
    <div v-if="$auth('rdorg:arch:rd:view')">
      <div>
        <span style="padding-left:30%;">
          <a-button
            v-if="isFirstOne && hasAdd"
            @click="onClick({key: undefined},{level: 0})"
            type="primary"
          >新增</a-button>
          <span v-if="prevYears && prevYears.length > 0 && isFirstOne">
            <a-button
              v-if="$auth('rdorg:arch:rd:import')"
              @click="$refs.importModal.showModal(prevYears,currentYear)"
              type="primary"
              style="margin-left:20px;"
            >导入</a-button>
          </span>
        </span>
      </div>
      <div style="text-align: center;">
        <Org
          style="padding: 0.6em;"
          v-show="!isFirstOne"
          ref="org"
          :data="treeNodes"
          :currentYear="currentYear"
          :onClick="onClick"
          :sortNode="sortNode"
          :modify="modify"></Org>
      </div>
      <arch-modal
        ref="archModal"
        @ok="loadTree"
      ></arch-modal>
      <import-arch-modal
        ref="importModal"
        @ok="loadTree"
      />
      <clone-arch-modal
        ref="cloneArchModal"
        @ok="loadTree"
      />
    </div>
  </a-spin>
</template>
<script>
import ImportArchModal from './modules/ImportArchModal'
import ArchModal from './modules/ArchModal'
import Org from '@/components/Org/index.js'
import yearMiXin from '@/utils/yearMixin'
import CloneArchModal from './modules/CloneArchModal'
import { mapGetters } from 'vuex'
import { isEditStatus, isExportStatus } from '@/utils/processDoc/auditStatus'
import domToImage from 'dom-to-image'
import { cloneDeep } from 'lodash'
export default {
  mixins: [yearMiXin],
  name: 'Arch',
  components: {
    ArchModal,
    ImportArchModal,
    CloneArchModal,
    Org
  },
  data () {
    return {
      spinning: false,
      treeNodes: {},
      isFirstOne: false,
      prevYears: [],
      hasAdd: false,
      hasClone: false,
      hasEdit: false,
      hasDel: false,
      modify: false,
      clientHeight: document.body.clientHeight
    }
  },
  watch: {
    auditStatus: {
      // immediate: true,
      handler (val) {
        this.modify = this.isEditStatus(val) || !this.userInfo.userSource
      }
    }
  },
  computed: {
    ...mapGetters(['auditStatus', 'userInfo'])
  },
  created () {
    this.hasAdd = this.$auth('rdorg:arch:rd:add')
    this.hasClone = this.$auth('rdorg:arch:rd:clone')
    this.hasEdit = this.$auth('rdorg:arch:rd:edit')
    this.hasDel = this.$auth('rdorg:arch:rd:del')
  },
  mounted () {
    this.modify = this.isEditStatus(this.auditStatus) || !this.userInfo.userSource
    this.loadTree()
  },
  methods: {
    isEditStatus,
    isExportStatus,
    search () {
      this.loadTree()
    },
    loadTree (isSaveImage) {
      if (!this.$auth('rdorg:arch:rd:view')) {
        this.$notification.error({ message: '无此权限，请跟管理员联系！' })
        return
      }
      this.spinning = true
      this.treeNodes = {}
      this.$http.get('/rdDept/getList', { params: { year: this.currentYear } })
        .then(res => {
          if (res.success && res.data) {
            const cpData = res.data
            if (cpData && cpData.data.length <= 0) {
              this.treeNodes = {}
              this.isFirstOne = true
              this.prevYears = cpData.prevYears
            } else {
              this.prevYears = []
              this.treeNodes = cpData.data[0]
              this.transformation(this.treeNodes)
              this.toggleExpand(this.treeNodes, true)
              this.isFirstOne = false
            }
            if (isSaveImage) {
              this.$getTree('rdDept', true, self.currentYear)
              this.$nextTick(() => {
                this.onSaveImage()
              })
            }
          } else {
            this.prevYears = []
            this.treeNodes = {}
            this.$message.error(res.errorMessage ? res.errorMessage : '加载研发部门失败，请稍后再试')
          }
          return this.treeNodes
        }).finally(() => {
          this.spinning = false
        })
    },
    toggleExpand (data, val) {
      var _this = this
      if (Array.isArray(data)) {
        data.forEach(function (item) {
          _this.$set(item, 'expand', val)
          if (item.children) {
            _this.toggleExpand(item.children, val)
          }
        })
      } else {
        this.$set(data, 'expand', val)
        if (data.children) {
          _this.toggleExpand(data.children, val)
        }
      }
    },
    onSaveImage (isShowMessage) {
      domToImage.toBlob(this.$refs.org.$el).then(res => {
        this.uploadOrgImg(res, isShowMessage)
      })
    },
    uploadOrgImg (blob, isShowMessage) {
      const param = new FormData()
      param.append('file', blob)
      param.append('beginYear', this.currentYear)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/projectDocFileData/uploadOrgImg', param, config).then((res) => {
        if (res.data && res.success) {
          if (isShowMessage) {
            this.$message.success(`组织架构图片生成成功`)
          }
        } else {
          console.error(`uploadOrgImg functin error : ${res.errorMessage}`)
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
        console.error(`uploadOrgImg functin error : ${error.message}`)
      }).finally((res) => {
        if (isShowMessage) {
          this.$emit('onBtnLoading', false)
        }
      })
    },
    dataURLToBlob (dataurl) {
      const arr = dataurl.split(',')
      const mime = arr[0].match(/:(.*?);/)[1]
      const bstr = atob(arr[1])
      let n = bstr.length
      const u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new Blob([u8arr], { type: mime })
    },
    onClick (keyObj, node, nodeType = 0) {
      if (keyObj.key === 'del') {
        this.delete(node)
      } else if (keyObj.key === 'cloneNode') {
        this.$refs.cloneArchModal.showModal(this.treeNodes, node, this.currentYear, nodeType)
      } else if (keyObj.key === 'addCommittee') {
        this.$refs.archModal.showModal(keyObj.key, node, this.currentYear, nodeType)
      } else {
        this.$refs.archModal.showModal(keyObj.key, node, this.currentYear, nodeType)
      }
    },
    sortNode (id, left) {
      this.spinning = true
      this.$http.post('/rdDept/sortNode', { id, left }).then(res => {
        if (res.success && res.data) {
          this.loadTree()
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '排序失败，请稍后重试。')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    delete (node) {
      if (node.children !== null && node.children !== undefined && node.children.length > 0) {
        this.$message.info('当前部门存在子部门，请先删除子部门')
      } else {
        const self = this
        this.$confirm({
          title: `您确定要删除部门[${node.title}]吗?`,
          onOk () {
            return self.$http.post('/rdDept/del', { id: node.value, year: self.currentYear })
              .then(res => {
                if (res.success) {
                  self.$message.success('删除成功')
                  self.loadTree(true)
                  self.$getTree('rdDept', true, self.currentYear)
                } else {
                  self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
                }
              })
          },
          onCancel () { }
        })
      }
    },
    transformation (treeNodes) {
      if (treeNodes.committee && treeNodes.committee.length) {
        const tmpAry = []
        const committee = cloneDeep(treeNodes.committee)
        for (var i = 0; i < committee.length; i += 2) {
          tmpAry.push(committee.slice(i, i + 2))
        }
        this.$set(treeNodes, 'committee', tmpAry)
      }
    }
  }
}
</script>

<style type="text/css" scoped>
.org-tree-node-label {
  white-space: nowrap;
}
/* .bg-white {
  background-color: white;
} */

.bg-yellowgreen {
  background-color: yellowgreen;
}
.org-tree-node-label .org-tree-node-label-inner {
  padding: 0;
}
</style>
