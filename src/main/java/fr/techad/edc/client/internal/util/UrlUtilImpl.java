/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.util;

import fr.techad.edc.client.model.ClientConfiguration;
import fr.techad.edc.client.model.InvalidUrlException;
import fr.techad.edc.client.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;

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
        this.clientConfiguration = clientConfiguration;

    }

    @Override
    public String getHomeUrl() throws InvalidUrlException {
        return clientConfiguration.getWebHelpUrl() + "/home";
    }

    @Override
    public String getErrorUrl() throws InvalidUrlException {
        return clientConfiguration.getWebHelpUrl() + "/error";
    }

    @Override
    public String getContextUrl(String publicationId, String mainKey, String subKey, String languageCode, int articleIndex) throws InvalidUrlException {
        return clientConfiguration.getWebHelpUrl() + "/context/" + publicationId + "/" + mainKey + "/" + subKey + "/" + languageCode + "/" + articleIndex;
    }

    @Override
    public String getDocumentationUrl(Long id, String languageCode) throws InvalidUrlException {
        String langCode = StringUtils.isNotBlank(languageCode) ? "/" + languageCode : "";
        return clientConfiguration.getWebHelpUrl() + "/doc/" + id + langCode;
    }
}
