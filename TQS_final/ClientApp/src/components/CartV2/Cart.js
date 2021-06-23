import React, { useState, useEffect } from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Title from "../Profile/Title";
// import ClientService from "../../services/client.service";
import ClientService from "../../services/client.service";
import Modal from "react-bootstrap/Modal";

export default function Cart() {
    const [productsList, setProductsList] = useState(null);
    const [client, setClient] = useState(null);

    const [addressInput, setAddressInput] = useState({
        cityInput: null,
        completeAddressInput: null,
        latitudeInput: null,
        longitudeInput: null,
    });

    const [selectedAddress, setSelectedAddress] = useState(null);
    const [createAddress, setCreateAddress] = useState(false);
    const [show, setShow] = useState(false);

    useEffect(() => {
        var orders = JSON.parse(sessionStorage.getItem("order"));
        var client = JSON.parse(sessionStorage.getItem("client"));

        ClientService.fetchClientByUserId(client.id).then((response) => {
            if (response.status === 200) {
                setClient(response.client);
            } else console.log(response.message);
        });

        if (orders !== null) setProductsList(orders.orderProducts);
        if (client !== null) setClient(client);
    }, []);

    function calculatePrice() {
        if (productsList !== null) {
            var totalPrice = 0;
            for (var x = 0; x < productsList.length; x++) {
                totalPrice +=
                    productsList[x].amount * productsList[x].product.price;
            }

            return totalPrice;
        }
    }

    const handleClose = () => {
        setShow(false);
        setSelectedAddress(null);
    };
    const handleShow = () => setShow(true);

    const changeSelectedAddress = (address) => {
        console.log("select");
        if (address !== selectedAddress) setSelectedAddress(address);
    };

    function updateClientAddress() {
        var client = JSON.parse(sessionStorage.getItem("client"));

        console.log(addressInput);
        console.log("aiskdaiskdiaksdkias");

        ClientService.updateClientAddress(
            client.id,
            addressInput.cityInput,
            addressInput.completeAddressInput,
            addressInput.latitudeInput,
            addressInput.longitudeInput
        ).then((response) => {
            if (response.status === 200) {
                setClient(response.client);
            } else console.log(response.message);
        });

        //setToggleAddressCreation(false);
    }

    return (
        <React.Fragment>
            <Title>Shopping Cart</Title>
            <Table size="small">
                <TableHead>
                    <TableRow>
                        <TableCell>Product</TableCell>
                        <TableCell>Origin</TableCell>
                        <TableCell align="right">Quantity</TableCell>
                        <TableCell align="right">Price</TableCell>
                    </TableRow>
                </TableHead>
                {productsList !== null && (
                    <TableBody>
                        {productsList.map((prod) => (
                            <TableRow key={prod.product.id}>
                                <TableCell>{prod.product.name}</TableCell>
                                <TableCell>{prod.product.origin}</TableCell>
                                <TableCell align="right">
                                    {prod.amount}
                                </TableCell>
                                <TableCell align="right">
                                    {prod.product.price}$
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                )}
            </Table>

            <div
                style={{ marginTop: 20, maringBottom: 0, textAlign: "center" }}
            >
                <p>Delivery: {productsList !== null ? "5" : ""} $</p>
                <p>Total Price: {calculatePrice()}$</p>

                <button
                    onClick={() => {
                        handleShow();
                    }}
                    style={{
                        fontWeight: "bold",
                        fontSize: "0.75rem",
                        color: "white",
                        backgroundColor: "grey",
                        borderRadius: 8,
                        padding: "3px 10px",
                        display: "inline-block",
                        marginTop: 20,
                    }}
                >
                    checkout
                </button>
            </div>

            {client !== null && (
                <div style={{ textAlign: "center", marginTop: "40px" }}>
                    <Modal show={show} onHide={handleClose} size="lg">
                        <Modal.Header closeButton>
                            <Modal.Title>Choose address</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <Table size="small">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Select</TableCell>
                                        <TableCell>City</TableCell>
                                        <TableCell>Address</TableCell>
                                        <TableCell>Latitude</TableCell>
                                        <TableCell>Longitude</TableCell>
                                    </TableRow>
                                </TableHead>
                                {client.addresses !== null && (
                                    <TableBody>
                                        {client.addresses.map((address) => (
                                            <TableRow key={address.id}>
                                                <TableCell align="center">
                                                    <input
                                                        type="radio"
                                                        name="fav_language"
                                                        onClick={() =>
                                                            changeSelectedAddress(
                                                                address
                                                            )
                                                        }
                                                    />
                                                </TableCell>
                                                <TableCell>
                                                    {address.city}
                                                </TableCell>
                                                <TableCell>
                                                    {address.completeAddress}
                                                </TableCell>
                                                <TableCell>
                                                    {address.latitude}
                                                </TableCell>
                                                <TableCell>
                                                    {address.longitude}
                                                </TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                )}
                            </Table>

                            {client.addresses.length === 0 && (
                                <p
                                    style={{
                                        textAlign: "center",
                                        marginTop: "20px",
                                    }}
                                >
                                    You have no addresses, create one first
                                </p>
                            )}

                            <div
                                style={{
                                    textAlign: "center",
                                    marginTop: "40px",
                                }}
                            >
                                <button
                                    onClick={() =>
                                        setCreateAddress(!createAddress)
                                    }
                                >
                                    {createAddress === true
                                        ? "Close"
                                        : "Create new address"}
                                </button>
                            </div>

                            {createAddress === true && (
                                <div
                                    className="row"
                                    style={{ marginTop: "20px" }}
                                >
                                    <div
                                        style={{
                                            flex: 1,
                                            flexDirection: "row",
                                            textAlign: "center",
                                        }}
                                        className="col-4"
                                    >
                                        <div style={{ flex: 0.5 }}>
                                            <input
                                                type="text"
                                                placeholder="City"
                                                id="cityInput"
                                                onChange={(event) =>
                                                    setAddressInput({
                                                        cityInput:
                                                            event.target.value,
                                                        completeAddressInput:
                                                            addressInput.completeAddressInput,
                                                        latitudeInput:
                                                            addressInput.latitudeInput,
                                                        longitudeInput:
                                                            addressInput.longitudeInput,
                                                    })
                                                }
                                            />
                                        </div>
                                        <div style={{ flex: 1 }}>
                                            <input
                                                type="text"
                                                placeholder="Latitude"
                                                onChange={(event) =>
                                                    setAddressInput({
                                                        cityInput:
                                                            addressInput.cityInput,
                                                        completeAddressInput:
                                                            addressInput.completeAddressInput,
                                                        latitudeInput:
                                                            event.target.value,
                                                        longitudeInput:
                                                            addressInput.longitudeInput,
                                                    })
                                                }
                                                id="latitudeInput"
                                            />
                                        </div>
                                    </div>
                                    <div
                                        style={{
                                            flex: 1,
                                            flexDirection: "row",
                                            textAlign: "center",
                                        }}
                                        className="col-4"
                                    >
                                        <div style={{ flex: 1 }}></div>
                                        <div style={{ flex: 1 }}>
                                            <input
                                                type="text"
                                                placeholder="Address"
                                                onChange={(event) =>
                                                    setAddressInput({
                                                        cityInput:
                                                            addressInput.cityInput,
                                                        completeAddressInput:
                                                            event.target.value,
                                                        latitudeInput:
                                                            addressInput.latitudeInput,
                                                        longitudeInput:
                                                            addressInput.longitudeInput,
                                                    })
                                                }
                                                id="completeAddressInput"
                                            />
                                            <input
                                                type="text"
                                                placeholder="Longitude"
                                                onChange={(event) =>
                                                    setAddressInput({
                                                        cityInput:
                                                            addressInput.cityInput,
                                                        completeAddressInput:
                                                            addressInput.completeAddressInput,
                                                        latitudeInput:
                                                            addressInput.latitudeInput,
                                                        longitudeInput:
                                                            event.target.value,
                                                    })
                                                }
                                                id="longitudeInput"
                                            />
                                        </div>
                                    </div>
                                    <div
                                        style={{
                                            textAlign: "center",
                                            justifyContent: "center",
                                            alignSelf: "center",
                                        }}
                                        className="col-3"
                                    >
                                        <button
                                            onClick={() => {
                                                updateClientAddress();
                                            }}
                                        >
                                            Create Address
                                        </button>
                                    </div>
                                </div>
                            )}
                        </Modal.Body>
                        <Modal.Footer>
                            <button variant="secondary" onClick={handleClose}>
                                Close
                            </button>
                            {selectedAddress !== null && (
                                <button variant="primary" onClick={handleClose}>
                                    Confirm
                                </button>
                            )}
                        </Modal.Footer>
                    </Modal>
                </div>
            )}
        </React.Fragment>
    );
}
