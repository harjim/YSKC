<template>
  <a-modal
    title="查重"
    :width="800"
    :visible="visible"
    :maskClosable="false"
    style="top: 0px;"
    :destroyOnClose="true"
    :bodyStyle="{ height: '65vh', maxHeight: '65vh', overflow: 'auto' }"
    :footer="null"
    :mask="false"
    @cancel="visible = false"
  >
    <div>
      <a-form layout="inline">
        <a-form-item label="相似度">
          &gt;&nbsp;<a-input-number
            size="small"
            :min="70"
            :max="100"
            v-model="limitRate"
            :formatter="value => `${value}%`"
            :parser="value => value.replace('%', '')"
            style="width:60px;"
          />
        </a-form-item>
        <a-form-item>
          <span>
            <a-button type="primary" size="small" @click="check" :loading="loading">确定</a-button>
          </span>
        </a-form-item>
      </a-form>
    </div>
    <a-spin tip="数据比较中,请稍等..." :spinning="loading">
      <a-list item-layout="vertical" :data-source="checkList">
        <template slot="renderItem" slot-scope="item">
          <a-list-item>
            <h4>{{ `${item.rdTitle} ${item.pname}` }}</h4>
            <div>
              <vxe-grid
                max-height="360"
                :data="item.simList"
                auto-resize
                highlight-current-row
                highlight-hover-row
                resizable
                show-overflow="title"
                size="small"
              >
                <vxe-table-column
                  type="seq"
                  width="40"
                ></vxe-table-column>
                <vxe-table-column
                  align="left"
                  field="data.companyName"
                  width="140"
                  title="客户名称"
                ></vxe-table-column>
                <vxe-table-column
                  align="left"
                  field="data.rdTitle"
                  width="80"
                  title="RD"
                >
                </vxe-table-column>
                <vxe-table-column
                  align="left"
                  field="data.pname"
                  title="项目名称"
                ><template v-slot="{row}">
                  <span @click="checkContent(item,row.data)">{{ row.data.pname }}</span>
                </template>
                </vxe-table-column>
                <vxe-table-column
                  align="left"
                  field="similarity"
                  title="相似度"
                  width="60"
                >
                </vxe-table-column>
                <vxe-table-column
                  align="left"
                  field="data.createTime"
                  header-align="left"
                  title="创建时间"
                  width="140"
                ></vxe-table-column>
              </vxe-grid>
            </div>
          </a-list-item>
        </template>
      </a-list>
    </a-spin>
  </a-modal>
</template>
<script>
export default {
  data () {
    return {
      checkList: [],
      visible: false,
      loading: false,
      limitRate: 90
    }
  },
  props: {
    checkUrl: {
      type: String,
      required: true
    },
    checkId: {
      type: String,
      default: 'id'
    }
  },
  methods: {
    open (checkList) {
      this.checkList = checkList
      this.visible = true
    },
    check () {
      this.loading = true
      this.$http.get(this.checkUrl, { params: {
        ids: this.checkList.map(a => a[this.checkId]),
        limitRate: this.limitRate / 100
      } }).then((res) => {
        if (res.success) {
          for (let i = 0; i < this.checkList.length; i++) {
            const c = { ...this.checkList[i] }
            if (res.data[c[this.checkId]]) {
              c.simList = res.data[c[this.checkId]]
            } else {
              c.simList = []
            }
            this.$set(this.checkList, i, c)
          }
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally(() => {
        this.loading = false
      })
    },
    checkContent (source, target) {
      this.$emit('viewDiff', source, target)
    }
  }
}
</script>
