package de.fkellner.casestudy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for Shopping Basket Pricing Calculation
 */
public class ShoppingBasketTest 
{
    private Book[] availableBooks = new Book[]{
        new Book("1", "Harry Potter and the Philosopher's Stone", 800),
        new Book("2", "Harry Potter and the Chamber of Secrets", 800),
        new Book("3", "Harry Potter and the Prisoner of Azkaban", 800),
        new Book("4", "Harry Potter and the Goblet of Fire", 800),
        new Book("5", "Harry Potter and the Order of the Phoenix", 800)
    };
    /**
     * Test simple Offer logic
     */
    @Test
    public void basicDiscounts() {
        ShoppingBasket basket = new ShoppingBasket(new Book[]{availableBooks[0]});

        assertEquals("1 Book: No Discount", 800, basket.getPriceInCents() );

        basket.addBook(availableBooks[1]);
        assertEquals("2 Books: 5% Discount", 1600 - 80, basket.getPriceInCents() );

        basket.addBook(availableBooks[2]);
        assertEquals("3 Books: 10% Discount", 2400 - 240, basket.getPriceInCents() );

        basket.addBook(availableBooks[3]);
        assertEquals("4 Books: 20% Discount", 3200 - 640, basket.getPriceInCents() );

        basket.addBook(availableBooks[4]);
        assertEquals("5 Books: 25% Discount", 4000 - 1000, basket.getPriceInCents() );
    }

    @Test
    public void givenExample() {
        ShoppingBasket basket = new ShoppingBasket(new Book[]{
            availableBooks[0], availableBooks[0],
            availableBooks[1], availableBooks[1],
            availableBooks[2], availableBooks[2],
            availableBooks[3],
            availableBooks[4]
        });

        assertEquals("Test example: Two sets of four are best", (3200 - 640) * 2, basket.getPriceInCents() );

    }

    @Test
    public void fuzzer() {
        // generate random basket distributions
        final int NUM_TRIES = 10000;
        final int MAX_BASKETS = 8;

        for(int i = 0; i < NUM_TRIES; i++) {
            int numBaskets = (int) Math.ceil(Math.random() * MAX_BASKETS);
            List<Book> allBooks = new LinkedList<Book>();
            int maxPrice = 0;
            String dist = "";
            for(int j = 0; j < numBaskets; j++) {
                ShoppingBasket basket = new ShoppingBasket(new Book[]{});
                int size = 0;
                for(int b = 0; b < Math.random() * availableBooks.length; b++) {
                    allBooks.add(availableBooks[b]);
                    basket.addBook(availableBooks[b]);
                    size++;
                }
                dist += dist == "" ? size : "," + size;
                maxPrice += basket.getPriceInCents();
            }
            int bestPrice = new ShoppingBasket(allBooks.toArray(new Book[]{})).getPriceInCents();
            assertTrue("Best price " + bestPrice + " for given books is as least as good price " + maxPrice + " for distribution: " + dist, bestPrice <= maxPrice );
        }
    }

}
