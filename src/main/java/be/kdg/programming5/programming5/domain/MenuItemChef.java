package be.kdg.programming5.programming5.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@Table(name = "menu_item_chef", uniqueConstraints = { @UniqueConstraint(columnNames = { "menu_item_id", "chef_id" }) })
public class MenuItemChef extends AbstractEntity<Long> implements Serializable {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chef_id")
    private Chef chef;

    public MenuItemChef() {
    }

    public MenuItemChef(long id, MenuItem menuItem, Chef chef) {
        super(id);
        setMenuItem(menuItem);
        setChef(chef);
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }
}
