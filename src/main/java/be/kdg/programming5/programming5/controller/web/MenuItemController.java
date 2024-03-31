package be.kdg.programming5.programming5.controller.web;

import be.kdg.programming5.programming5.controller.web.viewmodel.ChefViewModel;
import be.kdg.programming5.programming5.controller.web.viewmodel.MenuItemViewModel;
import be.kdg.programming5.programming5.model.Course;
import be.kdg.programming5.programming5.model.MenuItem;
import be.kdg.programming5.programming5.model.MenuItemChef;
import be.kdg.programming5.programming5.model.CustomUserDetails;
import be.kdg.programming5.programming5.service.MenuItemChefService;
import be.kdg.programming5.programming5.service.MenuItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

import static be.kdg.programming5.programming5.model.ChefRole.Admin;

/**
 * The type Menu item controller.
 */
@Controller
public class MenuItemController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MenuItemController.class);
    private final MenuItemService menuItemService;
    private final MenuItemChefService menuItemChefService;

    /**
     * Instantiates a new Menu item controller.
     *
     * @param menuItemService     the menu item service
     * @param menuItemChefService the menu item chef service
     */
    public MenuItemController(MenuItemService menuItemService, MenuItemChefService menuItemChefService) {
        this.menuItemService = menuItemService;
        this.menuItemChefService = menuItemChefService;
    }

    /**
     * All menu items model and view.
     *
     * @param session the session
     * @param model   the model
     * @param user    the user
     * @param request the request
     * @return the model and view
     */
    @GetMapping("/menu-items")
    public ModelAndView allMenuItems(HttpSession session, Model model, ModelAndView mav, @AuthenticationPrincipal CustomUserDetails user, HttpServletRequest request) {
        setupPage(session, model, "Menu Items");
        Long chefId = user != null ? user.getChefId() : null;
        mav.setViewName("menu/menu-items");
        mav.addObject("all_menu_items",
                menuItemService.getAllMenuItems()
                        .stream()
                        .map(menuItem -> new MenuItemViewModel(
                                menuItem.getId(),
                                menuItem.getName(),
                                menuItem.getPrice(),
                                menuItem.getCourse(),
                                menuItem.isVegetarian(),
                                menuItem.getSpiceLvl(),
                                request.isUserInRole(Admin.getCode())
                                        || chefId != null
                                        && menuItemChefService.isChefAssignedToMenuItem(menuItem.getId(), chefId)))
                        .toList());
        mav.addObject("courseValues", Course.values());
        return mav;
    }

    /**
     * One menu item model and view.
     *
     * @param menuItemId the menu item id
     * @param session    the session
     * @param model      the model
     * @param user       the user
     * @param request    the request
     * @return the model and view
     */
    @GetMapping("/menu-item")
    public ModelAndView oneMenuItem(@RequestParam("id") long menuItemId, HttpSession session, Model model, ModelAndView mav, @AuthenticationPrincipal CustomUserDetails user, HttpServletRequest request) {
        setupPage(session, model, "Menu Item");
        Long chefId = user != null ? user.getChefId() : null;
        MenuItem menuItem = menuItemService.getMenuItemWithChefs(menuItemId);
        mav.setViewName("menu/menu-item");
        mav.addObject("one_menu_item",
                new MenuItemViewModel(
                        menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getPrice(),
                        menuItem.getCourse(),
                        menuItem.isVegetarian(),
                        menuItem.getSpiceLvl(),
                        request.isUserInRole(Admin.getCode()) || chefId != null && menuItem.getChefs().stream().map(MenuItemChef::getChef).anyMatch(chef -> Objects.equals(chef.getId(), chefId)),
                        menuItem.getChefs().stream().map(menuItemChef -> new ChefViewModel(
                                menuItemChef.getChef().getId(),
                                menuItemChef.getChef().getFirstName(),
                                menuItemChef.getChef().getLastName(),
                                menuItemChef.getChef().getDateOfBirth(),
                                menuItemChef.getChef().getUsername(),
                                menuItemChef.getChef().getPassword(),
                                menuItemChef.getChef().getRole(),
                                false)).toList()));
        mav.addObject("courseValues", Course.values());
        return mav;
    }

    /**
     * Search menu items string.
     *
     * @param session the session
     * @param model   the model
     * @return the string
     */
    @GetMapping("/search-menu-items")
    public String searchMenuItems(HttpSession session, Model model) {
        setupPage(session, model, "Search Menu Items");
        return "menu/search-menu-items";
    }
}