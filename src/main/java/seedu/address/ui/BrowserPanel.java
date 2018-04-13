package seedu.address.ui;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Logger;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BookPanelSelectionChangedEvent;
import seedu.address.model.book.Book;

import java.net.URL;
import java.util.logging.Logger;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String NO_INTERNET_PAGE = "NoInternet.html";
    public static final String GOOD_READS_URL = "www.goodreads.com";
    public static final String SEARCH_PAGE_URL =
        "https://www.goodreads.com/search?utf8=%E2%9C%93&query=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
        browser.getEngine().setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) "
            + "AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A5297c Safari/602.1");
    }

    /**
     * Loads the GoodReads page for a book
     * @param book
     */
    private void loadBookPage(Book book) {
        if (pingHost(GOOD_READS_URL, 80 , 3000)) {
            loadPage(SEARCH_PAGE_URL + book.getIsbn().toString());
        } else {
            loadNoInternetPage();
        }
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Loads a HTML indicating that there is no Internet.
     */
    private void loadNoInternetPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + NO_INTERNET_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleBookPanelSelectionChangedEvent(BookPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadBookPage(event.getNewSelection().book);
    }

    /**
     * Connects to a host and returns a boolean indicating whether the connection is successful
     * @param host
     * @param port
     * @param timeout
     * @return
     */
    public static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
}
