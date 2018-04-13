//@@author QiuHaohao
package seedu.address.model.account;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrivilegeLevelTest {

    @Test
    public void constructor_invalidPrivilegeLevel_throwsIllegalArgumentException() {
        final int invalidPrivilegeLevel1 = 3;
        final int invalidPrivilegeLevel2 = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrivilegeLevel(invalidPrivilegeLevel1));
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrivilegeLevel(invalidPrivilegeLevel2));
    }

    @Test
    public void isValidPrivilegeLevel_test() {
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(0));
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(1));
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(2));
        assertFalse(PrivilegeLevel.isValidPrivilegeLevel(3));
        assertFalse(PrivilegeLevel.isValidPrivilegeLevel(-1));
    }

    @Test
    public void equals() {
        PrivilegeLevel p0 = new PrivilegeLevel(0);
        PrivilegeLevel p0copy = new PrivilegeLevel(0);
        PrivilegeLevel p1 = new PrivilegeLevel(1);
        PrivilegeLevel p2 = new PrivilegeLevel(2);

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p0.equals(p0copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }


    @Test
    public void compareTo() {
        PrivilegeLevel p0 = new PrivilegeLevel(0);
        PrivilegeLevel p1 = new PrivilegeLevel(1);
        PrivilegeLevel p2 = new PrivilegeLevel(2);

        assertTrue(p0.compareTo(p1) < 0);
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p1.compareTo(p0) > 0);
        assertTrue(p1.compareTo(p1) == 0);
    }
}
