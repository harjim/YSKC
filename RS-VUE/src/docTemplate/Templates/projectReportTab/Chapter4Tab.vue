<!--
 * @Author:
 * @Date: 2021-02-08 09:18:43
 * @LastEditTime: 2022-03-11 17:19:08
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\projectReportTab\Chapter4Tab.vue
-->
<template>
  <div>
    <a-tabs v-model="activeKey" @change="callback">
      <a-tab-pane key="1">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            text="(一)企业研发机构建设"
            title="以公司简介为起点，侧重点表达研发机构的建设基础；接着重点描述研发机构的硬件建设情况、软件实力、人员配置、职位设置、职能安排、研发创新精神、文化宣传等。
            比如：研发部门建设情况（国家级/省级/市级技术中心、工程技术中心、重点实验室、医药研究中心等）、研发人员配置情况（学历、专业、资质、擅长技术领域、某某项目带头人等）、研发设备（实验室、检测中心、试验中心等重点研发设备），企业在国家、省市项目中取得的成绩，技术人员在某大型合作研发项目中承担重要角色，产学研，等等。"
          />
        </template>
        <wang-editor
          key="institutionBuilding"
          v-model="content.institutionBuilding"
          :projectId="project.id"
          placeholder="以公司简介为起点，侧重点表达研发机构的建设基础；接着重点描述研发机构的硬件建设情况、软件实力、人员配置、职位设置、职能安排、研发创新精神、文化宣传等。
          <br/>比如：研发部门建设情况（国家级/省级/市级技术中心、工程技术中心、重点实验室、医药研究中心等）、研发人员配置情况（学历、专业、资质、擅长技术领域、某某项目带头人等）、研发设备（实验室、检测中心、试验中心等重点研发设备），企业在国家、省市项目中取得的成绩，技术人员在某大型合作研发项目中承担重要角色，产学研，等等。" />
      </a-tab-pane>
      <a-tab-pane key="2">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            placement="right"
            text="(二)近年来研发项目情况"
            title="第一种，每年都做研发项目（注意有的企业是前些年是自己做或其他中介做，今年才给我们做，但也是属于每年都做），言简意赅，列表或者直接分点写研发了哪些项目；
            第二种，第一次做加计扣除，可以侧重描述公司很重视研发创新活动的开展，每年相关技术部门也有提出产品、工艺、技术的项目技术方案，基本上安排部门内部直接实施，没有统一归纳到研发档案里，研发的主要项目有：……；经过几年努力，研发、创新给公司奠定了重要发展基础，特在公司层面组织研发项目团队，研发活动正规化、研发资料规整化、研发流程流畅化。（侧重公司近年来的研发方向、技术所在领域、企业近年来的规划、技术人员擅长领域、项目管理经验等，不建议罗列21年项目，对当年项目的研发没有借鉴意义，无经验支撑可谈。）"
          />
        </template>
        <wang-editor
          key="projectStatus"
          v-model="content.projectStatus"
          :projectId="project.id"
          placeholder="第一种，每年都做研发项目（注意有的企业是前些年是自己做或其他中介做，今年才给我们做，但也是属于每年都做），言简意赅，列表或者直接分点写研发了哪些项目；<br/>第二种，第一次做加计扣除，可以侧重描述公司很重视研发创新活动的开展，每年相关技术部门也有提出产品、工艺、技术的项目技术方案，基本上安排部门内部直接实施，没有统一归纳到研发档案里，研发的主要项目有：……；经过几年努力，研发、创新给公司奠定了重要发展基础，特在公司层面组织研发项目团队，研发活动正规化、研发资料规整化、研发流程流畅化。（侧重公司近年来的研发方向、技术所在领域、企业近年来的规划、技术人员擅长领域、项目管理经验等，不建议罗列21年项目，对当年项目的研发没有借鉴意义，无经验支撑可谈。）"
        />
      </a-tab-pane>
      <a-tab-pane key="3">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            text="(三)近年来取得的科技成果"
            title="成果的类型有：知识产权、企业制定或参加的标准、登记的技术合同、备案的专利许可、科技进步奖证书、专利奖证书、验收并登记的科技项目、第三方评价并登记的科技成果（如查新、第三方检测报告等）。
            每家企业根据企业实际情况，选择对应的成果类型进行列举。"
          />
        </template>
        <wang-editor
          key="scienceResult"
          v-model="content.scienceResult"
          :projectId="project.id"
          placeholder="成果的类型有：知识产权、企业制定或参加的标准、登记的技术合同、备案的专利许可、科技进步奖证书、专利奖证书、验收并登记的科技项目、第三方评价并登记的科技成果（如查新、第三方检测报告等）。<br/>每家企业根据企业实际情况，选择对应的成果类型进行列举。" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import WangEditor from '@/components/Editor/WangEditor.vue'
import YsTooltip from '@/components/YsTooltip/YsTooltip'
export default {
  name: 'Chapter4Tab',
  components: {
    WangEditor,
    YsTooltip
  },
  props: {
    content: {
      type: Object,
      required: true
    },
    project: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      domName,
      activeKey: '1'
    }
  },
  methods: {
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    callback (activeKey) {
      // 切换tab调用
      this.activeKey = activeKey
    }
  }
}
</script>

<style lang="less" scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
// #fullBox {
//    & /deep/ .ant-tooltip {
//   max-width: 450px;
//   }
// }
</style>
