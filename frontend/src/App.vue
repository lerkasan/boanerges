<template>
        <NavBar/>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
    name: 'App',
    components: {
        NavBar,
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
