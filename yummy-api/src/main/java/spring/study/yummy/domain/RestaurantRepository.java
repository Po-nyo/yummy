package spring.study.yummy.domain;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> findAll();

    Restaurant findById(long id);

    Restaurant save(Restaurant restaurant);
}
