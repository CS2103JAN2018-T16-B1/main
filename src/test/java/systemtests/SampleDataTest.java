package systemtests;

import org.junit.Test;
import seedu.address.model.Catalogue;
import seedu.address.model.book.Book;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

public class SampleDataTest extends CatalogueSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected Catalogue getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected String getDataFileLocation() {
        String filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void catalogue_dataFileDoesNotExist_loadSampleData() {
        Book[] expectedList = SampleDataUtil.getSampleBooks();
        assertListMatching(getBookListPanel(), expectedList);
    }
}
