import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'

import App from './App.vue'
import SignUp from "@/components/SignUp.vue";
import LoginForm from "@/components/LoginForm.vue";
import InterviewPage from "@/components/InterviewPage.vue";
import SelectTopics from "@/components/SelectTopics.vue";
import RecentQuestions from "@/components/RecentQuestions.vue";
import UserInterviews from "@/components/UserInterviews.vue";


const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/signup', component: SignUp},
        { path: '/login', component: LoginForm},
        { path: '/topics', component: SelectTopics},
        { path: '/interview', component: InterviewPage},
        { path: '/questions', component: RecentQuestions},
        { path: '/interviews', component: UserInterviews},

    ]
});

const app= createApp(App)

app.use(router)

app.mount('#app')
