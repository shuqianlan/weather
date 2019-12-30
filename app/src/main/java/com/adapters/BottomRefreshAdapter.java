package com.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import com.ViewHolder;
import com.ilifesmart.weather.R;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


/*
* 支持下拉更新的RecyclerView.Adapter
* 1. 支持自定义创建View
* 2. 支持不同的viewtype
* 3. 支持异步的数据加载
*
* */
public class BottomRefreshAdapter<T> extends RecyclerView.Adapter<ViewHolder>  {

    private int FOOTER_VIEW_TAG = -1;
    private SortedList<T> beans;
    private WeakReference<Context> instance_context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        instance_context = new WeakReference(parent.getContext());
        if (viewType == FOOTER_VIEW_TAG) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottomrefreshlayout, parent, false);
        } else {
            v = viewCreateListener.onCreatView(parent, viewType);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (position < beans.size()) {
            viewBindListener.onBindView(holder.itemView, beans.get(position));

            if (viewClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewClickListener.onClickItemView(holder.itemView, beans.get(position));
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return beans.size() + 1;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        int viewtype = holder.getItemViewType();
        if (viewtype == FOOTER_VIEW_TAG) {
            holder.itemView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dataloadListener.onLoadData(BottomRefreshAdapter.this);
                }
            }, 500);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = FOOTER_VIEW_TAG;
        if (position < beans.size()) {
            if (viewTypeListener != null) {
                viewType = viewTypeListener.getViewType(beans.get(position));
                if (viewType < 0) {
                    new InvalidParameterException("viewType must > 0");
                }
            } else {
                viewType = 0;
            }
        }

        return viewType;
    }

    public void extendDatas(List<T> beans) {
        if (instance_context.get() == null) {
            return;
        }

        this.beans.addAll(beans);
    }

    public static class Builder<B> {
        private BottomRefreshAdapter<B> adapter;

        public Builder(OnCreateViewListener listener1, OnBindViewListener<B> listener2,Class<B> clazz, InstanceBeansCallBack<B> cb) {
            adapter = new BottomRefreshAdapter<B>();
            setOnCreateViewListener(listener1);
            setOnBindViewListener(listener2);

            adapter.beans = new SortedList<B>(clazz, cb.instance(adapter));
        }

        public Builder<B> setOnCreateViewListener(OnCreateViewListener listener) {
            adapter.viewCreateListener = listener;
            return this;
        }

        public Builder<B> setOnBindViewListener(OnBindViewListener<B> listener) {
            adapter.viewBindListener = listener;
            return this;
        }

        public Builder<B> setOnViewtypeGetListener(OnGetViewTypeListener<B> listener) {
            adapter.viewTypeListener = listener;
            return this;
        }

        public Builder<B> setOnItemViewClickListener(OnItemViewClickListener<B> listener) {
            adapter.viewClickListener = listener;
            return this;
        }

        public Builder<B> setOnLoadMoreDataListener(OnLoadMoreDataListener<B> listener) {
            adapter.dataloadListener = listener;
            return this;
        }

        public Builder<B> setDatas(List<B> beans) {
            adapter.beans.addAll(beans);
            return this;
        }

        public BottomRefreshAdapter build() {
            return adapter;
        }
    }

    public interface OnCreateViewListener {
        View onCreatView(ViewGroup parent, int viewType);
    }

    public interface OnGetViewTypeListener<B> {
        int getViewType(B bean);
    }

    public interface OnBindViewListener<B> {
        void onBindView(View view, B bean);
    }

    public interface OnItemViewClickListener<B> {
        void onClickItemView(View view, B bean);
    }

    public interface OnLoadMoreDataListener<B> {
        void onLoadData(BottomRefreshAdapter<B> adapter);
    }

    @FunctionalInterface
    public interface InstanceBeansCallBack<T> {
        SortedListAdapterCallback<T> instance(BottomRefreshAdapter<T> adapter);
    }

    private OnCreateViewListener viewCreateListener;
    private OnBindViewListener viewBindListener;
    private OnGetViewTypeListener viewTypeListener;
    private OnItemViewClickListener viewClickListener;
    private OnLoadMoreDataListener dataloadListener;
}
