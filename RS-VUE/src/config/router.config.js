// eslint-disable-next-line
import { UserLayout, BasicLayout, RouteView, BlankLayout, PageView } from '@/layouts'

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Register')
      },
      {
        path: 'register-result',
        name: 'registerResult',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/RegisterResult')
      }
    ]
  },

  {
    path: '/test',
    component: BlankLayout,
    redirect: '/test/home',
    children: [
      {
        path: 'home',
        name: 'TestHome',
        component: () => import('@/views/Home')
      }
    ]
  },

  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  },
  {
    path: '/login',
    component: BlankLayout,
    redirect: '/test/home',
    children: [
      {
        path: 'msLogin',
        name: 'msLogin',
        component: () => import(/* webpackChunkName: "msLogin" */ '@/views/user/MsLogin')
      }
    ]
  },
  {
    path: '/ding',
    component: BlankLayout,
    redirect: '/test/home',
    children: [
      {
        path: 'dingLogin',
        name: 'dingLogin',
        component: () => import(/* webpackChunkName: "fail" */ '@/views/user/DingLogin')
      }
    ]
  }
]
