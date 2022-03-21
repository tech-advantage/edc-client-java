/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.io;

import com.google.common.base.Enums;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import fr.techad.edc.client.injector.provider.ContextItemProvider;
import fr.techad.edc.client.injector.provider.DocumentationItemProvider;
import fr.techad.edc.client.injector.provider.I18NProvider;
import fr.techad.edc.client.injector.provider.InformationProvider;
import fr.techad.edc.client.internal.http.Error4xxException;
import fr.techad.edc.client.internal.http.HttpClient;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.*;
import fr.techad.edc.client.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static fr.techad.edc.client.model.I18nTranslation.DEFAULT_LANGUAGE_CODE;

/**
 * Implement EdcReader to communicate with a server
 */
public class HttpReaderImpl implements EdcReader {
    /**
     * The default name which contain the context definition
     */
    private static final String MULTI_DOC_FILE = "multi-doc.json";
    private static final String CONTEXT_FILE = "context.json";
    private static final String INFO_FILE = "info.json";

    private static final String POPOVER_I18N_PATH = "i18n/popover/";
    private static final String I18N_FILE_EXTENSION = ".json";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpReaderImpl.class);
    private HttpClient client;
    private ClientConfiguration clientConfiguration;
    private KeyUtil keyUtil;
    private DocumentationItemProvider documentationItemProvider;
    private ContextItemProvider contextItemProvider;
    private InformationProvider informationProvider;
    private I18NProvider i18NProvider;


    @Inject
    public HttpReaderImpl(HttpClient client, ClientConfiguration clientConfiguration, KeyUtil keyUtil,
                          ContextItemProvider contextItemProvider, DocumentationItemProvider documentationItemProvider,
                          InformationProvider informationProvider, I18NProvider i18NProvider) {
        this.client = client;
        this.clientConfiguration = clientConfiguration;
        this.keyUtil = keyUtil;
        this.documentationItemProvider = documentationItemProvider;
        this.contextItemProvider = contextItemProvider;
        this.i18NProvider = i18NProvider;
        this.informationProvider = informationProvider;
    }

    @Override
    public Map<String, Information> readInfo() throws IOException, InvalidUrlException {
        Map<String, Information> information = Maps.newHashMap();
        for (String publicationId : readPublicationIds()) {
            Information info = readInfoFile(publicationId);
            information.put(publicationId, info);
        }
        return information;
    }

    @Override
    public Map<String, ContextItem> readContext() throws IOException, InvalidUrlException {
        Map<String, ContextItem> contexts = Maps.newHashMap();
        for (String publicationId : readPublicationIds()) {
            contexts.putAll(readContext(publicationId));
        }
        return contexts;
    }

    @Override
    public I18NContent readLabel(Set<String> languageCodes) throws IOException, InvalidUrlException {
        I18NContent i18NContent = this.i18NProvider.get();

        if (languageCodes != null) {
            for (String languageCode : languageCodes) {
                readi18NContent(languageCode, i18NContent);
            }

        }
        return i18NContent;
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

    private Information readInfoFile(String publicationId) throws IOException, InvalidUrlException {
        String infoFileUrl = StringUtils.appendIfMissing(this.clientConfiguration.getDocumentationUrl(), "/") + publicationId + "/" + INFO_FILE;
        LOGGER.debug("Reading info.json file from url {}", infoFileUrl);
        String infoContent;
        Information information = informationProvider.get();

        try {
            infoContent = client.get(infoFileUrl);
            JsonElement jsonContent = parseString(infoContent);
            LOGGER.debug("Fetched content from info.json file {}", jsonContent);

            if (jsonContent.isJsonObject()) {
                JsonObject infoSrc = jsonContent.getAsJsonObject();
                String defaultLangCode = infoSrc.get("defaultLanguage") != null ? infoSrc.get("defaultLanguage").getAsString() : DEFAULT_LANGUAGE_CODE.getValue();
                information.setDefaultLanguage(defaultLangCode);
                LOGGER.debug("Setting default Language from info.json : {}", defaultLangCode);

                Set<String> languages = Sets.newHashSet();
                JsonElement presentLanguages = infoSrc.get("languages");
                if (presentLanguages != null) {
                    JsonArray languagesSrc = presentLanguages.getAsJsonArray();
                    languagesSrc.forEach(lang -> {
                        if (lang != null && StringUtils.isNotBlank(lang.getAsString()))
                            languages.add(lang.getAsString());
                    });
                }
                LOGGER.debug("Setting languages from info.json : {}", languages);
                information.setLanguages(languages);
            }
        } catch (Error4xxException e) {
            LOGGER.error("Could not initialize info from info.json for publication id : {}", publicationId, e);
        } finally {
            if (StringUtils.isBlank(information.getDefaultLanguage()))
                information.setDefaultLanguage(DEFAULT_LANGUAGE_CODE.getValue());
            if (information.getLanguages() == null || information.getLanguages().isEmpty())
                information.setLanguages(Sets.newHashSet(DEFAULT_LANGUAGE_CODE.getValue()));
            LOGGER.debug("Created information from info.json : {}", information);
        }

        return information;
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

    private String getLabelUrl(String languageCode) throws InvalidUrlException {
        String relativePath = POPOVER_I18N_PATH + languageCode + I18N_FILE_EXTENSION;
        String labelUrl = StringUtils.appendIfMissing(this.clientConfiguration.getDocumentationUrl(), "/") + relativePath;
        LOGGER.debug("Reading labels for lang {}, url {}, labelUrl {}", languageCode, relativePath, labelUrl);
        return labelUrl;
    }

    private void readi18NContent(String languageCode, I18NContent i18nContent) throws IOException, InvalidUrlException {
        String label;
        String labelUrl = getLabelUrl(languageCode);

        try {
            // Get the file content (.json) for the language
            label = client.get(labelUrl);
            // Decode Json
            JsonElement jsonContent = parseString(label);

            Set<String> i18NKeys = jsonContent.getAsJsonObject().keySet();
            JsonObject jsonObjectType = null;

            for (String key : i18NKeys) {
                jsonObjectType = jsonContent.getAsJsonObject().get(key).getAsJsonObject();
                jsonObjectType.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                            LOGGER.debug("Creating map entry for key: {} and value {}", e.getKey(), e.getValue().getAsString());
                            i18nContent.setMessage(languageCode, key, e.getKey(), e.getValue().getAsString());

                            return e.getValue().getAsString();
                        }));
            }
        } catch (Error4xxException e) {
            LOGGER.error("Could not read the label for the lang {}, err {}", languageCode, e);
        }
    }
}
