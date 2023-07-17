<template>
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
// import { Upload } from "@aws-sdk/lib-storage";

let stream;
let audioChunks = [];

let audioMime = {
    mimeType: 'audio/webm',
    // numberOfAudioChannels: 2,
    audioBitsPerSecond: 16000,
}

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

async function getTranscribeCredentials() {
    const getCredentialsUrl = process.env.VUE_APP_BACKEND_PROTOCOL + "://" + process.env.VUE_APP_BACKEND_HOST + '/api/v1/sts';
    await fetch(getCredentialsUrl, {method: "POST"}).then(response => {
        if (!response.ok) {
            const message = `An error has occurred: ${response.status}`;
            throw new Error(message);
        }
        return response.json()
    }).then(data => {
        console.log("STS response: ", data);
        credentials = data;
        return data;
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