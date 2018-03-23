package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.Catalogue;
import seedu.address.testutil.TypicalBooks;

public class XmlSerializableCatalogueTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableCatalogueTest/");
    private static final File TYPICAL_BOOKS_FILE = new File(TEST_DATA_FOLDER + "typicalBooksCatalogue.xml");
    private static final File INVALID_BOOK_FILE = new File(TEST_DATA_FOLDER + "invalidBookCatalogue.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagCatalogue.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalBooksFile_success() throws Exception {
        XmlSerializableCatalogue dataFromFile = XmlUtil.getDataFromFile(TYPICAL_BOOKS_FILE,
                XmlSerializableCatalogue.class);
        Catalogue catalogueFromFile = dataFromFile.toModelType();
        Catalogue typicalBooksCatalogue = TypicalBooks.getTypicalCatalogue();
        assertEquals(catalogueFromFile, typicalBooksCatalogue);
    }

    @Test
    public void toModelType_invalidBookFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCatalogue dataFromFile = XmlUtil.getDataFromFile(INVALID_BOOK_FILE,
                XmlSerializableCatalogue.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCatalogue dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableCatalogue.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
