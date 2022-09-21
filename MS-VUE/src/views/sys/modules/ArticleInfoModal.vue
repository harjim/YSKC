<template>
  <a-drawer
    :width="1000"
    :headerStyle="{ padding: '12px 16px' }"
    :bodyStyle="{ padding: '0px 7px', height: 'calc(100% - 55px)' }"
    @close="onClose"
    :destroyOnClose="true"
    :visible="visible"
    :zIndex="99"
  >
    <div style="margin-top: 20px">
      <a-spin :spinning="this.spinning" tip="请稍后...">
        <table border style="width: 100%; border: solid #ddd 1px">
          <template>
            <tr>
              <td style="width: 10; text-align: center"><h4>文章标题</h4></td>
              <td style="width: 40%">
                <div style="margin-left: 6px">
                  <a-tooltip>
                    <template slot="title">
                      {{ row.tittle }}
                    </template>
                    <h4>{{ row.title }}</h4>
                  </a-tooltip>
                </div>
              </td>
              <td style="width: 10%; text-align: center"><h4>文章作者</h4></td>
              <td style="width: 40%">
                <div style="margin-left: 6px">
                  <a-tooltip>
                    <template slot="tittle">
                      {{ row.author }}
                    </template>
                    <h4>{{ row.author }}</h4>
                  </a-tooltip>
                </div>
              </td>
            </tr>
            <tr>
              <td style="width: 10%; text-align: center"><h4>文章类型</h4></td>
              <td style="width: 40%">
                <div style="padding-left: 6px">
                  <a-tooltip>
                    <template slot="tittle">
                      {{ row.type === 0 ? '公司新闻' : '行业动态' }}
                    </template>
                    <h4>{{ row.type }}</h4>
                  </a-tooltip>
                </div>
              </td>
              <td style="width: 10%; text-align: center"><h4>文章来源</h4></td>
              <td style="width: 40%">
                <div style="padding-left: 6px">
                  <a-tooltip>
                    <template slot="tittle">
                      {{ row.source }}
                    </template>
                    <h4>{{ row.source }}</h4>
                  </a-tooltip>
                </div>
              </td>
            </tr>
            <tr>
              <td style="width: 10%; text-align: center"><h4>发行日期</h4></td>
              <td style="width: 40%">
                <div style="padding-left: 6px">
                  <a-tooltip>
                    <template slot="tittle">
                      {{ row.issueDate }}
                    </template>
                    <h4>{{ row.issueDate }}</h4>
                  </a-tooltip>
                </div>
              </td>
            </tr>
          </template>
        </table>
        <div>
          <h2 style="text-align: center">{{ row.title }}</h2>
          <div v-html="row.content">{{ row.content }}</div>
        </div>
        <a-divider />
      </a-spin>
    </div>
  </a-drawer>
</template>

<script>
export default {
  data () {
    return {
      row: {},
      visible: false,
      spinning: false
    }
  },
  methods: {
    show (row) {
      this.row = row
      this.visible = true
      this.title = row.title
      this.type = row.type === 0 ? '公司新闻' : '行业动态'
      this.source = row.source
      this.author = row.author
      this.issueDate = row.issueDate
      this.curRate = row.rate / 2
    },
    onClose () {
      this.visible = false
      this.spinning = false
      this.row = {}
    }
  }
}
</script>

<style>
</style>
