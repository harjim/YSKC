import { Button, Icon, Modal } from 'ant-design-vue'

export default {
  name: 'ZoomModal',
  props: {
    title: {
      type: String,
      default: ''
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    visible (val) {
      this.show = val
    }
  },
  data () {
    return {
      show: this.visible,
      narrow: false,
      display: 'block'
    }
  },
  created () {},
  methods: {
    changeZoom () {
      this.narrow = !this.narrow
    },
    getContainer () {
      return document.getElementById('rootZoomModal')
    }
  },
  render () {
    const { title } = this.$props
    return (
      <div id="rootZoomModal">
        <Modal
          v-drag
          {...{ props: this.$attrs }}
          forceRender
          mask={false}
          footer={null}
          zIndex={1100}
          keyboard={false}
          visible={this.show}
          maskClosable={false}
          getContainer={this.getContainer}
          bodyStyle={{ display: this.narrow ? 'none' : 'block' }}
          onCancel={() => {
            this.show = false
            this.$emit('update:visible', false)
            this.$listeners.cancel && this.$emit('cancel')
          }}
          onOk={() => {
            this.show = false
            this.$emit('update:visible', false)
            this.$listeners.ok && this.$emit('ok')
          }}
        >
          <span
            slot="title"
            style="display: flex;align-items: baseline;"
          >
            <Button
              size="small"
              shape="circle"
              style="margin-right: 10px"
              onClick={() => this.changeZoom()}
            >
              <Icon type={this.narrow ? 'down' : 'up'} />
            </Button>
            {title}
          </span>
          {this.$slots.default}
        </Modal>
      </div>
    )
  }
}
