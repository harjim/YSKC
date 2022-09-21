<!--
 * @Author: your name
 * @Date: 2021-11-10 14:35:30
 * @LastEditors: lzh
 * @LastEditTime: 2022-02-21 14:33:29
 * @Description: In User Settings Edit
 * @FilePath: \MS-VUE\src\components\TableSearchbar\TableSearchbar.vue
-->
<script>
export default {
  props: {
    colNum: {
      type: Number,
      default: 3
    },
    labelCol: {
      type: Object,
      default: () => ({ span: 8 })
    },
    wrapperCol: {
      type: Object,
      default: () => ({ span: 16 })
    },
    foldable: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      fold: true,
      newRow: true
    }
  },
  methods: {
    toggleFold () {
      this.fold = !this.fold
      this.$emit('fold', this.fold)
    },
    onSearch () {
      this.$emit('search')
    }
  },
  render () {
    const { $slots, colNum, foldable, labelCol, wrapperCol } = this
    const mdCol = parseInt(24 / colNum)
    const formItemLength = $slots.default.length
    const newRow = formItemLength % colNum === 0
    const foldBtnTxt = this.fold ? <span ><a-icon type="down"/>展开</span> : <span><a-icon type="up" />收起</span>
    const renderItem = $slots.default.map((item, index) => {
      return (
        index >= colNum - 1 && foldable
          ? <a-col md={mdCol} sm={24} style={{ display: this.fold ? 'none' : 'block' }}>
            {item}
          </a-col>
          : <a-col md={mdCol} sm={24} style='display: block'>
            {item}
          </a-col>
      )
    })
    return (
      <div>
        <a-form layout="horizontal" class='form-container' labelCol={labelCol} wrapperCol={wrapperCol}>
          <a-row>
            {renderItem}
          </a-row>
          <div class={['actions-wrapper', { 'inlineRow': this.foldable && this.fold ? true : !newRow }]}>
            <a-button type="primary" onclick={this.onSearch}>查询</a-button>
            { foldable ? <a onclick={this.toggleFold} style="margin-left: 8px">{foldBtnTxt}</a> : null}
          </div>
        </a-form>
      </div>
    )
  }
}
</script>

<style lang="less" scoped>
.form-container{
  position: relative;
}
.actions-wrapper{
  text-align: right;
  &.inlineRow{
    position: absolute;
    bottom: 16px;
    right: 0;
  }
}
/deep/ .ant-form-item{
  margin-bottom: 12px;
}

@media screen and (max-width: 768px) {
  .actions-wrapper{
    &.inlineRow{
      position: relative;
      bottom: 0;
    }
  }
}
</style>
