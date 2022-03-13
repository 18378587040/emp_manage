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
                调薪申请
            </el-button>
            <el-button
                    type="danger"
                    size="small"
                    icon="el-icon-delete"
                    :disabled="TransSalaryIdList.length == 0"
                    @click="deleteFlag = true"
            >
                批量撤回
            </el-button>
            <!-- 条件筛选 -->
            <div style="margin-left:auto">
                <el-input
                        v-model="keywords"
                        prefix-icon="el-icon-search"
                        size="small"
                        placeholder="请输入姓名"
                        style="width:200px"
                        @keyup.enter.native="searchTransSalarys"
                />
                <el-button
                        type="primary"
                        size="small"
                        icon="el-icon-search"
                        style="margin-left:1rem"
                        @click="searchTransSalarys"
                >
                    搜索
                </el-button>
            </div>
        </div>
        <!-- 表格展示 -->
        <el-table
                size="mini"
                border
                :data="TransSalaryList"
                @selection-change="selectionChange"
                v-loading="loading"
        >
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="编号" align="center" />
            <el-table-column prop="name" label="姓名" align="center" />
            <el-table-column prop="afterSalary" label="调薪前" align="center" />
            <el-table-column prop="beforeSalary" label="调薪后" align="center" />
            <el-table-column
                    prop="status"
                    label="状态"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.status == 1 ">审批通过</el-tag>
                    <el-tag type="danger" v-else-if="scope.row.status == 2">拒绝</el-tag>
                    <el-tag type="danger" v-else-if="scope.row.status == 0">审批中</el-tag>
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
            <!-- 列操作 -->
            <el-table-column label="操作" align="center" width="160">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="openModel(scope.row)">
                        编辑
                    </el-button>
                    <el-popconfirm
                            title="确定撤回吗？"
                            style="margin-left:1rem"
                            @confirm="deleteTransSalary(scope.row.id)"
                    >
                        <el-button size="mini" type="danger" slot="reference">
                            撤回
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
        <!-- 批量撤回对话框 -->
        <el-dialog :visible.sync="deleteFlag" width="30%">
            <div class="dialog-title-container" slot="title">
                <i class="el-icon-warning" style="color:#ff9900" />提示
            </div>
            <div style="font-size:1rem">是否撤回选中项？</div>
            <div slot="footer">
                <el-button @click="deleteFlag = false">取 消</el-button>
                <el-button type="primary" @click="deleteTransSalary(null)">
                    确 定
                </el-button>
            </div>
        </el-dialog>
        <!-- 添加对话框 -->
        <el-dialog :visible.sync="addOrEdit" width="30%">
            <div class="dialog-title-container" slot="title" ref="TransSalaryTitle" />
            <el-form label-width="80px" size="medium" :model="TransSalaryForm">
                <el-form-item label="申请姓名">
                    <el-input style="width:250px" v-model="TransSalaryForm.name" />
                </el-form-item>
                <el-form-item label="调薪前">
                    <el-input style="width:250px" v-model="TransSalaryForm.afterSalary" />
                </el-form-item>
                <el-form-item label="调薪后">
                    <el-input style="width:250px" v-model="TransSalaryForm.beforeSalary" />
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
    export default {
        name: "TransSalary.vue",
        created() {
            this.listTransSalarys();
        },
        data: function() {
            return {
                loading: true,
                deleteFlag: false,
                addOrEdit: false,
                TransSalaryIdList: [],
                TransSalaryList: [],
                TransSalaryForm: {
                    name: "",
                    afterSalary: "",
                    beforeSalary: "",
                },
                keywords: null,
                current: 1,
                size: 10,
                count: 0
            };
        },
        methods: {
            selectionChange(TransSalaryList) {
                this.TransSalaryIdList = [];
                TransSalaryList.forEach(item => {
                    this.TransSalaryIdList.push(item.id);
                });
            },
            searchTransSalarys() {
                this.current = 1;
                this.listTransSalarys();
            },
            sizeChange(size) {
                this.size = size;
                this.listTransSalarys();
            },
            currentChange(current) {
                this.current = current;
                this.listTransSalarys();
            },
            deleteTransSalary(id) {
                var param = {};
                if (id == null) {
                    param = { data: this.TransSalaryIdList };
                } else {
                    param = { data: [id] };
                }
                this.axios.delete("/api/admin/transsalarys", param).then(( data ) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listTransSalarys();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.deleteFlag = false;
                });
            },
            openModel(TransSalary) {
                if (TransSalary != null) {
                    this.TransSalaryForm = JSON.parse(JSON.stringify(TransSalary));
                    this.$refs.TransSalaryTitle.innerHTML = "编辑调薪";
                } else {
                    this.TransSalaryForm.name = "";
                    this.TransSalaryForm.afterSalary = "";
                    this.TransSalaryForm.beforeSalary = "";
                    this.$refs.TransSalaryTitle.innerHTML = "调薪申请";
                }
                this.addOrEdit = true;
            },
            addOrEditCategory() {
                if (this.TransSalaryForm.name.trim() == "") {
                    this.$message.error("申请姓名不能为空");
                    return false;
                }
                this.axios.post("/api/admin/transsalarys", this.TransSalaryForm).then((data) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listTransSalarys();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.addOrEdit = false;
                });
            },
            listTransSalarys() {
                this.axios
                    .get("/api/admin/transsalarys", {
                        params: {
                            current: this.current,
                            size: this.size,
                            keywords: this.keywords
                        }
                    })
                    .then((data) => {
                        this.TransSalaryList = data.recordList;
                        this.count = data.count;
                        this.loading = false;
                    });
            }
        }
    };
</script>