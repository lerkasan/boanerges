import axios from "axios";

// const API_URL = "http://localhost:9090/api/v1";
const API_URL = process.env.VUE_APP_BACKEND_PROTOCOL + "://" + process.env.VUE_APP_BACKEND_HOST + "/api/v1";

const apiClient = axios.create({
    baseURL: API_URL,
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
});

const token = localStorage.getItem("jwtToken");
console.log("Token");
console.log(token);

apiClient.interceptors.request.use(
    (config) => {
        // try {
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            // config.headers.Authorization = `Bearer ${token}`;
            return config;
        // } catch (error) {
        //     console.log(error);
        // return Promise.reject(error);
        // }
    },
    (error) => {
        console.log(error);
        return Promise.reject(error);
    }
);

export default apiClient;