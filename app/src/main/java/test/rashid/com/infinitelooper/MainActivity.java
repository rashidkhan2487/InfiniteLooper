package test.rashid.com.infinitelooper;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MainActivityViewModel mainActivityViewModel;

    // chunk of next data to calculate
    static int offSet = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiating the ViewModel class and bounding it to this activity.
        // This make viewmodel to react according to this life cycle of this activty
         mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        // Setting the intital Value
         mainActivityViewModel.setInitialValue();
         // Collection of an Item to show in the recycler
         ArrayList<ListItem> itemList = new ArrayList<>();
        ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(R.layout.list_item, itemList);
        recyclerView = findViewById(R.id.item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);

        // Initiating BottomreachedListener to check if the recyclerview reach to the bottom of the application
        itemArrayAdapter.setOnBottomReachedListener(position -> {
            // Worker thread to perform the febonacci operation and update the livedata
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                mainActivityViewModel.fibonacciSeriesFromStartToEnd(offSet + 1, offSet + 5);
                offSet = offSet + 5;
            });
        });


            // Observing the observable to update the UI
        mainActivityViewModel.initializeObservable().observe(this, item ->
                UpdateUi(itemList, itemArrayAdapter, item));

    }

    private void UpdateUi(ArrayList<ListItem> itemList, ItemArrayAdapter itemArrayAdapter, String item) {
        itemList.add(new ListItem( item));
        itemArrayAdapter.notifyDataSetChanged();
        itemArrayAdapter.notifyItemInserted(itemList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Intializing the app with first 10 number of fibonacci series and gradually increasing it to 5 more on scrolling
        mainActivityViewModel.fibonacciSeriesFromStartToEnd(offSet + 1, offSet + 10);
        offSet = offSet + 10;
    }




}
