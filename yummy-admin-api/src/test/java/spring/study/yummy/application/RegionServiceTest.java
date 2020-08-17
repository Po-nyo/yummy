package spring.study.yummy.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.study.yummy.domain.Region;
import spring.study.yummy.domain.RegionRepository;
import spring.study.yummy.domain.Restaurant;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RegionServiceTest {

    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository);
    }

    @Test
    public void getRegions() {
        List<Region> mockRegions = new ArrayList<>();
        mockRegions.add(Region.builder().name("Seoul").build());

        given(regionRepository.findAll()).willReturn(mockRegions);

        List<Region> regions = regionService.getRegions();

        Region region = regions.get(0);
        assertEquals("Seoul", region.getName());
    }

    @Test
    public void addRegion() {
        given(regionRepository.save(any())).will(invocation -> {
            Region region = invocation.getArgument(0);
            return region;
        });

        Region region1 = regionService.addRegion(Region.builder().name("Seoul").build());

        verify(regionRepository).save(any());

        assertEquals("Seoul", region1.getName());
    }
}