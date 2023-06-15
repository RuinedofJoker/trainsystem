import request from "../../utils/request";
export function getRecommend(){
    return request({
        url: `hotel/list/101010100?pageNo=8&pageSize=3`,
        method: 'get',
    })
}

export function getHotRecommend(pageNo){
    return request({
        url: `hotel/list/101010100?pageNo=${pageNo}&pageSize=3`,
        method: 'get',
    })
}

