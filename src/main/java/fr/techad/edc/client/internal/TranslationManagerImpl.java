package fr.techad.edc.client.internal;

import com.google.common.collect.Maps;
import fr.techad.edc.client.TranslationManager;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.TranslationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

public class TranslationManagerImpl implements TranslationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslationManagerImpl.class);

    private EdcReader reader;
    private TranslationUtil translationUtil;

    private Map<String, Map<String, String>> cachedLabels = Maps.newHashMap();

    @Inject
    public TranslationManagerImpl(EdcReader reader, TranslationUtil translationUtil) {
        this.reader = reader;
        this.translationUtil = translationUtil;
    }

    @Override
    public String getLabel(String key, String languageCode, String defaultLanguage) throws IOException, InvalidUrlException {
        LOGGER.debug("Getting label with key {}, for languageCode {} and default language {}", key, languageCode, defaultLanguage);
        Map<String, String> labelForLanguage = this.getLabelsForLanguage(languageCode);
        if (labelForLanguage == null) {
            labelForLanguage = this.getLabelsForLanguage(defaultLanguage);
        }
        return labelForLanguage != null ? labelForLanguage.get(key) : TranslationConstants.DEFAULT_LABELS.get(key);
    }

    private Map<String, String> getLabelsForLanguage(String languageCode) throws IOException, InvalidUrlException {
        Map<String, String> labelsForLanguage = this.cachedLabels.get(languageCode);
        // If not found in cache, fetch it
        if (labelsForLanguage == null) {
            labelsForLanguage = this.reader.readLabels(languageCode);
            if (translationUtil.checkTranslatedLabels(labelsForLanguage)) {
                this.cachedLabels.put(languageCode, labelsForLanguage);
            } else {
                labelsForLanguage = null;
            }
        }
        return labelsForLanguage;
    }
}
