import { useNavigate } from "react-router-dom";

export const Home = () => {
    const navigate = useNavigate();

    return (
        <div className="container text-light py-5">
            <h1>Bienvenido a SmartLogix 🛒</h1>

            <p className="mt-3">
                Sistema de ecommerce con microservicios (Inventory, Order, BFF)
            </p>

            <div className="mt-4 d-flex gap-3">
                <button
                    className="btn btn-primary"
                    onClick={() => navigate("/products")}
                >
                    Ver Productos
                </button>

                <button
                    className="btn btn-success"
                    onClick={() => navigate("/checkout")}
                >
                    Ir al Checkout
                </button>
            </div>
        </div>
    );
};