/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.internal.util;

import fr.techad.edc.client.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * This builder is a key builder
 */
public class KeyUtilImpl implements KeyUtil {
    
    @Override
    public String getKey(String mainKey, String subKey, String languageCode) {
        return mainKey + "." + subKey + "." + languageCode;
    }

    @Override
    public boolean containsKey(String fullKey, String mainKey, String subKey) {
        return StringUtils.isNotBlank(fullKey) && StringUtils.isNotBlank(mainKey) && StringUtils.isNotBlank(subKey) &&
                fullKey.contains(mainKey + "." + subKey);
    }
}
