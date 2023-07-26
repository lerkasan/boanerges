<template>

    <nav id="header" class="w-full z-30 top-10 py-1 bg-white shadow-lg border-b border-blue-400 mt-10">
        <div class="w-full flex items-center justify-between mt-0 px-6 py-2">
            <label for="menu-toggle" class="cursor-pointer md:hidden block">
                <svg class="fill-current text-blue-600" xmlns="http://www.w3.org/2000/svg" width="20" height="10" viewBox="0 0 20 20">
                    <title>menu</title>
                    <path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z"></path>
                </svg>
            </label>
            <input class="hidden" type="checkbox" id="menu-toggle">

            <div class="hidden md:flex md:items-center md:w-auto w-full order-3 md:order-1" id="menu">
                <nav>
                    <ul class="md:flex items-center justify-between text-base text-blue-600 pt-4 md:pt-0">
                        <li>
                            <router-link to="/" class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2 ">
                                Home
                            </router-link>
                        </li>
                        <li><a class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2" href="#">Tech Interviews</a></li>
                        <li><a class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2" href="#">Recent Questions</a></li>
                        <li>
                            <router-link to="/books" class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2">
                                Books
                            </router-link>
                        </li>
                        <li><a class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2" href="#">About</a></li>
                        <li v-if="username">
                            <router-link to="/interview">
                                <button class="btn group flex items-center bg-transparent p-2 px-6 text-xl font-bold tracking-widest text-blue-900">
                                    <span class="relative pr-4 pb-1 text-blue-900 after:transition-transform after:duration-500 after:ease-out after:absolute after:bottom-0 after:left-0 after:block after:h-[2px] after:w-full after:origin-bottom-right after:scale-x-0 after:bg-blue-500 after:content-[''] after:group-hover:origin-bottom-left after:group-hover:scale-x-100">Start an interview</span>
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M4.26 10.147a60.436 60.436 0 00-.491 6.347A48.627 48.627 0 0112 20.904a48.627 48.627 0 018.232-4.41 60.46 60.46 0 00-.491-6.347m-15.482 0a50.57 50.57 0 00-2.658-.813A59.905 59.905 0 0112 3.493a59.902 59.902 0 0110.399 5.84c-.896.248-1.783.52-2.658.814m-15.482 0A50.697 50.697 0 0112 13.489a50.702 50.702 0 017.74-3.342M6.75 15a.75.75 0 100-1.5.75.75 0 000 1.5zm0 0v-3.675A55.378 55.378 0 0112 8.443m-7.007 11.55A5.981 5.981 0 006.75 15.75v-1.5" />
                                    </svg>
                                </button>
                            </router-link>
                        </li>
<!--                        <li v-if="user">-->
<!--                            <button class="group relative h-12 w-48 overflow-hidden rounded-lg bg-white text-lg shadow">-->
<!--                                <div class="absolute inset-0 w-3 bg-amber-400 transition-all duration-[250ms] ease-out group-hover:w-full"></div>-->
<!--                                <span class="relative text-black group-hover:text-white"> Start an interview </span>-->
<!--                            </button>-->
<!--                        </li>-->
                    </ul>
                </nav>
            </div>

            <div class="order-2 md:order-3 flex flex-wrap items-center justify-end mr-0 md:mr-4" id="nav-content">
                <div v-if="username">
                    <!--                    <div class="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600">-->
                    <!--                        <svg class="absolute w-12 h-12 text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20"-->
                    <!--                             xmlns="http://www.w3.org/2000/svg">-->
                    <!--                            <path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd">-->
                    <!--                            </path>-->
                    <!--                        </svg>-->
                    <!--                    </div>-->
                    <div class="auth flex items-center w-full md:w-full">
<!--                            <button class="group relative h-12 w-48 overflow-hidden rounded-lg bg-white text-lg shadow">-->
<!--                                <div class="absolute inset-0 w-3 bg-amber-400 transition-all duration-[250ms] ease-out group-hover:w-full"></div>-->
<!--                                <span class="relative text-black group-hover:text-white"> Start an interview </span>-->
<!--                            </button>-->
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z" />
                        </svg>
                        <button class="bg-transparent text-gray-800  p-2 rounded border border-gray-300 mr-4 hover:bg-gray-100 hover:text-gray-700">
                            <router-link to="/me">
                                Welcome, {{ username }}
                            </router-link>
                        </button>
                        <button class="
                            bg-blue-600
                            text-gray-200
                            p-2 rounded
                            hover:bg-blue-500
                            hover:text-white-100"
                                @click="logout"
                        >
                            Log out
                        </button>
                    </div>
                </div>
                <div v-else class="auth flex items-center w-full md:w-full">
                    <button class="bg-transparent text-gray-800  p-2 rounded border border-gray-300 mr-4 hover:bg-gray-100 hover:text-gray-700">
                        <router-link to="/login">Sign in</router-link>
                    </button>
                    <button class="bg-blue-600 text-gray-200  p-2 rounded  hover:bg-blue-500 hover:text-white-100">
                        <router-link to="/signup">Sign up</router-link>
                    </button>
                </div>
            </div>
        </div>
    </nav>

    <router-view v-slot="{ Component }">
        <suspense>
            <!--        <suspense timeout="0">-->
            <template #default>
                <component :is="Component" :key="$route.path"></component>
            </template>
            <template #fallback>
                <div>Loading...</div>
            </template>
        </suspense>
    </router-view>


    <!--    <router-view class="view one"></router-view>-->


