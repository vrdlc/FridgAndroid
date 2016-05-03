package com.example.ramon.fridgandroid.models;

import org.parceler.Parcel;

/**
 * Created by Ramon on 4/30/16.
 */
@Parcel
public class Item {

    private String item_name;
    private String item_quantity;
    private String item_notes;
    private int id;

    public Item() {

    }

    public Item(String item_name, String item_quantity, String item_notes, int id) {
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.item_notes= item_notes;
        this.id= id;
    }

    public String getItemName() {
        return item_name;
    }

    public String getItemQuantity() {
        return item_quantity;
    }

    public String getItemNotes() {
        return item_notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
