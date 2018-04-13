//@@author QiuHaohao
package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.UniqueAccountList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access AccountList data stored as an .ser file on the hard disk.
 */
public class SerialisedAccountListStorage implements AccountListStorage {
    private static final Logger logger = LogsCenter.getLogger(SerialisedAccountListStorage.class);

    private String filePath;

    public SerialisedAccountListStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getAccountListFilePath() {
        return filePath;
    }

    @Override
    public Optional<UniqueAccountList> readAccountList() throws DataConversionException, IOException {
        return readAccountList(filePath);
    }

    @Override
    public Optional<UniqueAccountList> readAccountList(String filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);
        FileInputStream file = new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(file);

        if (!new File(filePath).exists()) {
            logger.info("AccountList file " + filePath + " not found");
            return Optional.empty();
        }

        UniqueAccountList accountList = SerialisedFileStorage.loadDataFromSaveFile(in);
        return Optional.of(accountList);
    }

    @Override
    public void saveAccountList(UniqueAccountList accountList) throws IOException {
        saveAccountList(accountList, filePath);
    }

    @Override
    public void saveAccountList(UniqueAccountList accountList, String filePath) throws IOException {
        requireNonNull(accountList);
        requireNonNull(filePath);

        FileOutputStream file = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(file);
        SerialisedFileStorage.saveDataToFile(out, accountList);
        out.close();
        file.close();
    }
}
