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

public class PresetAdapter extends RecyclerView.Adapter<PresetAdapter.PresetViewHolder> {

    List<Food> PresetFoods;
    Context context;

    public PresetAdapter(Context c, List<Food> foods){
        context=c;
        PresetFoods=foods;

    }

    @NonNull
    @Override
    public PresetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.preset_list_item,parent,false);


        return new PresetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresetViewHolder holder, int position) {
        holder.image1.setImageResource(PresetFoods.get(position).getPictureID());
        holder.text1.setText(PresetFoods.get(position).getName());
        holder.presetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemEntry.class);
                intent.putExtra("PRESET",1);
                intent.putExtra("PRESET_POSITION",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return PresetFoods.size();
    }

    public class PresetViewHolder extends RecyclerView.ViewHolder{

        ImageView image1;
        TextView text1;
        ConstraintLayout presetLayout;

        public PresetViewHolder(@NonNull View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.preset1);
            text1 = itemView.findViewById(R.id.textView);
            presetLayout = itemView.findViewById(R.id.presetLayout);
        }



    }



}
