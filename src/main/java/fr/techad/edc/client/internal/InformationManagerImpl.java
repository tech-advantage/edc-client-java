package fr.techad.edc.client.internal;

import com.google.common.collect.Maps;
import fr.techad.edc.client.InformationManager;
import fr.techad.edc.client.io.EdcReader;
import fr.techad.edc.client.model.Information;
import fr.techad.edc.client.model.InvalidUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class InformationManagerImpl implements InformationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(InformationManager.class);
    private EdcReader reader;

    // Map containing the information for each publication Id
    private Map<String, Information> information = Maps.newHashMap();

    @Inject
    public InformationManagerImpl(EdcReader reader) {
        this.reader = reader;
    }

    @Override
    public void loadInformation() throws IOException, InvalidUrlException {
        if (this.information.isEmpty()) {
            this.information.putAll(this.reader.readInfo());
            LOGGER.debug("Information loaded {}", this.information);
        }
    }

    @Override
    public void forceReload() {
        this.information.clear();
        LOGGER.debug("Information cleared, will be ");
    }

    @Override
    public Map<String, Information> getPublicationInformation() {
        return Collections.unmodifiableMap(this.information);
    }
}
