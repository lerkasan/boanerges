import apiClient from "@/services/AxiosInstance";

class AuthService {
    async login(user) {
        return await apiClient.post("/auth/login", {
            username: user.username,
            password: user.password,
        })
            .catch(err => console.log("error " + err));

    }

    async logout() {
        window.localStorage.clear();
        window.location.href = "/login";
    }
}

export default new AuthService();