import { NavLink} from "react-router-dom"

export const Navbar = () => {
    return (
        <>
            <nav className="navbar mb-3 sticky-top">
                <div className="container">
                    <a className="navbar-brand fw-bold text-navbar" href="#">
                        Smarthlogix
                    </a>
                    <div className="collapse navabr-collapse" id="mainNav">
                        <ul className="navbar-nav me-auto">
                            <li className="nav-item">
                                <NavLink to="/" className="nav-link text-navbar">
                                    Home
                                </NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink to="/products" className="nav-link text-navbar">
                                    Products
                                </NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink to="/derp" className="nav-link text-navbar">
                                    derp
                                </NavLink>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    )
}