import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'

import App from './App.vue'
import SignUp from "@/components/SignUp.vue";
import LoginForm from "@/components/LoginForm.vue";
// import HelloWorld from "@/components/HelloWorld.vue";
import InterviewPage from "@/components/InterviewPage.vue";
import SelectTopics from "@/components/SelectTopics.vue";
import RecentQuestions from "@/components/RecentQuestions.vue";
import UserInterviews from "@/components/UserInterviews.vue";
// import InterviewMain from "@/components/InterviewMainBad.vue";
// import InterviewMainOld from "@/components/InterviewPage.vue";
// import {Deepgram} from "@deepgram/sdk";
// import DeepgramStream from "@/components/DeepgramStream.vue";
// import InterviewStream from "@/components/InterviewMainBad.vue";
// import SignUpSuccess from "@/components/SignUpSuccess.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/signup', component: SignUp},
        // { path: '/signup-success', component: SignUpSuccess},
        { path: '/login', component: LoginForm},
        // { path: '/books', component: DeepgramStream},
        { path: '/topics', component: SelectTopics},
        { path: '/interview', component: InterviewPage},
        { path: '/questions', component: RecentQuestions},
        { path: '/interviews', component: UserInterviews},

    ]
});

const app= createApp(App)

app.use(router)

app.mount('#app')

// createApp(App).mount('#app')
