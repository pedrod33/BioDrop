import React, { useState, useEffect } from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Title from "./Title";

export default function Pending() {
	const [orderList, setOrderList] = useState([]);

	useEffect(() => {
		var orders = JSON.parse(sessionStorage.getItem("order"));

		if (orders !== null) setOrderList(orders);
	}, []);

	return (
		<React.Fragment>
			<Title>Pending Orders</Title>
			<Table size="small">
				<TableHead>
					<TableRow>
						<TableCell>Product</TableCell>
						<TableCell>Origin</TableCell>
						<TableCell align="right">Quantity</TableCell>
						<TableCell align="right">Price</TableCell>
						<TableCell align="right">Status</TableCell>
					</TableRow>
				</TableHead>
				{orderList.length !== 0 && (
					<TableBody>
						{orderList.orderProducts.map((prod) => (
							<TableRow key={prod.id}>
								<TableCell>{prod.product.name}</TableCell>
								<TableCell>{prod.product.origin}</TableCell>
								<TableCell align="right">
									{prod.amount}
								</TableCell>
								<TableCell align="right">
									{prod.product.price}$
								</TableCell>
								<TableCell align="right">
									{/* {orderList[prod.id].status} */}
								</TableCell>
							</TableRow>
						))}
					</TableBody>
				)}
			</Table>
		</React.Fragment>
	);
}
