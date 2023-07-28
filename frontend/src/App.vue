<template>
<!--    <Suspense>-->
        <HomePage/>
<!--    </Suspense>-->

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



import HomePage from "@/components/HomePage.vue";

export default {
    name: 'App',
    // components: {NavbarLink, Navbar},
    components: {
        HomePage
    }
    //     RecordAudio,
    //     // LoginForm
    //     // SignUpNew,
    //     // LoginForm,
    //     // HelloWorld,
    //     // RecordAudio,
    // }
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
