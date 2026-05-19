export type Product = {
    id: number;
    title: string;
    description?: string;
    category?: string;
    price: number;
    imageSrc: string;
    stock: number;
};