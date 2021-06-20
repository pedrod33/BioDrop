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
import { useRef, useEffect, useState } from 'react';

import * as mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax
import MapboxDirections from '@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions'
import '@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions.css'

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
        this.mapContainer = React.createRef();
    }

    componentDidMount() {
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(position => {
                var map = new mapboxgl.Map({
                    // container id specified in the HTML
                    container: this.mapContainer.current,

                    // style URL
                    style: 'mapbox://styles/mapbox/light-v10',

                    // initial position in [lon, lat] format
                    center: [position.coords.longitude, position.coords.latitude],

                    // initial zoom

                    zoom: 14
                });
                map.on('move', () => {
                    this.setState({
                        lng: map.getCenter().lng.toFixed(4),
                        lat: map.getCenter().lat.toFixed(4),
                        zoom: map.getZoom().toFixed(2)
                    });
                });
                var marker = new mapboxgl.Marker({
                    color: "red",
                    }).setLngLat([position.coords.longitude, position.coords.latitude])
                    .addTo(map);

                const directions = new MapboxDirections({
                    accessToken: mapboxgl.accessToken,
                    unit: 'metric',
                    profile: 'mapbox/driving'
                });
                map.addControl(directions, 'top-left');
            });
        }


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

        const { classes } = this.props;
        return (
            <div>
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
                                            <Typography variant="body" className={classes.text} gutterBottom></Typography>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col className={classes.divTitle}>
                                            <Typography variant="h6" className={classes.title} gutterBottom>Client</Typography>
                                        </Col>
                                        <Col className={classes.divText}>
                                            <Typography variant="body1" className={classes.text} gutterBottom></Typography>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col className={classes.divTitle}>
                                            <Typography variant="h6" className={classes.title} gutterBottom>Order weight</Typography>
                                        </Col>
                                        <Col className={classes.divText}>
                                            <Typography variant="body1" className={classes.text} gutterBottom></Typography>
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
                <hr />
                <div>
                    <div ref={this.mapContainer} className="map-container" />
                </div>
                <hr />
            </div>
        );
    }
}
export default withStyles(styles)(Rider);