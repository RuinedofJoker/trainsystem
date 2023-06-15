import { defineStore } from "pinia";
import { getLikeData } from '@/api/train/train'
import { getHotCity } from "@/api/HotCity/hotCity";


const useTrainStore = defineStore('train', {
  state: () => ({
    'fromCity': '',
    'toCity': '',
    'fromDate': '',
    'toDate': '',
    cities: []
  }),
  actions: {
    saveDateArr(dates) {
      this.fromDate = dates[0].value
      this.toDate = dates[1].value
    },
    saveCityFn(citis) {
      this.fromCity = citis[0]
      this.toCity = citis[1]
    },
    async getCity(query) {
      const res = await getLikeData(query)
      // this.cities = res.data
      // console.log(res);
      this.cities = []
      let rowNum = 0
      let cNum = 0
      for (let i = 0; i < res.data.length; i++) {
        if (cNum == 0) {
          this.cities[rowNum] = new Array()
        }
        this.cities[rowNum][cNum] = res.data[i]
        if (cNum == 4) {
          cNum = 0
          rowNum++
          continue
        }
        cNum++
      }
    },
    async getOriginCities() {
      const res = await getHotCity()
      // this.cities = res.data
      let rowNum = 0
      let cNum = 0
      for (let i = 0; i < res.data.length; i++) {
        if (cNum == 0) {
          this.cities[rowNum] = new Array()
        }
        this.cities[rowNum][cNum] = res.data[i]
        if (cNum == 4) {
          cNum = 0
          rowNum++
          continue
        }
        cNum++
      }
    }
  }
})

export default useTrainStore