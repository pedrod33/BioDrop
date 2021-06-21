import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Row from 'react-bootstrap/Row';
import Form from 'react-bootstrap/Form';
import { Link } from "react-router-dom";

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

class Register extends Component {
    static displayName = Register.name;

    constructor(props) {
        super(props);
        this.state = { email: '', password: '', fName: '', lName: '', phone: '', sex: '', status: '', options: [] };
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleChangeFname = this.handleChangeFname.bind(this);
        this.handleChangeLname = this.handleChangeLname.bind(this);
        this.handleChangePhone = this.handleChangePhone.bind(this);
        this.handleChangeSex = this.handleChangeSex.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        fetch('https://localhost:8089/deliveries-api/vehicle/all')
            .then(response => response.json())
            .then(data => this.setState({ /*options: data */ }));
    }

    handleSubmit(event) {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            mode: 'cors',
            body: JSON.stringify({
                "name": this.state.fName,
                "email": this.state.email,
                "password": this.state.password,
                "gender": this.state.sex,
                "phoneNumber": this.state.phone,
                "supervisor_id": "1",
                "vehicle_id": "2"
            })
        };
        fetch('http://localhost:8089/deliveries-api/courier/register', requestOptions)
            .then((response) => {
                this.setState({ status: response.status })
                if (response.status === 201) {
                    console.log("fez isto")
                    this.props.history.push('/Rider')
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

    handleChangeFname(event) {
        this.setState({ fName: event.target.value });
    }

    handleChangeLname(event) {
        this.setState({ lName: event.target.value });
    }

    handleChangePhone(event) {
        this.setState({ phone: event.target.value });
    }

    handleChangeSex(event) {
        this.setState({ sex: event.target.value });
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
                            <Typography variant="h3" className={classes.title} gutterBottom>
                                Register
                                </Typography>
                            <Form className={classes.form} onSubmit={this.handleSubmit}>
                                <Form.Group className="mb-3" controlId="formEmail">
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control type="email" onChange={this.handleChangeEmail} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formPassword">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control type="password" onChange={this.handleChangePassword} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formNome">
                                    <Form.Label>First name</Form.Label>
                                    <Form.Control type="text" rows={1} onChange={this.handleChangeFname} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formApelido">
                                    <Form.Label>Last name</Form.Label>
                                    <Form.Control type="text" rows={1} onChange={this.handleChangeLname} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formTelefone">
                                    <Form.Label>Cellphone number</Form.Label>
                                    <Form.Control type="number" onChange={this.handleChangePhone} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formSexo">
                                    <select value={this.state.sex} onChange={this.handleChangeSex} >
                                        <option value="Male">Male</option>
                                        <option value="Female">Female</option>
                                        <option value="Other">Other</option>
                                    </select>
                                </Form.Group>
                                <Row style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: 30 }}>
                                    <input type="submit" value="Register" style={{
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
                                    Have an account?
                                </Typography>
                                <Link to="/" style={{
                                    fontWeight: 'bold',
                                    fontSize: '0.75rem',
                                    color: 'white',
                                    backgroundColor: 'green',
                                    borderRadius: 8,
                                    padding: '3px 10px',
                                    display: 'inline-block',
                                    marginLeft: 15,
                                    marginBottom: 7

                                }} className="outline-success">Login</Link>
                            </Row>
                        </CardContent>
                    </Card>
                </div>
            </div>
        );
    }
}
export default withStyles(styles)(Register);