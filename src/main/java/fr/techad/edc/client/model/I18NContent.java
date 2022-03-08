package fr.techad.edc.client.model;

public interface I18NContent {

    /**
     * Return the I18n Translation built with the lang and key
     *
     * @param lang
     * @param type
     * @param key
     * @param publicationId
     * @return translation
     */
    String getTranslation(String lang, String type, String key, String publicationId);

    /**
     * Set content to build key
     *
     * @param language
     * @param type
     * @param key
     * @param value
     */
    void setMessage(String language, String type, String key, String value);

}
