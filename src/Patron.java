/**
 * This class represents a Patron, with has a first and last name,
 * comic, dramatic and educational tendencies, and enjoyment threshold.
 */
public class Patron {

    /** First name of Patron */
    String firstName;

    /** Last name of the Patron */
    String lastName;

    /** Comic tendency of Patron */
    int comicTendency;

    /** Dramatic tendency of Patron */
    int dramaticTendency;

    /** Educational tendency of Patron */
    int educationalTendency;

    /** The enjoyment threshold of Patron */
    int enjoymentThreshold;

    /**
     * Creates a new patron with the given characteristics.
     * @param patronFirstName
     * @param patronLastName
     * @param comicTendency
     * @param dramaticTendency
     * @param educationalTendency
     * @param patronEnjoymentThreshold
     */
    Patron(String patronFirstName,
           String patronLastName,
           int comicTendency,
           int dramaticTendency,
           int educationalTendency,
           int patronEnjoymentThreshold) {
        this.firstName = patronFirstName;
        this.lastName = patronLastName;
        this.comicTendency = comicTendency;
        this.dramaticTendency = dramaticTendency;
        this.educationalTendency = educationalTendency;
        this.enjoymentThreshold = patronEnjoymentThreshold;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * @param book The book to asses.
     * @return the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        return this.comicTendency * book.getComicValue()
                + this.dramaticTendency * book.getDramaticValue()
                + this.educationalTendency * book.getEducationalValue();
    }

    /**
     * Returns a string representation of the patron,
     * which is a sequence of its first and last name,
     * separated by a single white space.
     * @return the String representation of this patron.
     */
    String stringRepresentation() {
        return firstName + " " + lastName;
    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     * @param book The book to asses.
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return this.getBookScore(book) >= this.enjoymentThreshold;
    }
}
