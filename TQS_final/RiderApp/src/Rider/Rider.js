import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";

import * as mapboxgl from "!mapbox-gl"; // eslint-disable-line import/no-webpack-loader-syntax
import MapboxDirections from "@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions";
import "@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions.css";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import RiderService from "../services/rider.services";
import ExploreIcon from '@material-ui/icons/Explore';
mapboxgl.accessToken = 
"pk.eyJ1IjoiYmlvZHJvcCIsImEiOiJja3B1MWkyaDUwdnZzMzFvYzNjcXFmNDdwIn0.lcBD-m2Fe3zsiW2ZOcEgcQ";



const styles = (theme) => ({
  center: {
    textAlign: "center",
  },
  /* sidebar {
       // backgroundColor: 'rgba(35, 55, 75, 0.9)',
        color: "white",
        padding:' 6px 12px',
        fontFamily: 'monospace',
        zIndex: 1,
        position: 'absolute',
        top: 0,
        left: 0,
        margin: '12px',
        borderRadius: "4px",
        } */
});

class Rider extends Component {
  static displayName = Rider.name;
  constructor(props) {
    super(props);
    this.state = {
      lng: -8.6586,
      lat: 40.6331,
      zoom: 17,
      ordersWaiting: [],
      destinationCoords: null
    };
    this.mapContainer = React.createRef();
  }

  componentDidMount() {

    const { lng, lat, zoom } = this.state;

    const map = new mapboxgl.Map({
      container: this.mapContainer.current,
      style: "mapbox://styles/mapbox/streets-v11",
      center: [lng, lat],
      zoom: zoom
    });

    map.on("move", () => {
      this.setState({
        lng: map.getCenter().lng.toFixed(4),
        lat: map.getCenter().lat.toFixed(4),
        zoom: map.getZoom().toFixed(2),
      });
    });

    const directions = new MapboxDirections({
      accessToken: mapboxgl.accessToken,
      unit: "metric",
      profile: "mapbox/driving",
    });
    map.addControl(directions, 'top-left');

    // Avenida João Jacinto Magalhães, 3810-193 Aveiro, Aveiro, Portugal
        // Avenida Araújo E Silva, 3810-049 Aveiro, Aveiro, Portugal

    /* directions.setOrigin("Avenida João Jacinto Magalhães, 3810-193 Aveiro, Aveiro, Portugal");
    directions.setDestination("Avenida Araújo E Silva, 3810-049 Aveiro, Aveiro, Portugal"); */
    this.map = map;
    this.directions = directions;
  }


  settingRoute(deliver_lat, deliver_lng, pickup_lat, pickup_lng) {
    let dest = deliver_lng+","+deliver_lat;
    let pickup = pickup_lng+","+pickup_lat;
    console.log(dest)
    this.directions.setOrigin(dest);
    this.directions.setDestination(pickup);
  }

  getDeliveries(){
    console.log("This was done")
    RiderService.fetchAllOrdersByStatus("waiting_for_rider").then(
      (result) => {
        if (result.status === 200) {
          if( result.orders !== this.state.ordersWaiting )
            this.setState({ ordersWaiting: result.orders });
        } 
      }
    );
  }
  changeStates(orderId){
    RiderService.updateOrderState(orderId)
    .then((result)=>{
      if (result.status === 200) {
        console.log(result.order);
      } 
    })
  }

  render() {
    const { classes } = this.props;
    const { lng, lat, zoom } = this.state;

    return (
      <div>
        <hr />
        {sessionStorage.getItem("courier") !== null ? (
          <div className={classes.center}>
            <div /* className={classes.sidebar} */>
              Longitude: {lng} | Latitude: {lat} | Zoom: {zoom}
            </div>
            <div ref={this.mapContainer} className="map-container" />
          </div>
        ) : (
          <div className={classes.center}>
            <h1>Sign in fist</h1>
          </div>
        )}
        <hr />
        <Button
          onClick={ () => {this.getDeliveries()}}
          variant="success"
          style={{
            marginLeft: "20px",
          }}
        >
          Accept
        </Button>

        <div
          style={{
            display: "flex",
            flex: 1,
            flexDirection: "row",
            alignItems: "center",
            justifyContent: "center",
            alignContent: "center",
            alignSelf: "center",
            flexWrap: "wrap",
          }}
        >
          {this.state.ordersWaiting.length !== 0 && (
            <div style={{ flex: 1 }}>
              {console.log(this.state.ordersWaiting)}
              {this.state.ordersWaiting.map((order, index) => (
                <Card style={{ width: "18rem" }} key={index}>

                  <Card.Body >
                    <Card.Title>{order.deliverAddress.city} <ExploreIcon onClick={ () => this.settingRoute(order.deliverAddress.latitude, order.deliverAddress.longitude, order.pickupAddress.latitude, order.pickupAddress.longitude)} /></Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">
                      {order.deliverAddress.latitude + "," + order.deliverAddress.longitude}
                    </Card.Subtitle>
                    <Card.Text>{order.deliverAddress.completeAddress}</Card.Text>
                    <div className="row">
                      <div style={{ flex: 1 }}>
                        <Button
                          onClick={()=>{this.changeStates(order.id)}}
                          variant="success"
                          style={{
                            marginLeft: "20px",
                          }}
                        >
                          Accept
                        </Button>
                        <Button
                          variant="danger"
                          style={{
                            float: "right",
                            flexBasis: "flex-end",
                            marginRight: "20px",
                          }}
                        >
                          Reject
                        </Button>
                      </div>
                    </div>
                  </Card.Body>
                </Card>
              ))}
            </div>
          )}
        </div>
      </div>
    );
  }
}
export default withStyles(styles)(Rider);
