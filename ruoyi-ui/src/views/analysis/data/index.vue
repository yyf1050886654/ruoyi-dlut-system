<template>
  <div class="app-container">
    <div class="box" style="height: 900px">
      <el-button v-if="currentTeacher.name=='刘宇峰'||currentTeacher.name == 'admin'||currentTeacher.name=='杨逸帆'" @click="exportRankExcel">导出Excel</el-button>
      <el-button @click="handleDownLoad">导出详情</el-button>
      <div class="excelTime">日期：{{reportData.reportDate}}</div>
      <table class="exceTable" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="4" class="headBody">用户统计</td>
        </tr>
        <tr>
          <td width='20%' class="tabletrBg">教师人数</td>
          <td width='30%'>{{reportData.teacherNum}}</td>
          <td width='20%' class="tabletrBg">管理员人数</td>
          <td width='30%'>{{reportData.adminNum}}</td>
        </tr>
        <tr>
          <td colspan="4" class="headBody">积分统计</td>
        </tr>
        <tr>
          <td width='20%' class="tabletrBg">最高分</td>
          <td width='30%'>{{reportData.maxCredit}}</td>
          <td width='20%' class="tabletrBg">最低分</td>
          <td width='30%'>{{reportData.minCredit}}</td>
        </tr>
        <tr>
          <td width='20%' class="tabletrBg">平均分</td>
          <td width='30%'>{{reportData.averageCredit}}</td>
          <td width='20%' class="tabletrBg">中位数</td>
          <td width='30%'>{{reportData.middleCredit}}</td>
        </tr>

      </table>
      <el-row>
        <div class="box1">
          <div id="chart" style="height:400px;"></div>
        </div>
      </el-row>

    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {getReportData, getTeacher, getTeacherCreditRank, rankExcel} from "@/api/report";
import {downLoadExcel} from "@/api/teacherAward/bulkImport/bulkImport";

export default {
  name: "Report",
  data() {
    return {
      currentTeacher:{},
      item:["教学评价","科研评价","人才荣誉","平台团队","国际化","学术兼职"],
      rank:'',
      reportData:{
        reportDate:null,
        teacherNum:0,
        adminNum:0,
        maxCredit:0,
        minCredit:0,
        averageCredit:0,
        middleCredit:0
      },
      checkedSorts:[1,2,3,4,5,6,7],
      checkedTeachers:[]
    };
  },
  created() {
    getTeacher().then((response)=>{
      this.currentTeacher = response.data;
      this.checkedTeachers = this.currentTeacher.uid;
      getReportData().then((res)=>{
        this.reportData = res.data;
        this.initBarChart();
      });
    });
  },
  methods: {
    exportRankExcel(){
      //window.location.href = '/report/rankExcel.do';
      rankExcel();
    },
    handleDownLoad(){
      //window.location.href = "/teacherAward/downLoadExcel.do?sorts="+this.checkedSorts+"&teachers="+this.checkedTeachers;
      downLoadExcel(this.checkedSorts,this.checkedTeachers);
    },
    initBarChart(){
      var myChart2 = echarts.init(document.getElementById('chart'));
      getTeacherCreditRank().then((res)=>{
        myChart2.setOption({
          color: ['rgba(84,170,173,0.5)', '#5470c6', '#77accd', '#65e3ef', '#f9c956', '#75bedc','#3c8dbc'],

          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: this.item
          },
          xAxis: {
            type: 'value'
          },
          yAxis: {
            type: 'category',
            data: res.data.teacherNames
          },
          dataZoom : [
            {
              type: 'slider',
              show: true,
              start: 0,
              end: 20,
              yAxisIndex: [0],
            },
          ],
          series: [
            {
              name: this.item[0],
              type: 'bar',
              stack: '总量',
              label: {
                show: true,
                position: 'inside'
              },
              data: res.data.sort1
            },
            {
              name: this.item[1],
              type: 'bar',
              stack: '总量',
              label: {
                show: true,
                position: 'inside'
              },
              data: res.data.sort2
            },
            {
              name: this.item[2],
              type: 'bar',
              stack: '总量',
              label: {
                show: true,
                position: 'inside'
              },
              data: res.data.sort3
            },
            {
              name: this.item[3],
              type: 'bar',
              stack: '总量',
              label: {
                show: true,
                position: 'inside'
              },
              data: res.data.sort4
            },
            {
              name: this.item[4],
              type: 'bar',
              stack: '总量',
              label: {
                show: true,
                position: 'inside'
              },
              data: res.data.sort5
            },
            {
              name: this.item[5],
              type: 'bar',
              stack: '总量',
              label: {
                show: true,
                position: 'inside'
              },
              data: res.data.sort6
            }
          ]
        });
      });
    },
  }
};
</script>
<style>
.excelTitle{
  text-align: center;
  overflow: hidden;
  line-height: 40px;
}
.excelTitle .el-button{
  float: left;
}
.excelTime{
  padding: 10px 0;
  text-align: right;
}
.exceTable{
  width: 100%;
  border-right: 1px solid #e6e6e6;
  border-bottom: 1px solid #e6e6e6;
  font-size: 14px;
  color: #333;
}
.exceTable tr,.exceTable td{
  border-left: 1px solid #e6e6e6;
  border-top: 1px solid #e6e6e6;
  height: 40px;
  line-height: 40px;
  padding: 0 10px;
}
.exceTable .headBody{
  text-align: center;
  font-weight: 700;
  font-size: 14px;
}
.tabletrBg{
  background: #fcfcfc;
  text-align: right;
}
</style>
