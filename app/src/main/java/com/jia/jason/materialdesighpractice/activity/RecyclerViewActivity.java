package com.jia.jason.materialdesighpractice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.adapter.RecyclerViewAdapter;
import com.jia.jason.materialdesighpractice.model.JListItem;

import java.util.List;

/**
 * Created by xin.jia
 * since 2016/3/23
 */
public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private List<JListItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        itemList = JListItem.mockItems();
        mAdapter = new RecyclerViewAdapter(itemList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
