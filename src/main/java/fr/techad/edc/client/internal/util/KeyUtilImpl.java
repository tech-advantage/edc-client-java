/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.util;

import fr.techad.edc.client.util.KeyUtil;

/**
 * This builder is a key builder
 */
public class KeyUtilImpl implements KeyUtil {
    
    @Override
    public String getKey(String mainKey, String subKey, String languageCode) {
        return mainKey + "." + subKey + "." + languageCode;
    }
}
