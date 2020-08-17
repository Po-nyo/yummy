package spring.study.yummy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    public void create() {
        Category category = Category.builder().name("Korean Food").build();

        assertEquals("Korean Food", category.getName());
    }
}