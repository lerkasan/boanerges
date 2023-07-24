import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'

import App from './App.vue'
import SignUp from "@/components/SignUp.vue";
import LoginForm from "@/components/LoginForm.vue";
import HelloWorld from "@/components/HelloWorld.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/signup', component: SignUp},
        { path: '/login', component: LoginForm},
        { path: '/books', component: HelloWorld}
    ]
});

const app= createApp(App)

app.use(router)

app.mount('#app')

// createApp(App).mount('#app')
