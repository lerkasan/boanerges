import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_PROTOCOL + "://" + process.env.VUE_APP_BACKEND_HOST + "/api/v1";

const apiClient = axios.create({
    baseURL: API_URL,
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
});

const token = localStorage.getItem("jwtToken");

apiClient.interceptors.request.use(
    (config) => {
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
    },
    (error) => {
        console.log(error);
        return Promise.reject(error);
    }
);

apiClient.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    if (401 === error.response.status) {
        window.localStorage.clear();
        window.location.href = "/login";
        // return Promise.resolve(error.response);
    } else {
        return Promise.reject(error);
    }
});

export default apiClient;