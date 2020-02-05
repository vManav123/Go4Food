package com.example.go4food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.go4food.model.Restaurants_Model;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

public class DatabaseActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView mRecyclerview;
    String selected_cities;
    ArrayList<String> list = new ArrayList<>();

    ArrayList<Restaurants_Model> list1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        shimmerFrameLayout =findViewById(R.id.shimmer_layout);
        Intent i = getIntent();
        list = i.getStringArrayListExtra("intrest");
        list = i.getStringArrayListExtra("cusines");
        selected_cities = i.getStringExtra("selected_cities");
        Log.d("selected ", String.valueOf(list));
        setmRecyclerview();
        readdataffromFirebase();
    }


    private void readdataffromFirebase() {

        db.collection("Shopes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                            }
                        } else {
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    private void setmRecyclerview() {
        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        CollectionReference collectionReference;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Shopes");
        collectionReference.whereEqualTo("city",selected_cities)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                list1.clear();
                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots)
                {

                    Restaurants_Model restaurants_model = queryDocumentSnapshot.toObject(Restaurants_Model.class);
                //    Log.d("data",restaurants_model.getCity() + " " + restaurants_model.getShop_name());

                    list1.add(restaurants_model);
               //    cusines =  restaurants_model.getCuisines();
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DatabaseActivity.this);
                mRecyclerview.setLayoutManager(layoutManager);
                RecycleviewAdaptor recycleviewAdaptor = new RecycleviewAdaptor(DatabaseActivity.this,list1);
                mRecyclerview.setAdapter(recycleviewAdaptor);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                mRecyclerview.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.startShimmer();
    }
}
