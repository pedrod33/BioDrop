import React, { Component } from 'react';
import { Route } from 'react-router';
import { Layout } from './components/Layout';
import Home from './components/Home';


import 'bootstrap/dist/css/bootstrap.min.css';


import './custom.css'


import Admin from './components/Admin/Admin';
import shop from './components/ShoppingCart/shop';

import 'mapbox-gl/dist/mapbox-gl.css';
import './index.css';
import Cart from './components/CartV2/Cart';

import storelist from './components/CartV2/storelist';

class App extends Component {
  static displayName = App.name;

  render () {
      return (
  
      <Layout>

            <Route exact path='/' component={Home} />
            <Route exact path='/stores' component={storelist} />
            <Route exact path='/Admin' component={Admin} />
            <Route exact path='/Shopping' component={shop} />
            <Route exact path='/Cart' component={Cart} />
            

              </Layout>
       

              
         
    );
  }

}

export default App;
