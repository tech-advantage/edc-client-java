/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.model;

/**
 * The configuration for the client. Define the server url and get the full utl.
 */
public interface ClientConfiguration {
    /**
     * Return the server url without context.
     *
     * @return the server url
     */
    String getServerUrl();

    /**
     * Define the server url like http:/localhost:8080/.
     * The url is just the protocol, the host and the port.
     *
     * @param serverUrl the server url to set
     */
    void setServerUrl(String serverUrl);

    /**
     * Return the WebHelp context.
     * <p>
     * This is the context to use the WebHelp application.
     * The default value is "help".
     *
     * @return the WebHelp context
     */
    default String getWebHelpContext() {
        return "help";
    }

    /**
     * Define the WebHelp context.
     *
     * @param webHelpContext the WebHelp context to set.
     * @throws InvalidUrlException if the context is null
     */
    void setWebHelpContext(String webHelpContext) throws InvalidUrlException;

    /**
     * Return the documentation context.
     * <p>
     * This is the context to use to read the documentation (edc export).
     * The default value is doc.
     *
     * @return the documentation context
     */
    default String getDocumentationContext() {
        return "doc";
    }

    /**
     * Define the documentation context.
     *
     * @param documentationContext the documentation context to set
     * @throws InvalidUrlException if the context is null
     */
    void setDocumentationContext(String documentationContext) throws InvalidUrlException;

    /**
     * Generate the WebHelp url bases on server url and its context.
     *
     * @return the WebHelp url
     * @throws InvalidUrlException if the url is malformed
     */
    String getWebHelpUrl() throws InvalidUrlException;

    /**
     * Generate the documentation url based on server url and its context.
     *
     * @return the documentation url
     * @throws InvalidUrlException if the url is malformed
     */
    String getDocumentationUrl() throws InvalidUrlException;
}
