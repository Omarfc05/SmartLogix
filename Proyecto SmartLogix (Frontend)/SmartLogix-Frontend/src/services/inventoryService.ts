const API = "http://localhost:8089/api/inventory/v1";

export const getProducts = async () => {
    const res = await fetch(API);

    if (!res.ok) throw new Error("Error cargando productos");

    return res.json();
};