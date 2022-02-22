package fr.techad.edc.client.internal.util;

import com.google.common.collect.Maps;
import fr.techad.edc.client.util.I18nContent;

import java.util.Map;

public class I18nContentImpl implements I18nContent {

    private Map<String, String> i18nLabel = Maps.newHashMap();
    private Map<String, String> i18nError = Maps.newHashMap();

    @Override
    public Map<String, String> getLabel(){
        return i18nLabel;
    }

    @Override
    public void setI18nLabel(Map<String, String> i18nLabel) {
        this.i18nLabel = i18nLabel;
    }

    @Override
    public Map<String, String> getError(){
        return i18nError;
    }

    @Override
    public void setI18nError(Map<String, String> i18nError) {
        this.i18nError = i18nError;
    }
}
