<template>
  <a-drawer
    :title="title"
    :width="1000"
    :headerStyle="{padding: '12px 16px'}"
    :bodyStyle="{padding: '0px 7px' ,height: 'calc(100% - 55px)'}"
    @close="onClose"
    :destroyOnClose="true"
    :visible="visible"
    :zIndex="99"
  >
    <component
      :is="coms[type] ? coms[type].component: undefined"
      :year="year"
      :companyId="companyId"
      :companyName="companyName"
    />
  </a-drawer>
</template>

<script>
import RdFunds from './detailModules/RdFunds'
import RdList from './detailModules/RdList'
import RdEmployeeList from './detailModules/RdEmployeeList'
import RdEquipmentList from './detailModules/RdEquipmentList'
import HighTechList from './detailModules/HighTechList'
import BuildList from './detailModules/BuildList'
const coms = {
  rdFunds: { component: RdFunds, title: '研发费用' },
  rdList: { component: RdList, title: '项目列表' },
  rdEmployeeList: { component: RdEmployeeList, title: '研发人员' },
  rdEquipmentList: { component: RdEquipmentList, title: '研发设备' },
  build: { component: BuildList, title: '机构建设事项' },
  highTech: { component: HighTechList, title: '高品列表' }
}
export default {
  data () {
    return {
      coms,
      title: '',
      visible: false,
      type: '',
      companyId: undefined,
      companyName: undefined
    }
  },
  props: {
    year: {
      type: Number,
      required: true
    }
  },
  created () {

  },
  methods: {
    open (type, companyName, companyId) {
      this.type = type
      this.companyName = companyName
      this.companyId = companyId
      this.title = `${companyName || '集团'}-${this.year}-${coms[type].title}`
      this.visible = true
    },
    onClose () {
      this.visible = false
    }
  }
}
</script>

<style>

</style>
