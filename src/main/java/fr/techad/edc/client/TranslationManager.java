package fr.techad.edc.client;

import fr.techad.edc.client.model.InvalidUrlException;

import java.io.IOException;

public interface TranslationManager {

    String getLabel(String key, String languageCode, String defaultLanguage) throws IOException, InvalidUrlException;

}
