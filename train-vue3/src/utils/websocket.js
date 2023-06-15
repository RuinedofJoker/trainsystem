import { getToken } from '@/utils/auth'
//封装websocket api
let client = null

const connectSocket = (topic) => {
    const baseUrl = import.meta.env.VITE_APP_WS_API
    const wsUrl = `ws://${location.host}${baseUrl}/${topic}`
    if (client) {
      return client
    } else {
      client = new WebSocket(wsUrl, getToken())
    }
    return client
}

export default connectSocket