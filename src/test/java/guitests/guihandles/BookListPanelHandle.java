package guitests.guihandles;

import javafx.scene.control.ListView;
import seedu.address.model.book.Book;
import seedu.address.ui.BookCard;

import java.util.List;
import java.util.Optional;

/**
 * Provides a handle for {@code BookListPanel} containing the list of {@code BookCard}.
 */
public class BookListPanelHandle extends NodeHandle<ListView<BookCard>> {
    public static final String BOOK_LIST_VIEW_ID = "#bookListView";

    private Optional<BookCard> lastRememberedSelectedBookCard;

    public BookListPanelHandle(ListView<BookCard> bookListPanelNode) {
        super(bookListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code BookCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public BookCardHandle getHandleToSelectedCard() {
        List<BookCard> bookList = getRootNode().getSelectionModel().getSelectedItems();

        if (bookList.size() != 1) {
            throw new AssertionError("Book list size expected 1.");
        }

        return new BookCardHandle(bookList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<BookCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the book.
     */
    public void navigateToCard(Book book) {
        List<BookCard> cards = getRootNode().getItems();
        Optional<BookCard> matchingCard = cards.stream().filter(card -> card.book.equals(book)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Book does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the book card handle of a book associated with the {@code index} in the list.
     */
    public BookCardHandle getBookCardHandle(int index) {
        return getBookCardHandle(getRootNode().getItems().get(index).book);
    }

    /**
     * Returns the {@code BookCardHandle} of the specified {@code book} in the list.
     */
    public BookCardHandle getBookCardHandle(Book book) {
        Optional<BookCardHandle> handle = getRootNode().getItems().stream()
            .filter(card -> card.book.equals(book))
            .map(card -> new BookCardHandle(card.getRoot()))
            .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Book does not exist."));
    }

    /**
     * Selects the {@code BookCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code BookCard} in the list.
     */
    public void rememberSelectedBookCard() {
        List<BookCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedBookCard = Optional.empty();
        } else {
            lastRememberedSelectedBookCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code BookCard} is different from the value remembered by the most recent
     * {@code rememberSelectedBookCard()} call.
     */
    public boolean isSelectedBookCardChanged() {
        List<BookCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedBookCard.isPresent();
        } else {
            return !lastRememberedSelectedBookCard.isPresent()
                || !lastRememberedSelectedBookCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
