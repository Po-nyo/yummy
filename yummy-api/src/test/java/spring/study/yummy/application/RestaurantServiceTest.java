package spring.study.yummy.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.yummy.domain.MenuItem;
import spring.study.yummy.domain.MenuItemRepository;
import spring.study.yummy.domain.Restaurant;
import spring.study.yummy.domain.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();
        mockMenuItemRepository();

        this.restaurantService = new RestaurantService(this.restaurantRepository, this.menuItemRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant(1004L, "Yummy", "Seoul");
        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("pasta"));

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    @Test
    public void getRestaurant() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        assertEquals(1004L, restaurant.getId());

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertEquals("pasta", menuItem.getName());
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        Restaurant restaurant = restaurants.get(0);
        assertEquals(1004L, restaurant.getId());
    }

    @Test
    public void addRestaurant() {
        Restaurant restaurant = new Restaurant("Yummy2", "Gangneung");
        Restaurant saved = new Restaurant(1234L, "Yummy2", "Gangneung");

        given(restaurantRepository.save(any())).willReturn(saved);

        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertEquals(1234L, created.getId());
    }

    @Test
    public void updateRestaurant() {
        Restaurant restaurant = new Restaurant(1004L, "Yum", "Seoul");

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Yummy", "Gangneung");

        assertEquals("Gangneung", restaurant.getAddress());
        assertEquals("Yummy", restaurant.getName());
    }
}