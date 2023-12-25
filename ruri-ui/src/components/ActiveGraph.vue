<template>
  <div class="container-card">
    <ul class="graph">
      <el-tooltip class="item" effect="dark" placement="top-start"
                  v-for="(item, index) in infos" :key="index"
                  :content="item.year + '-' + item.month + '-' + item.date">
        <li :data-level="item.level" class="li-day"></li>
      </el-tooltip>
    </ul>
    <ul class="months">
      <li class="li-month" v-for="(item,index) in monthBar" :key="index">{{ item }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      infos: [ ],
      current: {
        year: '',
        month: '',
        date: ''
      },
      monthBar: ["", "", "", "", "", "", "", "", "", "", "", ""],
    }
  },

  created() {
    this.init()
  },

  methods: {
    init() {
      let d = new Date()
      let day = d.getDay()
      let today = d.getDate()

      this.current.year = d.getFullYear()
      this.current.month = d.getMonth()
      this.current.date = d.getDate()

      let info = { }
      let month = ''
      let weekOfMonth = ''

      for (let i = 0; i < 84; i++) {
        d.setFullYear(this.current.year)
        d.setMonth(this.current.month)
        d.setDate(this.current.date)

        d.setDate(today - 77 - day + i)

        let level = Math.floor(Math.random() * 3)

        if (d.getMonth() === this.current.month && d.getDate() === this.current.date) {
          info = {
            year: d.getFullYear(),
            month: d.getMonth() + 1,
            date: d.getDate(),
            number: 10,
            level: level,
            isToday: true,
          }
          this.infos.push(info)
          return
        } else {
          info = {
            year: d.getFullYear(),
            month: d.getMonth() + 1,
            date: d.getDate(),
            number: 10,
            level: level,
            isToday: false,
          }
          this.infos.push(info)
        }
        if (d.getDate() === 1) {
          month = d.getMonth() + 1
          weekOfMonth = parseInt((i + 1) / 7)
          this.monthBar[weekOfMonth] = month + "月"
        }
      }
    }
  }
}
</script>

<style scoped>
.graph {
  display: grid;
  grid-template-columns: repeat(12, 21px);
  grid-template-rows: repeat(7, 21px);
  padding-inline-start: 0;
  grid-auto-flow: column;
  margin: 20px 20px 5px 20px;
}

.months {
  display: grid;
  grid-template-columns: repeat(12, 21px);
  grid-template-rows: 21px;
  font-size: 8px;
  color: #aaa;
  padding-inline-start: 0;
  margin: 5px 20px 5px 20px;
}

.li-month {
  display: inline-block;
}

.li-day {
  background-color: #eaeaea;
  list-style: none;
  margin: 2px;
  border-radius: 3px;
}

.li-day:hover {
  box-shadow: 0 0 5px rgb(57, 120, 255);
}

.graph li[data-level="1"] {
  background-color: #d9ecff;
}

.graph li[data-level="2"] {
  background-color: #8cc5ff;
}

.graph li[data-level="3"] {
  background-color: #409eff;
}
</style>