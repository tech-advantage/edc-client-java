/*
 * Copyright (c) 2018. All rights reserved
 */

package fr.techad.edc.client.internal.util;

import org.apache.commons.lang3.StringUtils;

/**
 * TECH ADVANTAGE
 * All right reserved
 * Created by cochon on 23/01/2018.
 */
public class EdcStringUtils {
    /**
     * Appends the suffix to the end of the string if the string does not
     * already end with the suffix.
     * <p>
     * <strong>The implementation is based on apache lang3 3.6</strong>
     *
     * @param str      The string.
     * @param suffix   The suffix to append to the end of the string.
     * @param suffixes Additional suffixes that are valid terminators (optional).
     * @return A new String if suffix was appended, the same string otherwise.
     */
    public static String appendIfMissing(final String str, final CharSequence suffix, final CharSequence... suffixes) {
        if (str == null || StringUtils.isEmpty(suffix) || StringUtils.endsWith(str, suffix)) {
            return str;
        }
        if (suffixes != null && suffixes.length > 0) {
            for (final CharSequence s : suffixes) {
                if (StringUtils.endsWith(str, s)) {
                    return str;
                }
            }
        }
        return str + suffix.toString();
    }

}
