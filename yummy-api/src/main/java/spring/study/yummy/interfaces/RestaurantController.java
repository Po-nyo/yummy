package spring.study.yummy.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.study.yummy.domain.MenuItem;
import spring.study.yummy.domain.MenuItemRepository;
import spring.study.yummy.domain.Restaurant;
import spring.study.yummy.domain.RestaurantRepository;

import java.awt.*;
import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
//        List<Restaurant> restaurants = new ArrayList<>();
//
//        restaurants.add(new Restaurant(1004L, "Yummy", "Seoul"));
//        restaurants.add(new Restaurant(2020L, "Gimbap Heaven", "Seoul"));

        return restaurantRepository.findAll();
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantRepository.findById(id);

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);

        restaurant.addMenuItem(new MenuItem("pasta"));

        return restaurant;
    }

}
