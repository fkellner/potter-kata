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
        // how often is each book in the basket?
        Map<String,Integer> amounts = new HashMap<String, Integer>();
        for(Book book: books) {
            int amount = amounts.getOrDefault(book.getIsbn(), 0);
            amounts.put(book.getIsbn(), amount + 1);
        }
        // how many sets of books do we need to fit them
        int minSets = 0;
        for(int v : amounts.values()) {
            if (v > minSets) minSets = v;
        }
        Offer[] sets = new Offer[minSets];
        for(int i = 0; i < sets.length; i++) {
            sets[i] = new Offer();
        }
        // distribute books evenly among sets
        List<Book> remainingBooks = new LinkedList<Book>(books);
        while(remainingBooks.size() > 0) {
            for(int i = 0; i < sets.length; i++) {
                if(remainingBooks.size() == 0) break;
                Offer set = sets[i];
                for(int j = 0; j < remainingBooks.size(); j++) {
                    if(set.addBook(remainingBooks.get(j))) {
                        remainingBooks.remove(j);
                        break;
                    }
                }
            }
        }
        // calculate price        
        int sum = 0;
        String[] sizes = new String[sets.length];
        for(int i = 0; i < sets.length; i++) {
            sizes[i] = sets[i].getSize() + "";
            sum += sets[i].getPriceInCents();
        }
        System.out.println("Distribution: " + String.join(",", sizes));
        return sum;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
