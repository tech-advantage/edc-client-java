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
     * Return the home url for the help client
     *
     * @return the home url
     * @throws InvalidUrlException If the base url is not defined
     */
    String getHomeUrl() throws InvalidUrlException;

    /**
     * Return the error url for the help client
     *
     * @return the home url
     * @throws InvalidUrlException If the base url is not defined
     */
    String getErrorUrl() throws InvalidUrlException;

    /**
     * Build the web help context url for the brick defined with the keys, the language and the index of the article to display
     *
     * @param publicationId the identifier of the publication
     * @param mainKey       the main key
     * @param subKey        the sub key
     * @param languageCode  the language code
     * @param articleIndex  the article index to display
     * @return the url
     * @throws InvalidUrlException If the url is malformed
     */
    String getContextUrl(String publicationId, String mainKey, String subKey, String languageCode, int articleIndex) throws InvalidUrlException;


    /**
     * Build the web help documentation url for the document defined by the identifier, the language code,
     * and the publication identifier if present
     *
     * @param id the idenitifer of the documentation
     * @param languageCode the 2 letters code of the language (en, fr..)
     * @param srcPublicationId the identifier of the publication from where navigation will start
     * @return the url
     * @throws InvalidUrlException If the url is malformed
     */
    String getDocumentationUrl(Long id, String languageCode, String srcPublicationId) throws InvalidUrlException;
}
