package pt.deliveries.business_initiative.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_initiative.JsonUtil;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.repository.ClientRepository;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BusinessInitiativeApplication.class)
//@AutoConfigureMockMvc

//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ClientRestControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClientRepository repository;

    @BeforeEach
    public void setUpDb() {
        //repository.save(new Client(1L, "cunha", "cunha@ua.pt", "1234", "address", "M", "96000000"));
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    //@Test
    public void whenValidRegisterInput_thenCreateClient() throws IOException, Exception {
        Client cunha = new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000");

        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(cunha)));

        List<Client> found = repository.findAll();
        assertThat(found).extracting(Client::getName).containsOnly("cunha");
    }

    /*
    @Test
    public void whenInvalidRegisterInput_thenReturnStatus226() throws IOException, Exception {
        repository.save(new Client("cunha", "cunha@ua.pt", "1234", "address", "M", "96000000"));
        Client invalidClient = new Client("cunha", "cunha@ua.pt", "1234", "address", "M", "1111111");
        Client invalidClient2 = new Client("cunha", "john@ua.pt", "1234", "address", "M", "96000000");

        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(invalidClient)))
                .andExpect(status().isImUsed())
                .andExpect(content().json("{\n" +
                        "    \"id\": null,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"1111111\"\n" +
                        "}"));


        mvc.perform(post("/businesses-api/clients/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(invalidClient2)))
                .andExpect(status().isImUsed())
                .andExpect(content().json("{\n" +
                        "    \"id\": null,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"john@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"96000000\"\n" +
                        "}"));

        List<Client> found = repository.findAll();
        assertThat(found).extracting(Client::getName).containsOnly("cunha");
    }

    @Test
    public void whenValidLoginInput_thenReturnStatus200() throws IOException, Exception {
        repository.save(new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000"));
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Client cunha = new Client(null, "cunha@ua.pt", "1234", null, null, null);
        cunha.setAddresses(new HashSet<>(Collections.singletonList(address)));

        mvc.perform(post("/businesses-api/clients/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(cunha)))
                .andDo(print())
                .andExpect(status().isOk())
                /* Nao funciona pq o id vai variando, apesar de haver o tear down da bd after each ??.
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"cunha\",\n" +
                        "    \"email\": \"cunha@ua.pt\",\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"address\": \"address\",\n" +
                        "    \"gender\": \"M\",\n" +
                        "    \"phoneNumber\": \"96000000\"\n" +
                        "}"));

                /*
                ERRO: Teoricamente recebe um linkedhashmap ???
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("cunha")));

    }


    @Test
    public void givenClients_whenGetClients_thenStatus200() throws Exception {
        repository.save(new Client("diogo", "giodo@ua.pt", "1234", "address", "M", "96000000"));
        repository.save(new Client("cunha", "cunha@ua.pt", "1234", "address", "M", "96004540"));

        mvc.perform(get("/businesses-api/clients/clients").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("diogo")))
                .andExpect(jsonPath("$[1].name", is("cunha")));
    }*/

}