import request from '@/utils/request'

//商家或管理员添加hotel
export function addHotelFromBusiness(data) {
    return request({
      url: '/hotel',
      method: 'post',
      data: data
    })
}