<!--    <div class="w-1/2 mx-auto">-->
<!--        <div class="w-full shadow-2xl subpixel-antialiased rounded h-64 bg-black border-black mx-auto">-->
<!--            <div class="flex items-center h-6 rounded-t bg-gray-100 border-b border-gray-500 text-center text-black" id="headerTerminal">-->
<!--                <div class="flex ml-2 items-center text-center border-red-900 bg-red-500  hover:bg-red-400 shadow-inner rounded-full w-3 h-3" id="closebtn">-->
<!--                </div>-->
<!--                <div class="ml-2 border-yellow-900 bg-yellow-500 hover:bg-yellow-400 shadow-inner rounded-full w-3 h-3" id="minbtn">-->
<!--                </div>-->
<!--                <div class="ml-2 border-green-900 bg-green-500 hover:bg-green-400 shadow-inner rounded-full w-3 h-3" id="maxbtn">-->
<!--                </div>-->
<!--                <div class="mx-auto pr-16" id="terminaltitle">-->
<!--                    <p class="text-center text-sm">Terminal</p>-->
<!--                </div>-->

<!--            </div>-->
<!--            <div class="pl-1 pt-1 h-auto  text-green-200 font-mono text-xs bg-black" id="console">-->
<!--                <p class="pb-1">Last login: Wed Sep 25 09:11:04 on levil190</p>-->
<!--                <p class="pb-1">Laraben:Devprojects levin$<input type="text" class="bg-black text-blue-600 focus:border-0 focus:outline-0 ml-1" autofocus/></p>-->
<!--            </div>-->
<!--        </div>-->
<!--    </div>-->


<!--    <div class="w-1/2 mx-auto">-->
<!--        <div class="w-full shadow-2xl subpixel-antialiased rounded h-64 bg-black border-black mx-auto">-->
<!--            <div class="flex items-center h-6 rounded-t bg-gray-100 border-b border-gray-500 text-center text-black" id="headerTerminal">-->
<!--                <div class="flex ml-2 items-center text-center border-red-900 bg-red-500 shadow-inner rounded-full w-3 h-3" id="closebtn">-->
<!--                </div>-->
<!--                <div class="ml-2 border-yellow-900 bg-yellow-500 shadow-inner rounded-full w-3 h-3" id="minbtn">-->
<!--                </div>-->
<!--                <div class="ml-2 border-green-900 bg-green-500 shadow-inner rounded-full w-3 h-3" id="maxbtn">-->
<!--                </div>-->
<!--                <div class="mx-auto pr-16" id="terminaltitle">-->
<!--                    <p class="text-center text-sm">Terminal</p>-->
<!--                </div>-->

<!--            </div>-->
<!--            <div class="pl-1 pt-1 h-auto  text-green-200 font-mono text-xs bg-black" id="console">-->
<!--                <p class="pb-1">Last login: Wed Sep 25 09:11:04 on ttys002</p>-->
<!--                <p class="pb-1">Laraben:Devprojects laraben$</p>-->
<!--            </div>-->
<!--        </div>-->
<!--    </div>-->






    <!--    <RecordAudio/>-->
    <!--    <SignUpNew/>-->
    <!--    <Suspense>-->
    <!--        <SignUpNew/>-->
    <!--    </Suspense>-->
    <!--        <LoginForm/>-->
    <!--    </Suspense>-->
    <!--    <Suspense>-->
    <!--        <RecordAudio/>-->
    <!--    </Suspense>-->
    <!--    <Suspense>-->
    <!--        <HelloWorld/>-->
    <!--    </Suspense>-->
</template>

<script setup>

import {ref} from "vue";
import AuthService from "@/services/AuthService";

let username = ref('');
username.value = localStorage.getItem("username");
console.log("user on home page " + username.value);

async function logout() {
    await AuthService.logout();
}

</script>

<style scoped>
ul {
    display: flex;
    list-style: none;
    padding: 0;
    margin: 0;
}

li:not(:last-of-type) {
    margin-right: 1rem;
}
</style>