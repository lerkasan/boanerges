<template>
    <div class="m-4 mx-auto p-6 sm:p-6 lg:p-12">

        <!-- Carousel Body -->
        <div class="relative rounded-lg block md:flex items-center bg-gray-100 shadow-xl" style="min-height: 19rem;">
            <div class="relative w-full md:w-2/5 h-full overflow-hidden rounded-t-lg md:rounded-t-none md:rounded-l-lg" style="min-height: 19rem;">
                <div class="absolute inset-0 w-full h-full bg-indigo-900 opacity-75"></div>
                <div class="absolute inset-0 w-full h-full flex items-center justify-center fill-current text-white">
                <p v-if="feedbackAlreadyClicked" class="text-white pl-8 pr-14">{{ question }} </p>
                <p v-else class="text-white p-8 font-bold uppercase">{{ topic }} </p>
                </div>
            </div>
            <div class="w-full md:w-3/5 h-full flex items-center bg-gray-100 rounded-lg">
                <div class="p-12 md:pr-24 md:pl-16 md:py-12">
                    <p v-if="feedbackAlreadyClicked" class="text-gray-900 pb-4 text-justify" >{{ feedbackText }}</p>
                    <p v-else class="text-gray-900 pb-4" >{{ question }}</p>
                        <button v-if="loading" type="button" class="bg-indigo-400 h-max w-max justify-center items-center rounded-lg text-white font-bold hover:bg-indigo-300 hover:cursor-not-allowed duration-[500ms,800ms]" disabled>
                            <div class="flex items-center justify-center m-[10px]">
                                <div class="h-5 w-5 border-t-transparent border-solid animate-spin rounded-full border-white border-4"></div>
                                <div class="ml-2"> Loading... </div>
                            </div>
                        </button>
                    <div class="flex space-x-2 justify-center">
                        <div>
                            <button type="button"
                                    @click="startRecording"
                                    v-if="!loading"
                                    :disabled="loading || audioPlaying || recordingStatus || replayPlaying || feedbackAlreadyClicked"
                                    class="inline-block px-6 py-2 border-2 border-blue-600 text-blue-600 disabled:opacity-25 font-bold text-sm leading-tight uppercase rounded-full hover:bg-black hover:bg-opacity-5 focus:outline-none focus:ring-0 transition duration-150 ease-in-out">
                                <span v-if="replayAlreadyClicked">Retry</span>
                                <span v-else>Reply</span>
                            </button>
                        </div>
                        <div>
                            <button type="button"
                                    @click="stopRecording"
                                    v-if="!loading"
                                    :disabled="loading || audioPlaying || !recordingStatus || feedbackAlreadyClicked"
                                    class="inline-block px-6 py-2 border-2 border-blue-600 text-blue-600 disabled:opacity-25 font-bold text-sm leading-tight uppercase rounded-full hover:bg-black hover:bg-opacity-5 focus:outline-none focus:ring-0 transition duration-150 ease-in-out">
                                Stop
                            </button>
                        </div>
                        <div>
                            <button type="button"
                                    @click="replayAnswer"
                                    v-if="!loading"
                                    :disabled="loading || audioPlaying || recordingStatus || replayPlaying || !replyAlreadyClicked || feedbackAlreadyClicked"
                                    class="inline-block px-6 py-2 border-2 border-blue-600 text-blue-600 disabled:opacity-25 font-bold text-sm leading-tight uppercase rounded-full hover:bg-black hover:bg-opacity-5 focus:outline-none focus:ring-0 transition duration-150 ease-in-out">
                                Replay answer
                            </button>
                        </div>
                        <div>
                            <button type="button"
                                    @click="getFeedback"
                                    v-if="!loading"
                                    :disabled="loading || audioPlaying || recordingStatus || replayPlaying || !replyAlreadyClicked || feedbackAlreadyClicked"
                                    class="inline-block px-6 py-2 border-2 border-green-600 text-green-600 disabled:opacity-25 font-bold text-sm leading-tight uppercase rounded-full hover:bg-black hover:bg-opacity-5 focus:outline-none focus:ring-0 transition duration-150 ease-in-out">
                                Feedback
                            </button>
                        </div>
                    </div>
                </div>
                <svg class="hidden md:block absolute inset-y-0 h-full w-24 fill-current text-gray-100 -ml-12" viewBox="0 0 100 100" preserveAspectRatio="none">
                    <polygon points="50,0 100,0 50,100 0,100" />
                </svg>
            </div>
            <button
                :disabled="loading || audioPlaying || replayPlaying"
                @click="nextQuestion"
                class="absolute top-0 mt-32 right-0 bg-white rounded-full disabled:opacity-25 shadow-md h-12 w-12 text-2xl text-indigo-600 hover:text-green-860 focus:text-indigo-700 -mr-6 focus:outline-none focus:shadow-outline">
                <span class="block" style="transform: scale(1);">&#x279c;</span>
            </button>
            <button
                @click="endInterview"
                class="absolute top-0 mt-50 right-0 bg-white rounded-full disabled:opacity-25 shadow-md h-12 w-12 text-2xl text-indigo-600 hover:text-red-600 focus:text-red-600 -mr-6 focus:outline-none focus:shadow-outline">
                <span class="block" style="transform: scale(1);">&#x26CC;</span>
            </button>


        </div>
