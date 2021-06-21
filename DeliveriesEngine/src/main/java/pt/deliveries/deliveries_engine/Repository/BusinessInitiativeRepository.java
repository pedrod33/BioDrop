package pt.deliveries.deliveries_engine.Repository;


import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class BusinessInitiativeRepository {

    WebClient client = WebClient.create("http://localhost:8090/business-api");
    public boolean existsOrder(long orderId){
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.POST);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/orders");
        return true;
    }

    public boolean existsClient(long clientId){
        return true;
    }
}
