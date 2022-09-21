<template>
  <a-modal
    :width="400"
    :visible="visible"
    title="专利登录"
    :footer="null"
    @cancel="closeModal"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-spin tip="请稍后..." :spinning="spin">
      <div style="height: 400px;">
        <div v-if="valid" style="margin-left:20px;">
          <div style="position:absolute;width:290px;height:200px;">
            <img id="pic" @click="ck" :src="picData" />
            <div
              @click="reloadUrl"
              id="reload"
              style="position: absolute;width: 40;height: 40;bottom:192px; left:280px;top:10px;"
            >
              <a style="color:#ddd;">
                <a-icon type="reload" style="font-size:20px;font-color:#ccc;" />
              </a>
            </div>
          </div>
          <div style="position:absolute;margin-top:220px;" v-html="validMessage"></div>
        </div>
        <div v-else>
          <b>已成功连接，当前同步数： {{ syncObj.syncCount }}/{{ syncObj.total }},状态： {{ syncObj.fail ? `同步失败，${syncObj.msg}` : syncObj.done ? '同步完成' : '正在同步中...' }}</b>
        </div>
        <div style="margin-left:20px;padding-top: 70%">
          <div v-if="valid">
            <a-form layout="inline">
              <a-row>
                <a-form-item label="账号">
                  <a-tree-select
                    v-model="accountId"
                    style="width: 180px;"
                    :dropdownStyle="{ maxHeight: '350px', overflow: 'scroll', width: '180px' }"
                    :treeData="accountTree"
                    @change="onChange"
                    placeholder="请选择账号"
                    :allowClear="true"
                    showSearch
                    treeNodeFilterProp="title"
                    treeDefaultExpandAll
                  ></a-tree-select>
                </a-form-item>
                <a-form-item label="密码">
                  <a-input
                    placeholder="请输入密码"
                    type="password"
                    style="width:180px"
                    v-model="ownerPassword"
                  />
                </a-form-item>
              </a-row>
            </a-form>
          </div>
          <div style="padding-top:20px;">
            <a-tooltip placement="top">
              <template slot="title">
                <span v-if="count != 3">请按提示点击图片中对应的文字</span>
                <span v-else-if="!account">请选择账号</span>
                <span v-else-if="!ownerPassword">请输入密码</span>
              </template>
              <a-button
                type="primary"
                v-if="valid"
                :disabled="count != 3 || !account || !ownerPassword"
                @click="validLogin"
                style="padding-left: 18px;"
              >登录</a-button>
            </a-tooltip>
          </div>
        </div>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  name: 'SyncPatentModal',
  data () {
    return {
      accountId: undefined,
      ownerPassword: undefined,
      accountTree: [],
      account: undefined,
      visible: false,
      picHeader: 'data:image/png;base64,',
      confirmLoading: false,
      count: 0,
      picData: undefined,
      validMessage: undefined,
      position: {},
      valid: true,
      spin: false,
      userName: undefined,
      password: undefined,
      ids: [],
      patentNo: undefined,
      syncObj: { syncCount: undefined, total: 0, msg: '', done: false, fail: false }
    }
  },
  methods: {
    getTree () {
      this.$http.get('/patentAccount/getPatentAccountTree')
        .then(res => {
          if (res.success && res.data) {
            this.accountTree = this.getRealData(res.data)
            if (this.accountTree.length <= 0) {
              this.$message.info('无任何有效专利账号，请先在[专利账号管理]添加一个有效账号')
            }
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '获取专利账号失败')
          }
        })
    },
    onChange (v, l, e) {
      if (e.triggerNode) {
        this.account = e.triggerNode.dataRef.source
        this.ownerPassword = this.account.password
      } else {
        this.account = undefined
        this.ownerPassword = undefined
      }
    },
    getRealData (data) {
      var result = []
      data.forEach(d => {
        result.push({ key: d.accountId, value: d.accountId + '', title: d.username, source: d })
      })
      return result
    },
    reloadUrl () {
      this.resetCheck()
      this.getPicData()
    },
    resetCheck () {
      this.position = {}
      for (let i = 1; i <= this.count; i++) {
        var current = document.getElementById(`ck${i}`)
        if (current && current.parentNode) {
          current.parentNode.removeChild(current)
        }
      }
      this.count = 0
    },
    openSync (ids) {
      this.syncObj = { syncCount: undefined, total: 0, msg: '', done: false, fail: false }
      this.total = 0
      this.syncCount = undefined
      this.ids = ids
      this.visible = true
      this.validHeart()
    },
    validHeart () {
      this.$http.post('rsPatent/validHeart', { hasSearch: true, ids: this.ids })
        .then(res => {
          if (res.success && res.data) {
            this.valid = false
          } else {
            this.valid = true
            this.reloadUrl()
            this.getTree()
          }
        })
    },
    getPicData () {
      this.spin = true
      this.$http.get('/rsPatent/validData').then(res => {
        if (res.success && res.data) {
          this.picData = this.picHeader + res.data.validPic
          this.validMessage = res.data.validMessage
        }
      }).finally(res => {
        this.spin = false
      })
    },
    ck (e) {
      this.count++
      if (this.count === 1) {
        this.position.x = e.offsetX
        this.position.y = e.offsetY
      } else if (this.count === 2) {
        this.position.x1 = e.offsetX
        this.position.y1 = e.offsetY
      } else if (this.count === 3) {
        this.position.x2 = e.offsetX
        this.position.y2 = e.offsetY
      } else {
        this.count = 3
        return
      }
      var div = document.createElement('div')
      div.style.left = e.offsetX - 15 + 'px'
      div.style.top = e.offsetY - 15 + 'px'
      div.id = `ck${this.count}`
      div.style.background = `url(/images/${this.count}.png) no-repeat`
      div.style.width = '32px'
      div.style.height = '32px'
      div.style.position = 'absolute'
      const self = this
      div.onclick = function () {
        var current = document.getElementById(`ck${self.count}`)
        if (current && current.parentNode) {
          current.parentNode.removeChild(current)
        }
        self.count--
      }
      var reload = document.getElementById('reload')
      reload.parentNode.insertBefore(div, reload)
    },
    closeModal () {
      this.visible = false
      this.account = undefined
      this.accountId = undefined
      this.ownerPassword = undefined
      this.$emit('ok', !this.valid, this.syncObj)
    },
    validLogin () {
      this.spin = true
      this.position = Object.assign(this.position, this.account)
      this.position.ownerPassword = this.ownerPassword
      if (this.ids && this.ids.length > 0) {
        this.position.ids = [...this.ids]
      }
      this.position.patentNo = this.patentNo
      this.$http.post('/rsPatent/validPosition', this.position).then(res => {
        if (res && res.data) {
          this.valid = false
          this.spin = false
          this.closeModal()
        } else {
          this.valid = true
          this.$message.error(res.errorMessage)
          this.reloadUrl()
        }
      })
    }
  }
}
</script>

<style>
</style>
