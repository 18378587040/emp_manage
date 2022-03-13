<template>
    <el-card class="main-card">
        <div class="title">{{ this.$route.name }}</div>
        <!-- 表格操作 -->
        <div class="operation-container">
            <!-- 条件筛选 -->
            <div style="margin-left:auto">
                <el-input
                        v-model="keywords"
                        prefix-icon="el-icon-search"
                        size="small"
                        placeholder="请输入姓名"
                        style="width:200px"
                        @keyup.enter.native="searchLeaves"
                />
                <el-button
                        type="primary"
                        size="small"
                        icon="el-icon-search"
                        style="margin-left:1rem"
                        @click="searchLeaves"
                >
                    搜索
                </el-button>
            </div>
        </div>
        <!-- 表格展示 -->
        <el-table
                size="mini"
                border
                :data="leaveList"
                @selection-change="selectionChange"
                v-loading="loading"
        >
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="编号" align="center" />
            <el-table-column prop="name" label="姓名" align="center" />
            <el-table-column prop="reason" label="请假事由"
                             width="200"
                             align="center" />
            <el-table-column
                    prop="createTime"
                    label="开始时间"
                    width="140"
                    align="center"
            >
                <template slot-scope="scope">
                    <i class="el-icon-time" style="margin-right:5px" />
                    {{ scope.row.beginDate | date }}
                </template>
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="结束时间"
                    width="140"
                    align="center"
            >
                <template slot-scope="scope">
                    <i class="el-icon-time" style="margin-right:5px" />
                    {{ scope.row.endDate | date }}
                </template>
            </el-table-column>
            <el-table-column
                    prop="statys"
                    label="状态"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.status == 1 ">已同意</el-tag>
                    <el-tag type="danger" v-else-if="scope.row.status == 2">已拒绝</el-tag>
                    <el-tag type="danger" v-else-if="scope.row.status == 0">待审批</el-tag>
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
                    <el-button type="primary" size="mini" @click="agree(scope.row.id)">
                        同意
                    </el-button>
                    <el-popconfirm
                            title="确定拒绝吗？"
                            style="margin-left:1rem"
                            @confirm="refuse(scope.row.id)"
                    >
                        <el-button size="mini" type="danger" slot="reference">
                            拒绝
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
                <el-button type="primary" @click="deleteLeave(null)">
                    确 定
                </el-button>
            </div>
        </el-dialog>
        <!-- 添加对话框 -->
        <el-dialog :visible.sync="addOrEdit" width="30%">
            <div class="dialog-title-container" slot="title" ref="leaveTitle" />
            <el-form label-width="80px" size="medium" :model="leaveForm">
                <el-form-item label="申请姓名">
                    <el-input style="width:250px" v-model="leaveForm.name" />
                </el-form-item>
                <el-form-item label="请假事由">
                    <el-input style="width:250px" v-model="leaveForm.reason" />
                </el-form-item>
                <el-form-item label="开始时间">
                    <el-date-picker
                            v-model="leaveForm.beginDate"
                            type="datetime"
                            placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间">
                    <el-date-picker
                            v-model="leaveForm.endDate"
                            type="datetime"
                            placeholder="选择日期时间">
                    </el-date-picker>
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
        name: "LeaveApprove.vue",
        created() {
            this.listLeaves();
        },
        data: function() {
            return {
                loading: true,
                deleteFlag: false,
                addOrEdit: false,
                leaveIdList: [],
                leaveList: [],
                leaveForm: {
                    name: "",
                    reason: "",
                    beginDate: "",
                    endDate: "",
                },
                keywords: null,
                current: 1,
                size: 10,
                count: 0
            };
        },
        methods: {
            agree(id) {
                this.putRequest("/api/admin/approve/leave/?id="+ id + '&status=1')
                    .then((data) => {
                        if (data) {
                            this.listLeaves();
                        }
                    });
            },
            refuse(id) {
                this.putRequest("/api/admin/approve/leave/?id="+ id + '&status=2')
                    .then((data) => {
                        if (data) {
                            this.listLeaves();
                        }
                    });
                this.listLeaves();
            },
            selectionChange(leaveList) {
                this.leaveIdList = [];
                leaveList.forEach(item => {
                    this.leaveIdList.push(item.id);
                });
            },
            searchLeaves() {
                this.current = 1;
                this.listLeaves();
            },
            sizeChange(size) {
                this.size = size;
                this.listLeaves();
            },
            currentChange(current) {
                this.current = current;
                this.listLeaves();
            },
            deleteLeave(id) {
                var param = {};
                if (id == null) {
                    param = { data: this.leaveIdList };
                } else {
                    param = { data: [id] };
                }
                this.axios.delete("/api/admin/leaves", param).then(( data ) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listLeaves();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.deleteFlag = false;
                });
            },
            openModel(leave) {
                if (leave != null) {
                    this.leaveForm = JSON.parse(JSON.stringify(leave));
                    this.$refs.leaveTitle.innerHTML = "编辑申请";
                } else {
                    this.leaveForm.name = "";
                    this.leaveForm.reason = "";
                    this.leaveForm.beginDate = "";
                    this.leaveForm.endDate = "";
                    this.$refs.leaveTitle.innerHTML = "请假申请";
                }
                this.addOrEdit = true;
            },
            addOrEditCategory() {
                if (this.leaveForm.name.trim() == "") {
                    this.$message.error("申请姓名不能为空");
                    return false;
                }
                if (this.leaveForm.reason.trim() == "") {
                    this.$message.error("申请事由不能为空");
                    return false;
                }
                this.axios.post("/api/admin/leaves", this.leaveForm).then((data) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listLeaves();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.addOrEdit = false;
                });
            },
            listLeaves() {
                this.axios
                    .get("/api/admin/leaves", {
                        params: {
                            current: this.current,
                            size: this.size,
                            keywords: this.keywords
                        }
                    })
                    .then((data) => {
                        this.leaveList = data.recordList;
                        this.count = data.count;
                        this.loading = false;
                    });
            }
        }
    };
</script>