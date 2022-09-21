<template>
  <div :style="{ height: maxContentHeight + 'px', overflow: 'auto'}">
    <page-header
      v-if="!$route.meta.hiddenHeaderContent"
      :title="pageTitle"
      :logo="logo"
      :avatar="avatar"
      :hasYearSelect="hasYearSelect"
    >
      <!-- <slot slot="action" name="action"></slot>
      <slot slot="content" name="headerContent"></slot>
      <div slot="content" v-if="!this.$slots.headerContent && description">
        <p style="font-size: 14px;color: rgba(0,0,0,.65)">{{ description }}</p>
        <div class="link">
          <template v-for="(link, index) in linkList">
            <a :key="index" :href="link.href">
              <a-icon :type="link.icon" />
              <span>{{ link.title }}</span>
            </a>
          </template>
        </div>
      </div>
      <slot slot="extra" name="extra">
        <div class="extra-img">
          <img v-if="typeof extraImage !== 'undefined'" :src="extraImage" />
        </div>
      </slot>
      <div slot="pageMenu">
        <div class="page-menu-search" v-if="search">
          <a-input-search
            style="width: 80%; max-width: 522px;"
            placeholder="请输入..."
            size="large"
            enterButton="搜索"
          />
        </div>
        <div class="page-menu-tabs" v-if="tabs && tabs.items">
          <a-tabs :tabBarStyle="{margin: 0}" :activeKey="tabs.active()" @change="tabs.callback" >
            <a-tab-pane v-for="item in tabs.items" :tab="item.title" :key="item.key" />
          </a-tabs>
        </div>
      </div> -->
    </page-header>
    <div class="content" style="height: calc(100% - 108px); overflow: auto">
      <slot>
        <!-- keep-alive  -->
        <keep-alive v-if="multiTab">
          <router-view ref="content" />
        </keep-alive>
        <router-view v-else ref="content" />
      </slot>
    </div>
    <div>
      <global-footer />
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import PageHeader from '@/components/PageHeader'
import GlobalFooter from '@/components/GlobalFooter'

export default {
  name: 'PageView',
  components: {
    PageHeader,
    GlobalFooter
  },
  props: {
    avatar: {
      type: String,
      default: null
    },
    title: {
      type: [String, Boolean],
      default: true
    },
    logo: {
      type: String,
      default: null
    },
    directTabs: {
      type: Object,
      default: null
    },
    hasYearSelect: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      pageTitle: null,
      description: null,
      linkList: [],
      extraImage: '',
      search: false,
      tabs: {},
      clientHeight: document.body.clientHeight
    }
  },
  computed: {
    ...mapState({
      multiTab: state => state.app.multiTab
    }),
    contentHeight () {
      // 184px 分别是：最上部导航栏高度 64px  +  下部的头部 59px + 下部的头 37px + 中部的外边距 24px
      return this.clientHeight - 184 * 1
    },
    maxContentHeight () {
      return this.clientHeight - 64 * 1
    }
  },
  created () { },
  mounted () {
    this.tabs = this.directTabs
    this.getPageMeta()
    // const that = this
    // window.onresize = () => {
    //   return (() => {
    //     window.screenheight = document.body.clientHeight
    //     that.screenheight = window.screenheight
    //   })()
    // }

    // this.clientHeight = document.body.clientHeight
    // const _this = this
    // window.onresize = () => {
    //   console.log(document.body.clientHeight)
    //   _this.clientHeight = document.body.clientHeight
    // }
    this.getPageHeight()
  },
  updated () {
    this.getPageMeta()
    this.getPageHeight()
  },
  methods: {
    getPageMeta () {
      // eslint-disable-next-line
      this.pageTitle = typeof this.title === 'string' || !this.title ? this.title : this.$route.meta.title
      const content = this.$refs.content
      if (content) {
        if (content.pageMeta) {
          Object.assign(this, content.pageMeta)
        } else {
          this.description = content.description
          this.linkList = content.linkList
          this.extraImage = content.extraImage
          this.search = content.search === true
          this.tabs = content.tabs
        }
      }
    },
    getPageHeight () {
      this.clientHeight = document.body.clientHeight
      const _this = this
      window.onresize = () => {
        _this.clientHeight = document.body.clientHeight
      }
    }
  }
}
</script>

<style lang="less" scoped>
.content {
  margin-top: 12px;
  .link {
    margin-top: 16px;
    &:not(:empty) {
      margin-bottom: 16px;
    }
    a {
      margin-right: 32px;
      height: 24px;
      line-height: 24px;
      display: inline-block;
      i {
        font-size: 24px;
        margin-right: 8px;
        vertical-align: middle;
      }
      span {
        height: 24px;
        line-height: 24px;
        display: inline-block;
        vertical-align: middle;
      }
    }
  }
}
.page-menu-search {
  text-align: center;
  margin-bottom: 16px;
}
.page-menu-tabs {
  margin-top: 48px;
}

.extra-img {
  margin-top: -60px;
  text-align: center;
  width: 195px;

  img {
    width: 100%;
  }
}

.mobile {
  .extra-img {
    margin-top: 0;
    text-align: center;
    width: 96px;

    img {
      width: 100%;
    }
  }
}
</style>
