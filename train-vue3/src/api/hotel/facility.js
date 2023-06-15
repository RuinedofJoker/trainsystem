import request from "../../utils/request";
export function getFacility(){
    return request({
        url: 'hotel/detail/beijing_city_114304',
        method: 'get',
    })
}
