package fr.techad.edc.client.model;

public enum I18nTranslation {

    I18N_LABELS_ROOT("labels"),
    I18N_ERRORS_ROOT("errors");

    private String value;

    public String getValue() {
        return value;
    }

    I18nTranslation(String value){
        this.value = value;
    }
}
