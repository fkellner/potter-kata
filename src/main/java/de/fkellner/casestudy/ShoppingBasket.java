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
        // apply transformations by offer
        List<Item> currentItems = Offer.apply(items); 
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

    public List<Item> getItems() {
        return Offer.apply(items);
    }

    @Override
    public String toString() {
        return "Shopping Basket with " + getItems().size() + " items worth " + (getPriceInCents() / 100) + "€"; 
    }

}
