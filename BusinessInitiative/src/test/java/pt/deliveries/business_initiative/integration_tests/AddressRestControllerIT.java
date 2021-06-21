package pt.deliveries.business_initiative.integration_tests;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_initiative.BusinessInitiativeApplication;
import pt.deliveries.business_initiative.JsonUtil;
import pt.deliveries.business_initiative.exception.ClientNotFoundException;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.repository.AddressRepository;
import pt.deliveries.business_initiative.repository.ClientRepository;

import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BusinessInitiativeApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AddressRestControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUpDb() {
        Address address1 = new Address("city1", "address1", 10, 11);

        Client goodClient = new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000");
        goodClient.setAddresses(new HashSet<>(Collections.singleton(address1)));

        clientRepository.save(goodClient);
    }

    @AfterEach
    public void resetDb() {
        clientRepository.deleteAll();
        addressRepository.deleteAll();
    }


    @Test
    void whenValidClientAddressAndClientId_thenStatus200( ) throws Exception {
        Address address1 = new Address("city2", "address2", 100, 111);

        mvc.perform(put("/businesses-api/addresses/update-client-address?clientId=1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(address1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("cunha")))
                .andExpect(jsonPath("email", is("cunha@ua.pt")))
                .andExpect(jsonPath("password", is("1234")))
                .andExpect(jsonPath("addresses.[0].city", is("city2")))
                .andExpect(jsonPath("addresses.[0].completeAddress", is("address2")))
                .andExpect(jsonPath("addresses.[0].latitude", is(100.0)))
                .andExpect(jsonPath("addresses.[0].longitude", is(111.0)))
                .andExpect(jsonPath("addresses.[1].city", is("city1")))
                .andExpect(jsonPath("addresses.[1].completeAddress", is("address1")))
                .andExpect(jsonPath("addresses.[1].latitude", is(10.0)))
                .andExpect(jsonPath("addresses.[1].longitude", is(11.0)))
                .andExpect(jsonPath("gender", is("M")))
                .andExpect(jsonPath("phoneNumber", is("96000000")));
    }


    @Test
    void whenValidClientAddressAndInvalidClientId_thenStatus200( ) throws Exception {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        mvc.perform(put("/businesses-api/addresses/update-client-address?clientId=0").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(address1)))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> instanceOf(ClientNotFoundException.class));
    }

}
