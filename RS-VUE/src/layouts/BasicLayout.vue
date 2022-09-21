<template>
  <a-layout :class="['layout', device]">
    <!-- SideMenu -->
    <!-- <a-drawer
      v-if="isMobile()"
      placement="left"
      :wrapClassName="`drawer-sider ${navTheme}`"
      :closable="false"
      :visible="collapsed"
      @close="drawerClose"
    >
      <side-menu
        mode="inline"
        :menus="menus"
        :theme="navTheme"
        :collapsed="false"
        :collapsible="true"
        @menuSelect="menuSelect"
      ></side-menu>
    </a-drawer>

    <side-menu
      v-else-if="isSideMenu()"
      mode="inline"
      :menus="menus"
      :theme="navTheme"
      :collapsed="collapsed"
      :collapsible="true"
    ></side-menu> -->

    <a-layout
      :class="[layoutMode, `content-width-${contentWidth}`]"
      :style="{ paddingLeft: contentPaddingLeft, minHeight: '100vh', maxHeight: '100vh'}"
    >
      <!-- layout header -->
      <global-header
        :logoPath="logoPath"
        :mode="layoutMode"
        :menus="menus"
        :theme="navTheme"
        :collapsed="collapsed"
        :device="device"
        @toggle="toggle"
        @change="headChange"
        :defaultPath="currentPath"
      />

      <a-layout>
        <!-- 右边侧边菜单栏 -->
        <template v-if="leftMenus && leftMenus.length > 0">
          <global-left ref="gLeft" :menus="leftMenus" />
          <div id="interval" @click="expendLeft">
            <div class="iconCenter">
              <a-icon v-if="!leftExpend" type="left" style="font-size:14px;" />
              <a-icon v-else type="right" style="font-size:14px;" />
            </div>
          </div>
        </template>
        <!-- layout content -->
        <a-layout>
          <!-- <a-layout-content
            :style="{ height: '100%', paddingTop: fixedHeader ? '64px' : '0' , overflow: 'auto' }"
          > -->
          <!-- <a-layout-content> -->
          <multi-tab v-if="multiTab"></multi-tab>
          <transition name="page-transition">
            <route-view />
          </transition>
          <!-- </a-layout-content> -->
          <!-- layout footer -->
          <!-- <a-layout-footer>
            <global-footer />
          </a-layout-footer> -->
        </a-layout>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script>
import { triggerWindowResizeEvent } from '@/utils/util'
import { mapState, mapActions } from 'vuex'
import { mixin, mixinDevice } from '@/utils/mixin'
import config from '@/config/defaultSettings'
import RouteView from './RouteView'
import MultiTab from '@/components/MultiTab'
import SideMenu from '@/components/Menu/SideMenu'
import GlobalHeader from '@/components/GlobalHeader'
import GlobalFooter from '@/components/GlobalFooter'
import GlobalLeft from '@/components/GlobalLeft'

export default {
  name: 'BasicLayout',
  mixins: [mixin, mixinDevice],
  components: {
    RouteView,
    MultiTab,
    SideMenu,
    GlobalHeader,
    GlobalFooter,
    GlobalLeft
  },
  data () {
    return {
      production: config.production,
      collapsed: false,
      leftColl: false,
      currentPath: undefined,
      menus: [],
      leftMenus: [],
      leftExpend: false
    }
  },
  computed: {
    ...mapState({
      // 动态主路由
      mainMenu: state => state.permission.addRouters
    }),
    logoPath () {
      // $store.state.user.info.companyLogoPath
      if (this.$store.state != null && this.$store.state.user != null && this.$store.state.user.info.companyLogoPath != null && this.$store.state.user.info.companyLogoPath.length > 0) {
        return `/static/images${this.$store.state.user.info.companyLogoPath}`
      } else {
        return '/logo.gif'
      }
    },
    contentPaddingLeft () {
      if (!this.fixSidebar || this.isMobile()) {
        return '0'
      }
      if (this.sidebarOpened) {
        return '256px'
      }
      return '80px'
    }
  },
  watch: {
    sidebarOpened (val) {
      this.collapsed = !val
    },
    '$route' () {
      this.currentPath = this.$route.matched[1].path
      this.headChange(this.currentPath)
    }
  },
  created () {
    this.menus = this.mainMenu.find(item => item.path === '/').children
    this.collapsed = !this.sidebarOpened
    this.currentPath = this.$route.matched[1].path
    this.headChange(this.currentPath)
  },
  mounted () {
    const userAgent = navigator.userAgent
    if (userAgent.indexOf('Edge') > -1) {
      this.$nextTick(() => {
        this.collapsed = !this.collapsed
        setTimeout(() => {
          this.collapsed = !this.collapsed
        }, 16)
      })
    }
  },
  methods: {
    expendLeft () {
      this.leftExpend = !this.leftExpend
      this.$refs.gLeft.setColl(this.leftExpend)
      this.setLeftExpend(this.leftExpend)
    },
    headChange (path) {
      for (let i = 0; i < this.menus.length; i++) {
        if (path === this.menus[i].path) {
          this.leftMenus = this.menus[i].children
          return
        }
      }
      this.leftMenus = []
    },
    ...mapActions(['setSidebar', 'setLeftExpend']),
    toggle () {
      this.collapsed = !this.collapsed
      this.setSidebar(!this.collapsed)
      triggerWindowResizeEvent()
    },
    paddingCalc () {
      let left = ''
      if (this.sidebarOpened) {
        left = this.isDesktop() ? '256px' : '80px'
      } else {
        left = (this.isMobile() && '0') || ((this.fixSidebar && '80px') || '0')
      }
      return left
    },
    menuSelect () {
      if (!this.isDesktop()) {
        this.collapsed = false
      }
    },
    drawerClose () {
      this.collapsed = false
    }
  }
}
</script>

<style lang="less">
@import url('../components/global.less');

/*
 * The following styles are auto-applied to elements with
 * transition="page-transition" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the page transition by editing
 * these styles.
 */

.page-transition-enter {
  opacity: 0;
}

.page-transition-leave-active {
  opacity: 0;
}

.page-transition-enter .page-transition-container,
.page-transition-leave-active .page-transition-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
#interval {
  width: 14px;
  text-align: center;
  vertical-align: middle;
  display: flex;
  align-items: center;
  .iconCenter {
    color: #1B92FF;
  }
}
#interval:hover {
  cursor:pointer;
  background-color: #e3e3e3;
}
</style>
