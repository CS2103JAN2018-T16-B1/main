package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.CatalogueChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CatalogueStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getCatalogueFilePath();

    @Override
    Optional<ReadOnlyCatalogue> readCatalogue() throws DataConversionException, IOException;

    @Override
    void saveCatalogue(ReadOnlyCatalogue catalogue) throws IOException;

    /**
     * Saves the current version of the Catalogue to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleCatalogueChangedEvent(CatalogueChangedEvent abce);
}
