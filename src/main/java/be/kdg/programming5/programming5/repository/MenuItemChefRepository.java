package be.kdg.programming5.programming5.repository;

import be.kdg.programming5.programming5.domain.MenuItemChef;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Menu item chef repository.
 */
public interface MenuItemChefRepository extends JpaRepository<MenuItemChef, Long> {
}