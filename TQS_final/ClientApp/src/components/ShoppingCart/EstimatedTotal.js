import React, { Component } from "react";
import { Row, Col } from "react-bootstrap";

export default class EstimatedTotal extends Component {
    render() {
        return (
            <Row>
                <Col xs={6}>
                    <h3>Est. Total:</h3>
                </Col>
                <Col xs={6}>
                    <h3>$97</h3>
                </Col>
            </Row>
        );
    }
}
