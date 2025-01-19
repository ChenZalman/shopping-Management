package com.example.shoppingmanagement.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingmanagement.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private ArrayList<Item> dataSet;
    public ItemAdapter(ArrayList<Item> dataSet) {
        this.dataSet = dataSet;
    }

    private RecyclerViewListener listener;

    public ItemAdapter(ArrayList<Item> dataSet, RecyclerViewListener listener) {
        this.dataSet = dataSet;
        this.listener = listener;
    }

    public interface RecyclerViewListener{
        void onClick(View  view, int position);
        boolean onLongClick(View view, int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvName);

            imageView = itemView.findViewById(R.id.ibItem);
        }
    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewName.setText(dataSet.get(position).getName());
        holder.imageView.setImageResource(dataSet.get(position).getImage());

        holder.itemView.setOnClickListener(v -> listener.onClick(v, position));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(v, position);
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}

