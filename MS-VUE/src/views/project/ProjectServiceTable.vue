<template>
  <a-drawer
    :title="title"
    placement="right"
    :maskClosable="false"
    :closable="true"
    :width="750"
    :visible="drawerVisible"
    :bodyStyle="{ height: 'calc(100% - 103px)', overflowY: 'auto' }"
    @close="onClose"
  >
    <a-spin id="spin" :spinning="spinning">
      <a-form :form="form">
        <table id="custom-table" style="border-collapse: collapse; width: 100%;" border="1">
          <tbody>
            <tr>
              <td style="text-align: center; width: 5%;" class="table_cell">序号</td>
              <td style="width: 25%; text-align: center;" class="table_cell">工作要点</td>
              <td style="text-align: center; width: 70%;" class="table_cell">服务内容与要求</td>
              <!-- <td style="width: 25%; text-align: center;" class="table_cell">
                <p>时间线</p>
                {{ timeLineData.minDate ? moment(timeLineData.minDate).format('YYYY-MM-DD') : '' }} {{ timeLineData.minDate || timeLineData.maxDate ? ' ~ ' : '' }} {{ timeLineData.maxDate ? moment(timeLineData.maxDate).format('YYYY-MM-DD') : '' }}
              </td> -->
            </tr>
            <!-- 基础信息 -->
            <template v-if="$auth('project:projectList:getBasicInfo') || $auth('project:projectList:editBasicInfo')">
              <tr>
                <!-- <td style="width: 70%;" colspan="4" class="table_cell"><strong>基础信息</strong></td> -->
                <td style="width: 70%;" colspan="3" class="table_cell"><strong>基础信息</strong></td>
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">1</td>
                <td style="width: 25%;" class="table_cell">标准化实施</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    <a-radio-group v-if="$auth('project:projectList:editBasicInfo')" :options="standardOptions" v-decorator="['isImplemented']" />
                    <a-radio-group class="custom-disabled" v-else :options="standardOptions" disabled v-decorator="['isImplemented']" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="80.8%" topLeft="5.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">2</td>
                <td style="width: 25%;" class="table_cell">企业财务基本信息</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    收入 <a-input-number
                      v-if="$auth('project:projectList:editBasicInfo')"
                      :min="0"
                      class="custom-input"
                      :precision="2"
                      v-decorator="['income']" />
                    <a-input-number
                      v-else
                      disabled
                      :min="0"
                      class="custom-input custom-disabled"
                      :precision="2"
                      v-decorator="['income']" />万元
                  </a-form-item>
                  <a-form-item class="noBottom">
                    所得税<a-input-number
                      v-if="$auth('project:projectList:editBasicInfo')"
                      :min="0"
                      class="custom-input"
                      :precision="2"
                      v-decorator="['incomeTax']" />
                    <a-input-number
                      v-else
                      :min="0"
                      disabled
                      class="custom-input custom-disabled"
                      :precision="2"
                      v-decorator="['incomeTax']" />万元
                  </a-form-item>
                  <a-form-item class="noBottom">
                    人数<a-input-number
                      v-if="$auth('project:projectList:editBasicInfo')"
                      :min="0"
                      class="custom-input"
                      :precision="0"
                      v-decorator="['totalStaff']" />
                    <a-input-number
                      v-else
                      :min="0"
                      disabled
                      class="custom-input custom-disabled"
                      :precision="0"
                      v-decorator="['totalStaff']" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="61.3%" color="#FFCD43"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">3</td>
                <td style="width: 25%;" class="table_cell">计划申请</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    约定研发费做<a-input-number
                      v-if="$auth('project:projectList:editBasicInfo')"
                      :min="0"
                      class="custom-input"
                      :precision="2"
                      v-decorator="['researchFee']" />
                    <a-input-number
                      v-else
                      :min="0"
                      disabled
                      class="custom-input custom-disabled"
                      :precision="2"
                      v-decorator="['researchFee']" />万元
                  </a-form-item>
                  <a-form-item class="noBottom">
                    减免所得税<a-input-number
                      v-if="$auth('project:projectList:editBasicInfo')"
                      :min="0"
                      class="custom-input"
                      :precision="2"
                      v-decorator="['taxRefiefs']" />
                    <a-input-number
                      v-else
                      :min="0"
                      disabled
                      class="custom-input custom-disabled"
                      :precision="2"
                      v-decorator="['taxRefiefs']" />万元
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="2.8%" topLeft="19.3%" color="#0DBC79"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">4</td>
                <td style="width: 25%;" class="table_cell">技术人员情况</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    <a-textarea v-if="$auth('project:projectList:editBasicInfo')" placeholder="技术人员情况" :rows="4" v-decorator="['techStaff']" />
                    <a-textarea
                      v-else
                      placeholder="技术人员情况"
                      class="custom-disabled"
                      disabled
                      :rows="4"
                      v-decorator="['techStaff']" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="0.8%" topLeft="20.3%" color="#16D46B"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">5</td>
                <td style="width: 25%;" class="table_cell">财务人员情况</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    <a-textarea v-if="$auth('project:projectList:editBasicInfo')" placeholder="财务人员情况" :rows="4" v-decorator="['financeStaff']" />
                    <a-textarea
                      v-else
                      placeholder="财务人员情况"
                      disabled
                      class="custom-disabled"
                      :rows="4"
                      v-decorator="['financeStaff']" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%" color="#3296FA"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">6</td>
                <td style="width: 25%;" class="table_cell">总经理或高层情况</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    <a-textarea v-if="$auth('project:projectList:editBasicInfo')" placeholder="总经理或高层情况" :rows="4" v-decorator="['manager']" />
                    <a-textarea
                      v-else
                      placeholder="总经理或高层情况"
                      disabled
                      class="custom-disabled"
                      :rows="4"
                      v-decorator="['manager']" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">7</td>
                <td style="width: 25%;" class="table_cell">启动时间</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    <a-date-picker v-if="$auth('project:projectList:editBasicInfo')" v-decorator="['startTime']" />
                    <a-date-picker v-else v-decorator="['startTime']" disabled class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">8</td>
                <td style="width: 25%;" class="table_cell">高新情况</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    <a-radio-group v-if="$auth('project:projectList:editBasicInfo')" :options="caseOptions" v-decorator="['applyStatus']" />
                    <a-radio-group v-else :options="caseOptions" disabled v-decorator="['applyStatus']" class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="9.8%" topLeft="20.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">9</td>
                <td style="width: 25%;" class="table_cell">专利支付费</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    <a-radio-group v-if="$auth('project:projectList:editBasicInfo')" :options="patentPaymentOptions" v-decorator="['hasPayPatent']" />
                    <a-radio-group v-else :options="patentPaymentOptions" disabled v-decorator="['hasPayPatent']" class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">10</td>
                <td style="width: 25%;" class="table_cell">其他要求</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    <a-textarea v-if="$auth('project:projectList:editBasicInfo')" placeholder="其他要求" :rows="4" v-decorator="['other']"></a-textarea>
                    <a-textarea
                      v-else
                      placeholder="其他要求"
                      :rows="4"
                      disabled
                      class="custom-disabled"
                      v-decorator="['other']"></a-textarea>
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
            </template>
            <!-- 技术部分 -->
            <template v-if="$auth('project:projectList:getTechInfo') || $auth('project:projectList:editTechInfo')">
              <tr>
                <!-- <td style="text-align: left; width: 70%;" colspan="4" class="table_cell"><strong>技术部分</strong></td> -->
                <td style="text-align: left; width: 70%;" colspan="3" class="table_cell"><strong>技术部分</strong></td>
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">1</td>
                <td style="width: 25%;" class="table_cell">启动与对接</td>
                <td style="width: 70%;" class="table_cell">
                  <!-- TODO 日期限制 -->
                  <a-form-item class="noBottom">
                    启动时间: <a-date-picker v-if="$auth('project:projectList:editTechInfo')" v-decorator="['tStartTime']" :disabledDate="tStartTimeDisabled" />
                    <a-date-picker v-else disabled v-decorator="['tStartTime']" :disabledDate="tStartTimeDisabled" />
                  </a-form-item>
                  <a-form-item class="noBottom">
                    对接时间: <a-date-picker v-if="$auth('project:projectList:editTechInfo')" v-decorator="['tDockingTime']" :disabledDate="tDockingTimeDisabled" />
                    <a-date-picker v-else disabled v-decorator="['tDockingTime']" :disabledDate="tDockingTimeDisabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <!-- <td style="text-align: center; width: 5%;" rowspan="3" class="table_cell">2</td> -->
                <td style="text-align: center; width: 5%;" rowspan="3" class="table_cell">2</td>
                <td style="width: 25%;" rowspan="3" class="table_cell">研发技术中心标准化</td>
                <td style="width: 70%;" class="table_cell">研发架构：
                  {{ timeLineData['20'] ? timeLineData['20'].result : '无' }}
                  <!-- <a-radio-group disabled :value="timeLineData['20'] ? timeLineData['20'].result : '无'" :options="frameworkOptions"/> -->
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['20'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['20'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    建立制度 <a-radio-group v-if="$auth('project:projectList:editTechInfo')" :options="systemOptions" v-decorator="['isSystem']" />
                    <a-radio-group v-else disabled :options="systemOptions" v-decorator="['isSystem']" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">研发人员数目: {{ timeLineData['30'] ? timeLineData['30'].result : '' }}</td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" rowspan="7" class="table_cell">3</td>
                <td style="width: 25%;" rowspan="7" class="table_cell">项目管理</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    现场挖掘<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="现场挖掘" :rows="4" v-decorator="['excavate']" />
                    <a-textarea
                      v-else
                      disabled
                      placeholder="现场挖掘"
                      :rows="4"
                      v-decorator="['excavate']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  RD:
                  <ul class="RD">
                    <li v-for="(item, index) in getRdList(timeLineData['40'])" :key="index">
                      {{ index +1 }}、{{ item }}
                    </li>
                  </ul>
                  <!-- <div class="RD">
                    {{ timeLineData['40'] ? timeLineData['40'].result : '' }}
                  </div> -->
                </td>
                <!-- <time-line topWidth="11.8%" topLeft="11.3%"></time-line> -->
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['40'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['40'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    整理阶段成果技术证明<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="整理阶段成果技术证明" :rows="4" v-decorator="['prove']"/>
                    <a-textarea
                      v-else
                      disabled
                      placeholder="整理阶段成果技术证明"
                      :rows="4"
                      v-decorator="['prove']"
                      class="custom-disabled"/>
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    知识产权申请数<a-input-number v-if="$auth('project:projectList:editTechInfo')" :min="0" class="custom-input" :precision="0" v-decorator="['IPRCount']" />
                    <a-input-number
                      v-else
                      disabled
                      :min="0"
                      class="custom-input custom-disabled"
                      :precision="0"
                      v-decorator="['IPRCount']" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    成果评估<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="成果评估" :rows="4" v-decorator="['achievement']"/>
                    <a-textarea
                      v-else
                      disabled
                      placeholder="成果评估"
                      :rows="4"
                      v-decorator="['achievement']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="boBottom">
                    效益评估<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="效益评估" :rows="4" v-decorator="['benefit']" />
                    <a-textarea
                      v-else
                      disabled
                      placeholder="效益评估"
                      :rows="4"
                      v-decorator="['benefit']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    技术查新<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="技术查新" :rows="4" v-decorator="['tech']"/>
                    <a-textarea
                      v-else
                      disabled
                      placeholder="技术查新"
                      :rows="4"
                      v-decorator="['tech']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" rowspan="9" class="table_cell">4</td>
                <td style="width: 25%;" rowspan="9" class="table_cell">梳理月度轨迹</td>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    规划阶段:
                  </div>
                  {{ timeLineData['100'] ? timeLineData['100'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['100'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['100'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    研究阶段:
                  </div>
                  {{ timeLineData['200'] ? timeLineData['200'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['200'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['200'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    设计开发:
                  </div>
                  {{ timeLineData['300'] ? timeLineData['300'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['300'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['300'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    设计验证:
                  </div>
                  {{ timeLineData['400'] ? timeLineData['400'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['400'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['400'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    实验验证:
                  </div>
                  {{ timeLineData['500'] ? timeLineData['500'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['500'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['500'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    试验证:
                  </div>
                  {{ timeLineData['600'] ? timeLineData['600'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['600'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['600'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    试制:
                  </div>
                  {{ timeLineData['700'] ? timeLineData['700'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['700'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['700'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    项目验收:
                  </div>
                  {{ timeLineData['800'] ? timeLineData['800'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['800'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['800'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <div class="custom-align">
                    项目收尾:
                  </div>
                  {{ timeLineData['900'] ? timeLineData['900'].result : '-' }}
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['900'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['900'])"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" rowspan="4" class="table_cell">5</td>
                <td style="width: 25%;" rowspan="4" class="table_cell">高新技术产品</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    高新技术产品名称<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="高新技术产品名称" :rows="4" v-decorator="['techName']"/>
                    <a-textarea
                      v-else
                      disabled
                      placeholder="高新技术产品名称"
                      :rows="4"
                      v-decorator="['techName']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    高新技术产品说明<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="高新技术产品说明" :rows="4" v-decorator="['techExplain']"/>
                    <a-textarea
                      v-else
                      disabled
                      placeholder="高新技术产品说明"
                      :rows="4"
                      v-decorator="['techExplain']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    研发技术产品核心技术关联性<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="研发技术产品核心技术关联性" :rows="4" v-decorator="['techCore']"/>
                    <a-textarea
                      v-else
                      disabled
                      placeholder="研发技术产品核心技术关联性"
                      :rows="4"
                      v-decorator="['techCore']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    认定与证书<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="认定与证书" :rows="4" v-decorator="['certificate']" />
                    <a-textarea
                      v-else
                      disabled
                      placeholder="认定与证书"
                      :rows="4"
                      v-decorator="['certificate']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" rowspan="6" class="table_cell">6</td>
                <td style="width: 25%;" rowspan="6" class="table_cell">交付</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    税务备查资料 <a-radio-group v-if="$auth('project:projectList:editTechInfo')" :options="bczlOptions" v-decorator="['isTaxData']"></a-radio-group>
                    <a-radio-group v-else disabled :options="bczlOptions" v-decorator="['isTaxData']"></a-radio-group>
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    研发体系文件 <a-radio-group v-if="$auth('project:projectList:editTechInfo')" :options="txwjOptions" v-decorator="['isSystemFile']"></a-radio-group>
                    <a-radio-group v-else disabled :options="txwjOptions" v-decorator="['isSystemFile']"></a-radio-group>
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    研发技术中心 <a-radio-group v-if="$auth('project:projectList:editTechInfo')" :options="jszxOptions" v-decorator="['isTechCore']"></a-radio-group>
                    <a-radio-group v-else disabled :options="jszxOptions" v-decorator="['isTechCore']"></a-radio-group>
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    研发轨迹 <a-radio-group v-if="$auth('project:projectList:editTechInfo')" :options="gjOptions" v-decorator="['isTechTrack']"></a-radio-group>
                    <a-radio-group v-else disabled :options="gjOptions" v-decorator="['isTechTrack']"></a-radio-group>
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    研发成果 <a-radio-group v-if="$auth('project:projectList:editTechInfo')" :options="cgOptions" v-decorator="['isTechResule']"></a-radio-group>
                    <a-radio-group v-else disabled :options="cgOptions" v-decorator="['isTechResule']"></a-radio-group>
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="nobottom">
                    其他<a-textarea v-if="$auth('project:projectList:editTechInfo')" placeholder="其他" :rows="4" v-decorator="['techOther']" />
                    <a-textarea
                      v-else
                      disabled
                      placeholder="其他"
                      :rows="4"
                      v-decorator="['techOther']"
                      class="custom-disabled" />
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
            </template>
            <!-- 财务部分 -->
            <template v-if="$auth('project:projectList:getFinanceInfo') || $auth('project:projectList:editFinanceInfo') ">
              <tr>
                <!-- <td style="text-align: left; width: 70%;" colspan="4" class="table_cell"><strong>财务部分</strong></td> -->
                <td style="text-align: left; width: 70%;" colspan="3" class="table_cell"><strong>财务部分</strong></td>
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">1</td>
                <td style="width: 25%;" class="table_cell">数据启动对接</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    对接日期: <a-date-picker v-if=" $auth('project:projectList:editFinanceInfo')" v-decorator="['dockingTime']"/>
                    <a-date-picker v-else disabled v-decorator="['dockingTime']"/>
                  </a-form-item>
                </td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">2</td>
                <td style="width: 25%;" class="table_cell">预计研发费总额</td>
                <td style="width: 70%;" class="table_cell">
                  <a-form-item class="noBottom">
                    预计归集研发总额:<a-input-number
                      v-if=" $auth('project:projectList:editFinanceInfo')"
                      :min="0"
                      class="custom-input"
                      :precision="2"
                      v-decorator="['estimateRdAmount']" />
                    <a-input-number
                      v-else
                      disabled
                      :min="0"
                      class="custom-input custom-disabled"
                      :precision="2"
                      v-decorator="['estimateRdAmount']" />
                  </a-form-item></td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line topWidth="11.8%" topLeft="11.3%" color="#FFE793"></time-line>
                </td> -->
              </tr>
              <tr>
                <td style="text-align: center; width: 5%;" class="table_cell">3</td>
                <td style="width: 25%;" class="table_cell">实际归集总额</td>
                <td style="width: 45%;" class="table_cell">研发总额: {{ financeInfoObj && financeInfoObj.result ? financeInfoObj.result : '-' }}</td>
                <!-- <td style="width: 25%;" class="table_cell">
                  <time-line color="#F70505" :topWidth="computeTopWidth(timeLineData.totalDay,timeLineData['50'])" :topLeft="computeTopLeft(timeLineData.totalDay,timeLineData.minDate,timeLineData['50'])"></time-line>
                </td> -->
              </tr>
            </template>
          </tbody>
        </table>
      </a-form>
      <div class="saveContainer" >
        <div class="saveBtn">
          <a-button
            v-if=" $auth('project:projectList:editFinanceInfo') || $auth('project:projectList:editTechInfo') || $auth('project:projectList:editBasicInfo') "
            style="margin: 0 5px;"
            type="primary"
            @click="handledSaveData">保存</a-button>
          <!-- <a-button style="margin: 0 5px;" type="primary" @click="onClose">关闭</a-button> -->
        </div>
      </div>
    </a-spin>
  </a-drawer>
</template>
<script>
import moment from 'moment'
import TimeLine from '@/components/TimeLine'
export default {
  name: 'ProjectServiceTable',
  components: {
    TimeLine
  },
  data () {
    return {
      projectId: undefined,
      form: this.$form.createForm(this),
      drawerVisible: false,
      spinning: false,
      // 服务表
      standardOptions: [ { value: 1, label: '是' }, { value: 0, label: '否' } ], // 标准化实施
      caseOptions: [ { value: 1, label: '是' }, { value: 0, label: '否' }, { value: 2, label: '申请中' } ], // 高新情况
      patentPaymentOptions: [ { value: true, label: '有' }, { value: false, label: '无' } ], // 专利支付费
      // frameworkOptions: [ { value: '有', label: '有' }, { value: '', label: '无' } ], // 组织架构
      // frameworkOptions: ['有', '无'], // 组织架构
      systemOptions: [ { value: 1, label: '有' }, { value: 0, label: '无' } ], // 体制
      bczlOptions: [ { value: 1, label: '有' }, { value: 0, label: '无' } ], // 税务备查资料
      txwjOptions: [ { value: 1, label: '有' }, { value: 0, label: '无' } ], // 研发体系文件
      jszxOptions: [ { value: 1, label: '有' }, { value: 0, label: '无' } ], // 研发技术中心
      gjOptions: [ { value: 1, label: '有' }, { value: 0, label: '无' } ], // 研发轨迹
      cgOptions: [ { value: 1, label: '有' }, { value: 0, label: '无' } ], // 研发成果
      updateIds: { // 编辑的时候用的id
        basicId: undefined, // 基本信息模块
        financeId: undefined, // 财务模块
        techId: undefined // 技术模块
      },
      title: '',
      timeLineData: {},
      projectInfoObj: {
        id: '',
        isImplemented: '',
        income: '',
        incomeTax: '',
        totalStaff: '',
        researchFee: '',
        taxRefiefs: '',
        techStaff: '',
        financeStaff: '',
        manager: '',
        startTime: '',
        applyStatus: '',
        hasPayPatent: '',
        other: '',
        projectId: ''
      },
      techInfoObj: {
        id: '',
        startTime: '', // 字符串转Date
        dockingTime: '', // 字符串转Date
        content: '',
        projectId: '',
        models: ''
      },
      financeInfoObj: {
        id: '',
        dockingTime: '', // 字符串转date
        estimateRdAmount: '',
        projectId: '',
        result: '',
        beginTime: '',
        endTime: ''
      }
    }
  },
  methods: {
    moment,
    show (projectId, row) {
      this.form.resetFields()
      this.title = `${row.companyName}客户${row.productName}服务跟踪表`
      // this.drawerVisible = true
      this.projectId = projectId
      this.getServiceData(projectId)
    },
    onClose () {
      this.drawerVisible = false
      this.projectId = undefined
      this.spinning = false
      this.title = ''
      this.timeLineData = {}
      this.drawerVisible = false
      this.updateIds = {
        basicId: undefined,
        financeId: undefined,
        techId: undefined
      }
      this.projectInfoObj = {
        id: '',
        isImplemented: '',
        income: '',
        incomeTax: '',
        totalStaff: '',
        researchFee: '',
        taxRefiefs: '',
        techStaff: '',
        financeStaff: '',
        manager: '',
        startTime: '',
        applyStatus: '',
        hasPayPatent: '',
        other: '',
        projectId: ''
      }
      this.techInfoObj = {
        id: '',
        startTime: '', // 字符串转Date
        dockingTime: '', // 字符串转Date
        content: '',
        projectId: '',
        models: ''
      }
      this.financeInfoObj = {
        id: '',
        dockingTime: '', // 字符串转date
        estimateRdAmount: '',
        projectId: '',
        result: '',
        beginTime: '',
        endTime: ''
      }
    },
    getServiceData (projectId) {
      const promiseAry = []
      if (this.$auth('project:projectList:getBasicInfo') || this.$auth('project:projectList:editBasicInfo')) {
        const getProjectInfo = this.$http.get('/projectBasic/getProjectInfo', { params: { projectId } }).then((res) => {
          // if (res.data && res.success) {
          //   Object.assign(this.projectInfoObj, res.data)
          //   return Promise.resolve(true)
          // } else {
          //   return Promise.reject(res.errorMessage)
          // }
          if (res.errorMessage) {
            return Promise.reject(res.errorMessage)
          } else {
            Object.assign(this.projectInfoObj, res.data)
            return Promise.resolve(true)
          }
        // eslint-disable-next-line handle-callback-err
        }).catch((error) => {
          this.$message.error('请求数据接口异常')
        })
        promiseAry.push(getProjectInfo)
      }
      if (this.$auth('project:projectList:getTechInfo') || this.$auth('project:projectList:editTechInfo')) {
        const getTechInfo = this.$http.get('/projectBasic/getTechInfo', { params: { projectId } }).then((res) => {
          // if (res.data && res.success) {
          //   Object.assign(this.techInfoObj, res.data)
          //   return Promise.resolve(true)
          // } else {
          //   return Promise.reject(res.errorMessage)
          // }
          if (res.errorMessage) {
            return Promise.reject(res.errorMessage)
          } else {
            Object.assign(this.techInfoObj, res.data)
            return Promise.resolve(true)
          }
        // eslint-disable-next-line handle-callback-err
        }).catch((error) => {
          this.$message.error('请求数据接口异常')
        })
        promiseAry.push(getTechInfo)
      }
      if (this.$auth('project:projectList:getFinanceInfo') || this.$auth('project:projectList:editFinanceInfo')) {
        const getFinanceInfo = this.$http.get('/projectBasic/getFinanceInfo', { params: { projectId } }).then((res) => {
          // if (res.data && res.success) {
          //   Object.assign(this.financeInfoObj, res.data)
          //   return Promise.resolve(true)
          // } else {
          //   return Promise.reject(res.errorMessage)
          // }
          if (res.errorMessage) {
            return Promise.reject(res.errorMessage)
          } else {
            Object.assign(this.financeInfoObj, res.data)
            return Promise.resolve(true)
          }
        // eslint-disable-next-line handle-callback-err
        }).catch((error) => {
          this.$message.error('请求数据接口异常')
        })
        promiseAry.push(getFinanceInfo)
      }
      Promise.all(promiseAry).then(reslove => {
        this.drawerVisible = true
        this.spinning = true
        this.$nextTick(() => {
          this.handleShowData()
        })
      }, reject => {
        this.$message.error(reject)
      // eslint-disable-next-line handle-callback-err
      }).catch((error) => {
        this.$message.error('请求数据接口异常')
      })
    },
    // 处理显示数据
    handleShowData () {
      const showObj = {}
      if (this.$auth('project:projectList:getBasicInfo') && this.projectInfoObj) {
        // ---- 处理基本数据
        showObj.isImplemented = this.projectInfoObj.isImplemented
        showObj.income = this.projectInfoObj.income ? this.projectInfoObj.income : ''
        showObj.incomeTax = this.projectInfoObj.incomeTax ? this.projectInfoObj.incomeTax : ''
        showObj.totalStaff = this.projectInfoObj.totalStaff ? this.projectInfoObj.totalStaff : ''
        showObj.researchFee = this.projectInfoObj.researchFee ? this.projectInfoObj.researchFee : ''
        showObj.taxRefiefs = this.projectInfoObj.taxRefiefs ? this.projectInfoObj.taxRefiefs : ''
        showObj.techStaff = this.projectInfoObj.techStaff ? this.projectInfoObj.techStaff : ''
        showObj.financeStaff = this.projectInfoObj.financeStaff ? this.projectInfoObj.financeStaff : ''
        showObj.manager = this.projectInfoObj.manager ? this.projectInfoObj.manager : ''
        showObj.startTime = this.projectInfoObj.startTime ? moment(this.projectInfoObj.startTime) : undefined // 字符串转Date
        showObj.applyStatus = this.projectInfoObj.applyStatus
        showObj.hasPayPatent = this.projectInfoObj.hasPayPatent
        showObj.other = this.projectInfoObj.other ? this.projectInfoObj.other : ''
      }
      if (this.$auth('project:projectList:getTechInfo') && this.techInfoObj) {
        // ---- 初始技术数据
        showObj.tStartTime = this.techInfoObj.startTime ? moment(this.techInfoObj.startTime) : undefined // 字符串转Date
        showObj.tDockingTime = this.techInfoObj.dockingTime ? moment(this.techInfoObj.dockingTime) : undefined // 字符串转Date
        showObj.isSystem = undefined
        showObj.excavate = undefined
        showObj.prove = undefined
        showObj.IPRCount = undefined
        showObj.achievement = undefined
        showObj.benefit = undefined
        showObj.tech = undefined
        showObj.isTaxData = undefined
        showObj.isSystemFile = undefined
        showObj.isTechCore = undefined
        showObj.isTechTrack = undefined
        showObj.isTechResule = undefined
        showObj.techName = undefined
        showObj.techExplain = undefined
        showObj.techCore = undefined
        showObj.certificate = undefined
        showObj.techOther = undefined
        // 填写技术数据
        if (this.techInfoObj.content) {
          const techObj = JSON.parse(this.techInfoObj.content)
          const fieldNames = ['isSystem', 'excavate', 'prove', 'IPRCount', 'achievement', 'benefit', 'tech', 'isTaxData',
            'isSystemFile', 'isTechCore', 'isTechTrack', 'isTechResule', 'techName', 'techExplain', 'techCore', 'certificate', 'techOther']
          for (const fieldName of fieldNames) {
            showObj[fieldName] = techObj[fieldName]
          }
        }
        if (this.techInfoObj.models && this.techInfoObj.models.length) {
          for (const timeLine of this.techInfoObj.models) {
            this.$set(this.timeLineData, timeLine.itemType, timeLine)
          }
        }
      }
      if (this.$auth('project:projectList:getFinanceInfo') && this.financeInfoObj) {
        // ---- 财务数据
        showObj.dockingTime = this.financeInfoObj.dockingTime ? moment(this.financeInfoObj.dockingTime) : undefined // 字符串转date
        showObj.estimateRdAmount = this.financeInfoObj.estimateRdAmount
      }
      this.form.setFieldsValue(showObj)
      this.spinning = false
    },
    // 新的保存数据
    handledSaveData () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = true
          const postData = this.handledPostData(values)
          const promiseAry = []
          if (this.$auth('project:projectList:editBasicInfo')) {
            const basicModel = this.$http.post('/projectBasic/editProjectInfo', postData.basicModel).then((res) => {
              if (res.data && res.success) {
                return Promise.resolve({ success: true })
              } else {
                // return Promise.resolve({ success: false, message: res.errorMessage })
                return Promise.reject(res.errorMessage)
              }
            // eslint-disable-next-line handle-callback-err
            }).catch((error) => {
              this.$message.error('请求保存接口异常')
            })
            promiseAry.push(basicModel)
          }
          if (this.$auth('project:projectList:editTechInfo')) {
            const financeInfoModel = this.$http.post('/projectBasic/editTechInfo', postData.techInfoModel).then((res) => {
              if (res.data && res.success) {
                return Promise.resolve({ success: true })
              } else {
                // return Promise.resolve({ success: false, message: res.errorMessage })
                return Promise.reject(res.errorMessage)
              }
            // eslint-disable-next-line handle-callback-err
            }).catch((error) => {
              this.$message.error('请求保存接口异常')
            })
            promiseAry.push(financeInfoModel)
          }
          if (this.$auth('project:projectList:editFinanceInfo')) {
            const techInfoModel = this.$http.post('/projectBasic/editFinanceInfo', postData.financeInfoModel).then((res) => {
              if (res.data && res.success) {
                return Promise.resolve({ success: true })
              } else {
                // return Promise.resolve({ success: false, message: res.errorMessage })
                return Promise.reject(res.errorMessage)
              }
            // eslint-disable-next-line handle-callback-err
            }).catch((error) => {
              this.$message.error('请求保存接口异常')
            })
            promiseAry.push(techInfoModel)
          }
          Promise.all(promiseAry).then(resolve => {
            // for (const item of resolve) {
            //   if (!item.success) {
            //     this.$message.error(item.message)
            //     this.spinning = false
            //     return
            //   }
            // }
            this.spinning = false
            this.$message.success('操作成功！')
            // this.getServiceData(this.projectId)
          }, reject => {
            this.spinning = false
            this.$message.error(reject)
          }).catch((error) => {
            this.spinning = false
            this.$message.error(error.message)
          })
        }
      })
    },
    // 新的处理提交数据
    handledPostData (values) {
      // 基础模块信息
      let basicModel = {}
      let techInfoModel = {}
      let financeInfoModel = {}
      if (this.$auth('project:projectList:editBasicInfo')) {
        basicModel = {
          id: this.projectInfoObj.id ? this.projectInfoObj.id : undefined,
          projectId: this.projectId,
          isImplemented: values.isImplemented,
          income: values.income,
          incomeTax: values.incomeTax,
          totalStaff: values.totalStaff,
          researchFee: values.researchFee,
          taxRefiefs: values.taxRefiefs,
          techStaff: values.techStaff,
          financeStaff: values.financeStaff,
          manager: values.manager,
          startTime: values.startTime ? moment(values.startTime).format('YYYY-MM-DD HH:mm:ss') : undefined, // date要转字符串
          applyStatus: values.applyStatus,
          hasPayPatent: values.hasPayPatent,
          other: values.other
        }
      }
      // 技术模块信息
      if (this.$auth('project:projectList:editTechInfo')) {
        const content = {
          isSystem: values.isSystem,
          excavate: values.excavate,
          prove: values.prove,
          IPRCount: values.IPRCount,
          achievement: values.achievement,
          benefit: values.benefit,
          tech: values.tech,
          isTaxData: values.isTaxData,
          isSystemFile: values.isSystemFile,
          isTechCore: values.isTechCore,
          isTechTrack: values.isTechTrack,
          isTechResule: values.isTechResule,
          techName: values.techName,
          techExplain: values.techExplain,
          techCore: values.techCore,
          certificate: values.certificate,
          techOther: values.techOther
        }
        const contentStr = JSON.stringify(content)
        techInfoModel = {
          id: this.techInfoObj.id ? this.techInfoObj.id : undefined,
          projectId: this.projectId,
          startTime: values.tStartTime ? moment(values.tStartTime).format('YYYY-MM-DD HH:mm:ss') : undefined, // date要转字符串
          dockingTime: values.tDockingTime ? moment(values.tDockingTime).format('YYYY-MM-DD HH:mm:ss') : undefined, // date要转字符串
          content: contentStr
        }
      }

      // 财务数据
      if (this.$auth('project:projectList:editFinanceInfo')) {
        financeInfoModel = {
          id: this.financeInfoObj.id ? this.financeInfoObj.id : undefined,
          projectId: this.projectId,
          dockingTime: values.dockingTime ? moment(values.dockingTime).format('YYYY-MM-DD HH:mm:ss') : undefined, // date要转字符串
          estimateRdAmount: values.estimateRdAmount
        }
      }
      return {
        basicModel,
        techInfoModel,
        financeInfoModel
      }
    },
    // 计算时间线宽度
    computeTopWidth (totalDay, timeLine) {
      // this.$set(this.timeLineData, 'minDate', minDate)
      //   this.$set(this.timeLineData, 'maxDate', maxDate)
      //   const totalDay = maxDate.diff(minDate, 'days')
      //   this.$set(this.timeLineData, 'totalDay', totalDay)
      if (timeLine && timeLine.beginTime && timeLine.endTime) {
        const day = Math.abs(moment(timeLine.beginTime).diff(moment(timeLine.endTime), 'days'))
        const temp = (day / totalDay) * 100
        return temp.toFixed(2) + '%'
      } else {
        return '0%'
      }
    },
    // 计算时间线左偏移
    computeTopLeft (totalDay, minDate, timeLine) {
      if (timeLine && timeLine.beginTime) {
        const day = Math.abs(moment(minDate).diff(moment(timeLine.beginTime), 'days'))
        const temp = (day / totalDay) * 100
        return temp.toFixed(2) + '%'
      } else {
        return '0%'
      }
    },
    // 获取RD数组
    getRdList (rd) {
      let returnAry = []
      if (rd && rd.result) {
        returnAry = rd.result.split(',')
      }
      return returnAry
    },
    // 技术启动时间限制
    tStartTimeDisabled (currentDate) {
      const endDate = this.form.getFieldValue('tDockingTime')
      if (endDate) {
        return currentDate > endDate
      }
    // },
    },
    // 技术对接时间限制
    tDockingTimeDisabled (currentDate) {
      const beginDate = this.form.getFieldValue('tStartTime')
      const begin = beginDate
      if (begin) {
        return currentDate < begin
      }
    }
  }
}
</script>
<style lang="less" scoped>
  .saveContainer {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e9e9e9;
  }
  .saveBtn {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    padding: 8px;
  }
  .table_cell {
    padding: 8px;
  }
  #custom-table /deep/ .ant-input-number {
    border: none;
    border-radius: 0;
    width: 110px;
  }
  #custom-table /deep/ .ant-input-number-focused {
    box-shadow: none;
    -webkit-box-shadow: none;
  }
  #custom-table .custom-input {
    border-bottom: 1px solid #d9d9d9;
  }
  #custom-table p {
    margin-bottom: 5px;
  }
  #custom-table .formItem {
    display: inline-block;
  }
  #custom-table .noBottom {
    margin-bottom: 0px;
  }
  #custom-table .custom-disabled {
    background-color: #fff;
    color: #000;
  }
  #custom-table /deep/ .ant-input-disabled {
    background-color: #fff;
    color: #000;
  }
  #custom-table /deep/ .ant-radio-disabled + span {
    background-color: #fff;
    color: #000;
  }
  #custom-table /deep/ .ant-radio-disabled .ant-radio-inner::after {
    background-color: #1890FF;
  }
  #custom-table /deep/ .ant-form-item-control {
    line-height: 20px;
  }
  #custom-table .custom-align {
    display: inline-block;
    width: 75px;
    text-align: right;
  }
  #spin {
    position: static;
  }
  #spin /deep/ .ant-spin-container {
    position: static;
  }
  #custom-table td {
    word-break: break-all;
    word-wrap:break-word;
  }
  .RD {
    max-height: 118px;
    overflow-y: auto;
  }
  ul {
    margin-bottom: 0px ;
    padding-left: 45px;
  }
  ul li {
      list-style: none;
    }
  //时间线样式
  #custom-table {
    .timeLineContainer {
      position: relative;
    }
    .timeLine {
      width: 100%;
      height: 5px;
      border-radius: 10px;
    }
    .timeLine-top,.timeLine-bottom {
      position: absolute;
    }
    .timeLine-bottom {
      background-color: #e9e9e9;
    }
    .timeLine-top {
      background-color: #1890FF;
    }
  }
</style>
