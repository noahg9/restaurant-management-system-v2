package be.kdg.programming5.programming5.controllers.api;

import be.kdg.programming5.programming5.controllers.api.dto.ChefDto;
import be.kdg.programming5.programming5.controllers.api.dto.MenuItemDto;
import be.kdg.programming5.programming5.controllers.api.dto.NewChefDto;
import be.kdg.programming5.programming5.controllers.api.dto.UpdateChefNameDto;
import be.kdg.programming5.programming5.domain.MenuItemChef;
import be.kdg.programming5.programming5.service.ChefService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Chefs controller.
 */
@RestController
@RequestMapping("/api/chefs")
public class ChefsController {
    private final ChefService chefService;
    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Chefs controller.
     *
     * @param chefService the chef service
     * @param modelMapper the model mapper
     */
    public ChefsController(ChefService chefService, ModelMapper modelMapper) {
        this.chefService = chefService;
        this.modelMapper = modelMapper;
    }

    /**
     * Add chef response entity.
     *
     * @param chefDto the chef dto
     * @return the response entity
     */
// "/api/chefs"
    @PostMapping
    ResponseEntity<ChefDto> addChef(@RequestBody @Valid NewChefDto chefDto) {
        var createdChef = chefService.addChef(
                chefDto.getFirstName(), chefDto.getLastName());
        return new ResponseEntity<>(
                modelMapper.map(createdChef, ChefDto.class),
                HttpStatus.CREATED
        );
    }

    /**
     * Gets one chef.
     *
     * @param chefId the chef id
     * @return the one chef
     */
// "/api/chefs/{id}"
    @GetMapping("{id}")
    ResponseEntity<ChefDto> getOneChef(@PathVariable("id") long chefId) {
        var chef = chefService.getChef(chefId);
        if (chef == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(chef, ChefDto.class));
    }

    /**
     * Gets menu items of chef.
     *
     * @param chefId the chef id
     * @return the menu items of chef
     */
// "/api/chefs/{id}/menu-items"
    @GetMapping("{id}/menu-items")
    ResponseEntity<List<MenuItemDto>> getMenuItemsOfChef(@PathVariable("id") long chefId) {
        var chef = chefService.getChefWithMenuItems(chefId);
        if (chef == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (chef.getMenuItems().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(chef.getMenuItems()
                .stream()
                .map(MenuItemChef::getMenuItem)
                .map(dev -> modelMapper.map(dev, MenuItemDto.class))
                .toList());
    }

    /**
     * Search chefs response entity.
     *
     * @param search the search
     * @return the response entity
     */
// "/api/chefs"
    @GetMapping
    ResponseEntity<List<ChefDto>> searchChefs(@RequestParam(required = false) String search) {
        if (search == null) {
            return ResponseEntity
                    .ok(chefService.getAllChefs()
                            .stream()
                            .map(chef -> modelMapper.map(chef, ChefDto.class))
                            .toList());
        } else {
            var searchResult = chefService.searchChefsByFirstNameOrLastName(search);
            if (searchResult.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(searchResult
                        .stream()
                        .map(chef -> modelMapper.map(chef, ChefDto.class))
                        .toList());
            }
        }
    }

    /**
     * Delete chef response entity.
     *
     * @param chefId the chef id
     * @return the response entity
     */
// "/api/chefs/{id}"
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteChef(@PathVariable("id") long chefId) {
        if (chefService.removeChef(chefId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Change chef response entity.
     *
     * @param chefId            the chef id
     * @param updateChefNameDto the update chef name dto
     * @return the response entity
     */
    @PatchMapping("{id}")
    ResponseEntity<Void> changeChef(@PathVariable("id") long chefId,
                                     @RequestBody @Valid UpdateChefNameDto updateChefNameDto) {
        if (chefService.changeChefName(chefId, updateChefNameDto.getFirstName(), updateChefNameDto.getLastName())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
