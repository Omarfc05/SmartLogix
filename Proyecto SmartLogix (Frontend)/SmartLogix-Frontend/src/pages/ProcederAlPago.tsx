import { useState } from "react";
import { useCar } from "../contexts/CartContext";
import { useCheckout } from "../hooks/useCheckout";

export const ProcederAlPago = () => {
    const { items, totalAmount, formatCLP } = useCar();
    const { pay } = useCheckout();

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(false);

    const handlePay = async () => {
        setLoading(true);
        setError(null);
        try {
            await pay();
            setSuccess(true);
        } catch (e) {
            setError("No se pudo procesar el pago. Verifica los microservicios.");
        } finally {
            setLoading(false);
        }
    };

    if (items.length === 0 && !success) {
        return (
            <div className="container mt-5 text-center glass-container">
                <h3 className="text-primary fw-bold">No hay nada que pagar</h3>
                <p className="text-muted">Tu carrito está vacío.</p>
            </div>
        );
    }

    return (
        <div className="container mt-4 d-flex justify-content-center">
            <div className="glass-container w-100" style={{ maxWidth: '600px' }}>
                <h2 className="fw-bold text-center mb-4" style={{ color: '#0077b6' }}>Checkout Seguro</h2>

                <div className="mb-4">
                    {items.map((i) => (
                        <div key={i.id} className="d-flex justify-content-between align-items-center p-3 mb-2 glossy-card">
                            <span className="fw-medium">{i.title} <span className="text-primary mx-2">x {i.qty}</span></span>
                            <strong className="text-dark">{formatCLP(i.price * i.qty)}</strong>
                        </div>
                    ))}
                </div>

                <div className="d-flex justify-content-between align-items-center mb-4 p-3 rounded-4" style={{ background: 'rgba(255,255,255,0.7)' }}>
                    <h4 className="m-0 text-muted">Total a pagar:</h4>
                    <h3 className="m-0 fw-bold" style={{ color: '#0077b6' }}>{formatCLP(totalAmount)}</h3>
                </div>

                {error && <div className="alert alert-danger rounded-4 shadow-sm border-0">{error}</div>}

                {success ? (
                    <div className="alert alert-success rounded-4 shadow-sm border-0 text-center">
                        <h4 className="fw-bold mb-1">¡Pago exitoso!</h4>
                        <p className="mb-0">Tu orden ha sido creada y enviada al servidor.</p>
                    </div>
                ) : (
                    <button
                        className="btn btn-bubble w-100 py-3 fs-5"
                        onClick={handlePay}
                        disabled={loading}
                    >
                        {loading ? "Procesando de forma segura..." : "Confirmar y Pagar"}
                    </button>
                )}
            </div>
        </div>
    );
};