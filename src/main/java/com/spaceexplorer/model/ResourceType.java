package main.java.com.spaceexplorer.model;

/**
 * Enumeration of available resource types in the game.
 * Resources can be collected by players from various space objects.
 * Each resource type has a description that provides more detail about the resource.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public enum ResourceType {
    /** Basic metallic resources found primarily on rocky planets and asteroids */
    IRON("Iron ore"),
    /** Nickel deposits commonly found in asteroids and planetary crusts */
    NICKEL("Nickel ore"),
    /** Valuable titanium resources used in advanced construction */
    TITANIUM("Titanium ore"),

    /** Atmospheric gases essential for terraforming and life support */
    CARBON_DIOXIDE("Carbon dioxide"),
    /** Volcanic mineral found on tectonically active planets */
    SULFUR("Sulfur"),
    /** Essential gas for creating breathable atmospheres */
    NITROGEN("Nitrogen"),
    /** Critical resource for life support systems */
    OXYGEN("Oxygen"),
    /** Fuel source for advanced propulsion systems */
    HYDROGEN("Hydrogen"),
    /** Rare gas used in scientific equipment */
    HELIUM("Helium"),
    /** Hydrocarbon gas found in gas giant atmospheres */
    METHANE("Methane"),

    /** Common resources essential for survival and base operations */
    WATER("Water"),
    /** Mixed mineral deposits with various industrial uses */
    MINERALS("Mixed minerals"),
    /** Common compound found on Mars-like planets */
    IRON_OXIDE("Iron oxide"),

    /** Various forms of frozen materials */
    ICE("Regular ice"),
    /** Frozen water found in polar regions and comets */
    WATER_ICE("Water ice"),

    /** Chemical compounds */
    AMMONIA("Ammonia");

    /** Descriptive text providing details about the resource */
    private final String description;

    /**
     * Constructs a new ResourceType with the specified description.
     *
     * @param description A detailed description of the resource type
     */
    ResourceType(String description) {
        this.description = description;
    }

    /**
     * Returns the detailed description of the resource type.
     *
     * @return The description of the resource type
     */
    public String getDescription() {
        return description;
    }
}