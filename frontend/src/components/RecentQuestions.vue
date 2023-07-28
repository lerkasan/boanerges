<script setup>


import apiClient from "@/services/AxiosInstance";
import {ref} from "vue";

const questions = ref();

getQuestions();

async function getQuestions() {
    const response = await apiClient.get('/questions');

    if (response.status !== 200) {
        const message = `An error has occurred: ${response.status}`;
        throw new Error(message);
    }
    questions.value = response.data;
    return response.data;
}

</script>

<template>
    <!-- Card Section -->
    <div class="max-w-[85rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
            <!-- Grid -->
                <div v-if="questions" class="grid grid-cols-2 gap-4">
                    <!-- Card -->

                    <div v-for="question in questions" :key="question.id" class="checkbox-label group flex flex-col bg-white border shadow-sm rounded-xl hover:shadow-md transition dark:bg-slate-900 dark:border-gray-800">
                        <div class="p-4 md:p-5">
                            <div class="flex justify-between items-center">
                                <div>
                                    <div class="group-hover:text-blue-600 font-normal text-gray-800 dark:group-hover:text-gray-400 dark:text-gray-200">
                                        {{ question }}
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
body {
    background-image: url('../../public/images/background.jpg');
    background-size: contain;
    background-repeat: no-repeat;
}
</style>