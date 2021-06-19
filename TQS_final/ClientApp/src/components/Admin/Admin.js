import React from 'react';
import CardAdmin from './CardAdmin';
import '../../App.css';
import './Cards.css';


export default function Admin() {
    return (
        <div className='cards'>

            <div className='cards__container'>
                <div className='cards__wrapper'>
                    <ul className='cards__items'>
                       
                      <CardAdmin
                            src='images/spinach.jpg'
                            text='View Historic'
                            label='Historic'
                            path='/'
                        />
                        <CardAdmin
                            src='images/pingo.png'
                            text='Check Available Stores'
                            label='Stores'
                            path='/'
                        />
                        <CardAdmin
                            src='images/carrot.jpg'
                            text='Check Products'
                            label='Products'
                            path='/'
                        />
                        <CardAdmin
                            src='images/ervilha.jpg'
                            text='Configure Settings'
                            label='Settings'
                            path='/'
                        />
                    </ul>

                </div>
            </div>
        </div>
    );
}

