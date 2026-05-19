import type { Product } from "../types/product";
import { useCar } from "../contexts/CartContext";

export const ProductCard = ({ product }: { product: Product }) => {
    const { addToCar, formatCLP } = useCar();

    const outOfStock = product.stock <= 0;

    return (
        <div className="card shadow-sm p-3">
            <img
                src={product.imageSrc}
                alt={product.title}
                className="card-img-top"
                style={{ height: "180px", objectFit: "cover" }}
            />

            <div className="card-body">
                <h5>{product.title}</h5>
                <p className="text-muted">{product.description}</p>

                <div className="d-flex justify-content-between align-items-center">
                    <strong>{formatCLP(product.price)}</strong>

                    <span className={`badge ${outOfStock ? "bg-danger" : "bg-success"}`}>
                        {outOfStock ? "Sin stock" : `Stock: ${product.stock}`}
                    </span>
                </div>

                <button
                    className="btn btn-primary w-100 mt-2"
                    disabled={outOfStock}
                    onClick={() => addToCar(product)}
                >
                    {outOfStock ? "No disponible" : "Agregar al carrito"}
                </button>
            </div>
        </div>
    );
};