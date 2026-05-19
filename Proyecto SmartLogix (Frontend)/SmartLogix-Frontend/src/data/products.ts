export type Product = {
    id: number;
    title: string;
    description: string;
    category: string;
    price: number;
    imageSrc: string;
  };

  export const products: Product[] = [
    {
      id: 1,
      title: "PC Raiden",
      description: 'Pc de ultima generacion',
      category: "Computadora",
      price: 70000,
      imageSrc: "...",
    }
  ];