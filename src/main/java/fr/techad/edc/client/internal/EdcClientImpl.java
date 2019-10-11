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
import fr.techad.edc.client.util.TranslationUtil;
import fr.techad.edc.client.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

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
    private TranslationUtil translationUtil;

    @Inject
    public EdcClientImpl(ClientConfiguration clientConfiguration, DocumentationManager documentationManager,
                         UrlUtil urlUtil, TranslationManager translationManager, InformationManager informationManager,
                         TranslationUtil translationUtil) {
        this.clientConfiguration = clientConfiguration;
        this.documentationManager = documentationManager;
        this.translationManager = translationManager;
        this.informationManager = informationManager;
        this.urlUtil = urlUtil;
        this.translationUtil = translationUtil;
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
        ContextItem context = documentationManager.getContext(mainKey, subKey, languageCode);
        if (context == null) {
            context = findDefaultContextItem(mainKey, subKey);
        }
        return context;
    }

    @Override
    public String getTranslatedLabel(String key, String languageCode, String publicationId) {
        String translatedLabel = "";
        try {
            String publicationLanguage = translationUtil.getPublicationLanguage(informationManager.findByPublicationId(publicationId));
            translatedLabel = translationManager.getLabel(key, languageCode, publicationLanguage);

        } catch (IOException e) {
            LOGGER.error("Could not get the translated label for key {}, language code {} and publication id {}", key, languageCode, publicationId, e);
        } catch (InvalidUrlException e) {
            LOGGER.error("Invalid url while getting the translated label for key {}, language code {} and publication id {}", key, languageCode, publicationId, e);
        } finally {
            if (translatedLabel == null) {
                translatedLabel = TranslationConstants.DEFAULT_LABELS.get(key);
            }
        }
        return translatedLabel;
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

    @Override
    public void loadContext() throws IOException, InvalidUrlException {
        LOGGER.debug("Loading of the configuration");
        documentationManager.loadContext();
    }

    private ContextItem findDefaultContextItem(String mainKey, String subKey) throws IOException, InvalidUrlException {
        LOGGER.debug("Finding default context item for mainKey {} subKey {}", mainKey, subKey);
        Map<String, String> defaultLanguages = this.translationUtil.getPublicationDefaultLanguages(this.informationManager.getPublicationInformation());
        return documentationManager.findDefaultContextItem(mainKey, subKey, defaultLanguages);
    }
}
