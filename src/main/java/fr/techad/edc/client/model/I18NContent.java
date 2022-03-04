package fr.techad.edc.client.model;

import java.util.Map;
import java.util.Set;

public interface I18NContent {

    /**
     * Set content to build key
     *
     * @param language
     * @param type
     * @param key
     * @param value
     */
    void setMessage(String language, String type, String key, String value);

    /**
     * Return the label built with the lang and key
     *
     * @param language
     * @param key
     * @return the label
     */
    String getLabel(String language, String key);

    /**
     * Return the error built with the lang and key
     *
     * @param language
     * @param key
     * @return the error
     */
    String getError(String language, String key);

    /**
     * Set labels
     *
     * @param languageCode
     * @param readLabelsForLang
     */
    void setLabels(String languageCode, Map<String, String> readLabelsForLang);

    /**
     * Read the translated popover labels for the given language codes
     *
     * @return labels
     */
    Map<String, Map<String, String>> getLabels();
}
