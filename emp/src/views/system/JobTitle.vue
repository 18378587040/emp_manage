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
                    :disabled="jobTitleIdList.length == 0"
                    @click="deleteFlag = true"
            >
                批量删除
            </el-button>
            <!-- 条件筛选 -->
            <div style="margin-left:auto">
                <el-input
                        v-model="keywords"
                        prefix-icon="el-icon-search"
                        size="small"
                        placeholder="请输入职称名"
                        style="width:200px"
                        @keyup.enter.native="searchjobTitles"
                />
                <el-button
                        type="primary"
                        size="small"
                        icon="el-icon-search"
                        style="margin-left:1rem"
                        @click="searchjobTitles"
                >
                    搜索
                </el-button>
            </div>
        </div>
        <!-- 表格展示 -->
        <el-table
                size="mini"
                border
                :data="jobTitleList"
                @selection-change="selectionChange"
                v-loading="loading"
        >
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="编号" align="center" />
            <el-table-column prop="name" label="职位名" align="center" />
            <el-table-column prop="jobTitle" label="工作等级" align="center" />
            <el-table-column
                    prop="isEnabled"
                    label="是否启用"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.isEnabled == 1 ">已启用</el-tag>
                    <el-tag type="danger" v-else>未启用</el-tag>
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
                            title="确定删除吗？"
                            style="margin-left:1rem"
                            @confirm="deletejobTitle(scope.row.id)"
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
                <el-button type="primary" @click="deletejobTitle(null)">
                    确 定
                </el-button>
            </div>
        </el-dialog>
        <!-- 添加对话框 -->
        <el-dialog :visible.sync="addOrEdit" width="30%">
            <div class="dialog-title-container" slot="title" ref="jobTitleTitle" />
            <el-form label-width="80px" size="medium" :model="jobTitleForm">
                <el-form-item label="职称名">
                    <el-input style="width:250px" v-model="jobTitleForm.name" />
                </el-form-item>
                <el-form-item label="职称等级">
                    <el-input style="width:250px" v-model="jobTitleForm.jobTitle" />
                </el-form-item>
                <!-- 是否启用 -->
                <el-form-item label="是否启用">
                    <el-radio-group v-model="jobTitleForm.isEnabled">
                        <el-radio :label="1">显示</el-radio>
                        <el-radio :label="0">隐藏</el-radio>
                    </el-radio-group>
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
        created() {
            this.listjobTitles();
        },
        data: function() {
            return {
                loading: true,
                deleteFlag: false,
                addOrEdit: false,
                jobTitleIdList: [],
                jobTitleList: [],
                jobTitleForm: {
                    name: "",
                    jobTitle: "",
                    isEnabled: 1,
                },
                keywords: null,
                current: 1,
                size: 10,
                count: 0
            };
        },
        methods: {
            selectionChange(jobTitleList) {
                this.jobTitleIdList = [];
                jobTitleList.forEach(item => {
                    this.jobTitleIdList.push(item.id);
                });
            },
            searchjobTitles() {
                this.current = 1;
                this.listjobTitles();
            },
            sizeChange(size) {
                this.size = size;
                this.listjobTitles();
            },
            currentChange(current) {
                this.current = current;
                this.listjobTitles();
            },
            deletejobTitle(id) {
                var param = {};
                if (id == null) {
                    param = { data: this.jobTitleIdList };
                } else {
                    param = { data: [id] };
                }
                this.axios.delete("/api/admin/jobTitles", param).then(( data ) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listjobTitles();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.deleteFlag = false;
                });
            },
            openModel(jobTitle) {
                if (jobTitle != null) {
                    this.jobTitleForm = JSON.parse(JSON.stringify(jobTitle));
                    this.$refs.jobTitleTitle.innerHTML = "修改职称";
                } else {
                    this.jobTitleForm.name = "";
                    this.jobTitleForm.jobTitle = "";
                    this.jobTitleForm.isEnabled = 1;
                    this.$refs.jobTitleTitle.innerHTML = "添加职称";
                }
                this.addOrEdit = true;
            },
            addOrEditCategory() {
                if (this.jobTitleForm.name.trim() == "") {
                    this.$message.error("职位名不能为空");
                    return false;
                }
                if (this.jobTitleForm.jobTitle.trim() == "") {
                    this.$message.error("职称名不能为空");
                    return false;
                }
                this.axios.post("/api/admin/jobTitles", this.jobTitleForm).then((data) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listjobTitles();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.addOrEdit = false;
                });
            },
            listjobTitles() {
                this.axios
                    .get("/api/admin/jobTitles", {
                        params: {
                            current: this.current,
                            size: this.size,
                            keywords: this.keywords
                        }
                    })
                    .then((data) => {
                        this.jobTitleList = data.recordList;
                        this.count = data.count;
                        this.loading = false;
                    });
            }
        }
    };
</script>
