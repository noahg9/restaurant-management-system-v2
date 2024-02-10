package be.kdg.programming5.programming5.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "menu_item_id", "chef_id" }) })
public class MenuItemChef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chef_id")
    private Chef chef;

    public MenuItemChef() {
    }

    public MenuItemChef(long id, MenuItem menuItem, Chef chef) {
        this.id = id;
        this.menuItem = menuItem;
        this.chef = chef;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
