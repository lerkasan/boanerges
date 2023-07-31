<script setup>


import apiClient from "@/services/AxiosInstance";
import {ref} from "vue";
import {maxLength, minLength, required} from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";

let renaming = ref(-1);
const interviews = ref();

const form = ref({
        name: ''
    }
)

const rules = {
    name: {
        required,
        min: minLength(3),
        max: maxLength(500),
        name_validation: {
            $validator: validName,
            $message: 'Only letters, spaces and digits are allowed'
        },
    }
}

const v$ = ref(useVuelidate(rules, form));

function validName(name) {
    let validNamePattern = new RegExp("^[a-zA-Z]+(?:[-'\\s][a-zA-Z0-9]+)*$");
    return validNamePattern.test(name);
}

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

async function deleteInterview(id) {
    await apiClient.delete(`/interviews/${id}`);
    window.location.href = "/interviews";
}

async function renameInterview(id) {
    if (renaming.value === -1) {
        renaming.value = id;
    } else {
        renaming.value = -1;
        if (form.value.name) {
            await apiClient.put(`/interviews/${id}?name=${form.value.name}`)
                .catch(err => console.log("error " + err));
            window.location.href = "/interviews";
        }
    }
}


</script>

<template>
    <!-- Card Section -->
    <div class="max-w-[85rem] px-4 py-10 sm:px-6 lg:px-8 lg:py-14 mx-auto">
        <!-- Grid -->
        <div v-if="!interviews">
            <button v-if="loading" type="button" class="bg-indigo-400 h-max w-max justify-center items-center rounded-lg text-white font-bold hover:bg-indigo-300 hover:cursor-not-allowed duration-[500ms,800ms]" disabled>
                <div class="flex items-center justify-center m-[10px]">
                    <div class="h-5 w-5 border-t-transparent border-solid animate-spin rounded-full border-white border-4"></div>
                    <div class="ml-2"> Loading... </div>
                </div>
            </button>
        </div>
        <div v-else-if="!interviews.length">
            <span class="font-bold text-xl bg-white px-2">
                No interviews
            </span>
        </div>
        <div v-else>
            <!-- Card -->

            <div v-for="interview in interviews" :key="interview.id"
                 class="checkbox-label group flex flex-col bg-white bg-opacity-70 border shadow-sm rounded-xl hover:shadow-md transition dark:bg-slate-900 dark:border-gray-800">
                <div class="p-2 md:p-3">
                    <div class="flex justify-between items-center">
                        <div>
                            <div>
                                <div class="py-2">
                                    <details class="group">
                                        <summary
                                            class="flex justify-between items-center font-bold cursor-pointer list-none">

                                            <div class="wrapper interview">
                                                <div class="text-left">
                                                    <span class="transition group-open:rotate-180">
                                                        <svg fill="none" height="24" shape-rendering="geometricPrecision"
                                                            stroke="currentColor" stroke-linecap="round"
                                                            stroke-linejoin="round" stroke-width="1.5" viewBox="0 0 24 24"
                                                            width="24"><path d="M6 9l6 6 6-6"></path>
                                                        </svg>
                                                    </span>
                                                    <span class="pl-16">
                                                        {{ interview.name }}
                                                    </span>
                                                </div>
                                                <div class="flex right-2 justify-center">

                                                    <div v-if="renaming === interview.id">
                                                        <div :class="{ 'hasError': v$.name.$error }">
                                                            <i
                                                                :class="{
                                                            'text-red-500': v$.name.$error,
                                                            'text-blue-500': !v$.name.$invalid
                                                        }">
                                                            </i>
                                                            <input
                                                                id="username"
                                                                v-model="v$.name.$model"
                                                                :class="{
                                                                    'border-red-500 focus:border-red-500': v$.name.$error,
                                                                    'border-[#42d392] ': !v$.name.$invalid
                                                                }"
                                                                class="
                                                                text-sm px-8 mx-8
                                                                placeholder-gray-500
                                                                flex right-2 justify-end
                                                                rounded-md border border-gray-400
                                                                focus:outline-none focus:border-blue-400"
                                                                name="username"
                                                                placeholder="Enter new name"
                                                                type="text"
                                                                @blur="v$.name.$touch"
                                                            >
                                                        </div>
                                                        <!-- Error Message -->
                                                        <div
                                                            v-for="(error, index) of v$.name.$errors"
                                                            :key="index"
                                                            class="input-errors mb-1 text-xs tracking-wide text-gray-600">
                                                            <span class="error-msg">{{ error.$message }}</span>
                                                        </div>
                                                    </div>

                                                    <div>
                                                        <span class="flex right-2 justify-end">
                                                            <button
                                                                class="inline-block mx-2 px-6 py-2 border-2 border-blue-600 text-blue-600 disabled:opacity-25 font-bold text-sm leading-tight uppercase rounded-full hover:bg-black hover:bg-opacity-5 focus:outline-none focus:ring-0 transition duration-150 ease-in-out"
                                                                @click="renameInterview(interview.id)">
                                                                    Rename
                                                            </button>
                                                            <button
                                                                class="inline-block mx-2 px-6 py-2 border-2 border-red-600 text-red-600 disabled:opacity-25 font-bold text-sm leading-tight uppercase rounded-full hover:bg-black hover:bg-opacity-5 focus:outline-none focus:ring-0 transition duration-150 ease-in-out"
                                                                @click="deleteInterview(interview.id)">
                                                                    Delete
                                                            </button>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
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
                                                            <audio class="text-left" controls>
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
                                                                <audio class="text-left" controls>
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
                                                                <audio class="text-left" controls>
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
        <!-- End Grid -->
    </div>
    <!-- End Card Section -->
</template>

<style lang="css" scoped>

.wrapper {
    display: grid;
    grid-template-columns: 6fr 1fr;
}


</style>