/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.io;

import fr.techad.edc.client.CommonBase;
import fr.techad.edc.client.internal.util.KeyUtilImpl;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.internal.model.ContextItemImpl;
import fr.techad.edc.client.model.ContextItem;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.KeyUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 19/06/2017.
 */
public class HttpReaderImplTest extends CommonBase {

    private EdcReader edcReader;

    @Before
    public void setup() {
        this.edcReader = createEdcReader();
    }

    @Test
    public void shouldGetContextJson() throws IOException, InvalidUrlException {
        KeyUtil keyUtil = new KeyUtilImpl();

        Map<String, ContextItem> contextItemMap = edcReader.readContext();
        Assert.assertEquals(31, contextItemMap.size());
        ContextItem contextItem = contextItemMap.get(keyUtil.getKey("fr.techad.edc", "help.center", "en"));
        Assert.assertEquals("All you need about edc", contextItem.getDescription());
        Assert.assertEquals("About edc", contextItem.getLabel());
        Assert.assertEquals("en", contextItem.getLanguageCode());
        Assert.assertEquals("edchelp/html/en/1/7868/index.html", contextItem.getUrl());
        Assert.assertEquals(1, contextItem.articleSize());
        Assert.assertEquals(3, contextItem.linkSize());

        contextItem = contextItemMap.get(keyUtil.getKey("fr.techad.edc.configuration", "storehouses", "en"));
        Assert.assertEquals(2, contextItem.articleSize());
        Assert.assertEquals(3, contextItem.linkSize());
    }

}