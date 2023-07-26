<template>
    <div class="m-10 mx-auto p-16 sm:p-24 lg:p-48">

        <!-- Carousel Body -->
        <div class="relative rounded-lg block md:flex items-center bg-gray-100 shadow-xl" style="min-height: 19rem;">
            <div class="relative w-full md:w-2/5 h-full overflow-hidden rounded-t-lg md:rounded-t-none md:rounded-l-lg" style="min-height: 19rem;">
                <img class="absolute inset-0 w-full h-full object-cover object-center" src="https://stripe.com/img/v3/payments/overview/photos/missguided.jpg" alt="">
                <div class="absolute inset-0 w-full h-full bg-indigo-900 opacity-75"></div>
                <div class="absolute inset-0 w-full h-full flex items-center justify-center fill-current text-white">
                    <svg class="w-full h-24" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 239 120"><path d="M4.21 86.4V33.31h8.94l4 28.85.86 9.52.87-9.52 4-28.85h9.02V86.4h-5.19V42.83l-.87 7.22-5.19 36.35h-5.19l-5.2-36.93-.57-6.64V86.4zm35.79 0h6V33.31h-6zm114.24 0h6.06V33.31h-6.06zm46.16-24h5.48v-6.01h-5.48v-17h9.23v-6.08h-15.31V86.4h15.29v-6.06h-9.23zm-60-29.14v44.19a2.89 2.89 0 1 1-5.77 0V33.31h-6.34v44.14a9.23 9.23 0 1 0 18.46 0V33.31zm40.11 44.14V42.55a2.9 2.9 0 0 0-2.89-2.89h-2.88v41h2.88a3.68 3.68 0 0 0 2.89-3.18zm-3.21-44.09a9.12 9.12 0 0 1 9.23 9.24v34.9a9.12 9.12 0 0 1-9.23 9.24h-9.23V33.31h9.23m51.64 44.14v-34.9a2.89 2.89 0 0 0-2.88-2.89h-2.89v41h2.89a3.67 3.67 0 0 0 2.88-3.18zm-2.88-44.14a9.06 9.06 0 0 1 8.94 9.24v34.9a9.12 9.12 0 0 1-9.23 9.24h-9.23V33.31h9.52M89.31 57.55c-2.88-2.6-5.19-4.91-5.19-9.23v-5.77A2.89 2.89 0 0 1 87 39.66a3.1 3.1 0 0 1 2.89 2.89v6.05H96v-6.05a9.24 9.24 0 1 0-18.47 0v6.05c.58 6.93 4.62 10.68 7.5 13.56 2.89 2.6 5.2 4.91 5.2 9.24v6a2.89 2.89 0 1 1-5.77 0v-8.89h-6.11v8.94a9.23 9.23 0 1 0 18.46 0v-6c-.57-7.22-4.32-10.68-7.5-13.85m-25.1 0C61.33 55 59 52.64 59 48.32v-5.77a2.89 2.89 0 1 1 5.77 0v6.05h6.06v-6.05a9.24 9.24 0 1 0-18.47 0v6.05c0 6.93 4 10.68 6.93 13.56 2.88 2.6 5.19 4.91 5.19 9.24v6a2.89 2.89 0 0 1-2.88 2.89 3.1 3.1 0 0 1-2.89-2.89v-8.89h-5.46v8.94a9.23 9.23 0 1 0 18.46 0v-6c-.28-7.22-4.32-10.68-7.5-13.85m56.84-9.23v-5.82a9.24 9.24 0 1 0-18.47 0v34.9a9.45 9.45 0 0 0 9 9.24 6.63 6.63 0 0 0 6.34-4l2.89 4V62.45h-9.23v6.06h2.88v8.94a2.73 2.73 0 0 1-2.88 2.89 2.89 2.89 0 0 1-2.89-2.89v-34.9a2.9 2.9 0 0 1 2.89-2.89 3.1 3.1 0 0 1 2.88 2.89v6.05h6.64z"></path></svg>
                </div>
            </div>
            <div class="w-full md:w-3/5 h-full flex items-center bg-gray-100 rounded-lg">
                <div class="p-12 md:pr-24 md:pl-16 md:py-12">
                    <p class="text-gray-900 pb-4" >{{ question }}</p>
