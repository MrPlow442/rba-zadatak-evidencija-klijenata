import {CardRequest} from "../../card-request/model/card-request-types";
import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/v1/card-request';

export const findAllCardRequests = async () : Promise<CardRequest[]> => {
    const response = await axios.get(BASE_URL);
    return response.data;
}

export const findOneCardRequest = async (oib: string): Promise<CardRequest> => {
    const response = await axios.get(`${BASE_URL}/${oib}`);
    return response.data;
}

export const createCardRequest = async (data: CardRequest): Promise<CardRequest> => {
    const response = await axios.post(`${BASE_URL}`, data);
    return response.data;
}

export const updateCardRequest = async (oib: string, data: CardRequest): Promise<CardRequest> => {
    const response = await axios.put(`${BASE_URL}/${oib}`, data);
    return response.data;
}

export const deleteCardRequest = async (oib: string): Promise<void> => {
    await axios.delete(`${BASE_URL}/${oib}`)
}