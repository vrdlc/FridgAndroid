package com.example.ramon.fridgandroid.models;

import com.example.ramon.fridgandroid.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import org.parceler.Parcel;

import java.util.HashMap;

/**
 * Created by Ramon on 4/30/16.
 */
@Parcel
public class Item {


    private String itemName;
    private String itemQuantity;
    private String itemNotes;
    private String id;
    private String chooseList;
    private HashMap<String, Object> timestampLastChanged;

    public Item() {
    }

    public Item(String item_name, String item_quantity, String item_notes, String list) {
        this.itemName = item_name;
        this.itemQuantity = item_quantity;
        this.itemNotes = item_notes;
        this.chooseList = list;
        HashMap<String, Object> timestampLastChangedObj = new HashMap<>();
        timestampLastChangedObj.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampLastChangedObj;
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

    public String getChooseList(){
        return chooseList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    @JsonIgnore
    public long getTimestampLastChangedLong() {
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }
}
