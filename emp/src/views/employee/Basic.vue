<template>
    <el-card class="main-card">
        <div class="title">{{ this.$route.name }}</div>
        <!-- 表格操作 -->
        <div class="operation-container">
            <el-button
                    type="primary"
                    size="small"
                    icon="el-icon-plus"
                    @click="openModel(null)"
            >
                新增
            </el-button>
            <el-button
                    type="danger"
                    size="small"
                    icon="el-icon-delete"
                    :disabled="employeeIdList.length == 0"
                    @click="deleteFlag = true"
            >
                批量删除
            </el-button>
            <el-button size="small" type="success" icon="el-icon-download" @click="exportEmp">
                导出数据
            </el-button>
            <el-upload
                    :headers="headers"
                    :disabled="importDataDisable"
                    :on-success="onSuccess"
                    :on-error="onError"
                    :before-upload="beforeUpload"
                    :show-file-list="false"
                    style="display: inline-flex;margin-right: 8px"
                    action="/api/import">
                <el-button size="small" style="margin-left: 10px" type="success" :icon="importDataBtnIcon" :disabled="importDataDisable">
                    {{importDataBtnText}}
                </el-button>
            </el-upload>
            <!-- 条件筛选 -->
            <div style="margin-left:auto">
                <el-input
                        v-model="keywords"
                        prefix-icon="el-icon-search"
                        size="small"
                        placeholder="请输入职称名"
                        style="width:200px"
                        @keyup.enter.native="searchEmployee"
                />
                <el-button
                        type="primary"
                        size="small"
                        icon="el-icon-search"
                        style="margin-left:1rem"
                        @click="searchEmployee"
                >
                    搜索
                </el-button>
            </div>
        </div>
        <!-- 表格展示 -->
        <el-table
                size="mini"
                border
                :data="employeeList"
                @selection-change="selectionChange"
                v-loading="loading"
        >
            <!-- 表格列 -->
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
                    width="60"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="age"
                    label="年龄"
                    width="60"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    width="150"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="adress"
                    label="详细地址"
                    width="250"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="phone"
                    align="center"
                    width="120"
                    label="电话号码">
            </el-table-column>
            <el-table-column
                    prop="depart.name"
                    label="部门"
                    width="100"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="position.name"
                    label="职位"
                    width="100"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="jobtitle.name"
                    label="职称"
                    width="100"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="创建时间"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    <i class="el-icon-time" style="margin-right:5px" />
                    {{ scope.row.createTime | date }}
                </template>
            </el-table-column>
            <!-- 列操作 -->
            <el-table-column label="操作" align="center" width="160">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="openModel(scope.row)">
                        编辑
                    </el-button>
                    <el-popconfirm
                            title="确定删除吗？"
                            style="margin-left:1rem"
                            @confirm="deleteEmployee(scope.row.id)"
                    >
                        <el-button size="mini" type="danger" slot="reference">
                            删除
                        </el-button>
                    </el-popconfirm>
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
        <!-- 批量删除对话框 -->
        <el-dialog :visible.sync="deleteFlag" width="30%">
            <div class="dialog-title-container" slot="title">
                <i class="el-icon-warning" style="color:#ff9900" />提示
            </div>
            <div style="font-size:1rem">是否删除选中项？</div>
            <div slot="footer">
                <el-button @click="deleteFlag = false">取 消</el-button>
                <el-button type="primary" @click="deleteEmployee(null)">
                    确 定
                </el-button>
            </div>
        </el-dialog>
        <!-- 添加对话框 -->
        <el-dialog :visible.sync="addOrEdit" width="30%">
            <div class="dialog-title-container" slot="title" ref="employeeTitle" />
            <el-form label-width="80px" size="medium" :model="employeeForm">
                <el-form-item label="姓名">
                    <el-input style="width:250px" v-model="employeeForm.name" />
                </el-form-item>
                <el-form-item label="工号">
                    <el-input style="width:250px" v-model="employeeForm.workId" disabled/>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input style="width:250px" v-model="employeeForm.age" />
                </el-form-item>
                <el-form-item label="性别">
                    <el-input style="width:250px" v-model="employeeForm.gender" />
                </el-form-item>
                <el-form-item label="详细地址">
                    <el-input style="width:250px" v-model="employeeForm.adress" />
                </el-form-item>
                <el-form-item label="手机号码">
                    <el-input style="width:250px" v-model="employeeForm.phone" />
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input style="width:250px" v-model="employeeForm.email" />
                </el-form-item>
                <el-form-item label="部门">
                    <el-popover
                            v-model="visible"
                            placement="bottom"
                            title="选择部门"
                            width="200"
                            trigger="manual">
                        <el-tree
                            :data="departs"
                            :props="defaultProps"
                            default-expand-all
                            @node-click="handleNodeClick"></el-tree>
                        <div slot="reference" style="width: 240px;display: inline-flex;border: 1px solid #dedede;
                    height: 35px;border-radius: 5px;cursor: pointer" @click="showDepart">{{inputDepartName}}</div>
                    </el-popover>
                </el-form-item>
                <el-form-item label="职位">
                    <el-select v-model="employeeForm.positionId" placeholder="请选择">
                        <el-option
                                v-for="item in positions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="职称">
                    <el-select v-model="employeeForm.jobtitleId" placeholder="请选择">
                        <el-option
                                v-for="item in jobtitles"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer">
                <el-button @click="addOrEdit = false">取 消</el-button>
                <el-button type="primary" @click="addOrEditCategory">
                    确 定
                </el-button>
            </div>
        </el-dialog>
    </el-card>
