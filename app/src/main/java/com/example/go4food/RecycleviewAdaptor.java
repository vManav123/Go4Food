package com.example.go4food;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4food.model.Restaurants_Model;

import java.util.ArrayList;
import java.util.List;

public class RecycleviewAdaptor extends RecyclerView.Adapter<RecycleviewAdaptor.MyViewHolder>
{

    private Context context;


    public RecycleviewAdaptor(Context context, List<Restaurants_Model> mdata) {
        this.context = context;
        this.mdata = mdata;
        this.option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    private List<Restaurants_Model> mdata;
    RequestOptions option;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflator = LayoutInflater.from(context);
        view = inflator.inflate(R.layout.shops_row_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.viewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,DetailActivity.class);
                i.putExtra("shop_name",mdata.get(viewHolder.getAdapterPosition()).getShop_name());
                i.putExtra("rating",mdata.get(viewHolder.getAdapterPosition()).getrating());
                i.putExtra("city",mdata.get(viewHolder.getAdapterPosition()).getCity());
                i.putExtra("address",mdata.get(viewHolder.getAdapterPosition()).getAddress());
                i.putExtra("image",mdata.get(viewHolder.getAdapterPosition()).getimage());
                i.putStringArrayListExtra("cusines",viewHolder.cusines);
                i.putStringArrayListExtra("menu",viewHolder.menu);
                for(String c:viewHolder.cusines)
                {
                    Log.d("Cusines",c);
                }

                context.startActivity(i);
            }
        });


        return viewHolder;
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_name.setText(mdata.get(position).getShop_name());
        holder.tv_rate.setText(mdata.get(position).getrating());
        holder.tv_city.setText(mdata.get(position).getCity());
        Glide.with(context).load(mdata.get(position).getimage()).apply(option).into(holder.image);
        holder. cusines =  mdata.get(position).getCuisines();
        holder.menu = mdata.get(position).getMenu();
        String categories="";
        for (int i=0;i<holder.cusines.size();i++)
        {
            if(holder.cusines.size()-1==i)
            {
                categories+=holder.cusines.get(i);
            }
            else
                categories += holder.cusines.get(i) + " | ";
        }
        Log.d("cc", "onBindViewHolder: " + categories);
        holder.tv_categories.setText((categories));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_name;
        TextView tv_rate;
        TextView tv_city;
        TextView tv_categories;
        ImageView image;
        LinearLayout viewContainer;
        ArrayList<String> cusines;
        ArrayList<String>menu;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            viewContainer=itemView.findViewById(R.id.containner);
            tv_name=itemView.findViewById(R.id.rowname);
            tv_categories=itemView.findViewById(R.id.categorie);
            tv_rate=itemView.findViewById(R.id.rating);
            tv_city=itemView.findViewById(R.id.studio);
            cusines = new ArrayList<>();
           image=itemView.findViewById(R.id.thumbnail);
        }
    }
}
