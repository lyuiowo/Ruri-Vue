import axios from "axios";
import {ElMessage} from "element-plus";

const request = axios.create({
    baseURL: "http://localhost:8080/api"
})

export const get = async (path, params) => {
    try {
        const response = await request.get(path, { params: params })
        return response.data
    } catch (error) {
        throw new Error(error)
    }
}

export const post = async (path, params) => {
    try {
        const response = await request.post(path, null, { params: params })
        return response.data
    } catch (error) {
        throw new Error(error)
    }
}
