import request from '@/utils/request'

//获取某个用户写的游记list
export function getUserEssayList(userId) {
    return request({
      url: `/attractions/essay/user/${userId}`,
      method: 'get'
    })
}