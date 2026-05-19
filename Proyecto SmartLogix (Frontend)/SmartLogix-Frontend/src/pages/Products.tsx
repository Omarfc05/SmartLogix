import { useProducts } from "../hooks/useProducts";
import { ProductCard } from "../components/ProductCard";

export const Products = () => {
  const { products, loading } = useProducts();

  if (loading) return <p>Cargando...</p>;

  return (
      <div>
        <h2>Productos</h2>

        <div className="grid">
          {products.map((p) => (
              <ProductCard key={p.id} product={p} />
          ))}
        </div>
      </div>
  );
};