<template>
  <a-drawer
    :title="title"
    :width="width"
    :visible="visible"
    @close="$emit('close')"
    :bodyStyle="{ background: ' #f3f5fa', height: 'calc(100vh - 55px)', padding: 0, position: 'relative' }"
  >
    <a-spin :spinning="spinning">
      <a-row :gutter="16" class="container-box" :style="{ height: !showBtn ? '100%' : 'calc(100% - 72px)' }">
        <a-col :span="4" class="container-box__left">
          <a-card title="节点" size="small" :bodyStyle="{ padding: '16px' }">
            <slot name="node"></slot>
          </a-card>
        </a-col>
        <a-col :span="20" :class="`container-box__right ${$slots.audit && 'container-box__right--flex'}`">
          <a-card size="small" :bodyStyle="{ padding: '16px' }">
            <slot name="content"></slot>
          </a-card>
          <template v-if="$slots.audit">
            <a-card size="small" :bodyStyle="{ padding: '16px' }">
              <slot name="audit"></slot>
            </a-card>
          </template>
        </a-col>
      </a-row>

      <div v-if="showBtn" class="btns">
        <a-button @click="$emit('add')">暂存 </a-button>
        <a-popconfirm title="是否确认提交?" :autoAdjustOverflow="false" placement="topRight" @confirm="$emit('submit')">
          <a-button type="primary">提交</a-button>
        </a-popconfirm>
      </div>
    </a-spin>
  </a-drawer>
</template>

<script>
export default {
  name: 'TwoColumnsDrawer',
  props: {
    title: {
      type: String,
      default: ''
    },
    width: {
      type: Number,
      default: 1184
    },
    visible: {
      type: Boolean,
      default: false
    },
    showBtn: {
      // 是否显示按钮组
      type: Boolean,
      default: false
    },
    spinning: {
      type: Boolean,
      default: false
    },
    edit: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style lang="less">
& /deep/ {
  .ant-spin-nested-loading {
    height: 100%;

    .ant-spin-container {
      height: 100%;
      overflow: hidden;
    }
  }
}

.container-box {
  padding: 16px;
  overflow: hidden;

  &__left {
    height: 100%;
    .ant-card {
      height: 100%;
    }
  }

  &__right {
    height: 100%;
    overflow-y: auto;

    &--flex {
      display: flex;
      flex-direction: column;
      row-gap: 16px;
      & > div:first-child {
        flex: 1;
        overflow: auto;
      }

      & > div:last-child {
        flex: 0 0 260px;
        overflow: auto;
      }
    }
  }
}

.btns {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 56px;
  width: 100%;
  background-color: #fff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  column-gap: 8px;
  padding: 0 24px;
  border: 1px solid #e8e8e8;
}
</style>
