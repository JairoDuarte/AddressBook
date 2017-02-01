package gq.tiinline.www.tutolistcontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import gq.tiinline.www.tutolistcontact.data.CacheChecker;
import gq.tiinline.www.tutolistcontact.data.VolleyDownloader;
import gq.tiinline.www.tutolistcontact.ui.GroupListActivity;

public class MainActivity extends AppCompatActivity {
    private TextView ui_welcomeLabel;
    private final  int PICK_CONTACT_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //

        ui_welcomeLabel = (TextView)findViewById(R.id.hello);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        CacheChecker cacheChecker = new CacheChecker((TextView)findViewById(R.id.hello));
        //Créer un tableau de string de 3 elements
        cacheChecker.execute("data 1","data 2","data 3");

        //request
        StringRequest request = new StringRequest("http://www.google.fr", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("Volley", "Success: " + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Volley","Error:"+volleyError.getLocalizedMessage());
            }
        });
       // VolleyDownloader.getInstance(this.getApplicationContext()).addToRequestQueue(request);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void welcomeButtonPressed(View view) {
        Intent openGroups = new Intent(this, GroupListActivity.class);
        startActivity(openGroups);
        ui_welcomeLabel.setText("Hello Word!!! life is good if you live with Jesus(Jesus is God)");

        //pickContact();
    }
    public void  pickContact(){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(pickContactIntent,PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==PICK_CONTACT_REQUEST && resultCode== Activity.RESULT_OK) {
            //tratement des données envoyé comme résultat

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
