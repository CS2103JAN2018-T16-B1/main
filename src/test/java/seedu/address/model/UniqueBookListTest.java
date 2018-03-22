package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.book.UniqueBookList;

public class uniqueBookListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueBookList UniqueBookList = new UniqueBookList();
        thrown.expect(UnsupportedOperationException.class);
        UniqueBookList.asObservableList().remove(0);
    }
}
