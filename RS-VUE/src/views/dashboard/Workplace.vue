<!--
 * @Author: ldx
 * @Date: 2021-05-27 08:18:30
 * @LastEditTime: 2022-03-29 16:02:57
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\views\dashboard\Workplace.vue
-->
<template>
  <div style="height: calc( 100vh )" :class="['background_'+device]" class="background">
    <a-modal :width="400" v-model="visible" :footer="null" :closable="false" destroyOnClose>
      <div style="font-weight: 500;font-size: 16px;line-height: 1.4;"><a-icon
        style="color:#faad14;margin-right: 16px;font-size: 22px;"
        type="exclamation-circle" />基本信息不完善，请先完善单位基本信息</div>
      <br>
      <div style="text-align: center">
        <a-button type="primary" @click="goToBaseInfoPage">去完善</a-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import { mixinDevice } from '@/utils/mixin.js'
import router from '@/router'
import store from '@/store/index'
import Vue from 'vue'
import { ISADMIN } from '@/store/mutation-types'
export default {
  name: 'Workplace',
  mixins: [mixinDevice],
  data () {
    return {
      visible: false
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
  created () {
    this.getCompanyFinished()
  },
  methods: {
    getCompanyFinished () {
      this.$http('/company/getCompanyFinished').then(res => {
        if (res.success) {
          if (!res.data) {
            this.visible = true
          }
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      })
    },
    goToBaseInfoPage () {
      this.visible = false
      router.push({ path: '/company/Info', ...this.queryParam }).catch(() => {})
    }
  }
}
</script>
<style lang="less" scoped>

.background_minDesktop {
  background-image: url('/images/workplace/workplace_minDesktop.png');
  background-repeat:no-repeat;
  background-size: 100% 100%;
}
.background_tablet {
  background-image: url('/images/workplace/workplace_tablet.png');
  background-repeat:no-repeat;
  background-size: 100% 100%;
}
.background_desktop {
  background-image: url('/images/workplace/workplace_desktop.png');
  background-repeat:no-repeat;
  background-size: 100% 100%;
}
</style>
