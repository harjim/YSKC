import moment from 'moment'
const yearMiXin = {
  data () {
    return {
      monthOptions: [
        { value: '0', label: '一月' },
        { value: '1', label: '二月' },
        { value: '2', label: '三月' },
        { value: '3', label: '四月' },
        { value: '4', label: '五月' },
        { value: '5', label: '六月' },
        { value: '6', label: '七月' },
        { value: '7', label: '八月' },
        { value: '8', label: '九月' },
        { value: '9', label: '十月' },
        { value: '10', label: '十一月' },
        { value: '11', label: '十二月' }
      ],
      currentYear: this.$store.state.currentYear,
      currentMonth: 0,
      startMonth: 0,
      endMonth: 11,
      name: 'yearMiXin'
    }
  },
  created () {
  },
  mounted () { },
  watch: {
    '$store.state.currentYear' (y) {
      this.currentYear = y
      this.search(true)
    }
  },
  methods: {
    moment,
    search (re) {
      this.$getTree('rdDept', false, this.currentYear)
    }
  }
}
export default yearMiXin
