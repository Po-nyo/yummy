package spring.study.yummy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantRepositoryImplTest {

    @Test
    public void save() {
        RestaurantRepository repository = new RestaurantRepositoryImpl();

        int oldCnt = repository.findAll().size();

        Restaurant restaurant = new Restaurant("Yummy2", "Gangneung");
        repository.save(restaurant);

        assertEquals(1234L, restaurant.getId());

        int newCnt = repository.findAll().size();

        assertEquals(1, newCnt - oldCnt);
    }
}