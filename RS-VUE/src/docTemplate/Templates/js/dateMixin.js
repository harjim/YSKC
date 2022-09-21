/*
 * @Author: ldx
 * @Date: 2021-05-21 14:59:53
 * @LastEditTime: 2021-07-06 16:10:14
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\js\dateMixin.js
 */
import { publicInfoObj } from './templateContentType'
export default {
  props: {
    stage: {
      type: Object,
      default: () => { return {} }
    }
  },
  computed: {
    year () {
      if (this.fileDate) {
        return this.fileDate.year()
      } else {
        return 0
      }
    }
  },
  watch: {
    docId () {
      this.hsaFileDataNull = false
      this.publicInfo = publicInfoObj()
    }
  },
  data () {
    return {
      employeeMap: {
        toCompile: undefined,
        audit: undefined,
        approval: undefined
      },
      publicInfo: publicInfoObj(),
      hsaFileDataNull: true, // true: 保存文档month时设置为undefined
      fileDate: undefined,
      BUfileDate: undefined
    }
  }
}
