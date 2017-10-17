package com.novoda.frankboylan.meetingseating;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ListView drawerList = findViewById(R.id.side_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        drawerList.setAdapter(new ArrayAdapter<>(this,
                                                 R.layout.drawer_list_item, mDrawerOptions
        ));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        Toolbar toolbarSettings = findViewById(R.id.toolbar_settings);
        toolbarSettings.setTitle(getString(R.string.toolbar_settings_title));

        toolbarSettings.setNavigationIcon(R.drawable.ic_action_burger);

        setSupportActionBar(toolbarSettings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_toolbar_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                break;
        }
        return true;
    }
}
