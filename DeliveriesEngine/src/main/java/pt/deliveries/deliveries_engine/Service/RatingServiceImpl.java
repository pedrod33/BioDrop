package pt.deliveries.deliveries_engine.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.deliveries.deliveries_engine.Exception.DeliveryDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.DeliveryHasNotBeenDeliveredException;
import pt.deliveries.deliveries_engine.Exception.DeliveryWithOrderIdHasBeenProcessedException;
import pt.deliveries.deliveries_engine.Exception.InvalidRatingScoreException;
import pt.deliveries.deliveries_engine.Model.Delivery;
import pt.deliveries.deliveries_engine.Model.Rating;
import pt.deliveries.deliveries_engine.Pojo.CreateRatingPojo;
import pt.deliveries.deliveries_engine.Repository.DeliveryRepository;
import pt.deliveries.deliveries_engine.Repository.RatingRepository;

@Service
@Transactional
public class RatingServiceImpl {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    RatingRepository ratingRepository;

    public Rating createRating(CreateRatingPojo ratingPojo, long client_id) {
        this.canCreate(ratingPojo, client_id);
        Rating rating = new Rating();
        rating.setScore(ratingPojo.getScore());
        if(ratingPojo.getDescription()!=null){
            rating.setDescription(ratingPojo.getDescription());
        }
        Delivery repDelivery = deliveryRepository.findDeliveryByOrderId(ratingPojo.getOrder_id());
        rating.setDelivery(repDelivery);
        rating.setScore(ratingPojo.getScore());
        return ratingRepository.save(rating);
    }

    public boolean canCreate(CreateRatingPojo ratingPojo, long client_id){
        if(ratingPojo.getScore() < 1 || ratingPojo.getScore() > 5){
            throw new InvalidRatingScoreException("The score must be between 1 and 5");
        }
        Delivery delivery = deliveryRepository.findDeliveryByOrderId(ratingPojo.getOrder_id());
        if(delivery==null){
            throw new DeliveryDoesNotExistException("There is no delivery with this information");
        }
        else if(delivery.getStatus()!=2){
            throw new DeliveryHasNotBeenDeliveredException("This delivery cannot be rated yet");
        }
        return true;
    }
}
