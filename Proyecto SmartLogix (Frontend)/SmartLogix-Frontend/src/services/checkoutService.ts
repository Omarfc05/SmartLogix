export const checkout = async (payload: any) => {
    console.log("PAYLOAD CHECKOUT:", payload);

    const res = await fetch("http://localhost:8089/api/bff/v1/checkout", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        const error = await res.text();
        console.error("ERROR BACKEND:", error);
        throw new Error("Error en checkout");
    }

    return res.json();
};