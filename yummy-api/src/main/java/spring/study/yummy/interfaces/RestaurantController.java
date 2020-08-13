package spring.study.yummy.interfaces;

import org.apache.catalina.util.ErrorPageSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.study.yummy.domain.Restaurant;
import spring.study.yummy.domain.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantRepository repository = new RestaurantRepository();

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
//        List<Restaurant> restaurants = new ArrayList<>();
//
//        restaurants.add(new Restaurant(1004L, "Yummy", "Seoul"));
//        restaurants.add(new Restaurant(2020L, "Gimbap Heaven", "Seoul"));

        return repository.findAll();
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

}