</template>

<script>
    import {Message} from "element-ui";

    export default {
        name: "Basic.vue",
        mounted() {
            this.initEmpInfo();
        },
        created() {
            this.listEmployees();
        },
        data: function() {
            return {
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                headers: {
                    Authorization: window.sessionStorage.getItem('tokenStr')
                },
                importDataDisable: false,
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                inputDepartName: "",
                intputPosition: "",
                inputJobtitle: "",
                visible: false,
                loading: true,
                deleteFlag: false,
                addOrEdit: false,
                employeeIdList: [],
                employeeList: [],
                positions: [],
                jobtitles: [],
                departs: [],
                employeeForm: {
                    name: "",
                    age: "",
                    adress: "",
                    phone: "",
                    email: "",
                    gender: "",
                    positionId: "",
                    departId: "",
                    jobtitleId: "",
                    salaryId: 1,
                    workId: "",
                },
                keywords: null,
                current: 1,
                size: 10,
                count: 0
            };
        },
        methods: {
            onError(){
                this.importDataBtnIcon='el-icon-upload2';
                this.importDataBtnText = '导入数据';
                this.importDataDisable = false;
            },
            onSuccess(){
                this.importDataBtnIcon='el-icon-upload2';
                this.importDataBtnText = '导入数据';
                this.importDataDisable = false;
                this.listEmployees();
            },
            beforeUpload(){
                this.importDataBtnIcon='el-icon-loading';
                this.importDataBtnText = '正在导入';
                this.importDataDisable = true;
            },
            exportEmp(){
                this.downloadRequest('/api/export');
            },
            handleNodeClick(data) {
                this.inputDepartName = data.name;
                this.employeeForm.departId = data.id;
                this.visible = !this.visible;
            },
            selectionChange(jobTitleList) {
                this.employeeIdList = [];
                jobTitleList.forEach(item => {
                    this.employeeIdList.push(item.id);
                });
            },
            searchEmployee() {
                this.current = 1;
                this.listEmployees();
            },
            sizeChange(size) {
                this.size = size;
                this.listEmployees();
            },
            currentChange(current) {
                this.current = current;
                this.listEmployees();
            },
            deleteEmployee(id) {
                var param = {};
                if (id == null) {
                    param = { data: this.employeeIdList };
                } else {
                    param = { data: [id] };
                }
                this.axios.delete("/api/admin/employee", param).then(( data ) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listEmployees();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.deleteFlag = false;
                });
            },
            openModel(employee) {
                this.getWorkId();
                if (employee != null) {
                    this.employeeForm = JSON.parse(JSON.stringify(employee));
                    this.inputDepartName = employee.depart.name;
                    this.intputPosition = employee.position.name;
                    this.inputJobtitle = employee.jobtitle.name;
                    this.$refs.employeeTitle.innerHTML = "修改员工信息";
                } else {
                    this.employeeForm.name = "";
                    this.employeeForm.workId = this.employeeForm.workId;
                    this.employeeForm.age = "";
                    this.employeeForm.gender = "";
                    this.employeeForm.adress = "";
                    this.employeeForm.phone = "";
                    this.employeeForm.email = "";
                    this.employeeForm.positionId = "";
                    this.employeeForm.departId = "";
                    this.inputDepartName = "";
                    this.employeeForm.jobtitleId = "";
                    this.employeeForm.salaryId = 1;
                    this.$refs.employeeTitle.innerHTML = "添加员工信息";
                }
                this.addOrEdit = true;
            },
            addOrEditCategory() {
                if (this.employeeForm.name.trim() == "") {
                    this.$message.error("姓名不能为空");
                    return false;
                }
                this.axios.post("/api/admin/employee", this.employeeForm).then((data) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listEmployees();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.addOrEdit = false;
                });
            },
            listEmployees() {
                this.axios
                    .get("/api/admin/employee", {
                        params: {
                            current: this.current,
                            size: this.size,
                            keywords: this.keywords
                        }
                    })
                    .then((data) => {
                        this.employeeList = data.recordList;
                        this.count = data.count;
                        this.loading = false;
                    });
            },
            getWorkId() {
                this.axios.get("/api/maxWorkID").then((data) => {
                    if (data) {
                        this.employeeForm.workId = data.obj;
                    }
                });
            },
            initEmpInfo() {
                if (this.$store.state.positions.length > 0) {
                    this.positions = this.$store.state.positions;
                } else {
                    this.axios.get("/api/positions").then((data) => {
                        // this.$store.state.positions = data;
                        this.$store.commit("updatePosition", data);
                    });
                }
                if (this.$store.state.jobtitles.length > 0) {
                    this.jobtitles = this.$store.state.jobtitles;
                } else {
                    this.axios.get("/api/jobtitles").then((data) => {
                        this.$store.commit("updateJobtitle", data);
                    });
                }
                if (this.$store.state.departs.length > 0) {
                    this.departs = this.$store.state.departs;
                } else {
                    this.axios.get("/api/departs").then((data) => {
                        // this.$store.state.departs = data;
                        this.$store.commit("updateDepart", data);
                    });
                }
            },
            showDepart(){
                this.visible = !this.visible;
            }
        },
    };
</script>
