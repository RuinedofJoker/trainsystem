import request from '@/utils/request'

//获取当前用户关注列表
export function getConcernList() {
    return request({
      url: '/concern/list',
      method: 'get'
    })
}

//获取当前用户私信列表
export function getMessageList() {
  return request({
    url: '/concern/message/list',
    method: 'get'
  })
}

//获取当前用户与目标用户所有私信
export function getHistoryMessage(receiverUserId) {
  return request({
    url: '/concern/message/history/' + receiverUserId,
    method: 'get'
  })
}