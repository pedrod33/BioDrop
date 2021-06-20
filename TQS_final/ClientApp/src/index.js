import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import cartReducer from '../src/components/reducers/cartReducer';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import './index.css';
const store = createStore(cartReducer);
const baseUrl = document.getElementsByTagName('base')[0].getAttribute('href');
const rootElement = document.getElementById('root');

window.lat = 40.75;
window.lng = -8.40;

ReactDOM.render(
  <BrowserRouter basename={baseUrl} store={store}>
    <Provider store={store}><App /></Provider>
    
  </BrowserRouter>,
  rootElement);

registerServiceWorker();

