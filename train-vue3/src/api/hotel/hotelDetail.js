import request from "../../utils/request";
export function getHotelDetail(hotelId){
    return request({
        url: `hotel/detail/${hotelId}`,
        method: 'get',
    })
}
