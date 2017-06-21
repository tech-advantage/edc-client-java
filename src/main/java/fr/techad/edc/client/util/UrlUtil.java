/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.util;

import fr.techad.edc.client.model.InvalidUrlException;

/**
 * This class generate the url according to the context.
 */
public interface UrlUtil {

    /**
     * Build the web help context url for the brick defined with the keys, the language and the index of the article to display
     *
     * @param mainKey      the main key
     * @param subKey       the sub key
     * @param languageCode the language code
     * @param articleIndex the article index to display
     * @return the url
     * @throws InvalidUrlException IF the url is malformed
     */
    String getContextUrl(String mainKey, String subKey, String languageCode, int articleIndex) throws InvalidUrlException;
}
