package be.kdg.programming5.programming5.service;

import be.kdg.programming5.programming5.domain.Course;
import be.kdg.programming5.programming5.domain.MenuItem;
import be.kdg.programming5.programming5.domain.Restaurant;
import be.kdg.programming5.programming5.repository.MenuItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation using Spring Data JPA for MenuItem-related operations.
 */
@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemChefService menuItemChefService;
    private final RestaurantService restaurantService;

    /**
     * Constructs a SpringDataMenuItemService with the specified repositories.
     *
     * @param menuItemRepository  The repository for MenuItem entities.
     * @param menuItemChefService The service for MenuItemChef entities.
     * @param restaurantService   The service for Restaurant entities.
     */
    public MenuItemService(MenuItemRepository menuItemRepository, MenuItemChefService menuItemChefService, RestaurantService restaurantService) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemChefService = menuItemChefService;
        this.restaurantService = restaurantService;
    }

    /**
     * Retrieves all chefs of all menu items.
     *
     * @return All chefs of all menu items.
     */
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    /**
     * Retrieves all chefs of all menu items with its associated chefs.
     *
     * @return All chefs of all menu items.
     */
    public List<MenuItem> getMenuItemsWithChefs() {
        return menuItemRepository.findAllWithChefs();
    }

    /**
     * Retrieves a menu item by its identifier.
     *
     * @param menuItemId The identifier of the menu item.
     * @return The menu item with the specified identifier, or null if not found.
     */
    public MenuItem getMenuItem(long menuItemId) {
        return menuItemRepository.findById(menuItemId).orElse(null);
    }

    /**
     * Retrieves a menu item with its associated chefs.
     *
     * @param menuItemId The identifier of the menu item.
     * @return The menu item with the specified identifier, or null if not found.
     */
    public MenuItem getMenuItemWithChefs(long menuItemId) {
        return menuItemRepository.findByIdWithChefs(menuItemId).orElse(null);
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
     * Retrieves all chefs of menu items with a price less than or equal to the specified maximum price.
     *
     * @param maxPrice The maximum price.
     * @return All chefs of menu items meeting the price criteria.
     */
    public List<MenuItem> getMenuItemsByMaxPrice(double maxPrice) {
        return menuItemRepository.findByPriceLessThanEqual(maxPrice);
    }

    /**
     * Retrieves all chefs of vegetarian menu items.
     *
     * @return All chefs of vegetarian menu items.
     */
    public List<MenuItem> getVegMenuItems() {
        return menuItemRepository.findByVegetarianTrue();
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
     * Adds a new menu item with the specified details.
     *
     * @param name       The name of the menu item.
     * @param price      The price of the menu item.
     * @param course     The course of the menu item.
     * @param vegetarian Whether the menu item is vegetarian.
     * @param spiceLvl   The spice level of the menu item.
     * @param restaurant The restaurant offering the menu item.
     * @return The newly created menu item.
     */
    public MenuItem addMenuItem(String name, double price, Course course, Boolean vegetarian, int spiceLvl, Restaurant restaurant) {
        return menuItemRepository.save(new MenuItem(name, price, course, vegetarian, spiceLvl, restaurant));
    }

    /**
     * Add menu item menu item.
     *
     * @param name  the name
     * @param price the price
     * @return the menu item
     */
    public MenuItem addMenuItem(String name, double price) {
        return menuItemRepository.save(new MenuItem(name, price, Course.Main, false, 0, restaurantService.getRestaurant(1)));
    }

    /**
     * Adds a menu item to the system.
     *
     * @param menuItem The menu item to add.
     * @return The added menu item.
     */
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    /**
     * Deletes a menu item by its identifier.
     *
     * @param menuItemId The identifier of the menu item to delete.
     * @return the boolean
     */
    @Transactional
    public boolean removeMenuItem(long menuItemId) {
        var menuItem = menuItemRepository.findByIdWithChefs(menuItemId);
        if (menuItem.isEmpty()) {
            return false;
        }
        menuItemChefService.removeAllMenuItems(menuItem.get());
        menuItemRepository.deleteById(menuItemId);
        return true;
    }

    /**
     * Change menu item name boolean.
     *
     * @param menuItemId the menu item id
     * @param name       the name
     * @return the boolean
     */
    public boolean changeMenuItemName(long menuItemId, String name) {
        var menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            return false;
        }
        menuItem.setName(name);
        menuItemRepository.save(menuItem);
        return true;
    }
}
