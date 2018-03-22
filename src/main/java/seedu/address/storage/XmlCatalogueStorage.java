package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyCatalogue;

/**
 * A class to access Catalogue data stored as an xml file on the hard disk.
 */
public class XmlCatalogueStorage implements CatalogueStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCatalogueStorage.class);

    private String filePath;

    public XmlCatalogueStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getCatalogueFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCatalogue> readCatalogue() throws DataConversionException, IOException {
        return readCatalogue(filePath);
    }

    /**
     * Similar to {@link #readCatalogue()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCatalogue> readCatalogue(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File catalogueFile = new File(filePath);

        if (!catalogueFile.exists()) {
            logger.info("Catalogue file "  + catalogueFile + " not found");
            return Optional.empty();
        }

        XmlSerializableCatalogue xmlCatalogue = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlCatalogue.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + catalogueFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCatalogue(ReadOnlyCatalogue catalogue) throws IOException {
        saveCatalogue(catalogue, filePath);
    }

    /**
     * Similar to {@link #saveCatalogue(ReadOnlyCatalogue)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveCatalogue(ReadOnlyCatalogue catalogue, String filePath) throws IOException {
        requireNonNull(catalogue);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableCatalogue(catalogue));
    }

}
