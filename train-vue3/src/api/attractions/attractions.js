import request from '@/utils/request'

//商家或管理员添加attractions
export function addAttractionsFromBusiness(data) {
  return request({
    url: '/attractions',
    method: 'post',
    data
  })
}