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
			"http://localhost:8090/businesses-api/clients/client?clientId=" +
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

	async addOrderToCart(clientId, productId, amount) {
		var url =
			"http://localhost:8090/businesses-api/orders/updateProductsOrder?clientId=" +
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

	async updateClientAddress(clientId, cityInput, completeAddressInput, latitudeInput, longitudeInput) {
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
}

export default new ClientService();
