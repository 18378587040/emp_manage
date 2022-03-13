let proxyObj = {}

proxyObj['/api'] = {
    //websocket
    ws: false,
    //目标地址
    target: 'http://localhost:8081',
    //发送请求头host会被设置target
    changeOrigin: true,
    //不重写请求地址
    pathReWrite: {
        '^/api':''
    }
}

module.exports = {
    devServer: {
        host:'localhost',
        port:8084,
        proxy: {
            "/api": {
                target: "http://localhost:8081",
                changeOrigin: true,
                pathRewrite: {
                    "^/api": ""
                }
            }
        },
        disableHostCheck: true
    },
    chainWebpack: config => {
        config.resolve.alias.set("@", resolve("src"));
    },
    lintOnSave: false
};

const path = require("path");
function resolve(dir) {
    return path.join(__dirname, dir);
}