<!--                    <div v-if="loading" class='flex items-center justify-center min-h-screen'>-->
                        <button v-if="loading" type="button" class="bg-indigo-400 h-max w-max justify-center items-center rounded-lg text-white font-bold hover:bg-indigo-300 hover:cursor-not-allowed duration-[500ms,800ms]" disabled>
                            <div class="flex items-center justify-center m-[10px]">
                                <div class="h-5 w-5 border-t-transparent border-solid animate-spin rounded-full border-white border-4"></div>
                                <div class="ml-2"> Loading... </div>
                            </div>
                        </button>
                    <div class="flex space-x-2 justify-center">
                        <div>
                            <button type="button"
                                    v-if="!loading"
                                    :disabled="loading || audioPlaying"
                                    class="inline-block px-6 py-2 border-2 border-blue-600 text-blue-600 disabled:opacity-25 font-bold text-sm leading-tight uppercase rounded-full hover:bg-black hover:bg-opacity-5 focus:outline-none focus:ring-0 transition duration-150 ease-in-out">
                                Reply
                            </button>
                        </div>
                    </div>

<!--                    <div v-if="questionAudioUrl" className="audio-player">-->
<!--                        <audio id="question_audio">-->
<!--&lt;!&ndash;                        <audio id="question_audio" controls autoplay>&ndash;&gt;-->
<!--                            <source :src="questionAudioUrl" type="audio/mpeg">-->
<!--                        </audio>-->
<!--                    </div>-->

<!--                    <a class="flex items-baseline mt-3 text-indigo-600 hover:text-indigo-900 focus:text-indigo-900" href="">-->
<!--                        <span>Learn more about our users</span>-->
<!--                        <span class="text-xs ml-1">&#x279c;</span>-->
<!--                    </a>-->
                </div>
                <svg class="hidden md:block absolute inset-y-0 h-full w-24 fill-current text-gray-100 -ml-12" viewBox="0 0 100 100" preserveAspectRatio="none">
                    <polygon points="50,0 100,0 50,100 0,100" />
                </svg>
            </div>
<!--            <button class="absolute top-0 mt-32 left-0 bg-white rounded-full shadow-md h-12 w-12 text-2xl text-indigo-600 hover:text-indigo-400 focus:text-indigo-400 -ml-6 focus:outline-none focus:shadow-outline">-->
<!--                <span class="block" style="transform: scale(-1);">&#x279c;</span>-->
<!--            </button>-->
            <button
                :disabled="loading || audioPlaying"
                @click="getQuestion"
                class="absolute top-0 mt-32 right-0 bg-white rounded-full disabled:opacity-25 shadow-md h-12 w-12 text-2xl text-indigo-600 hover:text-indigo-400 focus:text-indigo-400 -mr-6 focus:outline-none focus:shadow-outline">
                <span class="block" style="transform: scale(1);">&#x279c;</span>
            </button>
        </div>

        <!-- Carousel Tabs -->
        <div class="flex items-center pt-5 justify-between">
            <button class="px-2 opacity-50 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/kickstarter.svg" alt="" style="max-height: 60px;"></button>
            <button class="px-2 opacity-50 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/slack.svg" alt="" style="max-height: 60px;"></button>
            <button class="px-2 opacity-50 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/glossier.svg" alt="" style="max-height: 60px;"></button>
            <button class="px-2 opacity-50 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/charity_water.svg" alt="" style="max-height: 60px;"></button>
            <button class="px-2 opacity-100 hover:opacity-100 focus:opacity-100"><img class="w-full" src="https://stripe.com/img/v3/payments/overview/logos/missguided.svg" alt="" style="max-height: 60px;"></button>
        </div>
    </div>




    <div>
        <h2>Audio Recorder</h2>
        <main>
            <div>Permission: {{ permission }}</div>
            <div>Recording status: {{ recordingStatus }}</div>
            <div className="audio-controls">
                <button v-if="!permission" @click=getMicrophonePermission() type="button">
                    Get Microphone
                </button>
                <button v-if="permission && !recordingStatus" @click="startRecording()" type="button">
                    Start Recording
                </button>
                <button v-show="recordingStatus" @click="stopRecording()" type="button">
                    Stop Recording
                </button>
            </div>
            <div v-if="audioUrl" className="audio-player">
