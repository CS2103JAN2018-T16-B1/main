package seedu.address.ui;

import guitests.guihandles.BrowserPanelHandle;
import org.junit.Before;
import org.junit.Test;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.BookPanelSelectionChangedEvent;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalBooks.ANIMAL;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

public class BrowserPanelTest extends GuiUnitTest {
    private BookPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new BookPanelSelectionChangedEvent(new BookCard(ANIMAL, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        //This check should be removed because goodreads redirect the ISBN search
        // associated web page of a book
        // postNow(selectionChangedEventStub);
        // URL expectedBookUrl = new URL(BrowserPanel.SEARCH_PAGE_URL
        //                + ANIMAL.getTitle().fullTitle.replaceAll(" ", "%20"));


        //waitUntilBrowserLoaded(browserPanelHandle);
        //assertEquals(expectedBookUrl, browserPanelHandle.getLoadedUrl());
    }
}
