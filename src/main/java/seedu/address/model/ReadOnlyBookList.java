package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.tag.Tag;

public interface ReadOnlyBookList {

    ObservableList<Book> getBookList();

    ObservableList<Tag> getTagList();

}
