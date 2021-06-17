import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Box from '@material-ui/core/Box';
/*import Button from '@material-ui/core/Button';*/
import { Button } from 'reactstrap';
import Dropdown from 'react-bootstrap/Dropdown';
import mapImage from './../images/bNAN5.png';
import { useRef, useEffect, useState } from 'react';

import * as mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax

mapboxgl.accessToken = 'pk.eyJ1IjoiYmlvZHJvcCIsImEiOiJja3B1MWkyaDUwdnZzMzFvYzNjcXFmNDdwIn0.lcBD-m2Fe3zsiW2ZOcEgcQ';


const styles = theme => ({
    photo: {
        width: '100%',
        height: '100%',
        resizeMode: 'stretch',
    },
    row: {
        margin: 10,
        backgroundColor: '#ABA243',
        padding: 10,
    },
    center: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    card: {
        minWidth: 275,
        marginLeft: 15,
        marginRight: 15,
        marginTop: 10,
        marginBottom: 15,
        paddingBottom: 20,
    },
    title: {
        textAlign: 'right',
    },
    text: {
        textAlign: 'left',
    },
    divTitle: {
        flex: 1,
    },
    divText: {
        flex: 2,
    },
    title1: {
        textAlign: 'center',
    },
    rowB: {
        marginTop: 10,
        marginBottom: 10,
    },
    sidebar: {
        backgroundColor: "rgb(35, 55, 75)",
        color: '#ffffff',
        paddingtop: 6,
        paddingright: 12,
        margin: 12,
    },
});

class Rider extends Component {


    static displayName = Rider.name;
    constructor(props) {
        super(props);
        this.state = {
            lat: this.getCoords()[0], 
            lng: this.getCoords()[1],
            zoom: 11
        };
        this.mapContainer = React.createRef();
    }

    componentDidMount() {
        const { lat, lng, zoom } = this.state;

        const map = new mapboxgl.Map({
            container: this.mapContainer.current,
            style: 'mapbox://styles/mapbox/streets-v11',
            center: [lng, lat],
            zoom: zoom
        });

        map.on('move', () => {
            this.setState({
                lng: map.getCenter().lng.toFixed(4),
                lat: map.getCenter().lat.toFixed(4),
                zoom: map.getZoom().toFixed(2)
            });
        });        
    }

    getCoords() {
        if (navigator.geolocation) {
            navigator.geolocation.watchPosition(function (position) {
                window.lat = position.coords.latitude;
                window.lng = position.coords.longitude;
                
            });
            return [window.lat, window.lng];
        }
    }

    render() {

        if (navigator.geolocation) {
            navigator.geolocation.watchPosition(function (position) {
                const lat = position.coords.latitude;
                const lng = position.coords.longitude;
                window.lat = lat;
                window.lng = lng;
                console.log(window.lat);
                console.log(window.lng);
            });
        }

        const { lng, lat, zoom } = this.state;
        const { classes } = this.props;
        return (
            <div>
                <h1>Rider interface</h1>
                <h4>Accept the request to get more informations about it!</h4>
                <hr />
                <Row className={classes.center}>
                    <Col>
                        <Box>
                            <Card className={classes.card} variant="outlined">
                                <CardContent>
                                    <Row>
                                        <Col className={classes.divTitle}>
                                            <Typography variant="h3" className={classes.title1} gutterBottom>New order!</Typography>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col className={classes.divTitle}>
                                            <Typography variant="h6" className={classes.title} gutterBottom>Address</Typography>
                                        </Col>
                                        <Col className={classes.divText}>
                                            <Typography variant="body" className={classes.text} gutterBottom>Universidade de Aveiro</Typography>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col className={classes.divTitle}>
                                            <Typography variant="h6" className={classes.title} gutterBottom>Client</Typography>
                                        </Col>
                                        <Col className={classes.divText}>
                                            <Typography variant="body1" className={classes.text} gutterBottom>João Pereira</Typography>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col className={classes.divTitle}>
                                            <Typography variant="h6" className={classes.title} gutterBottom>Order weight</Typography>
                                        </Col>
                                        <Col className={classes.divText}>
                                            <Typography variant="body1" className={classes.text} gutterBottom>5.00 kg</Typography>
                                        </Col>
                                    </Row>
                                </CardContent>
                            </Card>
                        </Box>
                    </Col>
                    <Col>
                        <Box>
                            <Row className={classes.rowB}>
                                <Button outline color="success">Accept</Button>
                            </Row>
                            <Row className={classes.rowB}>
                                <Button outline color="danger">Decline</Button>
                            </Row>
                        </Box>
                    </Col>
                </Row>
                <div>
                    <div className="sidebar">
                        Latitude: {lat} | Longitude: {lng}
                    </div>
                    <div ref={this.mapContainer} className="map-container" />
                </div>
            </div>
        );
    }
}
export default withStyles(styles)(Rider);