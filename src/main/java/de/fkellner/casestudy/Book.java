package de.fkellner.casestudy;

public class Book {
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
}
