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


    <div class="m-10 mx-auto p-16 sm:p-24 lg:p-48">

        <!-- Carousel Body -->
        <div class="relative rounded-lg block md:flex items-center bg-gray-100 shadow-xl" style="min-height: 19rem;">
            <div class="relative w-full md:w-2/5 h-full overflow-hidden rounded-t-lg md:rounded-t-none md:rounded-l-lg" style="min-height: 19rem;">
                <img class="absolute inset-0 w-full h-full object-cover object-center" src="https://stripe.com/img/v3/payments/overview/photos/missguided.jpg" alt="">
                <div class="absolute inset-0 w-full h-full bg-indigo-900 opacity-75"></div>
                <div class="absolute inset-0 w-full h-full flex items-center justify-center fill-current text-white">
                    <svg class="w-full h-24" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 239 120"><path d="M4.21 86.4V33.31h8.94l4 28.85.86 9.52.87-9.52 4-28.85h9.02V86.4h-5.19V42.83l-.87 7.22-5.19 36.35h-5.19l-5.2-36.93-.57-6.64V86.4zm35.79 0h6V33.31h-6zm114.24 0h6.06V33.31h-6.06zm46.16-24h5.48v-6.01h-5.48v-17h9.23v-6.08h-15.31V86.4h15.29v-6.06h-9.23zm-60-29.14v44.19a2.89 2.89 0 1 1-5.77 0V33.31h-6.34v44.14a9.23 9.23 0 1 0 18.46 0V33.31zm40.11 44.14V42.55a2.9 2.9 0 0 0-2.89-2.89h-2.88v41h2.88a3.68 3.68 0 0 0 2.89-3.18zm-3.21-44.09a9.12 9.12 0 0 1 9.23 9.24v34.9a9.12 9.12 0 0 1-9.23 9.24h-9.23V33.31h9.23m51.64 44.14v-34.9a2.89 2.89 0 0 0-2.88-2.89h-2.89v41h2.89a3.67 3.67 0 0 0 2.88-3.18zm-2.88-44.14a9.06 9.06 0 0 1 8.94 9.24v34.9a9.12 9.12 0 0 1-9.23 9.24h-9.23V33.31h9.52M89.31 57.55c-2.88-2.6-5.19-4.91-5.19-9.23v-5.77A2.89 2.89 0 0 1 87 39.66a3.1 3.1 0 0 1 2.89 2.89v6.05H96v-6.05a9.24 9.24 0 1 0-18.47 0v6.05c.58 6.93 4.62 10.68 7.5 13.56 2.89 2.6 5.2 4.91 5.2 9.24v6a2.89 2.89 0 1 1-5.77 0v-8.89h-6.11v8.94a9.23 9.23 0 1 0 18.46 0v-6c-.57-7.22-4.32-10.68-7.5-13.85m-25.1 0C61.33 55 59 52.64 59 48.32v-5.77a2.89 2.89 0 1 1 5.77 0v6.05h6.06v-6.05a9.24 9.24 0 1 0-18.47 0v6.05c0 6.93 4 10.68 6.93 13.56 2.88 2.6 5.19 4.91 5.19 9.24v6a2.89 2.89 0 0 1-2.88 2.89 3.1 3.1 0 0 1-2.89-2.89v-8.89h-5.46v8.94a9.23 9.23 0 1 0 18.46 0v-6c-.28-7.22-4.32-10.68-7.5-13.85m56.84-9.23v-5.82a9.24 9.24 0 1 0-18.47 0v34.9a9.45 9.45 0 0 0 9 9.24 6.63 6.63 0 0 0 6.34-4l2.89 4V62.45h-9.23v6.06h2.88v8.94a2.73 2.73 0 0 1-2.88 2.89 2.89 2.89 0 0 1-2.89-2.89v-34.9a2.9 2.9 0 0 1 2.89-2.89 3.1 3.1 0 0 1 2.88 2.89v6.05h6.64z"></path></svg>
                </div>
            </div>
            <div class="w-full md:w-3/5 h-full flex items-center bg-gray-100 rounded-lg">
                <div class="p-12 md:pr-24 md:pl-16 md:py-12">
                    <p class="text-gray-600"><span class="text-gray-900">Missguided</span> is a UK-based fashion retailer that has nearly doubled in size since last year. They integrated Stripe to deliver seamless checkout across mobile and web for customers in 100+ countries, all while automatically combating fraud.</p>
                    <a class="flex items-baseline mt-3 text-indigo-600 hover:text-indigo-900 focus:text-indigo-900" href="">
                        <span>Learn more about our users</span>
                        <span class="text-xs ml-1">&#x279c;</span>
                    </a>
                </div>
                <svg class="hidden md:block absolute inset-y-0 h-full w-24 fill-current text-gray-100 -ml-12" viewBox="0 0 100 100" preserveAspectRatio="none">
                    <polygon points="50,0 100,0 50,100 0,100" />
                </svg>
            </div>
            <button class="absolute top-0 mt-32 left-0 bg-white rounded-full shadow-md h-12 w-12 text-2xl text-indigo-600 hover:text-indigo-400 focus:text-indigo-400 -ml-6 focus:outline-none focus:shadow-outline">
                <span class="block" style="transform: scale(-1);">&#x279c;</span>
            </button>
            <button class="absolute top-0 mt-32 right-0 bg-white rounded-full shadow-md h-12 w-12 text-2xl text-indigo-600 hover:text-indigo-400 focus:text-indigo-400 -mr-6 focus:outline-none focus:shadow-outline">
                <span class="block" style="transform: scale(1);">&#x279c;</span>
            </button>
        </div>

        <!-- Carousel Tabs -->
        <div class="flex items-center pt-5 justify-between">
            <button class="px-2 opacity-50 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/kickstarter.svg" alt="" style="max-height: 60px;"></button>
            <button class="px-2 opacity-50 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/slack.svg" alt="" style="max-height: 60px;"></button>
            <button class="px-2 opacity-50 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/glossier.svg" alt="" style="max-height: 60px;"></button>
            <button class="px-2 opacity-50 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/charity_water.svg" alt="" style="max-height: 60px;"></button>
            <button class="px-2 opacity-100 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/missguided.svg" alt="" style="max-height: 60px;"></button>
        </div>

    </div>






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