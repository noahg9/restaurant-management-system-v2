package be.kdg.programming5.programming5.domain;

/**
 * The enum Chef role.
 */
public enum ChefRole {

    /**
     * The Head chef.
     */
    HEAD_CHEF("ROLE_HEAD_CHEF", "Head Chef"),
    /**
     * The Sous chef.
     */
    SOUS_CHEF("ROLE_SOUS_CHEF", "Sous Chef"),
    /**
     * The Line cook.
     */
    LINE_COOK("ROLE_LINE_COOK", "Line Chef");

    private final String code;
    private final String name;

    ChefRole(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets role name.
     *
     * @return the role name
     */
    public String getName() {
        return name;
    }

    /**
     * From name chef role.
     *
     * @param name the name
     * @return the chef role
     */
    public static ChefRole fromName(java.lang.String name) {
        for (ChefRole role : values()) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No ChefRole found with name: " + name);
    }
}