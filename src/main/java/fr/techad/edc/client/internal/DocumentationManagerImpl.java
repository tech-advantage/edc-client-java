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
import java.util.Set;
import java.util.stream.Collectors;

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
        LOGGER.debug("Get Context item with mainkey: {}, subKey: {}, languageCode:{}", mainKey, subKey, languageCode);
        loadContext();
        return contexts.get(keyUtil.getKey(mainKey, subKey, languageCode));
    }

    @Override
    public void forceReload() {
        LOGGER.debug("Force reload on next call");
        contexts = null;
    }

    @Override
    public void loadContext() throws IOException, InvalidUrlException {
        if (contexts == null) {
            LOGGER.debug("No contexts defined, read it");
            contexts = reader.readContext();
        }
    }

    @Override
    public ContextItem findDefaultContextItem(String mainKey, String subKey, Map<String, String> defaultLangCodes) {
        ContextItem defaultContext = null;
        // Find the item corresponding to the mainKey and subKey
        Set<ContextItem> presentItems = contexts.entrySet().stream()
                .filter(e -> keyUtil.containsKey(e.getKey(), mainKey, subKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
        if (!presentItems.isEmpty()) {
            String exportId = presentItems.stream().map(ContextItem::getPublicationId).findFirst().orElse("");
            String defaultLang = defaultLangCodes.get(exportId);
            // Find the context item in the default language
            defaultContext = presentItems.stream()
                    .filter(c -> defaultLang.equals(c.getLanguageCode()))
                    .findFirst()
                    .orElse(null);
        }
        return defaultContext;
    }
}
