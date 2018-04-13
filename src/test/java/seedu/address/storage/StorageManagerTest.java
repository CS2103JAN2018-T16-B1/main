package seedu.address.storage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.events.model.CatalogueChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;

public class StorageManagerTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlCatalogueStorage catalogueStorage = new XmlCatalogueStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        AccountListStorage accountListStorage = new SerialisedAccountListStorage((getTempFilePath("accountList.ser")));

        storageManager = new StorageManager(catalogueStorage, userPrefsStorage, accountListStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void catalogueReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlCatalogueStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlCatalogueStorageTest} class.
         */
        Catalogue original = getTypicalCatalogue();
        storageManager.saveCatalogue(original);
        ReadOnlyCatalogue retrieved = storageManager.readCatalogue().get();
        assertEquals(original, new Catalogue(retrieved));
    }

    @Test
    public void getCatalogueFilePath() {
        assertNotNull(storageManager.getCatalogueFilePath());
    }

    @Test
    public void handleCatalogueChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlCatalogueStorageExceptionThrowingStub("dummy"),
            new JsonUserPrefsStorage("dummy"),
            new SerialisedAccountListStorage("dummy"));
        storage.handleCatalogueChangedEvent(new CatalogueChangedEvent(new Catalogue()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlCatalogueStorageExceptionThrowingStub extends XmlCatalogueStorage {

        public XmlCatalogueStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveCatalogue(ReadOnlyCatalogue catalogue, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
