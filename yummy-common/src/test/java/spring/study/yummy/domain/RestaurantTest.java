package spring.study.yummy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantTest {

    @Test
    public void creation() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Yummy")
                .address("Seoul")
                .build();

        assertEquals(1004L, restaurant.getId());
        assertEquals("Yummy", restaurant.getName());
        assertEquals("Seoul", restaurant.getAddress());
    }

    @Test
    public void information() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Yummy")
                .address("Seoul")
                .build();

        assertEquals("Yummy in Seoul", restaurant.getInformation());
    }
}