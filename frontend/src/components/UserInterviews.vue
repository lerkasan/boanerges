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
        <div v-if="interviews" class="grid grid-cols-2 gap-4">
            <!-- Card -->

            <div v-for="interview in interviews" :key="interview.id" class="checkbox-label group flex flex-col bg-white border shadow-sm rounded-xl hover:shadow-md transition dark:bg-slate-900 dark:border-gray-800">
                <div class="p-4 md:p-5">
                    <div class="flex justify-between items-center">
                        <div>
<!--                            <div class="group-hover:text-blue-600 font-normal text-gray-800 dark:group-hover:text-gray-400 dark:text-gray-200">-->
<!--                                {{ interview.name }}-->
<!--                            </div>-->


                            <div class="grid divide-y divide-neutral-200 max-w-xl mx-auto mt-8">
                                <div class="py-5">
                                    <details class="group">
                                        <summary class="flex justify-between items-center font-medium cursor-pointer list-none">
                                            <span> {{ interview.name }} </span>
                                            <span class="transition group-open:rotate-180">
                                                <svg fill="none" height="24" shape-rendering="geometricPrecision" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" viewBox="0 0 24 24" width="24"><path d="M6 9l6 6 6-6"></path>
                                                </svg>
                                            </span>
                                        </summary>
                                        <div v-for="question in interview.questions" :key="question.id" class="text-neutral-600 mt-3 group-open:animate-fadeIn">
                                            {{ question.text }}}
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

</style>