/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal;

import fr.techad.edc.client.DocumentationManager;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.KeyUtil;
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
public class DocumentationManagerImpl implements DocumentationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentationManagerImpl.class);
    private EdcReader reader;
    private KeyUtil keyUtil;

    private Map<String, ContextItem> contexts;

    @Inject
    public DocumentationManagerImpl(EdcReader reader, KeyUtil keyBuilder) {
        this.reader = reader;
        this.keyUtil = keyBuilder;
    }

    @Override
    public ContextItem getContext(String mainKey, String subKey, String languageCode) throws IOException, InvalidUrlException {
        LOGGER.debug("Get Context item with mainkey: {}, subKey: {}, languageCode:{}", mainKey,subKey,languageCode);
        if (contexts == null) {
            LOGGER.debug("No contexts defined, read it");
            contexts = reader.readContext();
        }
        return contexts.get(keyUtil.getKey(mainKey,subKey, languageCode));
    }

    @Override
    public void forceReload() {
        LOGGER.debug("Force reload on next call");
        contexts=null;
    }
}
