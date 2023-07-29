<script setup>


import apiClient from "@/services/AxiosInstance";
import {ref} from "vue";

const interviews = ref();

getUserInterviews();

async function getUserInterviews() {
    const response = await apiClient.get('/interviews');

    if (response.status !== 200) {
        const message = `An error has occurred: ${response.status}`;
        throw new Error(message);
    }
    interviews.value = response.data;
    return response.data;
}

</script>

<template>
    <!-- Card Section -->
    <div class="max-w-[85rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
        <!-- Grid -->
        <div v-if="interviews">
            <!-- Card -->

            <div v-for="interview in interviews" :key="interview.id"
                 class="checkbox-label group flex flex-col bg-white border shadow-sm rounded-xl hover:shadow-md transition dark:bg-slate-900 dark:border-gray-800">
                <div class="p-4 md:p-5">
                    <div class="flex justify-between items-center">
                        <div>
                            <div>
                                <div class="py-5">
                                    <details class="group">
                                        <summary
                                            class="flex justify-between items-center font-bold cursor-pointer list-none">
                                            <span> {{ interview.name }} </span>
                                            <span class="transition group-open:rotate-180">
                                                <svg fill="none" height="24" shape-rendering="geometricPrecision"
                                                     stroke="currentColor" stroke-linecap="round"
                                                     stroke-linejoin="round" stroke-width="1.5" viewBox="0 0 24 24"
                                                     width="24"><path d="M6 9l6 6 6-6"></path>
                                                </svg>
                                            </span>
                                        </summary>
                                        <div class="text-neutral-600 mt-3 group-open:animate-fadeIn">
                                            <div v-for="question in interview.questions" :key="question.id">
                                                <div
                                                    class="text-left px-8 mr-2 mb-6 py-6 bg-blue-400 rounded-bl-3xl rounded-tl-3xl rounded-tr-xl text-white">
                                                    <div class="wrapper question">
                                                        <div class="px=2">
                                                            {{ question.text }}
                                                        </div>
                                                        <div>
                                                            <audio controls class="text-left">
                                                                <source :src="question.audioUrl" type="audio/mpeg">
                                                            </audio>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div v-for="answer in question.answers" :key="answer.id">
                                                    <div
                                                        class="text-left px-8 mr-2 mb-6 py-6 bg-gray-400 rounded-br-3xl rounded-tr-3xl rounded-tl-xl text-white">
                                                        <div class="wrapper answer px=2">
                                                            <div class="px=2">
                                                                {{ answer.text }}
                                                            </div>
                                                            <div>
                                                                <audio controls class="text-left">
                                                                    <source :src="answer.audioUrl" type="audio/mpeg">
                                                                </audio>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div v-if="answer.feedback.text"
                                                         class="text-left px-8 mr-2 mb-6 py-6 bg-blue-400 rounded-bl-3xl rounded-tl-3xl rounded-tr-xl text-white">
                                                        <div class="wrapper question">
                                                            <div class="px=2">
                                                                {{ answer.feedback.text }}
                                                            </div>
                                                            <div>
                                                                <audio controls class="text-left">
                                                                    <source :src="answer.feedback.audioUrl"
                                                                            type="audio/mpeg">
                                                                </audio>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </details>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- End Card -->
        </div>
        <!-- clip-path with :after and :before can achieve this effect also -->
        <!-- End Grid -->
    </div>
    <!-- End Card Section -->
</template>

<style scoped lang="css">

.wrapper {
    display: grid;
    grid-template-columns: 6fr 1fr;
}

</style>