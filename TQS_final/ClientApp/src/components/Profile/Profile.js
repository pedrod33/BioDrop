﻿import React from 'react';
import ProfileCards from './ProfileCards';
import '../../App.css';
import './Cards.css';


export default function Profile() {
    return (
        <div className='cards'>

            <div className='cards__container'>
                <div className='cards__wrapper'>
                    <ul className='cards__items'>
                       
                      <ProfileCards
                            src='images/spinach.jpg'
                            text='View Historic'
                            label='Historic'
                            path='/Order'
                        />
                        <ProfileCards
                            src='images/pingo.png'
                            text='Check Available Stores'
                            label='Stores'
                            path='/Stores'
                        />
                        <ProfileCards
                            src='images/carrot.jpg'
                            text='Pending Orders'
                            label='Pending'
                            path='/Pending'
                        />
                        <ProfileCards
                            src='images/ervilha.jpg'
                            text='Check Profile'
                            label='Profile'
                            path='/Settings'
                        />
                    </ul>

                </div>
            </div>
        </div>
    );
}

