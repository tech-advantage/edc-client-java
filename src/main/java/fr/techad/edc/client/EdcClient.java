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
     * If languageCode is not defined or not found, system default will be used instead
     * Url will include the identifier of publication from where the navigation started, if present
     *
     * @param id the identifier of the documentation
     * @param languageCode the 2 letters code of the language to use
     * @param srcPublicationId the identifier of the publication where the navigation starts from
     * @return the url
     * @throws InvalidUrlException If the url is malformed
     */
    String getDocumentationWebHelpUrl(Long id, String languageCode, String srcPublicationId) throws InvalidUrlException;

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
     * Return the label translation for the given key
     * Will read the translated labels from the the i18n files present in the documentation export
     * (by default in folder doc/i18n/popover/*.json)
     *
     * If label was not found in the requested language, it will try and read in the publication default language
     * (as defined in the info.json file),
     * or in global default labels as a final fallback
     *
     * @param labelKey the label translation key
     * @param languageCode the 2 letters language code
     * @param publicationId default language, to use if content was not found in requested language
     * @return the translated label
     * @throws IOException         if an error occurred when reading the files
     * @throws InvalidUrlException if the url is malformed
     */
    String getLabel(String labelKey, String languageCode, String publicationId) throws IOException, InvalidUrlException;

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
     * Force the manager to reload the documentation definition, publication information and translations.
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
