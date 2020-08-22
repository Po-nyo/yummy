package spring.study.yummy.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.yummy.domain.User;
import spring.study.yummy.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void getUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .name("tester")
                .email("test@example.com")
                .level(100L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();

        assertEquals("tester", users.get(0).getName());
    }

    @Test
    public void addUser() {
        String name = "admin";
        String email = "admin@example.com";

        User mockUser = User.builder().name(name).email(email).build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(mockUser);

        assertEquals(name, user.getName());
    }
}