package de.fkellner.casestudy;

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

        int[] sets = new int[minSets];
        for(int i = 0; i < minSets; i++) {
            sets[i] = 0;
        }
        // brute-force this recursively
        Integer[] counts = amounts.values().toArray(new Integer[]{});
        int[] c = new int[counts.length];
        for(int i = 0; i < c.length; i++) {
            c[i] = counts[i];
        }
        return bestPrice(sets, 0, 0, c);

    }
    private int bestPrice(int[] sets, int pos, int leftEls, int[] amounts) {
        if(leftEls == 0 && amounts.length == 0) {
            // we are done, calculate price
            int sum = 0;
            for(int i = 0; i < sets.length; i++) {
                sum += getDiscountedPrice(sets[i]);
            }
            return sum;
        }
        if(leftEls == 0) {
            // take one element, leave rest
            int curr = amounts[0];
            int[] remaining = new int[amounts.length - 1];
            for(int i = 0; i < remaining.length; i++) {
                remaining[i] = amounts[i + 1];
            }
            return bestPrice(sets, 0, curr, remaining);
        }
        leftEls--;
        int best = Integer.MAX_VALUE;        
        // try all possibilities to distribute it
        for(int i = pos; i < sets.length - leftEls; i++) {
            int[] newSet = new int[sets.length];
            for(int s = 0; s < sets.length; s++) {
                newSet[s] = sets[s];
            }
            newSet[i]++;
            int price = bestPrice(newSet, i + 1, leftEls, amounts);
            if(price < best) best = price;
        }
        return best;
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
}
