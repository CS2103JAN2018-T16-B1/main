package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides a handle to a book card in the book list panel.
 */
public class BookCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String AUTHOR_FIELD_ID = "#author";
    private static final String ISBN_FIELD_ID = "#isbn";
    private static final String AVAIL_FIELD_ID = "#avail";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label authorLabel;
    private final Label isbnLabel;
    private final Label availLabel;
    private final List<Label> tagLabels;

    public BookCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.authorLabel = getChildNode(AUTHOR_FIELD_ID);
        this.isbnLabel = getChildNode(ISBN_FIELD_ID);
        this.availLabel = getChildNode(AVAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
            .getChildrenUnmodifiable()
            .stream()
            .map(Label.class::cast)
            .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getAuthor() {
        return authorLabel.getText();
    }

    public String getIsbn() {
        return isbnLabel.getText();
    }

    public String getAvail() {
        return availLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
            .stream()
            .map(Label::getText)
            .collect(Collectors.toList());
    }
}
