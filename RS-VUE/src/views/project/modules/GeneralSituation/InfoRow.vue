<!--
 * @Author: ldx
 * @Date: 2021-07-16 16:05:19
 * @LastEditTime: 2021-07-27 09:59:40
 * @LastEditors: ldx
 * @Description: 信息行
 * @FilePath: \RS-VUE\src\views\project\modules\GeneralSituation\InfoRow.vue
-->
<template>
  <a-row :gutter="[12,12]" style="margin-right: 0px; margin-left: 0px;">
    <a-col :span="6" style="padding-left: 0px;">
      <a-card class="myCard" :bodyStyle="bodyStyleCard">
        <div class="card_wrap">
          <div class="title">研发立项数</div>
          <div class="content">
            <span>
              {{ countProjectModel.projectCnt ? countProjectModel.projectCnt : 0 }}
              <div class="content_footer">
                {{ countProjectModel.projectCntPercent }}
                <a-tooltip>
                  <template slot="title">
                    比去年
                  </template>
                  <template v-if="countProjectModel.projectTrend !==null">
                    <a-icon v-if="countProjectModel.projectTrend > 0" type="arrow-up" style="color:#3DD598;" />
                    <a-icon v-if="countProjectModel.projectTrend < 0" type="arrow-down" style="color:#F0142F;" />
                    <span v-if="countProjectModel.projectTrend === 0">-</span>
                  </template>
                </a-tooltip>
                <span v-if="countProjectModel.childCnt" style="margin-left:8px;">子项目 <span>{{ countProjectModel.childCnt }}</span></span>
              </div>
            </span>
            <span class="icon_wrap" style="background-color: #E7F4FF;">
              <ys-icon icon="ProjecItem" class="icon" style="color: #2092FF;"></ys-icon>
            </span>
          </div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card class="myCard" :bodyStyle="bodyStyleCard">
        <div class="card_wrap">
          <div class="title">研发人员 </div>
          <div class="content">
            <span>
              {{ countProjectModel.rdEmployeeNum ? countProjectModel.rdEmployeeNum: 0 }}
              <div class="content_footer">
                {{ countProjectModel.rdEmployeePercent }}
                <a-tooltip>
                  <template slot="title">
                    比去年
                  </template>
                  <template v-if="countProjectModel.rdEmployeeTrend !==null">
                    <a-icon v-if="countProjectModel.rdEmployeeTrend > 0" type="arrow-up" style="color:#3DD598;" />
                    <a-icon v-if="countProjectModel.rdEmployeeTrend < 0" type="arrow-down" style="color:#F0142F;" />
                    <span v-if="countProjectModel.rdEmployeeTrend === 0">-</span>
                  </template>
                </a-tooltip>
              </div>
            </span>
            <span class="icon_wrap" style="background-color: #E4F7FF;">
              <ys-icon icon="RDPersonnel" class="icon" style="color: #07B6FF;"></ys-icon>
            </span>
          </div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6">
      <a-card class="myCard" :bodyStyle="bodyStyleCard">
        <div class="card_wrap">
          <div class="title">研发设备</div>
          <div class="content">
            <span>
              {{ countProjectModel.rdEquipmentNum ? countProjectModel.rdEquipmentNum: 0 }}
              <div class="content_footer">
                {{ countProjectModel.rdEquipmentPercent }}
                <a-tooltip>
                  <template slot="title">
                    比去年
                  </template>
                  <template v-if="countProjectModel.rdEquipmentTrend !==null">
                    <a-icon v-if="countProjectModel.rdEquipmentTrend > 0" type="arrow-up" style="color:#3DD598;" />
                    <a-icon v-if="countProjectModel.rdEquipmentTrend < 0" type="arrow-down" style="color:#F0142F;" />
                    <span v-if="countProjectModel.rdEquipmentTrend === 0">-</span>
                  </template>
                </a-tooltip>
              </div>
            </span>
            <span class="icon_wrap" style="background-color: #E4FAFD;">
              <ys-icon icon="ENumber" class="icon" style="color: #06D2EE;"></ys-icon>
            </span>
          </div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="6" style="padding-right: 0px;">
      <a-card class="myCard" :bodyStyle="bodyStyleCard">
        <div class="card_wrap">
          <div class="title">机构建设事项</div>
          <div class="content">
            <span>{{ countProjectModel.developmentNum ? countProjectModel.developmentNum : 0 }}</span>
            <span class="icon_wrap" style="background-color: #E4FCF7;">
              <ys-icon icon="BulidItem" class="icon" style="color: #05E7BC;"></ys-icon>
            </span>
          </div>
        </div>
      </a-card>
    </a-col>
  </a-row>
</template>

<script>
import YsIcon from '@/components/YsIcon/YsIcon.vue'
export default {
  name: 'InfoRow',
  components: {
    YsIcon
  },
  props: {
    countProjectModel: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      bodyStyleCard: {
        padding: '16px',
        height: '116px'
      },
      twoBodyStyleCard: {
        padding: '24px',
        height: '388px'
      }
    }
  },
  methods: {

  }
}
</script>
<style lang="less" scoped>
@import '../../less/generalSituation.less';
</style>
