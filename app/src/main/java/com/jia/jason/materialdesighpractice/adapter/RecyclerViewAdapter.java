package com.jia.jason.materialdesighpractice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.model.JListItem;

import java.util.List;

/**
 * Created by xin.jia
 * since 2016/3/23
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<JListItem> itemList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mCube;
        public TextView mTextView;
        public CheckBox mCheckBox;

        public MyViewHolder(LinearLayout itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_view_tv);
            mCube = (ImageView) itemView.findViewById(R.id.cube);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

    public RecyclerViewAdapter(List<JListItem> itemList) {
        this.itemList = itemList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout llItemView= (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_view, parent, false);
        return new MyViewHolder(llItemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final JListItem item = itemList.get(position);
        holder.mTextView.setText(item.text);
        holder.mCube.setBackgroundColor(item.color);
        holder.mCheckBox.setChecked(item.isChecked);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
