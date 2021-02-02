import Vue from 'vue'
import Router from 'vue-router'
import Refresh from '@/views/error/Refresh'
import store from '@/store'
import securityAPI from '@/api/security'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: () => import('@/views/page/Index')
      .then(page => page)
      .catch(() => Refresh),
      redirect: {name: 'drivingList'},
      children: [
        {
          name: 'drivingList',
          path: 'driving',
          meta: {
            title: '운행리스트',
          },
          component: () => import('@/views/page/DrivingList')
          .then(page => page)
          .catch(() => Refresh),
        },
        {
          name: 'drivingInfo',
          path: 'driving/:id',
          meta: {
            title: '운행상세',
          },
          component: () => import('@/views/page/DrivingInfo')
          .then(page => page)
          .catch(() => Refresh),
          props: function (route) {
            return {
              id: route.params.id
            }
          },
        },
        {
          name: 'vehicleList',
          path: 'vehicle',
          meta: {
            title: '차량리스트',
          },
          component: () => import('@/views/page/VehicleList')
          .then(page => page)
          .catch(() => Refresh),
        },
        {
          name: 'userList',
          path: 'user',
          meta: {
            title: '사용자리스트',
          },
          component: () => import('@/views/page/UserList')
          .then(page => page)
          .catch(() => Refresh),
        },
      ]
    },
    {
      path: '/auth',
      component: () => import('@/views/auth/Index')
      .then(page => page)
      .catch(() => Refresh),
      children: [
        {
          name: 'login',
          path: 'login',
          meta: {
            title: '로그인',
          },
          component: () => import('@/views/auth/Login')
          .then(page => page)
          .catch(() => Refresh),
          props: function (route) {
            return {
              token: route.query.token
            }
          },
        },
        {
          name: 'deny',
          path: 'deny',
          meta: {
            title: '접근차단',
          },
          component: () => import('@/views/auth/Deny')
          .then(page => page)
          .catch(() => Refresh),
        },
      ],
    },
    {
      path: '*',
      component: () => import('@/views/error/Index')
      .then(page => page)
      .catch(() => Refresh),
      children: [
        {
          name: '404error',
          path: '',
          meta: {
            title: '404',
          },
          component: () => import('@/views/error/Error')
          .then(page => page)
          .catch(() => Refresh),
        },
      ],
    },
  ],
})

router.beforeEach((to, from, next) => {
  if (from && from.name !== 'login') {
    store.commit('user/UPDATE_BEFORE_ROUTE_PATH', from.path)
  }
  if (to.name === '404error') {
    next()
    return
  }
  securityAPI.getSession()
  .then(response => {
    store.commit('user/UPDATE_LOGIN_SESSION', response)
    const isLoginPage = to.name === 'login'

    if (isLoginPage && response.authenticated) {
      // 정상이용자가 로그인페이지 접근시도시 메인페이지로 이동한다.
      next('/')
    } else if (!isLoginPage && !response.authenticated) {
      // 비정장이용자가 로그인페이지 이외의 페이지에 접근시도시 로그인페이지로 이동한다.
      next({
        name: 'login'
      })
    } else {
      if (response.role !== '시스템관리' && to.name === 'userList') {
        next({name: 'deny'})
      } else {
        next()
      }
    }
  })
  .catch(e => {
    console.log(e)
  })
})

router.afterEach((to) => {
  if (to.meta.title) {
    document.title = to.meta.title
  }
})
export default router
