import T from 'vxe-table/packages/grid'

const methods = {}
Object.keys(T.methods).forEach(name => {
  methods[name] = function (...args) {
    return this.$refs.xGrid && this.$refs.xGrid[name](...args)
  }
})
export default {
  data () {
    return {
      defaultProps: {
        resizable: true,
        highlightHoverRow: true,
        highlightCurrentRow: true,
        pagerConfig: {
          total: 0,
          currentPage: 1,
          pageSize: 10,
          align: 'right',
          pageSizes: [10, 20, 50, 100, 200, 500],
          layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
          perfect: true
        },
        customConfig: {
          storage: {
            visible: true,
            resizable: true
          }
        },
        proxyConfig: {
          seq: true, // 启用动态序号代理
          sort: true,
          autoLoad: this.autoLoad,
          props: {
            result: 'data.data',
            total: 'data.totalCount'
          },
          ajax: {
            query: ({ page, sort }) => {
              this.$emit('loading', true)
              const queryParams = {
                pageNo: page.currentPage,
                pageSize: page.pageSize,
                ...this.params
              }
              if (sort.field) {
                queryParams.field = sort.field
                queryParams.order = sort.order
              }
              return this.$http.get(this.queryUrl, { params: queryParams }).then(res => {
                this.$emit('completed', res)
                return res
              }).finally(() => {
                this.$emit('loading', false)
              })
            }
          }
        }
      }
    }
  },
  props: {
    ...T.props,
    queryUrl: {
      type: String,
      default: ''
    },
    params: {
      type: Object,
      default: () => { }
    },
    autoLoad: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    ...methods,
    /**
     * 刷新列表
     * @param {*} reload true表示刷回第一页,false表示刷新当前页
     */
    refresh (reload) {
      if (reload) {
        this.$refs.xGrid.commitProxy('reload')
      } else {
        this.$refs.xGrid.commitProxy('query')
      }
    }
  },
  render () {
    const props = {}
    Object.keys(T.props).forEach(k => {
      if (k === 'proxyConfig' && this.dataUrl) {
        props[k] = this.defaultProps.proxyConfig
        return props[k]
      }
      if (this[k]) {
        props[k] = this[k]
      } else if (this.defaultProps[k]) {
        props[k] = this.defaultProps[k]
      }
      return props[k]
    })
    if (typeof props['id'] === 'undefined') {
      props['id'] = this.queryUrl
    }
    return (
      <vxe-grid
        ref="xGrid" {...{
          props,
          scopedSlots: { ...this.$scopedSlots },
          on: this.$listeners
        }}>
        {Object.keys(this.$slots).map(name => (
          <template slot={name}>{this.$slots[name]}</template>))}
      </vxe-grid>
    )
  }
}
