package delacruz.fridg30;

/**
 * Created by Ramon on 7/18/16.
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}