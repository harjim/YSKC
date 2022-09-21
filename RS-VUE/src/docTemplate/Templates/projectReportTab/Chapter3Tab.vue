<!--
 * @Author:
 * @Date: 2021-02-08 09:18:43
 * @LastEditTime: 2022-03-11 17:16:50
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\projectReportTab\Chapter3Tab.vue
-->
<template>
  <div>
    <a-tabs v-model="activeKey" @change="callback">
      <a-tab-pane key="1">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            text="(一)研究开发方法"
            title="常见的研究开发方法有：文献调查法、观察法、思辨法、行为研究法、历史研究法、概念分析法、经验总结法、数据分析法、实验验证法、单一变量法、对比分析法等，撰写的时候要根据所研究内容的主体和研究步骤，找到对应的研究开发方法，并展开撰写是如何按照此方法进行研究的。"
          />
        </template>
        <wang-editor key="developmentMethod" v-model="content.developmentMethod" :projectId="project.id" placeholder="常见的研究开发方法有：文献调查法、观察法、思辨法、行为研究法、历史研究法、概念分析法、经验总结法、数据分析法、实验验证法、单一变量法、对比分析法等，撰写的时候要根据所研究内容的主体和研究步骤，找到对应的研究开发方法，并展开撰写是如何按照此方法进行研究的。" />
      </a-tab-pane>
      <a-tab-pane key="2">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            text="(二)技术路线"
            title="根据项目研究的主体撰写技术发展过程，描绘技术进阶的路线。通常采用合、分、合的方式，即一开始是一个整体，然后分不同的技术模块分析、研究、遇到问题、解决问题，最后再合并形成完整的技术发展轨迹。"
          />
        </template>
        <wang-editor key="technicalRoute" v-model="content.technicalRoute" :projectId="project.id" placeholder="根据项目研究的主体撰写技术发展过程，描绘技术进阶的路线。通常采用合、分、合的方式，即一开始是一个整体，然后分不同的技术模块分析、研究、遇到问题、解决问题，最后再合并形成完整的技术发展轨迹。"/>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import WangEditor from '@/components/Editor/WangEditor.vue'
import YsTooltip from '@/components/YsTooltip/YsTooltip'
export default {
  name: 'Chapter3Tab',
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
