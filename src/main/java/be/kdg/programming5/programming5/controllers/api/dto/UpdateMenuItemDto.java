package be.kdg.programming5.programming5.controllers.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * The type Update menu item dto.
 */
public class UpdateMenuItemDto {
    @NotBlank
    private String name;

    /**
     * Instantiates a new Update menu item dto.
     */
    public UpdateMenuItemDto() {
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
