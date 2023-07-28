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
                                        <div class="text-neutral-600 mt-3 group-open:animate-fadeIn">
                                            <table class="w-1/6 text-sm text-left text-gray-500 dark:text-gray-400">
                                                <tbody>
                                                    <tr v-for="question in interview.questions" :key="question.id" class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                                                        <td class="w-1/3 px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">
                                                            {{ question.text }}
                                                        </td>
                                                        <td class="w-1/12 px-6 py-4">
                                                            <audio controls>
                                                                <source :src="question.audioUrl" type="audio/mpeg">
                                                            </audio>
                                                        </td>
                                                        <td>
                                                            <table class="text-sm text-left text-gray-500 dark:text-gray-400">

                                                                <tbody>
                                                                <tr v-for="answer in question.answers" :key="answer.id"
                                                                    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                                                                    <td class="w-1/3 px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">
                                                                        {{ answer.text }}
                                                                    </td>
                                                                    <td class="w-1/12 px-6 py-4">
                                                                        <audio controls>
                                                                            <source :src="answer.audioUrl" type="audio/mpeg">
                                                                        </audio>
                                                                    </td>
                                                                    <td class="w-1/12 px-6 py-4 text-right">
                                                                        <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</a>
                                                                    </td>
                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>



<!--                                        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">-->

<!--                                            <tbody>-->
<!--                                                <tr v-for="answer in question.answers" :key="answer.id"-->
<!--                                                    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">-->
<!--&lt;!&ndash;                                                <td class="w-4 p-4">&ndash;&gt;-->
<!--&lt;!&ndash;                                                    <div class="flex items-center">&ndash;&gt;-->
<!--&lt;!&ndash;                                                        <input id="checkbox-table-search-1" type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">&ndash;&gt;-->
<!--&lt;!&ndash;                                                        <label for="checkbox-table-search-1" class="sr-only">checkbox</label>&ndash;&gt;-->
<!--&lt;!&ndash;                                                    </div>&ndash;&gt;-->
<!--&lt;!&ndash;                                                </td>&ndash;&gt;-->
<!--                                                <td class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">-->
<!--                                                    {{ answer.text }}-->
<!--                                                </td>-->
<!--                                                <td class="px-6 py-4">-->
<!--                                                    <audio controls>-->
<!--                                                        <source :src="answer.audioUrl" type="audio/mpeg">-->
<!--                                                    </audio>-->
<!--                                                </td>-->
<!--                                                <td class="px-6 py-4 text-right">-->
<!--                                                    <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</a>-->
<!--                                                </td>-->
<!--                                            </tr>-->
<!--                                            </tbody>-->
<!--                                        </table>-->



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