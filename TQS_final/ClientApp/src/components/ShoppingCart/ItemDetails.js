import React, { Component } from "react";
import { Button, Row, Col, Media, Accordion } from "react-bootstrap";

const styles = {
    mediaItem: {
        border: "1px solid black",
        backgroundColor: "#f5f5f5",
        paddingTop: "5px",
        paddingBottom: "5px"
    },
    mediaItemButtons: {
        paddingTop: "5px",
        paddingBottom: "5px"
    }
};

export default class ItemDetails extends Component {
    constructor(props) {
        super(props);

        this.state = {
            collapsed: false
        };
    }

    render() {
        return (
            // <Button
            //   style={{ outline: "none" }}
            //   onClick={() => this.setState({ collapsed: !this.state.collapsed })}
            // >
            //   {this.state.collapsed == false ? "See" : "Hide"} item details{" "}
            //   {this.state.collapsed == false ? "+" : "-"}
            // </Button>

            <Accordion>
                <Accordion.Toggle
                    as={Button}
                    variant="link"
                    eventKey="0"
                    onClick={() => this.setState({ collapsed: !this.state.collapsed })}
                >
                    {this.state.collapsed === false ? "See" : "Hide"} item details{" "}
                    {this.state.collapsed === false ? "+" : "-"}
                </Accordion.Toggle>
                <Accordion.Collapse eventKey="0">
                    <>
                        <Media className={styles.mediaItem}>
                            <img
                                width={100}
                                height={100}
                                className="align-self-center mr-3"
                                src="https://i5.walmartimages.com/asr/e73e1252-642c-4473-93ea-fd3b564a7027_1.3e81ea58fa3042452fe185129a4a865f.jpeg?odnWidth=undefined&odnHeight=undefined&odnBg=ffffff"
                                alt="Generic placeholder"
                            />
                            <Media.Body className={styles.mediaBody}>
                                <p>Dxracer Formula Gaming Chair (Black/Red)</p>
                                <Row>
                                    <Col xs={6}>
                                        <strong>$48.99</strong>
                                    </Col>
                                    <Col xs={6}>1 piece</Col>
                                </Row>

                                <Row style={styles.mediaItemButtons}>
                                    <Col xs={6}>
                                        <Button variant="primary" size="sm">
                                            Details
                    </Button>
                                    </Col>
                                    <Col xs={6}>
                                        <Button variant="danger" size="sm">
                                            Delete
                    </Button>
                                    </Col>
                                </Row>
                            </Media.Body>
                        </Media>
                        <Media className={styles.mediaItem}>
                            <img
                                width={100}
                                height={100}
                                className="align-self-center mr-3"
                                src=" https://images-na.ssl-images-amazon.com/images/I/81lGKc7oDGL._SX425_.jpg"
                                alt="Generic placeholder"
                            />
                            <Media.Body className={styles.mediaBody}>
                                <p>AOC 27 inch 144hz Gaming Monitor</p>
                                <Row>
                                    <Col xs={6}>
                                        <strong>$299.99</strong>
                                    </Col>
                                    <Col xs={6}>1 piece</Col>
                                </Row>

                                <Row style={styles.mediaItemButtons}>
                                    <Col xs={6}>
                                        <Button variant="primary" size="sm">
                                            Details
                    </Button>
                                    </Col>
                                    <Col xs={6}>
                                        <Button variant="danger" size="sm">
                                            Delete
                    </Button>
                                    </Col>
                                </Row>
                            </Media.Body>
                        </Media>
                    </>
                </Accordion.Collapse>
            </Accordion>
        );
    }
}
