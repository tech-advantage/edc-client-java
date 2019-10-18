/*
 * Copyright (c) 2017. All rights reserved
 */

package fr.techad.edc.client.util;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 20/06/2017.
 */
public interface KeyUtil {
    /**
     * Build the key according to the main, sub key and the language code.
     *
     * @param mainKey      the main key
     * @param subKey       the sub key
     * @param languageCode the language code
     * @return the key
     */
    String getKey(String mainKey, String subKey, String languageCode);

    /**
     * Check if the full key contains the main and subKey
     *
     * @param fullKey the full key, containing keys and language code
     * @param mainKey a main key
     * @param subKey a sub key
     * @return returns true if mainKey.subKey is present for any language
     */
    boolean containsKey(String fullKey, String mainKey, String subKey);
}
