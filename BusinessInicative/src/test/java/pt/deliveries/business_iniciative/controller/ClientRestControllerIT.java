package pt.deliveries.business_iniciative.controller;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_iniciative.BusinessIniciativeApplication;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.repository.ClientRepository;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BusinessIniciativeApplication.class)
//@AutoConfigureMockMvc

//@TestPropertySource(locations = "../../../../resources/pt/deliveries/business_initiative/application-integrationtest.properties")
public class ClientRestControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClientRepository repository;

    @BeforeEach
    public void setUpDb() {
        repository.save(new Client(1L, "cunha", "cunha@ua.pt", "1234", "address", "M", "96000000"));
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    //@Test
    public void whenValidInput_thenCreateEmployee() throws IOException, Exception {
        String body =
                "{\n" +
                    "    \"name\":\"cunha\",\n" +
                    "    \"email\":\"cunha@ua.pt\",\n" +
                    "    \"password\":\"ola\",\n" +
                    "    \"address\":\"address\",\n" +
                    "    \"gender\":\"M\",\n" +
                    "    \"phoneNumber\":\"9100000\"\n" +
                "}";

        mvc.perform(post("/businesses-api/register").contentType(MediaType.APPLICATION_JSON).content(body)) //.content(JsonUtil.toJson(bob))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"96000000\"\n" +
                        "}"));

        //verify(service, times(1)).save(Mockito.any());
    }

}
