import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";

import SignUp from "./SignUp";
import riderServices from "../services/rider.services";

const styles = (theme) => ({
	root: {
		width: "80%",
		marginLeft: 15,
		marginRight: 15,
		marginTop: 10,
		marginBottom: 15,
		paddingBottom: 20,
	},
	title: {
		textAlign: "center",
		fontSize: 30,
		color: "#536732",
		fontWeight: "bold",
	},
	pos: {
		marginBottom: 12,
	},
	form: {
		marginTop: 30,
	},
	mainDiv: {
		display: "flex",
		justifyContent: "center",
		alignItems: "center",
	},
});

class Register extends Component {
	static displayName = Register.name;

	constructor(props) {
		super(props);
		this.state = {
			email: "",
			password: "",
			name: "",
			phone: "",
			sex: "",
			vehicle: "",
		};
		this.handleChangeEmail = this.handleChangeEmail.bind(this);
		this.handleChangePassword = this.handleChangePassword.bind(this);
		this.handleChangeName = this.handleChangeName.bind(this);
		this.handleChangePhone = this.handleChangePhone.bind(this);
		this.handleChangeSex = this.handleChangeSex.bind(this);
		this.handleChangeVehicle = this.handleChangeVehicle.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	componentDidMount() {
		fetch("https://localhost:8089/deliveries-api/vehicle/all")
			.then((response) => response.json())
			.then((data) =>
				this.setState({
					/*options: data */
				})
			);
	}

	handleSubmit(event) {
		console.log(this.state);

		/* const requestOptions = {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			mode: "cors",
			body: JSON.stringify(newCourier),
		};
		fetch(
			"http://localhost:8089/deliveries-api/courier/register",
			requestOptions */

		riderServices
			.register(
				this.state.name,
				this.state.email,
				this.state.password,
				this.state.sex,
				this.state.phone,
                this.state.vehicle
			)
			.then((response) => {
				console.log(response);
				console.log(response.status);

				if (response.status === 201) {
					this.props.history.push("/");
				} else {
					this.props.history.push("/register");
				}
			});

		event.preventDefault();
	}

	handleChangeEmail(event) {
		this.setState({ email: event.target.value });
	}

	handleChangePassword(event) {
		this.setState({ password: event.target.value });
	}

	handleChangeName(event) {
		this.setState({ name: event.target.value });
	}

	handleChangePhone(event) {
		this.setState({ phone: event.target.value });
	}

	handleChangeSex(event) {
		this.setState({ sex: event.target.value });
	}

	handleChangeVehicle(event) {
		this.setState({ vehicle: event.target.value });
	}

	render() {
		const { classes } = this.props;
		return (
			<div>
				<h1>
					Welcome to{" "}
					<span style={{ color: "#536732" }}>BioDrop - Rider</span>
				</h1>
				<h4>You will be notified of your orders here!</h4>
				<hr />
				<div className={classes.mainDiv}>
					<SignUp
						nameChange={this.handleChangeName}
						emailChange={this.handleChangeEmail}
						passwordChange={this.handleChangePassword}
						genderChange={this.handleChangeSex}
						phoneChange={this.handleChangePhone}
						vehicleChange={this.handleChangeVehicle}
						onSubmit={this.handleSubmit}
					/>
				</div>
			</div>
		);
	}
}
export default withStyles(styles)(Register);
