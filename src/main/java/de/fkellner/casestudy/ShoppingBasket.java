package de.fkellner.casestudy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShoppingBasket {
    private List<Book> books;

    public ShoppingBasket(Book[] books) {
        this.books = new LinkedList<Book>();
        for(Book book : books) {
            this.books.add(book);
        }
    }

    public int getPriceInCents() {
        // get the best price by brute-force-distributing books among the offer
        // Map<String,Integer> amounts = new HashMap<String, Integer>();
        // for(Book book: books) {
        //     int amount = amounts.getOrDefault(book.getIsbn(), 0);
        //     amounts.put(book.getIsbn(), amount + 1);
        // }
        // int minSets = 0;
        int sum = 0;
        for(Book book : books) {
            sum += book.getPriceInCents();
        }
        return sum;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
