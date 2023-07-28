<script setup>

import apiClient from "@/services/AxiosInstance";
import {ref} from "vue";

const result = await getTopics()
    .catch(error => {
        console.log(error.message);
    });

const topics = ref(result);

const selectedTopics = ref([]);

onload();

function onload() {
    let topicIds = window.localStorage.getItem('selectedTopics');
    if (topicIds !== undefined && topicIds !== null) {
        window.location.href = "/interview";
    }
}

async function getTopics() {
    const response = await apiClient.get('/topics');

    if (response.status !== 200) {
        const message = `An error has occurred: ${response.status}`;
        throw new Error(message);
    }
    window.localStorage.setItem('topics', JSON.stringify(response.data));
    return response.data;
}

function submitTopics() {
    // console.log(selectedTopics.value);
    window.localStorage.setItem('selectedTopics', JSON.stringify(selectedTopics.value));

    window.location.href = "/interview";

}

</script>

<template>
    <!-- Card Section -->
    <div class="max-w-[85rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
        <div>
            <h1 class="text-xl justify-center p-6 font-medium text-blue-600">
                Please, select several topics for your interview:
            </h1>
        </div>
        <form>
        <!-- Grid -->
            <fieldset>
                <div v-if="topics" class="grid sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-4 sm:gap-6">
            <!-- Card -->

                    <div v-for="topic in topics" :key="topic.id" class="checkbox-label group flex flex-col bg-white border-2 shadow-sm rounded-xl hover:shadow-md transition dark:bg-slate-900 dark:border-gray-800">
                        <div class="p-4 md:p-5">
                            <div class="flex justify-between items-center">
                                <div>
                                    <label class="group-hover:text-blue-600 font-semibold text-gray-800 dark:group-hover:text-gray-400 dark:text-gray-200">
                                        <input type="checkbox" name="topics" :id="topic.id" v-model="selectedTopics" :value="topic.id"/>
                                        {{ topic.name }}
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>

            <!-- End Card -->
                </div>
            </fieldset>

            <!-- clip-path with :after and :before can achieve this effect also -->
<!--            <button-->
<!--                @click="submitTopics"-->
<!--                class='btn w-48 h-16 cursor-pointer relative-->
<!--    after:absolute after:block after:w-full after:h-full after:rounded-xl-->
<!--    after:top-0 after:left-0 after:-z-10 text-white text-2xl font-medium rounded-xl-->
<!--	shadow-[0_4px_6px_-1px_rgba(0,0,0,.6)] transition-all hover:shadow-none duration-500-->
<!--'>-->
            <button
                @click="submitTopics"
                class="text-md font-medium
                      bg-blue-600
                      text-gray-200
                      p-2 rounded
                      hover:bg-blue-500
                      hover:text-white-100">
                Select Topics
            </button>

        </form>
        <!-- End Grid -->
    </div>
    <!-- End Card Section -->
</template>

<style scoped lang="css">
@tailwind base;
@tailwind components;
@tailwind utilities;

@layer components {
    .checkbox-label:has(input:checked) {
        @apply border-blue-600 border-2;
    }
}

[type="checkbox"]{
    width: 0;
    height: 0;
    opacity: 0;
}

body {
    background-image: url('../../public/images/background.jpg');
    background-size: contain;
    background-repeat: no-repeat;
}

</style>