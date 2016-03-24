package com.jia.jason.materialdesighpractice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jia.jason.materialdesighpractice.R;
import com.jia.jason.materialdesighpractice.adapter.ImageAdapter;
import com.jia.jason.materialdesighpractice.model.ImageModel;

/**
 * Created by xin.jia
 * since 2016/3/24
 */
public class GridViewActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_layout);

        GridView gridView = (GridView) findViewById(R.id.grid_view_gv);
        gridView.setAdapter(new ImageAdapter(this, ImageModel.mock()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridViewActivity.this, "position:"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
