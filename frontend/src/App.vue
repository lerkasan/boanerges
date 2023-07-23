<template>
<!--    <Suspense>-->
        <SignUp/>
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
// import HelloWorld from "@/components/HelloWorld/HelloWorld";
// import RecordAudio from "@/components/Audio/RecordAudio";
// import LoginForm from "@/components/LoginForm.vue";
import SignUp from "@/components/SignUp.vue";

export default {
    name: 'App',
    components: {
        SignUp,
        // LoginForm,
        // HelloWorld,
        // RecordAudio,
    }
}
</script>

<style src="./app.css" lang="css"></style>
