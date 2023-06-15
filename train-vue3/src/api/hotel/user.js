import request from "../../utils/request";
export function getUser(userId){
    return request({
        url: `user/info/${userId}`,
        method: 'get',
        // responseType:'blob',
    })
}
