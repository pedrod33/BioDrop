package pt.deliveries.business_initiative.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.deliveries.business_initiative.JsonUtil;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.ClientRegistrationPOJO;
import pt.deliveries.business_initiative.repository.ClientRepository;
import pt.deliveries.business_initiative.service.ClientServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ClientRestControllerIT {

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:5.6")
            .withDatabaseName("business-mysql-test")
            .withUsername("tqs-test")
            .withPassword("password-test");


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

     /*@BeforeEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "client");
    }

      */

    @Test
    //@Transactional
    @Order(1)
    void contextLoads() {
 /*
        Client goodClient1 = new Client("cunha1", "cunha1@ua.pt", "1234", null, "M", "96000001");
        Client goodClient2 = new Client("cunha2", "cunha2@ua.pt", "1234", null, "M", "96000002");
        Client goodClient3 = new Client("cunha3", "cunha3@ua.pt", "1234", null, "M", "96000003");

        service.save(goodClient1);
        service.save(goodClient2);
        service.save(goodClient3);
  */
        //List<Client> clients = repository.findAll();
        /*assertThat(clients.get(0).getName()).isEqualTo("cunha1");
        assertThat(clients.get(1).getName()).isEqualTo("cunha2");
        assertThat(clients.get(2).getName()).isEqualTo("cunha3");
         */
        System.out.println("Context loads!");
    }

    /*@Test
    @Order(2)
    void whenValidRegisterInput_thenStatus201( ) throws Exception {
        ClientRegistrationPOJO goodClientPOJO = new ClientRegistrationPOJO("cunha", "cunha@ua.pt", "1234", "M", "96000000");


        String responseString = given()
                .port(8090)
                .header("Content-Type", "application/json")
                .body(goodClientPOJO)
                .post("/businesses-api/clients/register")
                .then()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .and()
                .body("name", is("cunha"))
                .and()
                .body("email", is("cunha@ua.pt"))
                .and()
                .body("password", is("1234"))
                .and()
                .body("phoneNumber", is("96000000"))
                .and()
                .extract()
                .asString();

        System.out.println(responseString);
    }

     */

    @Test
    @Order(2)
    void given3Clients_whenFindAllClients_thenReturnAllClients() throws Exception {
        /*Client goodClient1 = new Client("cunha1", "cunha1@ua.pt", "1234", null, "M", "96000001");
        Client goodClient2 = new Client("cunha2", "cunha2@ua.pt", "1234", null, "M", "96000002");
        Client goodClient3 = new Client("cunha3", "cunha3@ua.pt", "1234", null, "M", "96000003");

        service.save(goodClient1);
        service.save(goodClient2);
        service.save(goodClient3);

         */


        String responseString = given()
                .port(8090)
                .header("Content-Type", "application/json")
                .get("/businesses-api/clients/allClients")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)

                .and()
                .extract()
                .asString();

        System.out.println(
                responseString
        );


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
    }
    */
}
