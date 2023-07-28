import apiClient from "@/services/AxiosInstance";
// import {useRouter} from "vue-router";
// import router from "readable-stream/lib/internal/streams/readable";



class AuthService {
    async login(user) {
        return await apiClient.post("/auth/login", {
            username: user.username,
            password: user.password,
        })
            // .then(response => response.json())
            // .then(response => console.log("api response: " + response))
            .catch(err => console.log("error " + err));

    }

    async logout() {
        window.localStorage.clear();
        window.location.href = "/login";
        // const router = useRouter();
        // await router.push("/login");
    }
}

export default new AuthService();