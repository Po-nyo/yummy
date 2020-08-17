package spring.study.yummy.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.yummy.domain.MenuItem;
import spring.study.yummy.domain.MenuItemRepository;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getMenuItems(Long restaurant_id) {
        return menuItemRepository.findAllByRestaurantId(restaurant_id);
    }
}
