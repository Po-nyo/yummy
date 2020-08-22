package spring.study.yummy.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import spring.study.yummy.application.UserService;
import spring.study.yummy.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .name("tester")
                .email("test@example.com")
                .level(1L)
                .build());

        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tester")));
    }

    @Test
    public void create() throws Exception {
        given(userService.addUser(any())).will(invocation -> {
            User user = invocation.getArgument(0);

            return User.builder()
                    .id(1L)
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        });

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"admin\",\"email\":\"admin@example.com\"}"))
                    .andExpect(status().isCreated());

        verify(userService).addUser(any());
    }

    @Test
    public void update() throws Exception {
        Long id = 1004L;
        String name = "admin";
        String email = "admin@example.com";
        Long level = 100L;

        User user = User.builder()
                .id(id)
                .name(name)
                .email(email)
                .level(level)
                .build();

        given(userService.updateUser(id, name, email, level)).willReturn(user);

        mvc.perform(patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"admin\",\"email\":\"admin@example.com\",\"level\":100}"))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq(id), eq(name), eq(email), eq(level));
    }
}