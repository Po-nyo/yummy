package spring.study.yummy.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.study.yummy.application.ReviewService;
import spring.study.yummy.domain.Review;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurant_id}/reviews")
    public ResponseEntity<?> create(@PathVariable("restaurant_id") Long restaurant_id,
                                    @Valid @RequestBody Review resource) throws URISyntaxException {
        Review review = reviewService.addReview(restaurant_id, resource);

        String url = "/restaurants/"+restaurant_id+"/reviews/"+review.getId();
        URI location = new URI(url);

        return ResponseEntity.created(location).body("{}");
    }
}
