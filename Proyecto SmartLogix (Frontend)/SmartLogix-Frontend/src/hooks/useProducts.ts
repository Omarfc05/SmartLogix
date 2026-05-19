import { useEffect, useState } from "react";
import { getProducts } from "../services/inventoryService";
import type { Product } from "../types/product";

export const useProducts = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const load = async () => {
            const data = await getProducts();
            setProducts(data);
            setLoading(false);
        };

        load();
    }, []);

    return { products, loading };
};