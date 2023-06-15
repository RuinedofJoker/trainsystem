<template>
  <div id="max">
    <div id="bigBox">
      <div id="left">
        <div id="book">
          <book_hotel/>
        </div>

        <div id="ad">
            <test-ad/>
        </div>

        <div id="recommend">
          <recommend/>
        </div>

<!--        当季热推 旅游团-->
<!--        <div id="season">-->
<!--          <season/>-->
<!--        </div>-->

        <div id="map">
          <index-map/>
        </div>
      </div>

      <div id="right">
        <div id="swipe">
        <test-right/>
        </div>
      </div>

    </div>

    <!--  <div id="bottom"><h3>企业商旅部分</h3></div>-->
  </div>
</template>

<script>
import Recommend from "./components/recommend";
import Season from "./components/season";
import Book_hotel from "./components/book_hotel";
import TestRight from "./components/test-right";
import TestAd from "./components/test-ad";
import Hotel_map from "./hotelPlace/hotel_map";
import Map from "./components/map";
import IndexMap from "./components/indexMap";

export default {
  components: {IndexMap, Map, Hotel_map, TestAd, TestRight, Book_hotel, Season, Recommend}
}
</script>

<style scoped>
#max{
  margin: 0 auto;
  width: 100%;
  height: 2000px;
  padding: 10px;
  /*border: black 2px solid;*/
  background-color: white;
}
#bigBox {
  margin: 0 auto;
  padding: 0px;
  /*border: gold 4px solid;*/
  width: 1178px;
  /*background-color: rgba(255,255,255,0.7);*/
  /*border-radius: 15px;*/

  /*height: 1800px;*/
  /*overflow: hidden;*/
}
#left{
  float: left;
  /*border: green 2px solid;*/
}

#book {
  width: 740px;
  height: 280px;
  /*border: #f8ac59 1px solid;*/
  border-radius: 15px;
}

#ad{
  margin-top: 15px;
  width: 740px;
  height: 100px;
  background-color:lightgray;
  border-radius: 15px;
  overflow: hidden;
}
#recommend{
  margin-top: 20px;
  width: 740px;
  height: 350px;
  background-color: white;
}
#season{
  margin-top: 20px;
  width: 740px;
  height:590px;
  background-color:white;
  /*border: #7a6df0 1px solid;*/
}

#map{
  width: 740px;
  height: 400px;
}

#right{
margin: 0;
  width: 428px;
  height: 1400px;
  position: relative;
  left: 750px;
  max-height: 1200px;

}
#swipe{
  margin: 0;
  padding: 0;
  width: 428px;
  height: 680px;
  /*background-color: lightgreen;*/
  border-radius: 20px;
  position:fixed;
}
#bottom{
  width: 1200px;
  height: 400px;
  background-color: #FFDDBA;
}
</style>
