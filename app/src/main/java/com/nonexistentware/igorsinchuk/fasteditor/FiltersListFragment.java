package com.nonexistentware.igorsinchuk.fasteditor;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nonexistentware.igorsinchuk.fasteditor.Adapter.ThumbnailAdapter;
import com.nonexistentware.igorsinchuk.fasteditor.Interface.FilterListFragmentListener;
import com.nonexistentware.igorsinchuk.fasteditor.Utils.BitmapUtils;
import com.nonexistentware.igorsinchuk.fasteditor.Utils.SpaceItemDecoration;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FiltersListFragment extends Fragment implements FilterListFragmentListener {

    RecyclerView recyclerView;
    ThumbnailAdapter adapter;
    List<ThumbnailItem> thumbnailItems;

    FilterListFragmentListener listener;

    public FiltersListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setListener(FilterListFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_filters_list, container, false);

        thumbnailItems = new ArrayList<>();
        adapter = new ThumbnailAdapter(thumbnailItems, this, getActivity());

        recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));
        recyclerView.setAdapter(adapter);

        displayThumbnail(null);

        return itemView;
    }

    public void displayThumbnail(final Bitmap bitmap) {
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                Bitmap thumbImg;
                if (bitmap == null)
                    thumbImg = BitmapUtils.getBitmapFromAssets(getActivity(), MainActivity.pictureName, 100, 100);
                else
                    thumbImg = Bitmap.createScaledBitmap(bitmap, 100, 100, false);

                if (thumbImg == null)
                    return;

                ThumbnailsManager.clearThumbs();
                thumbnailItems.clear();

                ThumbnailItem thumbnailItem = new ThumbnailItem();
                thumbnailItem.image = thumbImg;
                thumbnailItem.filterName = "Normal";
                ThumbnailsManager.addThumb(thumbnailItem);

                List<Filter> filters = FilterPack.getFilterPack(getActivity());

                for (Filter filter : filters) {
                    ThumbnailItem thumbitem = new ThumbnailItem();
                    thumbitem.image = thumbImg;
                    thumbitem.filter = filter;
                    thumbitem.filterName = filter.getName();
                    ThumbnailsManager.addThumb(thumbitem);
                }

                thumbnailItems.addAll(ThumbnailsManager.processThumbs(getActivity()));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

        };

                new Thread(r).start();
            }


            @Override
            public void onFilterSelect(Filter filter) {
                if (listener != null)
                    listener.onFilterSelect(filter);
            }
        }
