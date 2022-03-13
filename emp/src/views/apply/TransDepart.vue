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
                部门调动申请
            </el-button>
            <el-button
                    type="danger"
                    size="small"
                    icon="el-icon-delete"
                    :disabled="TransDepartIdList.length == 0"
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
                        @keyup.enter.native="searchTransDeparts"
                />
                <el-button
                        type="primary"
                        size="small"
                        icon="el-icon-search"
                        style="margin-left:1rem"
                        @click="searchTransDeparts"
                >
                    搜索
                </el-button>
            </div>
        </div>
        <!-- 表格展示 -->
        <el-table
                size="mini"
                border
                :data="TransDepartList"
                @selection-change="selectionChange"
                v-loading="loading"
        >
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="编号" align="center" />
            <el-table-column prop="name" label="姓名" align="center" />
            <el-table-column prop="afterDepart" label="调动前部门" align="center" />
            <el-table-column prop="beforeDepart" label="调动后部门" align="center" />
            <el-table-column
                    prop="statys"
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
                            @confirm="deleteTransDepart(scope.row.id)"
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
                <el-button type="primary" @click="deleteTransDepart(null)">
                    确 定
                </el-button>
            </div>
        </el-dialog>
        <!-- 添加对话框 -->
        <el-dialog :visible.sync="addOrEdit" width="30%">
            <div class="dialog-title-container" slot="title" ref="TransDepartTitle" />
            <el-form label-width="80px" size="medium" :model="TransDepartForm">
                <el-form-item label="申请姓名">
                    <el-input style="width:250px" v-model="TransDepartForm.name" />
                </el-form-item>
                <el-form-item label="调动前">
                    <el-input style="width:250px" v-model="TransDepartForm.afterDepart" />
                </el-form-item>
                <el-form-item label="调动后">
                    <el-input style="width:250px" v-model="TransDepartForm.beforeDepart" />
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
        name: "TransDepart.vue",
        created() {
            this.listTransDeparts();
        },
        data: function() {
            return {
                loading: true,
                deleteFlag: false,
                addOrEdit: false,
                TransDepartIdList: [],
                TransDepartList: [],
                TransDepartForm: {
                    name: "",
                    afterDepart: "",
                    beforeDepart: "",
                },
                keywords: null,
                current: 1,
                size: 10,
                count: 0
            };
        },
        methods: {
            selectionChange(TransDepartList) {
                this.TransDepartIdList = [];
                TransDepartList.forEach(item => {
                    this.TransDepartIdList.push(item.id);
                });
            },
            searchTransDeparts() {
                this.current = 1;
                this.listTransDeparts();
            },
            sizeChange(size) {
                this.size = size;
                this.listTransDeparts();
            },
            currentChange(current) {
                this.current = current;
                this.listTransDeparts();
            },
            deleteTransDepart(id) {
                var param = {};
                if (id == null) {
                    param = { data: this.TransDepartIdList };
                } else {
                    param = { data: [id] };
                }
                this.axios.delete("/api/admin/transdeparts", param).then(( data ) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listTransDeparts();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.deleteFlag = false;
                });
            },
            openModel(TransDepart) {
                if (TransDepart != null) {
                    this.TransDepartForm = JSON.parse(JSON.stringify(TransDepart));
                    this.$refs.TransDepartTitle.innerHTML = "编辑调动";
                } else {
                    this.TransDepartForm.name = "";
                    this.TransDepartForm.reason = "";
                    this.TransDepartForm.afterDepart = "";
                    this.TransDepartForm.beforeDepart = "";
                    this.$refs.TransDepartTitle.innerHTML = "调动申请";
                }
                this.addOrEdit = true;
            },
            addOrEditCategory() {
                if (this.TransDepartForm.name.trim() == "") {
                    this.$message.error("申请姓名不能为空");
                    return false;
                }
                this.axios.post("/api/admin/transdeparts", this.TransDepartForm).then((data) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listTransDeparts();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.addOrEdit = false;
                });
            },
            listTransDeparts() {
                this.axios
                    .get("/api/admin/transdeparts", {
                        params: {
                            current: this.current,
                            size: this.size,
                            keywords: this.keywords
                        }
                    })
                    .then((data) => {
                        this.TransDepartList = data.recordList;
                        this.count = data.count;
                        this.loading = false;
                    });
            }
        }
    };
</script>