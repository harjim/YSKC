<!--
 * @Author: ldx
 * @Date: 2021-06-23 10:46:58
 * @LastEditTime: 2021-07-27 18:38:59
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\layouts\UserLayout.vue
-->
<template>
  <div
    id="userLayout"
    :class="[device]"
  >
    <div style="height: 100vh; width: 100vw; position: absolute;">
      <div class="logo_container" :class="['logo_position_'+ device]">
        <div :class="['logo_'+device]"></div>
      </div>
      <div clsss="route_container">
        <transition name="fade" mode="out-in">
          <route-view></route-view>
        </transition>
      </div>
      <div class="footer">
        <div>
          <span href="_self">帮助</span>
          <span href="_self">隐私</span>
          <span href="_self">条款</span>
        </div>
        <div >Copyright &copy; {{ year }}优赛科创<span style="padding:0 10px">V1.1.0</span></div>
      </div>
    </div>
  </div>
</template>

<script>
import RouteView from './RouteView'
import { mixinDevice } from '@/utils/mixin'
export default {
  name: 'UserLayout',
  components: { RouteView },
  mixins: [mixinDevice],
  data () {
    return {
      isUp: true,
      maxWidth: 680,
      leftSize: '90px',
      activeList: [true, false, false],
      currentActive: 0,
      canScroll: true,
      path: '',
      year: new Date().getFullYear()
    }
  },
  created () {
    this.path = window.location.pathname
  },
  mounted () {
    document.body.classList.add('userLayout')
  },
  beforeDestroy () {
    document.body.classList.remove('userLayout')
  },
  watch: {
    $route (to, from) {
      this.path = to.path
    }
  },
  methods: {

  }
}
</script>

<style lang="less" scoped>
.bg(@url) {
  background-image: url(@url);
  background-repeat:no-repeat;
  background-size: 100% 100%;
}
.logo(@w,@h) {
  display: inline-block;
  width: @w;
  height: @h;
}
.position(@top,@left) {
  position:absolute;
  top: @top;
  left: @left;
}

#userLayout {
  height: 100vh;
  position: relative;
  overflow: auto;
  // .route_container { }
  .logo_container {
     display: inline-block;
    .logo_mobile {
      .logo(80px,30px);
      .bg('/images/userLayout/logo_mobile.png')
    }
    .logo_minDesktop {
      .logo(80px,30px);
      .bg('/images/userLayout/logo_mobile.png')
    }
    .logo_tablet {
      .logo(160px,62px);
      .bg('/images/userLayout/logo_tablet.png')
    }
    .logo_desktop {
      .logo(160px,62px);
      .bg('/images/userLayout/logo_desktop.png')
    }
  }
  .logo_position_minDesktop {
    .position(26px,34px)
  }
  .logo_position_tablet {
     .position(40px,50px)
  }
  .logo_position_desktop {
    .position(40px,50px)
  }
  .footer {
    width: 100vw;
    position: fixed;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    bottom: 0;
    color: #fff;
    margin-bottom: 10px;
    span {
      padding: 0 10px;
    }
  }
}
.minDesktop {
  .bg('/images/userLayout/bg_mobile.png')
}
.tablet {
  .bg('/images/userLayout/bg_tablet.png')
}
.desktop {
  .bg('/images/userLayout/bg_desktop.png')
}

.fade-enter {
  opacity: 0;
}
.fade-leave {
  opacity: 1;
}
.fade-leave-active,.fade-enter-active {
  transition:opacity 0.5s;
}
</style>
