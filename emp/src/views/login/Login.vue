<template>
    <div class="login-container">
        <div class="login-card">
            <div class="login-title">用户登录</div>
            <!-- 登录表单 -->
            <el-form
                    status-icon
                    :model="loginForm"
                    :rules="rules"
                    ref="loginForm"
                    class="login-form"
            >
                <!-- 用户名输入框 -->
                <el-form-item prop="username">
                    <el-input
                            v-model="loginForm.username"
                            prefix-icon="el-icon-user-solid"
                            placeholder="用户名"
                            @keyup.enter.native="login"
                    />
                </el-form-item>
                <!-- 密码输入框 -->
                <el-form-item prop="password">
                    <el-input
                            v-model="loginForm.password"
                            prefix-icon="iconfont el-icon-mymima"
                            show-password
                            placeholder="密码"
                            @keyup.enter.native="login"
                    />
                </el-form-item>
                <!-- 验证码输入框 -->
                <el-form-item prop="code" class="captcha-item">
                    <el-input
                            v-model="loginForm.code"
                            prefix-icon="el-icon-key"
                            placeholder="点击图片更换验证码"
                            style="width: 245px;margin-right: 5px;"
                            @keyup.enter.native="login"
                    />
                    <img :src="captchaUrl" @click="updateCaptcha"/>
                </el-form-item>
            </el-form>
            <!-- 登录按钮 -->
            <el-button type="primary" @click="submitLogin">登录</el-button>
        </div>
    </div>
</template>

<script>
    import {generaMenu} from "../../utils/menu";
    import {getRequest} from "../../utils/api";
    import store from "../../store";

    export default {
        name: "Login",
        data(){
            return{
                captchaUrl:'/api/captcha?time='+new Date(),
                loginForm: {
                    username: "zhou",
                    password: "123456",
                    code: "abcd"
                },
                rules: {
                    username: [
                        { required: true, message: "用户名不能为空", trigger: "blur" }
                    ],
                    password: [
                        { required: true, message: "密码不能为空", trigger: "blur" }
                    ],
                    code: [
                        { required: true, message: "验证码不能为空", trigger: "blur" }
                    ]
                }
            }
        },
        methods: {
            updateCaptcha() {
                this.captchaUrl = '/api/captcha?time=' + new Date();
            },
            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.postRequest('/api/login', this.loginForm).then(resp => {
                            if (resp) {
                                //存储用户token
                                const tokenStr = resp.obj.tokenHead + resp.obj.token;
                                window.sessionStorage.setItem('tokenStr', tokenStr);
                                // if (!window.sessionStorage.getItem('user')) {
                                //     //判断用户信息是否存在
                                //     this.getRequest('/api/admin/info').then(resp => {
                                //     if (resp) {
                                //         // 存入用户信息
                                //         store.commit('INIT_ADMIN',resp);
                                //         window.sessionStorage.setItem('user', JSON.stringify(resp));
                                //
                                //     }
                                //     });
                                // }
                                // 加载用户菜单
                                // generaMenu();
                                //页面跳转
                                // let path = this.$route.query.redirect;
                                // this.$router.replace((path == '/login' || path == undefined) ? '/' : path);
                                this.$router.push({ path: "/" });
                            }
                        });
                    } else {
                        this.$message.error('请输入所有字段!');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .login-container {
        position: absolute;
        top: 0;
        bottom: 0;
        right: 0;
        left: 0;
        background: url(https://img0.baidu.com/it/u=3391201033,1773105988&fm=253&fmt=auto&app=138&f=JPG?w=500&h=334) center center /
    cover no-repeat;
    }
    .login-card {
        position: absolute;
        top: 0;
        bottom: 0;
        right: 0;
        background: #fff;
        padding: 170px 60px 180px;
        width: 350px;
    }
    .login-title {
        color: #303133;
        font-weight: bold;
        font-size: 1rem;
    }
    .login-form {
        margin-top: 1.2rem;
    }
    .login-card button {
        margin-top: 1rem;
        width: 100%;
    }
    .captcha-item{
        display: flex;
        align-items: center;
    }
</style>
