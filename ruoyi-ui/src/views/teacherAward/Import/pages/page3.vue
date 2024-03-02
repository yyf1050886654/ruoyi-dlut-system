<template>
  <div class="singleImportPage3">
    <el-button type="primary" plain @click="button='1';resetForm();dialog3_1 = true;addTableActiveName='1';handleClick4Add();">人才荣誉</el-button>
    <el-button type="primary" plain @click="button='2';resetForm();addTableActiveName='2';dialog3_2 = true;handleClick4Add();">人才引进</el-button>
    <el-button type="danger" icon="el-icon-delete" @click="handleBulkDelete()">批量删除</el-button>
    <!-- 获奖情况申报登记表单 -->
    <div class="add-form">
      <el-dialog title="申报" :visible.sync="dialog3_1">
        <el-divider content-position="left">
          <span style="color: #3c8dbc;font-weight: bold;font-size: 20px">奖项情况</span>
          <el-tooltip placement="top">
            <div slot="content" v-html="infoText"></div>
            <span class="help" style="color: #3c8dbc"><i class="fa fa-exclamation-circle" aria-hidden="true"></i>详情</span>
          </el-tooltip>
        </el-divider>
        <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
          <el-tabs v-model="addTableActiveName" @tab-click="handleClick4Add">
            <el-tab-pane label="一般荣誉" name="1">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="获得奖项">
                    <el-select v-model="selectedAward.awardName" filterable placeholder="请选择">
                      <el-option
                        v-for="item in awardList4Show"
                        :label="item"
                        :value="item">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" >
                  <el-form-item label="详情">
                    <el-form class="base-form" ref="baseForm" :model="baseForm" :rules="rules" auto-complete="on">
                      <el-table ref="table-input" class="table" :data="baseForm.demoList">
                        <el-table-column label="姓名" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.teacherUid'"  class="all">
                              <el-select v-model="scope.row.teacherUid" filterable placeholder="请选择">
                                <el-option
                                  v-for="(item,index) in allTeacher"
                                  :label="item.name"
                                  :value="item.uid">
                                </el-option>
                              </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="第几完成人" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.rankPosition'" class="all">
                              <el-select v-model="scope.row.rankPosition" filterable placeholder="请选择">
                                <el-option label="第一完成人" value="1"></el-option>
                              </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="分数" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.credit'" class="all">
                              <el-input placeholder="请选择" v-model="scope.row.credit" :disabled="true"></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column prop="" label="操作">
                          <template slot-scope="scope">
                            <el-button size= "mini" type="primary" icon="el-icon-upload" @click="addLine()"></el-button>
                            <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)"></el-button>
                          </template>
                        </el-table-column>
                      </el-table>
                    </el-form>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="起始时间">
                    <el-date-picker
                      type="date"
                      placeholder="选择日期"
                      v-model="teacherAchievements.beginDate"
                      style="width: 100%;"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyy-MM-dd">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="结束时间">
                    <el-date-picker
                      type="date"
                      placeholder="选择日期"
                      v-model="teacherAchievements.endDate"
                      style="width: 100%;"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyy-MM-dd">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="24">
                  <el-form-item label="备注">
                    <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="textarea">
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>

            </el-tab-pane>
            <el-tab-pane label="党建等其它荣誉" name="3">
              <el-row>
                <el-col :span="12">
                  <!--<el-form-item label="获得奖项" prop="id">-->
                  <el-form-item label="获得奖项">
                    <el-select v-model="selectedAward.awardName" filterable placeholder="请选择">
                      <el-option
                        v-for="item in awardList4Show"
                        :label="item"
                        :value="item">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" >
                  <el-form-item label="详情">
                    <el-form class="base-form" ref="baseForm" :model="baseForm" :rules="rules" auto-complete="on">
                      <el-table ref="table-input" class="table" :data="baseForm.demoList">
                        <el-table-column label="姓名" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.teacherUid'"  class="all">
                              <el-select v-model="scope.row.teacherUid" filterable placeholder="请选择">
                                <el-option
                                  v-for="(item,index) in allTeacher"
                                  :label="item.name"
                                  :value="item.uid">
                                </el-option>
                              </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="第几完成人" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.rankPosition'" class="all">
                              <el-select v-model="scope.row.rankPosition" filterable placeholder="请选择">
                                <el-option label="第一完成人" value="1"></el-option>
                                <el-option label="第二完成人" value="0.7"></el-option>
                                <el-option label="第三完成人" value="0.5"></el-option>
                                <el-option label="第四完成人" value="0.3"></el-option>
                                <el-option label="第五，六完成人" value="0.2"></el-option>
                              </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="分数" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.credit'" class="all">
                              <el-input placeholder="请选择" v-model="scope.row.credit" :disabled="true"></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column prop="" label="操作">
                          <template slot-scope="scope">
                            <el-button size= "mini" type="primary" icon="el-icon-upload" @click="addLine()"></el-button>
                            <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)"></el-button>
                          </template>
                        </el-table-column>
                      </el-table>
                    </el-form>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="起始时间">
                    <el-date-picker
                      type="date"
                      placeholder="选择日期"
                      v-model="teacherAchievements.beginDate"
                      style="width: 100%;"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyy-MM-dd">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="结束时间">
                    <el-date-picker
                      type="date"
                      placeholder="选择日期"
                      v-model="teacherAchievements.endDate"
                      style="width: 100%;"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyy-MM-dd">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="24">
                  <el-form-item label="备注">
                    <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="textarea">
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialog3_1 = false;resetForm()">取消</el-button>
          <el-button type="primary" @click="calculateCredit()">查询</el-button>
          <el-button type="success" @click="handleSubmit4Admin()">确定</el-button>
        </div>

      </el-dialog>
    </div>
    <!-- 项目情况申报登记表单 -->
    <div class="add-form">
      <el-dialog title="申报" :visible.sync="dialog3_2">
        <el-divider content-position="left">
          <span style="color: #3c8dbc;font-weight: bold;font-size: 20px">教学项目</span>
          <el-tooltip placement="top">
            <div slot="content" v-html="infoText"></div>
            <span class="help" style="color: #3c8dbc"><i class="fa fa-exclamation-circle" aria-hidden="true"></i>详情</span>
          </el-tooltip>
        </el-divider>
        <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
          <el-tabs v-model="addTableActiveName" @tab-click="handleClick4Add">
            <el-tab-pane label="人才引进荣誉" name="2">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="类别">
                    <el-select v-model="selectedAward.awardName" filterable placeholder="请选择">
                      <el-option
                        v-for="item in awardList4Show"
                        :label="item"
                        :value="item">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" >
                  <el-form-item label="详情">
                    <el-form class="base-form" ref="baseForm" :model="baseForm" :rules="rules" auto-complete="on">
                      <el-table ref="table-input" class="table" :data="baseForm.demoList">
                        <el-table-column label="姓名" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.teacherUid'"  class="all">
                              <el-select v-model="scope.row.teacherUid" filterable placeholder="请选择">
                                <el-option
                                  v-for="(item,index) in allTeacher"
                                  :label="item.name"
                                  :value="item.uid">
                                </el-option>
                              </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="第几完成人" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.rankPosition'" class="all">
                              <el-select v-model="scope.row.rankPosition" filterable placeholder="请选择">
                                <el-option label="第一完成人" value="1"></el-option>
                                <el-option label="第二完成人" value="0.7"></el-option>
                                <el-option label="第三完成人" value="0.5"></el-option>
                                <el-option label="第四完成人" value="0.3"></el-option>
                                <el-option label="第五，六完成人" value="0.2"></el-option>
                              </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="分数" show-overflow-tooltip>
                          <template slot-scope="scope">
                            <el-form-item :prop="'demoList.'+scope.$index+'.credit'" class="all">
                              <el-input placeholder="请选择" v-model="scope.row.credit" :disabled="true"></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column prop="" label="操作">
                          <template slot-scope="scope">
                            <el-button size= "mini" type="primary" icon="el-icon-upload" @click="addLine()"></el-button>
                            <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)"></el-button>
                          </template>
                        </el-table-column>
                      </el-table>
                    </el-form>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="起始时间">
                    <el-date-picker
                      type="date"
                      placeholder="选择日期"
                      v-model="teacherAchievements.beginDate"
                      style="width: 100%;"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyy-MM-dd">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="结束时间">
                    <el-date-picker
                      type="date"
                      placeholder="选择日期"
                      v-model="teacherAchievements.endDate"
                      style="width: 100%;"
                      format="yyyy 年 MM 月 dd 日"
                      value-format="yyyy-MM-dd">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="项目备注">
                    <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="textarea">
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialog3_2 = false;resetForm()">取消</el-button>
          <el-button type="primary" @click="calculateCredit()">查询</el-button>
          <el-button type="success" @click="handleSubmit4Admin()">确定</el-button>
        </div>
      </el-dialog>
    </div>
    <div class="filter-container">
      <el-input placeholder="项目名称" v-model="pagination.queryString" style="width: 200px;" class="filter-item"></el-input>
      <el-button @click="findPage()" class="dalfBut">查询</el-button>
    </div>
    <el-table size="small" @selection-change="handleSelectionChange" current-row-key="id" :data="dataList" stripe highlight-current-row >
      <el-table-column type="selection" label="序号"></el-table-column>
      <el-table-column type="index" align="center" label="序号"></el-table-column>
      <el-table-column prop="teacher.name" label="教师姓名" align="center"></el-table-column>
      <el-table-column prop="awards.awardName" label="项目" align="center"></el-table-column>
      <el-table-column label="奖项" align="center">
        <template slot-scope="scope">
                                <span>{{ scope.row.awards.priceLevel == '0' ? '特等奖' :
                                  scope.row.awards.priceLevel == '1' ? '一等奖' :
                                    scope.row.awards.priceLevel == '2' ? '二等奖' :
                                      scope.row.awards.priceLevel == '3' ? '三等奖' :
                                        scope.row.awards.priceLevel == '4' ? '优秀奖' :'不区分奖项'}}

                                </span>
        </template>
      </el-table-column>
      <el-table-column prop="rankPosition" label="排位折分系数" align="center"></el-table-column>
      <el-table-column label="种类" align="center">
        <template slot-scope="scope">
                                <span>{{ scope.row.awards.kind == '1' ? '一般荣誉' :
                                  scope.row.awards.kind == '2' ? '人才引进荣誉' :'党建及其他荣誉'}}
                                </span>
        </template>
      </el-table-column>
      <el-table-column prop="weight" label="调整系数" align="center"></el-table-column>
      <el-table-column prop="realCredit" label="业绩积分" align="center"></el-table-column>
      <el-table-column prop="beginDate" label="开始日期" align="center"></el-table-column>
      <el-table-column prop="endDate" label="结束日期" align="center"></el-table-column>
      <el-table-column prop="info" label="备注/详情" align="center"></el-table-column>
      <el-table-column label="编辑/删除" align="center">
        <template slot-scope="scope">
          <!--<el-button type="success" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>-->
          <el-button size= "mini" type="primary" icon="el-icon-edit" @click="handleUpdate(scope.row)"></el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete1(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination
        class="pagiantion"
        @current-change="handleCurrentChange"
        :current-page="pagination.currentPage"
        :page-size="pagination.pageSize"
        layout="total, prev, pager, next, jumper"
        :total="pagination.total">
      </el-pagination>
    </div>
  </div>
</template>

<style scoped lang="scss">

</style>
<script>
import {getAllTeacher} from "@/api/report";
import {bulkDelete} from "@/api/teacherAward/bulkImport/bulkImport";
import {
  addTeacherAward,
  delTeacherAward,
  editTeacherAward,
  findById,
  findPage4All, getInfo
} from "@/api/teacherAward/Import/Import";
import {findByKind, findByKindUnique} from "@/api/dlut/awards";

export default {
  name: 'singleImportPage1',
  data() {
    return {
      display:false,
      dialog3_1:false,
      dialog3_2:false,
      active: 0,
      dialogFormVisible4Edit:false,

      kind1:'',
      currentTeacher:{},
      kind:'',


      activeName: '1',
      addTableActiveName:"1",


      kindNum:"",
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize:10,//每页显示的记录数
        total:0,//总记录数
        queryString:null//查询条件
      },

      awardList4Show:'',
      awardList:[],
      selectedAward:{},
      addAwardId:'',
      credit4Show:'',
      teacherAchievements:{},
      textarea:'',
      projectName: '',
      input:1,

      guideStudent:{},
      attachedInfo:'',

      dataList: [],//当前页要展示的分页列表数据
      formData: {},//表单数据
      imageUrl:'',
      autoUpload:true,
      fileList:[],
      zip:{},
      booltemp:true,

      infoText:'',
      button:'',
      baseForm: {
        demoList: [
          {
            teacherUid: null,
            rankPosition: null,
            credit: 0
          }
        ]
      },
      rules: {
      },
      allTeacher:[],
      selection: [],
      ids:[],
      teachers:[],
      credits:[],
      boolTemp:false
    };
  },
  created() {
    getAllTeacher().then((response)=>{
      this.allTeacher = response.data;
      this.findPage();//VUE对象初始化完成后调用分页查询方法，完成分页查询
    });
  },
  methods: {
    handleBulkDelete() {
      //传数组进行批量删除
      this.$confirm('你确定要批量删除当前数据吗？','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //发送ajax请求，将id提交到Controller
        bulkDelete(this.ids,this.credits,this.teachers).then((res) => {
          if(res.flag){
            this.$message({
              type:'success',
              message:res.message
            });
            this.findPage();
          }else{
            //执行失败
            this.$message.error(res.message);
          }
        }).catch((r) => {
          this.showMessage(r);
        });
      }).catch(() => {
        this.$message("已取消");
      });
    },
    handleSelectionChange(selection) {
      this.selection = selection;
      this.ids = selection.map(item => item.id);// 需要根据数据情况调整id名称
      this.teachers = selection.map(item => item.teacherUid);// 需要根据数据情况调整id名称
      this.credits = selection.map(item => item.realCredit);// 需要根据数据情况调整id名称
    },
    handleEdit() {
      //进行表单校验
      this.$refs['dataEditForm'].validate((valid) => {
        if(valid){
          editTeacherAward(this.formData).then((res) => {
            if(res.flag){
              //弹出成功提示信息
              this.$message({
                type:'success',
                message:res.message
              });
            }else{
              //执行失败
              this.$message.error(res.message);
            }
          }).finally(() => {
            //不管成功还是失败，都调用分页查询方法
            this.findPage();
            //隐藏编辑窗口
            this.dialogFormVisible4Edit = false;
          });
        }else{
          //表单校验不通过
          this.$message.error("表单数据校验失败!");
          return false;
        }
      });
    },
    handleUpdate(row) {
      //弹出编辑窗口
      this.dialogFormVisible4Edit = true;
      findById(row.id).then((res) => {
        if(res.flag){
          //进行回显，基于VUE的数据绑定实现
          this.formData = res.data;
        }else{
          //查询失败，弹出提示
          this.$message.error(res.message);
        }
      });
    },
    handleDelete1(row) {
      //弹出确认框
      this.$confirm('你确定要删除当前数据吗？','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //发送ajax请求，将id提交到Controller
        delTeacherAward(row.id,row.realCredit,row.teacherUid).then((res) => {
          if(res.flag){
            this.$message({
              type:'success',
              message:res.message
            });
            this.findPage();
          }else{
            //执行失败
            this.$message.error(res.message);
          }
        }).catch((r) => {
          this.showMessage(r);
        });
      }).catch(() => {
        this.$message("已取消");
      });
    },
    calculateCredit(){
      this.checkAwards();
      if (this.boolTemp == true && this.addAwardId == '229'){
        this.credit4Show *= 0.6
      }
      if (this.boolTemp == true && this.addAwardId == '230'){
        this.credit4Show *= 0.2
      }
      let sumRankPosition = this.sumUpCredit();
      if (this.baseForm.demoList.length == 1){
        this.baseForm.demoList.forEach((rank)=>{
          rank.credit = this.credit4Show*rank.rankPosition;
        });
      }
      else {
        this.baseForm.demoList.forEach((rank)=>{
          rank.credit = (this.credit4Show*rank.rankPosition)/sumRankPosition;
        });
      }


    },
    sumUpCredit(){
      let sumRankPosition = 0;
      this.baseForm.demoList.forEach((rank)=>{
        sumRankPosition += parseFloat(rank.rankPosition);
      });
      return sumRankPosition;
    },
    addLine() {
      const newLine = {
        teacherUid: null,
        rankPosition: null,
        credit: 0
      }
      this.baseForm.demoList.push(newLine)
    },
    checkGuide(){
      if(this.guideStudent.sort != null &&
        this.guideStudent.level != null &&
        this.guideStudent.priceLevel != null &&
        this.guideStudent.rankPosition != null ){//执行成功
        this.credit4Show = this.guideStudent.sort*
          this.guideStudent.level*
          this.guideStudent.priceLevel*
          this.guideStudent.rankPosition*
          this.input;
        this.attachedInfo = '层次：'+this.guideStudent.level+
          '    类别：'+this.guideStudent.level+
          '    等级：'+this.guideStudent.priceLevel+
          '    排名：'+this.guideStudent.rankPosition+
          '    备注：';
        this.addAwardId='94';
      }else{//执行失败
        //弹出提示
        this.$message.error("请填写基本信息");
      }

    },
    checkAwards(){
      if (this.activeName == '1' && this.addTableActiveName == '3'){
        this.checkGuide();
      }
      if (this.activeName == '3' && this.addTableActiveName == '2'){
        this.selectedAward.rankPosition = 1;
      }
      if (this.activeName == '4' && this.addTableActiveName == '2'){
        this.selectedAward.priceLevel = 5;
      }
      if (this.activeName == '6' || this.activeName == '7'){
        this.selectedAward.rankPosition = 1;
      }
      if (this.addTableActiveName == '4'||
        this.addTableActiveName == '5'||
        this.addTableActiveName == '6'||
        this.addTableActiveName == '7'){
        this.selectedAward.priceLevel=5;
      }
      if (this.activeName == "7" ||this.activeName == "3"||this.activeName == "5"){
        this.selectedAward.priceLevel=5;
      }
      if (this.activeName === "2"){
        if (this.addTableActiveName != '1'){
          this.selectedAward.priceLevel=5;
        }
      }
      if (this.activeName === "5" && this.addTableActiveName === '4'){
        this.selectedAward.awardName='作为校内合作人，聘请海外知名教授为我校访问学者，开展教学和科研方面的合作';
      }
      var bool = 0;
      this.awardList.forEach((award) => {
        if (award.awardName == this.selectedAward.awardName
          && award.priceLevel == this.selectedAward.priceLevel){
          this.credit4Show = award.credit*this.input;
          this.addAwardId = award.id;
          bool=1;
        }
      });
      if (bool == 0){
        if (this.activeName != '1' || this.addTableActiveName != '3'){
          this.$message.error("没有查询到对应项目");
          this.resetTemp();
        }
      }

    },
    handleSubmit4Admin(){
      this.calculateCredit();
      if (this.teacherAchievements.beginDate > '2022-1-1'&& this.teacherAchievements.endDate <'2023-1-1' || this.activeName == "7"){
        //这里7只用收集开始日期，需要一起包括
        if(this.teacherAchievements.beginDate < this.teacherAchievements.endDate || this.activeName == "7"){
          this.baseForm.demoList.forEach((item)=>{
            var param = {
              id:null,
              teacherUid:item.teacherUid,
              awardsId: this.addAwardId,
              projectName:this.projectName,
              info:this.attachedInfo+this.textarea,
              beginDate:this.teacherAchievements.beginDate,
              endDate:this.teacherAchievements.endDate,
              realCredit:item.credit,
              weight: 1/this.sumUpCredit(),
              rankPosition:item.rankPosition,
              zip:this.zip
            };
            //表单校验通过，发生ajax请求，将录入的数据提交到后台进行处理
            addTeacherAward(param,this.activeName).then((res) => {
              this.dialog1_1 = false;
              this.dialog1_2 = false;
              this.dialog1_3 = false;
              this.dialog1_4 = false;
              this.dialog2_1 = false;
              this.dialog2_2 = false;
              this.dialog2_3 = false;
              this.dialog2_4 = false;
              this.dialog3_1 = false;
              this.dialog3_2 = false;
              this.dialog4_1 = false;
              this.dialog4_2 = false;
              this.dialog5_1 = false;
              this.dialog5_2 = false;
              this.dialog6_1 = false;
              this.dialog7_1 = false;
              if(res.flag){//执行成功
                //新增成功后，重新调用分页查询方法，查询出最新的数据
                this.findPage();
                //弹出提示信息
                this.$message({
                  message:res.message,
                  type:'success'
                });
              }else{//执行失败
                //弹出提示
                this.$message.error(res.message);
              }
            });
          });
          this.resetForm();

        }else{
          //校验不通过
          this.$message.error("数据校验失败，请检查你的输入信息是否正确！");
          return false;
        }
      }else {
        this.$message.error("请输入2023年的项目或者奖项");
        this.teacherAchievements={};
      }
    },
    handleDelete(row) {
      //弹出确认框
      this.$confirm('你确定要删除当前数据吗？','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.baseForm.demoList = this.baseForm.demoList.filter((item, i, arr) => {
          return item.teacherUid !== row.teacherUid;
        })
        if (this.baseForm.demoList.length === 0) {
          this.addLine();
        }
      }).catch(() => {
        this.$message("已取消");
      });

    },
    //分页查询
    findPage() {
      //发送ajax请求，提交分页相关请求参数（页码、每页显示记录数、查询条件）
      var param = {
        currentPage:this.pagination.currentPage,
        pageSize:this.pagination.pageSize,
        queryString: this.pagination.queryString
      };
      findPage4All(param,this.activeName).then((res)=>{
        //解析Controller响应回的数据，为模型数据赋值
        this.pagination.total = res.total;
        this.dataList = res.rows;
      });
    },
    // 重置表单
    resetForm() {
      this.formData = {};//重置数据
      this.awardList=[];
      this.resetTemp();
    },
    resetTemp(){
      this.selectedAward={};
      this.credit4Show='';
      this.teacherAchievements = {};
      this.textarea='';
      this.attachedInfo='';
      this.guideStudent={};
      this.projectName='';
      this.zip='';
      this.fileList=[];
      this.input=1;
      this.baseForm.demoList = [{
        teacherUid: null,
        rankPosition: null,
        credit: 0
      }];
    },
    //切换页码
    handleCurrentChange(currentPage) {
      //设置最新的页码
      this.pagination.currentPage = currentPage;
      //重新调用findPage方法进行分页查询
      this.findPage();
    },
    handleClick(tab, event) {
      console.log(this.activeName);
      this.pagination.currentPage = 1;
      this.pagination.queryString = null;
      this.findPage();
    },
    handleClick4Add() {
      this.resetForm();
      getInfo(this.button,this.activeName).then((res)=>{
        //解析Controller响应回的数据，为模型数据赋值
        this.infoText = res.data;
      });
      findByKind(this.addTableActiveName,this.activeName).then((res)=>{
        //解析Controller响应回的数据，为模型数据赋值
        this.awardList = res.data;
      });
      findByKindUnique(this.addTableActiveName,this.activeName).then((res)=>{
        //解析Controller响应回的数据，为模型数据赋值
        this.awardList4Show = res.data;
      });

    }
  }
}
</script>
