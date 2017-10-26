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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteDelete;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteInsert;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteRead;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteUpdate;
import com.novoda.frankboylan.meetingseating.seats.SeatDataRetrievalTask;

public class StatisticsActivity extends AppCompatActivity {
    private static final String TAG = "StatisticsActivity";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        ListView drawerList = findViewById(R.id.side_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        drawerList.setAdapter(new ArrayAdapter<>(this,
                                                 R.layout.drawer_list_item, mDrawerOptions
        ));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        Toolbar toolbarStats = findViewById(R.id.toolbar_stats);
        toolbarStats.setTitle(R.string.toolbar_statistics_title);
        toolbarStats.setNavigationIcon(R.drawable.ic_action_burger);

        setSupportActionBar(toolbarStats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (ConnectionStatus.hasActiveInternetConnection()) {

            DatabaseReference firebaseDb = FirebaseDatabase.getInstance().getReference();
            firebaseDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.child("users").child(auth.getUid()).child("email").getValue().toString();
                    String firstname = dataSnapshot.child("users").child(auth.getUid()).child("firstname").getValue().toString();
                    String surname = dataSnapshot.child("users").child(auth.getUid()).child("surname").getValue().toString();

                    // ToDo: Create static user for session.

                    final TextView tvLoggedUser = findViewById(R.id.tv_drawer_greeting);
                    tvLoggedUser.setText("Welcome, " + firstname + "!");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            updateUI();
        } else {
            makeToast("In Offline Mode, you won't have access to the latest data!");
        }
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stats_toolbar_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateUI();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Fetches Json data, refreshes ListViews with SQLite data
     */
    private void updateUI() {
        SeatDataRetrievalTask task = new SeatDataRetrievalTask(new SQLiteRead(this), new SQLiteDelete(this), new SQLiteInsert(this), new SQLiteUpdate(this));
        Toast.makeText(this, "Fetching data", Toast.LENGTH_LONG).show();
        task.execute();
    }
}
