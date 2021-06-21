package pt.deliveries.deliveries_engine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.deliveries.deliveries_engine.Model.Rating;
import pt.deliveries.deliveries_engine.Pojo.CreateRatingPojo;
import pt.deliveries.deliveries_engine.Service.RatingServiceImpl;

@RestController
@RequestMapping("/deliveries-api/ratings")
public class RatingRestController {

    @Autowired
    RatingServiceImpl ratingService;

    @PostMapping("/")
    public ResponseEntity<Rating> addRatingToDelivery(@RequestBody CreateRatingPojo ratingPojo, @RequestParam long client_id){
        Rating rating = ratingService.createRating(ratingPojo, client_id);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }
}
