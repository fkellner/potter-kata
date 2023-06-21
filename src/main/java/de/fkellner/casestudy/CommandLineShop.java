package de.fkellner.casestudy;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class CommandLineShop 
{

    public static void main( String[] args )
    {
        ShoppingBasket basket = new ShoppingBasket();
        System.out.println("#######################");
        System.out.println("# Flourish and Blotts #");
        System.out.println("#######################");
        System.out.println("");
        System.out.println("Welcome to our shop!");
        printCatalog();
        System.out.println("~~~~~ Special offer ~~~~~~~~~~~~~~~");
        System.out.println("2 diff. Harry Potter books:  5% off");
        System.out.println("3 diff. Harry Potter books: 10% off");
        System.out.println("4 diff. Harry Potter books: 20% off");
        System.out.println("5 diff. Harry Potter books: 25% off");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        printHelp();

        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.print("> ");
            String input = in.nextLine();
            switch(input) {
                case "show":
                    System.out.println(basket);
                    for(Item item: basket.getItems()) {
                        System.out.println("  " + item);
                    }
                    continue;
                case "buy":
                    System.out.println("Thanks for the " + (basket.getPriceInCents() / 100) + "€");
                    System.out.println("Starting afresh");
                    basket = new ShoppingBasket();
                    continue;
                case "exit":
                    in.close();
                    System.out.println("Bye!");
                    System.exit(0);
                case "catalog":
                    printCatalog();
                    continue;
                case "help":
                    printHelp();
                    continue;
            }
            Book b = Book.getByIsbn(input);
            if(b == null) {
                System.out.println("Sorry, we do not have a book with ISBN '" + input + "'");
                printHelp();
                continue;
            }
            basket.addItem(b);
            System.out.println("Added " + b);
        }
    }

    public static void printCatalog() {
        System.out.println("Here is our catalogue:");
        for(Book book: Book.getAll()) {
            System.out.println("  " + book);
        }
    }

    public static void printHelp() {
        System.out.println();
        System.out.println("- Enter an ISBN to add a book");
        System.out.println("- Type 'show' to see your cart");
        System.out.println("- Type 'buy' to buy and start with a fresh cart");
        System.out.println("- Type 'catalog' to see the catalog");
        System.out.println("- Type 'help' to see this help");
        System.out.println("- Type 'exit' to leave");
    }
}
