package model;

/**
 * Concrete implementation of the Item interface.
 * Represents standard game items loaded from external configuration files.
 */
public class ItemImpl implements Item {
    // Declared as final to enforce immutability after instantiation
    private final String name;
    private final String description;

    public ItemImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() { 
        return name; 
    }

    @Override
    public String getDescription() { 
        return description; 
    }
}