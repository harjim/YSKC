<template>
  <a-card :bordered="false">
    <vxe-grid border resizable align="center" :data="tableData" :columns="columns" />

    <statistics-drawer
      :visible.sync="visible"
      :title="title"
      :data="data"
      :isTech="isTech"
      :currentYear="currentYear"
      :rowIndex="rowIndex"
      @handleGetStatistics="getStatistics"
    />
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import StatisticsDrawer from './modules/StatisticsDrawer.vue'

export default {
  name: 'Statistics',
  components: {
    StatisticsDrawer
  },
  computed: {
    ...mapGetters(['currentYear', 'userInfo'])
  },
  watch: {
    currentYear () {
      this.loading = true
      this.getStatistics()
    }
  },
  created () {
    this.loading = true
    this.getStatistics()
  },
  data () {
    const renderColumnsNode = () => {
      const a = []
      for (let i = 1; i <= 12; i++) {
        a.push({
          title: `${i}月轨迹材料`,
          children: [
            {
              title: '技术部分',
              minWidth: 80,
              slots: {
                default: ({ row, rowIndex }, h) => {
                  if (row.techMap && row.techMap[i]) {
                    return [h(this.control.techView ? 'a' : 'span', {
                      domProps: {
                        innerHTML: `${row.techMap[i]}`
                      },
                      on: {
                        click: () => this.control.techView && this.handleClickTD('轨迹材料（技术部分）', i, rowIndex, 1, row)
                      }
                    })]
                  }
                  return ''
                }
              }
            },
            {
              title: '财务部分',
              minWidth: 80,
              slots: {
                default: ({ row, rowIndex }, h) => {
                  if (row.finaMap && row.finaMap[i]) {
                    return [h(this.control.finaView ? 'a' : 'span', {
                      domProps: {
                        innerHTML: `${row.finaMap[i]}`
                      },
                      on: {
                        click: () => this.control.finaView && this.handleClickTD('轨迹材料（财务部分）', i, rowIndex, 4, row)
                      }
                    })]
                  }
                  return ''
                }
              }
            }
          ]
        })
      }
      return a
    }
    return {
      loading: false,
      visible: false,
      isTech: true,
      title: '',
      data: {}, // 数据
      rowIndex: 0, // 行索引
      type: 1, // 类型
      treeData: [],
      tableData: [],
      columns: [
        {
          title: '节点',
          minWidth: 80,
          slots: {
            default: ({ row }, h) => {
              return [h('span', {
                domProps: {
                  innerHTML: row.node === 0 ? '优赛' : (row.node === 10 ? '工厂' : '区域')
                }
              })]
            }
          }
        },
        {
          title: '状态',
          minWidth: 80,
          slots: {
            default: ({ row }, h) => {
              return [h('span', {
                domProps: {
                  innerHTML: row.audit === 0 ? '未审核' : '已审核'
                }
              })]
            }
          }
        },
        {
          title: '立项材料',
          minWidth: 80,
          slots: {
            default: ({ row, rowIndex }, h) => {
              if (row.pApplication) {
                return [h(this.control.techView ? 'a' : 'span', {
                  domProps: {
                    innerHTML: `${row.pApplication}`
                  },
                  on: {
                    click: () => this.control.techView && this.handleClickTD('立项材料', null, rowIndex, 0, row)
                  }
                })]
              }
              return ''
            }
          }
        },
        ...renderColumnsNode(),
        {
          title: '验收材料',
          minWidth: 80,
          slots: {
            default: ({ row, rowIndex }, h) => {
              if (row.pAccept) {
                return [h(this.control.techView ? 'a' : 'span', {
                  domProps: {
                    innerHTML: `${row.pAccept}`
                  },
                  on: {
                    click: () => this.control.techView && this.handleClickTD('验收材料', null, rowIndex, 3, row)
                  }
                })]
              }
              return ''
            }
          }
        }
      ],
      control: {
        techView: this.$auth('deliver:statistics:techView'),
        finaView: this.$auth('deliver:statistics:finaView')
      }
    }
  },
  methods: {
    getStatistics () {
      this.$http.get('highTechProgress/getTotalList', {
        params: {
          year: this.currentYear
        }
      }).then(({ data, success, errorMessage }) => {
        if (success) {
          this.tableData = data
        } else {
          this.$message.error(errorMessage)
        }
      }).catch(err => {
        this.$message.error(err.message)
      }).finally(() => {
        this.loading = false
      })
    },
    handleClickTD (txt, month, rowIndex, type, row) {
      const node = row.node === 0 ? '优赛' : (row.node === 10 ? '工厂' : '区域')
      this.title = month ? `【${node}${month}月份】${txt}` : `【${node}】${txt}`
      this.data = {
        month: (type === 0 || type === 3) ? '0001-01-01 00:00:00' : `${this.currentYear}-${month}-01 00:00:00`,
        deliverType: type,
        node: row.node,
        audit: row.audit
      }
      this.rowIndex = rowIndex
      this.type = type === 4 ? 4 : 1
      this.isTech = type !== 4
      this.visible = true
    },
    submitAudit ({ suggestion, status, ids }) { // 审核提交
      const params = {
        suggestion,
        status,
        ids
      }
      this.$http.post('/highTechProgress/submitsAudit', params).then(({
        success,
        errorMessage
      }) => {
        if (success) {
          this.$message.success('操作成功！')
        } else {
          this.$message.error(errorMessage)
        }
      }).catch(e => this.$message.error(e.message))
    }
  }
}

</script>

<style>
</style>
