<template>
  <a-card style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}">
    <a-spin :spinning="spinning" tip="请稍后..." >
      <template v-if="$auth('company:setting:checkDate:view')">
        <ys-divider title="工资核算开始日期"/>
        <a-spin tip="请稍后..." :spinning="editStatus['accountPeriod'].spin">
          <div class="item_body">
            <a-form layout="inline">
              <div>
                <a-form-item label="开始月">
                  <a-select v-model="settingData.accountPeriod['accountMonth']" style="width:165px;" v-if="editStatus['accountPeriod'].edit">
                    <a-select-option value="0">上月</a-select-option>
                    <a-select-option value="1">本月</a-select-option>
                  </a-select>
                  <div v-else class="item_text">
                    {{ accountMonthArr[settingData.accountPeriod['accountMonth']] || '-' }}
                  </div>
                </a-form-item>
                <a-form-item label="开始日">
                  <a-select v-model="settingData.accountPeriod['day']" style="width:165px;" v-if="editStatus['accountPeriod'].edit">
                    <a-select-option v-for="v in 28" :value="v" :key="v">{{ v }}</a-select-option>
                  </a-select>
                  <div v-else class="item_text">
                    {{ settingData.accountPeriod['day']? settingData.accountPeriod['day'] : '-' }}
                  </div>
                </a-form-item>
              </div>
              <div>
                <a-form-item class="item_modify" v-if="$auth('company:setting:checkDate:edit')">
                  <template v-if="editStatus['accountPeriod'].edit">
                    <a-button class="item_btn" type="primary" @click="save('accountPeriod')">保存</a-button>
                    <a-button class="item_btn" type="primary" @click="cancel('accountPeriod')">取消</a-button>
                  </template>
                  <a-button class="item_btn" type="primary" v-if="!editStatus['accountPeriod'].edit" @click="edit('accountPeriod')">编辑</a-button>
                </a-form-item>
              </div>
            </a-form>
          </div>
        </a-spin>
      </template>
      <template v-if="$auth('company:setting:punchCard:view')">
        <ys-divider title="研发打卡范围"/>
        <a-spin tip="请稍后..." :spinning="editStatus['miniProgram'].spin">
          <div class="item_body">
            <a-form layout="inline">
              <div>
                <a-form-item label="浮动范围（百分比/月）">
                  <a-input-number
                    style="width:165px;"
                    v-if="editStatus['miniProgram'].edit"
                    v-model="settingData.miniProgram['rangeValue']"
                    :precision="1"
                    :max="100"
                    :min="0"/>
                  <div v-else class="item_text">
                    {{ settingData.miniProgram['rangeValue']? settingData.miniProgram['rangeValue'] : '-' }}
                  </div>
                </a-form-item>
              </div>
              <div>
                <a-form-item class="item_modify" v-if="$auth('company:setting:punchCard:edit')">
                  <template v-if="editStatus['miniProgram'].edit">
                    <a-button class="item_btn" type="primary" @click="save('miniProgram')">保存</a-button>
                    <a-button class="item_btn" type="primary" @click="cancel('miniProgram')">取消</a-button>
                  </template>
                  <a-button class="item_btn" type="primary" v-if="!editStatus['miniProgram'].edit" @click="edit('miniProgram')">编辑</a-button>
                </a-form-item>
              </div>
            </a-form>
          </div>
        </a-spin>
      </template>
      <!-- <template v-if="$auth('company:setting:percentage:view')">
        <ys-divider title="优惠明细表加计扣除比例"/>
        <a-spin tip="请稍后..." :spinning="editStatus['rdRatio'].spin">
          <div class="item_body">
            <a-form layout="inline">
              <div>
                <a-form-item label="年份">
                  <year-select style="width:165px;" @change="y=>year = y" :value="year" :allowClear="false" v-if="editStatus['rdRatio'].edit"/>
                  <div v-else class="item_text">
                    {{ year }}
                  </div>
                </a-form-item>
                <a-form-item label="比例">
                  <a-input-number
                    style="width:165px;"
                    v-if="editStatus['rdRatio'].edit"
                    v-model="settingData.rdRatio[String(year)]"
                    :precision="2"
                    :max="1"
                    :min="0"/>
                  <div v-else class="item_text">
                    {{ settingData.rdRatio[year]? settingData.rdRatio[year] : '0.75' }}
                  </div>
                </a-form-item>
              </div>
              <div>
                <a-form-item class="item_modify" v-if="$auth('company:setting:percentage:edit')">
                  <template v-if="editStatus['rdRatio'].edit">
                    <a-button class="item_btn" type="primary" @click="save('rdRatio')">保存</a-button>
                    <a-button class="item_btn" type="primary" @click="cancel('rdRatio')">取消</a-button>
                  </template>
                  <a-button class="item_btn" type="primary" v-if="!editStatus['rdRatio'].edit" @click="edit('rdRatio')">编辑</a-button>
                </a-form-item>
              </div>
            </a-form>
          </div>
        </a-spin>
      </template> -->
      <template v-if="$auth('company:setting:docNo:view')">
        <ys-divider title="文档编号规则"/>
        <a-spin tip="请稍后..." :spinning="editStatus['documentNo'].spin">
          <div class="item_body">
            <a-form layout="inline">
              <div>
                <a-form-item label="规则">
                  <a-input v-if="editStatus['documentNo'].edit" v-model="settingData.documentNo['documentNo']" style="width: 430px;"></a-input>
                  <div v-else> {{ settingData.documentNo['documentNo']? settingData.documentNo['documentNo'] : '-' }} </div>
                </a-form-item>
              </div>
              <div>
                <p>说明：项目年份{year} ； 项目编号{rdIndex} ； 生成文档序号{seq} ；</p>
              </div>
              <div style="margin-top: 10px;">
                <p>规则样例： {year}RD{rdIndex}-XM-{seq}</p>
                <p>规则结果： 2021RD21-XM-0001</p>
              </div>
              <div>
                <a-form-item class="item_modify" v-if="$auth('company:setting:docNo:edit')">
                  <template v-if="editStatus['documentNo'].edit">
                    <a-button class="item_btn" type="primary" @click="save('documentNo')">保存</a-button>
                    <a-button class="item_btn" type="primary" @click="cancel('documentNo')">取消</a-button>
                  </template>
                  <a-button class="item_btn" type="primary" v-if="!editStatus['documentNo'].edit" @click="edit('documentNo')">编辑</a-button>
                </a-form-item>
              </div>
            </a-form>
          </div>
        </a-spin>
      </template>
      <template v-if="$auth('company:setting:hourBit:view')">
        <ys-divider title="人员设备工时小数位"/>
        <a-spin tip="请稍后..." :spinning="editStatus['hourBit'].spin">
          <div class="item_body">
            <a-form layout="inline">
              <div>
                <a-form-item label="人员">
                  <a-input-number
                    style="width:165px;"
                    :max="2"
                    :min="0"
                    :precision="0"
                    v-model="settingData.hourBit.employee"
                    v-if="editStatus['hourBit'].edit"/>
                  <div v-else class="item_text">
                    {{ (settingData.hourBit.employee || settingData.hourBit.employee ===0) ? settingData.hourBit.employee : 1 }} 位小数
                  </div>
                </a-form-item>
                <a-form-item label="设备">
                  <a-input-number
                    style="width:165px;"
                    :max="2"
                    :min="0"
                    :precision="0"
                    v-model="settingData.hourBit.equipment"
                    v-if="editStatus['hourBit'].edit"/>
                  <div v-else class="item_text">
                    {{ (settingData.hourBit.equipment || settingData.hourBit.equipment ===0) ? settingData.hourBit.equipment : 1 }} 位小数
                  </div>
                </a-form-item>
              </div>
              <div>
                <a-form-item class="item_modify" v-if="$auth('company:setting:hourBit:edit')">
                  <template v-if="editStatus['hourBit'].edit">
                    <a-button class="item_btn" type="primary" @click="save('hourBit')">保存</a-button>
                    <a-button class="item_btn" type="primary" @click="cancel('hourBit')">取消</a-button>
                  </template>
                  <a-button class="item_btn" type="primary" v-if="!editStatus['hourBit'].edit" @click="edit('hourBit')">编辑</a-button>
                </a-form-item>
              </div>
            </a-form>
          </div>
        </a-spin>
      </template>
    </a-spin>
  </a-card>
