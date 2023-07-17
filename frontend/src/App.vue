<template>
    <Suspense>
        <RecordAudio/>
    </Suspense>
    <Suspense>
        <HelloWorld/>
    </Suspense>
</template>

<script>
import HelloWorld from "@/components/HelloWorld/HelloWorld";
import RecordAudio from "@/components/Audio/RecordAudio";

export default {
  name: 'App',
  components: {
    HelloWorld,
    RecordAudio,
  }
}
</script>

<style src="./app.css" lang="css"></style>