<!--            <div v-if="audioUrl" className="audio-player">-->
                <audio id="recorded_audio" controls autoplay>
                    <source :src="audioUrl" :type="mimeType">
                </audio>
                <p>
                    <a download :href="audioUrl" :type="mimeType">
                        Download Recording
                    </a>
                </p>
                <p>Link: {{ audioUrl }}</p>
                <p>mime-type: {{ mimeType }}</p>

                <p>Transcribe Job: {{ transcribingJob }}</p>
                <p>Transcribe Job Status: {{ transcribingJobStatus }}</p>
                <p>Text: {{ transcribedText }}</p>

            </div>
        </main>
    </div>
</template>


<script setup>

import {ref} from "vue";
// import {StartStreamTranscriptionCommand, TranscribeStreamingClient} from "@aws-sdk/client-transcribe-streaming";
import { TranscribeClient, StartTranscriptionJobCommand, GetTranscriptionJobCommand } from "@aws-sdk/client-transcribe";
// eslint-disable-next-line no-unused-vars
import { S3Client, GetObjectCommand,  PutObjectCommand } from "@aws-sdk/client-s3";
import apiClient from "@/services/AxiosInstance";
// import { Upload } from "@aws-sdk/lib-storage";

let stream;
let audioChunks = [];

let audioMime = {
    mimeType: 'audio/webm',
    // numberOfAudioChannels: 2,
    audioBitsPerSecond: 16000,
}

const question = ref('');
const questionAudioUrl = ref('');
const loading = ref(true);
const audioPlaying = ref(false);

const mediaRecorder = ref();
const permission = ref(false);
const recordingStatus = ref(false);
const audioUrl = ref("");
const mimeType = ref(audioMime.mimeType);
let transcribedText = ref("");
let transcribingJob = ref("");
let transcribingJobStatus = ref("");
// eslint-disable-next-line no-unused-vars
let transcribeClient;
// let transcribeStreamClient;
let credentials;

const region = process.env.VUE_APP_AWS_REGION;

getTranscribeCredentials();
await createInterview();
await getQuestion();

// const credentials = await getTranscribeCredentials()
//     .then(response => {return response.json()})
//     .then(data => {return data});

// eslint-disable-next-line no-undef
defineExpose({
    permission,
    recordingStatus,
    audioUrl,
    mimeType
})

async function createInterview() {
    let topicId = 10;
    window.localStorage.setItem('topicId', topicId);
    apiClient.post("/interviews", {
        name: "My awesome interview",
        topicId: topicId
    })
        .then(response => {
            window.localStorage.setItem("interviewId", response.data.id);
        })
    // .then(response => console.log("api response: " + response))
        .catch(err => console.log("error " + err));
}


async function getQuestion() {
    loading.value = true;
    question.value = '';
    questionAudioUrl.value = '';
    let topicId = window.localStorage.getItem('topicId');
    if (topicId !== undefined) {
        apiClient.get(`/questions?topicId=${topicId}`)
            .then(response => {
                question.value = response.data.text;
                questionAudioUrl.value = response.data.audioUrl;
                let oldQuestionAudio = document.getElementById("question_audio");
                if (oldQuestionAudio !== undefined && oldQuestionAudio !== null) {
                    oldQuestionAudio.remove();
                }
                loading.value = false;
                let questionAudio = document.createElement('audio');
                questionAudio.id = "question_audio";
                questionAudio.src = response.data.audioUrl;
                questionAudio.controls = false;
                questionAudio.autoplay = false;
                audioPlaying.value = true;
                questionAudio.addEventListener("ended", () => {
                    audioPlaying.value = false;
                });
                audioPlaying.value = true;
                questionAudio.play();

                saveQuestion(response.data.text, response.data.audioUrl);
            })
            // .then(response => console.log("api response: " + response))
            .catch(err => console.log("error " + err));
    }

}

async function saveQuestion(text, audioUrl) {
    let interviewId = window.localStorage.getItem('interviewId');
    apiClient.post(`/interviews/${interviewId}/questions`, {
        text: text,
        audioUrl: audioUrl
    })
        .then(response => {
            window.localStorage.setItem("questionId", response.data.id);
        })
        .catch(err => console.log("error " + err));
}

