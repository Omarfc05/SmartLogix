import type {Product} from "../types/product";

const API_URL = "http://localhost:8089/api/inventory/v1";

export const getProducts = async (): Promise<Product[]> => {
    const res = await fetch(API_URL);

    if (!res.ok) {
        throw new Error("Error cargando productos");
    }

    return res.json();
};