package be.kdg.programming5.programming5.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Menu item chef.
 */
@Entity
@Table(name = "menu_item_chef", uniqueConstraints = { @UniqueConstraint(columnNames = { "menu_item_id", "chef_id" }) })
public class MenuItemChef extends AbstractEntity<Long> implements Serializable {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chef_id")
    private Chef chef;

    @Column(nullable = false)
    private LocalDateTime assignedDateTime;

    /**
     * Instantiates a new Menu item chef.
     */
    public MenuItemChef() {
    }

    /**
     * Instantiates a new Menu item chef.
     *
     * @param id       the id
     * @param menuItem the menu item
     * @param chef     the chef
     */
    public MenuItemChef(long id, MenuItem menuItem, Chef chef) {
        super(id);
        setMenuItem(menuItem);
        setChef(chef);
    }

    /**
     * Instantiates a new Menu item chef.
     *
     * @param menuItem the menu item
     * @param chef     the chef
     */
    public MenuItemChef(MenuItem menuItem, Chef chef) {
        setMenuItem(menuItem);
        setChef(chef);
    }

    /**
     * Instantiates a new Menu item chef.
     *
     * @param id               the id
     * @param menuItem         the menu item
     * @param chef             the chef
     * @param assignedDateTime the assigned date time
     */
    public MenuItemChef(long id, MenuItem menuItem, Chef chef, LocalDateTime assignedDateTime) {
        super(id);
        setMenuItem(menuItem);
        setChef(chef);
        setAssignedDateTime(assignedDateTime);
    }

    /**
     * Instantiates a new Menu item chef.
     *
     * @param menuItem         the menu item
     * @param chef             the chef
     * @param assignedDateTime the assigned date time
     */
    public MenuItemChef(MenuItem menuItem, Chef chef, LocalDateTime assignedDateTime) {
        setMenuItem(menuItem);
        setChef(chef);
        setAssignedDateTime(assignedDateTime);
    }

    /**
     * Gets menu item.
     *
     * @return the menu item
     */
    public MenuItem getMenuItem() {
        return menuItem;
    }

    /**
     * Sets menu item.
     *
     * @param menuItem the menu item
     */
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    /**
     * Gets chef.
     *
     * @return the chef
     */
    public Chef getChef() {
        return chef;
    }

    /**
     * Sets chef.
     *
     * @param chef the chef
     */
    public void setChef(Chef chef) {
        this.chef = chef;
    }

    /**
     * Gets assigned date time.
     *
     * @return the assigned date time
     */
    public LocalDateTime getAssignedDateTime() {
        return assignedDateTime;
    }

    /**
     * Sets assigned date time.
     *
     * @param assignedDateTime the assigned date time
     */
    public void setAssignedDateTime(LocalDateTime assignedDateTime) {
        this.assignedDateTime = assignedDateTime;
    }
}