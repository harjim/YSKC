import { createRouter, createWebHashHistory } from 'vue-router'
import Customer from '../views/Customer/Index.vue'
import Home from '../views/Home/Index.vue'
import { useUserStore } from '../store/user'
import { Toast } from 'vant'
// createRouter 创建路由实例
const router = createRouter({
  history: createWebHashHistory(), // hash模式：createWebHashHistory，history模式：createWebHistory
  routes: [
    {
      path: '/Customer',
      component: Customer
    },
    {
      path: '/',
      name: 'home',
      component: Home
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  if (typeof userStore.info === 'undefined') {
    const result = await userStore.getInfo()
    console.log(result)
    if (result.success) {
      next()
    } else {
      Toast({ duration: 0, message: `${result.errorMessage}` })
      next(false)
    }
  } else {
    next()
  }
})

export default router
