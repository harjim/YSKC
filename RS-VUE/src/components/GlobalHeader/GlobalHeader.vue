<template>
  <transition name="showHeader">
    <div v-if="visible" class="header-animat">
      <a-layout-header
        v-if="visible"
        :class="[fixedHeader && 'ant-header-fixedHeader', sidebarOpened ? 'ant-header-side-opened' : 'ant-header-side-closed', ]"
        :style="{ padding: '0' }"
      >
        <div v-if="mode === 'sidemenu'" class="header">
          <a-icon
            v-if="device==='mobile'"
            class="trigger"
            :type="collapsed ? 'menu-fold' : 'menu-unfold'"
            @click="toggle"
          />
          <a-icon
            v-else
            class="trigger"
            :type="collapsed ? 'menu-unfold' : 'menu-fold'"
            @click="toggle"
          />
          <user-menu></user-menu>
        </div>
        <div v-else :class="['top-nav-header-index', theme]">
          <div class="header-index-wide">
            <div class="header-index-left">
              <logo :logoPath="logoPath" class="top-nav-header" :show-title="device !== 'mobile'" />
              <!-- <s-menu
                v-if="device !== 'mobile'"
                mode="horizontal"
                :menu="menus"
                :theme="theme"
                @breakpoint="breakpoint"
              />-->
              <a-menu
                v-if="device !== 'mobile'"
                mode="horizontal"
                :theme="theme"
                :selectedKeys="[defaultPath]"
              >
                <a-menu-item v-for="m in menus" :key="m.path">
                  <router-link
                    :to="{path:m.children && m.children.length > 0 ? m.children[0].path : m.path,...queryParam}"
                  >{{ m.meta.title }}</router-link>
                </a-menu-item>
              </a-menu>
              <a-icon
                v-else
                class="trigger"
                :type="collapsed ? 'menu-fold' : 'menu-unfold'"
                @click="toggle"
              />
            </div>
            <user-menu class="header-index-right"></user-menu>
          </div>
        </div>
      </a-layout-header>
    </div>
  </transition>
</template>

<script>
import Vue from 'vue'
import UserMenu from '../tools/UserMenu'
import store from '@/store/index'
import { ISADMIN } from '@/store/mutation-types'
// import SMenu from '../Menu/'
import Logo from '../tools/Logo'
import { mixin } from '@/utils/mixin'

export default {
  name: 'GlobalHeader',
  components: {
    UserMenu,
    //   SMenu,
    Logo
  },
  mixins: [mixin],
  props: {
    logoPath: {
      type: String,
      default: '/logo.gif',
      required: false
    },
    mode: {
      type: String,
      // sidemenu, topmenu
      default: 'sidemenu'
    },
    menus: {
      type: Array,
      required: true
    },
    theme: {
      type: String,
      required: false,
      default: 'dark'
    },
    collapsed: {
      type: Boolean,
      required: false,
      default: false
    },
    device: {
      type: String,
      required: false,
      default: 'desktop'
    },
    defaultPath: {
      type: String,
      required: false,
      default: '/dashboard/workplace'
    }
  },
  data () {
    return {
      visible: true,
      oldScrollTop: 0,
      selectedKey: '/dashboard/workplace'
    }
  },
  computed: {
    queryParam: () => {
      const companyType = store.getters.userInfo.companyType
      if (store.getters.companyId &&
       (((companyType === 1 || companyType === 3) && store.getters.userInfo.groupId !== store.getters.companyId) || Vue.ls.get(ISADMIN))) {
        return { query: { cId: store.getters.companyId } }
      } else {
        return {}
      }
    }
  },
  mounted () {
    document.addEventListener('scroll', this.handleScroll, { passive: true })
  },
  methods: {
    handleScroll () {
      if (!this.autoHideHeader) {
        return
      }
      const scrollTop = document.body.scrollTop + document.documentElement.scrollTop
      if (!this.ticking) {
        this.ticking = true
        requestAnimationFrame(() => {
          if (this.oldScrollTop > scrollTop) {
            this.visible = true
          } else if (scrollTop > 300 && this.visible) {
            this.visible = false
          } else if (scrollTop < 300 && !this.visible) {
            this.visible = true
          }
          this.oldScrollTop = scrollTop
          this.ticking = false
        })
      }
    },
    toggle () {
      this.$emit('toggle')
    }
  },
  beforeDestroy () {
    document.body.removeEventListener('scroll', this.handleScroll, true)
  }
}
</script>

<style lang="less">
.header-animat {
  position: relative;
  z-index: 2;
}
.showHeader-enter-active {
  transition: all 0.25s ease;
}
.showHeader-leave-active {
  transition: all 0.5s ease;
}
.showHeader-enter,
.showHeader-leave-to {
  opacity: 0;
}
</style>
