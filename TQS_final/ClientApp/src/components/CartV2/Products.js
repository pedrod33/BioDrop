import React, { Component } from "react";

import ClientService from "../../services/client.service";
import "bootstrap/dist/css/bootstrap.min.css";
import Card from "react-bootstrap/Card";

class Products extends Component {
	constructor(props) {
		super(props);
		this.state = {
			store: null,
			products: [],
            quantity: 1
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

        var quantity = document.getElementById("qnty").value

        if( quantity === null )
            quantity = 1;

        /* let { id } = useParams();
        console.log("id")
        console.log(id) */

		var storeId = window.location.pathname
		storeId = storeId.split("/")[1];
        console.log(storeId)

		ClientService.addOrderToCart(client.id, storeId, item.id, quantity).then(
			(response) => {
				if (response.status === 200) {
					console.log("Produto adicionado");
                    sessionStorage.setItem("storeId", storeId)
				} else console.log(response.message);
			}
		);
	}

   /*  handleChange(event) {
        console.log(event.target.value)

        this.setState({quantity: event.target.value});
      }
 */
	render() {
		let itemList = this.state.products.map((item) => {
			return (
				<Card style={{ width: "18rem" }} key={item.id}>
					<Card.Img
						variant="top"
						src={process.env.PUBLIC_URL + "images/carrot.jpg"}
					/>
					<Card.Body>
						<Card.Title>{item.name}</Card.Title>
						<Card.Text>
							Origin: {item.origin} <br /> Price: {item.price}$
							/kg
						</Card.Text>
						<div className="row">
							<button
								variant="primary"
								style={{
									fontWeight: "bold",
									fontSize: "0.75rem",
									color: "white",
									backgroundColor: "green",
									borderRadius: 8,
									padding: "8px 10px",
									textAlign: "center",
								}}
								onClick={() => {
									this.addToCart(item);
								}}
							>
								Add to cart
							</button>

                            <p style={{marginLeft: '30px', marginTop: '10px'}}>Qnt:    </p>
							<input type="number" placeholder="1" id="qnty" style={{ width: '30%', height: '90%', alignSelf: 'center'}} />
						</div>
					</Card.Body>
				</Card>
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
