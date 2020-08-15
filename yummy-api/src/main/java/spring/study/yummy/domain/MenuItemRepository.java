package spring.study.yummy.domain;

import java.util.List;

public interface MenuItemRepository {
    List<MenuItem> findAllByRestaurantId(long restaurantId);
}
