/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client;

import fr.techad.edc.client.injector.provider.ContextItemProvider;
import fr.techad.edc.client.injector.provider.DocumentationItemProvider;
import fr.techad.edc.client.injector.provider.I18NProvider;
import fr.techad.edc.client.injector.provider.InformationProvider;
import fr.techad.edc.client.internal.Constants;
import fr.techad.edc.client.internal.DocumentationManagerImpl;
import fr.techad.edc.client.internal.EdcClientImpl;
import fr.techad.edc.client.internal.InformationManagerImpl;
import fr.techad.edc.client.internal.TranslationManagerImpl;
import fr.techad.edc.client.internal.http.HttpClient;
import fr.techad.edc.client.internal.io.HttpReaderImpl;
import fr.techad.edc.client.internal.util.KeyUtilImpl;
import fr.techad.edc.client.internal.util.TranslationUtilImpl;
import fr.techad.edc.client.internal.util.UrlUtilImpl;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.internal.model.ClientConfigurationImpl;
import fr.techad.edc.client.util.KeyUtil;
import fr.techad.edc.client.util.TranslationUtil;
import fr.techad.edc.client.util.UrlUtil;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public abstract class CommonBase {

    private EdcReader edcReader;

    protected HttpClient createHttpClient() {
        return new HttpClient();
    }


    protected ClientConfiguration createClientConfiguration() {
        ClientConfiguration clientConfiguration = new ClientConfigurationImpl();
        clientConfiguration.setServerUrl(Constants.SERVER_URL);
        return clientConfiguration;
    }

    protected KeyUtil createKeyBuilder() {
        return new KeyUtilImpl();
    }

    protected EdcReader createEdcReader() {
        if (this.edcReader != null) {
            return this.edcReader;
        }
        HttpClient httpClient = createHttpClient();
        ClientConfiguration clientConfiguration = createClientConfiguration();
        KeyUtil keyUtil = createKeyBuilder();
        ContextItemProvider contextItemProvider = new ContextItemProvider();
        DocumentationItemProvider documentationItemProvider = new DocumentationItemProvider();
        InformationProvider informationProvider = new InformationProvider();
        I18NProvider i18nProvider = new I18NProvider();
        this.edcReader = new HttpReaderImpl(httpClient, clientConfiguration, keyUtil, contextItemProvider, documentationItemProvider, informationProvider, i18nProvider);
        return this.edcReader;
    }

    protected UrlUtil createUrlBuilder() {
        return new UrlUtilImpl(createClientConfiguration());
    }

    protected TranslationUtil createTranslationUtil() {
        return new TranslationUtilImpl();
    }

    protected DocumentationManager createDocumentationManager() {
        return new DocumentationManagerImpl(createEdcReader(), createKeyBuilder());
    }

    protected InformationManager createInformationManager() {
        return new InformationManagerImpl(createEdcReader());
    }

    protected TranslationManager createTranslationManager() {
        return new TranslationManagerImpl(createEdcReader(), createTranslationUtil());
    }

    protected EdcClient createEdcClient() {
        return new EdcClientImpl(createClientConfiguration(), createDocumentationManager(), createUrlBuilder(), createTranslationManager(), createInformationManager());
    }
}
