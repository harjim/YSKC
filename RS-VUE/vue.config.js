const path = require('path')
const webpack = require('webpack')
const ThemeColorReplacer = require('webpack-theme-color-replacer')
// const CopyWebpackPlugin = require('copy-webpack-plugin')
const generate = require('@ant-design/colors/lib/generate').default
// const TerserPlugin = require('terser-webpack-plugin')
function resolve (dir) {
  return path.join(__dirname, dir)
}

// vue.config.js
module.exports = {
  configureWebpack: {
    // devtool: 'source-map',
    optimization: {
      // minimizer: [new TerserPlugin({
      //   terserOptions: {
      //     mangle: true, // 混淆
      //     compress: {
      //       drop_console: true, // 删除所有的console.*.
      //       drop_debugger: true // 删除debugger;
      //     }
      //   }
      // })],
      splitChunks: {
        cacheGroups: {
          commons: {
            chunks: 'all',
            minChunks: 2,
            maxInitialRequests: 5
          },
          //   minChunks: 2,
          //   maxInitialRequests: 5, // The default limit is too small to showcase the effect
          //    // This is example is too small to create commons chunks
          // },
          dataSet: {
            chunks: 'all',
            test: /data-set\\build/,
            name: 'data-set',
            enforce: true
          },
          antDesignVue: {
            chunks: 'all',
            test: /ant-design-vue/,
            name: 'ant-design-vue',
            enforce: true
          },
          antDesign: {
            chunks: 'all',
            test: /@ant-design/,
            name: 'ant-design',
            enforce: true
          },
          theme: {
            chunks: 'all',
            test: /theme.js/,
            name: 'theme',
            enforce: true
          },
          vxeTable: {
            chunks: 'all',
            test: /vxe-table/,
            name: 'vxe-table',
            enforce: true
          },
          xlsxStyle: {
            chunks: 'all',
            test: /xlsx-style/,
            name: 'xlsx-style',
            enforce: true
          },
          xlsxJs: {
            chunks: 'all',
            test: /xlsx.js/,
            name: 'xlsxJs',
            enforce: true
          },
          xlsxDist: {
            chunks: 'all',
            test: /xlsx\\dist/,
            name: 'xlsxDist',
            enforce: true
          }
        }
      }
    },
    plugins: [
      // new CopyWebpackPlugin([
      //   {
      //     from: path.resolve(__dirname, '../COMMON-VUE/static'),
      //     to: path.join(__dirname, 'dist'),
      //     ignore: ['.*']
      //   }
      // ]),
      // Ignore all locale files of moment.js
      new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
      // 生成仅包含颜色的替换样式（主题色等）
      // TODO 需要增加根据环境不开启主题需求
      new ThemeColorReplacer({
        fileName: 'css/theme-colors-[contenthash:8].css',
        matchColors: getAntdSerials('#1890ff'), // 主色系列
        // 改变样式选择器，解决样式覆盖问题
        changeSelector (selector) {
          switch (selector) {
            case '.ant-calendar-today .ant-calendar-date':
              return ':not(.ant-calendar-selected-date)' + selector
            case '.ant-btn:focus,.ant-btn:hover':
              return '.ant-btn:focus:not(.ant-btn-primary),.ant-btn:hover:not(.ant-btn-primary)'
            case '.ant-steps-item-process .ant-steps-item-icon > .ant-steps-icon':
              return null
            case '.ant-btn.active,.ant-btn:active':
              return '.ant-btn.active:not(.ant-btn-primary),.ant-btn:active:not(.ant-btn-primary)'
            case '.ant-menu-horizontal>.ant-menu-item-active,.ant-menu-horizontal>.ant-menu-item-open,.ant-menu-horizontal>.ant-menu-item-selected,.ant-menu-horizontal>.ant-menu-item:hover,.ant-menu-horizontal>.ant-menu-submenu-active,.ant-menu-horizontal>.ant-menu-submenu-open,.ant-menu-horizontal>.ant-menu-submenu-selected,.ant-menu-horizontal>.ant-menu-submenu:hover':
            case '.ant-menu-horizontal > .ant-menu-item-active,.ant-menu-horizontal > .ant-menu-item-open,.ant-menu-horizontal > .ant-menu-item-selected,.ant-menu-horizontal > .ant-menu-item:hover,.ant-menu-horizontal > .ant-menu-submenu-active,.ant-menu-horizontal > .ant-menu-submenu-open,.ant-menu-horizontal > .ant-menu-submenu-selected,.ant-menu-horizontal > .ant-menu-submenu:hover':
              return '.ant-menu-horizontal > .ant-menu-item-active,.ant-menu-horizontal > .ant-menu-item-open,.ant-menu-horizontal > .ant-menu-item-selected,.ant-menu-horizontal > .ant-menu-item:hover,.ant-menu-horizontal > .ant-menu-submenu-active,.ant-menu-horizontal > .ant-menu-submenu-open,.ant-menu-horizontal:not(.ant-menu-dark) > .ant-menu-submenu-selected,.ant-menu-horizontal:not(.ant-menu-dark) > .ant-menu-submenu:hover'
            default:
              return selector
          }
        }
      })
    ]
  },

  chainWebpack: config => {
    config.resolve.alias.set('@$', resolve('src'))
    config.externals({ './cptable': 'var cptable' })
    const svgRule = config.module.rule('svg')
    svgRule.uses.clear()
    svgRule
      .oneOf('inline')
      .resourceQuery(/inline/)
      .use('vue-svg-icon-loader')
      .loader('vue-svg-icon-loader')
      .end()
      .end()
      .oneOf('external')
      .use('file-loader')
      .loader('file-loader')
      .options({
        name: 'assets/[name].[hash:8].[ext]'
      })
    /* svgRule.oneOf('inline')
      .resourceQuery(/inline/)
      .use('vue-svg-loader')
      .loader('vue-svg-loader')
      .end()
      .end()
      .oneOf('external')
      .use('file-loader')
      .loader('file-loader')
      .options({
        name: 'assets/[name].[hash:8].[ext]'
      })
    */
  },

  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          /* less 变量覆盖，用于自定义 ant design 主题 */
          /*
          'primary-color': '#F5222D',
          'link-color': '#F5222D',
          'border-radius-base': '4px',
          */
        },
        javascriptEnabled: true
      }
    }
  },

  devServer: {
    // development server port 8000
    port: 8000,
    proxy: {
      '/api/doc/': {
        target: 'http://localhost:8088'
      },
      '/api/': {
        target: 'http://localhost:8001', // 代理地址，这里设置的地址会代替axios中设置的baseURL
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        // ws: true, // proxy websockets
        // pathRewrite方法重写url
        pathRewrite: {
          '^/api/': '/api/'
          // pathRewrite: {'^/api': '/'} 重写之后url为 http://192.168.0.6:8100/xxxx
          // pathRewrite: {'^/api': '/api'} 重写之后url为 http://192.168.0.6:8100/api/xxxx
        }
      }
    },
    contentBase: [
      path.join(__dirname, '../resource'),
      path.join(__dirname, '../resource/static')
    ]
    // proxy: {
    //   '/api': {
    //     // target: 'https://mock.ihx.me/mock/5baf3052f7da7e07e04a5116/antd-pro',
    //     target: 'https://mock.ihx.me/mock/5baf3052f7da7e07e04a5116/antd-pro',
    //     ws: false,
    //     changeOrigin: true
    //   }
    // }
  },

  // disable source map in production
  productionSourceMap: false,
  lintOnSave: undefined,
  // babel-loader no-ignore node_modules/*
  transpileDependencies: []
}

function getAntdSerials (color) {
  // 淡化（即less的tint）
  const lightens = new Array(9).fill().map((t, i) => {
    return ThemeColorReplacer.varyColor.lighten(color, i / 10)
  })
  const colorPalettes = generate(color)
  return lightens.concat(colorPalettes)
}
