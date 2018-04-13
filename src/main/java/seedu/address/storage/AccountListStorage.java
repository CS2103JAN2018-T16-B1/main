//@@author QiuHaohao
package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.UniqueAccountList;

import java.io.IOException;
import java.util.Optional;

//import java.util.Optional;

/**
 * Represents a storage for {@link UniqueAccountList}.
 */
public interface AccountListStorage {
    /**
     * Returns the file path of the data file.
     */
    String getAccountListFilePath();

    /**
     * Returns AccountList data as a {@link UniqueAccountList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<UniqueAccountList> readAccountList() throws DataConversionException, IOException;

    /**
     * @see #getAccountListFilePath()
     */
    Optional<UniqueAccountList> readAccountList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link UniqueAccountList} to the storage.
     *
     * @param accountList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccountList(UniqueAccountList accountList) throws IOException;

    /**
     * @see #saveAccountList(UniqueAccountList)
     */
    void saveAccountList(UniqueAccountList accountList, String filePath) throws IOException;

}
