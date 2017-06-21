/*
 * TECH ADVANTAGE
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client;

import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;

/**
 * DocumentationManager manage all content of the documentation.
 */
public interface DocumentationManager {

    /**
     * Get the context item according to the keys and the language. If the context was not read, it will read.
     *
     * @param mainKey      the main key
     * @param subKey       the sub key
     * @param languageCode the language code
     * @return the context item or null
     * @throws IOException         if an is occurred on reading information
     * @throws InvalidUrlException if the url is malformed.
     */
    ContextItem getContext(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException;

    /**
     * Force the reload of the documentation definition on the next call.
     */
    void forceReload();

}
