package fr.techad.edc.client.internal.model;

import fr.techad.edc.client.internal.io.HttpReaderImpl;
import fr.techad.edc.client.model.I18NContent;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18NContentImpl implements I18NContent {

    private Logger LOGGER = LoggerFactory.getLogger(HttpReaderImpl.class);

    Map<String, String> traduction = new HashMap<String, String>();

    @Override
    public String getTranslation(String lang, String type, String key, String publicationId){
        LOGGER.debug("Get translation lang: {}, type: {}, key: {}, publicationId: {} ", lang, type, key, publicationId);
        return traduction.get(lang+"."+type+"."+key);
    }

    @Override
    public void setMessage(String lang, String type, String key, String value){
        LOGGER.debug("Set Message traduction lang: {}, type: {}, key: {}, value: {} ", lang, type, key, value);
        traduction.put(lang+"."+type+"."+key, value);
    }
}
