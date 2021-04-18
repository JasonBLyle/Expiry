package com.adeemm.expiry;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adeemm.expiry.Models.ExpirationDatabase;
import com.adeemm.expiry.Models.ListItem;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> items = new ArrayList<ListItem>();
    private OnItemClickListener listener;
    private Context ctx;

    public interface OnItemClickListener {
        void onItemClick(View view, ListItem obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener clickListener) {
        this.listener = clickListener;
    }

    public ListAdapter(Context context, List<ListItem> items) {
        this.items = items;
        ctx = context;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        public ImageView food_icon;
        public View parent_layout;
        public ImageButton freezeButton;

        public ItemViewHolder(View view) {
            super(view);
            parent_layout = (View) view.findViewById(R.id.parent_layout);
            food_icon = (ImageView) view.findViewById(R.id.expiration_icon);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            freezeButton = view.findViewById(R.id.FreezeButton);
        }
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView sectionName;

        public SectionViewHolder(View view) {
            super(view);
            sectionName = (TextView) view.findViewById(R.id.section_name);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ItemViewHolder(view);
        }

        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_section, parent, false);
            viewHolder = new SectionViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ListItem item = items.get(position);

        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder view = (ItemViewHolder) viewHolder;

            view.name.setText(item.getName());

            if (item.getFood() != null) {
                int resID = item.getFood().getPictureID();

                if (resID == 0) {
                    resID = R.drawable.food_misc;
                }

                view.food_icon.setImageResource(resID);

                String detailsText;
                int days = Math.abs(Math.round(ChronoUnit.DAYS.between(item.getFood().getExpiration().toInstant(), new Date().toInstant())));

                if (item.getFood().getExpiration().before(new Date())) {
                    detailsText = String.format("Expired %d day(s) ago!", days);
                    view.food_icon.setImageResource(R.drawable.ic_expiration_alert);
                }
                else {
                    detailsText = String.format("Expiring in %d day(s)", days);
                }

                view.description.setText(detailsText);
            }
            else {
                view.food_icon.setImageResource(R.drawable.food_misc);
            }

            view.parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(view, items.get(position), position);
                    }
                }
            });
            ((ItemViewHolder) viewHolder).freezeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpirationDatabase expirationDatabase = new ExpirationDatabase(ctx);

                    items.get(position).setFood(expirationDatabase.freezeFood(items.get(position).getFood()));
                    notifyDataSetChanged();
                }



            });
        }

        else {
            SectionViewHolder view = (SectionViewHolder) viewHolder;
            view.sectionName.setText(item.getName());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).isSection() ? 0 : 1;
    }

    public void addItem(int index, ListItem food){
        items.add(index, food);
        notifyItemInserted(index);
    }
}