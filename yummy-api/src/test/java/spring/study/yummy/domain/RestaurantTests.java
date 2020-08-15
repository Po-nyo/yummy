package spring.study.yummy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantTests {

    @Test
    public void creation() {
        Restaurant restaurant = new Restaurant(1004L, "Yummy", "Seoul");

        assertEquals(1004L, restaurant.getId());
        assertEquals("Yummy", restaurant.getName());
        assertEquals("Seoul", restaurant.getAddress());
    }

    @Test
    public void information() {
        Restaurant restaurant = new Restaurant(1004L,"Yummy", "Seoul");

        assertEquals("Yummy in Seoul", restaurant.getInformation());
    }
}