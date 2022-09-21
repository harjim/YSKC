<template>
  <div class="page-header" style="height:59px">
    <div class="page-header-index-wide clearfix" style="position: relative;">
      <s-breadcrumb :hasYearSelect="hasYearSelect" />
      <div style="position: absolute; right: 20px; top: 0; font-size: 16px;">
        <div v-if="modeList[$route.meta.id] && userInfo.userSource && currentShow && $route.meta.id !== 7" >
          <span style="margin:0 10px;" >
            <span>审核状态：</span>
            <span style="font-weight: 550;" :style="{color: statusColor[auidtStatus]}">{{ statusName[auidtStatus] }}</span>
          </span>
          <span v-if="isEditStatus(auidtStatus)" style="height: 100%" >
            <a-divider style="height: 2em; width: 2px;" type="vertical" />
            <a-button :loading="isDisabled" type="primary" @click="handleCommitAuidt">提交</a-button>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Breadcrumb from '@/components/tools/Breadcrumb'
import { isEditStatus, isExportStatus, getStatusName } from '@/utils/processDoc/auditStatus'
import yearMiXin from '@/utils/yearMixin'
import { mapGetters, mapActions } from 'vuex'
const modeList = {
  '23': 1, // 研发组织架构
  '214': 2, // 研发人员名单
  '220': 3, // 研发设备清单
  // '20': 4, // 项目基本情况
  // '218': 7, // 专利管理明细
  // '207': 8, // 研发成果管理(查新报告)
  '215': 9 // 机构建设事项(创新体系)
}
const statusName = {
  '0': '进行中',
  '1': '通过',
  '2': '驳回',
  '3': '激活',
  '4': '已提交',
  '5': '未提交',
  '6': '审核失败',
  '7': '提交失败'
}
const statusColor = {
  '-1': '#C8DF02',
  '0': '#606266',
  '1': '#5BB51D',
  '2': 'rgb(255,70,0)',
  '3': '#C8DF02',
  '6': 'rgb(255,70,0)',
  '7': 'rgb(255,70,0)'
}
export default {
  mixins: [yearMiXin],
  name: 'PageHeader',
  components: {
    's-breadcrumb': Breadcrumb
  },
  props: {
    title: {
      type: [String, Boolean],
      default: true,
      required: false
    },
    logo: {
      type: String,
      default: '',
      required: false
    },
    avatar: {
      type: String,
      default: '',
      required: false
    },
    hasYearSelect: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  data () {
    return {
      modeList,
      isDisabled: false,
      auidtStatus: 0,
      statusName,
      statusColor,
      moduleId: '',
      currentShow: false
    }
  },
  watch: {
    '$route.meta.id' (m) {
      this.moduleId = this.modeList[m]
      this.getAudit()
      this.currentShow = this.isSubmit()
    }
  },
  created () {
    this.moduleId = this.modeList[this.$route.meta.id]
    this.getAudit()
    this.currentShow = this.isSubmit()
  },
  methods: {
    ...mapActions(['setAuditStatus']),
    isEditStatus,
    isExportStatus,
    getStatusName,
    search () {
      this.getAudit()
    },
    handleCommitAuidt () {
      const me = this
      this.$confirm({
        title: '您确定要提交?',
        onOk () {
          me.commitAuidt()
        },
        onCancel () {}
      })
    },
    commitAuidt () {
      this.isDisabled = true
      // const modeName = this.$route.meta.title
      const postData = {
        year: this.currentYear,
        moduleId: this.moduleId
      }
      this.$http.post('/projectAudit/submitAudit', postData).then((res) => {
        if (res.data && res.success) {
          this.$message.success('操作成功')
          this.getAudit()
          this.isDisabled = false
        } else {
          this.$message.error(res.errorMessage)
          this.isDisabled = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.isDisabled = false
      })
    },
    getAudit () {
      // rs用户或无moduleId，auditStatus为5:未提交状态
      if (!this.userInfo.userSource || !this.moduleId) {
        this.setAuditStatus(5)
        return
      }
      this.isDisabled = true
      this.$http.get('/projectAudit/getAudit', { params: { year: this.currentYear, moduleId: this.moduleId } })
        .then(res => {
          if (res.success) {
            this.auidtStatus = res.data
            this.setAuditStatus(this.auidtStatus)
          }
        }).finally(() => {
          this.isDisabled = false
        })
    },
    isSubmit () {
      switch (this.moduleId) {
        case 1:
          return this.$auth('rdorg:arch:rd:submit')
        case 2:
          return this.$auth('rdorg:rdEmployee:submit')
        case 3:
          return this.$auth('rdorg:rdEquipment:submit')
        case 4:
          return this.$auth('project:reporting:submit')
        case 5:
          return this.$auth('project:doc:submit')
        case 6:
          return this.$auth('project:doc:submit')
        case 7:
          return this.$auth('project:patent:submit')
        case 8:
          return this.$auth('project:result:submit')
        case 9:
          return this.$auth('rdorg:buildList:submit')
        default:
          return false
      }
    }
  }
}
</script>

<style lang="less" scoped>
.page-header {
  background: #fff;
  padding: 16px 24px 0;
  border-bottom: 1px solid #e8e8e8;

  .breadcrumb {
    margin-bottom: 16px;
  }
  .detail {
    display: flex;
    /*margin-bottom: 16px;*/

    .avatar {
      flex: 0 1 72px;
      margin: 0 24px 8px 0;

      & > span {
        border-radius: 72px;
        display: block;
        width: 72px;
        height: 72px;
      }
    }

    .main {
      width: 100%;
      flex: 0 1 auto;

      .row {
        display: flex;
        width: 100%;

        .avatar {
          margin-bottom: 16px;
        }
      }
      .title {
        font-size: 20px;
        font-weight: 500;

        font-size: 20px;
        line-height: 28px;
        font-weight: 500;
        color: rgba(0, 0, 0, 0.85);
        margin-bottom: 16px;
        flex: auto;
      }
      .logo {
        width: 28px;
        height: 28px;
        border-radius: 4px;
        margin-right: 16px;
      }
      .content,
      .headerContent {
        flex: auto;
        color: rgba(0, 0, 0, 0.45);
        line-height: 22px;

        .link {
          margin-top: 16px;
          line-height: 24px;

          a {
            font-size: 14px;
            margin-right: 32px;
          }
        }
      }
      .extra {
        flex: 0 1 auto;
        margin-left: 88px;
        min-width: 242px;
        text-align: right;
      }
      .action {
        margin-left: 56px;
        min-width: 266px;
        flex: 0 1 auto;
        text-align: right;
        &:empty {
          display: none;
        }
      }
    }
  }
}

.mobile .page-header {
  .main {
    .row {
      flex-wrap: wrap;

      .avatar {
        flex: 0 1 25%;
        margin: 0 2% 8px 0;
      }

      .content,
      .headerContent {
        flex: 0 1 70%;

        .link {
          margin-top: 16px;
          line-height: 24px;

          a {
            font-size: 14px;
            margin-right: 10px;
          }
        }
      }

      .extra {
        flex: 1 1 auto;
        margin-left: 0;
        min-width: 0;
        text-align: right;
      }

      .action {
        margin-left: unset;
        min-width: 266px;
        flex: 0 1 auto;
        text-align: left;
        margin-bottom: 12px;

        &:empty {
          display: none;
        }
      }
    }
  }
}
</style>
