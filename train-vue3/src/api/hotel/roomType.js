import request from "../../utils/request";
export function getRoom(cityId){
    return request({
        url:`hotel/roomtype/${cityId}`,
        method: 'get',
    })
}
