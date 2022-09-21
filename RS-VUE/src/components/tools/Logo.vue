<template>
  <div class="logo">
    <img height="56" :src="logoPath" :alt="title" />
  </div>
</template>

<script>
export default {
  name: 'Logo',
  props: {
    logoPath: {
      type: String,
      default: '/logo.gif',
      required: false
    },
    title: {
      type: String,
      default: '创新标准化管理系统',
      required: false
    },
    showTitle: {
      type: Boolean,
      default: true,
      required: false
    }
  }
}
</script>
