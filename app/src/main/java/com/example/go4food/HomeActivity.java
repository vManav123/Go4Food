package com.example.go4food;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseUser currentuser;
    FirebaseAuth firebaseAuth;

    ImageView imageView;
    TextView name,email;
    Button North_Indian, Fast_Food , Mexican, American, Italian , European, Chinese , Continental, Street_Food, Cafe , Bakery , South_Indian , Mughlai , sweets , Japanese, Thai, Malaysian, Burmese, Asian,search;


    boolean selected_boolean [] ;
    ArrayList<Button> Buttons_items= new ArrayList();
    String selected_cities;
    ArrayList <String> intrest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        North_Indian=findViewById(R.id.northindian);
        Fast_Food=findViewById(R.id.fastfood);
        Mexican=findViewById(R.id.mexican);
        American=findViewById(R.id.american);
        Italian=findViewById(R.id.italian);
        European=findViewById(R.id.european);
        Chinese=findViewById(R.id.chinese);
        Continental=findViewById(R.id.contienental);
        Street_Food=findViewById(R.id.streetfood);
        Cafe=findViewById(R.id.cafe);
        Bakery=findViewById(R.id.bakery);
        South_Indian=findViewById(R.id.southindian);
        Mughlai=findViewById(R.id.mughlai);
        sweets=findViewById(R.id.sweet);
        Japanese=findViewById(R.id.japanese);
        Thai=findViewById(R.id.thai);
        Malaysian=findViewById(R.id.malasian);
        Burmese=findViewById(R.id.burmese);
        Asian=findViewById(R.id.asiant);
    search = findViewById(R.id.search);
   // name = findViewById(R.id.username);

        Buttons_items.add(North_Indian);
        Buttons_items.add(Fast_Food);
        Buttons_items.add(Mexican);
        Buttons_items.add(American);
        Buttons_items.add(Italian);
        Buttons_items.add(European);
        Buttons_items.add(Chinese);
        Buttons_items.add(Continental);
        Buttons_items.add(Street_Food);
        Buttons_items.add(Cafe);
        Buttons_items.add(Bakery);
        Buttons_items.add(South_Indian);
        Buttons_items.add(Mughlai);
        Buttons_items.add(sweets);
        Buttons_items.add(Japanese);
        Buttons_items.add(Thai);
        Buttons_items.add(Malaysian);
        Buttons_items.add(Burmese);
        Buttons_items.add(Asian);
        selected_boolean = new boolean[Buttons_items.size()];
        for(int i=0;i<Buttons_items.size();i++)
        {
            selected_boolean[i] = false;
        }
        intrest = new ArrayList<>();
        for(int i=0;i<Buttons_items.size();i++)
        {
            int finalI = i;
            Buttons_items.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selected_boolean[finalI]){

                        Buttons_items.get(finalI).setBackground(getDrawable(R.drawable.hollowround));
                        Buttons_items.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                        selected_boolean[finalI] = false;
                        intrest.remove(Buttons_items.get(finalI).getText().toString());
                        Log.d("updated selected ", String.valueOf(intrest));
                    }
                    else
                    {
                        Buttons_items.get(finalI).setTextColor(Color.WHITE);
                        Buttons_items.get(finalI).setBackground(getDrawable(R.drawable.roundbutton));
                        selected_boolean[finalI] = true;
                        intrest.add(Buttons_items.get(finalI).getText().toString());
                    Log.d("selected ", String.valueOf(intrest));
                    }
                }
            });
        }

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        //    google signIn //

        // database  //
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,DatabaseActivity.class);
                intent.putStringArrayListExtra("intrest",intrest);
                intent.putExtra("selected_cities",selected_cities);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button select_cities;
        select_cities  = findViewById(R.id.city);
        select_cities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this,select_cities);
                                popupMenu.getMenuInflater().inflate(R.menu.cities,popupMenu.getMenu());
                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        selected_cities = String.valueOf(item.getTitle());
                                    select_cities.setText(item.getTitle());
                                                return true;
                                    }
                                });
                                popupMenu.show();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        TextView textUsername = header.findViewById(R.id.username);
        TextView email = header.findViewById(R.id.emailid);
        if (acct != null) {
            textUsername.setText(acct.getDisplayName());
            email.setText(acct.getEmail());

        }
        else if(firebaseAuth.getCurrentUser()!=null)
        {
            textUsername.setText(firebaseAuth.getCurrentUser().getDisplayName());
            email.setText(firebaseAuth.getCurrentUser().getEmail());
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(HomeActivity.this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();
      //  name.setText("yash");
        navigationView.setNavigationItemSelectedListener(this);
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_logout)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//        navigationView.setNavigationItemSelectedListener(this);
//        updatenavheader();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        int id = item.getItemId();
        if(id==R.id.nav_home)
        {
            //getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
            startActivity(new Intent(HomeActivity.this,HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        }
        else if(id==R.id.nav_profile)
        {
            //getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();
            startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
        else if(id==R.id.nav_logout)
        {
            firebaseAuth.getInstance().signOut();
            GoogleSignInClient mGoogleSignInClient;
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut();
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getApplicationContext(),FrontActivity.class);
            Intent intent = new Intent(HomeActivity.this,FrontActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
