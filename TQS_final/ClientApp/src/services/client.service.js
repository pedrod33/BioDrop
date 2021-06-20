
class ClientService {

    async getUserById(userId) {
        //var url = urlAPI + 'api/users/' + userId;
        var url = 'http://localhost:8090/businesses-api/clients/allClients';
        console.log(url);

        var res = await fetch(url);

        console.log(res)

        return res.json();
    }


    async login(emailInput, passwordInput) {
        var url = 'http://localhost:8090/businesses-api/clients/login';


        let loginInput = {
            email: emailInput,
            password: passwordInput,
        }

        var res = await fetch(url, {
            method: 'POST',
            body: JSON.stringify(loginInput),
            headers: {
            'Content-Type': 'application/json'
            }
        });
        var json = await res.json()


        if( json.status === 403 )
            return {"status": json.status, "message": json.message};
        else
            sessionStorage.setItem("client", JSON.stringify(json));
            return {"status": 200, "client": json}
    }


    async register(nameInput, emailInput, passwordInput, genderInput, phoneNumberInput) {
        var url = 'http://localhost:8090/businesses-api/clients/register';


        let registerInput = {
            name: nameInput,
            email: emailInput,
            password: passwordInput,
            gender: genderInput, 
            phoneNumber: phoneNumberInput
        }

        console.log(registerInput)

        var res = await fetch(url, {
            method: 'POST',
            body: JSON.stringify(registerInput),
            headers: {
            'Content-Type': 'application/json'
            }
        });
        var json = await res.json()
        

        /* if(json.id) {
            sessionStorage.setItem("user", JSON.stringify(json));
        }  */
        if( json.status === 226 )
            return {"status": json.status, "message": json.message};
        else
            return {"status": 201, "client": json}
    }

}

export default new ClientService();