# khiayi
###### \data\XmlCatalogueStorageTest\invalidAndValidBookCatalogue.xml
``` xml
    <!-- Valid Book -->
    <books>
        <title>Animal Farm</title>
        <author isPrivate="false">George Orwell</author>
        <isbn isPrivate="false">9780736692427</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
    <!-- Book with invalid isbn field -->
    <books>
        <title>Animal Farm</title>
        <author isPrivate="false">George Orwell</author>
        <isbn isPrivate="false">978073669242a</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
</catalogue>
```
###### \data\XmlCatalogueStorageTest\invalidBookCatalogue.xml
``` xml
    <books>
        <title>Ha!ns Mu@ster</title>
        <author isPrivate="false">Muster Hans</author>
        <isbn isPrivate="false">9482424</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
</catalogue>
```
###### \data\XmlSerializableCatalogueTest\invalidBookCatalogue.xml
``` xml
    <!-- Book with invalid avail field -->
    <books>
        <title>Animal Farm</title>
        <author isPrivate="false">George Orwell</author>
        <isbn isPrivate="false">9780736692427</isbn>
        <avail isPrivate="false">not sure</avail>
    </books>
</catalogue>
```
###### \data\XmlSerializableCatalogueTest\typicalBooksCatalogue.xml
``` xml
<catalogue>
    <books>
        <title>Animal Farm</title>
        <author>George Orwell</author>
        <isbn>9780736692427</isbn>
        <avail>Available</avail>
        <tagged>political</tagged>
        <tagged>satire</tagged>
    </books>
    <books>
        <title>Breaking Dawn</title>
        <author>Stephenie Meyer</author>
        <isbn>9780316067928</isbn>
        <avail>Available</avail>
        <tagged>fiction</tagged>
    </books>
    <books>
        <title>California Girl</title>
        <author>Jefferson Parker</author>
        <isbn>9780060562373</isbn>
        <avail>Available</avail>
        <tagged>unlabelled</tagged>
    </books>
    <books>
        <title>Delirium</title>
        <author>Lauren Oliver</author>
        <isbn>9780061726835</isbn>
        <avail>Available</avail>
    </books>
    <books>
        <title>Emma</title>
        <author>Jane Austen</author>
        <isbn>9780141439587</isbn>
        <avail>Available</avail>
    </books>
    <books>
        <title>Fateful</title>
        <author>Claudia Gray</author>
        <isbn>9780062006202</isbn>
        <avail>Available</avail>
    </books>
    <books>
        <title>Gone Girl</title>
        <author>Gillian Flynn</author>
        <isbn>9780753827666</isbn>
        <avail>Available</avail>
    </books>
    <tagged>political</tagged>
    <tagged>satire</tagged>
    <tagged>fiction</tagged>
    <tagged>unlabelled</tagged>
</catalogue>
```
###### \data\XmlUtilTest\invalidBookField.xml
``` xml
<!-- Book with an invalid isbn field -->
<book>
    <title>Animal Farm</title>
    <author>George Orwell</author>
    <isbn>9482asf424</isbn>
    <avail>Borrowed</avail>
    <tagged>political</tagged>
</book>
```
###### \data\XmlUtilTest\missingBookField.xml
``` xml
<!-- Book with missing title field -->
<book>
    <isbn>9780736692427</isbn>
    <author>George Orwell</author>
    <avail>Borrowed</avail>
    <tagged>political</tagged>
</book>
```
###### \data\XmlUtilTest\validBook.xml
``` xml
<book>
    <title>Animal Farm</title>
    <author>George Orwell</author>
    <isbn>9780736692427</isbn>
    <avail>Borrowed</avail>
    <tagged>political</tagged>
</book>
```
###### \data\XmlUtilTest\validCatalogue.xml
``` xml
<catalogue>
    <books>
        <title>Animal Farm</title>
        <author isPrivate="false">George Orwell</author>
        <isbn isPrivate="false">9780736692427</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
    <books>
        <title>Breaking Dawn</title>
        <author isPrivate="false">Stephenie Meyer</author>
        <isbn isPrivate="false">9780316067928</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
    <books>
        <title>California Girl</title>
        <author isPrivate="false">Jefferson Parker</author>
        <isbn isPrivate="false">9780060562373</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
    <books>
        <title>Delirium</title>
        <author isPrivate="false">Lauren Oliver</author>
        <isbn isPrivate="false">9780061726835</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
    <books>
        <title>Emma</title>
        <author isPrivate="false">Jane Austen</author>
        <isbn isPrivate="false">9780141439587</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
    <books>
        <title>Fateful</title>
        <author isPrivate="false">Claudia Gray</author>
        <isbn isPrivate="false">9780062006202</isbn>
        <avail isPrivate="false">Available</avail>

    </books>
    <books>
        <title>Gone Girl</title>
        <author isPrivate="false">Gillian Flynn</author>
        <isbn isPrivate="false">9780753827666</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
    <books>
        <title>Holes</title>
        <author isPrivate="false">Louis Sachar</author>
        <isbn isPrivate="false">9780439244190</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
    <books>
        <title>Invisible Man</title>
        <author isPrivate="false">Ralph Ellison</author>
        <isbn isPrivate="false">9780140023350</isbn>
        <avail isPrivate="false">Available</avail>
    </books>
</catalogue>
```
###### \java\seedu\address\model\book\AuthorTest.java
``` java
    @Test
    public void isValidAuthor() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Author.isValidAuthor(null));

        // invalid name
        assertFalse(Author.isValidAuthor("")); // empty string
        assertFalse(Author.isValidAuthor(" ")); // spaces only
        assertFalse(Author.isValidAuthor("^")); // only non-alphanumeric characters
        assertFalse(Author.isValidAuthor("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Author.isValidAuthor("peter jack")); // alphabets only
        assertTrue(Author.isValidAuthor("12345")); // numbers only
        assertTrue(Author.isValidAuthor("peter the 2nd")); // alphanumeric characters
        assertTrue(Author.isValidAuthor("Capital Tan")); // with capital letters
        assertTrue(Author.isValidAuthor("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
```
###### \java\seedu\address\model\book\AvailTest.java
``` java
    @Test
    public void isValidAvail() {
        // null avail
        Assert.assertThrows(NullPointerException.class, () -> Avail.isValidAvail(null));

        // blank avail
        assertFalse(Avail.isValidAvail("")); // empty string
        assertFalse(Avail.isValidAvail(" ")); // spaces only

        // valid avail
        assertTrue(Avail.isValidAvail("Reserved"));  // Reserved
        assertTrue(Avail.isValidAvail("Borrowed"));  // Borrowed
        assertTrue(Avail.isValidAvail("Available"));  // Available
    }
}
```
###### \java\seedu\address\model\book\IsbnTest.java
``` java
    @Test
    public void isValidIsbn() {
        // null isbn
        Assert.assertThrows(NullPointerException.class, () -> Isbn.isValidIsbn(null));

        // invalid isbn numbers
        assertFalse(Isbn.isValidIsbn("")); // empty string
        assertFalse(Isbn.isValidIsbn(" ")); // spaces only
        assertFalse(Isbn.isValidIsbn("91")); // less than 13 numbers
        assertFalse(Isbn.isValidIsbn("phone")); // non-numeric
        assertFalse(Isbn.isValidIsbn("978073669242a")); // alphabets within digits
        assertFalse(Isbn.isValidIsbn("9780736 692427")); // spaces within digits
        assertFalse(Isbn.isValidIsbn("97807366924271")); // more than 13 numbers

        // valid isbn numbers
        assertTrue(Isbn.isValidIsbn("9780736692427")); // 13 isbn numbers
    }
}
```
###### \java\seedu\address\model\book\TitleContainsKeywordsPredicateTest.java
``` java
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
```
###### \java\seedu\address\model\book\TitleTest.java
``` java
    @Test
    public void isValidTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("peter jack")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Title.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
```
###### \java\seedu\address\testutil\TypicalBooks.java
``` java
    public static final Book ANIMAL = new BookBuilder().withTitle("Animal Farm")
        .withAuthor("George Orwell")
        .withAvail("Available")
        .withIsbn("9780736692427")
        .withTags("political", "satire").build();
    public static final Book BREAKING = new BookBuilder().withTitle("Breaking Dawn")
        .withAuthor("Stephenie Meyer")
        .withAvail("Available")
        .withIsbn("9780316067928")
        .withTags("fiction").build();
    public static final Book CALIFORNIA = new BookBuilder().withTitle("California Girl")
        .withAuthor("Jefferson Parker")
        .withIsbn("9780060562373")
        .withAvail("Borrowed")
        .withTags("unlabelled").build();
    public static final Book DELIRIUM = new BookBuilder().withTitle("Delirium")
        .withAuthor("Lauren Oliver")
        .withIsbn("9780061726835")
        .withAvail("Borrowed").build();
    public static final Book EMMA = new BookBuilder().withTitle("Emma")
        .withAuthor("Jane Austen")
        .withIsbn("9780141439587")
        .withAvail("Borrowed").build();
    public static final Book FATEFUL = new BookBuilder().withTitle("Fateful")
        .withAuthor("Claudia Gray")
        .withIsbn("9780062006202")
        .withAvail("Available").build();
    public static final Book GONE = new BookBuilder().withTitle("Gone Girl")
        .withAuthor("Gillian Flynn")
        .withIsbn("9780753827666")
        .withAvail("Available").build();

    // Manually added
    public static final Book HOLES = new BookBuilder().withTitle("Holes")
        .withAuthor("Louis Sachar")
        .withIsbn("9780439244190")
        .withAvail("Available").build();
    public static final Book INVISIBLE = new BookBuilder().withTitle("Invisible Man")
        .withAuthor("Ralph Ellison")
        .withIsbn("9780140023350")
        .withAvail("Available").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book XVI = new BookBuilder().withTitle(VALID_TITLE_XVI)
        .withAuthor(VALID_AUTHOR_XVI)
        .withIsbn(VALID_ISBN_XVI)
        .withAvail(VALID_AVAIL_XVI)
        .withTags(VALID_TAG_DYSTOPIA).build();
    public static final Book YOU = new BookBuilder().withTitle(VALID_TITLE_YOU)
        .withAuthor(VALID_AUTHOR_YOU)
        .withIsbn(VALID_ISBN_YOU)
        .withAvail(VALID_AVAIL_YOU)
        .withTags(VALID_TAG_FICTION)
        .build();

    public static final String KEYWORD_MATCHING_GIRL = "Girl"; // A keyword that matches GIRL
    public static final String KEYWORD_MATCHING_BREAKING = "Breaking"; // A keyword that matches BREAKING

```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a book without tags to a non-empty catalogue, command with leading spaces and trailing spaces
         * -> added
         */
        Book toAdd = XVI;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + TITLE_DESC_XVI + "  " + "   " + AUTHOR_DESC_XVI
            + " " + ISBN_DESC_XVI + " "
            + AVAIL_DESC_XVI + "   " + TAG_DESC_DYSTOPIA + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding XVI to the list -> XVI deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding XVI to the list -> XVI added again */
        command = RedoCommand.COMMAND_WORD;
        model.addBook(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a book with all fields same as another book in the catalogue except name -> not added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_YOU).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_YOU + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except isbn -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_YOU).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_YOU + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except name and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_YOU).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn("1111111111111").withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_YOU + AUTHOR_DESC_XVI + " " + PREFIX_ISBN + "1111111111111"
            + AVAIL_DESC_XVI + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except avail -> not added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_YOU
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except avail and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn("2222222222222").withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + " " + PREFIX_ISBN + "2222222222222"
            + AVAIL_DESC_YOU + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except author -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_YOU)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_YOU + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except author and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_YOU)
            .withIsbn("3333333333333").withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_YOU + " " + PREFIX_ISBN + "3333333333333"
            + AVAIL_DESC_XVI + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty catalogue -> added */
        deleteAllBooks();
        assertCommandSuccess(ANIMAL);

        /* Case: add a book with tags, command with parameters in random order -> added */
        toAdd = YOU;
        command = AddCommand.COMMAND_WORD + AUTHOR_DESC_YOU + ISBN_DESC_YOU + TITLE_DESC_YOU
            + TAG_DESC_FICTION + AVAIL_DESC_YOU;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book, missing tags -> added */
        assertCommandSuccess(HOLES);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the book list before adding -> added */
        showBooksWithTitle(KEYWORD_MATCHING_GIRL);
        assertCommandSuccess(INVISIBLE);

        /* ------------------------ Perform add operation while a book card is selected --------------------------- */

        /* Case: selects first card in the book list, add a book -> added, card selection remains unchanged */
        selectBook(Index.fromOneBased(1));
        assertCommandSuccess(CALIFORNIA);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate book -> rejected */
        command = BookUtil.getAddCommand(HOLES);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: add a duplicate book except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalBooks#ANIMAL
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // Catalogue#addBook(Book)
        command = BookUtil.getAddCommand(HOLES) + " " + PREFIX_TAG.getPrefix() + "unlabelled";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing isbn -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing avail -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing author -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + BookUtil.getBookDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_TITLE_DESC + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, Title.MESSAGE_TITLE_CONSTRAINTS);

        /* Case: invalid isbn -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + INVALID_ISBN_DESC + AVAIL_DESC_XVI;
        assertCommandFailure(command, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        /* Case: invalid avail -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + INVALID_AVAIL_DESC;
        assertCommandFailure(command, Avail.MESSAGE_AVAIL_CONSTRAINTS);

        /* Case: invalid author -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + INVALID_AUTHOR_DESC + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, Author.MESSAGE_AUTHOR_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);


    }
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
        /* Case: filtered book list, delete index within bounds of catalogue and book list -> deleted */
        showBooksWithTitle(KEYWORD_MATCHING_BREAKING);
        Index index = INDEX_FIRST_BOOK;
        assertTrue(index.getZeroBased() < getModel().getFilteredBookList().size());
        assertCommandSuccess(index);

        /* Case: filtered book list, delete index within bounds of catalogue but out of bounds of book list
         * -> rejected
         */
        showBooksWithTitle(KEYWORD_MATCHING_BREAKING);
        int invalidIndex = getModel().getCatalogue().getBookList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_BOOK;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + TITLE_DESC_YOU + "  "
            + ISBN_DESC_YOU + " " + AVAIL_DESC_YOU + "  " + AUTHOR_DESC_YOU + " " + TAG_DESC_FICTION + " ";
        Book editedBook = new BookBuilder().withTitle(VALID_TITLE_YOU).withIsbn(VALID_ISBN_YOU)
            .withAvail(VALID_AVAIL_YOU).withAuthor(VALID_AUTHOR_YOU).withTags(VALID_TAG_FICTION).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: undo editing the last book in the list -> last book restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last book in the list -> last book edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateBook(
            getModel().getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()), editedBook);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a book with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_YOU + ISBN_DESC_YOU
            + AVAIL_DESC_YOU + AUTHOR_DESC_YOU + TAG_DESC_FICTION;
        assertCommandSuccess(command, index, YOU);


        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_BOOK;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_DYSTOPIA;
        Book bookToEdit = getModel().getFilteredBookList().get(index.getZeroBased());
        editedBook = new BookBuilder(bookToEdit).withTags(VALID_TAG_DYSTOPIA).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_BOOK;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedBook = new BookBuilder(bookToEdit).withTags().build();
        assertCommandSuccess(command, index, editedBook);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */
        /* Case: filtered book list, edit index within bounds of catalogue and book list -> edited */
        showBooksWithTitle(KEYWORD_MATCHING_GIRL);
        index = INDEX_FIRST_BOOK;
        assertTrue(index.getZeroBased() < getModel().getFilteredBookList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TITLE_DESC_YOU;
        bookToEdit = getModel().getFilteredBookList().get(index.getZeroBased());
        editedBook = new BookBuilder(bookToEdit).withTitle(VALID_TITLE_YOU).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: filtered book list, edit index within bounds of catalogue but out of bounds of book list
         * -> rejected
         */
        showBooksWithTitle(KEYWORD_MATCHING_GIRL);
        int invalidIndex = getModel().getCatalogue().getBookList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_YOU,
            Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a book card is selected -------------------------- */

        /* Case: selects first card in the book list, edit a book -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllBooks();
        index = INDEX_FIRST_BOOK;
        selectBook(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_XVI + ISBN_DESC_XVI
            + AVAIL_DESC_XVI + AUTHOR_DESC_XVI + TAG_DESC_DYSTOPIA;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new book's name
        assertCommandSuccess(command, index, XVI, index);
        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + TITLE_DESC_YOU,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + TITLE_DESC_YOU,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredBookList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_YOU,
            Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + TITLE_DESC_YOU,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased(),
            EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_TITLE_DESC,
            Title.MESSAGE_TITLE_CONSTRAINTS);

        /* Case: invalid isbn -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_ISBN_DESC,
            Isbn.MESSAGE_ISBN_CONSTRAINTS);

        /* Case: invalid avail -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_AVAIL_DESC,
            Avail.MESSAGE_AVAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_AUTHOR_DESC,
            Author.MESSAGE_AUTHOR_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_TAG_DESC,
            Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a book with new values same as another book's values -> rejected */
        executeCommand(BookUtil.getAddCommand(YOU));
        assertTrue(getModel().getCatalogue().getBookList().contains(YOU));
        index = INDEX_FIRST_BOOK;
        assertFalse(getModel().getFilteredBookList().get(index.getZeroBased()).equals(YOU));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_YOU + ISBN_DESC_YOU
            + AVAIL_DESC_YOU + AUTHOR_DESC_YOU + TAG_DESC_DYSTOPIA + TAG_DESC_FICTION;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: edit a book with new values same as another book's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_YOU + ISBN_DESC_YOU
            + AVAIL_DESC_YOU + AUTHOR_DESC_YOU + TAG_DESC_FICTION;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_BOOK);
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find multiple books in catalogue, command with leading spaces and trailing spaces
         * -> 2 books found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA, GONE); // Two titles contains "Girl"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where book list is displaying the books we are finding
         * -> 2 books found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book where book list is not displaying the book we are finding -> 1 book found */
        command = FindCommand.COMMAND_WORD + " California";
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords -> 2 books found */
        command = FindCommand.COMMAND_WORD + " California Gone";
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA, GONE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords in reversed order -> 2 books found */
        command = FindCommand.COMMAND_WORD + " Gone California";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords with 1 repeat -> 2 books found */
        command = FindCommand.COMMAND_WORD + " Gone California Gone";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 matching keywords and 1 non-matching keyword
         * -> 2 books found
         */
        command = FindCommand.COMMAND_WORD + " Gone California NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find same books in catalogue after deleting 1 of them -> 1 book found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getCatalogue().getBookList().contains(CALIFORNIA));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, GONE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is same as name but of different case -> 1 book found */
        command = FindCommand.COMMAND_WORD + " GoNe GiRl";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is substring of name -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Gon";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, name is substring of keyword -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Oliver";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book not in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is substring of author -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Lau";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, author is substring of keyword -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Lauren";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book not in catalogue, author not in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find isbn number of book in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " " + DELIRIUM.getIsbn().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find availability of book in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " " + DELIRIUM.getAvail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of book in catalogue -> 0 books found */
        List<Tag> tags = new ArrayList<>(DELIRIUM.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a book is selected -> selected card deselected */
        showAllBooks();
        selectBook(Index.fromOneBased(1));
        assertFalse(getBookListPanel().getHandleToSelectedCard().getTitle().equals(DELIRIUM.getTitle().fullTitle));
        command = FindCommand.COMMAND_WORD + " Delirium";
        ModelHelper.setFilteredList(expectedModel, DELIRIUM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find book in empty catalogue -> 0 books found */
        deleteAllBooks();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DELIRIUM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Delirium";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }
```
