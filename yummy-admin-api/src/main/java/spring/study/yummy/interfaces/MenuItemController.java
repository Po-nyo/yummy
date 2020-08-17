package spring.study.yummy.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.study.yummy.application.MenuItemService;
import spring.study.yummy.domain.MenuItem;

import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/restaurants/{restaurant_id}/menuitems")
    public List<MenuItem> list(@PathVariable("restaurant_id") Long restaurant_id) {
        return menuItemService.getMenuItems(restaurant_id);
    }

    @PatchMapping("/restaurants/{restaurant_id}/menuitems")
    public String bulkUpdate(@PathVariable("restaurant_id") Long restaurant_id,
                             @RequestBody List<MenuItem> menuItems) {
        menuItemService.bulkUpdate(restaurant_id, menuItems);

        return "";
    }
}

