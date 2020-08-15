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

        Restaurant restaurant2 = Restaurant.builder()
                .id(2020L)
                .name("Gimbap Heaven")
                .address("Seoul")
                .build();

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurant(2020L)).willReturn(restaurant2);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1004")))
                .andExpect(content().string(containsString(
                        "\"name\":\"Yummy\"")))
                .andExpect(content().string(containsString(
                        "pasta"
                )));

        mvc.perform(get("/restaurants/2020"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":2020")))
                .andExpect(content().string(containsString(
                        "\"name\":\"Gimbap Heaven\"")));
    }

    @Test
    public void detailWithNotExists() throws Exception {
        given(restaurantService.getRestaurant(404L)).
                willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                    .id(1234L)
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .build();
        });

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Yummy2\",\"address\":\"Gangneung\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", "/restaurants/1234"))
                    .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Yummy\",\"address\":\"Gangneung\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "Yummy", "Gangneung");
    }

    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}