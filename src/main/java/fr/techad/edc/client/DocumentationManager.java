/*
 * TECH ADVANTAGE
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client;

import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;
import java.util.Map;

/**
 * DocumentationManager manage all content of the documentation.
 */
public interface DocumentationManager {

    /**
     * Get the context item according to the keys and the language or the default language if not found.
     * If the context was not found in the requested language, it will find the publication from the given keys
     * and use the default language, identified from the the default languages map
     *
     * @param mainKey      the main key
     * @param subKey       the sub key
     * @param languageCode the language code
     * @param defaultLanguages a map containing the publication id as key and default language code as value
     * @return the context item or null
     * @throws IOException         if an is occurred on reading information
     * @throws InvalidUrlException if the url is malformed.
     */
    ContextItem getContext(String mainKey, String subKey, String languageCode, Map<String, String> defaultLanguages) throws IOException, InvalidUrlException;

    /**
     * Force the reload of the documentation definition on the next call.
     */
    void forceReload();

    /**
     * Force the documentation loading. This call is optional. Don't call if you want to use the lazy loading.
     *
     * @throws IOException         if an is occurred on reading information
     * @throws InvalidUrlException if the url is malformed.
     */
    void loadContext() throws IOException, InvalidUrlException;
}
