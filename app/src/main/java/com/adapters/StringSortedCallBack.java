package com.adapters;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedListAdapterCallback;

public class StringSortedCallBack extends SortedListAdapterCallback<String> {

    public StringSortedCallBack(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }

    @Override
    public boolean areContentsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areItemsTheSame(String item1, String item2) {
        return item1 == item2;
    }
}
