package fr.techad.edc.client.internal.util;

import fr.techad.edc.client.internal.TranslationConstants;
import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.util.TranslationUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

import static fr.techad.edc.client.model.I18nTranslation.DEFAULT_LANGUAGE_CODE;

public class TranslationUtilImpl implements TranslationUtil {

    @Override
    public Map<String, String> getPublicationDefaultLanguages(Map<String, Information> information) {
        return information.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> getDefaultLanguage(e.getValue())));
    }

    @Override
    public boolean checkTranslatedLabels(Map<String, String> labels) {
        return labels != null && !labels.isEmpty() &&
                TranslationConstants.DEFAULT_LABELS.entrySet().stream()
                        .allMatch(entry -> labels.containsKey(entry.getKey())) &&
                labels.values().stream().allMatch(l -> StringUtils.isNotBlank(l));
    }

    @Override
    public boolean isLanguageCodeValid(String languageCode) {
        return TranslationConstants.LANGUAGE_CODES.contains(languageCode);
    }

    private String getDefaultLanguage(Information info) {
        return (info != null && StringUtils.isNotBlank(info.getDefaultLanguage())) ?
                info.getDefaultLanguage() : DEFAULT_LANGUAGE_CODE.getValue();
    }

}
