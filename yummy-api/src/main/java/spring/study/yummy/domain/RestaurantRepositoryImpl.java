package spring.study.yummy.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepositoryImpl() {
        restaurants.add(new Restaurant(1004L, "Yummy", "Seoul"));
        restaurants.add(new Restaurant(2020L, "Gimbap Heaven", "Seoul"));
    }

    @Override
    public List<Restaurant> findAll() {
        return this.restaurants;
    }

    @Override
    public Restaurant findById(long id) {
        return restaurants.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        restaurant.setId(1234L);
        restaurants.add(restaurant);
        return restaurant;
    }
}
