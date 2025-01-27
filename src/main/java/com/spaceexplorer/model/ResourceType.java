package main.java.com.spaceexplorer.model;

/**
 * Enumeration of available resource types in the game.
 * Resources can be collected by players from various space objects.
 */
public enum ResourceType {
    /** Basic metallic resources */
    IRON("Iron ore"),
    NICKEL("Nickel ore"),
    TITANIUM("Titanium ore"),

    /** Atmospheric gases */
    CARBON_DIOXIDE("Carbon dioxide"),
    SULFUR("Sulfur"),
    NITROGEN("Nitrogen"),
    OXYGEN("Oxygen"),
    HYDROGEN("Hydrogen"),
    HELIUM("Helium"),
    METHANE("Methane"),

    /** Common resources */
    WATER("Water"),
    MINERALS("Mixed minerals"),
    IRON_OXIDE("Iron oxide"),

    /** Ice variants */
    ICE("Regular ice"),
    WATER_ICE("Water ice"),

    /** Compounds */
    AMMONIA("Ammonia");

    private final String description;

    ResourceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
