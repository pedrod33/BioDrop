import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Dropdown from "react-bootstrap/Dropdown";

import ClientService from "../services/client.service";


const styles = (theme) => ({
	root: {
		minWidth: 275,
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
});

class Home extends Component {
	static displayName = Home.name;

	constructor(props) {
		super(props);
		this.state = {
            user: null
        };
	}

	login() {
		var emailInput = document.getElementById("formLoginEmail").value;
		var passwordInput = document.getElementById("formLoginPassword").value;

		if (passwordInput !== "" && emailInput !== "")
			ClientService.login(emailInput, passwordInput).then((response) => {
				console.log(response);

				if (response.status === 200) {
                    //this.setState({user: response})
                    this.props.history.push({pathname: "/stores", state: { user: response }});
                } 
				else console.log(response.message);
			});

		return;
	}

	register() {
		var nameInput = document.getElementById("formRegisterName").value;
		var emailInput = document.getElementById("formRegisterEmail").value;
		var passwordInput = document.getElementById(
			"formRegisterPassword"
		).value;
		var phoneNumberInput = document.getElementById(
			"formRegisterPhoneNumber"
		).value;
		var genderInput = "Male"; //document.getElementById("formRegisterGender");

		if (
			nameInput != null &&
			emailInput != null &&
			passwordInput != null &&
			phoneNumberInput != null &&
			genderInput != null
		)
			ClientService.register(
				nameInput,
				emailInput,
				passwordInput,
				genderInput,
				phoneNumberInput
			).then((response) => {
				console.log(response);

				if (response.status === 200) console.log(response.client);
				else console.log(response.message);
			});
	}

	render() {
		const { classes } = this.props;
		return (
			<div>
				<button onClick={() => this.login()}>login</button>

				<button onClick={() => this.register()}>register</button>

				<h1>
					Welcome to <span style={{ color: "#536732" }}>BioDrop</span>
				</h1>
				<h4>Get your groceries deliver to you fresh!</h4>
				<hr />
				<Row style={{ backgroundColor: "#ABA243" }}>
					<Col>
						<Card className={classes.root} variant="outlined">
							<CardContent>
								<Typography
									variant="h3"
									className={classes.title}
									gutterBottom
								>
									Register
								</Typography>
								<Form className={classes.form}>
									<Form.Group
										className="mb-3"
										controlId="formRegisterEmail"
									>
										<Form.Label>Email</Form.Label>
										<Form.Control type="email" />
									</Form.Group>
									<Form.Group
										className="mb-3"
										controlId="formRegisterPassword"
									>
										<Form.Label>Password</Form.Label>
										<Form.Control type="password" />
									</Form.Group>
									<Form.Group
										className="mb-3"
										controlId="formRegisterName"
									>
										<Form.Label>First name</Form.Label>
										<Form.Control type="text" rows={1} />
									</Form.Group>
									<Form.Group
										className="mb-3"
										controlId="formRegisterPhoneNumber"
									>
										<Form.Label>
											Cellphone number
										</Form.Label>
										<Form.Control type="number" />
									</Form.Group>
									<Form.Group
										className="mb-3"
										controlId="formRegisterGenderGroup"
									>
										<Dropdown id="dropdown-basic">
											<Dropdown.Toggle
												variant="Light"
												id="formRegisterGender"
											>
												Sex
											</Dropdown.Toggle>

											<Dropdown.Menu>
												<Dropdown.Item>
													Male
												</Dropdown.Item>
												<Dropdown.Item>
													Female
												</Dropdown.Item>
											</Dropdown.Menu>
										</Dropdown>
									</Form.Group>
								</Form>
							</CardContent>
						</Card>
					</Col>
					<Col>
						<Card className={classes.root} variant="outlined">
							<CardContent>
								<Typography
									variant="h3"
									className={classes.title}
									color="textSecondary"
									gutterBottom
								>
									Login
								</Typography>
								<Form className={classes.form}>
									<Form.Group
										className="mb-3"
										controlId="formLoginEmail"
									>
										<Form.Label>Email</Form.Label>
										<Form.Control type="email" />
									</Form.Group>
									<Form.Group
										className="mb-3"
										controlId="formLoginPassword"
									>
										<Form.Label>Password</Form.Label>
										<Form.Control type="password" />
									</Form.Group>
								</Form>
							</CardContent>
						</Card>
					</Col>
				</Row>
			</div>
		);
	}
}
export default withStyles(styles)(Home);
