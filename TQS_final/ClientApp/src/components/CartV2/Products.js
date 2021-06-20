import React, { Component } from "react";
import { connect } from "react-redux";

import ClientService from "../../services/client.service";

class Products extends Component {

	constructor(props) {
		super(props);
		this.state = {
			store: null,
			products: [],
		};
	}



	handleClick = (id) => {
		this.props.addToCart(id);
	};

	componentDidMount() {
		ClientService.fetchProductsInStore(this.props.match.params.id).then(
			(response) => {
                console.log( response )

				if (response.status === 200) {
					this.setState({
						store: null,
						products: response.stores,
					});
				} else console.log(response.message);
			}
		);
    }


	render() {
        console.log()
		let itemList = this.state.products.map(item=>{
            console.log(item)
            return(
                <div className="card" key={item.id}>
                        <div style={{width:'auto', height:'100px'}}>
                           {/*  <img className="item-img" src={item.img} alt={item.title}
                                style={{width:'auto', height:'100px'}}/> */}
                            <span className="card-title">{item.name}</span>
                            <span to="/" className="btn-floating halfway-fab waves-effect waves-light red" onClick={()=>{this.handleClick(item.id)}}><i className="material-icons">add</i></span>
                        </div>

                        <div className="card-content">
                            <p><b>Price: {item.price}$</b></p>
                        </div>
                 </div>

            )
        })

		return (
			<div className="container">
				<h3 className="center">Our items</h3>
				<div className="box"> {itemList} </div>
			</div>
		);
	}
}

export default Products;
