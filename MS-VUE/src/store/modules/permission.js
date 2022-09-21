import { constantRouterMap } from '@/config/router.config'
import { BasicLayout, PageView, BlankLayout } from '@/layouts'

function menus2Routers (menus) {
  const routers = []
  const firstRouter = {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: '' },
    redirect: '/dashboard/workplace',
    children: [
      {
        path: '/dashboard/workplace',
        name: 'Workplace',
        component: () => import(/* webpackChunkName: "Workplace" */'@/views/dashboard/Workplace'),
        meta: { title: '首页', icon: 'desktop', keepAlive: true }
      }
    ]
  }

  const menuMap = {}
  for (let index = 0; index < menus.length; index++) {
    const menu = menus[index]
    menuMap[menu.id] = {
      path: menu.url,
      name: menu.url,
      meta: { title: menu.name, icon: menu.icon }
    }
    if (menuMap[menu.parentId]) {
      if (menu.type === 0) {
        menuMap[menu.id]['component'] = BlankLayout
      } else {
        menuMap[menu.id]['component'] = () => import(/* webpackChunkName: "dyys" */'@/views' + menu.url)
      }
      // menuMap[menu.id]['component'] = () => import(/* webpackChunkName: "dyys" */'@/views' + menu.url)
      if (typeof menuMap[menu.parentId].redirect === 'undefined') {
        menuMap[menu.parentId].redirect = menu.url
        menuMap[menu.parentId].children = []
      }
      menuMap[menu.parentId].children.push(menuMap[menu.id])
    } else {
      menuMap[menu.id]['component'] = PageView
      firstRouter.children.push(menuMap[menu.id])

      if (firstRouter.redirect === 'undefined') {
        firstRouter.redirect = menu.url
      }
    }
  }

  routers.push(firstRouter)
  routers.push({
    path: '*', redirect: '/dashboard/workplace', hidden: true
  })
  return routers
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes ({ commit }, data) {
      return new Promise(resolve => {
        const { menus } = data
        const accessedRouters = menus2Routers(menus)
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    }
  }
}

export default permission
