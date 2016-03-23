package com.jia.jason.materialdesighpractice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jia.jason.materialdesighpractice.R;

/**
 * Created by xin.jia
 * since 2016/3/23
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private String[] dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llItemView;
        public TextView mTextView;

        public MyViewHolder(LinearLayout itemView) {
            super(itemView);
            this.llItemView = itemView;
            mTextView = (TextView) itemView.findViewById(R.id.item_view_tv);
        }
    }

    public RecyclerViewAdapter(String[] dataSet) {
        this.dataSet = dataSet;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout llItemView= (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_view, parent, false);
        return new MyViewHolder(llItemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(dataSet[position]);
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }
}
