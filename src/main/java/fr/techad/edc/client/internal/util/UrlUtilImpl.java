/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.util;

import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.UrlUtil;

import javax.inject.Inject;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public class UrlUtilImpl implements UrlUtil {
    private ClientConfiguration clientConfiguration;

    @Inject
    public UrlUtilImpl(ClientConfiguration clientConfiguration) {
        this.clientConfiguration=clientConfiguration;

    }

    @Override
    public String getContextUrl(String mainKey, String subKey, String languageCode, int articleIndex) throws InvalidUrlException {
        return clientConfiguration.getWebHelpUrl()+"/context/" + mainKey + "/" + subKey + "/" + languageCode + "/" + articleIndex;
    }
}
