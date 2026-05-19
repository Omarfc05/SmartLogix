import { Link, NavLink } from "react-router-dom";
import { useCar } from "../contexts/CartContext";

export const Navbar = () => {
    const { items } = useCar();

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm">
            <div className="container">

                {/* BRAND */}
                <Link className="navbar-brand fw-bold text-warning" to="/">
                    SmartLogix
                </Link>

                {/* TOGGLE */}
                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#mainNav"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>

                {/* LINKS */}
                <div className="collapse navbar-collapse" id="mainNav">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">

                        <li className="nav-item">
                            <NavLink className="nav-link" to="/">
                                Home
                            </NavLink>
                        </li>

                        <li className="nav-item">
                            <NavLink className="nav-link" to="/products">
                                Products
                            </NavLink>
                        </li>

                        <li className="nav-item">
                            <NavLink className="nav-link" to="/checkout">
                                Checkout
                            </NavLink>
                        </li>

                    </ul>

                    {/* CART BUTTON */}
                    <Link to="/cart" className="btn btn-warning position-relative">

                        🛒 Carrito

                        <span className="badge bg-dark ms-2">
              {items.length}
            </span>

                    </Link>
                </div>
            </div>
        </nav>
    );
};