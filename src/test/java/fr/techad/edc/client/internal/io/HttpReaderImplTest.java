/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.io;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import fr.techad.edc.client.CommonBase;
import fr.techad.edc.client.internal.util.KeyUtilImpl;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.KeyUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class HttpReaderImplTest extends CommonBase {

    private EdcReader edcReader;
    String languageCode = "en";
    Set<String> languagesCodes = Sets.newHashSet();

    @Before
    public void setup() {
        languagesCodes.add("en");
        languagesCodes.add("fr");
        this.edcReader = createEdcReader();
    }

    @Test
    public void shouldGetContextJson() throws IOException, InvalidUrlException {
        KeyUtil keyUtil = new KeyUtilImpl();

        Map<String, ContextItem> contextItemMap = edcReader.readContext();
        Assert.assertEquals(39, contextItemMap.size());
        ContextItem contextItem = contextItemMap.get(keyUtil.getKey("fr.techad.edc",  "leftmenu.account", "en"));
        this.languageCode = contextItem.getLanguageCode();
        Assert.assertEquals("All you need about edc", contextItem.getDescription());
        Assert.assertEquals("About edc", contextItem.getLabel());
        Assert.assertEquals("en", contextItem.getLanguageCode());
        Assert.assertEquals("edchelp/html/en/1/7868/index.html", contextItem.getUrl());
        Assert.assertEquals(1, contextItem.articleSize());
        Assert.assertEquals(3, contextItem.linkSize());

        contextItem = contextItemMap.get(keyUtil.getKey("fr.techad.edc", "leftmenu.account", "en"));
        Assert.assertEquals(2, contextItem.articleSize());
        Assert.assertEquals(3, contextItem.linkSize());
    }

    @Test
    public void shouldGetLabelValueWithDefinedLanguage() throws IOException, InvalidUrlException {

        String articlesLabelEN = edcReader.readLabel(languagesCodes).getTranslation("en", "labels", "iconAlt", "leftmenu.account");
        Assert.assertFalse(Strings.isNullOrEmpty(articlesLabelEN));
        Assert.assertEquals("Help", articlesLabelEN);

        String articlesLabelFR = edcReader.readLabel(languagesCodes).getTranslation("fr", "labels", "iconAlt", "leftmenu.account");
        Assert.assertFalse(Strings.isNullOrEmpty(articlesLabelFR));
        Assert.assertEquals("Aide", articlesLabelFR);
    }

    @Test
    public void shouldGetErrorValueWithDefinedLanguage() throws IOException, InvalidUrlException {

        String errorLabelEN = edcReader.readLabel(languagesCodes).getTranslation("en",  "errors","failedData", "leftmenu.account");
        Assert.assertFalse(Strings.isNullOrEmpty(errorLabelEN));
        Assert.assertEquals("An error occurred when fetching data ! \nCheck the brick keys provided to the EdcHelp component.", errorLabelEN);

        String errorLabelFR = edcReader.readLabel(languagesCodes).getTranslation("fr", "errors", "failedData", "leftmenu.account");
        Assert.assertFalse(Strings.isNullOrEmpty(errorLabelFR));
        Assert.assertEquals("Une erreur s'est produite lors de la récupération des données! \nVérifiez les clés de brique fournies au composant EdcHelp", errorLabelFR);
    }

}