<!--Terminal-->
    <div class="w-full mx-auto pt-4">
        <div class="w-full shadow-2xl subpixel-antialiased rounded h-80 bg-black border-black mx-auto">
            <div class="flex items-center h-6 rounded-t bg-gray-100 border-b border-gray-500 text-center text-black" id="headerTerminal">
                <div class="flex ml-2 items-center text-center border-red-900 bg-red-500 shadow-inner rounded-full w-3 h-3" id="closebtn">
                </div>
                <div class="ml-2 border-yellow-900 bg-yellow-500 shadow-inner rounded-full w-3 h-3" id="minbtn">
                </div>
                <div class="ml-2 border-green-900 bg-green-500 shadow-inner rounded-full w-3 h-3" id="maxbtn">
                </div>
                <div class="mx-auto pr-16" id="terminaltitle">
                    <p class="text-center text-sm">Terminal</p>
                </div>

            </div>
            <div class="pl-1 pt-1 h-auto w-full text-left text-green-200 font-mono text-sm bg-black" id="console">
                <p class="pb-1">Last login: Wed Sep 25 09:11:04 on ttys002</p>
                <p class="pb-1">boanerges$</p>
                <span class="text-left text-sm" v-for="(transcript, i) in transcripts" :key="i">{{ transcript }}&nbsp;</span>
            </div>
        </div>
    </div>

</div>
    <div>
        <main>
            <div v-show="questionAudioUrl" className="audio-player">
                <audio id="question_audio">
                    <source :src="questionAudioUrl" :type="mimeType">
                </audio>
            </div>
            <div v-if="audioUrl" className="audio-player">
                <audio id="recorded_audio">
                    <source :src="audioUrl" :type="mimeType">
                </audio>
            </div>
        </main>
    </div>
</template>


<script setup>

import {ref} from "vue";
import { S3Client, PutObjectCommand } from "@aws-sdk/client-s3";
import apiClient from "@/services/AxiosInstance";

let stream;
let audioChunks = [];

let audioMime = {
    mimeType: 'audio/webm',
    audioBitsPerSecond: 16000,
}

const question = ref('');
const questionAudioUrl = ref('');
const loading = ref(true);
const audioPlaying = ref(false);
const replayAlreadyClicked = ref(false);
const replayPlaying = ref(false);
const replyAlreadyClicked = ref(false);
const feedbackAlreadyClicked = ref(false);
const topic = ref('');

const mediaRecorder = ref();
let socket;
const permission = ref(false);
const recordingStatus = ref(false);
const audioUrl = ref("");
const mimeType = ref(audioMime.mimeType);
let credentials;
let transcripts = ref([]);
let randomUuid;

let feedbackText = ref('');
let feedbackAudioUrl = ref('');
let feedbackScore = ref();

const region = process.env.VUE_APP_AWS_REGION;

getTranscribeCredentials();
await getMicrophonePermission();
await createInterview();
await getQuestion();

created();

