import React, { useState, useEffect } from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Title from "./Title";
import ClientService from "../../services/client.service";

export default function Pending() {
	const [orderList, setOrderList] = useState([]);
	const [detailsVisibility, setDetailsVisibility] = useState(false);

	useEffect(() => {
		var client = JSON.parse(sessionStorage.getItem("client"));

		ClientService.fetchClientByUserId(client.id).then((response) => {
			if (response.status === 200) {
				setOrderList(response.client.orders);
			} else console.log(response.message);
		});

		//TODO: ir buscar delivery info
	}, []);

	function changeDetailsVisibility() {
		setDetailsVisibility(!detailsVisibility);
	}

	function closeDelivery(orderId, status, index) {
		//setDetailsVisibility(!detailsVisibility);

	
		ClientService.updateOrderStatus2(
			orderId, status
		).then((response) => {
			if (response.status === 200) {
				console.log(response);
				/* let newList = orderList.splice(index,1);
				//newList.splice(index,1)
				setOrderList(newList);
				console.log(newList) */
				window.location.reload(); 
				//setOrderList(response.client.orders);
			} else console.log(response.message);
		});

	}

	var orderNo = 1;
	return (
		<React.Fragment>
			<Title>Pending Orders</Title>

			<div>
				<Table size="small">
					<TableHead>
						<TableRow>
							<TableCell>Order No</TableCell>
							<TableCell>Deliver Address</TableCell>
							<TableCell align="right">Rider</TableCell>
							<TableCell align="right">Vehicle</TableCell>
							<TableCell align="right">Status</TableCell>
							<TableCell align="right">Detalhes</TableCell>
						</TableRow>
					</TableHead>

					<TableBody>
						{orderList.map((order, index) => {
								if(order.status === "waiting" || order.status === "waiting_for_rider") {
									return (
										(order.status === "waiting" || order.status === "waiting_for_rider") && (
											<TableRow key={order.id}>
												<TableCell>{orderNo++}</TableCell>
												<TableCell>
													{order.deliverAddress.city + " - " + order.deliverAddress.completeAddress}
												</TableCell>
												<TableCell align="right">
													rider
												</TableCell>
												<TableCell align="right">
													vehicle
												</TableCell>
												<TableCell align="right">
													{order.status}
												</TableCell>
												<TableCell align="right">
													<button
														onClick={() => {
															changeDetailsVisibility();
														}}
													>
														+
													</button>
												</TableCell>
											</TableRow>
										)
									)
								} else if ( order.status === "accepted"){
									return (
										<TableRow key={order.id}>
											<TableCell>{orderNo++}</TableCell>
                                            <TableCell>
                                                {order.deliverAddress.city + " - " + order.deliverAddress.completeAddress}
                                            </TableCell>
											<TableCell align="right">
												rider
											</TableCell>
											<TableCell align="right">
												vehicle
											</TableCell>
											<TableCell align="right">
												{order.status}
											</TableCell>
											<TableCell align="right">
												<button
													onClick={() => {
														closeDelivery(order.id, "Delivered", index);
													}}
												>
													Confirm Delivery
												</button>
												<button
													onClick={() => {
														closeDelivery(order.id, "Not Delivered", index);
													}}
												>
													Not Delivered
												</button>
											</TableCell>
										</TableRow>
									)	
								}
							})}
                            
					</TableBody>
				</Table>
				{detailsVisibility === true && (
					<div>
						<h1>details</h1>
					</div>
				)}
			</div>
		</React.Fragment>
	);
}
