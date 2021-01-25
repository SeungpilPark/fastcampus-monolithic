const path = require('path')
process.env.VUE_APP_VUETIFY_VERSION = require('./node_modules/vuetify/package.json').version

module.exports = {
  devServer: {
    disableHostCheck: true,
    port: 8081,
    proxy: 'http://localhost:8080'
  },

  transpileDependencies: ['vuetify'],

  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: false,
    },
  },

  outputDir: '../web/dist/',
  assetsDir: './static/'
}
