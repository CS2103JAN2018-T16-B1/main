package systemtests;

import seedu.address.model.Model;
import seedu.address.model.book.Book;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Book> PREDICATE_MATCHING_NO_BOOKS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Book> toDisplay) {
        Optional<Predicate<Book>> predicate =
            toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredBookList(predicate.orElse(PREDICATE_MATCHING_NO_BOOKS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Book... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Book} equals to {@code other}.
     */
    private static Predicate<Book> getPredicateMatching(Book other) {
        return book -> book.equals(other);
    }
}
