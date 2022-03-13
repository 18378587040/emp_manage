<template>
    <div>
        <div style="display: flex;justify-content: space-between">
            <el-button size="small" type="primary" icon="el-icon-plus" @click="showDialogVisible">添加工资账套</el-button>
            <el-button size="small" type="success" icon="el-icon-refresh" @click="initSalaries"></el-button>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    size="mini"
                    stripe
                    :data="salaries">
                <el-table-column
                        width="40"
                        type="selection">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="账套名称"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="basicSalary"
                        label="基本工资"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="trafficSalary"
                        label="交通补助"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="lunchSalary"
                        label="午餐补助"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="bonus"
                        label="奖金"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="启用时间"
                        width="100">
                </el-table-column>
                <el-table-column
                        label="养老金"
                        align="center">
                    <el-table-column
                            prop="pensionPer"
                            label="比率"
                            width="70">
                    </el-table-column>
                    <el-table-column
                            prop="pensionBase"
                            label="基数"
                            width="70">
                    </el-table-column>
                </el-table-column>
                <el-table-column
                        label="医疗保险"
                        align="center">
                    <el-table-column
                            prop="medicalPer"
                            label="比率"
                            width="70">
                    </el-table-column>
                    <el-table-column
                            prop="medicalBase"
                            label="基数"
                            width="70">
                    </el-table-column>
                </el-table-column>
                <el-table-column
                        label="公积金"
                        align="center">
                    <el-table-column
                            prop="accumulationFundPer"
                            label="比率"
                            width="70">
                    </el-table-column>
                    <el-table-column
                            prop="accumulationFundBase"
                            label="基数"
                            width="70">
                    </el-table-column>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="small" type="primary" @click="editSalary(scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="deleteSalary(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
                :title="salaryTitle"
                :visible.sync="dialogVisible"
                width="50%">
            <div style="display: flex;justify-content: space-around;align-items: center">
                <el-steps direction="vertical" :active=activeItemIndex>
                    <el-step
                            :title="itemName"
                            v-for="(itemName,index) in salaryItemName"
                            :key="index">
                    </el-step>
                </el-steps>
                <el-input
                        style="width: 200px"
                        size="small"
                        v-model="salary[title]"
                        v-for="(values,title,index) in salary"
                        :placeholder="'请输入'+salaryItemName[index]+'...'"
                        :key="index"
                        v-show="activeItemIndex==index">
                </el-input>
            </div>
            <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="preStep">{{activeItemIndex == 10 ? '取消':'上一步'}}</el-button>
        <el-button size="mini" type="primary" @click="nextStep">{{activeItemIndex == 10 ? '完成':'下一步'}}</el-button>
      </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "SalaryManage.vue",
        data(){
            return {
                salaryTitle:'添加工资账套',
                activeItemIndex:0,
                salaries:[],
                dialogVisible:false,
                salaryItemName: [
                    '账套名称',
                    '基本工资',
                    '交通补助',
                    '午餐补助',
                    '奖金',
                    '养老金比率',
                    '养老金基数',
                    '医疗保险比率',
                    '医疗保险基数',
                    '公积金比率',
                    '公积金基数',
                ],
                salary: {
                    name: '',
                    basicSalary: 0,
                    trafficSalary: 0,
                    lunchSalary: 0,
                    bonus: 0,
                    pensionPer: 0,
                    pensionBase: 0,
                    medicalPer: 0,
                    medicalBase: 0,
                    accumulationFundPer: 0,
                    accumulationFundBase: 0
                }
            }
        },
        mounted() {
            this.initSalaries();
        },
        methods:{
            editSalary(data){
                this.salaryTitle = '编辑工资账套';
                this.activeItemIndex = 0;
                this.salary.id = data.id;
                this.salary.name = data.name;
                this.salary.basicSalary = data.basicSalary;
                this.salary.trafficSalary = data.trafficSalary;
                this.salary.lunchSalary = data.lunchSalary;
                this.salary.bonus = data.bonus;
                this.salary.pensionPer = data.pensionPer;
                this.salary.pensionBase = data.pensionBase;
                this.salary.medicalPer = data.medicalPer;
                this.salary.medicalBase = data.medicalBase;
                this.salary.accumulationFundPer = data.accumulationFundPer;
                this.salary.accumulationFundBase = data.accumulationFundBase;
                this.dialogVisible = true;
            },
            deleteSalary(data){
                this.$confirm('此操作将永久删除['+data.name+']模板, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest('/api/admin/salarymanage/'+data.id).then(resp => {
                        if (resp) {
                            this.initSalaries();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            preStep(){
                if (this.activeItemIndex == 0){
                    return;
                } else if (this.activeItemIndex == 10) {
                    this.dialogVisible = false;
                    return ;
                }
                this.activeItemIndex--;
            },
            nextStep(){
                if (this.activeItemIndex == 10) {
                    if (this.salary.id) {
                        this.putRequest('/api/admin/salarymanage/',this.salary).then(resp => {
                            if (resp) {
                                this.dialogVisible = false;
                                this.initSalaries();
                            }
                        });
                    } else {
                        this.postRequest('/api/admin/salarymanage/',this.salary).then(resp => {
                            if (resp) {
                                this.dialogVisible = false;
                                this.initSalaries();
                            }
                        });
                    }
                    return;
                }
                this.activeItemIndex++;
            },
            showDialogVisible(){
                this.salary = {
                    name: '基础账套',
                    basicSalary: 0,
                    trafficSalary: 0,
                    lunchSalary: 0,
                    bonus: 0,
                    pensionPer: 0,
                    pensionBase: 0,
                    medicalPer: 0,
                    medicalBase: 0,
                    accumulationFundPer: 0,
                    accumulationFundBase: 0
                }
                this.activeItemIndex = 0;
                this.dialogVisible = true;
            },
            initSalaries(){
                this.getRequest('/api/admin/salarymanage/').then(resp => {
                    if (resp) {
                        console.log(resp)
                        this.salaries = resp;
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>
