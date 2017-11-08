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
     * @param publicationId the identifier of the publication
     * @param mainKey       the main key
     * @param subKey        the sub key
     * @param languageCode  the language code
     * @param articleIndex  the article index to display
     * @return the url
     * @throws InvalidUrlException IF the url is malformed
     */
    String getContextUrl(String publicationId, String mainKey, String subKey, String languageCode, int articleIndex) throws InvalidUrlException;


    /**
     * Build the web help documentation url for the document defined by the identifier
     *
     * @param id the idenitifer of the documentation
     * @return the url
     */
    String getDocumentationUrl(Long id) throws InvalidUrlException;
}
