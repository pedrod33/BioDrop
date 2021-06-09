package pt.deliveries.business_initiative.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_initiative.JsonUtil;
import pt.deliveries.business_initiative.exception.ClientNotFoundException;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.service.AddressServiceImpl;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressRestController.class)
public class AddressRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressServiceImpl service;


    @Test
    void give3Addresses_whenFindAllAddresses_thenReturnAllAddresses( ) throws Exception {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Address address2 = new Address("city2", "address2", 10, 11);
        address2.setId(2L);

        Address address3 = new Address("city3", "address3", 10, 11);
        address3.setId(3L);

        List<Address> allAddresses = new ArrayList<>(Arrays.asList(address1, address2, address3));

        when( service.findAllAddresses()).thenReturn( allAddresses );

        mvc.perform(get("/businesses-api/addresses/allAddresses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].city", is("city1")))
                .andExpect(jsonPath("$.[0].address", is("address1")))
                .andExpect(jsonPath("$.[0].latitude", is(10.0)))
                .andExpect(jsonPath("$.[0].longitude", is(11.0)))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].city", is("city2")))
                .andExpect(jsonPath("$.[1].address", is("address2")))
                .andExpect(jsonPath("$.[1].latitude", is(10.0)))
                .andExpect(jsonPath("$.[1].longitude", is(11.0)))
                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[2].city", is("city3")))
                .andExpect(jsonPath("$.[2].address", is("address3")))
                .andExpect(jsonPath("$.[2].latitude", is(10.0)))
                .andExpect(jsonPath("$.[2].longitude", is(11.0)));

        verify(service, times(1)).findAllAddresses();
    }


    @Test
    void whenValidClientAddressAndClientId_thenStatus200( ) throws Exception {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Client goodClient = new Client("cunha", "cunha@ua.pt", "1234", null, "M", "96000000");
        goodClient.setId(1L);
        goodClient.setAddresses(new HashSet<>(Collections.singleton(address1)));

        Long clientId = 1L;
        when( service.saveClientAddress( Mockito.any(), eq(clientId) )).thenReturn( goodClient );

        mvc.perform(put("/businesses-api/addresses/save-address?clientId=1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(address1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("cunha")))
                .andExpect(jsonPath("email", is("cunha@ua.pt")))
                .andExpect(jsonPath("password", is("1234")))
                .andExpect(jsonPath("addresses.[0].id", is(1)))
                .andExpect(jsonPath("addresses.[0].city", is("city1")))
                .andExpect(jsonPath("addresses.[0].address", is("address1")))
                .andExpect(jsonPath("addresses.[0].latitude", is(10.0)))
                .andExpect(jsonPath("addresses.[0].longitude", is(11.0)))
                .andExpect(jsonPath("gender", is("M")))
                .andExpect(jsonPath("phoneNumber", is("96000000")));

        verify(service, times(1)).saveClientAddress(Mockito.any(), eq(clientId));
    }

    @Test
    void whenValidClientAddressAndInvalidClientId_thenStatus200( ) throws Exception {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Long wrongClientId = 0L;
        when( service.saveClientAddress( Mockito.any(), eq(wrongClientId) )).thenThrow(ClientNotFoundException.class);

        mvc.perform(put("/businesses-api/addresses/save-address?clientId=0").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(address1)))
                .andExpect(status().isNotFound());

        verify(service, times(1)).saveClientAddress(Mockito.any(), eq(wrongClientId));
    }

}
