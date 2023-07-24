<script setup>
import useVuelidate from '@vuelidate/core'
// import { required, email, minLength, sameAs } from '@vuelidate/validators'
import {required, email, minLength, sameAs} from '@vuelidate/validators'
import {computed, ref} from "vue";
// import { ExclamationCircleIcon } from '@heroicons/vue/24/solid'

const form = ref({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
    }
)

// const form = reactive({
//         firstName: '',
//         lastName: '',
//         email: '',
//         password: '',
//         confirmPassword: '',
//     }
// )

const rules = {
    firstName: {
        required, $autoDirty: true, name_validation: {
            $validator: validName,
            $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
        }
    },
    // lastName: {
    //     required, $autoDirty: true, */ name_validation: {
    //         $validator: validName,
    //         $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
    //     }
    // },
    email: {required, email},
    password: {required, min: minLength(8),
        password_validation: {
            $validator: validPassword,
            $message: "Weak password. Please at least one upper letter, one lower letter, one digit, one special symbol."
        }
    },
    // password: {
    //     passwordValue: {
    //         required, min: minLength(8),
    //         password_validation: {
    //             $validator: validPassword,
    //             $message: "Weak password. Please at least one upper letter, one lower letter, one digit, one special symbol."
    //         }
    //     },
    //     confirmPassword: {
    //         required, match_validation: {
    //             $validator: passwordMatch,
    //             $message: "Passwords don't match."
    //         }
    //     }
    // },
    confirmPassword: {required, sameAsPassword: sameAs(computed(()=> form.value.password), "password field")}
    // confirmPassword: {required, sameAsPassword: sameAs(v$.value.password)},
    // confirmPassword: { required, $autoDirty: true, sameAsPassword: sameAs(form.value.password) },
}

const v$ = ref(useVuelidate(rules, form));

// defineExpose({
//     form,
//     validations
// })

function validName(name) {
    let validNamePattern = new RegExp("^[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
    return validNamePattern.test(name);

}

function validPassword(password) {
    let containsUppercase = /[A-Z]/.test(password)
    let containsLowercase = /[a-z]/.test(password)
    let containsNumber = /[0-9]/.test(password)
    let containsSpecial = /[#?!@$%^&*-]/.test(password)
    return containsUppercase && containsLowercase && containsNumber && containsSpecial
}

// function passwordMatch(password) {
//     return password.password === password.confirmPassword;
// }

function register() {
    v$.value.$touch();
    if (v$.value.$error) return
    console.log(v$)

}

// const submitForm = async () => {
//     const result = await v$.value.$validate();
//     if(result) {
//         alert('success')
//     }else{
//         alert('error')
//     }
// }

// function validations() {
//         return {
//             form: {
//                 firstName: {
//                     required, name_validation: {
//                         $validator: validName,
//                         $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
//                     }
//                 },
//                 lastName: {
//                     required, name_validation: {
//                         $validator: validName,
//                         $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
//                     }
//                 },
//                 email: { required, email },
//                 password: { required, min: minLength(8) },
//                 confirmPassword: { required, sameAsPassword: sameAs('password') },
//             },
//         }
//     // },
// }
</script>

<template>
    <div class="wrapper">
        <div class="holder">
            <div class="detailBox">
                <div class="container-fluid h-100">

                    <!-- Main modal -->
                        <div class="justify-content-center align-items-center  w-min px-4 h-full ">
                            <!-- Modal content -->
                            <div class="justify-content-center align-items-center align-middle bg-white rounded-lg shadow-2xl  dark:bg-gray-700">

                    <div class="row justify-content-center align-items-center h-100">
                        <div class="col col-sm-6 col-md-6 col-lg-4 col-xl-4">
                            <!--        <form @submit="submitForm">-->
                            <form @submit.prevent="onSubmit" novalidate>
                                <!-- First and Last Name Row -->
                                <div class="row px-20">
                                    <div class="col-sm-6">
                                        <div class="form-group pt-6 pb-1">
                                            <!--                        <label for=""> First Name:</label><input class="form-control" placeholder="Enter first name" type="text" v-model="form.firstName">-->
                                            <div :class="{ 'hasError': v$.firstName.$error }">
                                                <label for=""> First Name:</label>
                                                <input class="bg-opacity-20 rounded border border-gray-600 bg-transparent py-1 px-3 text-base leading-8 outline-none transition-colors duration-200 ease-in-out focus:border-blue-500 focus:bg-transparent focus:ring-2 focus:ring-transparent"
                                                                                         :class="{'border-red-500 focus:border-red-500': v$.firstName.$error,
                                                                                        'border-[#42d392] ': !v$.firstName.$invalid}"
                                                                                         placeholder="Enter first name"
                                                                                         type="text"
                                                                                         @blur="v$.firstName.$touch"
                                                                                         v-model="v$.firstName.$model">
                                                <div class="pre-icon os-icon os-icon-user-male-circle"></div>
                                            </div>
                                            <!-- Error Message -->
                                            <div class="input-errors" v-for="(error, index) of v$.firstName.$errors"
                                                 :key="index">
                                                <!--                        <div class="input-errors" v-for="(error, index) of v$.form.firstName.$errors" :key="index">-->
                                                <div class="error-msg">{{ error.$message }}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <!-- Email Row -->
                                <div class="form-group p-1">
                                    <!--                <label for=""> Email address</label><input class="form-control" placeholder="Enter email" type="email" v-model="form.email">-->
                                    <label for=""> Email address</label><input class="bg-opacity-20 rounded border border-gray-600 bg-transparent py-1 px-3 text-base leading-8 outline-none transition-colors duration-200 ease-in-out focus:border-blue-500 focus:bg-transparent focus:ring-2 focus:ring-transparent"
                                                                               :class="{'border-red-500 focus:border-red-500': v$.email.$error,
                                                                                        'border-[#42d392] ': !v$.email.$invalid}"
                                                                               placeholder="Enter email" type="email"
                                                                               @blur="v$.email.$touch"
                                                                               v-model="v$.email.$model">
