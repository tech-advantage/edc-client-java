package fr.techad.edc.client.injector.provider;

import fr.techad.edc.client.internal.model.I18NContentImpl;
import fr.techad.edc.client.model.I18NContent;

import javax.inject.Provider;

public class I18NProvider implements Provider<I18NContent> {
    @Override
    public I18NContent get() {
        return new I18NContentImpl();
    }
}
