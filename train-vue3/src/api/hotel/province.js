import request from "../../utils/request";
export function getProvince() {
    return request({
        url: '/province',
        method: 'get',
    })
}
