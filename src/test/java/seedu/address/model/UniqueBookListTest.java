package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.book.UniqueBookList;

public class UniqueBookListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueBookList uniqueBookList = new UniqueBookList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueBookList.asObservableList().remove(0);
    }
}
