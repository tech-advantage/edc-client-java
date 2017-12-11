/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.io;

import com.google.common.base.Enums;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import fr.techad.edc.client.injector.provider.ContextItemProvider;
import fr.techad.edc.client.injector.provider.DocumentationItemProvider;
import fr.techad.edc.client.internal.http.Error4xxException;
import fr.techad.edc.client.internal.http.HttpClient;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.DocumentationItem;
import fr.techad.edc.client.model.DocumentationItemType;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Implement EdcReader to communicate with a server
 */
public class HttpReaderImpl implements EdcReader {
    /**
     * The default name which contain the context definition
     */
    private static final String MULTI_DOC_FILE = "multi-doc.json";
    private static final String CONTEXT_FILE = "context.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpReaderImpl.class);
    private HttpClient client;
    private ClientConfiguration clientConfiguration;
    private KeyUtil keyUtil;
    private DocumentationItemProvider documentationItemProvider;
    private ContextItemProvider contextItemProvider;

    @Inject
    public HttpReaderImpl(HttpClient client, ClientConfiguration clientConfiguration, KeyUtil keyUtil, ContextItemProvider contextItemProvider,
                          DocumentationItemProvider documentationItemProvider) {
        this.client = client;
        this.clientConfiguration = clientConfiguration;
        this.keyUtil = keyUtil;
        this.documentationItemProvider = documentationItemProvider;
        this.contextItemProvider = contextItemProvider;
    }

    @Override
    public Map<String, ContextItem> readContext() throws IOException, InvalidUrlException {
        Map<String, ContextItem> contexts = Maps.newHashMap();
        for (String publicationId : readPublicationIds()) {
            contexts.putAll(readContext(publicationId));
        }
        return contexts;
    }

    private Set<String> readPublicationIds() throws InvalidUrlException, IOException {
        Set<String> publicationIds = Sets.newHashSet();
        String multiDocUrl = StringUtils.appendIfMissing(this.clientConfiguration.getDocumentationUrl(), "/") + MULTI_DOC_FILE;
        LOGGER.info("Context url: {}", multiDocUrl);
        String content = null;
        try {
            content = client.get(multiDocUrl);
        } catch (Error4xxException e) {
            throw new IOException(e.getMessage());
        }
        // Decode Json
        JsonElement jsonContent = parseString(content);
        if (jsonContent.isJsonArray()) {
            JsonArray jsonArray = jsonContent.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    publicationIds.add(jsonObject.get("pluginId").getAsString());
                }
            }
        }
        return publicationIds;
    }

    private Map<String, ContextItem> readContext(String publicationId) throws IOException, InvalidUrlException {
        String urlContext = StringUtils.appendIfMissing(this.clientConfiguration.getDocumentationUrl(), "/") + publicationId + "/" + CONTEXT_FILE;
        LOGGER.info("Context url: {}", urlContext);
        Map<String, ContextItem> contexts = Maps.newHashMap();
        try {
            String content = client.get(urlContext);
            // Decode Json
            JsonElement jsonContent = parseString(content);
            if (jsonContent.isJsonObject()) {
                JsonObject jsonObject = jsonContent.getAsJsonObject();
                LOGGER.debug("jsonObject: {}", jsonObject);
                Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
                entries.forEach(e -> parseContext(contexts, publicationId, e.getKey(), e.getValue()));
            }
        } catch (Error4xxException e) {
            // For multi product, some product should be undefined, ignore it in this case.
            LOGGER.warn("No context found, the product was not published", e);
        }
        return contexts;
    }

    private JsonElement parseString(String text) throws IOException {
        JsonElement jsonContent;
        try {
            JsonParser jsonParser = new JsonParser();
             jsonContent = jsonParser.parse(text);
        } catch (JsonSyntaxException e) {
            LOGGER.error("Context is not json: {}", text);
            throw new IOException("The response of server is unknown format, wait json response.");
        }
        return jsonContent;
    }

    private void parseContext(Map<String, ContextItem> contexts, String publicationId, String mainKey, JsonElement jsonElement) {
        LOGGER.debug("Decode for main key: {}", mainKey);
        Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
        entries.forEach(e -> parseContext(contexts, publicationId, mainKey, e.getKey(), e.getValue()));
    }

    private void parseContext(Map<String, ContextItem> contexts, String publicationId, String mainKey, String subKey, JsonElement jsonElement) {
        LOGGER.debug("Decode for sub key: {}", subKey);
        Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
        entries.forEach(e -> createContext(contexts, publicationId, mainKey, subKey, e.getKey(), e.getValue()));
    }

    private void createContext(Map<String, ContextItem> contexts, String publicationId, String mainKey, String subKey, String languageCode, JsonElement jsonElement) {
        LOGGER.debug("Decode for language code: {}", languageCode);
        JsonObject jsonCtxt = jsonElement.getAsJsonObject();
        String description = jsonCtxt.get("description").getAsString();

        ContextItem contextItem = contextItemProvider.get();
        contextItem.setLabel(getLabel(jsonCtxt));
        contextItem.setLanguageCode(languageCode);
        contextItem.setUrl(getUrl(jsonCtxt));
        contextItem.setPublicationId(publicationId);
        contextItem.setDescription(description);
        contextItem.setMainKey(mainKey);
        contextItem.setSubKey(subKey);

        createArticles(contextItem, jsonCtxt.get("articles").getAsJsonArray(), languageCode);
        createLinks(contextItem, jsonCtxt.get("links").getAsJsonArray(), languageCode);
        contexts.put(keyUtil.getKey(mainKey, subKey, languageCode), contextItem);
    }

    private void createArticles(DocumentationItem documentationItem, JsonArray articles, String languageCode) {
        articles.forEach(jsonElement -> {
            JsonObject articleJson = jsonElement.getAsJsonObject();
            LOGGER.debug("Article to decode: {}", articleJson);
            DocumentationItem article = documentationItemProvider.get();
            article.setDocumentationItemType(DocumentationItemType.ARTICLE);
            article.setLanguageCode(languageCode);
            article.setId(getId(articleJson));
            article.setLabel(getLabel(articleJson));
            article.setUrl(getUrl(articleJson));
            LOGGER.debug("new article: {}", article);
            documentationItem.addArticle(article);
        });
    }

    private void createLinks(DocumentationItem documentationItem, JsonArray links, String languageCode) {
        links.forEach(jsonElement -> {
            JsonObject linkJson = jsonElement.getAsJsonObject();
            LOGGER.debug("link to decode: {}", linkJson);
            DocumentationItem link = documentationItemProvider.get();
            link.setDocumentationItemType(getType(linkJson));
            link.setLanguageCode(languageCode);
            link.setId(getId(linkJson));
            link.setLabel(getLabel(linkJson));
            link.setUrl(getUrl(linkJson));
            LOGGER.debug("new link: {}", link);
            documentationItem.addLink(link);
        });
    }

    private String getLabel(JsonObject jsonObject) {
        return jsonObject.get("label").getAsString();
    }

    private String getUrl(JsonObject jsonObject) {
        return jsonObject.get("url").getAsString();
    }

    private Long getId(JsonObject jsonObject) {
        return jsonObject.get("id").getAsLong();
    }

    private DocumentationItemType getType(JsonObject jsonObject) {
        return Enums.getIfPresent(DocumentationItemType.class, jsonObject.get("type").getAsString())
                .or(DocumentationItemType.UNKNOWN);

    }


}