</template>

<script>
import YsDivider from '@/components/YsDivider'
import YearSelect from '@/components/YearSelect'
export default {
  components: {
    YsDivider,
    YearSelect
  },
  data () {
    return {
      accountMonthArr: ['上月', '本月'],
      defaultRatio: 0.75,
      spinning: false,
      year: new Date().getFullYear(),
      editStatus: {
        accountPeriod: { edit: false, spin: false },
        miniProgram: { edit: false, spin: false },
        rdRatio: { edit: false, spin: false },
        documentNo: { edit: false, spin: false },
        hourBit: { edit: false, spin: false }
      },
      settingData: {
        accountPeriod: {},
        miniProgram: {},
        rdRatio: {},
        documentNo: {},
        hourBit: {}
      }
    }
  },
  created () {
    this.getSetting()
  },
  watch: {
    year (y) {
      this.setDefaultRatio()
    }
  },
  methods: {
    getSetting () {
      // res.data若返回数据，则默认实例化 settingData 的所有属性。
      this.$http.get('/companySetting/getSetting').then(res => {
        if (res.success && res.data) {
          this.settingData = res.data
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '获取数据失败')
        }
        this.setDefaultRatio()
      })
    },
    setDefaultRatio () {
      if (!this.settingData.rdRatio[String(this.year)]) {
        this.$set(this.settingData.rdRatio, String(this.year), this.defaultRatio)
      }
    },
    edit (key) {
      this.editStatus[key].edit = !this.editStatus[key].edit
      this.settingData[`${key}Copy`] = { ...this.settingData[key] }
      if (key === 'rdRatio') {
        this.setDefaultRatio()
      }
    },
    cancel (key) {
      this.editStatus[key].edit = !this.editStatus[key].edit
      this.settingData[key] = { ...this.settingData[`${key}Copy`] }
    },
    save (key) {
      this.editStatus[key].spin = true
      this.$http.post(`/companySetting/${key}Save`, this.settingData[key]).then(res => {
        if (res.success && res.data) {
          this.$message.success('保存成功')
          this.editStatus[key].edit = false
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
        }
        this.editStatus[key].spin = false
      })
    }
  }
}
</script>

<style>
  .item_body {
    min-height:150px;
    padding-left: 30px;
  }

  .item_modify{
    margin-top: 20px;
    margin-left: 120px;
  }
  .item_modify .item_btn{
  margin-right: 16px;
  }
  .item_text{
    width: 165px;
  }
</style>
