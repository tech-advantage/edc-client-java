package fr.techad.edc.client.internal.util;

import com.google.common.collect.Maps;
import fr.techad.edc.client.CommonBase;
import fr.techad.edc.client.internal.TranslationConstants;
import fr.techad.edc.client.internal.model.InformationImpl;
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
        TranslationUtil translationUtil = new TranslationUtilImpl();
        Information information1 = new InformationImpl();
        information1.setDefaultLanguage("fr");
        Information information2 = new InformationImpl();
        information2.setDefaultLanguage("ru");
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
    public void shouldGetDefaultLanguageIfPresent() {
        TranslationUtil translationUtil = new TranslationUtilImpl();
        Information information1 = new InformationImpl();
        information1.setDefaultLanguage("fr");
        String langCode = translationUtil.getPublicationLanguage(information1);
        Assert.assertEquals("fr", langCode);
    }

    @Test
    public void shouldGetSystemDefaultLanguageIfInformationNull() {
        TranslationUtil translationUtil = new TranslationUtilImpl();
        String langCode = translationUtil.getPublicationLanguage(null);
        Assert.assertEquals(TranslationConstants.DEFAULT_LANGUAGE_CODE, langCode);
    }

    @Test
    public void shouldGetSystemDefaultLanguageIfNotPresent() {
        TranslationUtil translationUtil = new TranslationUtilImpl();
        Information information1 = new InformationImpl();
        information1.setDefaultLanguage("");
        String langCode = translationUtil.getPublicationLanguage(information1);
        Assert.assertEquals(TranslationConstants.DEFAULT_LANGUAGE_CODE, langCode);
    }

    @Test
    public void shouldCheckValidTranslatedLabels() {
        TranslationUtil translationUtil = new TranslationUtilImpl();
        Map<String, String> labelsToCheck = createLabels("my articles label", "my links label");
        boolean isValid = translationUtil.checkTranslatedLabels(labelsToCheck);
        Assert.assertTrue(isValid);
    }

    @Test
    public void shouldCheckInvalidTranslatedLabels() {
        TranslationUtil translationUtil = new TranslationUtilImpl();
        Map<String, String> labelsToCheck = createLabels("my articles label", null);
        boolean isValid = translationUtil.checkTranslatedLabels(labelsToCheck);
        Assert.assertFalse(isValid);
    }

    @Test
    public void shouldCheckEmptyTranslatedLabels() {
        TranslationUtil translationUtil = new TranslationUtilImpl();
        Map<String, String> labelsToCheck = createLabels("my articles label", "");
        boolean isValid = translationUtil.checkTranslatedLabels(labelsToCheck);
        Assert.assertFalse(isValid);
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
