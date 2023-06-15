<template>
<div class="s-max">
  <div class="s-left">
    <destination/>
  </div>
  <div class="s-right">
    <housing1/>

  </div>
</div>
</template>

<script setup>
import Housing1 from "./housing1";
import Destination1 from "./destination1";
import Destination from "../components/bookHotel/destination";


</script>

<style scoped>
.s-max{
  /*border: hotpink 2px solid;*/
  background-color: white;
  height: 110px;
  width: 100%;
}
.s-left{
  width: 50%;
  height: 92px;
  float: left;
  /*overflow: hidden;*/
}
.s-right{
  width: 50%;
  float: right;
}
</style>
