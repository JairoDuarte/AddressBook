package gq.tiinline.www.tutolistcontact.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import gq.tiinline.www.tutolistcontact.R;
import gq.tiinline.www.tutolistcontact.ui.adapter.GroupListAdapter;

public class GroupListActivity extends AppCompatActivity {
    private TextView title;
    private TextView id;
    private ImageView imageView;
    private RecyclerView ui_groupListRecyclerView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView) findViewById(R.id.title);
        id = (TextView) findViewById(R.id.id);
        imageView = (ImageView) findViewById(R.id.imageView);
        ui_groupListRecyclerView = (RecyclerView) findViewById(R.id.grouplist_recycler_view);
        ui_groupListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ui_groupListRecyclerView.setAdapter(new GroupListAdapter(this));

    }

}
