package fr.techad.edc.client.internal;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface TranslationConstants {

    String DEFAULT_LANGUAGE_CODE = "en";

    String ARTICLES_KEY = "articles";
    String LINKS_KEY = "links";

    Map<String, String> DEFAULT_LABELS = Stream.of(
            new AbstractMap.SimpleEntry<>(ARTICLES_KEY, "Need more..."),
            new AbstractMap.SimpleEntry<>(LINKS_KEY, "Related topics"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
}
