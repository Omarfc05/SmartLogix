import { useCar } from "../contexts/CartContext";

export default function PaginaCarritoPruebas() {
  const { items, clearCart,} = useCar();

  const manejarPagoBackend = async () => {
    // URL de tu API Gateway (Ajusta el puerto si tu Gateway usa otro, ej: 8080)
    const GATEWAY_URL = "http://localhost:8089/api/bff/checkout/procesar";

    try {
      const response = await fetch(GATEWAY_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // Aquí iría el token de Keycloak si ya lo tienes integrado:
          // "Authorization": `Bearer ${token}` 
        },
        body: JSON.stringify(items), // Enviamos la lista de productos del carrito
      });

      if (!response.ok) {
        throw new Error(`Error en la petición: ${response.status}`);
      }

      const mensajeServidor = await response.text();
      alert(`Respuesta del Sistema: ${mensajeServidor}`);
      
      // Si la compra fue exitosa, limpiamos el carrito en el frontend
      if (!mensajeServidor.includes("Error")) {
          clearCart();
      }

    } catch (error) {
      console.error("Error en el laboratorio de pruebas:", error);
      alert("No se pudo procesar el pago. Revisa si el API Gateway y el BFF están encendidos.");
    }
  };

  // ... (Tu código de renderizado del carrito que ya funciona)
  // En tu botón de "Proceder al pago", asegúrate de asignarle la función:
  // <button className="btn btn-success" onClick={manejarPagoBackend}>Proceder al pago</button>
}