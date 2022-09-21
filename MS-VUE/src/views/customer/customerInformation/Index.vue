<!--
 * @Author: ldx
 * @Date: 2020-12-04 11:41:25
 * @LastEditTime: 2020-12-23 16:46:59
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\Index.vue
-->
<template>
  <a-drawer
    :zIndex="99"
    :title="title"
    placement="right"
    :closable="true"
    :visible="visible"
    :width="width"
    @close="onClose"
    :bodyStyle="{height: 'calc(100% - 55px)', overflowX: 'auto'}"
  >
    <section
      ref="drawerPage"
      style="height: 100%; overflowX: auto;"
    >
      <div class="profile-page">
        <div class="profile-page-warp">
          <div class="profile-page-content clearfix">
            <div class="content-page-left">
              <a-anchor
                :affix="true"
                :bounds="60"
                :offsetTop="40"
                :getContainer="() => this.$refs.drawerPage"
              >
                <a-anchor-link
                  v-for="(achor, index ) in anchors"
                  :key="index"
                  :href="achor.href"
                  :title="achor.title"
                />
              </a-anchor>
            </div>
            <div
              class="content-page-right"
              ref="contentRight"
            >
              <a id="customerBase"></a>
              <customer-base :companyId="companyId" @refresh="refresh"></customer-base>
              <a id="customerExtra"></a>
              <customer-extra :companyId="companyId"></customer-extra>
              <a id="personnelSituation"></a>
              <personnel-situation :companyId="companyId"></personnel-situation>
              <a id="ownershipStructure"></a>
              <ownership-structure :companyId="companyId"></ownership-structure>
              <a id="accountInfo"></a>
              <account-info :companyId="companyId"></account-info>
              <a id="supportCase"></a>
              <support-case :companyId="companyId"></support-case>
              <a id="finaceCase"></a>
              <finace-case :companyId="companyId"></finace-case>
            </div>
          </div>
        </div>
      </div>
    </section>
  </a-drawer>

</template>

<script>
import AccountInfo from './modules/AccountInfo'
import CustomerBase from './modules/CustomerBase'
import FinaceCase from './modules/FinaceCase'
import OwnershipStructure from './modules/OwnershipStructure'
import PersonnelSituation from './modules/PersonnelSituation'
import SupportCase from './modules/SupportCase'
import CustomerExtra from './modules/CustomerExtra'
const anchors = [
  { title: '基本信息', href: '#customerBase' },
  { title: '扩展信息', href: '#customerExtra' },
  { title: '上年末公司从业人员情况', href: '#personnelSituation' },
  { title: '公司股权结构', href: '#ownershipStructure' },
  { title: '单位银行开户信息', href: '#accountInfo' },
  { title: '国家省市财政全部支持情况', href: '#supportCase' },
  { title: '单位财务情况', href: '#finaceCase' }
]
export default {
  name: 'Index',
  components: {
    CustomerBase,
    PersonnelSituation,
    OwnershipStructure,
    AccountInfo,
    SupportCase,
    FinaceCase,
    CustomerExtra
  },
  data () {
    return {
      anchors,
      width: 1024,
      visible: false,
      title: '',
      companyId: -1
    }
  },
  methods: {
    show (row) {
      this.visible = true
      this.title = row.companyName
      this.companyId = row.companyId
    },
    onClose () {
      this.visible = false
    },
    refresh () {
      this.$emit('refresh')
    }
  }
}
</script>
<style lang="less" scoped>
.customer-name {
  font-size: 18px;
  font-weight: 600;
  color: #151515;
  text-align: center;
  line-height: 50px;
  padding-top: 0 !important;
}
.customer-base-info {
  text-align: center;
}
.content-page-left {
  float: left;
  width: 23%;
  padding-left: 5px;
}
.content-page-right {
  width: 77%;
  float: left;
  padding: 0 5px;
  border-left: 1px dashed #f3f3f3;
}
.profile-page-base-info {
  margin-bottom: 24px;
}
.mode-title {
  font-size: 18px;
  font-weight: 600;
  color: #151515;
}
.content-page-right a {
  position: relative;
  top: -30px;
}
</style>
