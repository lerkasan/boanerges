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
                        <li><a class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2 " href="#">Home</a></li>
                        <li><a class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2" href="#">Tech Interviews</a></li>
                        <li><a class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2" href="#">Recent Questions</a></li>
                        <li><a class="inline-block no-underline hover:text-black font-medium text-lg py-2 px-4 lg:-ml-2" href="#">About</a></li>

                    </ul>
                </nav>
            </div>

            <div class="order-2 md:order-3 flex flex-wrap items-center justify-end mr-0 md:mr-4" id="nav-content">
                <div class="auth flex items-center w-full md:w-full">
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

    <router-view class="view one"></router-view>



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

<script>
// export default {
//     name: "App",
// };
// import HelloWorld from "@/components/HelloWorld/HelloWorld";
// import RecordAudio from "@/components/Audio/RecordAudio";
// import LoginForm from "@/components/LoginFormOld.vue";
// import SignUpNew from "@/components/SignUp.vue";
// import LoginForm from "@/components/LoginForm.vue";
// import RecordAudio from "@/components/RecordAudio.vue";

// import {Navbar, NavbarLink} from 'flowbite-vue';

export default {
    name: 'App',
    // components: {NavbarLink, Navbar},
    // components: {
    //     RecordAudio,
    //     // LoginForm
    //     // SignUpNew,
    //     // LoginForm,
    //     // HelloWorld,
    //     // RecordAudio,
    // }
}
</script>

<style src="./app.css" lang="css"></style>

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