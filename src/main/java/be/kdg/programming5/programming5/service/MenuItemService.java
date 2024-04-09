package be.kdg.programming5.programming5.service;

import be.kdg.programming5.programming5.model.AssignedChef;
import be.kdg.programming5.programming5.model.Chef;
import be.kdg.programming5.programming5.model.Course;
import be.kdg.programming5.programming5.model.MenuItem;
import be.kdg.programming5.programming5.repository.AssignedChefRepository;
import be.kdg.programming5.programming5.repository.ChefRepository;
import be.kdg.programming5.programming5.repository.MenuItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The type Menu item service.
 */
@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final AssignedChefRepository assignedChefRepository;
    private final ChefRepository chefRepository;

    public MenuItemService(MenuItemRepository menuItemRepository, AssignedChefRepository assignedChefRepository, ChefRepository chefRepository) {
        this.menuItemRepository = menuItemRepository;
        this.assignedChefRepository = assignedChefRepository;
        this.chefRepository = chefRepository;
    }

    /**
     * Gets all menu items.
     *
     * @return the all menu items
     */
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    /**
     * Gets menu item.
     *
     * @param menuItemId the menu item id
     * @return the menu item
     */
    public MenuItem getMenuItem(long menuItemId) {
        return menuItemRepository.findById(menuItemId).orElse(null);
    }

    /**
     * Gets menu item with chefs.
     *
     * @param menuItemId the menu item id
     * @return the menu item with chefs
     */
    @Transactional
    public MenuItem getMenuItemWithChefs(long menuItemId) {
        return menuItemRepository.findByIdWithChefs(menuItemId)
                .map(menuItem -> {
                    menuItem.getChefs().size();
                    return menuItem;
                })
                .orElse(null);
    }

    /**
     * Gets menu items of chef.
     *
     * @param chefId the chef id
     * @return the menu items of chef
     */
    public List<MenuItem> getMenuItemsOfChef(long chefId) {
        return menuItemRepository.findByChefId(chefId);
    }

    /**
     * Search menu items by name like list.
     *
     * @param searchTerm the search term
     * @return the list
     */
    public List<MenuItem> searchMenuItemsByNameLike(String searchTerm) {
        return menuItemRepository.findMenuItemsByNameLikeIgnoreCase("%" + searchTerm + "%");
    }

    /**
     * Save menu item menu item.
     *
     * @param name       the name
     * @param price      the price
     * @param course     the course
     * @param vegetarian the vegetarian
     * @param spiceLevel   the spice level
     * @return the menu item
     */
    @Transactional
    public MenuItem saveMenuItem(String name, double price, Course course, Boolean vegetarian, int spiceLevel) {
        return menuItemRepository.save(new MenuItem(name, price, course, vegetarian, spiceLevel));
    }

    /**
     * Save menu item menu item.
     *
     * @param name       the name
     * @param price      the price
     * @param course     the course
     * @param vegetarian the vegetarian
     * @param spiceLevel   the spice level
     * @param userId     the user id
     * @return the menu item
     */
    @Transactional
    public MenuItem saveMenuItem(String name, double price, Course course, Boolean vegetarian, int spiceLevel, List<Long> chefIds, long userId) {
        MenuItem menuItem = menuItemRepository.save(new MenuItem(name, price, course, vegetarian, spiceLevel));
        Chef user = chefRepository.findById(userId).orElse(null);
        assignedChefRepository.save(new AssignedChef(menuItem, user, LocalDateTime.now()));
        if (chefIds != null && !chefIds.isEmpty()) {
            for (Long chefId : chefIds) {
                chefRepository.findById(chefId).ifPresent(chef -> assignedChefRepository.save(new AssignedChef(menuItem, chef, LocalDateTime.now())));
            }
        }
        return menuItem;
    }

    /**
     * Save menu item menu item.
     *
     * @param menuItem the menu item
     * @return the menu item
     */
    @Transactional
    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    /**
     * Update menu item boolean.
     *
     * @param menuItemId the menu item id
     * @param name       the name
     * @param price      the price
     * @param vegetarian the vegetarian
     * @param spiceLevel   the spice level
     * @return the boolean
     */
    public boolean updateMenuItem(long menuItemId, String name, double price, boolean vegetarian, int spiceLevel) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            return false;
        }
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setVegetarian(vegetarian);
        menuItem.setSpiceLevel(spiceLevel);
        menuItemRepository.save(menuItem);
        return true;
    }

    /**
     * Delete menu item boolean.
     *
     * @param menuItemId the menu item id
     * @return the boolean
     */
    @Transactional
    public boolean deleteMenuItem(long menuItemId) {
        Optional<MenuItem> menuItem = menuItemRepository.findByIdWithChefs(menuItemId);
        if (menuItem.isEmpty()) {
            return false;
        }
        assignedChefRepository.deleteAll(menuItem.get().getChefs());
        menuItemRepository.deleteById(menuItemId);
        return true;
    }
}
