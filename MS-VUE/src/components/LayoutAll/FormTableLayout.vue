<!-- 表格不超出可视范围 -->
<template>
  <a-card>
    <div class="form">
      <!-- 表上方查询部分 -->
      <slot name="form"></slot>
    </div>
    <div class="table">
      <!-- 表部分 -->
      <slot name="table"></slot>
    </div>
    <template>
      <!-- 其他组件 例如model、drawer -->
      <slot name="other"></slot>
    </template>
  </a-card>
</template>

<style lang="less" scoped>

  /deep/ .ant-card-body {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 189px);
    min-height: 340px;
  }
  .form {
    height: auto;
    flex-shrink: 0;
  }
  .table {
    flex: 1;
    overflow: hidden;
  }
</style>
