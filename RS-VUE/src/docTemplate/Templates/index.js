/*
 * @Author: ldx
 * @Date: 2020-12-30 08:27:20
 * @LastEditTime: 2021-01-04 11:43:14
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\index.js
 */
const resultComps = {}
const requireComps = require.context(
  './', // 当前文件下查找
  false, // 不遍历子文件夹
  /\.vue$/ // 正则匹配 .vue文件
)
requireComps.keys().forEach((fileName) => {
  const comp = requireComps(fileName)
  const name = fileName.replace(/^\.\/(.*)\.\w+$/, '$1')
  resultComps[name] = comp.default
})
export default resultComps
