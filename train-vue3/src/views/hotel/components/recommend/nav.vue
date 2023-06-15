<template>
  <div id="app">
    <div
      id="allmap"
      ref="allmap"
    ></div>
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  name: 'nav',
  methods: {
    map () {
      let map = new BMapGL.Map(this.$refs.allmap) // 创建Map实例
      map.centerAndZoom(new window.BMapGL.Point(116.404, 39.915), 11) // 初始化地图,设置中心点坐标和地图级别
      map.addControl(new window.BMapGL.MapTypeControl({ // 添加地图类型控件
        mapTypes: [
          window.BMAP_NORMAL_MAP,
          window.BMAP_HYBRID_MAP
        ]
      }))
      map.setCurrentCity('北京') // 设置地图显示的城市 此项是必须设置的
      map.enableScrollWheelZoom(true)// 开启鼠标滚轮缩放
    }

  },
  mounted () {
    this.map()
  }
}
</script>
<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  border: #13ce66 2px solid;
}
#allmap {
  height: 500px;
  /*overflow: hidden;*/
}
</style>

