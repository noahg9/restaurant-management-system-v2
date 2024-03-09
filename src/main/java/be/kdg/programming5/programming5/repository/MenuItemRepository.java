package be.kdg.programming5.programming5.repository;

import be.kdg.programming5.programming5.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Menu item repository.
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    /**
     * Find by id with chefs optional.
     *
     * @param menuItemId the menu item id
     * @return the optional
     */
    @Query("""
        select m from MenuItem m
        left join fetch m.chefs chefs
        left join fetch chefs.chef
        where m.id = :menuItemId
        """)
    Optional<MenuItem> findByIdWithChefs(long menuItemId);

    /**
     * Find all with chefs list.
     *
     * @return the list
     */
    @Query("""
        select m from MenuItem m
        left join fetch m.chefs chefs
        left join fetch chefs.chef
        """)
    List<MenuItem> findAllWithChefs();

    /**
     * Find by chef id list.
     *
     * @param chefId the chef id
     * @return the list
     */
    @Query("""
           select m from MenuItem m
           left join m.chefs chefs
           left join chefs.chef chef
           where chef.id = :chefId
           """)
    List<MenuItem> findByChefId(long chefId);

    /**
     * Find by price less than equal list.
     *
     * @param maxPrice the max price
     * @return the list
     */
    List<MenuItem> findByPriceLessThanEqual(double maxPrice);

    /**
     * Find by vegetarian true list.
     *
     * @return the list
     */
    List<MenuItem> findByVegetarianTrue();

    /**
     * Find menu items by name like ignore case list.
     *
     * @param searchTerm the search term
     * @return the list
     */
    List<MenuItem> findMenuItemsByNameLikeIgnoreCase(String searchTerm);

}
