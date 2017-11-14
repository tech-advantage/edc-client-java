/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal;

import fr.techad.edc.client.DocumentationManager;
import fr.techad.edc.client.EdcClient;
import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class EdcClientImpl implements EdcClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(EdcClientImpl.class);
    private ClientConfiguration clientConfiguration;
    private DocumentationManager documentationManager;
    private UrlUtil urlUtil;

    @Inject
    public EdcClientImpl(ClientConfiguration clientConfiguration, DocumentationManager documentationManager, UrlUtil urlUtil) {
        this.clientConfiguration = clientConfiguration;
        this.documentationManager = documentationManager;
        this.urlUtil = urlUtil;
    }

    @Override
    public String getContextWebHelpUrl(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException {
        LOGGER.debug("Get WebHelp Context item with mainKey: {}, subKey: {}, languageCode:{}", mainKey, subKey, languageCode);
        return getContextWebHelpUrl(mainKey, subKey, 0, languageCode);
    }

    @Override
    public String getContextWebHelpUrl(String mainKey, String subKey, int rank, String languageCode) throws IOException, InvalidUrlException {
        LOGGER.debug("Get WebHelp Context item with mainKey: {}, subKey: {}, languageCode:{}", mainKey, subKey, languageCode);
        String url = null;
        ContextItem context = documentationManager.getContext(mainKey, subKey, languageCode);
        if (context != null && (context.articleSize() > 0 || context.linkSize() > 0)) {
            url = urlUtil.getContextUrl(context.getPublicationId(), mainKey, subKey, languageCode, rank);
        } else {
            url = urlUtil.getHomeUrl();
        }
        LOGGER.debug("Get WebHelp url: {}", url);
        return url;
    }

    @Override
    public String getDocumentationWebHelpUrl(Long id) throws InvalidUrlException {
        String url;
        if (id != null)
            url = urlUtil.getDocumentationUrl(id);
        else
            url = urlUtil.getHomeUrl();
        return url;
    }

    @Override
    public ContextItem getContextItem(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException {
        LOGGER.debug("Get WebHelp Context item with mainKey: {}, subKey: {}, languageCode:{}", mainKey, subKey, languageCode);
        return documentationManager.getContext(mainKey, subKey, languageCode);
    }

    @Override
    public void setServerUrl(String serverUrl) {
        LOGGER.debug("New server url: {}", serverUrl);
        clientConfiguration.setServerUrl(serverUrl);
    }

    @Override
    public void setWebHelpContextUrl(String webHelpContextUrl) throws InvalidUrlException {
        LOGGER.debug("New WebHelp Context: {}", webHelpContextUrl);
        clientConfiguration.setWebHelpContext(webHelpContextUrl);
    }

    @Override
    public void setDocumentationContextUrl(String documentationContextUrl) throws InvalidUrlException {
        LOGGER.debug("New Documentation Context: {}", documentationContextUrl);
        clientConfiguration.setDocumentationContext(documentationContextUrl);
    }

    @Override
    public void forceReload() {
        LOGGER.debug("Force reload");
        documentationManager.forceReload();
    }
}
