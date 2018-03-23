package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;

/**
 * An Immutable Catalogue that is serializable to XML format
 */
@XmlRootElement(name = "catalogue")
public class XmlSerializableCatalogue {

    @XmlElement
    private List<XmlAdaptedBook> books;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableCatalogue.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCatalogue() {
        books = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCatalogue(ReadOnlyCatalogue src) {
        this();
        books.addAll(src.getBookList().stream().map(XmlAdaptedBook::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this catalogue into the model's {@code Catalogue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedBook} or {@code XmlAdaptedTag}.
     */
    public Catalogue toModelType() throws IllegalValueException {
        Catalogue catalogue = new Catalogue();
        for (XmlAdaptedTag t : tags) {
            catalogue.addTag(t.toModelType());
        }
        for (XmlAdaptedBook p : books) {
            catalogue.addBook(p.toModelType());
        }
        return catalogue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCatalogue)) {
            return false;
        }

        XmlSerializableCatalogue otherAb = (XmlSerializableCatalogue) other;
        return books.equals(otherAb.books) && tags.equals(otherAb.tags);
    }
}
