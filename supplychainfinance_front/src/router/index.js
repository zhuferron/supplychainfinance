import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from "@/components/Login";
import Register from "@/components/Register";

import Home1 from "@/components/Home1";
import Home2 from "@/components/Home2";
import Home3 from "@/components/Home3";
import Home4 from "@/components/Home4";

import Information from "@/components/Information";
import Vertify from "@/components/Vertify";
import CompAndBankList from "@/components/CompAndBankList";
import ReceiptList from "@/components/ReceiptList"

import List2 from "@/components/List2";
import Information2 from "@/components/Information2";
import RealNameRegister2 from "@/components/RealNameRegister2";
import QueryBalance2 from "../components/QueryBalance2";


import List3 from "@/components/List3";
import Information3 from "@/components/Information3";
import RealNameRegister3 from "@/components/RealNameRegister3";
import QueryBalance3 from "../components/QueryBalance3";
import ReceiptUse3 from "../components/ReceiptUse3";

import List4 from "@/components/List4";
import Information4 from "@/components/Information4";
import RealNameRegister4 from "@/components/RealNameRegister4";
import ReceiptUse4 from "../components/ReceiptUse4";
import QueryBalance4 from "../components/QueryBalance4";

Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    redirect: "/login"
  }, {
    path: "/login",
    component: Login
  }, {
    path: "/register",
    component: Register
  },

  {
    path: '/home1',
    component: Home1,
    redirect: "/vertify",
    children: [
      { path: "/vertify", component: Vertify },
      { path: '/compAndBank', component: CompAndBankList },
      { path: '/receipt', component: ReceiptList },
      { path: "/info", component: Information },
    ]
  },
  {
    path: "/home2",
    component: Home2,
    redirect: "/QueryBalance2",
    children: [
      { path: "/list2", component: List2 },
      { path: "/info2", component: Information2 },
      { path: "/RealNameRegister2", component: RealNameRegister2 },
      { path: "/QueryBalance2", component: QueryBalance2 },
    ]
  }, {
    path: "/home3",
    component: Home3,
    redirect: "/list3",
    children: [
      { path: "/list3", component: List3 },
      { path: "/info3", component: Information3 },
      { path: "/RealNameRegister3", component: RealNameRegister3 },
      { path: "/ReceiptUse3", component: ReceiptUse3 },
      { path: "/QueryBalance3", component: QueryBalance3 },
    ]
  }, {
    path: "/home4",
    component: Home4,
    redirect: "/list4",
    children: [
      { path: "/list4", component: List4 },
      { path: "/info4", component: Information4 },
      { path: "/ReceiptUse4", component: ReceiptUse4 },
      { path: "/RealNameRegister4", component: RealNameRegister4 },
      { path: "/QueryBalance4", component: QueryBalance4 },
    ]
  }
]

const router = new VueRouter({
  routes
})

//重复点击导航控制台出错
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
  return VueRouterPush.call(this, to).catch(err => err)
}


// 路由守卫
router.beforeEach((to, from, next) => {
  //登录页和注册页随时都可以访问
  if (to.path == '/login') return next();
  if (to.path == '/register') return next();
  //其他请求必须登录后才可以访问
  const userFlag = window.sessionStorage.getItem("id");  //取出当前用户
  if (!userFlag) return next('/login');  //无值，返回登录页

  next();  //符合要求，放行
})



export default router
