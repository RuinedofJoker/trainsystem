import request from "../../utils/request";
export function getComment(hotelId){
    return request({
        url: `comment/list/4/${hotelId}?pageNo=0&pageSize=30`,
        method: 'get',
    })
}

//用户添加评论
export function addComment(data) {
    return request({
        url: 'comment/addMainComment',
        method: 'post',
        data: data
    })
}

//用户添加评论图片
export function addPicture(data){
    return request({
        url:'comment/picture',
        method: 'post',
        data: data
    })
}
