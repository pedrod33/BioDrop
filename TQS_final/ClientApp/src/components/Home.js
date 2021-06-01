import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form';
import Dropdown from 'react-bootstrap/Dropdown';

const styles = theme => ({
    root: {
        minWidth: 275,
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
    }

});

class Home extends Component {
    static displayName = Home.name;

    render() {
        const { classes } = this.props;
        return (
          <div>
                <h1>Welcome to <span style={{ color: "#536732" }}>biodrop</span></h1>
                <h4>Get your groceries deliver to you fresh!</h4>
                <hr />
                <Row style={{ backgroundColor: "#ABA243" }}>   
                    <Col>
                        <Card className={classes.root} variant="outlined">
                            <CardContent>
                                <Typography variant="h3" className={classes.title} gutterBottom>
                                    Register
                                </Typography>
                                <Form className={classes.form}>
                                    <Form.Group className="mb-3" controlId="formEmail">
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control type="email" />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formPassword">
                                        <Form.Label>Password</Form.Label>
                                        <Form.Control type="password" />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formNome">
                                        <Form.Label>First name</Form.Label>
                                        <Form.Control type="text" rows={1} />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formApelido">
                                        <Form.Label>Last name</Form.Label>
                                        <Form.Control type="text" rows={1} />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formTelefone">
                                        <Form.Label>Cellphone number</Form.Label>
                                        <Form.Control type="number" />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formTelefone">
                                        <Dropdown >
                                            <Dropdown.Toggle variant="Light" id="dropdown-basic">
                                                Sex
                                            </Dropdown.Toggle>

                                            <Dropdown.Menu>
                                                <Dropdown.Item>Male</Dropdown.Item>
                                                <Dropdown.Item>Female</Dropdown.Item>
                                                <Dropdown.Item>Other</Dropdown.Item>
                                            </Dropdown.Menu>
                                        </Dropdown>
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formConta">
                                        <Form.Label>Account type:</Form.Label>
                                        <Row>   
                                            <Col>
                                            <Form.Check
                                                type={'radio'}
                                                id={'inline-radio-1'}
                                                label={'Client'}
                                                />
                                            </Col>
                                            <Col>
                                            <Form.Check
                                                type={'radio'}
                                                id={'inline-radio-2'}
                                                label={'Driver'}
                                                />
                                            </Col>
                                        </Row>
                                    </Form.Group>
                                </Form>
                            </CardContent>
                        </Card>
                    </Col>
                    <Col> 
                        <Card className={classes.root} variant="outlined">
                            <CardContent>
                                <Typography variant="h3" className={classes.title} color="textSecondary" gutterBottom>
                                    Login
                                </Typography>
                                <Form className={classes.form}>
                                    <Form.Group className="mb-3" controlId="formEmail">
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control type="email" />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="formPassword">
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