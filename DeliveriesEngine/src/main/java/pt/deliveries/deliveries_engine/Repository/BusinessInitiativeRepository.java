package pt.deliveries.deliveries_engine.Repository;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Repository
public class BusinessInitiativeRepository {
    private final String BASE_URL = "http://localhost:8090/business-api";
    ObjectMapper mapper = new ObjectMapper();

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    public boolean existsOrder(long orderId) {
        ResponseEntity<String> response = null;
        String url = BASE_URL+"/orders/?orderId="+orderId;
        try {
            response = restTemplate.getForEntity(url, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            if(response.getStatusCodeValue()!=200 || root.isEmpty())
                return false;
            else if(root.path("id").asInt()!=orderId)
                return false;
        }
        catch (JsonProcessingException e){
            return false;
        }
        catch(HttpClientErrorException e){
            return false;
        }

        return true;
    }

    public boolean existsClient(long clientId){
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL+"/clients/?clientId="+clientId, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            if(response.getStatusCodeValue()!=200 || root.isEmpty())
                return false;
            else if(root.path("id").asInt()!=clientId)
                return false;
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
