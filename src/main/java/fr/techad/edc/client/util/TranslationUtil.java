package fr.techad.edc.client.util;

import fr.techad.edc.client.model.Information;

import java.util.Map;

public interface TranslationUtil {

    /**
     * Return a map of default languages by publication id
     *
     * @param information the map containing the publication id as key and information as value
     * @return a map of publication id as key and default content language code as value
     */
    Map<String, String> getPublicationDefaultLanguages(Map<String, Information> information);

    /**
     * Return the publication default language code from the information
     *
     * @param info the information containing the default language code
     * @return the default language code if found
     */
    String getPublicationLanguage(Information info);

    /**
     * Check labels consistency
     * Labels must contain all the keys present in default language labels
     * And their values must not be empty
     *
     * @param labels the labels to check
     * @return true if labels are valid
     */
    boolean checkTranslatedLabels(Map<String, String> labels);
}
