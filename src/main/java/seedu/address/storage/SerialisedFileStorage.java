//@@author QiuHaohao
package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.UniqueAccountList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Stores accountList data in a .ser file
 */
public class SerialisedFileStorage {
    /**
     * Saves the given catalogue data to the specified file.
     */
    public static void saveDataToFile(ObjectOutputStream out, UniqueAccountList accountList) {
        try {
            out.writeObject(accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns catalogue in the file or an empty catalogue
     */
    public static UniqueAccountList loadDataFromSaveFile(ObjectInputStream in) throws DataConversionException {
        try {
            return (UniqueAccountList) in.readObject();
        } catch (IOException e) {
            throw new DataConversionException(e);
        } catch (ClassNotFoundException e) {
            throw new DataConversionException(e);
        }
    }
}
