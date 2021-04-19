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

/**
 * This is the list adapter for the main activity recycler view
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> items = new ArrayList<ListItem>();
    private OnItemClickListener listener;
    private Context ctx;

    public interface OnItemClickListener {
        void onItemClick(View view, ListItem obj, int position);
    }

    /**
     * Pre:clickListener is an OnItemClickListener
     * Post: this.listener = clickListener;
     */
    public void setOnItemClickListener(final OnItemClickListener clickListener) {
        this.listener = clickListener;
    }

    /**
     * Pre:context is the context for the list
     * items is the list of listitems for the list
     * Post:this.items = items;
     *   ctx = context;
     */
    public ListAdapter(Context context, List<ListItem> items) {
        this.items = items;
        ctx = context;
    }

    /**
     * This is the view holder for the list adapter for list items
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        public ImageView food_icon;
        public View parent_layout;
        public ImageButton freezeButton;

        /**
         * This constructor initialized all the view items in the row
         */
        public ItemViewHolder(View view) {
            super(view);
            parent_layout = (View) view.findViewById(R.id.parent_layout);
            food_icon = (ImageView) view.findViewById(R.id.expiration_icon);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            freezeButton = view.findViewById(R.id.FreezeButton);


        }
    }

    /**
     * This is the view holder for the list adapter for sections
     */
    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView sectionName;

        public SectionViewHolder(View view) {
            super(view);
            sectionName = (TextView) view.findViewById(R.id.section_name);
        }
    }

    /**
     * THis function creates the row depending on if it is a section or a list item
     */
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

    /**
     * This function adds all the data from the item at position and initialized the freeze button
     * and the onclicklistener
     */
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
                if(items.get(position).getFood().isFrozen()){
                    view.food_icon.setBackgroundColor(ctx.getResources().getColor(R.color.freeze_blue));
                }
                else {
                    view.food_icon.setBackgroundColor(ctx.getResources().getColor(R.color.white));
                }

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
                    if(items.get(position).getFood().isFrozen()){
                        view.food_icon.setBackgroundColor(ctx.getResources().getColor(R.color.freeze_blue));
                    }
                    else {
                        view.food_icon.setBackgroundColor(ctx.getResources().getColor(R.color.white));
                    }
                    notifyDataSetChanged();
                }
            });
        }
        else {
            SectionViewHolder view = (SectionViewHolder) viewHolder;
            view.sectionName.setText(item.getName());
        }
    }

    /**
     * Pre:None
     * Post:return items.size();
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Pre: 0<= position < items.size()
     * post: the type of item at the position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).isSection() ? 0 : 1;
    }

    /**
     * Pre:0<= position < items.size()
     * food is a food item
     * Post: food is inserted in that location
     */
    public void addItem(int index, ListItem food){
        items.add(index, food);
        notifyItemInserted(index);
    }
}