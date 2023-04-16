const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: './',
  devServer: {
    port: 4000,
    open: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  },
  transpileDependencies: true
})
