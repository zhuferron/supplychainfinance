<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick" :stretch="true">
      <el-table
          :data="
        tableData.filter(
          (data) =>
            !search ||
            data.receiptId.toLowerCase().includes(search.toLowerCase())
        )
      "
          style="width: 100%"
          :stripe="true"
          v-loading="loading"
          element-loading-text="拼命加载中"
      >
        <el-table-column label="票据ID" prop="receiptId"> </el-table-column>
        <el-table-column label="来源ID" prop="companyIdFrom"> </el-table-column>
        <el-table-column label="票据数额" prop="amount"> </el-table-column>
        <el-table-column label="到期日期" prop="maturityDate"> </el-table-column>
        <el-table-column align="center">
          <template slot="header" >
            <el-input v-model="search" size="mini" placeholder="输入票据ID搜索" />
          </template>
          <template slot-scope="scope">
            <el-button size="small" type="primary" @click="confirm(scope.row)"
            >承兑票据</el-button
            >
          </template>
        </el-table-column>
      </el-table>
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
      tableData: [],
      search: "",
      loading: true,
      dialogVisible: false
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
    async getData() {
      this.id = window.sessionStorage.getItem("id"); //自动获取id
      const { data: res } = await this.$http.get(
        "getBankReceiptsToById/" + window.sessionStorage.getItem("id")
      );
      console.log(res); //得到结果
      if (res.code == 200) {
        console.log(res.data); //得到结果
        for (let n in res.data) {
          this.tableData[n] = res.data[n];
          this.tableData[n].maturityDate = this.timeFormat(
            res.data[n].maturityDate
          );
        }
        this.loading = false;
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
    if (window.sessionStorage.getItem("verify") == "2") {
      this.$router.push("/RealNameRegister2");
    }
    this.getData();
  },
};
</script>