<template>
    <LoginFormNew/>
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
// import HelloWorld from "@/components/HelloWorld/HelloWorld";
// import RecordAudio from "@/components/Audio/RecordAudio";
// import LoginForm from "@/components/LoginForm.vue";
// import SignUpNew from "@/components/SignUpNew.vue";
import LoginFormNew from "@/components/LoginFormNew.vue";

export default {
    name: 'App',
    components: {
        LoginFormNew
        // SignUpNew,
        // SignUpNew,
        // LoginForm,
        // HelloWorld,
        // RecordAudio,
    }
}
</script>

<style src="./app.css" lang="css"></style>
