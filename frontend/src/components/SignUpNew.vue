<script setup>
import useVuelidate from '@vuelidate/core'
import {required, email, minLength, sameAs} from '@vuelidate/validators'
import {computed, ref} from "vue";

const form = ref({
        username: '',
        firstName: '',
        email: '',
        password: '',
        confirmPassword: '',
    }
)

const rules = {
    username: {
        required, $autoDirty: true,
        name_validation: {
            $validator: validUsername,
            $message: 'Only letters and digits are allowed'
        }
    },
    firstName: {
        required, $autoDirty: true,
        name_validation: {
            $validator: validName,
            $message: 'Only letters, dashes and spaces are allowed'
        }
    },
    email: {required, $autoDirty: true, email},
    password: {required, $autoDirty: true,
        min: minLength(8),
        password_validation: {
            $validator: validPassword,
            $message: "At least one upper letter, one lower letter, one digit, one special symbol required"
        }
    },
    confirmPassword: {required, $autoDirty: true,
        sameAsPassword: sameAs(computed(()=> form.value.password), "password field")
    }
}

const v$ = ref(useVuelidate(rules, form));

function validName(name) {
    let validNamePattern = new RegExp("^[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
    return validNamePattern.test(name);

}

function validUsername(username) {
    let validNamePattern = new RegExp("^[a-zA-Z]+(?:[a-zA-Z0-9]+)*$");
    return validNamePattern.test(username);

}

function validPassword(password) {
    let containsUppercase = /[A-Z]/.test(password)
    let containsLowercase = /[a-z]/.test(password)
    let containsNumber = /[0-9]/.test(password)
    let containsSpecial = /[#?!@$%^&*-]/.test(password)
    return containsUppercase && containsLowercase && containsNumber && containsSpecial
}

function register() {
    v$.value.$touch();
    if (v$.value.$error) {
        alert("Invalid input in registration form");
        return
    }

    console.log(v$)
}

</script>

<template>

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
                Create an account
            </div>

            <div class="mt-10">
                <form action="#">
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

                    <div class="flex flex-col mb-5">
<!--                        <label for="firstName" class="mb-1 text-sm tracking-wide text-gray-600">-->
<!--                            First name:-->
<!--                        </label>-->
                        <div class="relative">
                            <div :class="{ 'hasError': v$.firstName.$error }">
                                <div class="tooltip w-full">
                                    <span class="tooltiptext text-sm">First name</span>
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
                                        {'text-red-500': v$.firstName.$error,
                                        'text-blue-500': !v$.firstName.$invalid}">
                                        </i>
                                    </div>
                                    <input
                                        id="firstName"
                                        type="text"
                                        name="firstName"
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
                                            {'border-red-500 focus:border-red-500': v$.firstName.$error,
                                            'border-[#42d392] ': !v$.firstName.$invalid}"
                                        placeholder="Enter your first name"
                                        @blur="v$.firstName.$touch"
                                        v-model="v$.firstName.$model"
                                    >
                                </div>
                            </div>
                            <!-- Error Message -->
                            <div class="input-errors mb-1 text-xs tracking-wide text-gray-600" v-for="(error, index) of v$.firstName.$errors"
                                 :key="index">
                                <div class="error-msg">{{ error.$message }}</div>
                            </div>
                        </div>
                    </div>

                    <div class="flex flex-col mb-5">
<!--                        <label for="email" class="mb-1 text-sm tracking-wide text-gray-600">-->
<!--                            Email:-->
<!--                        </label>-->
                        <div class="relative">
                            <div :class="{ 'hasError': v$.email.$error }">
                                <div class="tooltip w-full">
                                    <span class="tooltiptext text-sm">Email</span>
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
                                        <i class="fas fa-at absolute left-3.5 top-3"
                                           :class="
                                        {'text-red-500': v$.email.$error,
                                        'text-blue-500': !v$.email.$invalid}">
                                        </i>
                                    </div>
                                    <input
                                        id="email"
                                        type="email"
                                        name="email"
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
                                        placeholder="Enter your email"
                                        @blur="v$.email.$touch"
                                        v-model="v$.email.$model"
                                    />
                                </div>
                            </div>
                            <!-- Error Message -->
                            <div class="input-errors mb-1 text-xs tracking-wide text-gray-600" v-for="(error, index) of v$.email.$errors"
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
                        </div>
                    </div>

                    <div class="flex flex-col mb-6">
<!--                        <label for="confirmPassword" class="mb-1 text-sm tracking-wide text-gray-600">-->
<!--                            Confirm password:-->
<!--                        </label>-->
                        <div class="relative">
                            <div :class="{ 'hasError': v$.confirmPassword.$error }">
                                <div class="tooltip w-full">
                                    <span class="tooltiptext text-sm">Password confirmation</span>
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
                                        {'text-red-500': v$.confirmPassword.$error,
                                        'text-blue-500': !v$.confirmPassword.$invalid}">
                                        </i>
                                    </div>
                                    <input
                                        id="confirmPassword"
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
                                        placeholder="Confirm password"
                                        @blur="v$.confirmPassword.$touch"
                                        v-model="v$.confirmPassword.$model"
                                    />
                                </div>
                            </div>
                            <!-- Error Message -->
                            <div class="input-errors mb-1 text-xs tracking-wide text-gray-600" v-for="(error, index) of v$.confirmPassword.$errors"
                                 :key="index">
                                <div class="error-msg">{{ error.$message }}</div>
                            </div>
                        </div>
                    </div>

                    <div class="flex w-full">
                        <button
                            type="submit"
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
                            @click="register"
                        >
                            <span class="mr-2 uppercase font-bold">Sign up</span>
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
                <span class="ml-2">Already have an account?
                    <a
                        href="#"
                        class="text-sm ml-2 text-blue-500 font-semibold"
                    >
                        Login
                    </a>
                </span>
            </a>
        </div>
    </div>

</template>

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