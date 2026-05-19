import { checkout } from "../services/checkoutService";
import { useCar } from "../contexts/CartContext";

export const useCheckout = () => {
    const { items, clearCart } = useCar(); // Quitamos totalAmount porque tu backend no lo pide

    const pay = async () => {
        // 🔥 AQUÍ ARREGLAMOS EL PAYLOAD
        const payload = {
            client: "Cliente Web", // Puedes poner un string fijo por ahora
            details: items.map((i) => ({
                productId: i.id,
                quantity: i.qty,
            }))
        };

        const order = await checkout(payload);

        clearCart();

        return order;
    };

    return { pay };
};