// eslint-disable-next-line no-unused-vars
async function getMicrophonePermission() {
    // credentials = await getTranscribeCredentials().then();
    if ("MediaRecorder" in window) {
        try {
            const mediaStream = await navigator.mediaDevices.getUserMedia({
                audio: true,
                video: false,
            });
            permission.value = true;
            console.log("permission ", permission.value);
            console.log("recording status ", recordingStatus.value);
            stream = mediaStream;
        } catch (err) {
            alert(err.message);
        }
    } else {
        alert("The MediaRecorder API is not supported in your browser.");
    }
}

// const input = { // StartStreamTranscriptionRequest
//     LanguageCode: "en-US",
//     MediaSampleRateHertz: Number(16000),
//     MediaEncoding: "pcm", // "pcm" || "ogg-opus" || "flac"
//     AudioStream: {
//         AudioEvent: {
//             AudioChunk: "BLOB_VALUE"
//         }
//     }
// }
//
// const command = new StartStreamTranscriptionCommand(input);
// const response = await transcribeClient.send(command);


// eslint-disable-next-line no-unused-vars
async function startRecording() {
    console.log("credentials: ", credentials);
    // transcribeStreamingClient = new TranscribeStreamingClient({
    //     region,
    //     credentials
    // });

    transcribeClient = new TranscribeClient({
        region,
        credentials
    });

    recordingStatus.value = true;
    // const media = new MediaRecorder(stream, audioMime);
    mediaRecorder.value = new MediaRecorder(stream, audioMime);
    mediaRecorder.value.start();

    // let localAudioChunks = [];

    mediaRecorder.value.ondataavailable = async (event) => {
        if (typeof event.data === "undefined") return;
        if (event.data.size === 0) return;
        // localAudioChunks.push(event.data);
        audioChunks.push(event.data);

        // const input = { // StartStreamTranscriptionRequest
        //     LanguageCode: "en-US",
        //     MediaSampleRateHertz: Number(16000),
        //     MediaEncoding: "ogg-opus", // "pcm" || "ogg-opus" || "flac"
        //     AudioStream: {
        //         AudioEvent: {
        //             AudioChunk: event.data
        //         }
        //     }
        // }

        // const command = new StartStreamTranscriptionCommand(input);
        // transcribedText.value = await transcribeStreamingClient.send(command);

        // const input = { // StartTranscriptionJobRequest
        //     TranscriptionJobName: "boanerges-transcribe",
        //     LanguageCode: "en-US",
        //     MediaSampleRateHertz: Number(16000),
        //     MediaFormat: "webm", // "mp3" || "mp4" || "wav" || "flac" || "ogg" || "amr" || "webm",
        //     Media: { // Media
        //         MediaFileUri: "s3://boanerges-recorded-audio/recordedAudio.webm" //s3 location  s3://DOC-EXAMPLE-BUCKET/my-media-file.flac
        //     },
        // }
        //
        // const command = new StartTranscriptionJobCommand(input);
        // transcribedText.value = await transcribeClient.send(command);

    }
    // setAudioChunks(localAudioChunks);
}

