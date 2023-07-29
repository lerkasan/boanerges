<template>
        <HomePage/>
</template>

<script>
import HomePage from "@/components/HomePage.vue";

export default {
    name: 'App',
    components: {
        HomePage
    }
}
</script>
<style>
body {
    background-image: url('../public/images/background.jpg');
    background-size: cover;
    background-repeat: no-repeat;
}
</style>
<style src="./app.css" lang="css"></style>
