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
}
