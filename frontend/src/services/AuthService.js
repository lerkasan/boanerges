import apiClient from "@/services/AxiosInstance";
import {useRouter} from "vue-router";
// import router from "readable-stream/lib/internal/streams/readable";



class AuthService {
    async login(user) {
        return await apiClient.post("/auth/login", {
            username: user.username,
            password: user.password,
        });
    }

    async logout() {
        window.localStorage.removeItem("jwtToken");
        const router = useRouter();
        await router.push("/login");
    }
}

export default new AuthService();