// eslint-disable-next-line no-unused-vars
function stopRecording() {
    let s3PutCmd;
    let audioBlob;

    const s3Client = new S3Client({
        region,
        credentials
    });

    // const s3Client = new S3Client({
    //     region,
    //     credentials
    // });

    recordingStatus.value = false;
    mediaRecorder.value.stop();

    // s3PutCommand = new PutObjectCommand({
    //     Bucket: "boanerges-recorded-audio",
    //     Key: "recordedAudio.webm",
    //     Body: audioBlob,
    // });
    //
    // console.log("S3PutCommand");
    // console.log(s3PutCommand);
    //
    // try {
    //     const response = await s3Client.send(s3PutCommand);
    //     console.log(response);
    // } catch (err) {
    //     console.error(err);
    // }

    mediaRecorder.value.onstop = async () => {
        audioBlob = new Blob(audioChunks, audioMime);
        audioUrl.value = URL.createObjectURL(audioBlob);
        // + "." + audioMime.mimeType.substring(audioMime.mimeType.lastIndexOf("/") + 1);
        console.log("audioURL: ", audioUrl.value);
        // setAudio(audioUrl);

        audioChunks = [];

        s3PutCmd = new PutObjectCommand({
            Bucket: "boanerges-recorded-audio",
            Key: "recordedAudio.webm",
            Body: audioBlob,
        });

        console.log("S3PutCommand");
        console.log(s3PutCmd);

        try {
            const response = await s3Client.send(s3PutCmd);
            console.log(response);
        } catch (err) {
            console.error(err);
        }


        const startTranscribingInput = { // StartTranscriptionJobRequest
            TranscriptionJobName: "boanerges-transcribe9g",
            LanguageCode: "en-US",
            MediaSampleRateHertz: Number(48000),
            // MediaSampleRateHertz: Number(16000),
            MediaFormat: "webm", // "mp3" || "mp4" || "wav" || "flac" || "ogg" || "amr" || "webm",
            Media: { // Media
                MediaFileUri: "s3://boanerges-recorded-audio/recordedAudio.webm" //s3 location  s3://DOC-EXAMPLE-BUCKET/my-media-file.flac
            },
            OutputBucketName: "boanerges-recorded-audio",
            OutputKey: "automatedresult9g.json",
        }


        // eslint-disable-next-line no-unused-vars
        const startTranscribingCmd = new StartTranscriptionJobCommand(startTranscribingInput);
        transcribingJob.value = await transcribeClient.send(startTranscribingCmd);



        const GetTranscriptionJobInput = { // GetTranscriptionJobRequest
            TranscriptionJobName: "boanerges-transcribe9g", // required
        };
        const GetTranscriptionJobCmd = new GetTranscriptionJobCommand(GetTranscriptionJobInput);

        // let attempts = 0;

        // while (transcribingJobStatus.value !== "COMPLETED" || transcribingJobStatus.value !== "FAILED" || attempts < 20 ) {
        //     attempts++;

        setTimeout(() => { console.log("World!"); getTranscribedText(); }, 150000);

        async function getTranscribedText() {
            // let attempts = 0;

            // while (transcribingJobStatus.value !== "COMPLETED" || transcribingJobStatus.value !== "FAILED" || attempts < 10 ) {
            //     attempts++;
            for (let attempts = 0; attempts < 10; attempts++) {
                let transcribeResponse = await transcribeClient.send(GetTranscriptionJobCmd);
                transcribingJobStatus.value = transcribeResponse.TranscriptionJob.TranscriptionJobStatus;
                // transcribedText.value = transcribeResponse.TranscriptionJob.transcript;
                console.log("transcribingJobStatus attempt", attempts);
                console.log(transcribingJobStatus.value)
                // console.log(transcribedText.value)
            // let responseBodyJson = JSON.parse(transcribeResponse).TranscriptionJobStatus;;
            }


            if (transcribingJobStatus.value === "COMPLETED") {
                try {
                    let s3GetCmd = new GetObjectCommand({
                        Bucket: "boanerges-recorded-audio",
                        Key: "automatedresult9g.json"
                    });

                    let s3GetResponse = await s3Client.send(s3GetCmd);
                    let responseBody = await s3GetResponse.Body.transformToString();
                    let responseBodyJson = JSON.parse(responseBody);
                    transcribedText.value = responseBodyJson.results.transcripts[0].transcript;
                    console.log(s3GetResponse);
                    console.log(responseBody);
                    console.log(responseBodyJson);
                    console.log(transcribedText.value);
                } catch (err) {
                    console.error(err);
                }
            }

        }




        //
        // file = {
        //     Bucket: "boanerges-recorded-audio",
        //     Key: "recordedAudio.webm",
        //     Body: audioBlob,
        // }

        // try {
        //     const response = await client.send(command);
        //     console.log(response);
        // } catch (err) {
        //     console.error(err);
        // }
        //

        // setAudioChunks([]);
        // let recordedAudio = document.getElementById("recorded_audio");
        // console.log("recorded audio :", recordedAudio);
        // recordedAudio.play();

        // console.log("transcribe response: ", transcribedText.value);

    }

    // console.log("S3PutCommand");
    // console.log(s3PutCommand);

    // await s3.upload({
    //     Bucket: "boanerges-recorded-audio",
    //     Key: "recordedAudio.webm",
    //     Body: audioBlob,
    // }).then(response => {console.log(response.json)});

    // try {
    //     const response = await s3Client.send(s3PutCommand);
    //     console.log(response);
    // } catch (err) {
    //     console.error(err);
    // }

    // const target = {
    //         Bucket: "boanerges-recorded-audio",
    //         Key: "recordedAudio.webm",
    //         Body: audioBlob
    // };

    // await s3.putObject(s3PutCommand);

    // try {
    //     const parallelUploads3 = new Upload({
    //         client: s3Client,
    //         queueSize: 4, // optional concurrency configuration
    //         leavePartsOnError: false, // optional manually handle dropped parts
    //         params: target,
    //     });
    //
    //     parallelUploads3.on("httpUploadProgress", (progress) => {
    //         console.log(progress);
    //     });
    //
    //     await parallelUploads3.done();
    // } catch (e) {
    //     console.log(e);
    // }

    // const input = { // StartTranscriptionJobRequest
    //     TranscriptionJobName: "boanerges-transcribe",
    //     LanguageCode: "en-US",
    //     MediaSampleRateHertz: Number(16000),
    //     MediaFormat: "webm", // "mp3" || "mp4" || "wav" || "flac" || "ogg" || "amr" || "webm",
    //     Media: { // Media
    //         MediaFileUri: "s3://boanerges-recorded-audio/recordedAudio.webm" //s3 location  s3://DOC-EXAMPLE-BUCKET/my-media-file.flac
    //     },
    // }
    //
    //
    // // eslint-disable-next-line no-unused-vars
    // const command = new StartTranscriptionJobCommand(input);
    // transcribedText.value = await transcribeClient.send(command);

    // const response = await s3Client.send(s3PutCommand);
    // console.log(response);

    // try {
    //     const response = await s3Client.send(s3PutCommand);
    //     console.log(response);
    // } catch (err) {
    //     // console.error(err);
    //     console.error("S3 error");
    // }
}

