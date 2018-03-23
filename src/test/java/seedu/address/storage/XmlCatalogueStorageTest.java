package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalBooks.ALICE;
import static seedu.address.testutil.TypicalBooks.HOON;
import static seedu.address.testutil.TypicalBooks.IDA;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;

public class XmlCatalogueStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlCatalogueStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readCatalogue_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readCatalogue(null);
    }

    private java.util.Optional<ReadOnlyCatalogue> readCatalogue(String filePath) throws Exception {
        return new XmlCatalogueStorage(filePath).readCatalogue(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCatalogue("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readCatalogue("NotXmlFormatCatalogue.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readCatalogue_invalidBookCatalogue_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCatalogue("invalidBookCatalogue.xml");
    }

    @Test
    public void readCatalogue_invalidAndValidBookCatalogue_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCatalogue("invalidAndValidBookCatalogue.xml");
    }

    @Test
    public void readAndSaveCatalogue_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempCatalogue.xml";
        Catalogue original = getTypicalCatalogue();
        XmlCatalogueStorage xmlCatalogueStorage = new XmlCatalogueStorage(filePath);

        //Save in new file and read back
        xmlCatalogueStorage.saveCatalogue(original, filePath);
        ReadOnlyCatalogue readBack = xmlCatalogueStorage.readCatalogue(filePath).get();
        assertEquals(original, new Catalogue(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addBook(HOON);
        original.removeBook(ALICE);
        xmlCatalogueStorage.saveCatalogue(original, filePath);
        readBack = xmlCatalogueStorage.readCatalogue(filePath).get();
        assertEquals(original, new Catalogue(readBack));

        //Save and read without specifying file path
        original.addBook(IDA);
        xmlCatalogueStorage.saveCatalogue(original); //file path not specified
        readBack = xmlCatalogueStorage.readCatalogue().get(); //file path not specified
        assertEquals(original, new Catalogue(readBack));

    }

    @Test
    public void saveCatalogue_nullCatalogue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCatalogue(null, "SomeFile.xml");
    }

    /**
     * Saves {@code catalogue} at the specified {@code filePath}.
     */
    private void saveCatalogue(ReadOnlyCatalogue catalogue, String filePath) {
        try {
            new XmlCatalogueStorage(filePath).saveCatalogue(catalogue, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCatalogue_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCatalogue(new Catalogue(), null);
    }


}
