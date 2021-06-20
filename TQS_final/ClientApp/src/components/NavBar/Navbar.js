import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Button } from "../Button/Button";
import "./Navbar.css";

function Navbar() {
	const [click, setClick] = useState(false);
	const [button, setButton] = useState(true);
	const [user, setUser] = useState(null);

	const handleClick = () => setClick(!click);
	const closeMobileMenu = () => setClick(false);

	const showButton = () => {
		if (window.innerWidth <= 960) {
			setButton(false);
		} else {
			setButton(true);
		}
	};

	useEffect(() => {
		showButton();
	}, []);

	window.addEventListener("resize", showButton);

    setInterval(() => {
        var session_user = sessionStorage.getItem("client");

        if( session_user !== null )
            setUser(session_user)
    }, 1000);


	return (
		<>
			<nav className="navbar">
				<div className="navbar-container">
					<Link
						to="/"
						className="navbar-logo"
						onClick={closeMobileMenu}
					>
						BioDrop <i className="fab fa-typo3" />
					</Link>
					<div className="menu-icon" onClick={handleClick}>
						<i className={click ? "fas fa-times" : "fas fa-bars"} />
					</div>
					{user !== null && (
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
										{" "}
										shopping_cart
									</i>
								</Link>
							</li>
						</ul>
					)}
				</div>
			</nav>
		</>
	);
}

export default Navbar;
