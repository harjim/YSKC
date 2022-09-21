<!--
 * @Author: ldx
 * @Date: 2021-05-13 08:21:07
 * @LastEditTime: 2021-05-24 11:24:44
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\components\tools\UserMenu.vue
-->
<template>
  <div class="user-wrapper">
    <div class="content-box" style="font-size:14px;">
      <!-- <a-switch checked-children="显示水印" un-checked-children="隐藏水印" v-model="watermarkStatus" change="switchChange" /> -->
      <span class="action">{{ userInfo().companyName }}</span>
      <a-dropdown>
        <span class="action ant-dropdown-link user-dropdown-menu">
          <a-avatar class="avatar" size="small" :src="avatar()" />
          <span>{{ userInfo().realName }}</span>
        </span>
        <a-menu slot="overlay" class="user-dropdown-menu-wrapper">
          <a-menu-item key="2" v-if="!watermarkStatus">
            <a @click="watermarkStatus = true">
              <a-icon type="eye" />
              显示水印
            </a>
          </a-menu-item>
          <a-menu-item key="4" v-if="watermarkStatus">
            <a @click="watermarkStatus = false">
              <a-icon type="eye-invisible" />
              隐藏水印
            </a>
          </a-menu-item>
          <a-menu-item
            key="1"
            v-if="typeof userInfo().userSource==='undefined' || userInfo().userSource===0"
          >
            <a :to="{ name: 'center' }" @click="$refs.modyPasswordModal.init()">
              <a-icon type="user" />
              <span>修改密码</span>
            </a>
          </a-menu-item>
          <a-menu-item key="3">
            <a @click="handleLogout">
              <a-icon type="logout" />
              退出登录
            </a>
          </a-menu-item>
        </a-menu>
      </a-dropdown>
    </div>
    <modyPassword-modal ref="modyPasswordModal"></modyPassword-modal>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import { getStore, setStore } from '@/utils/storage'
import ModyPasswordModal from './modules/ModyPasswordModal'
export default {
  name: 'UserMenu',
  components: {
    ModyPasswordModal
  },
  data () {
    return {
      currentCompanyId: undefined,
      watermarkStatus: false
    }
  },
  mounted () {
    this.currentCompanyId = this.userInfo().companyId
    if (getStore('WATERMARK_STATUS') === false) {
      this.watermarkStatus = false
    } else {
      this.watermarkStatus = true
    }
  },

  watch: {
    '$route.path' (path) {
      if (this.watermarkStatus) {
        this.createWatermark(this.currentYear)
      }
    },
    currentYear: {
      immediate: true,
      handler (val) {
        if (val > 0 && this.watermarkStatus) {
          this.createWatermark(val)
        }
      }
    },
    watermarkStatus: {
      // immediate: true,
      handler (val) {
        val ? this.createWatermark(this.currentYear) : this.clearWatermark()
        setStore('WATERMARK_STATUS', val)
      }
    }
  },
  computed: {
    ...mapGetters(['currentYear']),
    isAddYear () {
      return !(this.$route.path.trim().startsWith('/company') || this.$route.path.trim().startsWith('/tech'))
    }
  },
  methods: {
    ...mapActions(['Logout']),
    ...mapGetters(['nickname', 'avatar', 'userInfo']),
    switchChange (checked) {
      this.watermarkStatus = checked
    },
    clearWatermark () {
      // this.watermarkStatus = false
      this.hanlderClear()
    },
    hanlderClear () {
      const id = Symbol('watermark').toString()
      const domId = document.getElementById(id)
      const symbolModalWrap = document.body
      if (domId) {
        const el = symbolModalWrap
        el && el.removeChild(domId)
      }
    },
    createWatermark (str) {
      this.hanlderClear()
      const id = Symbol('watermark').toString()
      const can = document.createElement('canvas')
      const symbolModalWrap = document.body
      can.width = 650
      can.height = 400
      const cans = can.getContext('2d')
      if (cans) {
        cans.rotate((-15 * Math.PI) / 120)
        cans.font = '50px Vedana'
        cans.fillStyle = 'rgba(0, 0, 0, 0.05)'
        cans.textAlign = 'center'
        cans.textBaseline = 'middle'
        if (str && this.isAddYear) {
          cans.fillText(str, can.width / 5, can.height / 3)
        }
        cans.font = '25px Vedana'
        cans.textAlign = 'left'
        cans.textBaseline = 'middle'
        cans.fillText(this.userInfo().companyName, 0, can.height / 1.5)
      }
      const div = document.createElement('div')
      div.id = id
      div.style.pointerEvents = 'none'
      div.style.top = '0px'
      div.style.left = '0px'
      div.style.position = 'absolute'
      div.style.zIndex = '100000'
      div.style.width = document.documentElement.clientWidth + 'px'
      div.style.height = document.documentElement.clientHeight + 'px'
      div.style.background = 'url(' + can.toDataURL('image/png') + ') left top repeat'
      const el = symbolModalWrap
      el && el.appendChild(div)
    },
    handleLogout () {
      const that = this
      this.$confirm({
        title: '提示',
        content: '真的要注销登录吗 ?',
        onOk () {
          return that.Logout({}).then(() => {
            window.location.reload()
          }).catch(err => {
            that.$message.error({
              title: '错误',
              description: err.message
            })
          })
        },
        onCancel () {
        }
      })
    }
  }
}
</script>
