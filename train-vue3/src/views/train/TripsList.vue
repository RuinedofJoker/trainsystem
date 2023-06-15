<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="from" size="small" :inline="true">
      <el-form-item>
        <el-radio-group v-model="radio1" @change="change">
          <el-radio label="单程" />
          <el-radio label="往返" />
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="roleName">
        <city-box 
          class="cityBox" 
          label="出发城市" 
          placeholder="出发城市" 
          v-model="from.fromCity"
          @keyup.enter.native="handleQuery"
        />
        <!-- <el-input
          v-model="from.fromCity"
          placeholder="请输入出发地"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        /> -->
      </el-form-item>
      <el-form-item prop="roleKey">
        <city-box 
          class="cityBox" 
          label="到达城市" 
          placeholder="到达城市" 
          v-model="from.toCity"
          @keyup.enter.native="handleQuery"
        />
        <!-- <el-input
          v-model="from.toCity"
          placeholder="请输入目的地"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        /> -->
      </el-form-item>
      <el-form-item label="出发日">
        <el-date-picker
          v-model="from.fromDate"
          style="width: 240px"
          value-format="YYYY-MM-DD"
          type="date"
        />
      </el-form-item>
      <el-form-item label="返程日">
        <el-date-picker
          v-model="from.toDate"
          style="width: 240px"
          value-format="YYYY-MM-DD"
          type="date"
          :disabled="isd"
        />
      </el-form-item>
      <el-form-item>
        <el-radio-group v-model="radio2" @change="changeXue">
          <el-radio label="普通" />
          <el-radio label="学生" />
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格数据 -->
    <el-table v-loading="loading" :data="roleList[0]" @selection-change="handleSelectionChange" :key="stationTrainCode">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="出发地" prop="fromStationName" width="120" />
      <el-table-column label="目的地" prop="toStationName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="列车号" prop="stationTrainCode" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="历经时间" prop="elapseTime" width="100" />
      <el-table-column label="出发时间" align="center" prop="fromTime" width="100" />
      <el-table-column label="到达时间" align="center" prop="toTime" />
      <el-table-column label="是否今日到达" prop="isToday" />
    </el-table>

    <pagination
      v-model:page="from.pageNo"
      v-model:limit="from.pageSize"
      :page-sizes="[10, 15, 30, 50]"
      :total="roleBasicInfo.totalNum"
      @pagination="handlePageChange"
    />
  </div>
</template>

<script setup name="Train">
import Pagination from '@/components/Pagination'
import { getTrainTripList } from '@/api/train/train'
import { useRoute } from 'vue-router'
import CityBox from '@/components/TrainCityQuery/city-box.vue'

const route = useRoute()

const roleList = ref([])
const roleBasicInfo = ref({})

const loading = ref(false)
const showSearch = ref(true)

const isd = ref(true)

const radio1 = ref('单程')
const radio2 = ref('普通')
const from = ref(route.query)

// 改变pageNo和pageSize的类型，与接口一致
from.value.pageNo = parseInt(from.value.pageNo)
from.value.pageSize = parseInt(from.value.pageSize)
getList(from.value);


/** 查询角色列表 */
function getList(from) {
  loading.value = true
  
  getTrainTripList(from).then(res => {
    roleList.value.splice(0, roleList.value.length, res.data.data)
    roleBasicInfo.value = res.data
    Reflect.deleteProperty(roleBasicInfo.value,'data')
    loading.value = false
  }
  )
}
/** 搜索按钮操作 */
function handleQuery() {
  
  console.log(route.query);
  getList(from.value)
}
watch(
  () => route.query,
  () => {
    
  }
)

function change(t) {
  if (t === '单程') {
    isd.value = true
  } else {
    isd.value = false
  }
}

// 根据改变分页的数据  重新请求数据列表
function handlePageChange(newInfo) {
  from.value.pageNo = newInfo.page-1
  from.value.pageSize = newInfo.limit
  getList(from.value)
}
</script>

<style scoped>
.cityBox {
  padding-left: 80px;
}
</style>