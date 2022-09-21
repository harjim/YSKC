<template>
  <a-spin tip="请稍后..." :spinning="spinning">
    <div style="width:100%;">
      <a-table bordered :dataSource="tableDatas" :pagination="false" size="small" rowKey="name">
        <!-- <template slot="action" slot-scope="text,record">
        <a-input :value="text" @change="(e)=>onCellChange(e.target.value,record,'tel')" />
        </template>-->
        <a-table-column-group>
          <span slot="title" style="text-align:center">工业投资工业技术改造投资情况（万元）</span>
          <a-table-column align="left" title="序号" data-index="num" key="num" :width="80" />
          <a-table-column align="left" title="投资" data-index="name" key="name" :width="300" />
          <a-table-column align="left" title="数值" data-index="amount" key="amount" :width="160">
            <template slot-scope="text,record">
              <a-input-number
                :min="0"
                :precision="2"
                v-model="record.amount"
                style="width:100%"
                placeholder="请输入数值"
                @change="hasChange"
              />
            </template>
          </a-table-column>
        </a-table-column-group>
      </a-table>

      <a-table
        :dataSource="amountData"
        bordered
        :pagination="false"
        size="small"
        rowKey="robotsOfPlan"
      >
        <a-table-column-group>
          <span slot="title" style="text-align:center">新增机器人数量统计表（台/套）</span>
          <a-table-column
            align="center"
            :title="`截至${this.year}年底企业工业机器人保有数量`"
            data-index="robotsCount"
            key="robotsCount"
            :width="80"
          >
            <template slot-scope="text,record">
              <a-input-number
                :min="0"
                :precision="0"
                v-model="record.robotsCount"
                style="width:100%"
                @change="hasChange"
                placeholder="请输入数量"
              />
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            :title="`${this.year + 1}年预计新增工业机器人总数`"
            data-index="robotsOfPlan"
            key="robotsOfPlan"
            :width="80"
          >
            <template slot-scope="text,record">
              <a-input-number
                :min="0"
                :precision="0"
                v-model="record.robotsOfPlan"
                style="width:100%"
                @change="hasChange"
                placeholder="请输入数量"
              />
            </template>
          </a-table-column>
          <a-table-column-group :title="`其中（${this.year + 1}年）`">
            <a-table-column
              align="center"
              title="使用的国外工业机器人数量"
              data-index="robotsOfAbroad"
              key="robotsOfAbroad"
              :width="80"
            >
              <template slot-scope="text,record">
                <a-input-number
                  :min="0"
                  :precision="0"
                  v-model="record.robotsOfAbroad"
                  style="width:100%"
                  @change="hasChange"
                  placeholder="请输入数量"
                />
              </template>
            </a-table-column>
            <a-table-column
              align="center"
              title="国产（不含粤产） 工业机器人数量"
              data-index="robotsOfDomestic"
              key="robotsOfDomestic"
              @change="hasChange"
              :width="80"
            >
              <template slot-scope="text,record">
                <a-input-number
                  :min="0"
                  :precision="0"
                  v-model="record.robotsOfDomestic"
                  style="width:100%"
                  @change="hasChange"
                  placeholder="请输入数量"
                />
              </template>
            </a-table-column>
            <a-table-column
              align="center"
              title="粤产工业机器人数量"
              data-index="robotsOfGd"
              key="robotsOfGd"
              :width="80"
            >
              <template slot-scope="text,record">
                <a-input-number
                  :min="0"
                  :precision="0"
                  v-model="record.robotsOfGd"
                  style="width:100%"
                  @change="hasChange"
                  placeholder="请输入数量"
                />
              </template>
            </a-table-column>
          </a-table-column-group>
        </a-table-column-group>
      </a-table>
      <div style="padding-top: 18px; text-align: center">
        <a-button :disabled="noModify" type="primary" @click="submit">提交</a-button>
      </div>
    </div>
  </a-spin>
</template>
<script>
const columns = [{
  title: '工业投资工业技术改造投资情况（万元）',
  children: [{
    title: '序号',
    dataIndex: 'index',
    key: 'index',
    width: 100
  }, {
    title: '投资',
    dataIndex: 'name',
    key: 'name',
    width: 100
  }, {
    title: '数值',
    dataIndex: 'unmber',
    key: 'unmber',
    width: 100
  }]
}]
const defaultAmount = [{ robotsCount: 0, robotsOfPlan: 0, robotsOfAbroad: 0, robotsOfDomestic: 0, robotsOfGd: 0 }]
export default {
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    year: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      defaultData: [{
        num: '1',
        name: `${this.year}年度工业投资（万元）`,
        dataFiled: 'investment',
        amount: 0
      }, {
        num: '2',
        name: `${this.year}年度工业技术改造投资（万元）`,
        dataFiled: 'transform',
        amount: 0
      }, {
        num: '3',
        name: `其中：${this.year}年度节水改造投入（万元）`,
        dataFiled: 'water',
        amount: 0
      }, {
        num: '4',
        name: `${this.year + 1}年度工业投资计划（万元）`,
        dataFiled: 'investmentOfPlan',
        amount: 0
      }, {
        num: '5',
        name: `${this.year + 1}年度工业技术改造投资计划（万元）`,
        dataFiled: 'transformOfPlan',
        amount: 0
      }, {
        num: '6',
        name: `其中：${this.year + 1}年度节水改造投入计划（万元）`,
        dataFiled: 'waterOfPlan',
        amount: 0
      }],
      tableDatas: [],
      amountData: [],
      noModify: true,
      columns,
      id: null,
      spinning: false
    }
  },
  watch: {
    projectId (newId) {
      this.loadData(newId)
    }
  },
  created () {
    this.loadData(this.projectId)
  },
  methods: {
    hasChange () {
      this.noModify = false
    },
    loadData (projectId) {
      this.id = null
      this.noModify = true
      this.tableDatas = JSON.parse(JSON.stringify(this.defaultData))
      this.amountData = JSON.parse(JSON.stringify(defaultAmount))
      this.$http.get('/techProjectQuota/getQuota', { params: { projectId: projectId } })
        .then(res => {
          if (res.success && res.data) {
            this.id = res.data.id
            this.tableDatas.forEach(row => {
              row.amount = res.data[row.dataFiled]
            })
            this.amountData.forEach(row => {
              for (var prop in row) {
                row[prop] = res.data[prop]
              }
            })
          }
          return res.data
        })
    },
    submit () {
      this.spinning = true
      var values = {}
      this.tableDatas.forEach(row => {
        values[row.dataFiled] = row.amount
      })
      this.amountData.forEach(row => {
        values = Object.assign(values, row)
      })
      values.id = this.id
      values.projectId = this.projectId
      this.$http.post('/techProjectQuota/save', values)
        .then(res => {
          if (res.success && res.data) {
            this.noModify = true
            this.$message.success('保存成功')
            this.id = res.data
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
          this.spinning = false
        })
    }
  }
}
</script>
