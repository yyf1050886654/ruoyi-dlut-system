<template>
  <div class="app-container home" v-loading="loading">
    <el-row >
      <el-col :span="16">
        <div class="box1" style="height:440px;">
          <el-row>
                            <span style="color: #3c8dbc;font-weight: bold;font-size: 20px;border-bottom: 3px solid #3c8dbc;">积分排名
                                （当前排名：{{rank}}）</span>
          </el-row>
          <el-row>
            <div id="chart2" style="height:400px;"></div>

          </el-row>
        </div>

      </el-col>
      <el-col :span="8">
        <div class="box1" style="height: 440px">
          <el-row>
            <span style="color: #3c8dbc;font-weight: bold;font-size: 20px;border-bottom: 3px solid #3c8dbc;">分数构成（当前总分：{{currentTeacher.creditSum}}）</span>
          </el-row>
          <el-row>
            <div id="chart1" style="height:400px;"></div>
          </el-row>

        </div>
      </el-col>
    </el-row>
    <el-row>
      <div class="box1">
        <div id="chart3" style="height:400px;"></div>
      </div>
    </el-row>
  </div>
</template>

<script>
import {findRank, getPieReport, getTeacher, getTeacherCreditRank} from "@/api/report";
import * as echarts from 'echarts';

export default {
  name: "Index",
  data() {
    return {
      currentTeacher:{},
      item:["教学评价","科研评价","人才荣誉","平台团队","国际化","学术兼职"],
      rank:'',
      loading:true
    };
  },
  created() {
    getTeacher().then((response)=>{
      this.currentTeacher = response.data;
      findRank(this.currentTeacher.creditSum).then((res)=>{
        this.rank = res.data;
      });
      this.initCharts();
      this.initBarChart();
      this.loading = false;

    });
  },
  methods: {
    initCharts(){
      var myChart1 = echarts.init(document.getElementById('chart1'));
      getPieReport(this.currentTeacher.uid).then((res)=>{
        myChart1.setOption({

          color: ['rgba(84,170,173,0.5)', '#5470c6', '#77accd', '#65e3ef', '#f9c956', '#75bedc','#3c8dbc'],
          tooltip : {//提示框组件
            trigger: 'item',//触发类型，在饼形图中为item
            formatter: "{a} <br/>{b} : {c} ({d}%)"//提示内容格式
          },

          legend: {
            orient: 'vertical',
            left: 'left',
            data: res.data.sortNames
          },
          series : [
            {
              radius: ['30%', '55%'],
              name: '分数构成',
              type: 'pie',
              center: ['50%', '60%'],
              data:res.data.sortCount,
              itemStyle: {
                borderColor: '#fff',
                borderWidth: 2
              },
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          ]
        });
      });
    },
    initBarChart(){
      var currentTeacher = this.currentTeacher;
      var myChart = echarts.init(document.getElementById('chart2'));
      var myChart2 = echarts.init(document.getElementById('chart3'));
      getTeacherCreditRank().then((res)=>{
        myChart.setOption({
          tooltip : {//提示框组件
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          xAxis: {
            type: 'category',
            data: res.data.teacherNames
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              type: 'bar',
              data: res.data.teacherCredit,
              itemStyle: {
                normal: {
                  color: function(params) {
                    var index_color = params.name;
                    if(index_color === currentTeacher.name){
                      return '#91cc75';
                    }else {
                      return '#73c0de';
                    }
                  }
                }
              },
              showBackground: true,
              backgroundStyle: {
                color: 'rgba(180, 180, 180, 0.2)'
              }
            }

          ],
          dataZoom : [
            {
              type: 'slider',
              show: true,
              start: 0,
              end: 27,
              xAxisIndex: [0],
            },
          ]

        });
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
    }
  }
};
</script>
