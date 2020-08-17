package spring.study.yummy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionTest {

    @Test
    public void create() {
        Region region = Region.builder().name("서울").build();

        assertEquals("서울", region.getName());
    }
}