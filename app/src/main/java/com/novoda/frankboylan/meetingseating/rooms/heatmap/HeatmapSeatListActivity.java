package com.novoda.frankboylan.meetingseating.rooms.heatmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.novoda.frankboylan.meetingseating.ConnectionStatus;
import com.novoda.frankboylan.meetingseating.R;

import java.util.List;

public class HeatmapSeatListActivity extends AppCompatActivity implements HeatmapSeatListDisplayer {
    HeatmapSeatListPresenterImpl heatmapSeatListPresenter;
    ListView seatListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap_room);

        Toolbar toolbarHeatmap = findViewById(R.id.toolbar_heatmap);
        toolbarHeatmap.setTitle(R.string.toolbar_heatmap_title);

        setSupportActionBar(toolbarHeatmap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seatListView = findViewById(R.id.lv_seat_heatmap);

        heatmapSeatListPresenter = new HeatmapSeatListPresenterImpl();
        heatmapSeatListPresenter.bind(this);

        if (ConnectionStatus.hasActiveInternetConnection()) {
            Intent intent = getIntent();
            String roomId = intent.getStringExtra("roomId");
            heatmapSeatListPresenter.getData(roomId);
            //ArrayAdapter<HeatmapSeat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            //seatListView.setAdapter(adapter);
        } else {
            makeToast("No internet connection!");
        }

        heatmapSeatListPresenter.startPresenting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.heatmap_toolbar_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_heatmap:
                // ToDo: Toggle Heatmap Colours & update listView
                break;
            case android.R.id.home:
                heatmapSeatListPresenter.unbind();
                finish();
                break;
        }
        return true;
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAdapter(List<HeatmapSeat> seatList) {
        CustomHeatmapAdapter adapter = new CustomHeatmapAdapter(this, seatList);
        seatListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        heatmapSeatListPresenter.unbind();
        super.onDestroy();
    }
}
