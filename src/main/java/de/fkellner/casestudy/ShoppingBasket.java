package de.fkellner.casestudy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ShoppingBasket {
    private List<Item> items;

    public ShoppingBasket() {
        this.items = new LinkedList<Item>();
    }
    public ShoppingBasket(Collection<Item> items) {
        this.items = new LinkedList<Item>();
        this.items.addAll(items);
    }

    public int getPriceInCents() {
        // create a copy for modification
        List<Item> currentItems = new LinkedList<Item>(items);
        // apply transformations by offer
        currentItems = Offer.apply(currentItems); 
        // sum up
        int sum = 0;
        for(Item item: currentItems) {
            sum += item.getPriceInCents();
        }
        return sum;

    }

    public void addItem(Item item) {
        this.items.add(item);
    }

}
