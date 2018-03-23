package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CatalogueChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Catalogue data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CatalogueStorage catalogueStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CatalogueStorage catalogueStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.catalogueStorage = catalogueStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Catalogue methods ==============================

    @Override
    public String getCatalogueFilePath() {
        return catalogueStorage.getCatalogueFilePath();
    }

    @Override
    public Optional<ReadOnlyCatalogue> readCatalogue() throws DataConversionException, IOException {
        return readCatalogue(catalogueStorage.getCatalogueFilePath());
    }

    @Override
    public Optional<ReadOnlyCatalogue> readCatalogue(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return catalogueStorage.readCatalogue(filePath);
    }

    @Override
    public void saveCatalogue(ReadOnlyCatalogue catalogue) throws IOException {
        saveCatalogue(catalogue, catalogueStorage.getCatalogueFilePath());
    }

    @Override
    public void saveCatalogue(ReadOnlyCatalogue catalogue, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        catalogueStorage.saveCatalogue(catalogue, filePath);
    }


    @Override
    @Subscribe
    public void handleCatalogueChangedEvent(CatalogueChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveCatalogue(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
