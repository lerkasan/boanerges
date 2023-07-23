<template>
    <div>
        <input type="text" v-model="user.username" placeholder="Username" />
        <input type="password" v-model="user.password" placeholder="Password" />
        <button @click="login">Login</button>
    </div>
</template>

<script setup>
import AuthService from "@/services/AuthService";
import {ref} from "vue";

let user = ref({
        username: "",
        password: "",
    });

async function login() {
    AuthService.login(user.value).then((response) => {
        if (response.data.token) {
            window.localStorage.clear();
            window.localStorage.setItem("jwtToken", response.data.token);
        }
        window.location.href = "/";
    });
}
</script>

<style scoped lang="css">

</style>