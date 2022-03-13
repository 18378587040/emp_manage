<template>
    <el-card class="main-card">
        <div class="title">{{ this.$route.name }}</div>
        <!-- 表格操作 -->
        <div class="operation-container">
        </div>
        <el-table
                size="mini"
                border
                :data="emps"
                v-loading="loading">
            <el-table-column
                    type="selection"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="姓名"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="workId"
                    label="工号"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="gender"
                    label="性别"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="age"
                    label="年龄"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    width="150"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="phone"
                    align="center"
                    label="电话号码">
            </el-table-column>
            <el-table-column
                    prop="depart.name"
                    label="部门"
                    align="center">
            </el-table-column>
            <el-table-column
                    align="center"
                    label="工资账套">
                <template slot-scope="scope">
                    <el-tooltip
                            placement="right"
                            v-if="scope.row.salary.name">
                        <div slot="content">
                            <table>
                                <tr>
                                    <td>基本工资:</td>
                                    <td>{{scope.row.salary.basicSalary}}</td>
                                </tr>
                                <tr>
                                    <td>奖金:</td>
                                    <td>{{scope.row.salary.bonus}}</td>
                                </tr>
                                <tr>
                                    <td>午餐补贴:</td>
                                    <td>{{scope.row.salary.lunchSalary}}</td>
                                </tr>
                                <tr>
                                    <td>交通补贴:</td>
                                    <td>{{scope.row.salary.trafficSalary}}</td>
                                </tr>
                            </table>
                        </div>
                        <el-tag >{{scope.row.salary.name}}</el-tag>
                    </el-tooltip>
                    <el-tag v-else>暂未设置工资账套</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="创建时间"
                    width="140"
                    align="center"
            >
                <template slot-scope="scope">
                    <i class="el-icon-time" style="margin-right:5px" />
                    {{ scope.row.createTime | date }}
                </template>
            </el-table-column>
            <el-table-column
                    align="center"
                    label="操作">
                <template slot-scope="scope">
                    <el-popover
                            @hide="hide(scope.row)"
                            @show="show(scope.row.salary)"
                            placement="bottom"
                            title="编辑工资账套"
                            width="200"
                            trigger="click">
                        <el-select v-model="optionSalary" placeholder="请选择">
                            <el-option
                                    v-for="item in salaries"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                        <el-button slot="reference" size="mini" type="danger">修改工资账套</el-button>
                    </el-popover>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
                class="pagination-container"
                background
                @size-change="sizeChange"
                @current-change="currentChange"
                :current-page="current"
                :page-size="size"
                :total="count"
                :page-sizes="[10, 20]"
                layout="total, sizes, prev, pager, next, jumper"
        />
    </el-card>
</template>

<script>
    export default {
        name: "Salary.vue",
        data(){
            return {
                loading: true,
                keywords: null,
                current: 1,
                size: 10,
                count: 0,
                optionSalary:'',
                emps: [],
                salaries: []
            }
        },
        mounted() {
            this.initEmps();
            this.initSalary();
        },
        methods: {
            hide(data){
                if (this.optionSalary && this.optionSalary != data.salary.id) {
                    this.putRequest('/api/admin/salary/?eid='+data.id+'&sid='+this.optionSalary).then(resp => {
                        if (resp) {
                            this.initEmps();
                        }
                    });
                }
            },
            show(data){
                if (data) {
                    this.optionSalary = data.id;
                } else {
                    this.optionSalary = null;
                }
            },
            initSalary(){
                this.getRequest('/api/admin/salary/').then(resp => {
                    if (resp) {
                        this.salaries = resp;
                    }
                });
            },
            currentChange(current) {
                this.current = current;
                this.initEmps();
            },
            sizeChange(size){
                this.size = size;
                this.initEmps();
            },
            initEmps(){
                this.axios
                    .get("/api/admin/salary/employee", {
                        params: {
                            current: this.current,
                            size: this.size,
                            keywords: this.keywords
                        }
                    }).then(data => {
                    if (data) {
                        this.emps = data.recordList;
                        this.count = data.count;
                        this.loading = false;
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>
