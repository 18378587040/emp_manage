<template>
    <div style="width: 100%">
        <el-input
                prefix-icon="el-icon-search"
                placeholder="输入要搜索的部门名称..."
                v-model="filterText">
        </el-input>

        <el-tree
                :expand-on-click-node="false"
                :data="deps"
                :props="defaultProps"
                default-expand-all
                :filter-node-method="filterNode"
                ref="tree">
            <span class="custom-tree-node" slot-scope="{ node, data }" style="display: flex;justify-content: space-between;width: 100%">
            <span>{{ data.name }}</span>
            <span>
              <el-button
                      class="depBon"
                      type="primary"
                      size="mini"
                      @click="() => appendDep(data)">
                添加部门
              </el-button>
              <el-button
                      class="depBon"
                      type="danger"
                      size="mini"
                      @click="() => removeDep(data)">
                删除部门
              </el-button>
            </span>
          </span>
        </el-tree>
        <el-dialog
                title="添加部门"
                :visible.sync="dialogVisible"
                width="22%">
            <div>
                <el-tag>上级部门</el-tag>
                <el-input
                        disabled
                        class="depInput"
                        v-model="pname"
                        size="small">
                </el-input>
                <el-tag>部门名称</el-tag>
                <el-input
                        class="depInput"
                        placeholder="请输入部门名称..."
                        v-model="dep.name"
                        size="small">
                </el-input>
            </div>
            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addDep">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "DepManage",
        data(){
            return{
                filterText: '',
                deps: [],
                defaultProps :{
                    children: 'children',
                    label: 'name'
                },
                dialogVisible: false,
                pname: '',
                dep: {
                    name: '',
                    parentId: -1
                }
            }
        },
        mounted() {
            this.initDeps();
        },
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
        methods: {
            addDep2Deps(deps,dep){
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id == dep.parentId) {
                        d.children = d.children.concat(dep);
                        if (d.children.length > 0) {
                            d.isParent = true;
                        }
                        return ;
                    } else {
                        this.addDep2Deps(d.children, dep);
                    }
                }
            },
            addDep(){
                if (this.dep.name.trim() == "") {
                    this.$message.error("部门名称不能为空");
                    return false;
                }
                this.postRequest('/api/admin/departs',this.dep).then(resp => {
                    if (resp) {
                        this.addDep2Deps(this.deps,resp.obj);
                        this.dialogVisible = false;
                        this.dep.parentId = '';
                        this.dep.name = '';
                        this.pname = '';
                    }
                });
            },
            appendDep(data){
                this.pname = data.name;
                this.dep.parentId = data.id;
                this.dialogVisible = true;
            },
            removeDepFromDeps(p,deps, id){
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id == id) {
                        deps.splice(i, 1);
                        if (deps.length == 0) {
                            p.isParent = false;
                        }
                        return ;
                    } else {
                        this.removeDepFromDeps(d,d.children, id);
                    }
                }
            },
            removeDep(data){
                if (data.isParent) {
                    this.$message.error('父部门删除失败,有子部门！');
                } else {
                    this.$confirm('此操作将永久删除['+data.name+']部门, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.deleteRequest('/api/admin/departs/' + data.id).then(resp => {
                            if (resp) {
                                this.removeDepFromDeps(null,this.deps, data.id);
                            }
                        });
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }
            },
            initDeps(){
                this.getRequest('/api/admin/departs').then(resp => {
                    if (resp) {
                        this.deps = resp;
                    }
                });
            },
            filterNode(value, data){
                if (!value) {
                    return true;
                } else {
                    return data.name.indexOf(value) !== -1;
                }
            }
        }
    }
</script>

<style scoped>
    .depBon{
        padding: 2px;
    }
    .depInput{
        width: 230px;
        margin-left: 8px;
    }
</style>