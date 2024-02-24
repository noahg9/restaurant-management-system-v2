package be.kdg.programming5.programming5.service;

import be.kdg.programming5.programming5.domain.Chef;
import be.kdg.programming5.programming5.domain.Course;
import be.kdg.programming5.programming5.domain.MenuItem;
import be.kdg.programming5.programming5.domain.Restaurant;
import be.kdg.programming5.programming5.repository.ChefRepository;
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
    private final ChefRepository chefRepository;

    /**
     * Constructs a SpringDataMenuItemService with the specified repositories.
     *
     * @param menuItemRepository The repository for MenuItem entities.
     * @param chefRepository     The repository for Chef entities.
     */
    public MenuItemService(MenuItemRepository menuItemRepository, ChefRepository chefRepository) {
        this.menuItemRepository = menuItemRepository;
        this.chefRepository = chefRepository;
    }

    /**
     * Retrieves a list of all menu items.
     *
     * @return A list of all menu items.
     */
    
    public List<MenuItem> getMenuItems() {
        return menuItemRepository.findAll();
    }

    /**
     * Retrieves a list of all menu items with its associated chefs.
     *
     * @return A list of all menu items.
     */
    
    public List<MenuItem> getMenuItemsWithChefs() {
        return menuItemRepository.findAllWithChefs();
    }

    /**
     * Retrieves a menu item by its identifier.
     *
     * @param id The identifier of the menu item.
     * @return The menu item with the specified identifier, or null if not found.
     */
    
    public MenuItem getMenuItem(int id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a menu item with its associated chefs.
     *
     * @param id The identifier of the menu item.
     * @return The menu item with the specified identifier, or null if not found.
     */
    
    public MenuItem getMenuItemWithChefs(int id) {
        return menuItemRepository.findByIdWithChefs(id).orElse(null);
    }

    public List<MenuItem> getMenuItemsOfChef(int chefId) {
        return menuItemRepository.findByChefId(chefId);
    }

    /**
     * Retrieves a list of menu items with a price less than or equal to the specified maximum price.
     *
     * @param maxPrice The maximum price.
     * @return A list of menu items meeting the price criteria.
     */
    
    public List<MenuItem> getMenuItemsByMaxPrice(double maxPrice) {
        return menuItemRepository.findByPriceLessThanEqual(maxPrice);
    }

    /**
     * Retrieves a list of vegetarian menu items.
     *
     * @return A list of vegetarian menu items.
     */
    
    public List<MenuItem> getVegMenuItems() {
        return menuItemRepository.findByVegetarianTrue();
    }

    public List<MenuItem> searchMenuItemsByNameLike(String searchTerm) {
        return menuItemRepository.findMenuItemsByNameLike(searchTerm);
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
    
    @Transactional
    public MenuItem addMenuItem(String name, double price, Course course, Boolean vegetarian, int spiceLvl, Restaurant restaurant) {
        return menuItemRepository.save(new MenuItem(name, price, course, vegetarian, spiceLvl, restaurant));
    }

    /**
     * Adds a menu item to the system.
     *
     * @param menuItem The menu item to add.
     * @return The added menu item.
     */
    
    @Transactional
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    /**
     * Updates a menu item's information.
     *
     * @param menuItem The menu item to update.
     */
    
    @Transactional
    public void updateMenuItem(MenuItem menuItem) {
        // Implementation needed based on the specific requirements.
    }

    /**
     * Deletes a menu item by its identifier.
     *
     * @param id The identifier of the menu item to delete.
     */
    
    @Transactional
    public void deleteMenuItem(int id) {
/*        MenuItem menuItem = getMenuItem(id);
        if (menuItem != null) {
            menuItem.getChefs().forEach(chef -> {
                chef.getMenuItems().remove(menuItem);
                chefRepository.save(chef);
            });
            menuItemRepository.deleteById(id);
        }*/
    }
}
