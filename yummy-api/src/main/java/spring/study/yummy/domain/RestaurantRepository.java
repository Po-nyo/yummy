package spring.study.yummy.domain;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {

    private final List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepository() {
        restaurants.add(new Restaurant(1004L, "Yummy", "Seoul"));
        restaurants.add(new Restaurant(2020L, "Gimbap Heaven", "Seoul"));
    }

    public List<Restaurant> findAll() {
        return this.restaurants;
    }

    public Restaurant findById(Long id) {
        return restaurants.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
