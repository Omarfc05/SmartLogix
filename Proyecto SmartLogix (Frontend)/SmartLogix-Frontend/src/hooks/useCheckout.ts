import { checkout } from "../services/checkoutService";
import { useCar } from "../contexts/CartContext";

export const useCheckout = () => {
    const { items, clearCart, totalAmount } = useCar();

    const pay = async () => {
        const payload = {
            items: items.map((i) => ({
                productId: i.id,
                quantity: i.qty,
            })),
            total: totalAmount,
        };

        const order = await checkout(payload);

        clearCart();

        return order;
    };

    return { pay };
};