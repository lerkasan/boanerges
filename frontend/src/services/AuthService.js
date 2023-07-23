import apiClient from "@/services/AxiosInstance";
import router from "readable-stream/lib/internal/streams/readable";



class AuthService {
    async login(user) {
        return await apiClient.post("/auth/login", {
            username: user.username,
            password: user.password,
        });
    }

    logout() {
        window.localStorage.removeItem("jwtToken");
        router.push("/login");
    }
}

export default new AuthService();