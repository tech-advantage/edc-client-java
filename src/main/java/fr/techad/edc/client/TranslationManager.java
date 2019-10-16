package fr.techad.edc.client;

import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;
import java.util.Map;

public interface TranslationManager {

    /**
     * Load the translation information and the translation popover labels
     *
     * Information :  - load the languages used by all the publications
     *                - load the map of default languages by publication
     * Labels: the labels used by the popover (articles, links),
     * These labels are read from the i18n files present in the documentation root folder
     *
     * @param publicationInformation a map containing all the publication information for every publication
     * @throws IOException if an IO error occurred
     * @throws InvalidUrlException if the i18n files urls were not valid
     */
    void loadTranslations(Map<String, Information> publicationInformation) throws IOException, InvalidUrlException;

    /**
     * Force the reload of the translation information and labels on the next read
     *
     */
    void forceReload();

    /**
     * Return the translated popover label for the requested key
     * If no label was found for the language, will look for the label in the publication default language
     * Finally, system default labels will be used if no valid label was found in default language
     *
     * @param labelKey the label key
     * @param languageCode the requested language
     * @param publicationId the identifier of the publication, to find the default language
     * @return A string containing the translated label
     */
    String getLabel(String labelKey, String languageCode, String publicationId);

    /**
     * Return a Map with the default language for each publication
     *
     * @return a Map containing the publication id as key, default language code as value
     */
    Map<String, String> getDefaultPublicationLanguages();
}
