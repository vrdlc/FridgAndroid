package com.example.ramon.fridgandroid.models;

import org.parceler.Parcel;

/**
 * Created by Ramon on 4/30/16.
 */
@Parcel
public class Item {

    private String itemName;
    private String itemQuantity;
    private String itemNotes;
    private String id;

    public Item() {
    }

    public Item(String item_name, String item_quantity, String item_notes) {
        this.itemName = item_name;
        this.itemQuantity = item_quantity;
        this.itemNotes = item_notes;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public String getItemNotes() {
        return itemNotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
