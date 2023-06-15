import { createWebHistory, createRouter } from 'vue-router'
import { getToken } from '@/utils/auth'
/* Layout */
import Layout from '@/layout'
/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */
import Hotel_card from '../views/hotel/components/recommend/hotel_card'
import path from 'path'
// 公共路由
export const constantRoutes = [
  //默认首页路由
  {
    path: '',
    name: 'default',
    redirect: '/index/train',
  },
  //首页
  {
    path: '/index',
    name: 'index',
    component: Layout,
    hidden: true,
    children: [
      //火车模块
      {
        path: 'train',
        component: () => import('@/views/train/index'),
        name: 'train-index'
      },
      {
        path: 'train/trips',
        component: () => import('@/views/train/TripsList.vue'),
        name: 'trips-list'
      },

      //酒店模块
      {
        path: 'hotel',
        component: () => import('@/views/hotel/index.vue'),
        name: 'hotel-index',
        // redirect:'hotel/hotel_card',
        children:[
          {
            path: 'hotel_card',
            component:Hotel_card,
            name:'hotel_card',
            // redirect:'hotel/hotel_card'
          },
        ]
      },
      {
        path: 'hotel_detail/:hotelId/:hotelName/:userId',
        component:()=>import('@/views/hotel/hotel_detail.vue'),
        name: 'hotel_detail',
      },
      {
        path: 'hotel/place/:search',
        component: () => import('@/views/hotel/HotelPlace.vue'),
        name: 'hotel-place'
      },
      //
      {
        path: 'hotel_place/:cityName',
        component: () => import('@/views/hotel/hotel_place.vue'),
        name: 'hotel_place'
      },
      {
        path: 'book',
        component: () => import('@/views/hotel/pay/book.vue'),
        name: 'book',
      },
      {
        path: 'pay',
        component:()=>import('@/views/hotel/pay/pay.vue'),
        name:'pay',
      },
      {
        path:'alipay',
        component:()=>import('@/views/hotel/pay/alipay.vue'),
        name:'alipay',
      },
      {
        path: 'test',
        component: () => import('@/views/hotel/MyTest.vue'),
        name: 'test',
      },

      //旅游模块
      {
        path: 'attractions',
        component: () => import('@/views/attractions/index'),
        name: 'attractions-index'
      },
      {
        path: 'attractions/place',
        component: () => import('@/views/attractions/AttractionsPlace.vue'),
        name: 'attractions-place'
      }
    ]
  },
  //用户模块(子路由私信模块)
  {
    path: '/user/detail/:userId',
    name: 'user-detail',
    component: () => import('@/views/user/index.vue')
  },
  //私信
  {
    path: '/concern/message',
    name: 'concern-message',
    component: () => import('@/views/user/PrivateMessage.vue'),
    meta: {
      isAuth: true
    }
  },

  //旅游模块
  {
    path: '/attractions/detail/:attractionsId',
    name: 'attractions-detail',
    component: () => import('@/views/attractions/AttractionsDetail.vue')
  },
  {
    path: '/attractions/appraise/:appraiseId',
    name: 'appraise-detail',
    component: () => import('@/views/attractions/AttractionsAppraise.vue')
  },
  {
    path:'/atrrationsComment',
    component: ()=> import('@/views/attractions/AttractionsComment.vue')
  },
  {
    path:'/attractionsYouji/:essayId',
    component: () => import('@/views/attractions/AttractionsYouji.vue')
  },
  {
    path:'/attractionsYoujiWrite',
    component:()=>import('@/views/attractions/AttractionsYoujiWrite.vue')
  },

  //酒店模块
  {
    path: '/hotel/detail/:hotelId',
    name: 'hotel-detail',
    component: () => import('@/views/hotel/HotelDetail.vue')
  },

  //登录界面
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login'),
    hidden: true
  },
  //注册界面
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/register'),
    hidden: true
  }
]

// 基于用户权限加载的动态路由
export const dynamicRoutes = []

const router = createRouter({
  history: createWebHistory(),
  routes: [...constantRoutes, ...dynamicRoutes],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  },
});

// 路由守卫
//判断是否登录
router.beforeEach((to, from, next) => {
  if(to.meta.isAuth === true) {
    if(getToken() === undefined || getToken() === "") {
      next({ path: '/login' })
    }
  }
  next()
})
export default router;


