package be.kdg.programming5.programming5.controller.api;

import be.kdg.programming5.programming5.dto.ChefDto;
import be.kdg.programming5.programming5.dto.MenuItemDto;
import be.kdg.programming5.programming5.dto.NewMenuItemDto;
import be.kdg.programming5.programming5.dto.UpdateMenuItemDto;
import be.kdg.programming5.programming5.model.MenuItem;
import be.kdg.programming5.programming5.model.MenuItemChef;
import be.kdg.programming5.programming5.model.CustomUserDetails;
import be.kdg.programming5.programming5.service.MenuItemChefService;
import be.kdg.programming5.programming5.service.MenuItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static be.kdg.programming5.programming5.model.ChefRole.Admin;


/**
 * The type Menu items controller.
 */
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemsController {
    private final MenuItemService menuItemService;
    private final MenuItemChefService menuItemChefService;
    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Menu items controller.
     *
     * @param menuItemService     the menu item service
     * @param menuItemChefService the menu item chef service
     * @param modelMapper         the model mapper
     */
    public MenuItemsController(MenuItemService menuItemService, MenuItemChefService menuItemChefService, ModelMapper modelMapper) {
        this.menuItemService = menuItemService;
        this.menuItemChefService = menuItemChefService;
        this.modelMapper = modelMapper;
    }

    /**
     * Gets one menu item.
     *
     * @param menuItemId the menu item id
     * @return the one menu item
     */
    @GetMapping("{id}")
    ResponseEntity<MenuItemDto> getOneMenuItem(@PathVariable("id") long menuItemId) {
        MenuItem menuItem = menuItemService.getMenuItem(menuItemId);
        if (menuItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(menuItem, MenuItemDto.class));
    }

    /**
     * Gets all menu items.
     *
     * @return the all menu items
     */
    @GetMapping
    ResponseEntity<List<MenuItemDto>> getAllMenuItems() {
        List<MenuItem> allMenuItems = menuItemService.getAllMenuItems();
        if (allMenuItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<MenuItemDto> menuItemDtos = allMenuItems.stream().map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class)).collect(Collectors.toList());
            return ResponseEntity.ok(menuItemDtos);
        }
    }

    /**
     * Gets chefs of menu item.
     *
     * @param menuItemId the menu item id
     * @return the chefs of menu item
     */
    @GetMapping("{id}/chefs")
    ResponseEntity<List<ChefDto>> getChefsOfMenuItem(@PathVariable("id") long menuItemId) {
        MenuItem menuItem = menuItemService.getMenuItemWithChefs(menuItemId);
        if (menuItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (menuItem.getChefs().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(menuItem.getChefs().stream().map(MenuItemChef::getChef).map(chef -> modelMapper.map(chef, ChefDto.class)).toList());
    }

    /**
     * Search menu items response entity.
     *
     * @param search the search
     * @return the response entity
     */
    @GetMapping("search")
    ResponseEntity<List<MenuItemDto>> searchMenuItems(@RequestParam(required = false) String search) {
        if (search == null || search.trim().isEmpty()) {
            return ResponseEntity.ok(menuItemService.getAllMenuItems().stream().map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class)).toList());
        } else {
            List<MenuItem> searchResult = menuItemService.searchMenuItemsByNameLike(search);
            if (searchResult.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(searchResult.stream().map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class)).toList());
            }
        }
    }

    /**
     * Add menu item response entity.
     *
     * @param menuItemDto the menu item dto
     * @param user        the user
     * @return the response entity
     */
    @PostMapping
    ResponseEntity<MenuItemDto> addMenuItem(@RequestBody @Valid NewMenuItemDto menuItemDto, @AuthenticationPrincipal CustomUserDetails user) {
        MenuItem createdMenuItem = menuItemService.addMenuItem(menuItemDto.getName(), menuItemDto.getPrice(), menuItemDto.getCourse(), menuItemDto.isVegetarian(), menuItemDto.getSpiceLvl(), user.getChefId());
        return new ResponseEntity<>(modelMapper.map(createdMenuItem, MenuItemDto.class), HttpStatus.CREATED);
    }

    /**
     * Delete menu item response entity.
     *
     * @param menuItemId the menu item id
     * @param user       the user
     * @param request    the request
     * @return the response entity
     */
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteMenuItem(@PathVariable("id") long menuItemId, @AuthenticationPrincipal CustomUserDetails user, HttpServletRequest request) {
        if (!menuItemChefService.isChefAssignedToMenuItem(menuItemId, user.getChefId()) && !request.isUserInRole(Admin.getCode())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (menuItemService.removeMenuItem(menuItemId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Change menu item response entity.
     *
     * @param menuItemId        the menu item id
     * @param updateMenuItemDto the update menu item dto
     * @param user              the user
     * @param request           the request
     * @return the response entity
     */
    @PatchMapping("{id}")
    ResponseEntity<Void> changeMenuItem(@PathVariable("id") long menuItemId, @RequestBody @Valid UpdateMenuItemDto updateMenuItemDto, @AuthenticationPrincipal CustomUserDetails user, HttpServletRequest request) {
        if (!menuItemChefService.isChefAssignedToMenuItem(menuItemId, user.getChefId()) && !request.isUserInRole(Admin.getCode())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (menuItemService.changeMenuItem(menuItemId, updateMenuItemDto.getName(), updateMenuItemDto.getPrice(), updateMenuItemDto.getCourse(), updateMenuItemDto.isVegetarian(), updateMenuItemDto.getSpiceLvl())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}