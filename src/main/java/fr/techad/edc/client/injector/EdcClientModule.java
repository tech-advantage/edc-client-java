/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.injector;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import fr.techad.edc.client.DocumentationManager;
import fr.techad.edc.client.EdcClient;
import fr.techad.edc.client.injector.provider.ContextItemProvider;
import fr.techad.edc.client.injector.provider.DocumentationItemProvider;
import fr.techad.edc.client.internal.DocumentationManagerImpl;
import fr.techad.edc.client.internal.EdcClientImpl;
import fr.techad.edc.client.internal.io.HttpReaderImpl;
import fr.techad.edc.client.internal.model.ClientConfigurationImpl;
import fr.techad.edc.client.internal.util.KeyUtilImpl;
import fr.techad.edc.client.internal.util.UrlUtilImpl;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.DocumentationItem;
import fr.techad.edc.client.util.KeyUtil;
import fr.techad.edc.client.util.UrlUtil;

/**
 * The guice module to configure injection
 */
public class EdcClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EdcClient.class).to(EdcClientImpl.class).in(Scopes.SINGLETON);
        bind(DocumentationManager.class).to(DocumentationManagerImpl.class).in(Scopes.SINGLETON);

        // Utils
        bind(KeyUtil.class).to(KeyUtilImpl.class).in(Scopes.SINGLETON);
        bind(UrlUtil.class).to(UrlUtilImpl.class);

        // io
        bind(EdcReader.class).to(HttpReaderImpl.class);

        // Model
        bind(ClientConfiguration.class).to(ClientConfigurationImpl.class).in(Scopes.SINGLETON);
        bind(ContextItem.class).toProvider(ContextItemProvider.class);
        bind(DocumentationItem.class).toProvider(DocumentationItemProvider.class);

    }
}
