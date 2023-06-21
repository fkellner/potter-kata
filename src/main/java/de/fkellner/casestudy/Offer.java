package de.fkellner.casestudy;

import java.util.HashSet;
import java.util.Set;

public class Offer {
    private Set<String> bookISBNs;
    private Set<Book> books;

    public Offer() {
        this.books = new HashSet<Book>();
        this.bookISBNs = new HashSet<String>();
    }

    public boolean addBook(Book book) {
        if(bookISBNs.contains(book.getIsbn())) return false;

        bookISBNs.add(book.getIsbn());
        books.add(book);
        return true;
    }

    public int getPriceInCents() {
        float discount = 0;
        switch(books.size()) {
            case 0:
            case 1:
                break;
            case 2:
                discount = 0.05f;
                break;
            case 3:
                discount = 0.10f;
                break;
            case 4:
                discount = 0.20f;
                break;
            case 5:
            default: // if there are more books...
                discount = 0.25f;
                break;
        }
        int price = 0;
        for(Book book: books) {
            price += book.getPriceInCents();
        }
        return (int)Math.floor(price * (1.0f - discount));
    }
}
