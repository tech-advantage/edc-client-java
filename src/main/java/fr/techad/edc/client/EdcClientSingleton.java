/*
 * TECH ADVANTAGE
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.techad.edc.client.injector.EdcClientModule;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;

/**
 * EdcClientSingleton is the class to use when you want to integrate edc in a software without injection management.
 * This singleton delegate all call to EdcClient.
 */
public class EdcClientSingleton implements EdcClient {

    private static EdcClientSingleton instance = null;
    private EdcClient edcClient;

    /**
     * A private constructor to avoid to instantiate it.
     * Use the method {@link EdcClientSingleton#getInstance()}
     */
    private EdcClientSingleton() {
        super();
    }

    /**
     * Return the unique instance of this utility class
     *
     * @return the instance
     */
    public static EdcClientSingleton getInstance() {
        if (instance == null) {
            instance = new EdcClientSingleton();
            instance.init();
        }
        return instance;
    }

    /**
     * Initialize the instance.
     */
    private void init() {
        Injector injector = Guice.createInjector(new EdcClientModule());
        edcClient = injector.getInstance(EdcClient.class);
    }

    @Override
    public String getContextWebHelpUrl(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException {
        return edcClient.getContextWebHelpUrl(mainKey, subKey, languageCode);
    }

    @Override
    public String getContextWebHelpUrl(String mainKey, String subKey, int rank, String languageCode) throws IOException, InvalidUrlException {
        return edcClient.getContextWebHelpUrl(mainKey, subKey, rank, languageCode);
    }

    @Override
    public String getDocumentationWebHelpUrl(Long id) throws InvalidUrlException {
        return edcClient.getDocumentationWebHelpUrl(id);
    }

    @Override
    public ContextItem getContextItem(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException {
        return edcClient.getContextItem(mainKey, subKey, languageCode);
    }

    @Override
    public String getTranslatedLabel(String key, String languageCode, String publicationId) {
        return edcClient.getTranslatedLabel(key, languageCode, publicationId);
    }

    @Override
    public void setServerUrl(String serverUrl) {
        edcClient.setServerUrl(serverUrl);
    }

    @Override
    public void setWebHelpContextUrl(String contextUrl) throws InvalidUrlException {
        edcClient.setWebHelpContextUrl(contextUrl);
    }

    @Override
    public void setDocumentationContextUrl(String documentationContextUrl) throws InvalidUrlException {
        edcClient.setDocumentationContextUrl(documentationContextUrl);
    }

    @Override
    public void forceReload() {
        edcClient.forceReload();
    }

    @Override
    public void loadContext() throws IOException, InvalidUrlException {
        edcClient.loadContext();
    }
}
