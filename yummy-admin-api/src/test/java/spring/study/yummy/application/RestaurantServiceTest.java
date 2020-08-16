package spring.study.yummy.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.yummy.domain.Restaurant;
import spring.study.yummy.domain.RestaurantNotFoundException;
import spring.study.yummy.domain.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();

        this.restaurantService = new RestaurantService(this.restaurantRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Yummy")
                .address("Seoul")
                .menuItems(new ArrayList<>())
                .build();
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    @Test
    public void getRestaurantWithExists() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertEquals(1004L, restaurant.getId());
    }

    @Test
    public void getRestaurantWithNotExists() {
        Long id = 404L;

        Throwable e = assertThrows(RestaurantNotFoundException.class,
                () -> restaurantService.getRestaurant(id));

        assertEquals("Could not find restaurant id : " + id,
                e.getMessage());
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);

        assertEquals(1004L, restaurant.getId());
    }

    @Test
    public void addRestaurant() {
        Restaurant restaurant1 = Restaurant.builder()
                .name("Yummy2")
                .address("Gangneung")
                .build();

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant created = restaurantService.addRestaurant(restaurant1);

        assertEquals(1234L, created.getId());
    }

    @Test
    public void updateRestaurant() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Yum")
                .address("Seoul")
                .build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Yummy", "Gangneung");

        assertEquals("Gangneung", restaurant.getAddress());
        assertEquals("Yummy", restaurant.getName());
    }
}