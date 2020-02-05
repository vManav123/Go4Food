package com.example.go4food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class RecycleViewAdaptor extends RecyclerView.Adapter<RecycleViewAdaptor.ViewHolder>{

    ArrayList<Notes> list = new ArrayList<>();
    boolean showshimmer = true;
    Context context;
    int SHIMMER_ITEM_NUMBER=5;
    RecycleViewAdaptor(ArrayList<Notes>list,Context context)
    {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(showshimmer)
        {
            holder.shimmerFrameLayout.startShimmer();
        }
        else
        {
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            holder.tvtile.setBackground(null);
            holder.tvtile.setText(list.get(position).getTitle());

            holder.tvdiscription.setBackground(null);
            holder.tvdiscription.setText(list.get(position).getTitle());

            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.coffee));
        }
    }

    @Override
    public int getItemCount() {
        return showshimmer?SHIMMER_ITEM_NUMBER : list.size( );
    }


    class ViewHolder extends RecyclerView.ViewHolder {



        ShimmerFrameLayout shimmerFrameLayout;
        TextView tvtile,tvdiscription;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shimmerFrameLayout = itemView.findViewById(R.id.shimmer_layout);
            tvtile=itemView.findViewById(R.id.title);
            tvdiscription=itemView.findViewById(R.id.tv_title);
            imageView=itemView.findViewById(R.id.imageView);
        }

    }
}
