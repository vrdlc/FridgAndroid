package com.example.ramon.fridgandroid.models;

/**
 * Created by Ramon on 4/30/16.
 */
public class Item {

    private String item_name, item_quantity, item_notes;
    private int id;

    public Item() {
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

    public void setItemName(String item_name) {
        this.item_name = item_name;
    }

    public void setItemQuantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }

    public void setItemNotes(String item_notes) {
        this.item_notes = item_notes;
    }

    public void setId(int id) {
        this.id = id;
    }
}
