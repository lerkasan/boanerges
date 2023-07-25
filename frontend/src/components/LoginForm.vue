<template>
<!--    <div>-->
<!--        <input type="text" v-model="user.username" placeholder="Username" />-->
<!--        <input type="password" v-model="user.password" placeholder="Password" />-->
<!--        <button @click="login">Login</button>-->
<!--    </div>-->



    <div
        class="min-h-screen flex flex-col items-center justify-center bg-gray-100">
        <div class="
          flex flex-col
          bg-white
          shadow-md
          px-4
          sm:px-6
          md:px-8
          lg:px-10
          py-8
          rounded-3xl
          w-96
          max-w-md">
            <div class="font-medium self-center text-2xl text-gray-800">
                Sign in
            </div>

            <div class="mt-10">
<!--                <form action="login" method="post">-->
                <form @change="resetLoginError">
                    <div class="flex flex-col mb-5">
                        <!--                        <label for="username" class="mb-1 text-sm tracking-wide text-gray-600">-->
                        <!--                            Username:-->
                        <!--                        </label>-->
                        <div class="relative">
                            <div :class="{ 'hasError': v$.username.$error }">
                                <div class="tooltip w-full">
                                    <span class="tooltiptext text-sm">Username</span>
                                    <div class="
                                        inline-flex
                                        items-center
                                        justify-center
                                        absolute
                                        left-0
                                        top-0
                                        h-full
                                        w-10
                                        text-gray-400"
                                    >
                                        <i class="fas fa-user absolute left-3.5 top-3"
                                           :class="
                                        {'text-red-500': v$.username.$error,
                                        'text-blue-500': !v$.username.$invalid}">
                                        </i>
                                    </div>
                                    <input
                                        id="username"
                                        type="text"
                                        name="username"
                                        class="
                                            text-sm
                                            placeholder-gray-500
                                            pl-10
                                            pr-4
                                            rounded-md
                                            border border-gray-400
                                            w-full
                                            py-2
                                            focus:outline-none focus:border-blue-400"
                                        :class="
                                            {'border-red-500 focus:border-red-500': v$.username.$error,
                                            'border-[#42d392] ': !v$.username.$invalid}"
                                        placeholder="Enter your username"
                                        @blur="v$.username.$touch"
                                        v-model="v$.username.$model"
                                    >
                                </div>
                            </div>
                            <!-- Error Message -->
                            <div class="input-errors mb-1 text-xs tracking-wide text-gray-600" v-for="(error, index) of v$.username.$errors"
                                 :key="index">
                                <div class="error-msg">{{ error.$message }}</div>
                            </div>
                        </div>
                    </div>

                    <div class="flex flex-col mb-6">
                        <!--                        <label for="password" class="mb-1 text-sm tracking-wide text-gray-600">-->
                        <!--                            Password:-->
                        <!--                        </label>-->
                        <div class="relative">
                            <div :class="{ 'hasError': v$.password.$error }">
                                <div class="tooltip w-full">
                                    <span class="tooltiptext text-sm">Password</span>
                                    <div class="
                                        inline-flex
                                        items-center
                                        justify-center
                                        absolute
                                        left-0
                                        top-0
                                        h-full
                                        w-10
                                        text-gray-400"
                                    >
                                        <i class="fas fa-lock absolute left-3.5 top-3"
                                           :class="
                                        {'text-red-500': v$.password.$error,
                                        'text-blue-500': !v$.password.$invalid}">
                                        </i>
                                    </div>
                                    <input
                                        id="password"
                                        type="password"
                                        name="password"
                                        class="
                                            text-sm
                                            placeholder-gray-500
                                            pl-10
                                            pr-4
                                            rounded-md
                                            border border-gray-400
                                            w-full
                                            py-2
                                            focus:outline-none focus:border-blue-400"
                                        placeholder="Enter your password"
                                        @blur="v$.password.$touch"
                                        v-model="v$.password.$model"
                                    />
                                </div>
                            </div>
                            <!-- Error Message -->
                            <div class="input-errors mb-1 text-xs tracking-wide text-gray-600" v-for="(error, index) of v$.password.$errors"
                                 :key="index">
                                <div class="error-msg">{{ error.$message }}</div>
                            </div>
                            <div class="input-errors error-msg">{{ loginError }}</div>
                        </div>
                    </div>

                    <div class="flex w-full">
                        <button
                            @click="login"
                            type="button"
                            class="
                                flex
                                mt-2
                                items-center
                                justify-center
                                focus:outline-none
                                text-white text-sm
                                sm:text-base
                                bg-blue-500
                                hover:bg-blue-600
                                rounded-md
                                py-2
                                w-full
                                transition
                                duration-150
                                ease-in
                                disabled:opacity-50"
                            :disabled="v$.$invalid"
                        >
                            <span class="mr-2 uppercase font-bold">Sign in</span>
                            <span>
                                <svg
                                    class="h-6 w-6"
                                    fill="none"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="2"
                                    viewBox="0 0 24 24"
                                    stroke="currentColor"
                                >
                                    <path
                                        d="M13 9l3 3m0 0l-3 3m3-3H8m13 0a9 9 0 11-18 0 9 9 0 0118 0z"
                                    />
                                </svg>
                            </span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <div class="flex justify-center items-center mt-6">
            <a
                href="#"
                target="_blank"
                class="
                inline-flex
                items-center
                text-gray-700
                font-medium
                text-sm text-center"
            >
                <span class="ml-2">Don't have an account?
                    <router-link class="text-sm ml-2 text-blue-500 font-bold" to="/signup">
                        Sign up
                    </router-link>
                </span>
            </a>
        </div>
    </div>

