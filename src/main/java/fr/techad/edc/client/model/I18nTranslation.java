package fr.techad.edc.client.model;

public enum I18nTranslation {

    DEFAULT_LANGUAGE_CODE("en"),
    I18N_LABELS_ROOT("labels"),
    I18N_ERRORS_ROOT("errors"),
    ARTICLES_KEY("articles"),
    LINKS_KEY("links"),
    COMING_SOON_KEY("comingSoon"),
    ERROR_TITLE_KEY("errorTitle"),
    ERRORS_KEY("failedData");

    private String value;

    public String getValue() {
        return value;
    }

    I18nTranslation(String value){
        this.value = value;
    }
}
