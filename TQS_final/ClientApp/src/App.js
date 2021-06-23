import React, { Component } from "react";
import { Route } from "react-router";
import { Layout } from "./components/Layout";
import Home from "./components/Home";
import "bootstrap/dist/css/bootstrap.min.css";
import "./custom.css";
import "mapbox-gl/dist/mapbox-gl.css";
import "./index.css";
import Cart from "./components/CartV2/Cart";
import Products from "./components/CartV2/Products";
import Stores from "./components/Stores";
import Profile from "./components/Profile/Profile";
import OrdesHistory from "./components/Profile/OrdersHistory";
import ProfileSettings from "./components/Profile/ProfileSettings";
import Pending from "./components/Profile/Pending";
import Register from "./components/Register";

class App extends Component {
	static displayName = App.name;

	render() {
		return (
			<Layout>
				<Route exact path="/" component={Home} />
                <Route exact path="/register" component={Register} />
				<Route exact path="/Profile" component={Profile} />
				<Route exact path="/Product/:id" component={Products} />
				<Route exact path="/Stores" component={Stores} />
				<Route exact path="/Cart" component={Cart} />
				<Route exact path="/OrdesHistory" component={OrdesHistory} />
				<Route exact path="/Settings" component={ProfileSettings} />
				<Route exact path="/Pending" component={Pending} />
			</Layout>
		);
	}
}

export default App;
