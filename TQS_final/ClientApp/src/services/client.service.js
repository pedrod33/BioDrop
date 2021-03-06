class ClientService {
	/* async getUserById(userId) {
		//var url = urlAPI + 'api/users/' + userId;
		var url = "http://localhost:8090/businesses-api/clients/allClients";
		console.log(url);

		var res = await fetch(url);

		console.log(res);

		return res.json();
	} */

	async fetchClientByUserId(clientId) {
		var url =
			"http://localhost:8090/businesses-api/clients/?clientId=" +
			clientId;

		var res = await fetch(url);
		var json = await res.json();

		if (res.status === 404)
			return { status: res.status, message: json.message };
		else {
			sessionStorage.setItem("client", JSON.stringify(json));
			return { status: 200, client: json };
		}
	}

	async login(emailInput, passwordInput) {
		var url = "http://localhost:8090/businesses-api/clients/login";

		let loginInput = {
			email: emailInput,
			password: passwordInput,
		};

		var res = await fetch(url, {
			method: "POST",
			body: JSON.stringify(loginInput),
			headers: {
				"Content-Type": "application/json",
			},
		});
		var json = await res.json();

		if (json.status === 403)
			return { status: json.status, message: json.message };
		else {
			sessionStorage.setItem("client", JSON.stringify(json));
			return { status: 200, client: json };
		}
	}

	async register(
		nameInput,
		emailInput,
		passwordInput,
		genderInput,
		phoneNumberInput
	) {
		var url = "http://localhost:8090/businesses-api/clients/register";

		let registerInput = {
			name: nameInput,
			email: emailInput,
			password: passwordInput,
			gender: genderInput,
			phoneNumber: phoneNumberInput,
		};

		var res = await fetch(url, {
			method: "POST",
			body: JSON.stringify(registerInput),
			headers: {
				"Content-Type": "application/json",
			},
		});
		var json = await res.json();

		/* if(json.id) {
            sessionStorage.setItem("user", JSON.stringify(json));
        }  */
		if (json.status === 226)
			return { status: json.status, message: json.message };
		else return { status: 201, client: json };
	}

	async fetchAllStores() {
		var url = "http://localhost:8090/businesses-api/stores/allStores";

		var res = await fetch(url);

		if (res.status === 200) {
			var json = await res.json();
			sessionStorage.setItem("stores", JSON.stringify(json));
			return { status: 200, stores: json };
		} else {
			return { status: 201, message: "Erro fetch all stores" };
		}
	}

	async fetchProductsInStore(storeId) {
		var url =
			"http://localhost:8090/businesses-api/stores/productsIn?storeId=" +
			storeId;

		var res = await fetch(url);
		var json = await res.json();

		if (res.status === 200) {
			return { status: 200, stores: json };
		} else {
			return { status: 404, message: json.message };
		}
	}

	async addOrderToCart(clientId, storeId, productId, amount) {
		var url =
			"http://localhost:8090/businesses-api/orders/updateProductsOrder?storeId="+
			storeId+
			"&clientId=" +
			clientId +
			"&productId=" +
			productId +
			"&amount=" +
			amount;

		var res = await fetch(url, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
			},
		});
		var json = await res.json();

		console.log(json);
		if (res.status === 200) {
			sessionStorage.setItem("order", JSON.stringify(json));
			return { status: res.status, order: json };
		} else return { status: res.status, message: json };
	}

	async updateClientAddress(
		clientId,
		cityInput,
		completeAddressInput,
		latitudeInput,
		longitudeInput
	) {
		var url =
			"http://localhost:8090/businesses-api/addresses/update-client-address?clientId=" +
			clientId;

		let addressInput = {
			city: cityInput,
			completeAddress: completeAddressInput,
			latitude: latitudeInput,
			longitude: longitudeInput,
		};

		var res = await fetch(url, {
			method: "PUT",
			body: JSON.stringify(addressInput),
			headers: {
				"Content-Type": "application/json",
			},
		});
		var json = await res.json();

		console.log(json);
		if (res.status === 200) {
			sessionStorage.setItem("client", JSON.stringify(json));
			return { status: res.status, client: json };
		} else return { status: res.status, message: json };
	}

	async updateOrderStatus(clientId, newStatus) {
		var url =
			"http://localhost:8090/businesses-api/orders/updateStatus?clientId=" +
			clientId +
			"&orderStatus=" +
			newStatus;

		var res = await fetch(url, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
			},
		});
		var json = await res.json();

		console.log(json);
		if (res.status === 200) {
			sessionStorage.removeItem("order");
			return { status: res.status, client: json };
		} else return { status: res.status, message: json };
	}


	async updateOrderStatus2(orderId, newStatus) {
		var url =
			"http://localhost:8090/businesses-api/orders/updateStatus2?orderId=" +
			orderId +
			"&orderStatus=" +
			newStatus;

		var res = await fetch(url, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
			},
		});
		var json = await res.json();

		console.log(json);
		if (res.status === 200) {
			sessionStorage.removeItem("order");
			return { status: res.status, client: json };
		} else return { status: res.status, message: json };
	}


    async updateAddressOrder(
		address,
        clientId
	) {
		var url = "http://localhost:8090/businesses-api/orders/updateOrderAddress?clientId=" + clientId;

		let addressBody = {
			city: address.city,
			completeAddress: address.completeAddress,
			latitude: address.latitude,
			longitude: address.longitude,
		};

		var res = await fetch(url, {
			method: "PUT",
			body: JSON.stringify(addressBody),
			headers: {
				"Content-Type": "application/json",
			},
		});
		var json = await res.json();

		/* if(json.id) {
            sessionStorage.setItem("user", JSON.stringify(json));
        }  */
		if (json.status === 226)
			return { status: json.status, message: json.message };
		else return { status: 200, order: json };
	}


    async fetchStoreById(storeId) {
        
		var url =
			"http://localhost:8090/businesses-api/stores/?id=" +
			storeId;
		console.log("antes do await")
		var res = await fetch(url);
		var json = await res.json();
		console.log(json);
		console.log(res);
		if (res.status === 200) {
			return { status: 200, store: json };
		} else {
			return { status: 404, message: json.message };
		}
	}

    async createDelivery(clientId, storeId, clientAddress) {
        var order = JSON.parse(sessionStorage.getItem("order"))

		var url = "http://localhost:8089/deliveries-api/deliveries/create?clientId=" + clientId + "&orderId=" + order.id;


        this.fetchStoreById(storeId).then( (result) => {

            let locations = {
                latStore: result.store.address.latitude,
                longStore: result.store.address.longitude,
                latClient: clientAddress.latitude,
                longClient: clientAddress.longitude,
            };

            fetch(url, {
                method: "POST",
                body: JSON.stringify(locations),
                headers: {
                    "Content-Type": "application/json",
                },
            }).then( (res) => {
                console.log(res)
                var json = res.json()
                    
                console.log(json)
                if (json.status !== 201)
                    return { status: json.status, message: json.message };
                else {
                    //sessionStorage.setItem("client", JSON.stringify(json));
                    return { status: 201, client: json };
                }
                
            })


		})
	}

}

export default new ClientService();
