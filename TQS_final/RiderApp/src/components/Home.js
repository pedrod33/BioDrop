import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";



import RiderService from "../services/rider.services"
import SignIn from "./SignIn";

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

class Home extends Component {
    static displayName = Home.name;

    constructor(props) {
        super(props);
        this.state = {
            email: "",
            password: "",
        };
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        console.log(this.state)
      
        RiderService.login(this.state.email, this.state.password).then( (response) => {
            console.log(response)
            if( response.status === 200) {
                console.log(response.courier)
                this.props.history.push("/rider");
            }
        })
        event.preventDefault();
    }

    handleChangeEmail(event) {
        this.setState({ email: event.target.value });
    }

    handleChangePassword(event) {
        this.setState({ password: event.target.value });
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
                   <SignIn emailChange={this.handleChangeEmail} passwordChange={this.handleChangePassword} submitLogin={this.handleSubmit}/>
                </div>
            </div>
        );
    }
}
export default withStyles(styles)(Home);
