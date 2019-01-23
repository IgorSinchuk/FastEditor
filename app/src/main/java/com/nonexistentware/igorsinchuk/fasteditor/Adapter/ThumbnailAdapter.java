package com.nonexistentware.igorsinchuk.fasteditor.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nonexistentware.igorsinchuk.fasteditor.Interface.FilterListFragmentListener;
import com.nonexistentware.igorsinchuk.fasteditor.R;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.MyViewHolder> {

    private List<ThumbnailItem> thumbnailItems;
    private FilterListFragmentListener listener;
    private Context context;
    private int selectedIndex = 0;

    public ThumbnailAdapter(List<ThumbnailItem> thumbnailItems, FilterListFragmentListener listener, Context context) {
        this.thumbnailItems = thumbnailItems;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.thumbnail_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ThumbnailItem thumbnailItem = thumbnailItems.get(position);

        holder.thumbnail.setImageBitmap(thumbnailItem.image);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFilterSelect(thumbnailItem.filter);
                selectedIndex = position;
                notifyDataSetChanged();
            }
        });

        holder.filterName.setText(thumbnailItem.filterName);

        if (selectedIndex == position) {
            holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.selected_filter));
        } else {
            holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.default_filter));
        }
    }

    @Override
    public int getItemCount() {
        return thumbnailItems.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView filterName;
        public MyViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            filterName = (TextView) itemView.findViewById(R.id.filterName);
        }
    }
}
