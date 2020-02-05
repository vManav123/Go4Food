package com.example.go4food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4food.model.Restaurants_Model;
import com.example.go4food.ui.MapActivity;
import com.example.go4food.ui.Recycler_Menu_Adapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ArrayList<String> menu;
    LinearLayout linearLayout;
    RecyclerView recyclerView;

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        AppBarLayout appBarLayout = findViewById(R.id.appbarlayout_id);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        linearLayout = findViewById(R.id.mapbutton);

        //   getSupportActionBar().hide();
        menu = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        String name = getIntent().getExtras().getString("shop_name");
        String categories ="";
        String address=getIntent().getStringExtra("address");
        String rate = getIntent().getExtras().getString("rating");
        String image = getIntent().getExtras().getString("image");
        String city = getIntent().getExtras().getString("city");
        ArrayList<String> cusines = getIntent().getStringArrayListExtra("cusines");

        collapsingToolbarLayout.setTitleEnabled(true);
    for (int i=0;i<cusines.size();i++)
    {
        if(cusines.size()-1==i)
        {
            categories+=cusines.get(i);
        }
        else
        categories += cusines.get(i) + " | ";
    }
        TextView tv_name = findViewById(R.id.aa_anime_name);
        TextView tv_studio = findViewById(R.id.aa_studio);
        TextView tv_categorie = findViewById(R.id.aa_categorie) ;
        TextView tv_rating  = findViewById(R.id.aa_rating) ;
        ImageView shop_img = findViewById(R.id.aa_thumbnail);

        // setting values to each view

        tv_name.setText(name);
        tv_categorie.setText(categories);
        tv_rating.setText(rate);
        tv_studio.setText(city);
        collapsingToolbarLayout.setTitle(name);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        Glide.with(this).load(image).apply(requestOptions).into(shop_img);
        if(isServicesOK())
        {
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailActivity.this, MapActivity.class);
                    intent.putExtra("address",address);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(DetailActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(DetailActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent i = getIntent();
       menu =  i.getStringArrayListExtra("menu");
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DetailActivity.this,2);
                recyclerView.setLayoutManager(layoutManager);
                Recycler_Menu_Adapter recycleviewAdaptor = new Recycler_Menu_Adapter(menu,DetailActivity.this);
                recyclerView.setAdapter(recycleviewAdaptor);

    }
}
