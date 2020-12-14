package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    List<String> items;
    public ItemsAdapter(List<String> items){
        this.items = items;
    }
    /*
        Respsonsible for creating each view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create the new view and wrap inside of a view holder
        // Use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // return the inflated view wrapped inside of ViewHolder
        return new ViewHolder(todoView);
    }

    /*
        Responsible for taking data at various particular positions and putting them
        into a view holder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Read the item at the position
        String item = items.get(position);
        // Bind the item into the specified view holder
        holder.bind(item); // method of the ViewHolder class for writing
    }

    /*
        Number of items in the ViewHolder object,
        tells the rv (RecyclerView) how many items are in the list
     */
    @Override
    public int getItemCount() {
        return this.items.size();
//        return 0;
    }
    /*
         A RecyclerView.Adapter and ViewHolder for rendering items
        - need to define the view holder
     */

    // Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // Update the view inside of the view holder with this data
        public void bind(String item){
            tvItem.setText(item);
        }
    }
}
