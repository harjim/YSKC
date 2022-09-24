<script setup>
import * as dd from 'dingtalk-jsapi'
import { ref } from 'vue'
import { axios } from '../utils/http'

defineProps({
  msg: { type: String, default: '' }
})

function getDDCode() {
  if (dd.env.platform === 'notInDingTalk') {
    return {
      success: false,
      errorMessage: '当前功能只适合在钉钉手机端用'
    }
  }

  return dd.runtime.permission
    .requestAuthCode({
      corpId: 'ding543f60a6e3104cf835c2f4657eb6378f'
    })
    .then((res) => {
      alert(res.code)
      return { success: true, data: res }
    })
    .catch((err) => {
      alert(JSON.stringify(dd.env))
      return Promise.resolve({
        success: false,
        errorMessage: err.errorMessage
      })
    })
}
const loginByCode = async () => {
  const ddResult = await getDDCode()
  if (ddResult.success) {
    return axios.post('/index/loginByCode', ddResult.data).catch((err) => {
      return Promise.resolve({
        success: false,
        errorMessage: err.message
      })
    })
  }
  return ddResult
}
const count = ref(0)
async function testClick() {
  console.log(window.__corpId__)
  const result = await loginByCode()
  alert(JSON.stringify(result))
  // alert(dd.env.platform)
  // alert(window.__corpId__)
  // await dd.runtime.permission.requestAuthCode({
  //   corpId: 'ding543f60a6e3104cf835c2f4657eb6378f',
  //   onSuccess: (res) => {
  //     // 调用成功时回调
  //     alert(JSON.stringify(res))
  //   },
  //   onFail: (err) => {
  //     alert(JSON.stringify(err))
  //     // 调用失败时回调
  //     console.log(err)
  //   }
  // })
  console.log(3)
}
</script>

<template>
  <h1>{{ msg }}</h1>

  <div class="card">
    <button type="button" @click="testClick">count is {{ count }}</button>
    <p>
      Edit
      <code>components/HelloWorld.vue</code> to test HMR
    </p>
  </div>

  <p>
    Check out
    <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank">create-vue</a>, the official Vue + Vite starter
  </p>
  <p>
    Install
    <a href="https://github.com/johnsoncodehk/volar" target="_blank">Volar</a>
    in your IDE for a better DX
  </p>
  <p class="read-the-docs">Click on the Vite and Vue logos to learn more</p>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
