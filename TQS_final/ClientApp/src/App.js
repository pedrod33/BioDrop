import React, { Component } from 'react';
import { Route } from 'react-router';
import { Layout } from './components/Layout';
import Home from './components/Home';
import 'bootstrap/dist/css/bootstrap.min.css';
import './custom.css'
import Admin from './components/Admin/Admin';
import 'mapbox-gl/dist/mapbox-gl.css';
import './index.css';
import Cart from './components/CartV2/Cart';

import Products from './components/CartV2/Products';
import Stores from './components/Stores/Stores';

class App extends Component {
  static displayName = App.name;

  render () {
      return (
  
      <Layout>

            <Route exact path='/' component={Home} />
            <Route exact path='/Admin' component={Admin} />
            <Route exact path='/Product' component={Products}/>
            <Route exact path='/Stores' component={Stores} />
            <Route exact path='/Cart' component={Cart} />
            

              </Layout>
       

              
         
    );
  }

}

export default App;
