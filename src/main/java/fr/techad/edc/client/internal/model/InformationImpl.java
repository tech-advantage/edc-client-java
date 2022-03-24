package fr.techad.edc.client.internal.model;

import fr.techad.edc.client.internal.TranslationConstants;
import fr.techad.edc.client.model.Information;
import static fr.techad.edc.client.model.I18nTranslation.DEFAULT_LANGUAGE_CODE;

import java.util.Set;

public class InformationImpl implements Information {
    private String defaultLanguage = DEFAULT_LANGUAGE_CODE.getValue();
    private Set<String> languages;

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }
}
