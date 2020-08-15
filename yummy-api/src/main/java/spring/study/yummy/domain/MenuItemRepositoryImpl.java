package spring.study.yummy.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final List<MenuItem> menuItems = new ArrayList<>();

    public MenuItemRepositoryImpl() {
        menuItems.add(new MenuItem("pasta"));
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(long restaurantId) {
        return this.menuItems;
    }
}
