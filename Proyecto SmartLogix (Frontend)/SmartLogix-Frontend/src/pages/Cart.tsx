import { useCar } from "../contexts/CartContext";
import { useNavigate } from "react-router-dom";

export const Cart = () => {
    const {
        items,
        removeOne,
        addToCar,
        removeAllItem,
        clearCart,
        formatCLP,
        totalAmount,
    } = useCar();

    const navigate = useNavigate();

    if (items.length === 0) {
        return (
            <div className="container text-light p-4">
                <h2>Carrito vacío</h2>
                <p>No has agregado productos aún.</p>
            </div>
        );
    }

    return (
        <div className="container text-light p-4">
            <h2>🛒 Tu carrito</h2>

            <div className="mt-4">
                {items.map((item) => (
                    <div
                        key={item.id}
                        className="d-flex justify-content-between align-items-center border p-3 mb-2 rounded bg-dark"
                    >
                        <div>
                            <h5>{item.title}</h5>
                            <p className="mb-1">{formatCLP(item.price)}</p>
                            <small>Cantidad: {item.qty}</small>
                        </div>

                        <div className="d-flex gap-2 align-items-center">
                            <button
                                className="btn btn-sm btn-secondary"
                                onClick={() => removeOne(item.id)}
                            >
                                -
                            </button>

                            <span>{item.qty}</span>

                            <button
                                className="btn btn-sm btn-secondary"
                                onClick={() => addToCar(item)}
                            >
                                +
                            </button>

                            <button
                                className="btn btn-sm btn-danger"
                                onClick={() => removeAllItem(item.id)}
                            >
                                eliminar
                            </button>
                        </div>
                    </div>
                ))}
            </div>

            <hr />

            <h4>Total: {formatCLP(totalAmount)}</h4>

            <div className="d-flex gap-2 mt-3">
                <button
                    className="btn btn-outline-danger"
                    onClick={clearCart}
                >
                    Vaciar carrito
                </button>

                <button
                    className="btn btn-success"
                    onClick={() => navigate("/checkout")}
                >
                    Proceder al pago
                </button>
            </div>
        </div>
    );
};