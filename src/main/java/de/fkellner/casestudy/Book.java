package de.fkellner.casestudy;

public class Book implements Item {
    private int priceInCents;
    private String title;
    private String isbn;

    public Book(String isbn, String title, int priceInCents) {
        this.isbn = isbn;
        this.title = title;
        this.priceInCents = priceInCents;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
	public int hashCode() {
		return isbn.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return this.getIsbn() == ((Book)obj).getIsbn();
	}

    @Override
    public String toString() {
        return "ISBN " + isbn + ": " + title + " (" + (priceInCents / 100) + "€)";
    }

    // ######## mock database #########
    private static Book[] allBooks = new Book[]{
            new Book("1", "Harry Potter and the Philosopher's Stone", 800),
            new Book("2", "Harry Potter and the Chamber of Secrets", 800),
            new Book("3", "Harry Potter and the Prisoner of Azkaban", 800),
            new Book("4", "Harry Potter and the Goblet of Fire", 800),
            new Book("5", "Harry Potter and the Order of the Phoenix", 800),
            new Book("42", "The Hitchhiker's Guide to the Galaxy", 600)
        };

    public static Book[] getAll() {
        return allBooks;
    }

    public static Book getByIsbn(String isbn) {
        for(Book b : allBooks) {
            if(b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }
}
