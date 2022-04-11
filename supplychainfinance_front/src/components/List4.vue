<template>
  <div>
  <el-table
    :data="
      tableData.filter(
        (data) =>
          !search || data.receiptId.toLowerCase().includes(search.toLowerCase())
      )
    "
    style="width: 100%"
    :stripe="true"
    :row-style="{ height: '57px' }"
    v-loading="loading"
    element-loading-text="拼命加载中"
  >
    <el-table-column label="票据ID" prop="receiptId"> </el-table-column>
    <el-table-column label="流向ID" prop="IdTo"> </el-table-column>
    <el-table-column label="票据数额" prop="amount"> </el-table-column>
    <el-table-column label="到期日期" prop="maturityDate"> </el-table-column>
    <el-table-column align="center">
      <template slot="header" >
        <el-input v-model="search" size="mini" placeholder="输入票据ID搜索" />
      </template>
    </el-table-column>
  </el-table>
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
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
      this.$axios.post("/", this.tableData.receiptId).then((res) => {
        console.log(res);
        if (res.data.code == "200") {
          alert("承兑成功");
        }
        this.clean();
        return this.$message.success(res.msg);
      });
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
        this.tableData.push(res.data[n]);
        if (res.data[n].companyIdTo != "null") {
          this.tableData[n].IdTo = res.data[n].companyIdTo;
        } else {
          this.tableData[n].IdTo = res.data[n].bankIdTo;
        }
        this.tableData[n].maturityDate = this.timeFormat(
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
    if (window.sessionStorage.getItem("verify") == "4") {
      this.$router.push("/RealNameRegister4");
    }
    this.getLauchData();
  },
};
</script>