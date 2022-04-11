<template>
  <!--整体容器  -->
  <el-container class="home-container">
    <!--头部-->
    <el-header>
      <div>
        <img src="../assets/images/logo.png" />
      </div>
      <span style="margin-left: 5%">供应链金融</span>
      <el-menu
        default-active="1"
        class="el-menu-demo"
        mode="horizontal"
        background-color="#373d41"
        text-color="#fff"
        active-text-color="#ffd04b"
        style="margin-left: 35%"
      >
        <el-menu-item index="1" @click="goList3" style="width: 200px">
          <span slot="title">票据列表</span>
        </el-menu-item>
        <el-menu-item index="2" @click="goReceiptUse3" style="width: 200px">
          <span slot="title">票据使用</span>
        </el-menu-item>
        <el-menu-item index="3" @click="goQueryBalance3" style="width: 200px">
          <span slot="title">查询余额</span>
        </el-menu-item>
      </el-menu>
      <el-dropdown>
        <i
          class="el-icon-setting"
          style="margin-right: 15px; font-size: 30px"
        ></i>
        <el-dropdown-menu slot="dropdown">
          <center class="font_bk">{{ identity }}</center>
          <el-dropdown-item>
            <div type="info" @click="goInfo3" class="iconfont icon-denglu">
              个人信息
            </div>
          </el-dropdown-item>
          <el-dropdown-item>
            <div
              type="info"
              @click="goRealNameR3"
              class="iconfont icon-shiming"
            >
              申请认证
            </div>
          </el-dropdown-item>
          <el-dropdown-item>
            <div type="info" @click="logout" class="iconfont icon-tuichu">
              退出登录
            </div>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <!--身体容器 -->
    <el-container>
      <!--主体容器-->
      <el-container>
        <!--主体-->
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Home3",
  data() {
    return {
      isCollapse: true,
      identity: "非核心企业",
    };
  },

  methods: {
    //退出登录
    logout() {
      window.sessionStorage.clear(); //清除session
      this.$router.push("/login"); //回到首页
    },
    goWelcome() {
      this.$router.push("/welcome3");
    },
    goInfo3() {
      this.$router.push("/info3");
    },
    goRealNameR3() {
      this.$router.push("/RealNameRegister3");
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    goList3() {
      this.$router.push("/list3");
    },
    goBlockchainAccount3() {
      this.$router.push("/BlockchainAccount3");
    },
    goAssetTf() {
      this.$router.push("/AssetTf");
    },
    goReceiptUse3() {
      this.$router.push("/ReceiptUse3");
    },
    goQueryBalance3() {
      this.$router.push("/QueryBalance3");
    },
    //控制伸缩
    toggleCollapase() {
      this.isCollapse = !this.isCollapse;
    },
  },
};
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between; //左右贴边
  padding-left: 0; //左边界
  align-items: center;
  color: #fff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.aside-container {
  width: 200px;
}

.el-aside {
  background-color: #545c64;
  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: #eaedf1;
}
img {
  width: 55px;
  height: 55px;
}
//  |||按钮样式
.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer; //显示小手
}

.font_bk {
  background: rgb(78, 78, 78); /*设置背景色*/
  font-size: 20px;
  color: #fff;
}
</style>