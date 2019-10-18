/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal;

import fr.techad.edc.client.DocumentationManager;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
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
    public ContextItem getContext(String mainKey, String subKey, String languageCode, Map<String, String> defaultLanguages) throws IOException, InvalidUrlException {
        LOGGER.debug("Get Context item with mainkey: {}, subKey: {}, languageCode:{}", mainKey, subKey, languageCode);
        loadContext();
        ContextItem contextItem = contexts.get(keyUtil.getKey(mainKey, subKey, languageCode));
        LOGGER.debug("Context item with mainkey: {}, subKey: {}, languageCode:{}", mainKey, subKey, languageCode);
        if (contextItem == null && StringUtils.isNotBlank(languageCode)) {
            LOGGER.debug("Context item was null, getting from defaultLanguages:{}", defaultLanguages);
            contextItem = this.findDefaultContextItem(mainKey, subKey, defaultLanguages);
        }
        LOGGER.debug("Returning context item {}", contextItem);
        return contextItem;
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

    /**
     * Return the context item in the default language for the given keys.
     * Will find the publication from the key and subKey, identify its default language,
     * and then return the context item corresponding to that language.
     *
     * @param mainKey the mainKey
     * @param subKey the subKey
     * @param defaultLangCodes a map containing the publication id as key and default language code as value
     * @return the context item in the default language of the publication
     */
    private ContextItem findDefaultContextItem(String mainKey, String subKey, Map<String, String> defaultLangCodes) {
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
            defaultContext = contexts.get(keyUtil.getKey(mainKey, subKey, defaultLang));
        }
        return defaultContext;
    }
}
