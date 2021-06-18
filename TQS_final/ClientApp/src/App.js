import React, { Component } from 'react';
import { Route } from 'react-router';
import { Layout } from './components/Layout';
import Home from './components/Home';
import Rider from './Rider/Rider';

import 'bootstrap/dist/css/bootstrap.min.css';


import './custom.css'

import Album from './components/storelist';
import Admin from './components/Admin/Admin';
import shop from './components/ShoppingCart/shop';

import 'mapbox-gl/dist/mapbox-gl.css';
import './index.css';

class App extends Component {
  static displayName = App.name;

  render () {
      return (
  
      <Layout>

            <Route exact path='/' component={Home} />
            <Route exact path='/stores' component={Album} />
            <Route exact path='/Admin' component={Admin} />
            <Route exact path='/Shopping' component={shop} />
            <Route exact path='/Rider' component={Rider} />

              </Layout>
       

              
         
    );
  }

}

export default App;
