<template>
  <div>
    <el-tabs :stretch="true">
      <!-- 未审核列表 -->
      <el-tab-pane label="未审核">
        <el-button @click="clearFilter">清除所有过滤器</el-button>
        <el-table
          row-key="create_date"
          ref="filterTable"
          :data="unverify"
          style="width: 100%"
          v-loading="loading"
          element-loading-text="拼命加载中"
        >
          <el-table-column
            prop="create_date"
            label="日期"
            sortable
            column-key="create_date"
          >
            <template #default="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.create_date }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="username" label="名称"> </el-table-column>
          <el-table-column
            prop="role"
            label="类型"
            :filters="[
              { text: '银行', value: '银行' },
              { text: '企业', value: '企业' },
            ]"
            :filter-method="filterHandler"
          >
          </el-table-column>
          <el-table-column label="操作">
            <template #default="scoped">
              <el-button
                type="primary"
                @click="showDetail(scoped.row, false, unverify)"
                >详情</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 已审核列表 -->
      <el-tab-pane label="已审核">
        <el-button @click="clearFilter">清除所有过滤器</el-button>
        <el-table
          row-key="create_date"
          ref="filterTable"
          :data="verified"
          style="width: 100%"
          v-loading="loading"
          element-loading-text="拼命加载中"
        >
          <el-table-column
            prop="create_date"
            label="日期"
            sortable
            column-key="create_date"
          >
            <template #default="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.create_date }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="username" label="名称"> </el-table-column>
          <el-table-column
            prop="role"
            label="类型"
            :filters="[
              { text: '银行', value: '银行' },
              { text: '企业', value: '企业' },
            ]"
            :filter-method="filterHandler"
          >
          </el-table-column>
          <el-table-column
            prop="verify"
            label="状态"
            :filters="[
              { text: '已通过', value: '已通过' },
              { text: '未通过', value: '未通过' },
            ]"
            :filter-method="filterHandler"
          >
          </el-table-column>
          <el-table-column label="操作">
            <template #default="scoped">
              <el-button
                type="primary"
                @click="showDetail(scoped.row, true, verified)"
                >详情</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <!-- 详情弹窗 -->
    <el-dialog :visible.sync="detailVisible" width="700px">
      <el-form model="verifyingData" disabled="verifyAble">
        <el-form-item label="申请时间">
          <el-input v-model="verifyingData.date"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="verifyingData.name"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="verifyingData.type"></el-input>
        </el-form-item>
        <el-form-item label="证明材料">
          <el-input v-model="verifyingData.proof"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="success" :disabled="verifyAble" @click="handlePass()"
        >通过</el-button
      >
      <el-button type="danger" :disabled="verifyAble" @click="handleReject()"
        >不通过</el-button
      >
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      detailVisible: false,
      loading: true,

      unverify: [],
      verified: [],

      verifyingData: {
        date: "",
        name: "",
        type: "",
        proof: "",
        state: "",
        id: "",
      },
      verifyAble: "",
    };
  },

  created() {
    // this.getIfVerify()
    this.getTableData();
  },

  methods: {
    async getTableData() {
      const { data: res } = await this.$http.get("/verifyInfoAll"); //访问后台
      if (res.code == 200) {
        // console.log(res)
        for (let n in res.data) {
          let data = {};
          data.create_date = this.timeFormat(res.data[n].createTime);
          data.username = res.data[n].username;
          data.verify = res.data[n].verify;
          data.info = res.data[n].info;
          data.id = res.data[n].id;
          if (res.data[n].role == "2") {
            data.role = "银行";
          } else if (res.data[n].role == "1") {
            data.role = "权威机构";
          } else {
            data.role = "企业";
          }

          if (data.verify == "认证中") {
            this.unverify.push(data);
          } else if (data.verify == "未认证") {
            continue;
          } else {
            this.verified.push(data);
          }
        }
        this.loading = false;
      }
      console.log(this.unverify);
    },
    clearFilter() {
      this.$refs.filterTable.clearFilter();
    },

    filterHandler(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },

    showDetail(row, verifyAble, currentList) {
      this.verifyingData.date = row.create_date;
      this.verifyingData.name = row.username;
      this.verifyingData.type = row.role;
      this.verifyingData.proof =
        currentList[
          this.getItemIndex(currentList, this.verifyingData.name)
        ].info;
      this.verifyingData.id =
        currentList[this.getItemIndex(currentList, this.verifyingData.name)].id;
      this.detailVisible = true;
      this.verifyAble = verifyAble;
      console.log(this.verifyingData);
    },

    async handlePass() {
      //将审核结果发送给后端
      const { data: res } = await this.$http.post("/passVerify", {
        id: this.verifyingData.id,
      }); //访问后台
      if (res.code == 200) {
        console.log(res);
        this.$message.success(res.msg);
        //更改审核状态；获取特定机构id和密码；
        const { data: res_getInfo } = await this.$http.get(
          "/verifyInfo/" + this.verifyingData.id
        );
        if (res_getInfo.code == 200) {
          console.log(res_getInfo);
          let pwd = res_getInfo.data.password;
          let role = res_getInfo.data.role;
          //在链上注册
          const { data: res_register } = await this.$http.post(
            "invokeContract/registerUser",
            { id: this.verifyingData.id, password: pwd }
          ); //访问后台
          if (res_register.code == 200) {
            console.log(res_register);
            this.$message.success(res_register.msg);
            //在链上添加公司或银行
            if (role == 2) {
              const { data: res_addBank } = await this.$http.post(
                "invokeContract/addBank",
                {
                  bankId: this.verifyingData.id,
                  bankName: this.verifyingData.name,
                }
              ); //访问后台
              if (res_addBank.code == 200) {
                console.log(res_addBank);
                this.$message.success(res_addBank.msg);
                //刷新整个页面?
                this.detailVisible = false;
                this.verified = [];
                this.unverify = [];
                this.getTableData();
              } else {
                this.$message.error(res.msg);
              }
            } else {
              let isCore = role == 3 ? "no" : "yes";
              const { data: res_addComp } = await this.$http.post(
                "invokeContract/addCompany",
                {
                  companyId: this.verifyingData.id,
                  companyName: this.verifyingData.name,
                  isCore: isCore,
                }
              ); //访问后台
              if (res_addComp.code == 200) {
                console.log(res_addComp);
                this.$message.success(res_addComp.msg);
                //刷新整个页面?
                this.detailVisible = false;
                this.verified = [];
                this.unverify = [];
                this.getTableData();
              } else {
                this.$message.error(res.msg);
              }
            }
          } else {
            this.$message.error(res.msg);
          }
        } else {
          this.$message.error(res.msg);
        }
      } else {
        this.$message.error(res.msg);
      }
    },

    async handleReject() {
      //将审核结果发送给后端
      const { data: res } = await this.$http.post("/unPassVerify", {
        id: this.verifyingData.id,
      }); //访问后台
      if (res.code == 200) {
        //更改审核状态
        //刷新整个页面?
        this.detailVisible = false;
        this.verified = [];
        this.unverify = [];
        this.getTableData();
      }
    },

    timeFormat(timeStamp) {
      let date = new Date(timeStamp);
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let dd = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      return year + "-" + month + "-" + dd;
    },

    getItemIndex(currentList, obj) {
      var i = currentList.length;
      while (i--) {
        if (currentList[i].username === obj) {
          return i;
        }
      }
      return false;
    },
  },
};
</script>
<style>
</style>