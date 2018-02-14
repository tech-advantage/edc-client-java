/*
 * TECH ADVANTAGE
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client;

import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;

/**
 * EdcClient is the utility class to get all information about documentation.
 */
public interface EdcClient {

    /**
     * Create the url for the context according to the main key, the subkey and the language code.
     * <p>
     * The language code is 2 digits in lowercase ie fr, en, ...
     *
     * @param mainKey      the main key
     * @param subKey       the sub key
     * @param languageCode the language code
     * @return the url
     * @throws IOException         if an error is occurred on reading
     * @throws InvalidUrlException if the url is malformed
     */
    String getContextWebHelpUrl(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException;

    /**
     * Create the url for the context according to the main key, the subkey and the language code.
     * <p>
     * The language code is 2 digits in lowercase ie fr, en, ...
     *
     * @param mainKey      the main key
     * @param subKey       the sub key
     * @param rank         the rank
     * @param languageCode the language code
     * @return the url
     * @throws IOException         if an error is occurred on reading
     * @throws InvalidUrlException if the url is malformed
     */
    String getContextWebHelpUrl(String mainKey, String subKey, int rank, String languageCode) throws IOException, InvalidUrlException;

    /**
     * Create the url for the documentation.
     * <p>
     * The language code is 2 digits in lowercase ie fr, en, ...
     *
     * @param id the identifier of the documentation
     * @return the url
     * @throws InvalidUrlException If the url is malformed
     */
    String getDocumentationWebHelpUrl(Long id) throws InvalidUrlException;

    /**
     * Return the context item associated with main and sub keys and the language code.
     *
     * @param mainKey      the main key
     * @param subKey       the sub bey
     * @param languageCode the language code
     * @return the context item
     * @throws IOException         if an error is occurred on reading
     * @throws InvalidUrlException if the url is malformed
     */
    ContextItem getContextItem(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException;

    /**
     * Define the server url like http://localhost:8080
     *
     * @param serverUrl the server url
     */
    void setServerUrl(String serverUrl);

    /**
     * Define the context url ie like doc in http://localhost:8080/doc
     * The default value is 'doc'. Do nothing if you don't change the default behavior.
     *
     * @param documentationContextUrl the documentation context url
     * @throws InvalidUrlException if the url is malformed
     */
    void setDocumentationContextUrl(String documentationContextUrl) throws InvalidUrlException;

    /**
     * Define the context url ie like help in http://localhost:8080/help
     * The default value is 'help'. Do nothing if you don't change the default behavior.
     *
     * @param webHelpContextUrl the web help context url
     * @throws InvalidUrlException if the url is malformed
     */
    void setWebHelpContextUrl(String webHelpContextUrl) throws InvalidUrlException;

    /**
     * Force the manager to reload the documentation definition.
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
