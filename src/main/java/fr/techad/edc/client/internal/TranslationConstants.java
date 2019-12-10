package fr.techad.edc.client.internal;

import com.google.common.collect.Sets;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
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
    
    Set<String> LANGUAGE_CODES = Sets.newHashSet(
            "en", // English
            "ar", // Arabic
            "bg", // Bulgarian
            "zh", // Chinese
            "hr", // Croatian
            "cs", // Czech
            "da", // Danish
            "nl", // Dutch
            "et", // Estonian
            "fi", // Finnish
            "fr", // French
            "de", // German
            "el", // Greek
            "he", // Hebrew
            "hu", // Hungarian
            "is", // Icelandic
            "ga", // Irish
            "it", // Italian
            "ja", // Japanese
            "ko", // Korean
            "lv", // Latvian
            "lt", // Lithuanian
            "lb", // Luxembourgish
            "mt", // Maltese
            "no", // Norwegian
            "fa", // Persian
            "pl", // Polish
            "pt", // Portuguese
            "ro", // Romanian
            "ru", // Russian
            "sk", // Slovak
            "sl", // Slovenian
            "es", // Spanish
            "sv", // Swedish
            "tr", // Turkish
            "vi" // Vietnamese
    );
}
