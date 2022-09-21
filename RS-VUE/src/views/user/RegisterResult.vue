<template>
  <div style="margin-top:200px;color:#00F">
    <result :isSuccess="true" :content="false" :title="email" :description="test">
      <template slot="action">
        <!-- <a-button size="large" type="primary">查看邮箱</a-button> -->
        <a-button size="large" style="margin-left: 8px" @click="goHomeHandle">返回</a-button>
      </template>
    </result>
  </div>
</template>

<script>
import { Result } from '@/components'

export default {
  name: 'RegisterResult',
  components: {
    Result
  },
  data () {
    return {
      form: {}
    }
  },
  computed: {
    email () {
      // const v = this.form && this.form.email || 'xxx'     //description: '您的公司${v}，用户名为${v}。', // '激活邮件已发送到你的邮箱中，邮件有效期为24小时。请及时登录邮箱，点击邮件中的链接激活帐户。',
      const title = `注册成功，我们将在三个工作日内审核您的信息。`
      return title
    },
    test () {
      const userName = this.form && this.form.userName || 'xxx'
      const companyName = this.form && this.form.companyName || 'xxx'
      const description = `您的公司${companyName}，用户名为${userName}。`
      return description
    }
  },
  created () {
    this.form = this.$route.params
  },
  methods: {
    goHomeHandle () {
      this.$router.push({ name: 'login' })
    }
  }
}
</script>

<style scoped>
</style>
