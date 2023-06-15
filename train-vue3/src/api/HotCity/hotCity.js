import request from '@/utils/request'

//
export function getHotCity() {
    return request({
        url: '/city/hotspot',
        method: 'get',
    })
}
