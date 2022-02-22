package fr.techad.edc.client.util;

import java.util.Map;

public interface I18nContent {


    /**
     * Return labels content from json file
     *
     * @return labels content
     */
    Map<String, String> getLabel();

    /**
     * Set the labels content from json file
     *
     * @param i18nLabel
     */
    void setI18nLabel(Map<String, String> i18nLabel);

    /**
     * Return errors content from json file
     *
     * @return errors content
     */
    Map<String, String> getError();

    /**
     * Set the errors content from json file
     *
     * @param i18nError
     */
    void setI18nError(Map<String, String> i18nError);
}
