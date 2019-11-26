/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal;

import fr.techad.edc.client.CommonBase;
import fr.techad.edc.client.EdcClient;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public class EdcClientImplTest extends CommonBase {
    private EdcClient edcClient;

    @Before
    public void setup() {
         edcClient = createEdcClient();
    }

    @Test
    public void shouldGetContextUrl() throws IOException, InvalidUrlException {
        String url = edcClient.getContextWebHelpUrl("fr.techad.edc", "help.center", "en");
        Assert.assertEquals("https://demo.easydoccontents.com/help/context/edchelp/fr.techad.edc/help.center/en/0", url);
    }

    @Test
    public void shouldGetContextUrlWithRank() throws IOException, InvalidUrlException {
        String url = edcClient.getContextWebHelpUrl("fr.techad.edc", "help.center", 2, "en");
        Assert.assertEquals("https://demo.easydoccontents.com/help/context/edchelp/fr.techad.edc/help.center/en/2", url);
    }

    @Test
    public void shouldGetContext() throws IOException, InvalidUrlException {
        ContextItem contextItem = edcClient.getContextItem("fr.techad.edc", "help.center", "en");
        Assert.assertNotNull(contextItem);
        Assert.assertEquals(1,contextItem.articleSize());
        Assert.assertEquals(3,contextItem.linkSize());
    }

    @Test
    public void shouldGetDocumentationUrl() throws InvalidUrlException {
        String documentationWebHelpUrl = edcClient.getDocumentationWebHelpUrl(434L, "ru", "myPluginId");
        Assert.assertEquals("https://demo.easydoccontents.com/help/doc/myPluginId/434/ru", documentationWebHelpUrl);
    }

    @Test
    public void shouldGetDocumentationUrlWithNullLanguage() throws InvalidUrlException {
        String documentationWebHelpUrl = edcClient.getDocumentationWebHelpUrl(434L, null, "myPluginId");
        Assert.assertEquals("https://demo.easydoccontents.com/help/doc/myPluginId/434", documentationWebHelpUrl);
    }

    @Test
    public void shouldGetDocumentationUrlWithNullPublicationId() throws InvalidUrlException {
        String documentationWebHelpUrl = edcClient.getDocumentationWebHelpUrl(434L, null, null);
        Assert.assertEquals("https://demo.easydoccontents.com/help/doc/434", documentationWebHelpUrl);
    }


}