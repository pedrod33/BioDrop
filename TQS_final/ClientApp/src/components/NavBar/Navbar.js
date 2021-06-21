import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
	const [click, setClick] = useState(false);
	const [logged, setLogged] = useState(false);

	const handleClick = () => setClick(!click);
	const closeMobileMenu = () => setClick(false);


    const logout = () => {
        sessionStorage.removeItem("client");
    }

	useEffect(() => {
		const interval = setInterval(() => {
			if (sessionStorage.getItem("client") !== null) setLogged(true);
			else setLogged(false);
		}, 1000);

		return () => clearInterval(interval);
	}, []);


	return (
		<>
			<nav className="navbar">
				{logged === true && (
					<div className="navbar-container">
						<Link
							to="/"
							className="navbar-logo"
							onClick={closeMobileMenu}
						>
							BioDrop <i className="fab fa-typo3" />
						</Link>
						<div className="menu-icon" onClick={handleClick}>
							<i
								className={
									click ? "fas fa-times" : "fas fa-bars"
								}
							/>
						</div>
						<ul className={click ? "nav-menu active" : "nav-menu"}>
							<li className="nav-item">
								<Link
									to="/Profile"
									className="nav-links"
									onClick={closeMobileMenu}
								>
									Profile
								</Link>
							</li>
							<li className="nav-item">
								<Link
									to="/Stores"
									className="nav-links"
									onClick={closeMobileMenu}
								>
									Stores
								</Link>
							</li>
							<li className="nav-item">
								<Link
									to="/Cart"
									className="nav-links"
									onClick={closeMobileMenu}
								>
									<i className="material-icons">
										shopping_cart
									</i>
								</Link>
							</li>
							<li className="nav-item">
								<Link
									to="/"
									className="nav-links"
									onClick={logout}
								>
									<i className="material-icons">
										logout  
									</i>
								</Link>
							</li>
						</ul>
					</div>
				)}
			</nav>
		</>
	);
}

export default Navbar;
