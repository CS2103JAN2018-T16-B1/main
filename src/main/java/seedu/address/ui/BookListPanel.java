package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import org.fxmisc.easybind.EasyBind;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BookPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.book.Book;

import java.util.logging.Logger;

/**
 * Panel containing the list of books.
 */
public class BookListPanel extends UiPart<Region> {
    private static final String FXML = "BookListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BookListPanel.class);

    @FXML
    private ListView<BookCard> bookListView;

    public BookListPanel(ObservableList<Book> bookList) {
        super(FXML);
        setConnections(bookList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Book> bookList) {
        ObservableList<BookCard> mappedList = EasyBind.map(
            bookList, (book) -> new BookCard(book, bookList.indexOf(book) + 1));
        bookListView.setItems(mappedList);
        bookListView.setCellFactory(listView -> new BookListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        bookListView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    logger.fine("Selection in book list panel changed to : '" + newValue + "'");
                    raise(new BookPanelSelectionChangedEvent(newValue));
                }
            });
    }

    /**
     * Scrolls to the {@code BookCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            bookListView.scrollTo(index);
            bookListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code BookCard}.
     */
    class BookListViewCell extends ListCell<BookCard> {

        @Override
        protected void updateItem(BookCard book, boolean empty) {
            super.updateItem(book, empty);

            if (empty || book == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(book.getRoot());
            }
        }
    }

}
