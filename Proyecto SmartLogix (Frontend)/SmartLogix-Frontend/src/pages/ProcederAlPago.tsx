import { useState } from "react";
import { useCar } from "../contexts/CartContext";
import { useCheckout } from "../hooks/useCheckout";

export const ProcederAlPago = () => {
    const { items, totalAmount, formatCLP, clearCart } = useCar();
    const { pay } = useCheckout();

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(false);

    const handlePay = async () => {
        setLoading(true);
        setError(null);

        try {
            await pay(); // 👈 así, sin params
            setSuccess(true);
        } catch (e) {
            setError("No se pudo procesar el pago");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Checkout</h2>

            {items.map((i) => (
                <div key={i.id} className="d-flex justify-content-between">
                    <span>{i.title} x {i.qty}</span>
                    <span>{formatCLP(i.price * i.qty)}</span>
                </div>
            ))}

            <hr />

            <h3>Total: {formatCLP(totalAmount)}</h3>

            {error && <div className="alert alert-danger">{error}</div>}
            {success && <div className="alert alert-success">Pago exitoso</div>}

            <button
                className="btn btn-primary mt-3"
                onClick={handlePay}
                disabled={loading}
            >
                {loading ? "Procesando..." : "Pagar"}
            </button>
        </div>
    );
};