// async function getTranscribeCredentials() {
//     const getCredentialsUrl = process.env.VUE_APP_BACKEND_PROTOCOL + "://" + process.env.VUE_APP_BACKEND_HOST + '/api/v1/sts';
//     await fetch(getCredentialsUrl, {method: "POST"}).then(response => {
//         if (!response.ok) {
//             const message = `An error has occurred: ${response.status}`;
//             throw new Error(message);
//         }
//         return response.json()
//     }).then(data => {
//         console.log("STS response: ", data);
//         credentials = data;
//         return data;
//     });
// }

async function getTranscribeCredentials() {
    await apiClient.post('/sts').then(response => {
        if (response.status !== 200) {
            const message = `An error has occurred: ${response.statusText}`;
            throw new Error(message);
        }
        console.log("STS response: ", response.data);
        credentials = response.data;
        return response.data;
    });
}

// const AudioRecorder = () => {
//   const [permission, setPermission] = useState(false);
//
//   const mediaRecorder = useRef(null);
//
//   const [recordingStatus, setRecordingStatus] = useState("inactive");
//
//   const [stream, setStream] = useState(null);
//
//   const [audio, setAudio] = useState(null);
//
//   const [audioChunks, setAudioChunks] = useState([]);

// async function getMicrophonePermission() {
//   if ("MediaRecorder" in window) {
//     try {
//       const mediaStream = await navigator.mediaDevices.getUserMedia({
//         audio: true,
//         video: false,
//       });
//       setPermission(true);
//       setStream(mediaStream);
//     } catch (err) {
//       alert(err.message);
//     }
//   } else {
//     alert("The MediaRecorder API is not supported in your browser.");
//   }
// }

// const startRecording = async () => {
//   setRecordingStatus("recording");
//   const media = new MediaRecorder(stream, {type: mimeType});
//
//   mediaRecorder.current = media;
//
//   mediaRecorder.current.start();
//
//   let localAudioChunks = [];
//
//   mediaRecorder.current.ondataavailable = (event) => {
//     if (typeof event.data === "undefined") return;
//     if (event.data.size === 0) return;
//     localAudioChunks.push(event.data);
//   }
//
//   setAudioChunks(localAudioChunks);
// }

// const stopRecording = () => {
//   setRecordingStatus("inactive");
//   mediaRecorder.current.stop();
//
//   mediaRecorder.current.onstop = () => {
//     const audioBlob = new Blob(audioChunks, {type: mimeType});
//     const audioUrl = URL.createObjectURL(audioBlob);
//
//     setAudio(audioUrl);
//
//     setAudioChunks([]);
//   }
// }

</script>


<style scoped lang="css">
</style>