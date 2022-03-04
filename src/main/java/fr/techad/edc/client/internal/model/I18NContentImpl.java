package fr.techad.edc.client.internal.model;

import com.google.common.collect.Maps;
import fr.techad.edc.client.internal.io.HttpReaderImpl;
import fr.techad.edc.client.model.I18NContent;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18NContentImpl implements I18NContent {

    private String I18N_LABELS_ROOT = ".labels.";
    private String I18N_ERRORS_ROOT = ".errors.";
    private Logger LOGGER = LoggerFactory.getLogger(HttpReaderImpl.class);

    Map<String, String> traduction = new HashMap<String, String>();



    Map<String, Map<String, String>> labels = Maps.newHashMap();

    @Override
    public void setMessage(String lang, String type, String key, String value){
        LOGGER.debug("Set Message traduction lang: {}, type: {}, key: {}, value: {} ", lang, type, key, value);
        traduction.put(lang+"."+type+"."+key, value);
    }

    @Override
    public String getLabel(String lang, String key) {
        LOGGER.debug("Get label traduction lang: {}, key: {}", lang, key);
        return traduction.get(lang+I18N_LABELS_ROOT+key);
    }

    @Override
    public String getError(String lang, String key) {
        LOGGER.debug("Get errors traduction lang: {}, key:{}", lang, key);
        return traduction.get(lang+I18N_ERRORS_ROOT+key);
    }

    @Override
    public void setLabels(String lang, Map<String, String> readLabelsForLang) {
        LOGGER.debug("set labels traduction lang: {}, key: {}", lang, readLabelsForLang);
        labels.put(lang, readLabelsForLang);
    }

    @Override
    public Map<String, Map<String, String>> getLabels() {
        LOGGER.debug("Get labels traduction {}", labels);
        return labels;
    }

}
