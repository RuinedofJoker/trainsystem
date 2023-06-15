import request from "../../utils/request";
export function getAllHotel(pageNo){
    return request({
        url: `hotel/list/101010100?pageNo=${pageNo}&pageSize=10`,
        method: 'get',
    })
}
