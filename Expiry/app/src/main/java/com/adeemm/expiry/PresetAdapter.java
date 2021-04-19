package com.adeemm.expiry;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.adeemm.expiry.Activities.ItemEntry;
import com.adeemm.expiry.Models.Food;
import com.adeemm.expiry.Models.PresetDatabase;

import java.util.List;


/**
 * This is the adapter for the preset recycler view which allows the user to select our premade
 * items.
 */
public class PresetAdapter extends RecyclerView.Adapter<PresetAdapter.PresetViewHolder> {

    List<Food> PresetFoods;
    Context context;

    /**
     * Pre:c is the context for the app
     * foods is the list containing all the presets in the database
     */
    public PresetAdapter(Context c, List<Food> foods){
        context = c;
        PresetFoods = foods;
    }

    /**
     * This function loads the view / layout for the user.
     */
    @NonNull
    @Override
    public PresetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.preset_list_item,parent,false);

        return new PresetViewHolder(view);
    }

    /**
     * This function launches the item form after clicking on a row and initializes said row
     */
    @Override
    public void onBindViewHolder(@NonNull PresetViewHolder holder, int position) {
        holder.image1.setImageResource(PresetFoods.get(position).getPictureID());
        holder.text1.setText(PresetFoods.get(position).getName());
        holder.presetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food f = PresetFoods.get(position);

                Intent intent = new Intent(context, ItemEntry.class);
                intent.putExtra("FOOD_NAME", f.getName());
                intent.putExtra("FOOD_EXP", f.getExpiration().getTime());
                intent.putExtra("FOOD_PIC", f.getPictureID());

                context.startActivity(intent);
            }
        });
    }

    /**
     * Pre:none
     * Post: return PresetFoods.size();
     */
    @Override
    public int getItemCount() {
        return PresetFoods.size();
    }

    /**
     * This is the view holder for hte preset recycler view.
     */
    public class PresetViewHolder extends RecyclerView.ViewHolder {
        ImageView image1;
        TextView text1;
        ConstraintLayout presetLayout;

        /**
         * This adds all the items in the row to the holder for the recycler view.
         */
        public PresetViewHolder(@NonNull View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.preset1);
            text1 = itemView.findViewById(R.id.textView);
            presetLayout = itemView.findViewById(R.id.presetLayout);
        }
    }
}
