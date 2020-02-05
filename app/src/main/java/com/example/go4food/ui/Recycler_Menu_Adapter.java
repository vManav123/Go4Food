package com.example.go4food.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4food.HomeActivity;
import com.example.go4food.ImageSlider;
import com.example.go4food.Notes;
import com.example.go4food.R;
import com.example.go4food.RecycleViewAdaptor;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class Recycler_Menu_Adapter   extends RecyclerView.Adapter<Recycler_Menu_Adapter.ViewHolder>{

    ArrayList<String> list = new ArrayList<>();
    Context context;
    public Recycler_Menu_Adapter(ArrayList<String> list, Context context)
    {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public Recycler_Menu_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_imageview_layout, parent, false);
        return new Recycler_Menu_Adapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Recycler_Menu_Adapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position)).into(holder.imageView);
       holder. itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageSlider.class);
                Log.d("ofdgbnhmjgfndb","aaya ddddddddddddddddd");
                intent.putStringArrayListExtra("menu",list) ;
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);


        }

    }
}
