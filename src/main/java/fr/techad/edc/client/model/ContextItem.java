/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.model;

/**
 * This class is a specialized DocumentationItem for the contextual content (bricks)
 */
public interface ContextItem extends DocumentationItem {
    /**
     * Return the description
     *
     * @return the description
     */
    String getDescription();

    /**
     * Define the description
     *
     * @param description the description to set
     */
    void setDescription(String description);

    /**
     * Return the main key ie pluginId, package, other, ...
     *
     * @return the main key
     */
    String getMainKey();

    /**
     * Define the main key
     *
     * @param mainKey the main key to set
     */
    void setMainKey(String mainKey);

    /**
     * Return the secondary key (id of the brick)
     *
     * @return the secondary key
     */
    String getSubKey();

    /**
     * Define the secondary key
     *
     * @param subKey the secondary key to set
     */
    void setSubKey(String subKey);
}