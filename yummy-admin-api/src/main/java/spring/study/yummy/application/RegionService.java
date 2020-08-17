package spring.study.yummy.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.yummy.domain.Region;
import spring.study.yummy.domain.RegionRepository;
import spring.study.yummy.domain.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {

    private RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions() {
        return regionRepository.findAll();
    }

    public Region addRegion(Region region) {
        return regionRepository.save(region);
    }
}
