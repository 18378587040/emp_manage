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
                    :disabled="positionIdList.length == 0"
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
                        placeholder="请输入职位名"
                        style="width:200px"
                        @keyup.enter.native="searchPositions"
                />
                <el-button
                        type="primary"
                        size="small"
                        icon="el-icon-search"
                        style="margin-left:1rem"
                        @click="searchPositions"
                >
                    搜索
                </el-button>
            </div>
        </div>
        <!-- 表格展示 -->
        <el-table
                size="mini"
                border
                :data="positionList"
                @selection-change="selectionChange"
                v-loading="loading"
        >
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="编号" align="center" />
            <el-table-column prop="name" label="职位名" align="center" />
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
                            @confirm="deletePosition(scope.row.id)"
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
                <el-button type="primary" @click="deletePosition(null)">
                    确 定
                </el-button>
            </div>
        </el-dialog>
        <!-- 添加对话框 -->
        <el-dialog :visible.sync="addOrEdit" width="30%">
            <div class="dialog-title-container" slot="title" ref="positionTitle" />
            <el-form label-width="80px" size="medium" :model="positionForm">
                <el-form-item label="职位名">
                    <el-input style="width:250px" v-model="positionForm.name" />
                </el-form-item>
                <!-- 是否启用 -->
                <el-form-item label="是否启用">
                    <el-radio-group v-model="positionForm.isEnabled">
                        <el-radio :label="1">显示</el-radio>
                        <el-radio :label="0">隐藏</el-radio>
                    </el-radio-group>
                </el-form-item>
<!--                <el-switch-->
<!--                        style="margin-left: 25px"-->
<!--                        v-model="positionForm.isEnabled"-->
<!--                        active-color="#13ce66"-->
<!--                        inactive-color="#ff4949"-->
<!--                        :active-value="1"-->
<!--                        :inactive-value="0">-->
<!--                </el-switch>-->
<!--                <el-form-item label="是否启用">-->
<!--                    <el-input style="width:250px" v-model="positionForm.isEnabled" />-->
<!--                </el-form-item>-->
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
            this.listPositions();
        },
        data: function() {
            return {
                loading: true,
                deleteFlag: false,
                addOrEdit: false,
                positionIdList: [],
                positionList: [],
                positionForm: {
                    name: "",
                    isEnabled: 1,
                },
                keywords: null,
                current: 1,
                size: 10,
                count: 0
            };
        },
        methods: {
            selectionChange(positionList) {
                this.positionIdList = [];
                positionList.forEach(item => {
                    this.positionIdList.push(item.id);
                });
            },
            searchPositions() {
                this.current = 1;
                this.listPositions();
            },
            sizeChange(size) {
                this.size = size;
                this.listPositions();
            },
            currentChange(current) {
                this.current = current;
                this.listPositions();
            },
            deletePosition(id) {
                var param = {};
                if (id == null) {
                    param = { data: this.positionIdList };
                } else {
                    param = { data: [id] };
                }
                this.axios.delete("/api/admin/positions", param).then(( data ) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listPositions();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.deleteFlag = false;
                });
            },
            openModel(position) {
                if (position != null) {
                    this.positionForm = JSON.parse(JSON.stringify(position));
                    this.$refs.positionTitle.innerHTML = "修改职位";
                } else {
                    this.positionForm.name = "";
                    this.positionForm.isEnabled = 1;
                    this.$refs.positionTitle.innerHTML = "添加职位";
                }
                this.addOrEdit = true;
            },
            addOrEditCategory() {
                if (this.positionForm.name.trim() == "") {
                    this.$message.error("职位名不能为空");
                    return false;
                }
                this.axios.post("/api/admin/positions", this.positionForm).then((data) => {
                    if (data) {
                        this.$notify.success({
                            title: "成功",
                            message: "操作成功"
                        });
                        this.listPositions();
                    } else {
                        this.$notify.error({
                            title: "失败",
                            message: "操作失败"
                        });
                    }
                    this.addOrEdit = false;
                });
            },
            listPositions() {
                this.axios
                    .get("/api/admin/positions", {
                        params: {
                            current: this.current,
                            size: this.size,
                            keywords: this.keywords
                        }
                    })
                    .then((data) => {
                        this.positionList = data.recordList;
                        this.count = data.count;
                        this.loading = false;
                    });
            }
        }
    };
</script>
