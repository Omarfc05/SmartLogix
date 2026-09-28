import { Link, NavLink } from "react-router-dom";
import { useCar } from "../contexts/CartContext";

export const Navbar = () => {
    const { items } = useCar();

    return (
        <nav
            className="navbar navbar-expand-lg sticky-top shadow-sm mb-4"
            style={{
                background: 'rgba(255, 255, 255, 0.55)',
                backdropFilter: 'blur(12px)',
                borderBottom: '1px solid rgba(255, 255, 255, 0.8)'
            }}
        >
            <div className="container">
                {/* BRAND */}
                <Link className="navbar-brand fw-bold" to="/" style={{ color: '#0077b6', fontSize: '1.5rem' }}>
                    🌐 SmartLogix
                </Link>

                {/* TOGGLE */}
                <button
                    className="navbar-toggler border-0"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#mainNav"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>

                {/* LINKS */}
                <div className="collapse navbar-collapse" id="mainNav">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0 fw-semibold">
                        <li className="nav-item">
                            <NavLink className="nav-link text-primary" to="/">Inicio</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link text-primary" to="/products">Productos</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link text-warning fw-bold" to="/admin">⚙️ Admin</NavLink>
                        </li>
                    </ul>

                    {/* CART BUTTON */}
                    <Link to="/cart" className="btn btn-bubble d-flex align-items-center gap-2">
                        🛒 Carrito
                        <span className="badge bg-white text-primary rounded-pill shadow-sm">
                            {items.length}
                        </span>
                    </Link>
                </div>
            </div>
        </nav>
    );
};