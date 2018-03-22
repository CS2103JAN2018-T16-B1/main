package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Catalogue;
import seedu.address.storage.XmlAdaptedBook;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableCatalogue;
import seedu.address.testutil.CatalogueBuilder;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validCatalogue.xml");
    private static final File MISSING_BOOK_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingBookField.xml");
    private static final File INVALID_BOOK_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidBookField.xml");
    private static final File VALID_BOOK_FILE = new File(TEST_DATA_FOLDER + "validBook.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempCatalogue.xml"));

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_AVAILABILITY = "Borrowed";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, Catalogue.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Catalogue.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Catalogue.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        Catalogue dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableCatalogue.class).toModelType();
        assertEquals(9, dataFromFile.getBookList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedBookFromFile_fileWithMissingBookField_validResult() throws Exception {
        XmlAdaptedBook actualBook = XmlUtil.getDataFromFile(
                MISSING_BOOK_FIELD_FILE, XmlAdaptedBookWithRootElement.class);
        XmlAdaptedBook expectedBook = new XmlAdaptedBook(
                null, VALID_PHONE, VALID_AVAILABILITY, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void xmlAdaptedBookFromFile_fileWithInvalidBookField_validResult() throws Exception {
        XmlAdaptedBook actualBook = XmlUtil.getDataFromFile(
                INVALID_BOOK_FIELD_FILE, XmlAdaptedBookWithRootElement.class);
        XmlAdaptedBook expectedBook = new XmlAdaptedBook(
                VALID_NAME, INVALID_PHONE, VALID_AVAILABILITY, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void xmlAdaptedBookFromFile_fileWithValidBook_validResult() throws Exception {
        XmlAdaptedBook actualBook = XmlUtil.getDataFromFile(
                VALID_BOOK_FILE, XmlAdaptedBookWithRootElement.class);
        XmlAdaptedBook expectedBook = new XmlAdaptedBook(
                VALID_NAME, VALID_PHONE, VALID_AVAILABILITY, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new Catalogue());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Catalogue());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableCatalogue dataToWrite = new XmlSerializableCatalogue(new Catalogue());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableCatalogue dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableCatalogue.class);
        assertEquals(dataToWrite, dataFromFile);

        CatalogueBuilder builder = new CatalogueBuilder(new Catalogue());
        dataToWrite = new XmlSerializableCatalogue(
                builder.withBook(new BookBuilder().build()).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableCatalogue.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedBook}
     * objects.
     */
    @XmlRootElement(name = "book")
    private static class XmlAdaptedBookWithRootElement extends XmlAdaptedBook {}
}
