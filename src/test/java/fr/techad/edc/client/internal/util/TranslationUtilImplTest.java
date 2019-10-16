package fr.techad.edc.client.internal.util;

import com.google.common.collect.Maps;
import fr.techad.edc.client.CommonBase;
import fr.techad.edc.client.injector.provider.InformationProvider;
import fr.techad.edc.client.internal.TranslationConstants;
import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.util.TranslationUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TranslationUtilImplTest extends CommonBase {

    @Test
    public void shouldGetPublicationDefaultLanguages() {
        TranslationUtil translationUtil = createTranslationUtil();
        Information information1 = createInformation("fr");
        Information information2 = createInformation("ru");
        Map<String, Information> informationPerPublication = Stream.of(
                new AbstractMap.SimpleEntry<>("pub1", information1),
                new AbstractMap.SimpleEntry<>("pub2", information2))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<String, String> defaultLanguages = translationUtil.getPublicationDefaultLanguages(informationPerPublication);

        Assert.assertEquals(Stream.of(
                new AbstractMap.SimpleEntry<>("pub1", "fr"),
                new AbstractMap.SimpleEntry<>("pub2", "ru"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), defaultLanguages);
    }

    @Test
    public void shouldCheckIfLanguageCodeIsValid() {
        TranslationUtil translationUtil = createTranslationUtil();
        Assert.assertTrue(translationUtil.isLanguageCodeValid("en"));
        Assert.assertTrue(translationUtil.isLanguageCodeValid("fr"));
        Assert.assertFalse(translationUtil.isLanguageCodeValid(""));
        Assert.assertFalse(translationUtil.isLanguageCodeValid("abc"));
        Assert.assertFalse(translationUtil.isLanguageCodeValid(" fr"));
        Assert.assertFalse(translationUtil.isLanguageCodeValid("fr "));
        Assert.assertFalse(translationUtil.isLanguageCodeValid(null));
    }

    @Test
    public void shouldCheckValidTranslatedLabels() {
        TranslationUtil translationUtil = createTranslationUtil();
        Map<String, String> labelsToCheck = createLabels("my articles label", "my links label");
        boolean isValid = translationUtil.checkTranslatedLabels(labelsToCheck);
        Assert.assertTrue(isValid);
    }

    @Test
    public void shouldCheckInvalidTranslatedLabels() {
        TranslationUtil translationUtil = createTranslationUtil();
        Map<String, String> labelsToCheck = createLabels("my articles label", null);
        boolean isValid = translationUtil.checkTranslatedLabels(labelsToCheck);
        Assert.assertFalse(isValid);
    }

    @Test
    public void shouldCheckEmptyTranslatedLabels() {
        TranslationUtil translationUtil = createTranslationUtil();
        Map<String, String> labelsToCheck = createLabels("my articles label", "");
        boolean isValid = translationUtil.checkTranslatedLabels(labelsToCheck);
        Assert.assertFalse(isValid);
    }

    private Information createInformation(String defaultLanguageCode) {
        InformationProvider informationProvider = new InformationProvider();
        Information information = informationProvider.get();
        information.setDefaultLanguage(defaultLanguageCode);
        return information;
    }

    private Map<String, String> createLabels(String articlesLabel, String linksLabels) {
        Map<String, String> labels = Maps.newHashMap();
        if (articlesLabel != null)
            labels.put(TranslationConstants.ARTICLES_KEY, articlesLabel);
        if (linksLabels != null)
            labels.put(TranslationConstants.LINKS_KEY, linksLabels);
        return labels;
    }
}
