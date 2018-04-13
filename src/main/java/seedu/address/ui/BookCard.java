package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.book.Book;

import java.util.Random;


/**
 * An UI component that displays information of a {@code Book}.
 */
public class BookCard extends UiPart<Region> {

    private static final String FXML = "BookListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Catalogue</a>
     */

    public final Book book;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label id;
    @FXML
    private Label isbn;
    @FXML
    private Label avail;
    @FXML
    private FlowPane tags;


    public BookCard(Book book, int displayedIndex) {
        super(FXML);
        this.book = book;
        id.setText(displayedIndex + ". ");
        title.setText(book.getTitle().fullTitle);
        author.setText(book.getAuthor().value);
        isbn.setText(book.getIsbn().value);
        avail.setText(book.getAvail().getValue());
        colorTags(book);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookCard)) {
            return false;
        }

        // state check
        BookCard card = (BookCard) other;
        return id.getText().equals(card.id.getText())
            && book.equals(card.book);
    }

    private String getTagColor() {
        Random rand = new Random();
        int sCase = rand.nextInt(10);
        switch (sCase) {
        case 0:
            return "-fx-background-color: blue;";
        case 1:
            return "-fx-background-color: green;";
        case 2:
            return "-fx-background-color: red;";
        case 3:
            return "-fx-background-color: maroon;";
        case 4:
            return "-fx-background-color: orange;";
        case 5:
            return "-fx-background-color: violet;";
        case 6:
            return "-fx-background-color: brown;";
        case 7:
            return "-fx-background-color: khaki;";
        case 8:
            return "-fx-background-color: olive;";
        case 9:
            return "-fx-background-color: indigo;";
        default:
            return "-fx-background-color: pink;";
        }

    }

    /**
     * Assign a new color to each new tag
     *
     * @param book
     */

    private void colorTags(Book book) {
        book.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tags.getChildren().add(tagLabel);
            tagLabel.setStyle(getTagColor());
        });
    }
}
