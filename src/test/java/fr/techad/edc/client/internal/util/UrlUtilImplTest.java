/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.util;

import fr.techad.edc.client.CommonBase;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.UrlUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public class UrlUtilImplTest extends CommonBase {

    @Test
    public void shouldGetHomeUrl() throws InvalidUrlException {
        UrlUtil urlUtil = createUrlBuilder();
        String url = urlUtil.getHomeUrl();
        Assert.assertEquals("https://demo.easydoccontents.com/help/home", url);
    }
    @Test
    public void shouldGetErrorUrl() throws InvalidUrlException {
        UrlUtil urlUtil = createUrlBuilder();
        String url = urlUtil.getErrorUrl();
        Assert.assertEquals("https://demo.easydoccontents.com/help/error", url);
    }
    @Test
    public void shouldCreateAContextUrl() throws InvalidUrlException {
        UrlUtil urlUtil = createUrlBuilder();
        String url = urlUtil.getContextUrl("fr.techad.edc.help","fr.techad.edc", "help.center", "en", 0);
        Assert.assertEquals("https://demo.easydoccontents.com/help/context/fr.techad.edc.help/fr.techad.edc/help.center/en/0", url);
    }

    @Test
    public void shouldCreateADocUrl() throws InvalidUrlException {
        UrlUtil urlUtil = createUrlBuilder();
        String url = urlUtil.getDocumentationUrl(12L, "fr", "myPluginId");
        Assert.assertEquals("https://demo.easydoccontents.com/help/doc/myPluginId/12/fr", url);
    }

    @Test
    public void shouldCreateADocUrlWithNullLanguage() throws InvalidUrlException {
        UrlUtil urlUtil = createUrlBuilder();
        String url = urlUtil.getDocumentationUrl(12L, null, "myPluginId");
        Assert.assertEquals("https://demo.easydoccontents.com/help/doc/myPluginId/12", url);
    }

    @Test
    public void shouldCreateADocUrlWithNull() throws InvalidUrlException {
        UrlUtil urlUtil = createUrlBuilder();
        String url = urlUtil.getDocumentationUrl(null, null, null);
        Assert.assertEquals("https://demo.easydoccontents.com/help/doc/null", url);
    }



}