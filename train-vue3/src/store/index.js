import piniaPersist from 'pinia-plugin-persist'
import { createPinia } from 'pinia'
const store = createPinia()
store.use(piniaPersist)

export default store
