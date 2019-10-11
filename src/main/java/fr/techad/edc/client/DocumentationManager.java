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
     * Return the default language context item for the given keys,
     * Based on the publication default language
     *
     * @param mainKey the mainKey
     * @param subKey the subKey
     * @param defaultLangCodes a map containing the publication id as key and default language code as value
     * @return the context item in the default language of the publication
     */
    ContextItem findDefaultContextItem(String mainKey, String subKey, Map<String, String> defaultLangCodes);

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
