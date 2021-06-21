class CourierService {

	async fetchCourierById(courierId) {
		var url =
			"http://localhost:8090/businesses-api/clients/client?clientId=" +
			courierId;

		var res = await fetch(url);
		var json = await res.json();

		if (res.status === 404)
			return { status: res.status, message: json.message };
		else {
			sessionStorage.setItem("courier", JSON.stringify(json));
			return { status: 200, courier: json };
		}
	}

	async login(emailInput, passwordInput) {
		var url = "http://localhost:8089/deliveries-api/courier/login";

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
			sessionStorage.setItem("courier", JSON.stringify(json));
			return { status: 200, courier: json };
		}
	}

	async register(
		nameInput,
		emailInput,
		passwordInput,
		genderInput,
		phoneNumberInput,
		vehicleIdInput
	) {
		var url = "http://localhost:8089/deliveries-api/courier/register";

		let registerInput = {
			name: nameInput,
			email: emailInput,
			password: passwordInput,
			gender: genderInput,
			phoneNumber: phoneNumberInput,
			vehicle_id: vehicleIdInput,
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
		else return { status: 201, courier: json };
	}

}

export default new CourierService();