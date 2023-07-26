import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'

import App from './App.vue'
import SignUp from "@/components/SignUp.vue";
import LoginForm from "@/components/LoginForm.vue";
import HelloWorld from "@/components/HelloWorld.vue";
import InterviewMain from "@/components/InterviewMain.vue";
// import {Deepgram} from "@deepgram/sdk";
// import DeepgramStream from "@/components/DeepgramStream.vue";
// import InterviewStream from "@/components/InterviewMain.vue";
// import SignUpSuccess from "@/components/SignUpSuccess.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/signup', component: SignUp},
        // { path: '/signup-success', component: SignUpSuccess},
        { path: '/login', component: LoginForm},
        // { path: '/books', component: DeepgramStream},
        { path: '/books', component: HelloWorld},
        { path: '/interview', component: InterviewMain}
    ]
});

const app= createApp(App)

app.use(router)

app.mount('#app')

// createApp(App).mount('#app')
