package pt.deliveries.deliveries_engine.Controller;


import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeDoesNotExistException;
import pt.deliveries.deliveries_engine.Model.*;
import pt.deliveries.deliveries_engine.Pojo.CreateRatingPojo;
import pt.deliveries.deliveries_engine.Service.RatingServiceImpl;
import pt.deliveries.deliveries_engine.utils.JsonUtil;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingRestController.class)
public class RatingRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    RatingServiceImpl ratingService;

    private Delivery delivery;
    Vehicle vehicle;
    Courier courier;
    Rating rating;
    @BeforeEach
    void setUp(){
        courier = new Courier("Marco Alves",
                "marcoA@gmail.com","12345678","M",931231233L,
                new Supervisor("carlos@gmail.com","12345678", "Carlos"),
                new Vehicle("car")
        );
        courier.setId(1L);
        vehicle = new Vehicle("car");
        vehicle.setId(1L);
        delivery = new Delivery(courier, 1L,3,40,5,60, 1L);
        delivery.setId(1L);
        rating = new Rating();
        rating.setDelivery(delivery);
        rating.setScore(5);
        rating.setId(1L);

    }

    @Test
    void createRatingValidContent_ReturnsRating() throws Exception{
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5, "descricao", 1L);
        when(ratingService.createRating(Mockito.any(), Mockito.anyLong())).thenReturn(rating);
        rating.setDescription(ratingPojo.getDescription());
        mvc.perform((post("/deliveries-api/ratings/?client_id=1")
            .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(ratingPojo))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.delivery.status", is(0)))
            .andExpect(jsonPath("$.description", is(ratingPojo.getDescription())));
        verify(ratingService, times(1)).createRating(Mockito.any(), Mockito.anyLong());
    }

    @Test
    void createRatingValidContentWithoutDescription_ReturnsRating() throws Exception{
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5,  1L);
        when(ratingService.createRating(Mockito.any(), Mockito.anyLong())).thenReturn(rating);
        mvc.perform((post("/deliveries-api/ratings/?client_id=1")
            .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(ratingPojo))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.delivery.status", is(0)))
            .andExpect(jsonPath("$.description").value(IsNull.nullValue()));
        verify(ratingService, times(1)).createRating(Mockito.any(), Mockito.anyLong());
    }
}
