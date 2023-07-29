<script setup>

import {StartStreamTranscriptionCommand, TranscribeStreamingClient} from "@aws-sdk/client-transcribe-streaming";
import {ref} from "vue";

const region = process.env.VUE_APP_AWS_REGION;
const credentials = await getTranscribeCredentials();

const transcribeClient = new TranscribeStreamingClient({
    region,
    credentials
});

const input = { // StartStreamTranscriptionRequest
    LanguageCode: "en-US",
    MediaSampleRateHertz: Number(16000),
    MediaEncoding: "pcm", // "pcm" || "ogg-opus" || "flac"
    AudioStream: {
        AudioEvent: {
            AudioChunk: "BLOB_VALUE"
        }
    }
}

const command = new StartStreamTranscriptionCommand(input);
const response = await transcribeClient.send(command);

async function getTranscribeCredentials() {
    const getCredentialsUrl = '/api/v1/sts';
    const response = await fetch(getCredentialsUrl, {method: "POST"})
    if (!response.ok) {
        const message = `An error has occurred: ${response.status}`;
        throw new Error(message);
    }
    return response.data;
}

const transcribedText = ref(response);

</script>

<template>
    <p>Text: {{ transcribedText }}</p>
</template>

<style scoped lang="css">

</style>