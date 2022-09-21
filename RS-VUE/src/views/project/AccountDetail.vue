<template>
  <a-card>
    <a-layout style="background: #fff; padding: 0">
      <a-layout-sider
        :trigger="null"
        style="max-width: 175px; min-width: 10px; width: 175px;background: rgb(255, 255, 255);"
        collapsible
        v-model="collapsed"
      >
        <a-menu mode="inline" class="menu-style" :defaultSelectedKeys="['10']" @click="menuClick">
          <a-menu-item v-for="(v,k) in keyTypeMap" :key="k">
            <span>{{ v.name }}</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-content
          :style="{ margin: '0px 10px', padding: '24px', background: '#fff', minHeight: '280px' }"
        >
          <detail-card :ref="`fillCard${selectKey}`" :key="`${selectKey}`"></detail-card>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-card>
</template>
<script>
import RdAccountDetail from './detail/RdAccountDetail'
import MakeDetail from './detail/MakeDetail'
import PayableDetail from './detail/PayableDetail'
import ProdDetail from './detail/ProdDetail'
const keyTypeMap = {
  '10': {
    'name': '管理费用明细账',
    'component': RdAccountDetail
  },
  '20': {
    'name': '制造费用明细账',
    'component': MakeDetail
  },
  '30': {
    'name': '应付工资明细账',
    'component': PayableDetail
  },

  '40': {
    'name': '生产成本明细账',
    'component': ProdDetail
  }
}
export default {
  name: 'AccountDetail',
  components: {
    'DetailCard': {
      functional: true,
      render: function (createElement, context) {
        const d = context.data
        return createElement(
          keyTypeMap[d.key].component,
          context.data,
          context.children
        )
      }
    }
  },
  data () {
    return {
      keyTypeMap: keyTypeMap,
      collapsed: false,
      selectKey: '10'
    }
  },
  methods: {
    menuClick (e) {
      if (this.selectKey !== e.key) {
        this.selectKey = e.key
      }
    }
  }
}
</script>
<style lang="less" scoped>
.menu-style {
  border: '0';
  width: 'auto';
  padding-top: 12px;
}
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>
