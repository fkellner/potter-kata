package de.fkellner.casestudy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        // new heuristic: pack as dense as possible, then combine all 5 and 3 high stacks to 4 - 4
        List<Set<String>> sets = new LinkedList<Set<String>>();
        for(int i = 0; i < minSets; i++) {
            sets.add(new HashSet<String>());
        }
        // pack as densely as possible
        amounts.forEach((isbn, amount) -> {
            for(int i = 0; i < amount; i++) {
                sets.get(i).add(isbn);
            }
        });

        // combine size 3 and 5 stacks where possible
        for(int i = 0; i < sets.size(); i++) {
            Set<String> curr = sets.get(i);
            if(curr.size() < 5) break;
            for(int j = i + 1; j < sets.size(); j++) {
                Set<String> smaller = sets.get(j);
                if(smaller.size() > 3) continue;
                if(smaller.size() < 3) break;
                Set<String> intersection = new HashSet<String>(curr);
                intersection.removeAll(smaller);
                String transfer = intersection.iterator().next();
                curr.remove(transfer);
                smaller.add(transfer);
                break;
            }
        }
        int sum = 0;
        for(Set<String> set: sets) {
            sum += getDiscountedPrice(set.size());
        }
        return sum;

    }

    private static int getDiscountedPrice(int numBooks) {
        int pricePerBook = 800;
        float discount = 0;
        switch(numBooks) {
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
            default:
                discount = 0.25f;
                break;
        }
        return (int) Math.ceil((1.0f - discount) * numBooks * pricePerBook);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public static void printHelp() {
        // debugging
        System.out.println("-----------------");
        for(int i = 2; i <= 10; i++) {
            System.out.println("## Distributing " + i + " to 2 stacks:");
            for(int s1 = i - 5; s1 < i && s1 < 5; s1++) {
                int s2 = i - s1;
                int price = getDiscountedPrice(s1) + getDiscountedPrice(s2);
                System.out.println(s1 + " and " + s2 + ": " + price);
            }
        }
        System.out.println("-----------------");
    }
}
