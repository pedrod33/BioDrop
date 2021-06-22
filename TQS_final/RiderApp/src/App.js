import React, { Component } from 'react';
import { Route } from 'react-router';
import { Layout } from './components/Layout';
import Rider from './Rider/Rider';
import Home from './components/Home';
import Register from './components/Register';

import 'bootstrap/dist/css/bootstrap.min.css';

import './custom.css'

import 'mapbox-gl/dist/mapbox-gl.css';
import './index.css';

class App extends Component {
  static displayName = App.name;

  render () {
      return (
  
      <Layout>
             <Route exact path='/' component={Home } />
              <Route exact path='/Rider' component={Rider} />
              <Route exact path='/Register' component={Register} />
              </Layout>
       

              
         
    );
  }

}

export default App;
