package fr.techad.edc.client.internal;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import fr.techad.edc.client.TranslationManager;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.TranslationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class TranslationManagerImpl implements TranslationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationManagerImpl.class);

    private EdcReader reader;
    private TranslationUtil translationUtil;

    private Map<String, Map<String, String>> labels = Maps.newHashMap();
    // The languages codes present among all the publications
    private Set<String> languageCodes = Sets.newHashSet();
    // The default language code for each publication id
    private Map<String, String> defaultPublicationLanguages = Maps.newHashMap();

    @Inject
    public TranslationManagerImpl(EdcReader reader, TranslationUtil translationUtil) {
        this.reader = reader;
        this.translationUtil = translationUtil;
    }

    @Override
    public void loadTranslations(Map<String, Information> publicationInformation) throws IOException, InvalidUrlException {
        if (this.labels.isEmpty() && publicationInformation != null) {
            for (Map.Entry<String, Information> entry : publicationInformation.entrySet()) {
                Information information = entry.getValue();
                this.addToDefaultLanguages(entry.getKey(), information);
                this.addToLanguages(information);
            }
            this.labels.putAll(this.reader.readLabels(this.languageCodes));
        }
    }

    @Override
    public void forceReload() {
        this.labels.clear();
        this.languageCodes.clear();
        this.defaultPublicationLanguages.clear();
    }

    @Override
    public String getLabel(String labelKey, String languageCode, String publicationId) {
        LOGGER.debug("Getting label with key {}, for languageCode {} and publicationId {}", labelKey, languageCode, publicationId);
        Map<String, String> labelForLanguage = this.labels.get(languageCode);
        if (labelForLanguage == null) {
            String defaultLanguage = this.defaultPublicationLanguages.get(publicationId);
            // If default language is not found, use default labels
            labelForLanguage = this.labels.get(defaultLanguage) != null ? this.labels.get(defaultLanguage) : TranslationConstants.DEFAULT_LABELS;
        }
        // If no label values for the label key, return the default label for this key
        return StringUtils.isNotBlank(labelForLanguage.get(labelKey)) ? labelForLanguage.get(labelKey) : TranslationConstants.DEFAULT_LABELS.get(labelKey);
    }

    @Override
    public Map<String, String> getDefaultPublicationLanguages() {
        return Collections.unmodifiableMap(this.defaultPublicationLanguages);
    }

    private void addToDefaultLanguages(String publicationId, Information information) {
        if (information != null && translationUtil.isLanguageCodeValid(information.getDefaultLanguage())) {
            this.defaultPublicationLanguages.put(publicationId, information.getDefaultLanguage());
        }
    }

    private void addToLanguages(Information information) {
        if (information != null && information.getLanguages() != null) {
            for (String languageCode : information.getLanguages()) {
                if (translationUtil.isLanguageCodeValid(languageCode))
                    this.languageCodes.add(languageCode);
            }
        }
    }
}
