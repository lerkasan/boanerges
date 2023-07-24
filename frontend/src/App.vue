<template>
<!--    <Suspense>-->
        <SignUpNew/>
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
import SignUpNew from "@/components/SignUpNew.vue";

export default {
    name: 'App',
    components: {
        SignUpNew,
        // LoginForm,
        // HelloWorld,
        // RecordAudio,
    }
}
</script>

<style src="./app.css" lang="css"></style>
