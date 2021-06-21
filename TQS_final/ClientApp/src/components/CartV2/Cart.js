import React, { useState, useEffect } from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Title from "../Profile/Title";
// import ClientService from "../../services/client.service";

export default function Cart() {
	const [productsList, setProductsList] = useState(null);

	useEffect(() => {
		var orders = JSON.parse(sessionStorage.getItem("order"));

		if (orders !== null) setProductsList(orders.orderProducts);
	}, []);

	function calculatePrice() {
        if( productsList !== null) {
  
            var totalPrice = 0;
            for(var x=0; x < productsList.length; x++) {
                totalPrice += productsList[x].amount * productsList[x].product.price
            }

            return totalPrice;
        }
    }

	return (
		<React.Fragment>
			<Title>Shopping Cart</Title>
			<Table size="small">
				<TableHead>
					<TableRow>
						<TableCell>Product</TableCell>
						<TableCell>Origin</TableCell>
						<TableCell align="right">Quantity</TableCell>
						<TableCell align="right">Price</TableCell>
					</TableRow>
				</TableHead>
				{productsList !== null && (
					<TableBody>
						{productsList.map((prod) => (
							<TableRow key={prod.product.id}>
								<TableCell>{prod.product.name}</TableCell>
								<TableCell>{prod.product.origin}</TableCell>
								<TableCell align="right">
									{prod.amount}
								</TableCell>
								<TableCell align="right">
									{/* {calculatePrice(prod.product.price, prod.amount)}$ */}
									{prod.product.price}$
								</TableCell>
							</TableRow>
						))}
					</TableBody>
				)}
			</Table>

			<div>
                
                <p>Price: {calculatePrice()}$</p>

				<button
					style={{
						fontWeight: "bold",
						fontSize: "0.75rem",
						color: "white",
						backgroundColor: "grey",
						borderRadius: 8,
						padding: "3px 10px",
						display: "inline-block",
						marginTop: 20,
					}}
				>
					checkout
				</button>
			</div>
		</React.Fragment>
	);
}
