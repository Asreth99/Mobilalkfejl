package com.example.balatontour_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Tours extends AppCompatActivity {
    private static final String LOG_TAG = Tours.class.getName();

    private FirebaseUser user;

    private RecyclerView mRecyclerView;
    private ArrayList<ToursItem> mItemsData;
    private ToursItemAdapter mAdapter;
    private int gridNumber = 1;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours_list);


        // recycle view
        mRecyclerView = findViewById(R.id.recyclerView);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, gridNumber));
        // Initialize the ArrayList that will contain the data.
        mItemsData = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new ToursItemAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);
        // Get the data.




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tours);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.tours:
                    startActivity(new Intent(getApplicationContext(), Tours.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        });

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");
        queryData();
    }


    private void initializeData() {
        // Get the resources from the XML file.
        String[] itemsList = getResources().getStringArray(R.array.tours_names);

        String[] itemsInfo = getResources().getStringArray(R.array.tours_description);

        TypedArray itemsImageResources = getResources().obtainTypedArray(R.array.tours_image);

        // Clear the existing data (to avoid duplication).
        //mItemsData.clear();

        // Create the ArrayList of Sports objects with the titles and
        // information about each sport.
        for (int i = 0; i < itemsList.length; i++) {
            mItems.add(new ToursItem(itemsList[i], itemsInfo[i], itemsImageResources.getResourceId(i, 0)));
        }

        // Recycle the typed array.
        itemsImageResources.recycle();

        // Notify the adapter of the change.
        //mAdapter.notifyDataSetChanged();
    }

    private void queryData() {
        mItemsData.clear();
        mItems.orderBy("name").limit(3).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ToursItem item = document.toObject(ToursItem.class);
                mItemsData.add(item);
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