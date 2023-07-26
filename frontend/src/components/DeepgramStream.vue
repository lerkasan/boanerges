<script setup>

import {ref} from "vue";

let mediaRecorder;
let socket;
let transcripts = ref([]);

created();

async function created() {
        const stream = await navigator.mediaDevices.getUserMedia({ audio: true })
            .catch(error => alert(error))

        if(!MediaRecorder.isTypeSupported('audio/webm')) return alert('Unsupported browser')
        mediaRecorder = new MediaRecorder(stream, { mimeType: 'audio/webm' })
    }

function begin() {
            const DG_URL = 'wss://api.deepgram.com/v1/listen'
            const DG_KEY = 'MY_SECRET_API_KEY'
            socket = new WebSocket(DG_URL, ['token', DG_KEY])
            socket.onopen = startStreaming;
            socket.onmessage = handleResponse1;
}

function  startStreaming() {
    mediaRecorder.start(250);
            mediaRecorder.addEventListener('dataavailable', event => {
                if(event.data.size > 0 && socket.readyState === 1) {
                    socket.send(event.data)
                }
                // mediaRecorder.start(250)
            })
}

function  handleResponse1(message) {
            const received = JSON.parse(message.data)
            const transcript = received.channel.alternatives[0].transcript
            if (transcript) {
                transcripts.value.push(transcript);
                console.log(transcript);
            }
}

</script>

<template>
    <div id="app">
        <button @click="begin">Begin transcription</button>
        <p v-for="(transcript, i) in transcripts" :key="i">{{ transcript }}</p>
    </div>
</template>

<style scoped lang="css">

</style>