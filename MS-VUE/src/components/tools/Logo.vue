<template>
  <div class="logo">
    <router-link :to="{name:'index'}">
      <!-- <LogoSvg alt="logo" /> -->
      <img alt="logo" src="@/assets/logo.png">
      <h1 v-if="showTitle">{{ title }}</h1>
    </router-link>
  </div>
</template>

<script>
// import LogoSvg from '@/assets/logo.png'

export default {
  name: 'Logo',
  // components: {
  //   LogoSvg
  // },
  props: {
    title: {
      type: String,
      default: '优赛科创',
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
