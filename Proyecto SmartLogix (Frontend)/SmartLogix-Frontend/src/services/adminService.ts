
const API_URL = "http://localhost:8089/api";

export const getOrders = async () => {
    const res = await fetch(`${API_URL}/order/v1`);
    if (!res.ok) throw new Error("Error cargando órdenes");
    return res.json();
};

export const approveOrder = async (id: number) => {
    const res = await fetch(`${API_URL}/order/v1/${id}/status?status=APROBADO`, { method: "PUT" });
    if (!res.ok) throw new Error("Error aprobando orden");
    return res.json();
};

export const addStock = async (id: number, quantity: number) => {
    // Usamos el endpoint manual que creamos antes
    const res = await fetch(`${API_URL}/inventory/v1/${id}/stock/replenish?quantity=${quantity}&reason=Ingreso desde Panel Admin`, { method: "POST" });
    if (!res.ok) throw new Error("Error agregando stock");
    return res.json();
};

export const getProductMovements = async (id: number) => {
    const res = await fetch(`${API_URL}/inventory/v1/${id}/movements`);
    if (!res.ok) throw new Error("Error cargando el historial");
    return res.json();
};