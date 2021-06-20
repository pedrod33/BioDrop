import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Button } from '../Button/Button';
import './Navbar.css';

function Navbar() {
    const [click, setClick] = useState(false);
    const [button, setButton] = useState(true);

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

    window.addEventListener('resize', showButton);

    return (
        <>
            <nav className="navbar">
                <div className="navbar-container">
                    <Link to="/" className="navbar-logo" onClick={closeMobileMenu}>
                        BioDrop <i className='fab fa-typo3' />
                    </Link>
                    <div className='menu-icon' onClick={handleClick}>
                        <i className={click ? 'fas fa-times' : 'fas fa-bars'} />
                    </div>
                    <ul className={click ? 'nav-menu active' : 'nav-menu'}>
                        <li className='nav-item'>
                            <Link to='/'
                                className='nav-links'
                                onClick={closeMobileMenu}
                            >
                                Home
                            </Link>
                        </li>

                        <li className='nav-item'>
                            <Link to='/Admin'
                                className='nav-links'
                                onClick={closeMobileMenu}
                            >
                              Admin
                            </Link>
                        </li>
                        <li className='nav-item'>
                            <Link to='/stores'
                                className='nav-links'
                                onClick={closeMobileMenu}
                            >
                              Products
                            </Link>
                        </li>
                        <li className='nav-item'>
                            <Link to='/Cart'
                                className='nav-links'
                                onClick={closeMobileMenu}
                            >
                                <i className="material-icons"> shopping_cart</i>
                              
                            </Link>
                        </li>
                    </ul>
     
                </div>
            </nav>
        </>
    );
}

export default Navbar;