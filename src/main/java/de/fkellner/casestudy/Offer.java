package de.fkellner.casestudy;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Offer implements Item {
    private static final Set<String> applicableIds = new HashSet<String>(Arrays.asList("1", "2", "3", "4", "5"));
    private Set<String> bookISBNs;
    private Set<Book> books;

    public Offer() {
        this.books = new HashSet<Book>();
        this.bookISBNs = new HashSet<String>();
    }

    public Offer(Collection<Book> books) {
        this.books = new HashSet<Book>();
        this.bookISBNs = new HashSet<String>();
        for(Book book: books) {
            if(!this.addBook(book)) throw new IllegalArgumentException("Offer can not be applied to duplicate books or books not in the Harry Potter Series!");
        }
    }

    public boolean addBook(Book book) {
        String isbn = book.getIsbn();
        if( bookISBNs.contains(isbn) || !applicableIds.contains(isbn) ) return false;

        bookISBNs.add(isbn);
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
        return (int)Math.ceil(price * (1.0f - discount));
    }

    public int getSize() {
        return books.size();
    }

    public static List<Item> apply(List<Item> items) {
        // ### extract all items to which the offer applies
        List<Book> potterBooks = new LinkedList<Book>();
        List<Item> result = new LinkedList<Item>();
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if(item.getClass() == Book.class) {
                Book book = (Book) item;
                if(applicableIds.contains(book.getIsbn())) {
                    potterBooks.add(book);
                } else {
                    result.add(book);
                }
            } else {
                result.add(item);
            }
        }
        // ### find best distribution
        // how often is each book in the basket?
        Map<Book,Integer> amounts = new HashMap<Book, Integer>();
        for(Book book: potterBooks) {
            int amount = amounts.getOrDefault(book, 0);
            amounts.put(book, amount + 1);
        }
        // how many sets of books do we need to fit them
        int minSets = 0;
        for(int v : amounts.values()) {
            if (v > minSets) minSets = v;
        }
        // new heuristic: pack as dense as possible, then combine all 5 and 3 high stacks to 4 - 4
        List<Set<Book>> sets = new LinkedList<Set<Book>>();
        for(int i = 0; i < minSets; i++) {
            sets.add(new HashSet<Book>());
        }
        // pack as densely as possible
        amounts.forEach((isbn, amount) -> {
            for(int i = 0; i < amount; i++) {
                sets.get(i).add(isbn);
            }
        });

        // combine size 3 and 5 stacks where possible
        for(int i = 0; i < sets.size(); i++) {
            Set<Book> curr = sets.get(i);
            if(curr.size() < 5) break;
            for(int j = i + 1; j < sets.size(); j++) {
                Set<Book> smaller = sets.get(j);
                if(smaller.size() > 3) continue;
                if(smaller.size() < 3) break;
                Set<Book> intersection = new HashSet<Book>(curr);
                intersection.removeAll(smaller);
                Book transfer = intersection.iterator().next();
                curr.remove(transfer);
                smaller.add(transfer);
                break;
            }
        }
        // ## re-add best distribution as Offer items
        for(Set<Book> set: sets) {
            result.add(new Offer(set));
        }
        return result;
    }
}
