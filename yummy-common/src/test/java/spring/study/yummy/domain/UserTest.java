package spring.study.yummy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void create() {
        User user = User.builder()
                .name("tester")
                .email("test@example.com")
                .level(100L)
                .build();

        assertEquals("tester", user.getName());
        assertTrue(user.isAdmin());
        assertTrue(user.isActive());

        user.deactivate();
        assertFalse(user.isActive());
    }
}