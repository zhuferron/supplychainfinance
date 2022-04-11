<template>
  <el-card
    class="box-card"
    v-loading="loading"
    element-loading-text="拼命加载中..."
  >
    <div slot="header" class="clearfix">
      <span>公司信息</span>
    </div>
    <div class="text item">
      <div class="item_tag">
        <span>余额</span>
      </div>
      <div class="item_desc">
        {{ info.balance }}
      </div>
    </div>
    <div class="text item">
      <div v-model="info.companyId" class="item_tag">
        <span>公司ID</span>
      </div>
      <div v-model="info.companyId" class="item_desc">
        {{ info.companyId }}
      </div>
    </div>
    <div class="text item">
      <div v-model="info.companyName" class="item_tag">
        <span>公司名称</span>
      </div>
      <div v-model="info.companyName" class="item_desc">
        <span>{{ info.companyName }}</span>
      </div>
    </div>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      info: {
        // balance: "5264852",
        // companyId: "1c0cfc7054e948d3ad628321469419e7",
        // companyName: "Alibaba Group",
        // isCore: "Yes",
        balance: "",
        companyId: "",
        companyName: "",
        isCore: "",
      },
      loading: true,
      // loading: false,
    };
  },
  methods: {
    async getData() {
      this.id = window.sessionStorage.getItem("id"); //自动获取id

      const { data: res } = await this.$http.get(
        "getCompanyBalance/" + this.id
      ); //访问后台
      if (res.code == 200) {
        console.log(res); //得到结果
        this.info.balance = res.data;
        this.info.companyId = window.sessionStorage.getItem("id");
        this.info.companyName = window.sessionStorage.getItem("username");
      } else {
        this.info.balance = "加载失败";
        this.info.companyId = "加载失败";
        this.info.companyName = "加载失败";
      }
      this.loading = false;
    },
  },
  created() {
    if (window.sessionStorage.getItem("verify") == "4") {
      this.$router.push("/RealNameRegister4");
    }
    this.getData();
  },
};
</script>


<style lang='less'>
.item {
  margin-bottom: 20px;
}
.item_tag {
  float: left;
  text-align: left;
}
.item_desr {
  margin-left: 40%;
  min-height: 30px;
  text-align: left;
}
.text {
  width: 100%;
  font-size: 16px;
  font-family: "Microsoft YaHei";
  color: #909399;
  // text-align: left;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.box-card {
  width: 480px;
  margin-left: 33%;
}
</style>