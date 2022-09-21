<!--
 * @Author: zdf
 * @Date: 2021-07-15 14:43:11
 * @LastEditTime: 2021-07-20 15:25:16
 * @LastEditors: zdf
 * @Description: 上海rd 辅助帐其他页
 * @FilePath: \RS-VUE\src\views\project\modules\rdGneralLedgerTab\ShangHaiRdTab.vue
-->
<!--
 * @Author: zdf
 * @Date: 2021-07-14 17:03:59
 * @LastEditTime: 2021-07-15 10:52:45
 * @LastEditors: zdf
 * @Description: sd
 * @FilePath: \RS-VUE\src\views\project\RdGeneralLedger.vue
-->
<template>
  <div>
    <div style="position:relative;text-align:center;">
      <b style="font-size:24px;">“研发支出”辅助账</b>
    </div>
    <div>
      <a-row :gutter="24">
        <a-col :span="4"><b>项目编号：{{ dataMap.rdNumber }}</b></a-col>
        <a-col :span="6"><div style="text-align:center;"><b>项目名称：{{ dataMap.pname }}</b></div></a-col>
        <a-col :span="4"><div style="text-align:center;"><b>研发形式：自主研发</b></div></a-col>
        <a-col :span="4"><div style="text-align:center;"><b>完成情况：{{ dataMap.finish }}</b></div></a-col>
        <a-col :span="3"><div style="text-align:right;"><b>支出类型：费用化</b></div></a-col>
        <a-col :span="3"><div style="text-align:right;"><b>金额单位：元</b></div></a-col>
      </a-row>
    </div>
    <div style="overflow:auto">
      <table border style="border:solid #333 1px;width:3400px;table-layer:fixed;">
        <tr class="tdHead">
          <td style="width:260px;" colspan="3" rowspan="2">凭证信息</td>
          <td style="width:100px;" rowspan="3">会计凭证记载金额</td>
          <td style="width:100px;" rowspan="3">税收口径归集金额</td>
          <td colspan="29">费用明细（税收口径）</td>
        </tr>
        <tr class="tdHead">
          <td style="width:260px;" colspan="3">一、人员人工费用</td>
          <td style="width:740px;" colspan="8">二、直接投入费用</td>
          <td style="width:190px;" colspan="2">三、折旧费用</td>
          <td style="width:260px;" colspan="3">四、无形资产摊销</td>
          <td style="width:330px;" colspan="4">五、新产品设计费等</td>
          <td style="width:640px" colspan="6">六、其他相关费用及限额</td>
          <td style="width:260px;" colspan="3">七、委托研发费用及限额</td>
        </tr>
        <tr class="tdHead">
          <td style="width:75px;">日期</td>
          <td style="width:110px;">号数</td>
          <td style="width:75px;">摘要</td>
          <!-- 一 -->
          <td style="width:100px;">直接从事研发活动人员的工资薪金</td>
          <td style="width:100px;">直接从事研发活动人员的五险一金</td>
          <td style="width:60px;">外聘研发人员的劳务费用</td>

          <!-- 二-->
          <td style="width:90px;">研发活动直接消耗的材料费用</td>
          <td style="width:90px;">研发活动直接消耗的燃料费用</td>
          <td style="width:110px;">研发活动直接消耗的动力费用</td>
          <td style="width:110px;">用于中间试验和产品试制的模具、工艺装备开发及制造费</td>
          <td style="width:70px;">不构成固定资产的样品、样机及一般测试手段购置费</td>
          <td style="width:90px;">试制产品的检验费</td>
          <td style="width:100px;">用于研发活动的仪器、设备的运行维护、调整、检验、维修等费用</td>
          <td style="width:70px;">通过经营租赁方式租入的用于研发活动的仪器、设备租赁费</td>

          <!-- 三-->
          <td style="width:85px;">用于研发活动的仪器的折旧费</td>
          <td style="width:105px;">用于研发活动的设备的折旧费</td>

          <!-- 四-->
          <td style="width:110px;">用于研发活动的软件的摊销费用</td>
          <td style="width:75px;">用于研发活动的专利权的摊销费用</td>
          <td style="width:75px;">用于研发活动的非专利技术的摊销费用</td>

          <!-- 五-->
          <td style="width:83px;">新产品设计费</td>
          <td style="width:82px;">新工艺规程制定费</td>
          <td style="width:82px;">新药研制的临床试验费</td>
          <td style="width:83px;">勘探开发技术的现场试验费</td>

          <!-- 六-->
          <td style="width:110px;">技术图书资料费、资料翻译费、专家咨询费、高新科技研发保险费</td>
          <td style="width:110px;">研发成果的检索、分析、评议、论证、鉴定、评审、评估、验收费用</td>
          <td style="width:100px;">知识产权的申请费、注册费、代理费</td>
          <td style="width:100px;">职工福利费、补充养老保险费、补充医疗保险费</td>
          <td style="width:110px;">差旅费、会议费</td>
          <td style="width:110px;">经限额调整后的其他相关费用</td>
          <!-- 七-->
          <td style="width:90px;">委托境内机构或个人进行研发活动所发生的费用</td>
          <td style="width:90px;">委托境外机构进行研发活动所发生的费用</td>
          <td style="width:80px;">经限额调整后的委托境外机构进行研发活动所发生的费用</td>
        </tr>
        <tr class="tdHead">
          <td colspan="3">期初余额（仅适用未完成资本化项目）</td>
          <td>*</td><td></td>
          <td></td><td></td><td></td>
          <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
          <td></td><td></td>
          <td></td><td></td><td></td>
          <td></td><td></td><td></td><td></td>
          <td></td><td></td><td></td><td></td><td></td><td></td>
          <td></td><td></td><td></td>
        </tr>
        <tr class="tdNum" v-for="(t,index) in dataMap.data" :key="index">
          <td>{{ t.month }}</td>
          <td>{{ t.voucherNo }}</td>
          <td>{{ t.typeName }}</td>
          <td>{{ t.amount }}</td>
          <td>{{ t.summary }}</td>
          <td>{{ t.salary | NumberFormat }}</td><td>{{ t.insurance | NumberFormat }}</td><td>-</td>

          <td>{{ t.material | NumberFormat }}</td><td>{{ t.fuel | NumberFormat }}</td><td>{{ t.stimulus | NumberFormat }}</td>
          <td>{{ t.trialProd | NumberFormat }}</td><td>{{ t.sampleMachine | NumberFormat }}</td>
          <td>{{ t.trialTest | NumberFormat }}</td><td>{{ t.inspection | NumberFormat }}</td><td>-</td>

          <td>{{ t.lab | NumberFormat }}</td><td>{{ t.prod | NumberFormat }}</td>

          <td>{{ t.softAmortization | NumberFormat }}</td><td>{{ t.patentAmortization | NumberFormat }}</td>
          <td>{{ t.otherAmortization | NumberFormat }}</td>

          <td>{{ t.design | NumberFormat }}</td><td>{{ t.techProcedure | NumberFormat }}</td>
          <td>{{ t.clinicalTrials | NumberFormat }}</td><td>{{ t.explore | NumberFormat }}</td>

          <td>{{ t.book | NumberFormat }}</td><td>{{ t.rdProduction | NumberFormat }}</td><td>{{ t.copyright | NumberFormat }}</td>
          <td>{{ t.benefits | NumberFormat }}</td><td>{{ t.travel | NumberFormat }}</td><td>{{ t.other | NumberFormat }}</td>

          <td>{{ t.inside | NumberFormat }}</td><td>{{ t.outside | NumberFormat }}</td><td>-</td>
        </tr>
        <tr class="tdNum">
          <td style="text-align:center;">合计金额</td>
          <td></td><td></td><td style="text-align:center;">*</td><td>{{ dataMap.total.summary }}</td>
          <td>{{ dataMap.total.salary | NumberFormat }}</td><td>{{ dataMap.total.insurance | NumberFormat }}</td><td>-</td>

          <td>{{ dataMap.total.material | NumberFormat }}</td><td>{{ dataMap.total.fuel | NumberFormat }}</td><td>{{ dataMap.total.stimulus | NumberFormat }}</td>
          <td>{{ dataMap.total.trialProd | NumberFormat }}</td><td>{{ dataMap.total.sampleMachine | NumberFormat }}</td>
          <td>{{ dataMap.total.trialTest | NumberFormat }}</td><td>{{ dataMap.total.inspection | NumberFormat }}</td><td>-</td>

          <td>{{ dataMap.total.lab | NumberFormat }}</td><td>{{ dataMap.total.prod | NumberFormat }}</td>

          <td>{{ dataMap.total.softAmortization | NumberFormat }}</td><td>{{ dataMap.total.patentAmortization | NumberFormat }}</td>
          <td>{{ dataMap.total.otherAmortization | NumberFormat }}</td>

          <td>{{ dataMap.total.design | NumberFormat }}</td><td>{{ dataMap.total.techProcedure | NumberFormat }}</td>
          <td>{{ dataMap.total.clinicalTrials | NumberFormat }}</td><td>{{ dataMap.total.explore | NumberFormat }}</td>

          <td>{{ dataMap.total.book | NumberFormat }}</td><td>{{ dataMap.total.rdProduction | NumberFormat }}</td><td>{{ dataMap.total.copyright | NumberFormat }}</td>
          <td>{{ dataMap.total.benefits | NumberFormat }}</td><td>{{ dataMap.total.travel | NumberFormat }}</td><td>{{ dataMap.total.other | NumberFormat }}</td>

          <td>{{ dataMap.total.inside | NumberFormat }}</td><td>{{ dataMap.total.outside | NumberFormat }}</td><td>-</td>
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
.tdHead{
    text-align:center;
    word-wrap:break-word;
}
.tdNum{
    text-align:right;
    word-wrap:break-word;
}
</style>
