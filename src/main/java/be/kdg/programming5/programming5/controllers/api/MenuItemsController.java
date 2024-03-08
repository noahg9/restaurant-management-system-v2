package be.kdg.programming5.programming5.controllers.api;

import be.kdg.programming5.programming5.controllers.api.dto.ChefDto;
import be.kdg.programming5.programming5.controllers.api.dto.MenuItemDto;
import be.kdg.programming5.programming5.controllers.api.dto.NewMenuItemDto;
import be.kdg.programming5.programming5.controllers.api.dto.UpdateMenuItemNameDto;
import be.kdg.programming5.programming5.domain.MenuItemChef;
import be.kdg.programming5.programming5.service.MenuItemService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Menu items controller.
 */
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemsController {
    private final MenuItemService menuItemService;
    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Menu items controller.
     *
     * @param menuItemService the menu item service
     * @param modelMapper     the model mapper
     */
    public MenuItemsController(MenuItemService menuItemService, ModelMapper modelMapper) {
        this.menuItemService = menuItemService;
        this.modelMapper = modelMapper;
    }

    /**
     * Add menu item response entity.
     *
     * @param menuItemDto the menu item dto
     * @return the response entity
     */
// "/api/menu-items"
    @PostMapping
    ResponseEntity<MenuItemDto> addMenuItem(@RequestBody @Valid NewMenuItemDto menuItemDto) {
        var createdMenuItem = menuItemService.addMenuItem(
                menuItemDto.getName(), menuItemDto.getPrice());
        return new ResponseEntity<>(
                modelMapper.map(createdMenuItem, MenuItemDto.class),
                HttpStatus.CREATED
        );
    }

    /**
     * Gets one menu item.
     *
     * @param menuItemId the menu item id
     * @return the one menu item
     */
// "/api/menu-items/{id}"
    @GetMapping("{id}")
    ResponseEntity<MenuItemDto> getOneMenuItem(@PathVariable("id") long menuItemId) {
        var menuItem = menuItemService.getMenuItem(menuItemId);
        if (menuItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(menuItem, MenuItemDto.class));
    }

    /**
     * Gets chefs of menu item.
     *
     * @param menuItemId the menu item id
     * @return the chefs of menu item
     */
// "/api/menu-items/{id}/chefs"
    @GetMapping("{id}/chefs")
    ResponseEntity<List<ChefDto>> getChefsOfMenuItem(@PathVariable("id") long menuItemId) {
        var menuItem = menuItemService.getMenuItemWithChefs(menuItemId);
        if (menuItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (menuItem.getChefs().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(menuItem.getChefs()
                .stream()
                .map(MenuItemChef::getChef)
                .map(dev -> modelMapper.map(dev, ChefDto.class))
                .toList());
    }

    /**
     * Search menu items response entity.
     *
     * @param search the search
     * @return the response entity
     */
// "/api/menu-items"
    @GetMapping
    ResponseEntity<List<MenuItemDto>> searchMenuItems(@RequestParam(required = false) String search) {
        if (search == null) {
            return ResponseEntity
                    .ok(menuItemService.getAllMenuItems()
                            .stream()
                            .map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class))
                            .toList());
        } else {
            var searchResult = menuItemService.searchMenuItemsByNameLike(search);
            if (searchResult.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(searchResult
                        .stream()
                        .map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class))
                        .toList());            }
        }
    }

    /**
     * Delete menu item response entity.
     *
     * @param menuItemId the menu item id
     * @return the response entity
     */
// "/api/menu-items/{id}"
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteMenuItem(@PathVariable("id") long menuItemId) {
        if (menuItemService.removeMenuItem(menuItemId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Change menu item response entity.
     *
     * @param menuItemId            the menu item id
     * @param updateMenuItemNameDto the update menu item name dto
     * @return the response entity
     */
    @PatchMapping("{id}")
    ResponseEntity<Void> changeMenuItem(@PathVariable("id") long menuItemId,
                                    @RequestBody @Valid UpdateMenuItemNameDto updateMenuItemNameDto) {
        if (menuItemService.changeMenuItemName(menuItemId, updateMenuItemNameDto.getName())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
