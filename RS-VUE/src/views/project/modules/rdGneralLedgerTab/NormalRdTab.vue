<!--
 * @Author: zdf
 * @Date: 2021-07-15 15:34:43
 * @LastEditTime: 2022-07-14 17:34:07
 * @LastEditors: zdf
 * @Description: 通用辅助帐
 * @FilePath: \RS-VUE\src\views\project\modules\rdGneralLedgerTab\NormalRdTab.vue
-->
<template>
  <div>
    <div style="position:relative;text-align:center;">
      <b style="font-size:24px;">自主研发“研发支出”辅助账</b>
    </div>
    <div>
      <a-row :gutter="24">
        <!-- <a-col :span="17"><b>项目名称：{{ dataMap.pname }}&emsp;项目编号：{{ dataMap.rdNumber }}&emsp;&emsp;资本化、费用化支出选项：◎资本化&emsp;●费用化&emsp;项目实施状态选项：{{ dataMap.noFinish }}未完成&emsp;{{ dataMap.finished }}已完成</b></a-col>
        <a-col :span="7"><div style="text-align:right;"><b>研发成果：&emsp;&emsp;研发成果证书号：&emsp;金额单位：元（列至角分）</b></div></a-col>-->
        <a-col :span="5">
          <b>项目名称：{{ dataMap.pname }}</b>
        </a-col>
        <a-col :span="3">
          <div style="text-align:center;">
            <b>项目编号：{{ dataMap.rdNumber }}</b>
          </div>
        </a-col>
        <a-col :span="3">
          <div style="text-align:center;">
            <b>资本化、费用化支出选项：</b>
          </div>
        </a-col>
        <a-col :span="2">
          <div style="text-align:right;">
            <b>◎资本化●费用化</b>
          </div>
        </a-col>
        <a-col :span="4">
          <div style="text-align:right;">
            <b>项目实施状态选项：{{ dataMap.noFinish }}未完成{{ dataMap.finished }}已完成</b>
          </div>
        </a-col>
        <a-col :span="2">
          <div style="text-align:right;">
            <b>研发成果：</b>
          </div>
        </a-col>
        <a-col :span="2">
          <div style="text-align:right;">
            <b>研发成果证书号：</b>
          </div>
        </a-col>
        <a-col :span="3">
          <div style="text-align:right;">
            <b>金额单位：元（列至角分）</b>
          </div>
        </a-col>
      </a-row>
    </div>
    <div style="overflow:auto">
      <table border style="border:solid #333 1px;width:3400px;table-layer:fixed;">
        <tr class="tdHead">
          <td style="width:100px;" colspan="2" rowspan="3">{{ dataMap.year }}年</td>
          <td style="width:150px;" colspan="2" rowspan="3">凭证</td>
          <td style="width:100px;" rowspan="4">摘要</td>
          <td style="width:100px;" rowspan="4">借方金额</td>
          <td style="width:90px;" rowspan="4">贷方金额</td>
          <td style="width:50px;" rowspan="4">借或贷</td>
          <td style="width:110px;" rowspan="4">余额</td>
          <td colspan="26">费用明细（借方）</td>
        </tr>
        <tr class="tdHead">
          <td style="width:260px;" colspan="3">一、人员人工费用</td>
          <td style="width:740px;" colspan="8">二、直接投入费用</td>
          <td style="width:190px;" colspan="2">三、折旧费用</td>
          <td style="width:260px;" colspan="3">四、无形资产摊销</td>
          <td style="width:330px;" colspan="4">五、新产品设计费等</td>
          <td style="width:640px;" colspan="6">六、其他相关费用</td>
        </tr>
        <tr class="tdHead">
          <td style="width:200px;" colspan="2">直接从事研发活动人员</td>
          <td style="width:60px;" rowspan="2">外聘研发人员的劳务费用</td>
          <td style="width:290px;" colspan="3">研发活动直接消耗</td>
          <td style="width:110px;" rowspan="2">用于中间试验和产品试制的模具、工艺装备开发及制造费</td>
          <td style="width:70px;" rowspan="2">用于不构成固定资产的样品、样机及一般测试手段购置费</td>
          <td style="width:90px;" rowspan="2">用于试制产品的检验费</td>
          <td style="width:100px;" rowspan="2">用于研发活动的仪器、设备的运行维护、调整、检验、维修等费用</td>
          <td style="width:70px;" rowspan="2">通过经营租赁方式租入的用于研发活动的仪器、设备租赁费</td>

          <!-- 三-->
          <td style="width:85px;" rowspan="2">用于研发活动的仪器的折旧费</td>
          <td style="width:105px;" rowspan="2">用于研发活动的设备的折旧费</td>

          <!-- 四-->
          <td style="width:110px;" rowspan="2">用于研发活动的软件的摊销费用</td>
          <td style="width:75px;" rowspan="2">用于研发活动的专利权的摊销费用</td>
          <td style="width:75px;" rowspan="2">用于研发活动的非专利技术（包括许可证、专有技术、设计和计算方法等）的摊销费用</td>

          <!-- 五-->
          <td style="width:83px;" rowspan="2">新产品设计费</td>
          <td style="width:82px;" rowspan="2">新工艺规程制定费</td>
          <td style="width:82px;" rowspan="2">新药研制的临床试验费</td>
          <td style="width:83px;" rowspan="2">勘探开发技术的现场试验费</td>

          <!-- 六-->
          <td style="width:110px;" rowspan="2">技术图书资料费、资料翻译费、专家咨询费、高新科技研发保险费</td>
          <td style="width:110px;" rowspan="2">研发成果的检索、分析、评议、论证、鉴定、评审、评估、验收费用</td>
          <td style="width:100px;" rowspan="2">知识产权的申请费、注册费、代理费</td>
          <td style="width:100px;" rowspan="2">职工福利费、补充养老保险费、补充医疗保险费</td>
          <td style="width:110px;" rowspan="2">差旅费、会议费</td>
          <td style="width:110px;" rowspan="2">其他费用</td>
        </tr>
        <tr class="tdHead">
          <td style="width:50px;" rowspan="2">月</td>
          <td style="width:50px;" rowspan="2">日</td>
          <td style="width:50px;" rowspan="2">种类</td>
          <td style="width:100px;" rowspan="2">号数</td>
          <!-- 一 -->
          <td style="width:100px;">工资薪金</td>
          <td style="width:100px;">五险一金</td>

          <!-- 二-->
          <td style="width:90px;">材料</td>
          <td style="width:90px;">燃料</td>
          <td style="width:110px;">动力费用</td>
        </tr>
        <tr class="tdHead">
          <td colspan="5">序号</td>
          <td>1.1</td>
          <td>1.2</td>
          <td>1.3</td>
          <td>2.1</td>
          <td>2.2</td>
          <td>2.3</td>
          <td>2.4</td>
          <td>2.5</td>
          <td>2.6</td>
          <td>2.7</td>
          <td>2.8</td>
          <td>3.1</td>
          <td>3.2</td>
          <td>4.1</td>
          <td>4.2</td>
          <td>4.3</td>
          <td>5.1</td>
          <td>5.2</td>
          <td>5.3</td>
          <td>5.4</td>
          <td>6.1</td>
          <td>6.2</td>
          <td>6.3</td>
          <td>6.4</td>
          <td>6.5</td>
          <td>6.6</td>
        </tr>
        <tr class="tdHead">
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td colspan="4">期初余额</td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <tr class="tdNum" v-for="(t,index) in dataMap.data" :key="index">
          <td style="text-align:center;">{{ t.m }}</td>
          <td style="text-align:center;"></td>
          <td style="text-align:center;"></td>
          <td>{{ t.voucherNo }}</td>
          <td>{{ t.typeName }}</td>
          <td>{{ t.summary | NumberFormat }}</td>
          <td style="text-align:center;"></td>
          <td>{{ t.borrow }}</td>
          <td>{{ t.amount | NumberFormat }}</td>
          <td>{{ t.salary | NumberFormat }}</td>
          <td>{{ t.insurance | NumberFormat }}</td>
          <td>-</td>

          <td>{{ t.material | NumberFormat }}</td>
          <td>{{ t.fuel | NumberFormat }}</td>
          <td>{{ t.stimulus | NumberFormat }}</td>
          <td>{{ t.trialProd | NumberFormat }}</td>
          <td>{{ t.sampleMachine | NumberFormat }}</td>
          <td>{{ t.trialTest | NumberFormat }}</td>
          <td>{{ t.inspection | NumberFormat }}</td>
          <td>-</td>

          <td>{{ t.lab | NumberFormat }}</td>
          <td>{{ t.prod | NumberFormat }}</td>

          <td>{{ t.softAmortization | NumberFormat }}</td>
          <td>{{ t.patentAmortization | NumberFormat }}</td>
          <td>{{ t.otherAmortization | NumberFormat }}</td>

          <td>{{ t.design | NumberFormat }}</td>
          <td>{{ t.techProcedure | NumberFormat }}</td>
          <td>{{ t.clinicalTrials | NumberFormat }}</td>
          <td>{{ t.explore | NumberFormat }}</td>

          <td>{{ t.book | NumberFormat }}</td>
          <td>{{ t.rdProduction | NumberFormat }}</td>
          <td>{{ t.copyright | NumberFormat }}</td>
          <td>{{ t.benefits | NumberFormat }}</td>
          <td>{{ t.travel | NumberFormat }}</td>
          <td>{{ t.other | NumberFormat }}</td>
        </tr>
        <tr class="tdNum">
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td>期末余额</td>
          <td>{{ dataMap.total.summary | NumberFormat }}</td>
          <td></td>
          <td></td>
          <td>{{ dataMap.total.summary | NumberFormat }}</td>
          <td>{{ dataMap.total.salary | NumberFormat }}</td>
          <td>{{ dataMap.total.insurance | NumberFormat }}</td>
          <td>-</td>

          <td>{{ dataMap.total.material | NumberFormat }}</td>
          <td>{{ dataMap.total.fuel | NumberFormat }}</td>
          <td>{{ dataMap.total.stimulus | NumberFormat }}</td>
          <td>{{ dataMap.total.trialProd | NumberFormat }}</td>
          <td>{{ dataMap.total.sampleMachine | NumberFormat }}</td>
          <td>{{ dataMap.total.trialTest | NumberFormat }}</td>
          <td>{{ dataMap.total.inspection | NumberFormat }}</td>
          <td>-</td>

          <td>{{ dataMap.total.lab | NumberFormat }}</td>
          <td>{{ dataMap.total.prod | NumberFormat }}</td>

          <td>{{ dataMap.total.softAmortization | NumberFormat }}</td>
          <td>{{ dataMap.total.patentAmortization | NumberFormat }}</td>
          <td>{{ dataMap.total.otherAmortization | NumberFormat }}</td>

          <td>{{ dataMap.total.design | NumberFormat }}</td>
          <td>{{ dataMap.total.techProcedure | NumberFormat }}</td>
          <td>{{ dataMap.total.clinicalTrials | NumberFormat }}</td>
          <td>{{ dataMap.total.explore | NumberFormat }}</td>

          <td>{{ dataMap.total.book | NumberFormat }}</td>
          <td>{{ dataMap.total.rdProduction | NumberFormat }}</td>
          <td>{{ dataMap.total.copyright | NumberFormat }}</td>
          <td>{{ dataMap.total.benefits | NumberFormat }}</td>
          <td>{{ dataMap.total.travel | NumberFormat }}</td>
          <td>{{ dataMap.total.other | NumberFormat }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    dataMap: {
      type: Object,
      default: () => { return { total: {}, data: [] } }
    }
  }
}
</script>

<style>
.tdHead {
  text-align: center;
  word-wrap: break-word;
}
.tdNum {
  text-align: right;
  word-wrap: break-word;
}
</style>
