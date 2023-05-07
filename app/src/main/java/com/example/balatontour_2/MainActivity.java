package com.example.balatontour_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    private RecyclerView mRecyclerView;
    private ArrayList<MainActivityItem> mItemsData;
    private MainActivityItemAdapter mAdapter;

    private NotificationHelper notificationHelper;
    private int gridNumber = 1;
    private FirebaseFirestore mFirestore2;
    private CollectionReference mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            Log.i(LOG_TAG, "Authenticated User, The email: "+ user.getEmail());

        }else {
            Log.i(LOG_TAG, "Unauthenticated User!");
        }

        // recycle view
        mRecyclerView = findViewById(R.id.EventRecyclerView);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, gridNumber));
        // Initialize the ArrayList that will contain the data.
        mItemsData = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new MainActivityItemAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.tours:
                    startActivity(new Intent(getApplicationContext(), Tours.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return true;


            }

            return false;
        });

        mFirestore2 = FirebaseFirestore.getInstance();
        mItem = mFirestore2.collection("Events");
        //initializeData();
        queryData();

        notificationHelper = new NotificationHelper(this);
    }
    private void initializeData() {


        // Get the resources from the XML file.
        String[] dateListStart = getResources().getStringArray(R.array.event_dates);
        String[] dateListHypen = getResources().getStringArray(R.array.event_hypen);
        String[] dateListEnd = getResources().getStringArray(R.array.event_dates2);


        String[] nameList = getResources().getStringArray(R.array.event_names);

        TypedArray itemsImageResources = getResources().obtainTypedArray(R.array.event_image);


        for (int i = 0; i < dateListStart.length; i++) {
            mItem.add(new MainActivityItem(dateListStart[i],dateListEnd[i],dateListHypen[i], nameList[i], itemsImageResources.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        itemsImageResources.recycle();


    }

    private void queryData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        mItemsData.clear();
        mItem.orderBy("eventDate").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                MainActivityItem item = document.toObject(MainActivityItem.class);
                mItemsData.add(item);
            }

            for(MainActivityItem item : mItemsData){
                LocalDate date = LocalDate.parse(item.getEventDate(), formatter);
                if (date.isEqual(LocalDate.now())){
                    notificationHelper.send("The "+item.getEventTitle()+" starts today!");



                }
            }

            if (mItemsData.size() == 0) {
                initializeData();
                queryData();
            }

            // Notify the adapter of the change.
            mAdapter.notifyDataSetChanged();
        });


    }



}