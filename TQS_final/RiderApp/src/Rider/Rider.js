import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';

import * as mapboxgl from '!mapbox-gl'; // eslint-disable-line import/no-webpack-loader-syntax
import MapboxDirections from '@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions'
import '@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions.css'

import Grid from '@material-ui/core/Grid';

mapboxgl.accessToken = 'pk.eyJ1IjoiYmlvZHJvcCIsImEiOiJja3B1MWkyaDUwdnZzMzFvYzNjcXFmNDdwIn0.lcBD-m2Fe3zsiW2ZOcEgcQ';


const styles = theme => ({
    center: {
        textAlign: 'center',
    },
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
});

class Rider extends Component {


    static displayName = Rider.name;
    constructor(props) {
        super(props);
        this.mapContainer = React.createRef();
        this.state = { status: '', delivery: '' };
    }

    componentDidMount() {

        if (sessionStorage.getItem("courier")) {
            console.log("há um courier -> " + sessionStorage["courier"]);
            var courier = JSON.parse(sessionStorage.getItem("courier"));

            fetch('http://localhost:8089/deliveries-api/deliveries/findAll')
                .then((response) => {
                    this.setState({ status: response.status, deliveries: response.items })
                    if (response.status === 200) {
                        for (d in this.state.delivery) {
                            if (d.getStatus() < 2 && d.getCourier().getId() == courier.getId()) {
                                this.state.delivery = d;
                            }
                        }
                    }
                })

        } else {
            console.log("n há nada")
        }

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
        const { classes } = this.props;
        const delivery = this.state.delivery;
        return (
            <div>
                <hr />
                <div className={classes.center}>
                    <div ref={this.mapContainer} className="map-container" />
                </div>
                <hr />
                <div className={classes.center}>

                </div>
                <hr />
                <Grid container spacing={3}>
                    <Grid item xs={4}>
                        <Paper className={classes.paper}>{ delivery.getId() }</Paper>
                    </Grid>
                    <Grid item xs={4}>
                        <Paper className={classes.paper}>{delivery.getVehicle()}</Paper>
                    </Grid>
                    <Grid item xs={4}>
                        <Paper className={classes.paper}>{delivery.getCourier()}</Paper>
                    </Grid>
                </Grid>
            </div>
        );
    }
}
export default withStyles(styles)(Rider);