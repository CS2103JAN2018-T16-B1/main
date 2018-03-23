package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String catalogueFilePath = "data/catalogue.xml";
    private String catalogueBookTitle = "MyCatalogue";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getCatalogueFilePath() {
        return catalogueFilePath;
    }

    public void setCatalogueFilePath(String catalogueFilePath) {
        this.catalogueFilePath = catalogueFilePath;
    }

    public String getCatalogueBookTitle() {
        return catalogueBookTitle;
    }

    public void setCatalogueBookTitle(String catalogueBookTitle) {
        this.catalogueBookTitle = catalogueBookTitle;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(catalogueFilePath, o.catalogueFilePath)
                && Objects.equals(catalogueBookTitle, o.catalogueBookTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, catalogueFilePath, catalogueBookTitle);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + catalogueFilePath);
        sb.append("\nCatalogue name : " + catalogueBookTitle);
        return sb.toString();
    }

}
