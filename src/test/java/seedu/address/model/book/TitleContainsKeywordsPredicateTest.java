package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BookBuilder;

public class TitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TitleContainsKeywordsPredicate firstPredicate = new TitleContainsKeywordsPredicate(firstPredicateKeywordList);
        TitleContainsKeywordsPredicate secondPredicate = new TitleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TitleContainsKeywordsPredicate firstPredicateCopy;
        firstPredicateCopy = new TitleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different book -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
    //@@author khiayi
    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        TitleContainsKeywordsPredicate predicate;
        predicate = new TitleContainsKeywordsPredicate(Collections.singletonList("Animal"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Animal Breaking").build()));

        // Multiple keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Animal", "Breaking"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Animal Breaking").build()));

        // Only one matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Breaking", "Carol"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Animal Carol").build()));

        // Mixed-case keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("aNimal", "bREAKING"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Animal Breaking").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TitleContainsKeywordsPredicate predicate = new TitleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BookBuilder().withTitle("Animal").build()));

        // Non-matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BookBuilder().withTitle("Animal Breaking").build()));

        // Keywords match isbn, avail and address, but does not match name
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("9780736692427", "Borrowed", "Main", "Street"));
        assertFalse(predicate.test(new BookBuilder().withTitle("Animal").withIsbn("9780736692427")
            .withAvail("Borrowed").withAuthor("Main Street").build()));
    }
}
