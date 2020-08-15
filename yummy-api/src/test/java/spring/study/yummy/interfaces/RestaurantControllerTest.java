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

import java.util.ArrayList;
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
        restaurants.add(new Restaurant(1004L, "Yummy", "Seoul"));
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1004")))
                .andExpect(content().string(containsString(
                "\"name\":\"Yummy\"")));
    }

    @Test
    public void detail() throws Exception {
        Restaurant restaurant1 = new Restaurant(1004L, "Yummy", "Seoul");
        restaurant1.addMenuItem(new MenuItem("pasta"));
        Restaurant restaurant2 = new Restaurant(2020L, "Gimbap Heaven", "Seoul");
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
    public void create() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Yummy2\",\"address\":\"Gangneung\"}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", "/restaurants/null"))
                    .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void update() throws Exception {
        mvc.perform(patch("/restaurants/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Yummy\",\"address\":\"Gangneung\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "Yummy", "Gangneung");
    }
}