<!--                                    <div class="pre-icon os-icon os-icon-email-2-at2">-->
<!--                                    class="absolute right-2 h-full text-xl text-green-500"-->
<!--                                    <div class="absolute right-auto text-xl text-green-500">-->
<!--                                        <ExclamationCircleIcon-->
<!--                                        class="icon"-->
<!--                                        :class="{ 'text-green-500': !v$.email.$invalid, 'text-yellow-500': v$.email.$error }"-->
<!--                                        :name="`heroicons-solid:${!v$.email.$error ? 'check-circle' : 'exclamation'}`"-->
<!--                                        />-->
<!--                                    </div>-->


                                    <!-- Error Message -->
                                    <div v-if="v$.email.$error">
                                        <div class="input-errors" v-for="(error, index) of v$.email.$errors"
                                             :key="index">
                                            <div class="error-msg">{{ error.$message }}</div>
                                        </div>
                                    </div>
                                </div>


                                <!-- Password and Confirm Password Row -->
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group p-1">
                                            <!--                        <label for=""> Password</label><input class="form-control" placeholder="Password" type="password" v-model="form.password">-->
                                            <label for=""> Password</label><input class="bg-opacity-20 rounded border border-gray-600 bg-transparent py-1 px-3 text-base leading-8 outline-none transition-colors duration-200 ease-in-out focus:border-blue-500 focus:bg-transparent focus:ring-2 focus:ring-transparent"
                                                                                  :class="{'border-red-500 focus:border-red-500': v$.password.$error,
                                                                                        'border-[#42d392] ': !v$.password.$invalid}"
                                                                                  placeholder="Password" type="password"
                                                                                  @blur="v$.password.$touch"
                                                                                  v-model="v$.password.$model">
                                            <div class="pre-icon os-icon os-icon-fingerprint"></div>
                                            <!-- Error Message -->
                                            <div class="input-errors" v-for="(error, index) of v$.password.$errors"
                                                 :key="index">
                                                <div class="error-msg">{{ error.$message }}</div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group p-1">
                                            <!--                        <label for="">Confirm Password</label><input @input="checkPassword()" class="form-control" placeholder="Confirm Password" type="password" v-model="form.confirmPassword">-->
                                            <!--                        <label for="">Confirm Password</label><input @input="checkPassword()" class="form-control" placeholder="Confirm Password" type="password" v-model="v$.form.confirmPassword.$model">-->
                                            <label for="">Confirm Password</label><input class="bg-opacity-20 rounded border border-gray-600 bg-transparent py-1 px-3 text-base leading-8 outline-none transition-colors duration-200 ease-in-out focus:border-blue-500 focus:bg-transparent focus:ring-2 focus:ring-transparent"
                                                                                         :class="{'border-red-500 focus:border-red-500': v$.confirmPassword.$error,
                                                                                        'border-[#42d392] ': !v$.confirmPassword.$invalid}"
                                                                                         placeholder="Confirm Password"
                                                                                         type="password"
                                                                                         @blur="v$.confirmPassword.$touch"
                                                                                         v-model="v$.confirmPassword.$model">
                                            <!-- Error Message -->
                                            <div class="input-errors"
                                                 v-for="(error, index) of v$.confirmPassword.$errors" :key="index">
                                                <div class="error-msg">{{ error.$message }}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Submit Button -->
                                <div class="buttons-w pt-4 pb-6">
                                    <!--                <button class="btn btn-primary" style="margin-left:120px" @click="register">Signup</button>-->
                                    <!--                <button class="btn btn-primary" :disabled="v$.$invalid" style="margin-left:120px" @click="register">Signup</button>-->
<!--                                    <button class="sign-up" style="margin-left:120px" :disabled="v$.$invalid" @click="register">sign up</button>-->
<!--                                    :class="{'disabled:opacity-50': v$.$invalid}"-->
                                    <button class="rounded border-0 bg-blue-500 py-2 px-8 p-4 font-bold text-gray-50 transition-colors duration-500 disabled:opacity-50 hover:bg-blue-600 focus:outline-none"
                                            :disabled="v$.$invalid"
                                            @click="register">Sign up
                                    </button>
<!--                                    :disabled="v$.$invalid"-->
<!--                                                    <button class="btn btn-primary" :disabled="v$.$invalid" style="margin-left:120px" @click="register">Sign up</button>-->
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
                    </div>
            </div>
            </div>
        </div>
    </div>
</template>

<style scoped lang="css">

body {
    background: #212121;
}

.wrapper {
    height: 100%;
    width: 100%;
    overflow: hidden;
}

.holder {
    height: 50%;
    width: 100%;
    position: absolute;
    top: 25%;
    left: 0%;
}

.detailBox {
    height: 100%;
    font-size: 10;
    text-align: center;
}

h3, h2 {
    color: black;
    text-align: left;
    padding-bottom: 10px;
}

.sign-up {
    text-align: right;
}

.sign-up button {
    color: darkblue;
}


div.hasError input {
    border-color: red;
    border-width: 2px;
}

.input-errors {
    color: red;
    font-size: small;
}

label {
    font-size: 0;
}

.input-icons i {
    position: absolute;
}

.input-icons {
    width: 100%;
    margin-bottom: 10px;
}

.icon {
    padding: 10px;
    color: green;
    min-width: 50px;
    text-align: center;
}


</style>