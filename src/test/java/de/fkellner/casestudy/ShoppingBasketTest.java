package de.fkellner.casestudy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for Shopping Basket Pricing Calculation
 */
public class ShoppingBasketTest 
{
    /**
     * Test simple Offer logic
     */
    @Test
    public void basicDiscounts() {
        ShoppingBasket basket = new ShoppingBasket(Arrays.asList(Book.getByIsbn("1")));

        // the choice is between removing the "magic number" of the book prices
        // (so you can change the book prices and it will still work) and
        // not reimplementing the price calculation logic which is being tested
        // I chose the latter, but am open to discussing it
        assertEquals("1 Book: No Discount", 800, basket.getPriceInCents() );

        basket.addItem(Book.getByIsbn("2"));
        assertEquals("2 Books: 5% Discount", 1600 - 80, basket.getPriceInCents() );

        basket.addItem(Book.getByIsbn("3"));
        assertEquals("3 Books: 10% Discount", 2400 - 240, basket.getPriceInCents() );

        basket.addItem(Book.getByIsbn("4"));
        assertEquals("4 Books: 20% Discount", 3200 - 640, basket.getPriceInCents() );

        basket.addItem(Book.getByIsbn("5"));
        assertEquals("5 Books: 25% Discount", 4000 - 1000, basket.getPriceInCents() );
    }

    @Test
    public void givenExample() {
        ShoppingBasket basket = new ShoppingBasket(Arrays.asList(
            Book.getByIsbn("1"), Book.getByIsbn("1"),
            Book.getByIsbn("2"), Book.getByIsbn("2"),
            Book.getByIsbn("3"), Book.getByIsbn("3"),
            Book.getByIsbn("4"),
            Book.getByIsbn("5")
        ));

        assertEquals("Test example: Two sets of four are best", (3200 - 640) * 2, basket.getPriceInCents() );

    }

    @Test
    public void emptyBasket() {
        ShoppingBasket basket = new ShoppingBasket();
        assertEquals("An empty shopping basket does not cost anything", 0, basket.getPriceInCents() );
    }

    @Test
    public void fuzzer() {
        // generate random basket distributions
        final int NUM_TRIES = 100000;
        final int MAX_BASKETS = 16;
        Book[] availableBooks = Book.getAll();

        long start = System.currentTimeMillis();
        for(int i = 0; i < NUM_TRIES; i++) {
            int numBaskets = (int) Math.ceil(Math.random() * MAX_BASKETS);
            List<Item> allBooks = new LinkedList<Item>();
            int maxPrice = 0;
            String dist = "";
            for(int j = 0; j < numBaskets; j++) {
                ShoppingBasket basket = new ShoppingBasket();
                int size = 0;
                for(int b = 0; b < Math.random() * availableBooks.length; b++) {
                    allBooks.add(availableBooks[b]);
                    basket.addItem(availableBooks[b]);
                    size++;
                }
                dist += dist == "" ? size : "," + size;
                maxPrice += basket.getPriceInCents();
            }
            int bestPrice = new ShoppingBasket(allBooks).getPriceInCents();
            // basic idea: we should at least sometimes hit an optimal distribution
            // otherwise, the implemented distribution should at least not be worse than the given one
            assertTrue("Best price " + bestPrice + " for given books is as least as good as price " + maxPrice + " for distribution: " + dist, bestPrice <= maxPrice );
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        double avg = (double) time / NUM_TRIES;
        System.out.println("Fuzzing " + NUM_TRIES + " took " + time + "ms (avg: " + ((float)(int)(avg * 100) / 100) + "ms)");
    }

}
