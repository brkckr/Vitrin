package com.bullseyedevs.vitrin.model;

/**
 * Created by brkckr on 23.11.2017.
 */

public class Order
{
    public Item item;
    public int quantity;
    public double extendedPrice = 0.0;

    public Order(Item item, int quantity)
    {
        this.item = item;
        this.quantity = quantity;
        this.extendedPrice = item.unitPrice * quantity;
    }
}
