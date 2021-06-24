import React, { useState, useEffect } from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Title from "./Title";
import ClientService from "../../services/client.service";

export default function OrdesHistory() {
	const [orderList, setOrderList] = useState([]);

	useEffect(() => {
		/* var orders = JSON.parse(sessionStorage.getItem("order"));

        console.log(orders)

		if (orders !== null) setOrderList(orders); */
		var client = JSON.parse(sessionStorage.getItem("client"));

		ClientService.fetchClientByUserId(client.id).then((response) => {
			if (response.status === 200) {
				var orders = response.client.orders;
				var filteredOrders = [];
				for (var x = 0; x < orders.length; x++)
					if (
						orders[x].status === "done" ||
						orders[x].status === "canceled"
					)
						filteredOrders.push(orders[x]);

				console.log(filteredOrders);

				if (filteredOrders.length !== 0) setOrderList(filteredOrders);
			} else console.log(response.message);
		});
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
						{orderList.map((order) => {
							numOrders++;
							console.log("order");
							console.log(order);
							return (
								<TableRow key={order.id}>
									<TableCell>{numOrders++}</TableCell>
									<TableCell>
										{order.address.city +
											" - " +
											order.address.completeAddress}
									</TableCell>
									<TableCell align="right">
										{order.status}
									</TableCell>
								</TableRow>
							);
						})}
					</TableBody>
				)}
			</Table>
			{orderList.length === 0 && (
				<p
					style={{
						textAlign: "center",
						marginTop: "20px",
					}}
				>
					You dont have any finished orders yet
				</p>
			)}
		</React.Fragment>
	);
}
