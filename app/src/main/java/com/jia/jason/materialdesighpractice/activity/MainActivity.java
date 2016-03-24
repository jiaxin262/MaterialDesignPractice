package com.jia.jason.materialdesighpractice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.jia.jason.materialdesighpractice.R;
public class MainActivity extends BaseActivity {

    private TextView tvRecyclerView;
    private TextView tvCardView;
    private TextView tvGridView;
    private TextView gBall;
    private TextView ListViewContainer;
    private TextView ListViewTest;
    private TextView jLunarLander;
    private TextView ListViewAndOther;
    private TextView gravitySensor;
    private TextView listAddSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRecyclerView = (TextView) findViewById(R.id.recycler_view_test);
        tvCardView = (TextView) findViewById(R.id.card_view_test);
        tvGridView = (TextView) findViewById(R.id.grid_view_test);
        gBall = (TextView) findViewById(R.id.g_ball);
        ListViewContainer = (TextView) findViewById(R.id.list_view_container);
        ListViewTest = (TextView) findViewById(R.id.list_view_test);
        jLunarLander = (TextView) findViewById(R.id.j_lunar_lander);
        ListViewAndOther = (TextView) findViewById(R.id.list_view_and_other);
        gravitySensor = (TextView) findViewById(R.id.gravity_sensor);
        listAddSet = (TextView) findViewById(R.id.list_add_set);

        tvRecyclerView.setOnClickListener(this);
        tvCardView.setOnClickListener(this);
        tvGridView.setOnClickListener(this);
        gBall.setOnClickListener(this);
        ListViewContainer.setOnClickListener(this);
        ListViewTest.setOnClickListener(this);
        jLunarLander.setOnClickListener(this);
        ListViewAndOther.setOnClickListener(this);
        gravitySensor.setOnClickListener(this);
        listAddSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recycler_view_test:
                jStartActivity(RecyclerViewActivity.class);
                break;
            case R.id.card_view_test:
                jStartActivity(CardViewActivity.class);
                break;
            case R.id.grid_view_test:
                jStartActivity(GridViewActivity.class);
                break;
            case R.id.g_ball:
                //jStartActivity(GBallActivity.class);
                break;
            case R.id.list_view_container:
                //jStartActivity(ListViewsActivity.class);
                break;
            case R.id.list_view_test:
                //jStartActivity(ListViewTestActivity.class);
                break;
            case R.id.j_lunar_lander:
                //jStartActivity(JLunarLanderActivity.class);
                break;
            case R.id.list_view_and_other:
                //jStartActivity(ListViewAndOtherActivity.class);
                break;
            case R.id.gravity_sensor:
                //jStartActivity(GravitySensorActivity.class);
                break;
            case R.id.list_add_set:
                //jStartActivity(ListAddSetActivity.class);
                break;
        }
    }

}
