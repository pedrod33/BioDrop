import React from "react";
import ReactDOM from "react-dom";
import { Container } from "react-bootstrap";
import SubTotal from "./SubTotal";
import EstimatedTotal from "./EstimatedTotal";
import ItemDetails from "./ItemDetails";
import "bootstrap/dist/css/bootstrap.min.css";
import "./styles.css";

export default function shop(){
    return (
        <div className="purchase-card">
            <Container>
                <SubTotal />
                <br />
                <EstimatedTotal />
                <br />
                <ItemDetails />
            </Container>
        </div>
    );
}

