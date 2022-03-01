package fr.techad.edc.client.model;

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

}
