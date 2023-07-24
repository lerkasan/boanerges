<template>
    <h1>Hello</h1>
    <div>
        <ul v-if="books">
            <li v-for="book in books" :key="book.id">
                {{ book.title }}
            </li>
        </ul>
    </div>
</template>


<script setup>

import {ref} from "vue";
import apiClient from "@/services/AxiosInstance";

async function getBooks() {
    const response = await apiClient.get('/books');

    if (response.status !== 200) {
        const message = `An error has occurred: ${response.status}`;
        throw new Error(message);
    }

    return response.data;
}

const result = await getBooks()
        .catch(error => {
            console.log(error.message);
});

// const result = await getBooks();

const books = ref(result);
console.log("Books2");
console.log(result);

</script>


<style scoped lang="css">
    li {
        list-style-type: none;
    }
</style>