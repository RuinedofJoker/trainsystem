import request from '@/utils/request'

//1.先在此组件里定义请求接口数据的方法
//获取省份
export function getProvince() {
    return request({
        url: '/province',
        method: "get",
    })
}

export function getAllProvinceCity() {
    return request({
        url: '/attractions/city',
        method: 'get'
    })
}
// //通过省份获取城市
export function getCityByProvinceName(provinceName) {
    return request({
        url: '/province/city?provinceName=' + provinceName,
        method: "get",
    })
}


//根据景点id获取景点详细信息
export function getAttractionsDetailById(attractionsId) {
    return request({
        url: `/attractions/detail/${attractionsId}`,
        // url:'/attractions/detail??attractionsId='+attractionsId,
        method: "get"
    })
}

//获取城市的推荐景点
export function getIntraduceAttraction(englishName) {
    return request({
        url: `/attractions/place/${englishName}`,
        method: "get"
    })
}

//根据城市englishName和图片fileld获取图片
export function getPicture(fileId) {
    return request({
        url: `/picture/${fileId}`,
        method: "get"
    })
}

//分页获取文章下的评论区的主评论
// commentType旅游攻略模块固定是1，articId就是attractionsId
export function getComment(commentType, articleId, pageNo, pageSize) {
    return request({
        url: `/comment/list/${commentType}/${articleId}?pageNo=${pageNo}&pageSize=${pageSize}`,
        method: "get"
    })
}

//首页游记
export function getEssay(num) {
    return request({
        url: `/attractions/essay/recommend?num=${num}`,
        method: "get"
    })
}

//查看用户信息
export function getUser(userId) {
    return request({
        url: `/user/info/${userId}`,
        method: "get"
    })
}

//添加游记
export function addEssay() {
    return request({
        url: '/attractions/essay/addEssay',
        method: "post"
    })
}

//上传图片
export function addPicture(file) {
    return request({
        url: '/attractions/essay/picture',
        method: "post",
        data: file
    })
}

//更新游记内容
export function updateEssayContent(data) {
    return request({
        url: '/attractions/essay/updateEssayContent',
        method: "put",
        data: data
    })
}

//删除游记
export function deleteEssay(essayId) {
    return request({
        url: `/attractions/essay/deleteEssay/${essayId}`,
        method: "delete",
    })
}

//发布游记
export function publishEssay(essayId) {
    return request({
        url: `/attractions/essay/publishEssay/${essayId}`,
        method: "put",
    })
}

//获取游记地点;query参数用?=
export function getAttractions(nums, search) {
    return request({
        url: `/attractions/essay/getAttractions?nums=${nums}&search=${search}`
    })
}

// //发布评论
// export function addCommennt(data) {
//     return request({
//         url: `/comment/addMainComment`,
//         data
//     })
// }
//用户添加评论
export function addComment(data) {
    return request({
        url: 'comment/addMainComment',
        method: 'post',
        data: data
    })
}

//获取游记
export function getoneEssay(essayId){
    return request({
        url:`/attractions/essay/detail/essay/${essayId}`,
        method:'get',
    })
}