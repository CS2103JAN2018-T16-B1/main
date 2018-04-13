package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores catalogue data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given catalogue data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableCatalogue catalogue)
        throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, catalogue);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns catalogue in the file or an empty catalogue
     */
    public static XmlSerializableCatalogue loadDataFromSaveFile(File file) throws DataConversionException,
        FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCatalogue.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
