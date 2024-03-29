/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal;

import fr.techad.edc.client.DocumentationManager;
import fr.techad.edc.client.EdcClient;
import fr.techad.edc.client.InformationManager;
import fr.techad.edc.client.TranslationManager;
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
    private TranslationManager translationManager;
    private InformationManager informationManager;
    private UrlUtil urlUtil;

    @Inject
    public EdcClientImpl(ClientConfiguration clientConfiguration, DocumentationManager documentationManager,
                         UrlUtil urlUtil, TranslationManager translationManager, InformationManager informationManager) {
        this.clientConfiguration = clientConfiguration;
        this.documentationManager = documentationManager;
        this.translationManager = translationManager;
        this.informationManager = informationManager;
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
        String url;
        ContextItem context = documentationManager.getContext(mainKey, subKey, languageCode, translationManager.getDefaultPublicationLanguages());
        if (context != null && (context.articleSize() > 0 || context.linkSize() > 0)) {
            url = urlUtil.getContextUrl(context.getPublicationId(), mainKey, subKey, languageCode, rank);
        } else {
            url = urlUtil.getHomeUrl();
        }
        LOGGER.debug("Get WebHelp url: {}", url);
        return url;
    }

    @Override
    public String getDocumentationWebHelpUrl(Long id, String languageCode, String srcPublicationId) throws InvalidUrlException {
        String url;
        if (id != null)
            url = urlUtil.getDocumentationUrl(id, languageCode, srcPublicationId);
        else
            url = urlUtil.getHomeUrl();
        return url;
    }

    @Override
    public ContextItem getContextItem(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException {
        LOGGER.debug("Get WebHelp Context item with mainKey: {}, subKey: {}, languageCode:{}", mainKey, subKey, languageCode);
        // Make sure context was previously loaded - including information and translations
        loadContext();
        return documentationManager.getContext(mainKey, subKey, languageCode, translationManager.getDefaultPublicationLanguages());
    }

    @Override
    public String getLabel(String labelKey, String languageCode, String publicationId) throws IOException, InvalidUrlException {
        LOGGER.debug("Getting label for key {}, language code {} and publication id {}", labelKey, languageCode, publicationId);
        // Make sure the whole context was previously loaded
        loadContext();
        return translationManager.getLabel(labelKey, languageCode, publicationId);
    }

    @Override
    public String getError(String errorKey, String languageCode, String publicationId) throws IOException, InvalidUrlException {
        LOGGER.debug("Getting error for key {}, language code {} and publication id {}", errorKey, languageCode, publicationId);
        // Make sure the whole context was previously loaded
        loadContext();
        return translationManager.getError(errorKey, languageCode, publicationId);
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
        informationManager.forceReload();
        translationManager.forceReload();
        documentationManager.forceReload();
    }

    @Override
    public void loadContext() throws IOException, InvalidUrlException {
        LOGGER.debug("Loading of the configuration");
        informationManager.loadInformation();
        translationManager.loadTranslations(informationManager.getPublicationInformation());
        documentationManager.loadContext();
    }
}
