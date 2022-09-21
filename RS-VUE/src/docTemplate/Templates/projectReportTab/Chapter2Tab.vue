<!--
 * @Author:
 * @Date: 2021-02-08 09:18:43
 * @LastEditTime: 2022-03-11 17:16:27
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\projectReportTab\Chapter2Tab.vue
-->
<template>
  <div>
    <a-tabs v-model="activeKey" @change="callback">
      <a-tab-pane key="1">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            placement="right"
            text="(一)项目主要研究内容"
            title="如下提纲仅供参考，各地请根据各地政府要求做调整！（注意用将来时表述。）
              撰写方向：写研究本项目所要做的工作内容，例如：从多方面研究可食用软胶在不规则杯盖中的稳定性，进一步确定软胶材料对此款杯子整体稳定性使用及使用寿命的影响；
              注意：不可单一描述设备结构，研究内容从多方面进行阐述：
              1）设备、结构设计：要做哪些调整、改进或开发；
              2）系统软硬件开发：如何搭建硬件平台，软件功能模块包括什么；
              3）关联到工艺上需要做哪些研究、试验试制；
              4）配方是否有优化等等，技术深度要挖掘、提升。
              项目主要内容是针对项目进行研究，重视方法论，按目标回溯，要能体现不确定性，多样性，方向性。不要直接展现研究结果。"
          />
        </template>
        <wang-editor
          key="researchContents"
          v-model="content.researchContents"
          :projectId="project.id"
          placeholder="如下提纲仅供参考，各地请根据各地政府要求做调整！（注意用将来时表述。）
            <br/>撰写方向：写研究本项目所要做的工作内容，例如：从多方面研究可食用软胶在不规则杯盖中的稳定性，进一步确定软胶材料对此款杯子整体稳定性使用及使用寿命的影响；
            <br/>注意：不可单一描述设备结构，研究内容从多方面进行阐述：
            <br/>1）设备、结构设计：要做哪些调整、改进或开发；
            <br/>2）系统软硬件开发：如何搭建硬件平台，软件功能模块包括什么；
            <br/>3）关联到工艺上需要做哪些研究、试验试制；
            <br/>4）配方是否有优化等等，技术深度要挖掘、提升。
            <br/>项目主要内容是针对项目进行研究，重视方法论，按目标回溯，要能体现不确定性，多样性，方向性。不要直接展现研究结果。" />
      </a-tab-pane>
      <a-tab-pane key="2">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            text="(二)项目关键技术及创新点"
            title="承上“研究内容”，突出/浓缩写研究主体在技术、工艺及产品本身上的一些创新，所使用的关键技术、关键工艺、关键方法、关键配方、关键结构、关键原料等。
              创新点：各地请根据各地查新要求做调整！！"
          />
        </template>
        <wang-editor
          key="innovation"
          v-model="content.innovation"
          :projectId="project.id"
          placeholder="承上“研究内容”，突出/浓缩写研究主体在技术、工艺及产品本身上的一些创新，所使用的关键技术、关键工艺、关键方法、关键配方、关键结构、关键原料等。
          <br/>创新点：各地请根据各地查新要求做调整！！" />
      </a-tab-pane>
      <a-tab-pane key="3">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            text="(三)项目技术及经济指标"
            title="此处要根据研究主体区别对待：（注意用将来时表述）
              研究的是产品则写产品的技术指标及对应的销售收入、利润、税收等；
              研究的是工艺则写工艺的关键指标，同时对产品产生影响的可以增加产品的技术指标；以项目研发结果为导向进行量化，勿将技术参数当做技术指标。
              经济效益则重点写提质、增效、节能、降耗等方面创造、节省带来的经济效益。
              经济指标建议写法，通过分析项目预计达到的节能、降耗、减员、增效、减排等方面的描述，减少煤耗/电耗或减员多少，为企业节省多少成本，尽量用计算过程或算法分析项目为公司带来的经济效益。"
          />
        </template>
        <wang-editor
          key="economicIndicators"
          v-model="content.economicIndicators"
          :projectId="project.id"
          placeholder="此处要根据研究主体区别对待：（注意用将来时表述）
          <br/>研究的是产品则写产品的技术指标及对应的销售收入、利润、税收等；
          <br/>研究的是工艺则写工艺的关键指标，同时对产品产生影响的可以增加产品的技术指标；以项目研发结果为导向进行量化，勿将技术参数当做技术指标。
          <br/>经济效益则重点写提质、增效、节能、降耗等方面创造、节省带来的经济效益。
          <br/>经济指标建议写法，通过分析项目预计达到的节能、降耗、减员、增效、减排等方面的描述，减少煤耗/电耗或减员多少，为企业节省多少成本，尽量用计算过程或算法分析项目为公司带来的经济效益。" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import WangEditor from '@/components/Editor/WangEditor.vue'
import YsTooltip from '@/components/YsTooltip/YsTooltip'
export default {
  name: 'Chapter2Tab',
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

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
