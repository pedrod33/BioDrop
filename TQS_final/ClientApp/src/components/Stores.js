import React, { useState, useEffect } from "react";

import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import CssBaseline from "@material-ui/core/CssBaseline";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import ClientService from "../services/client.service";
import { Link } from "react-router-dom";

const useStyles = makeStyles((theme) => ({
	icon: {
		marginRight: theme.spacing(2),
	},
	heroContent: {
		backgroundColor: theme.palette.background.paper,
		padding: theme.spacing(8, 0, 6),
	},
	heroButtons: {
		marginTop: theme.spacing(4),
	},
	cardGrid: {
		paddingTop: theme.spacing(8),
		paddingBottom: theme.spacing(8),
	},
	card: {
		height: "100%",
		display: "flex",
		flexDirection: "column",
	},
	cardMedia: {
		paddingTop: "56.25%", // 16:9
	},
	cardContent: {
		flexGrow: 1,
	},
	footer: {
		backgroundColor: theme.palette.background.paper,
		padding: theme.spacing(6),
	},
}));


export default function Stores(props) {
	const [stores, setStores] = useState(null);

	useEffect(() => {
		ClientService.fetchAllStores().then((response) => {
			if (response.status === 200) {
				setStores(response.stores);
			} else console.log(response.message);
		});
	}, []);

	const classes = useStyles();
	return (
		<React.Fragment>
			<CssBaseline />

			<main>
				{/* Hero unit */}
				<div className={classes.heroContent}>
					<Container maxWidth="sm">
						<Typography
							component="h1"
							variant="h3"
							align="center"
							color="textPrimary"
							gutterBottom
						>
							Our Stores
						</Typography>
					</Container>
				</div>
				<Container className={classes.cardGrid} maxWidth="md">
					{/* End hero unit */}
					{stores !== null && (
						<Grid container spacing={4}>
							{stores.map((store) => (
								<Grid item key={store.id} xs={12} sm={6} md={4}>
									<Card className={classes.card}>
										<CardMedia
											className={classes.cardMedia}
											image={process.env.PUBLIC_URL + "images/continente.png"}
											title="Image title"
										/>
										<CardContent
											className={classes.cardContent}
										>
											<Typography
												gutterBottom
												variant="h5"
												component="h2"
											>
												{store.name}
											</Typography>
											<Typography>
												{store.address.city} {" - "}{" "}
												{store.address.completeAddress}
											</Typography>
										</CardContent>
										<CardActions>
											{/* <Button
												className="btns"
												buttonStyle="btn--outline"
												buttonSize="btn--large"
											>
												Check Our Products
											</Button> */}
											<Link to={"/" + store.id + "/Product"}>
												<button
													style={{
														fontWeight: "bold",
														fontSize: "0.75rem",
														color: "white",
														backgroundColor:
															"green",
														borderRadius: 8,
														padding: "8px 10px",
														textAlign: "center",
													}}
												>
													Check store products
												</button>
											</Link>
										</CardActions>
									</Card>
								</Grid>
							))}
						</Grid>
					)}
				</Container>
			</main>
		</React.Fragment>
	);
}