function uuid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'
        .replace(/[xy]/g, function (c) {
            const r = Math.random() * 16 | 0,
                v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
}

async function createInterview() {
    let topicId = 10;
    apiClient.post("/interviews", {
        name: "My awesome interview-" + uuid(),
        topicId: topicId
    })
        .then(response => {
            window.localStorage.setItem("interviewId", response.data.id);
        })
        .catch(err => console.log("error " + err));
}

async function getFeedback() {
    feedbackAlreadyClicked.value = true;
    loading.value = true;

    let interviewId = window.localStorage.getItem('interviewId');
    let questionId = window.localStorage.getItem('questionId');
    let answerId = window.localStorage.getItem('answerId');

    if (interviewId !== null && questionId !== null && answerId !== null) {
        apiClient.post(`/interviews/${interviewId}/questions/${questionId}/answers/${answerId}/feedback`)
            .then(response => {
                feedbackText.value = response.data.text;
                feedbackAudioUrl.value = response.data.audioUrl;
                feedbackScore.value = response.data.score;

                let oldFeedbackAudio = document.getElementById("feedback_audio");
                if (oldFeedbackAudio !== undefined && oldFeedbackAudio !== null) {
                    oldFeedbackAudio.remove();
                }

                loading.value = false;
                let feedbackAudio = document.createElement('audio');
                feedbackAudio.id = "feedback_audio";
                feedbackAudio.src = response.data.audioUrl;
                feedbackAudio.controls = false;
                feedbackAudio.autoplay = false;
                audioPlaying.value = true;
                feedbackAudio.addEventListener("ended", () => {
                    audioPlaying.value = false;
                });
                audioPlaying.value = true;
                feedbackAudio.play();

            })
            .catch(err => console.log("error " + err));
    }
}

function endInterview() {
    topic.value = '';
    audioPlaying.value = false;
    replayAlreadyClicked.value = false;
    replyAlreadyClicked.value = false;
    feedbackAlreadyClicked.value = false;
    loading.value = true;
    question.value = '';
    questionAudioUrl.value = '';
    audioUrl.value = '';
    feedbackText.value = '';
    feedbackAudioUrl.value = '';
    feedbackScore.value = '';
    transcripts.value = [];
    let authToken = window.window.localStorage.getItem('jwtToken');
    let username = window.window.localStorage.getItem('username');
    window.localStorage.clear();
    window.localStorage.setItem('jwtToken', authToken);
    window.localStorage.setItem('username', username);
    window.location.href = "/";
}

async function nextQuestion() {

    topic.value = '';
    audioPlaying.value = false;
    replayAlreadyClicked.value = false;
    replyAlreadyClicked.value = false;
    feedbackAlreadyClicked.value = false;
    loading.value = true;
    question.value = '';
    questionAudioUrl.value = '';
    audioUrl.value = '';
    feedbackText.value = '';
    feedbackAudioUrl.value = '';
    feedbackScore.value = '';
    transcripts.value = [];

    await getQuestion();
}

function getRandomNumberLessThan(max) {
    return Math.floor(Math.random() * max);
}

async function getQuestion() {

    let selectedTopicsIds = JSON.parse(localStorage.getItem('selectedTopics'));
    let randomArrayIndex = getRandomNumberLessThan(selectedTopicsIds.length);
    let randomTopicId = selectedTopicsIds[randomArrayIndex];

    if (randomTopicId !== undefined) {
        apiClient.get(`/topics/${randomTopicId}`)
            .then(response => {
                topic.value = response.data.name;
            });

        apiClient.post(`/questions?topicId=${randomTopicId}`)
            .then(response => {
                question.value = response.data.text;
                questionAudioUrl.value = response.data.audioUrl;
                let questionAudio = document.getElementById("question_audio");
                if (questionAudio !== undefined && questionAudio !== null) {
                    loading.value = false;
                    questionAudio.src = response.data.audioUrl;
                    questionAudio.controls = false;
                    questionAudio.autoplay = false;
                    questionAudio.addEventListener("ended", () => {
                        audioPlaying.value = false;
                    });
                    audioPlaying.value = true;
                    questionAudio.play();
                }

                window.localStorage.setItem('currentQuestion', response.data.text);
                window.localStorage.setItem('currentQuestionAudio', response.data.audioUrl);

                saveQuestion(response.data.text, response.data.audioUrl);
            })
            .catch(err => console.log("error " + err));
    }

}

async function saveQuestion(text, audioUrl) {
    let interviewId = window.localStorage.getItem('interviewId');
    let question = {
        text: text,
        audioUrl: audioUrl
    }
    apiClient.post(`/interviews/${interviewId}/questions`, question)
        .then(response => {
            window.localStorage.setItem("questionId", response.data.id);
        })
        .catch(err => console.log("error " + err));
}

async function saveAnswer(text, audioUrl) {
    let interviewId = window.localStorage.getItem('interviewId');
    let questionId = window.localStorage.getItem("questionId");
    let answer = {
        text: text,
        audioUrl: audioUrl
    }
    apiClient.post(`/interviews/${interviewId}/questions/${questionId}/answers`, answer)
        .then(response => {
            window.localStorage.setItem("answerId", response.data.id);
            return response.data.id;
        })
        .catch(err => console.log("error " + err));
}

async function getMicrophonePermission() {
    if ("MediaRecorder" in window) {
        try {
            const mediaStream = await navigator.mediaDevices.getUserMedia({
                audio: true,
                video: false,
            });
            permission.value = true;
            stream = mediaStream;
        } catch (err) {
            alert(err.message);
        }
    } else {
        alert("The MediaRecorder API is not supported in your browser.");
    }
}

async function startRecording() {
    recordingStatus.value = true;
    replayAlreadyClicked.value = true;
    replyAlreadyClicked.value = true;

    transcripts.value = [];
    audioPlaying.value = false;
    audioUrl.value = '';

    await openWebSocket();

    mediaRecorder.value.ondataavailable = async (event) => {
        if (typeof event.data === "undefined") return;
        if (event.data.size === 0) return;
        audioChunks.push(event.data);

        if (socket.readyState === 1 && event.data.size > 0) {
            socket.send(event.data)
        }
    }
}

async function getDeepgramToken() {
    await apiClient.post('/deepgram/token').then(response => {
        if (response.status !== 200) {
            const message = `An error has occurred: ${response.statusText}`;
            throw new Error(message);
        }
        let deepgramToken = response.data.key;
        window.localStorage.setItem('deepgramToken', deepgramToken);
        return deepgramToken;
    })
}

async function stopRecording() {
    let s3PutCmd;
    let audioBlob;

    const s3Client = new S3Client({
        region,
        credentials
    });

    recordingStatus.value = false;

    if (mediaRecorder.value.state === 'recording') {
        mediaRecorder.value.stop();
    }

    socket.close();

    socket.onclose = async () => {
    audioBlob = new Blob(audioChunks, audioMime);
    audioUrl.value = URL.createObjectURL(audioBlob);

    audioChunks = [];
    randomUuid = uuid();

    s3PutCmd = new PutObjectCommand({
        Bucket: "boanerges-radio-voice",
        Key: "answerAudio-" + randomUuid + ".webm", // TODO TODO GENERATE UUID name
        Body: audioBlob,
    });


    try {
        const response = await s3Client.send(s3PutCmd);
        console.log(response);
    } catch (err) {
        console.error(err);
    }

    let answerS3Url = "https://boanerges-radio-voice.s3.amazonaws.com/answerAudio-" + randomUuid + ".webm";
    let answerText = transcripts.value.join(' ');

    await saveAnswer(answerText, answerS3Url);
    }
}

async function getTranscribeCredentials() {
    await apiClient.post('/sts').then(response => {
        if (response.status !== 200) {
            const message = `An error has occurred: ${response.statusText}`;
            throw new Error(message);
        }
        credentials = response.data;
        return response.data;
    });
}


async function created() {
    stream = await navigator.mediaDevices.getUserMedia({ audio: true })
        .catch(error => alert(error))

    if (!MediaRecorder.isTypeSupported('audio/webm')) return alert('Unsupported browser')
    mediaRecorder.value = new MediaRecorder(stream, { mimeType: 'audio/webm' })
}

async function openWebSocket() {
    const DG_URL = 'wss://api.deepgram.com/v1/listen?punctuate=true';
    await getDeepgramToken().then(() => {
        let token = window.localStorage.getItem('deepgramToken');
        socket = new WebSocket(DG_URL, ['token', token])
        socket.onopen = startStreaming;
        socket.onmessage = handleResponse;
        socket.onerror = (error) => {
            console.log("WebSocket error:", error);
        };
        socket.onclose = (event)  => {
            console.log("WebSocket connection closed:", event.code, event.status, event.message);
        };
    });
}

function startStreaming() {
    mediaRecorder.value.start(250);
    mediaRecorder.value.ondataavailable = async (event) => {
        if (typeof event.data === "undefined") return;
        if (event.data.size === 0) return;
        audioChunks.push(event.data);

        if (socket.readyState === 1 && event.data.size > 0) {
            socket.send(event.data)
        }
    }
}

function handleResponse(message) {
    const received = JSON.parse(message.data)
    const transcript = received.channel.alternatives[0].transcript
    if (transcript) {
        transcripts.value.push(transcript);
    }
}

function replayAnswer() {
    let recordedAnswer = document.getElementById("recorded_audio");
    replayPlaying.value = true;
    recordedAnswer.onended = () => {
        replayPlaying.value = false;
    }
    recordedAnswer.play();

}
</script>


<style scoped lang="css">

body {
    background-image: url('../../public/images/background.jpg');
    background-size: auto;
    background-repeat: no-repeat;
}

</style>