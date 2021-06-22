import React, { useState, useEffect } from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Title from "./Title";

export default function Historic() {
	const [orderList, setOrderList] = useState([]);

	useEffect(() => {
		/* var orders = JSON.parse(sessionStorage.getItem("order"));

		console.log(orders)

		if (orders !== null) setOrderList(orders); */

		if (sessionStorage.getItem("courier")) {
			console.log("há um courier -> " + sessionStorage["courier"])
		} else {
			console.log("n há nada")
		}

		var courier = JSON.parse(sessionStorage.getItem("courier"));

	}, []);

	var numOrders = 0;

	return (
		<React.Fragment>
			<Title>Recent Orders</Title>
			<Table size="small">
				<TableHead>
					<TableRow>
						<TableCell>No</TableCell>
						<TableCell>Delivery Address</TableCell>
						<TableCell align="right">Status</TableCell>
					</TableRow>
				</TableHead>
				{orderList.length !== 0 && (
					<TableBody>
						{orderList.map((order) => (
							<TableRow key={order.id}>
								<TableCell>{count++}</TableCell>
								<TableCell>{order.address}</TableCell>
								<TableCell align="right">
									{order.status}
								</TableCell>
							</TableRow>
						))}
					</TableBody>
				)}
			</Table>
		</React.Fragment>
	);
}