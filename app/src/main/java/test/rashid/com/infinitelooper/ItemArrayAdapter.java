package test.rashid.com.infinitelooper;

/**
 * Created by rashi on 2019-01-23.
 */
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class ItemArrayAdapter extends RecyclerView.Adapter<ItemArrayAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<ListItem> itemList;

    // Create an instance of interface to invoke a listener when recycler reach end of the list
    private OnBottomReachedListener onBottomReachedListener;

    ItemArrayAdapter(int layoutId, ArrayList<ListItem> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        return new ViewHolder(view);
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {
        TextView item = holder.item;
        item.setText(itemList.get(listPosition).getNumber());

        //When second last position reach invoke the listener
        if (listPosition == itemList.size() - 1){
            onBottomReachedListener.onBottomReached(listPosition);

        }
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        ViewHolder(View itemView) {
            super(itemView);
            item =  itemView.findViewById(R.id.row_item);
        }

    }
}
