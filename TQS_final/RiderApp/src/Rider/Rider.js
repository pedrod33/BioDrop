import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";

import * as mapboxgl from "!mapbox-gl"; // eslint-disable-line import/no-webpack-loader-syntax
import MapboxDirections from "@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions";
import "@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions.css";

mapboxgl.accessToken =
    "pk.eyJ1IjoiYmlvZHJvcCIsImEiOiJja3B1MWkyaDUwdnZzMzFvYzNjcXFmNDdwIn0.lcBD-m2Fe3zsiW2ZOcEgcQ";

const styles = (theme) => ({
    center: {
        textAlign: "center",
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
            navigator.geolocation.getCurrentPosition((position) => {
                var map = new mapboxgl.Map({
                    // container id specified in the HTML
                    container: this.mapContainer.current,

                    // style URL
                    style: "mapbox://styles/mapbox/light-v10",

                    // initial position in [lon, lat] format
                    center: [
                        position.coords.longitude,
                        position.coords.latitude,
                    ],

                    // initial zoom

                    zoom: 14,
                });
                map.on("move", () => {
                    this.setState({
                        lng: map.getCenter().lng.toFixed(4),
                        lat: map.getCenter().lat.toFixed(4),
                        zoom: map.getZoom().toFixed(2),
                    });
                });
                //var marker = new mapboxgl.Marker({
                //    color: "red",
                //    }).setLngLat([position.coords.longitude, position.coords.latitude])
                //   .addTo(map);

                const directions = new MapboxDirections({
                    accessToken: mapboxgl.accessToken,
                    unit: "metric",
                    profile: "mapbox/driving",
                });
                map.addControl(directions, "top-left");
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
        return (
            <div>
                <hr />
                <div className={classes.center}>
                    <div ref={this.mapContainer} className="map-container" />
                </div>
                <hr />
            </div>
        );
    }
}
export default withStyles(styles)(Rider);
