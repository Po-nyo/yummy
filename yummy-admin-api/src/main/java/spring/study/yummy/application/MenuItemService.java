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

    public void bulkUpdate(Long restaurant_id, List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems) {
            update(restaurant_id, menuItem);
        }
    }

    private void update(Long restaurant_id, MenuItem menuItem) {
        if(menuItem.isDelete()) {
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestaurantId(restaurant_id);
        menuItemRepository.save(menuItem);
    }
}
