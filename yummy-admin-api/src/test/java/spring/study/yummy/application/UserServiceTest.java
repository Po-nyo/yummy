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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockUserRepository();
        userService = new UserService(userRepository);
    }

    public void mockUserRepository() {
        Long id = 1004L;
        String name = "admin";
        String email = "admin@example.com";
        Long level = 100L;

        User user1 = User.builder().id(id).name(name).email(email).level(level).build();

        given(userRepository.findById(1004L)).willReturn(java.util.Optional.ofNullable(user1));
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

    @Test
    public void updateUser() {
        Long id = 1004L;
        String name = "Superman";
        String email = "admin@example.com";
        Long level = 100L;

        User user = userService.updateUser(id, name, email, level);

        verify(userRepository).findById(eq(id));

        assertEquals("Superman", user.getName());
    }
}