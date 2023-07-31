<script setup>

</script>

<template>
    <div class="grid min-h-screen place-content-center">
        <div class="h-9/10 w-full rounded-lg border-b-4 border-orange-300 bg-gradient-to-tl from-orange-100 to-orange-200/60 p-6">
            <div class="flex h-full flex-col justify-center">
                <div class="leading-relaxed text-slate-800 text-justify">
                    <h2 class="text-xl font-black text-slate-900">
                        Welcome to our cutting-edge interactive system designed to supercharge your technical interview preparation!
                    </h2>
                    <br>
                    <br>
                    <span class="text-md font-bold">
                        Are you ready to take your job interview skills to the next level?
                    </span>
                        Our innovative platform leverages the power of AI technologies to provide an unparalleled practice experience. With a unique combination of the ChatGPT API, AWS Polly, AWS Transcribe, and Deepgram Stream API, we have created a dynamic and engaging environment for job seekers like you.
                        Say goodbye to the days of static question banks and monotonous mock interviews. Our platform offers a personalized experience where you get to choose the topics you want to cover during your practice session. Whether it's algorithms and data structures, system design, or any other technical aspect, we've got you covered.
                    <br>
                    <br>
                        <span class="text-md font-bold">
                            Here's how it works:
                        </span>
                    Once you select your preferred topics, our intelligent system collaborates with ChatGPT API to generate tailor-made technical questions for you. To make the experience even more immersive, AWS Polly takes the text-based questions and converts them into lifelike speech, creating a truly interactive interview environment.
                    Our platform records your responses and, thanks to the magic of Deepgram Stream API, the real-time stream of your speech is transcribed and displayed back to you. It's like having your personal interview coach analyzing your performance on the spot.
                    After you've completed the practice session, your answers are sent back to ChatGPT for evaluation. ChatGPT provides valuable insights and constructive feedback, helping you identify areas for improvement and showcasing your strengths.
                    <br>
                    <br>
                    <span class="text-md font-bold">
                        Sign up now to embark on a transformative journey of interview preparation.
                    </span>
                    Equip yourself with the confidence and expertise to tackle any technical question that comes your way.
                    Join us today and experience the future of technical interview preparation.
                </div>
                <div class="flex">
                    <button class="rounded border-2 bg-orange-300 px-4 py-2 font-semibold text-slate-900 shadow">
                        <router-link to="/signup">Sign up</router-link>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped lang="css">

</style>