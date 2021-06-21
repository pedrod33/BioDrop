import React, { Component } from "react";

import ClientService from "../../services/client.service";

class Products extends Component {
	constructor(props) {
		super(props);
		this.state = {
			store: null,
			products: [],
		};
	}

    componentDidMount() {
		ClientService.fetchProductsInStore(this.props.match.params.id).then(
			(response) => {

				if (response.status === 200) {
					this.setState({
						store: null,
						products: response.stores,
					});
				} else console.log(response.message);
			}
		);
	}


    //TODO: mudar amount
	addToCart(item) {
        var client = JSON.parse(sessionStorage.getItem("client"));

        ClientService.addOrderToCart(client.id, item.id, 10).then(
			(response) => {

				if (response.status === 200) {
					console.log("Produto adicionado")
				} else console.log(response.message);
			}
		); 
	};


	render() {
		let itemList = this.state.products.map((item) => {
			return (
				<div className="card" key={item.id}>
					<div>
						<img
							className="item-img"
							src={"https://source.unsplash.com/random"}
							alt={item.title}
							style={{ width: "auto", height: "200px" }}
						/>
						<span className="card-title">{item.name}</span>
						<span
							to="/"
							className="btn-floating halfway-fab waves-effect waves-light red"
							onClick={() => {
								this.addToCart(item);
							}}
						>
							<i className="material-icons">add</i>
						</span>
					</div>

					<div className="card-content">
						<p>
							<b>Price: {item.price}$</b>
						</p>
					</div>
				</div>
			);
		});

		return (
			<div className="container">
				<h3 className="center">Our items</h3>
				<div className="box"> {itemList} </div>
			</div>
		);
	}
}

export default Products;
