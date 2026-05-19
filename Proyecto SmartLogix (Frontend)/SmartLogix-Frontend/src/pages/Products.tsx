import React, { useState, useEffect } from 'react';
import { useCar } from "../contexts/CartContext"

export const Products = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const { addToCar, formatCLP } = useCar();

  // URL del API Gateway
  const API_URL = "http://localhost:8089/api/products"; 

  useEffect(() => {
    // Función asíncrona para traer los datos del backend
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const response = await fetch(API_URL);
        
        if (!response.ok) {
          throw new Error(`Error en el servidor: ${response.status}`);
        }
        
        const data = await response.json();
        setProducts(data); // Guardamos los productos del backend en el estado
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []); // El arreglo vacío asegura que solo se ejecute una vez al cargar la página

  // --- RENDERS DE CONTROL ---
  if (loading) return <div className="text-light p-4">Cargando productos del Inventario...</div>;
  if (error) return <div className="text-danger p-4">Error de conexión: {error}</div>;

  // --- VISTA PRINCIPAL ---
  return (
    <main className="container py-4">
      <h2 className="text-light mb-4">Productos Disponibles</h2>
      <div className="row row-cols-1 row-cols-md-3 g-4">
        {products.map((prod) => (
          <div className="col" key={prod.id}>
            <div className="card card-gray h-100 bg-dark text-light border-secondary">
              <img 
                src={prod.imageSrc || "https://via.placeholder.com/150"} 
                className="card-img-top p-3 object-fit-contain" 
                alt={prod.title}
                style={{ height: "200px" }}
              />
              <div className="card-body d-flex flex-column justify-content-between">
                <div>
                  <h5 className="card-title">{prod.title}</h5>
                  <p className="card-text text-white small">{prod.description}</p>
                </div>
                <div className="mt-3">
                  <div className="h5 text-success fw-bold mb-2">{formatCLP(prod.price)}</div>
                  <button 
                    className="btn btn-primary w-100"
                    onClick={() => addToCar(prod)}
                  >
                    Agregar al Carrito
                  </button>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}
