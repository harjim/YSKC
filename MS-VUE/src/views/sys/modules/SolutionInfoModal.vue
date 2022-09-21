<!--
 * @Author: zdf
 * @Date: 2021-07-12 14:17:50
 * @LastEditTime: 2021-07-16 16:43:53
 * @LastEditors: zdf
 * @Description: 解决方案明细
 * @FilePath: \MS-VUE\src\views\sys\modules\SolutionInfoModal.vue
-->
<template>
  <a-drawer
    :title="title"
    :width="1000"
    :headerStyle="{padding: '12px 16px'}"
    :bodyStyle="{padding: '0px 7px' ,height: 'calc(100% - 55px)'}"
    @close="onClose"
    :destroyOnClose="true"
    :visible="visible"
    :zIndex="99"
  >
    <div style="margin-top:20px;">
      <a-spin :spinning="spinning" tip="请稍后...">
        <table border style="width:100%;border: solid #ddd 1px;">
          <template v-if="row.type === 0">
            <tr>
              <td style="width:10;text-align:center;"><h4>客户名称</h4></td>
              <td style="width:40%;">
                <div style="margin-left:6px;">
                  <a-tooltip>
                    <template slot="title">
                      {{ row.customer }}
                    </template><h4>{{ row.customer }}</h4>
                  </a-tooltip>
                </div>
              </td>
              <td style="width:10%;text-align:center;"><h4>服务项目</h4></td>
              <td style="width:40%;">
                <div style="margin-left:6px;">
                  <a-tooltip>
                    <template slot="title">
                      {{ row.serveProject }}
                    </template><h4>{{ row.serveProject }}</h4>
                  </a-tooltip>
                </div>
              </td>
            </tr>
            <tr>
              <td style="width:10%;text-align:center;"><h4>行业</h4></td>
              <td style="width:40%;">
                <div style="padding-left:6px;">
                  <a-tooltip>
                    <template slot="title">
                      {{ row.industry }}
                    </template><h4>{{ row.industry }}</h4>
                  </a-tooltip>
                </div>
              </td>
              <td style="width:10;text-align:center;">星级</td>
              <td style="width:40%;">
                <div style="padding-left:6px;">
                  <a-rate v-model="curRate" :allowHalf="true" @change="changeRate" :disabled="notCanRate"/>
                </div>
              </td>
            </tr>
          </template>
          <template v-else>
            <tr>
              <td style="width:10;text-align:center;"><h4>服务项目</h4></td>
              <td style="width:40%;">
                <div style="margin-left:6px;">
                  <a-tooltip>
                    <template slot="title">
                      {{ row.serveProject }}
                    </template><h4>{{ row.serveProject }}</h4>
                  </a-tooltip>
                </div>
              </td>
              <td style="width:10%;text-align:center;"><h4>行业</h4></td>
              <td style="width:40%;">
                <div style="margin-left:6px;">
                  <a-tooltip>
                    <template slot="title">
                      {{ row.industry }}
                    </template><h4>{{ row.industry }}</h4>
                  </a-tooltip>
                </div>
              </td>
            </tr>
          </template>
        </table>
        <a-divider/>
        <div>
          <h2 style="text-align:center;">{{ row.title }}</h2>
          <div v-html="row.content"></div>
        </div>
      </a-spin>
    </div>
  </a-drawer>
</template>

<script>
import Template from '@/views/doc/Template.vue'
export default {
  components: { Template },
  data () {
    return {
      visible: false,
      title: '',
      row: {},
      curRate: 0,
      notCanRate: !this.$auth('sys:solution:rate'),
      spinning: false
    }
  },
  methods: {
    show (row) {
      this.row = row
      this.visible = true
      this.title = row.title
      this.curRate = row.rate / 2
    },
    changeRate (v) {
      const rate = parseInt(v * 2)
      this.spinning = true
      this.$http.post('/solution/rate', { id: this.row.id, rate: rate }).then(res => {
        if (res.success && res.data) {
          this.row.rate = rate
        } else {
          this.$message.error(res.errorMessage || '评星失败')
        }
      }).finally(() => {
        this.spinning = false
      })
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
