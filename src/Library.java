/**
 * This class represents a Library, which contains a list of patrons,
 * list of books, maximum capacities of books, patrons, and borrowed books.
 */
public class Library {

    /** Maximum number of books in the library */
    int maxBookCapacity;

    /** Maximum number of simultanius borrows
     * for each patron. */
    int maxBorrowedBooks;

    /** Maximum number of patrons in the library */
    int maxPatronCapacity;

    /** List of Patron objects */
    Patron[] patronList;

    /** List of Books in library */
    Book[] bookList;

    /**
     * Creates a new library with the given parameters.
     * @param maxBookCapacity The maximal number of books this
     *                        library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows a single
     *                         patron to borrow at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this
     *                          library can handle.
     */
    Library(int maxBookCapacity,
            int maxBorrowedBooks,
            int maxPatronCapacity) {
        this.maxBookCapacity = maxBookCapacity;
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.maxPatronCapacity = maxPatronCapacity;

        this.bookList = new Book[maxBookCapacity];
        this.patronList = new Patron[maxPatronCapacity];
    }

    /**
     * Adds the given book to this library, if there is place available,
     * and it isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was
     * successfully added, or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        // Check if the book already exists
        int i = 0;
        while (i < this.bookList.length && this.bookList[i] != null) {
            // If the book exists, return the id of it
            if (this.bookList[i] == book) {
                return i;
            }

            i++;
        }

        // Now i contains the first empty place in bookList
        if (i >= this.bookList.length) {
            return -1;
        }

        this.bookList[i] = book;
        return i;
    }

    /**
     * Returns the number of books that a specific
     * patronId borrowed. If he didn't borrow any
     * books or the patronId is not valid, will return 0.
     * @param patronId The id of the Patron
     * @return number of books borrowed, 0 if none or
     * if the patronId isn't valid
     */
    int bookBorrowedForPatronId(int patronId) {
        // Check if the patronId exists in array
        if (!this.isPatronIdValid(patronId)) {
            return 0;
        }

        // Count how many books has borrowedId that
        // is equal to patronId
        int borrowed = 0;
        int i = 0;
        while (i < this.bookList.length && this.bookList[i] != null) {
            if (this.bookList[i].getCurrentBorrowerId() == patronId) {
                borrowed += 1;
            }

            i++;
        }

        return borrowed;
    }

    /**
     * Marks the book with the given id number as borrowed by the
     * patron with the given patron id, if this book is available,
     * the given patron isn't already borrowing the maximal number of
     * books allowed, and if the patron will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if (!this.isPatronIdValid(patronId)) {
            return false;
        }

        if (!this.isBookIdValid(bookId)) {
            return false;
        }

        Patron patron = this.patronList[patronId];
        Book book = this.bookList[bookId];

        // Fail if patron will not enjoy the book
        if (!patron.willEnjoyBook(book)) {
            return false;
        }

        int borrowed = this.bookBorrowedForPatronId(patronId);

        // If the patron already borrowed maximum number
        // of books, fail
        if (borrowed == this.maxBorrowedBooks) {
            return false;
        }

        // If the book is not available, fail
        if (!this.isBookAvailable(bookId)) {
            return false;
        }

        // Set the book as borrowed by patronId
        book.setBorrowerId(patronId);

        return true;
    }

    /**
     * Returns the non-negative id number of the given book
     * if he is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book
     * if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        int i = 0;
        while (i < this.bookList.length && this.bookList[i] != null) {
            // If we found same book, return
            // the index of find.
            if (this.bookList[i] == book) {
                return i;
            }

            i++;
        }

        // We didn't find same book
        return -1;
    }

    /**
     * Returns the non-negative id number of the given patron
     * if he is registered to this library, -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron
     * if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        int i = 0;
        while (i < this.patronList.length && this.patronList[i] != null) {
            // If we found same patron, return
            // the index of the find.
            if (this.patronList[i] == patron) {
                return i;
            }

            i++;
        }

        // We didn't find the patron.
        return -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if (!this.isBookIdValid(bookId)) {
            return false;
        }

        Book book = this.bookList[bookId];

        return book.getCurrentBorrowerId() == -1;
    }

    /**
     * Returns true if the given number is an id of some
     * book in the library, false otherwise.
     * @param bookId The id to check.
     * @return true if the given number is an id of
     * some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        if (bookId >= this.maxBookCapacity) {
            return false;
        }

        return this.bookList[bookId] != null;
    }

    /**
     * Returns true if the given number is an id of
     * a patron in the library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of
     * a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        if (patronId >= this.maxPatronCapacity) {
            return false;
        }

        return this.patronList[patronId] != null;
    }

    /**
     * Registers the given Patron to this library,
     * if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was
     * a spot and the patron was successfully registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        // Check if the patron already exists
        int i = 0;
        while (i < this.patronList.length && this.patronList[i] != null) {
            // If the patron exists, return the id
            if (this.patronList[i] == patron) {
                return i;
            }

            i++;
        }

        // Now i contains the first empty place in bookList
        if (i >= this.patronList.length) {
            return -1;
        }

        this.patronList[i] = patron;
        return i;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (!this.isBookIdValid(bookId)) {
            return;
        }

        // Call returnBook method on Book class
        this.bookList[bookId].returnBook();
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most,
     * out of all available books he will enjoy, if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given will enjoy the most.
     * Null if no book is available.
     */
    Book suggestBookToPatron(int patronId) {
        if (!isPatronIdValid(patronId)) {
            return null;
        }

        Patron patron = this.patronList[patronId];

        // Try all available books until we find
        // a book that the patron will enjoy.
        int i = 0;
        while (i < this.bookList.length && this.bookList[i] != null) {
            if (!this.isBookAvailable(i)) {
                i++;
                continue;
            }

            Book book = this.bookList[i];

            if (patron.willEnjoyBook(book)) {
                return this.bookList[i];
            }

            i++;
        }

        return null;
    }
}
