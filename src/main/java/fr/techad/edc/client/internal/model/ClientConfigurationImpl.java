/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.model;

import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.model.InvalidUrlException;
import org.apache.commons.lang3.StringUtils;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public class ClientConfigurationImpl implements ClientConfiguration {
    private String serverUrl;
    private String webHelpContext = "help";
    private String documentationContext = "doc";


    @Override
    public String getServerUrl() {
        return serverUrl;
    }

    @Override
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public String getWebHelpContext() {
        return webHelpContext;
    }

    @Override
    public void setWebHelpContext(String webHelpContext) throws InvalidUrlException {
        if (webHelpContext == null)
            throw new InvalidUrlException("The WebHelp context is null");
        this.webHelpContext = webHelpContext;
    }

    @Override
    public String getDocumentationContext() {
        return documentationContext;
    }

    @Override
    public void setDocumentationContext(String documentationContext) throws InvalidUrlException {
        if (documentationContext == null)
            throw new InvalidUrlException("The documentation context is null");
        this.documentationContext = documentationContext;
    }

    @Override
    public String getWebHelpUrl() throws InvalidUrlException {
        if (StringUtils.isBlank(serverUrl))
            throw new InvalidUrlException("The server url is not defined");
        return StringUtils.appendIfMissing(serverUrl, "/") + webHelpContext;
    }

    @Override
    public String getDocumentationUrl() throws InvalidUrlException {
        if (StringUtils.isBlank(serverUrl))
            throw new InvalidUrlException("The server url is not defined");
        return StringUtils.appendIfMissing(serverUrl, "/") + documentationContext;
    }
}
