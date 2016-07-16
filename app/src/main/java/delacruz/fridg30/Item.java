package delacruz.fridg30;

import com.google.firebase.database.ServerValue;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;

/**
 * Created by Ramon on 7/13/16.
 */
public class Item {

    private String itemName;
    private String itemQuantity;
    private String itemNotes;
    private String id;
    private String chooseList;
    private String pushId;
//    private HashMap<String, Object> timestampLastChanged;

    public Item() {
    }

    public Item(String item_name, String item_quantity, String item_notes, String list) {
        this.itemName = item_name;
        this.itemQuantity = item_quantity;
        this.itemNotes = item_notes;
        this.chooseList = list;
        HashMap<String, Object> timestampLastChangedObj = new HashMap<>();
        timestampLastChangedObj.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
//        this.timestampLastChanged = timestampLastChangedObj;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setItemNotes(String itemNotes) {
        this.itemNotes = itemNotes;
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

    public void setChooseList(String chooseList) {
        this.chooseList = chooseList;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public HashMap<String, Object> getTimestampLastChanged() {
//        return timestampLastChanged;
//    }

//    @JsonIgnore //Old app didn't have a Jackson dependency, so how was this working? And do I need it to get timestamp?
//    public long getTimestampLastChangedLong() {
//        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
//    }
}
