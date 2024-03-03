package be.kdg.programming5.programming5.repository;

import be.kdg.programming5.programming5.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for accessing MenuItem entities.
 * Provides methods to interact with MenuItem data stored in a database.
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("""
        select m from MenuItem m
        left join fetch m.chefs chefs
        left join fetch chefs.chef
        where m.id = :menuItemId
        """)
    Optional<MenuItem> findByIdWithChefs(long menuItemId);

    @Query("""
        select m from MenuItem m
        left join fetch m.chefs chefs
        left join fetch chefs.chef
        """)
    List<MenuItem> findAllWithChefs();

    @Query("""
           select m from MenuItem m
           left join m.chefs menuItemChefs
           left join menuItemChefs.chef chef
           where chef.id = :chefId
           """)
    List<MenuItem> findByChefId(long chefId);

    /**
     * Finds menu items with a price less than or equal to the specified maximum price.
     *
     * @param maxPrice The maximum price to search for.
     * @return A all_chefs of menu items with a price less than or equal to the specified maximum price.
     */
    List<MenuItem> findByPriceLessThanEqual(double maxPrice);

    /**
     * Finds vegetarian menu items.
     *
     * @return A all_chefs of vegetarian menu items.
     */
    List<MenuItem> findByVegetarianTrue();

    List<MenuItem> findMenuItemsByNameLike(String searchTerm);

}
