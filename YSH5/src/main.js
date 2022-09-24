import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { VueAxios } from './utils/http'
import { createPinia } from 'pinia'
import { Form, Field, Cell, CellGroup, ActionBar, ActionBarIcon, ActionBarButton, Button, Toast, Row, Col, Icon } from 'vant'
import 'vant/lib/index.css'
const app = createApp(App)

app.use(createPinia())

app.use(VueAxios)
app.use(router)
// 注册组件
app.use(Form)
app.use(Button)
app.use(Field)
app.use(Cell)
app.use(CellGroup)
app.use(Row)
app.use(Col)
app.use(Icon)
app.use(ActionBar)
app.use(ActionBarIcon)
app.use(ActionBarButton)
app.use(Toast)

app.mount('#app')
