<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick" :stretch="true">
      <el-tab-pane label="发起" name="first">
        <el-table
          :data="
            tableData1.filter(
              (data) =>
                !search ||
                data.receiptId.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
          :stripe="true"
          :row-style="{ height: '57px' }"
          v-loading="loading"
          element-loading-text="拼命加载中"
        >
          <el-table-column label="票据ID" prop="receiptId"> </el-table-column>
          <el-table-column label="票据流向" prop="IdTo"> </el-table-column>
          <el-table-column label="票据数额" prop="amount"> </el-table-column>
          <el-table-column label="到期日期" prop="maturityDate">
          </el-table-column>
          <el-table-column align="center">
            <template slot="header" >
              <el-input
                v-model="search"
                size="mini"
                placeholder="输入票据ID搜索"
              />
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="接收" name="second">
        <el-table
          :data="
            tableData2.filter(
              (data) =>
                !search ||
                data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
          :stripe="true"
          :row-style="{ height: '57px' }"
          v-loading="loading"
          element-loading-text="拼命加载中"
        >
          <el-table-column label="票据ID" prop="receiptId"> </el-table-column>
          <el-table-column label="票据来源" prop="companyIdFrom">
          </el-table-column>
          <el-table-column label="票据数额" prop="amount"> </el-table-column>
          <el-table-column label="到期日期" prop="maturityDate">
          </el-table-column>
          <el-table-column align="center">
            <template slot="header" >
              <el-input
                v-model="search"
                size="mini"
                placeholder="输入票据ID搜索"
              />
            </template>
            <template slot-scope="scope">
              <el-button size="small" type="primary" @click="confirm(scope.row)"
                >承兑票据</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog :visible.sync="dialogVisible" width="500px" style="margin-top: 10%">
      <el-col align="center" style="font-size: 16px">是否已收到金额，并且确定承兑该票据？</el-col>
      <el-row style="margin-top:50px">
        <el-button @click="handleDelete()" type="primary" >确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeName: "first",
      tableData1: [],
      tableData2: [],
      search: "",
      loading: true,
      // loading: false,
      dialogVisible: false,
      rid: "",
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    confirm(row) {
      this.rid = row.receiptId;
      this.dialogVisible = true;
    },
    cancel(){
      this.dialogVisible = false;
    },
    async handleDelete() {
      // console.log(row.receiptId);
      const { data: res } = await this.$http.post("/acceptReceipt/", {
        id: window.sessionStorage.getItem("id"),
        rid: this.rid,
      });
      if (res.code == 200) {
        this.$message.success(res.msg);
        this.$router.go(0);
      } else {
        this.$message.error(res.msg);
      }
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    async getLauchData() {
      this.id = window.sessionStorage.getItem("id"); //自动获取id

      // this.$message.success(this.userId); //弹出提示框
      const { data: res } = await this.$http.get(
        "getCompanyReceiptsFromById/" + window.sessionStorage.getItem("id")
      ); //访问后台
      console.log(res.data); //得到结果
      this.loading = false;
      for (let n in res.data) {
        this.tableData1.push(res.data[n]);
        console.log(res.data[n].bankIdTo == null);
        this.tableData1[n].IdTo =
          res.data[n].bankIdTo == null
            ? res.data[n].companyIdTo
            : res.data[n].bankIdTo;
        this.tableData1[n].maturityDate = this.timeFormat(
          res.data[n].maturityDate
        );
      }
    },
    async getReceiveData() {
      this.id = window.sessionStorage.getItem("id"); //自动获取id

      // this.$message.success(this.userId); //弹出提示框
      const { data: res } = await this.$http.get(
        "getCompanyReceiptsToById/" + window.sessionStorage.getItem("id")
      ); //访问后台
      // console.log(res.data); //得到结果
      for (let n in res.data) {
        // console.log(res.data[n].companyIdTo);
        this.tableData2[n] = res.data[n];
        this.tableData2[n].maturityDate = this.timeFormat(
          res.data[n].maturityDate
        );
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
      let hh = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let mm =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let ss =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      return year + "." + month + "." + dd + " " + hh + ":" + mm + ":" + ss;
    },
  },
  created() {
    if (window.sessionStorage.getItem("verify") == "3") {
      this.$router.push("/RealNameRegister3");
    }
    this.getLauchData();
    this.getReceiveData();
  },
};
</script>