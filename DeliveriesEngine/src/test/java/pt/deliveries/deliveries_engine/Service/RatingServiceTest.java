package pt.deliveries.deliveries_engine.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Exception.ClientDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.DeliveryDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.DeliveryHasNotBeenDeliveredException;
import pt.deliveries.deliveries_engine.Model.*;
import pt.deliveries.deliveries_engine.Pojo.CreateRatingPojo;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;
import pt.deliveries.deliveries_engine.Repository.RatingRepository;


import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    Logger logger = Logger.getLogger(RatingServiceTest.class.getName());

    @Mock(lenient = true)
    private RatingRepository ratingRepository;

    @Mock(lenient = true)
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    Courier c1;
    private Vehicle v1;
    private Delivery delivery;
    private Vehicle v2;
    private Rating rating;
    @BeforeEach
    public void setUp(){
        v1 = new Vehicle("car");
        v1.setId(1L);
        v2 = new Vehicle("bike");
        v2.setId(2L);
        c1 = new Courier("Marco Alves", "marcoA@gmail.com", "12345678", "M",
                931231233L, new Supervisor("Carlos", "carlos@gmail.com","12345678")
                , new Vehicle("car")
        );
        c1.setId(1L);
        delivery = new Delivery(c1, 1L);
        delivery.setOrder_id(2L);
        delivery.setVehicle(v2);
        when(deliveryRepository.findDeliveryByOrderId(1L)).thenReturn(delivery);
        when(deliveryRepository.existsOrderFromDeliveryById(1L)).thenReturn(true);
        when(ratingRepository.getClientById(1L)).thenReturn(true);

        rating = new Rating();
        rating.setDelivery(delivery);
        when(ratingRepository.save(Mockito.any())).thenReturn(rating);
    }

    @Test
    public void createRatingInvalidOrderId_returnsException(){
        delivery.setStatus(2);
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5, "description", 3L);
        assertThrows(DeliveryDoesNotExistException.class, () -> ratingService.createRating(ratingPojo, 1L));
    }

    @Test
    public void createRatingInvalidOrderIdNoDesc_returnsException(){
        delivery.setStatus(2);
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5,  3L);
        assertThrows(DeliveryDoesNotExistException.class, () -> ratingService.createRating(ratingPojo, 1L));
    }

    @Test
    public void createRatingInvalid_returnsException(){
        delivery.setStatus(2);
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5, "description", 1L);
        this.rating.setScore(ratingPojo.getScore());
        this.rating.setDescription(ratingPojo.getDescription());
        Rating rating = ratingService.createRating(ratingPojo, 1L);
        assertThat(rating.getDescription()).isEqualTo(ratingPojo.getDescription());
        assertThat(rating.getScore()).isEqualTo(ratingPojo.getScore());
    }

    @Test
    public void createRatingInvalidNoDesc_returnsException(){
        delivery.setStatus(2);
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5, 1L);
        this.rating.setScore(ratingPojo.getScore());
        this.rating.setDescription(ratingPojo.getDescription());
        Rating rating = ratingService.createRating(ratingPojo, 1L);
        assertThat(rating.getDescription()).isEqualTo(ratingPojo.getDescription());
        assertThat(rating.getScore()).isEqualTo(ratingPojo.getScore());
    }

    @Test
    public void createRatingNotCompletedDelivery_returnsException(){
        delivery.setStatus(1);
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5, "description", 1L);
        assertThrows(DeliveryHasNotBeenDeliveredException.class, () -> ratingService.createRating(ratingPojo, 1L));
    }

    @Test
    public void createRatingNotCompletedDeliveryNoDesc_returnsException(){
        delivery.setStatus(1);
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5, 1L);
        assertThrows(DeliveryHasNotBeenDeliveredException.class, () -> ratingService.createRating(ratingPojo, 1L));
    }

    @Test
    public void createRatingInvalidClientId_returnsException(){
        delivery.setStatus(2);
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5, "description", 1L);
        assertThrows(ClientDoesNotExistException.class, () -> ratingService.createRating(ratingPojo, 2L));
    }

    @Test
    public void createRatingInvalidClientIdNoDesc_returnsException(){
        delivery.setStatus(2);
        CreateRatingPojo ratingPojo = new CreateRatingPojo(5, 1L);
        assertThrows(ClientDoesNotExistException.class, () -> ratingService.createRating(ratingPojo, 2L));
    }
}
