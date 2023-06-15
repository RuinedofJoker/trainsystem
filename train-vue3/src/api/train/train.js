import request from '@/utils/request'

export function getTrainTripList(data) {
  return request({
    url: '/train/trips/list',
    method: 'post',
    data: data
  })
}

//只是演示,get请求具体传参和拼接url要自己写
export function getTrainTripPrice(data) {
  return request({
    url: '/train/trips/price' + '?xxx&xxx',
    method: 'get',
  })
}

export function getCityInfo(data) {
  return request({
    url: '/train/city/info',
    method: 'get',
  })
}

export function getTrainStationList(data) {
  return request({
    url: '/train/station/list',
    method: 'get',
  })
}

export function getCityByName(data) {
  return request({
    url: '/train/city/getCityByName',
    method: 'get',
  })
}

export function getLikeData(query) {
  return request({
    url: `/city/fuzzy?fuzzyCity=${query}`,
  })
}
