package de.fkellner.casestudy;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Book[] availableBooks = new Book[]{
        new Book("1", "Harry Potter and the Philosopher's Stone", 800),
        new Book("2", "Harry Potter and the Chamber of Secrets", 800),
        new Book("3", "Harry Potter and the Prisoner of Azkaban", 800),
        new Book("4", "Harry Potter and the Goblet of Fire", 800),
        new Book("5", "Harry Potter and the Order of the Phoenix", 800)
    };

    public static void main( String[] args )
    {
        ShoppingBasket basket = new ShoppingBasket(Arrays.asList(
            availableBooks[0], availableBooks[0],
            availableBooks[1], availableBooks[1],
            availableBooks[2], availableBooks[2],
            availableBooks[3],
            availableBooks[4]
        ));
        System.out.println( "Basket costs " + basket.getPriceInCents() );
    }
}
