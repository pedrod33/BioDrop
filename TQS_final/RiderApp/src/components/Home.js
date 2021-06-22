import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import { Link } from "react-router-dom";

import Form from 'react-bootstrap/Form';


const styles = theme => ({
    root: {
        width: '80%',
        marginLeft: 15,
        marginRight: 15,
        marginTop: 10,
        marginBottom: 15,
        paddingBottom: 20
    },
    title: {
        textAlign: 'center',
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
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: "#ABA243"
    }

});

class Home extends Component {
    static displayName = Home.name;

    constructor(props) {
        super(props);
        this.state = { email: '', password: '', postId: '' };
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        console.log(this.state.email);
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            mode: 'cors',
            body: JSON.stringify({
                "email": this.state.email,
                "password": this.state.password,
            })
        };
        fetch('http://localhost:8089/deliveries-api/courier/login', requestOptions)
            .then((response) => {
                if (response.status === 200) {
                    return response.json();

                }
                return null;
            })
            .then((res) => {
                if (res === null) {
                    return;
                }
                console.log(res);
                let json = JSON.stringify(res);
                sessionStorage.setItem("courier", json);
                let courier = JSON.parse(sessionStorage.getItem("courier"))
                console.log(courier.email);
                this.props.history.push('/Rider')
            });
        event.preventDefault();
    }


    handleChangeEmail(event) {
        this.setState({ email: event.target.value });
        console.log(this.state.email);
    }

    handleChangePassword(event) {
        this.setState({ password: event.target.value });
    }


    render() {
        const { classes } = this.props;
        return (
            <div>
                <h1>Welcome to <span style={{ color: "#536732" }}>BioDrop - Rider</span></h1>
                <h4>You will be notified of your orders here!</h4>
                <hr />
                <div className={classes.mainDiv}>
                    <Card className={classes.root} variant="outlined">
                        <CardContent>
                            <Typography variant="h3" className={classes.title} color="textSecondary" gutterBottom>
                                Login
                            </Typography>
                            <Form className={classes.form} onSubmit={this.handleSubmit}>
                                <Form.Group className="mb-3" controlId="formEmail2">
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control type="email" onChange={this.handleChangeEmail} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formPassword2">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control type="password" onChange={this.handleChangePassword} />
                                </Form.Group>
                                <Row style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: 30 }}>
                                    <input type="submit" value="Login" style={{
                                        fontWeight: 'bold',
                                        fontSize: '0.75rem',
                                        color: 'white',
                                        backgroundColor: 'green',
                                        borderRadius: 8,
                                        padding: '3px 10px',
                                        display: 'inline-block',

                                    }} />
                                </Row>
                            </Form>
                            <Row style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', }}>
                                <Typography variant="h3" className={classes.title} color="textSecondary" gutterBottom>
                                    Create an account:
                                </Typography>
                                <Link to="/Register" style={{
                                    fontWeight: 'bold',
                                    fontSize: '0.75rem',
                                    color: 'white',
                                    backgroundColor: 'green',
                                    borderRadius: 8,
                                    padding: '3px 10px',
                                    display: 'inline-block',
                                    marginLeft: 15,
                                    marginBottom: 7

                                }} className="outline-success">Register</Link>
                            </Row>
                        </CardContent>
                    </Card>
                </div>
            </div>
        );
    }
}
export default withStyles(styles)(Home);