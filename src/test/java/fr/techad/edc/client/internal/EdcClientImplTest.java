/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal;

import fr.techad.edc.client.CommonBase;
import fr.techad.edc.client.EdcClient;
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
        Assert.assertEquals("https://demo.easydoccontents.com/help/context/fr.techad.edc/help.center/en/0", url);
    }

}