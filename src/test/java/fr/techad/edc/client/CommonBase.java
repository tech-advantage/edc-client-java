/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client;

import fr.techad.edc.client.injector.provider.ContextItemProvider;
import fr.techad.edc.client.injector.provider.DocumentationItemProvider;
import fr.techad.edc.client.internal.Constants;
import fr.techad.edc.client.internal.DocumentationManagerImpl;
import fr.techad.edc.client.internal.EdcClientImpl;
import fr.techad.edc.client.internal.http.HttpClient;
import fr.techad.edc.client.internal.io.HttpReaderImpl;
import fr.techad.edc.client.internal.util.KeyUtilImpl;
import fr.techad.edc.client.internal.util.UrlUtilImpl;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.internal.model.ClientConfigurationImpl;
import fr.techad.edc.client.util.KeyUtil;
import fr.techad.edc.client.util.UrlUtil;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public abstract class CommonBase {
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
        HttpClient httpClient = createHttpClient();
        ClientConfiguration clientConfiguration = createClientConfiguration();
        KeyUtil keyUtil = createKeyBuilder();
        ContextItemProvider contextItemProvider = new ContextItemProvider();

        DocumentationItemProvider documentationItemProvider = new DocumentationItemProvider();
        return new HttpReaderImpl(httpClient, clientConfiguration, keyUtil, contextItemProvider, documentationItemProvider);

    }

    protected UrlUtil createUrlBuilder() {
        return new UrlUtilImpl(createClientConfiguration());
    }

    protected DocumentationManager createDocumentationManager() {
        return new DocumentationManagerImpl(createEdcReader(), createKeyBuilder());
    }

    protected EdcClient createEdcClient() {
        return new EdcClientImpl(createClientConfiguration(), createDocumentationManager(), createUrlBuilder());
    }
}
