export function BMPGL(ak) {
    return new Promise(function (resolve, reject) {
        window.init = function () {
            resolve(BMapGL)
        }
        const script = document.createElement('script')
        script.type = 'text/javascript'
        script.src = `http://api.map.baidu.com/api?v=1.0&type=webgl&ak=${ak}&callback=init`
        script.onerror = reject
        document.head.appendChild(script)
    })
}
