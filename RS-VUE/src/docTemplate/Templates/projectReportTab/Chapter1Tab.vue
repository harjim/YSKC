<!--
 * @Author:
 * @Date: 2021-02-08 09:18:43
 * @LastEditTime: 2022-03-11 17:15:43
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\projectReportTab\Chapter1Tab.vue
-->
<template>
  <div>
    <a-tabs v-model="activeKey" @change="callback">
      <a-tab-pane key="1">
        <template slot="tab">
          <!-- <i class="required-field"/>(一)项目立项背景 -->
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            text="(一)项目立项背景"
            title="注意：此处不只是表述该技术/工艺/产品在国内外现状，须重点表达项目在企业内部的背景，比如：
                之前XXX工艺技术不能适应新产品的高精度、高效率、高标准的要求；
                之前的产品设计零部件较多，组装复杂，稳定性不足、耐受性不足影响使用寿命、外观不够新颖时尚等。
                需要写具体的技术瓶颈、技术障碍、需要攻克的技术、产品、技术参数等。"
          />
        </template>
        <wang-editor
          key="background"
          v-model="content.background"
          :projectId="project.id"
          placeholder="注意：此处不只是表述该技术/工艺/产品在国内外现状，须重点表达项目在企业内部的背景，比如：
          <br/>之前XXX工艺技术不能适应新产品的高精度、高效率、高标准的要求；
          <br/>之前的产品设计零部件较多，组装复杂，稳定性不足、耐受性不足影响使用寿命、外观不够新颖时尚等。
          <br/>需要写具体的技术瓶颈、技术障碍、需要攻克的技术、产品、技术参数等。"
        />
      </a-tab-pane>
      <a-tab-pane key="2">
        <template slot="tab">
          <ys-tooltip
            :getPopupContainer="getCalendarContainer"
            required
            text="(二)项目立项目的与意义"
            title="承上“项目立项背景”（为了解决背景中所述技术瓶颈等问题），重点撰写项目的实施要达到什么样的效果、目的，包括产品、技术、工艺本身的目的和对公司发展的目的与意义，从小到大展开写。"
          />
        </template>
        <wang-editor key="meaning" v-model="content.meaning" :projectId="project.id" placeholder="承上“项目立项背景”（为了解决背景中所述技术瓶颈等问题），重点撰写项目的实施要达到什么样的效果、目的，包括产品、技术、工艺本身的目的和对公司发展的目的与意义，从小到大展开写。" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import WangEditor from '@/components/Editor/WangEditor.vue'
import YsTooltip from '@/components/YsTooltip/YsTooltip'
export default {
  name: 'Chapter1Tab',
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
    callback (activeKey) { // 切换tab调用
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