</template>

<script setup>
import AuthService from "@/services/AuthService";
import {ref} from "vue";
import {required} from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";
// import jwt from "jsonwebtoken";
// import {useRouter} from "vue-router";
// import apiClient from "@/services/AxiosInstance";

// let user = ref({
//     username: "",
//     password: "",
// });

let loginError = ref('');

const user = ref({
        username: '',
        password: '',
    }
)

const rules = {
    username: { required, $autoDirty: true },
    password: { required, $autoDirty: true }
}

const v$ = ref(useVuelidate(rules, user));

// const router = useRouter();

// decode the logged in user
function parseJwt(token) {
    if (!token) {
        return;
    }
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace("-", "+").replace("_", "/");
    return JSON.parse(window.atob(base64));
}

async function login() {
    try {
        AuthService.login(user.value).then((response) => {
            if (response !== undefined && response.status === 200) {
                if (response.data.token) {
                    window.localStorage.clear();
                    window.localStorage.setItem("jwtToken", response.data.token);
                    // loggedin user
                    const decodedJwt = parseJwt(response.data.token);
                    let user = decodedJwt.sub;
                    console.log(user);
                    window.localStorage.setItem("username", user);
                }
                // this.$router.push("/books");

                // router.push("/books");
                // router.push("/books");
                // window.location.href = "http://localhost:9090/api/v1/books";
                window.location.href = "/";
            } else {
                loginError.value = "User hasn't confirmed email or username/password is incorrect";
            }
        });
    } catch (error) {
        loginError.value = "User hasn't confirmed email or username/password is incorrect";
        console.error(error);
    }
    // await apiClient.get("/books").then((response) => console.log(response));
}

function resetLoginError() {
    loginError.value = '';
}

</script>

<style scoped lang="css">

@import url("https://use.fontawesome.com/releases/v5.15.4/css/all.css");

div.hasError input {
    border-color: red;
    border-width: 2px;
}

.input-errors {
    color: red;
    font-size: small;
}

.tooltip {
    position: relative;
    display: inline-block;
}

.tooltip .tooltiptext {
    visibility: hidden;
    width: 120px;
    background-color: #555;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -60px;
    opacity: 0;
    transition: opacity 0.3s;
}

.tooltip .tooltiptext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
    opacity: 1;
}

</style>