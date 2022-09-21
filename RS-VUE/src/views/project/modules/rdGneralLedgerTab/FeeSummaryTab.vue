<template>
  <div>
    <div style="position: relative;text-align:center">
      <b style="font-size:24px;">{{ isType ? '' : userInfo().companyName }}{{ dataMap.title }}归集费用</b>
    </div>
    <div style="overflow:auto">
      <table border style="border:solid #333 1px;width:100%;table-layer:fixed;">
        <tr class="tdHead">
          <td>{{ isType ? '项目' : '费用类型' }}</td>
          <td v-for="n in 12" :key="n">{{ year }}{{ n &lt; 10 ? `0${n}` : n }}</td>
          <td>合计</td>
        </tr>
        <tr class="tdHead" v-for="(item, idx) in dataMap.list" :key="idx">
          <td>{{ item.typeName }}</td>
          <td v-for="n in 12" :key="n">{{ (item[`month${n}`] || '') | NumberFormat }}</td>
          <td>{{ item.total | NumberFormat }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  props: {
    dataMap: {
      type: Object,
      default: () => { return { total: {}, data: [] } }
    },
    year: {
      type: Number,
      default: () => { return new Date().getFullYear() }
    },
    isType: {
      type: Boolean,
      default: () => { return true }
    }
  },
  methods: {
    ...mapGetters(['userInfo'])
  }
}
</script>

<style>
.tdHead {
  text-align: center;
  word-wrap: break-word;
}
.tdNum {
  text-align: right;
  word-wrap: break-word;
}
</style>
