package spring.study.yummy.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.study.yummy.application.RestaurantService;
import spring.study.yummy.domain.MenuItem;
import spring.study.yummy.domain.Restaurant;
import spring.study.yummy.domain.RestaurantNotFoundException;
import spring.study.yummy.domain.Review;
import spring.study.yummy.interfaces.RestaurantController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
            .id(1004L)
            .name("Yummy")
            .address("Seoul")
            .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1004")))
                .andExpect(content().string(containsString(
                "\"name\":\"Yummy\"")));
    }

    @Test
    public void detailWithExists() throws Exception {
        Restaurant restaurant1 = Restaurant.builder()
                .id(1004L)
                .name("Yummy")
                .address("Seoul")
                .build();

        restaurant1.setMenuItems(Arrays.asList(
                MenuItem.builder()
                .name("pasta")
                .build()));

        Review review = Review.builder()
                .name("Woo")
                .score(5)
                .description("great").build();

        restaurant1.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1004")))
                .andExpect(content().string(containsString(
                        "\"name\":\"Yummy\"")))
                .andExpect(content().string(containsString(
                        "pasta"
                )))
                .andExpect(content().string(containsString(
                        "great"
                )));
    }

    @Test
    public void detailWithNotExists() throws Exception {
        given(restaurantService.getRestaurant(404L)).
                willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }
}