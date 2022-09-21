<template>
  <div class="user-wrapper">
    <div class="content-box">
      <!-- <a href="https://pro.loacg.com/docs/getting-started" target="_blank">
        <span class="action">
          <a-icon type="question-circle-o"></a-icon>
        </span>
      </a> -->
      <!-- <notice-icon class="action"/> -->
      <a-dropdown>
        <span class="action ant-dropdown-link user-dropdown-menu">
          <a-avatar
            class="avatar"
            size="small"
            :src="avatar()"
          />
          <span>{{ nickname() }} </span>
        </span>
        <a-menu
          slot="overlay"
          class="user-dropdown-menu-wrapper"
        >
          <a-menu-item
            key="1"
            @click="$refs.modyPassword.init(false)"
          >
            <!-- <router-link :to="{ name: 'settings' }"> -->
            <a-icon type="key" />
            <span>修改密码</span>
            <!-- </router-link> -->
          </a-menu-item>
          <a-menu-divider v-if="$notInDingTalk"/>
          <a-menu-item key="3" v-if="$notInDingTalk">
            <a
              href="javascript:;"
              @click="handleLogout"
            >
              <a-icon type="logout" />
              <span>退出登录</span>
            </a>
          </a-menu-item>
          <!-- <a-menu-item key="4" v-else>
            <a
              href="javascript:;"
              @click="openByBrowser"
            >
              <a-icon type="right-circle"/>
              <span>浏览器打开</span>
            </a>
          </a-menu-item> -->
        </a-menu>
      </a-dropdown>
    </div>
    <mody-password ref="modyPassword" />
  </div>
</template>

<script>
// import NoticeIcon from '@/components/NoticeIcon'
import { mapActions, mapGetters } from 'vuex'
import ModyPassword from './modules/ModyPassword'

export default {
  name: 'UserMenu',
  components: {
    ModyPassword
  },
  watch: {
    'user.reSetPassword' (value) {
      if (!value) {
        this.$refs.modyPassword.init(true)
      }
    }
  },
  data () {
    return {
      user: {}
    }
  },
  computed: {
    userInfo () {
      return this.$store.getters.userInfo
    }
  },
  created () {
    this.user = this.userInfo
  },
  methods: {
    ...mapActions(['Logout']),
    ...mapGetters(['nickname', 'avatar']),
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
    },
    openByBrowser () {
      const href = window.location.pathname
      this.$openByBrowser({ 'redirect': href || '/' })
    }
  }
}
</script>
