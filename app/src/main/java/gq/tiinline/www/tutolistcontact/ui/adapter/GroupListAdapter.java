package gq.tiinline.www.tutolistcontact.ui.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gq.tiinline.www.tutolistcontact.R;
import gq.tiinline.www.tutolistcontact.data.AddressBookApi;
import gq.tiinline.www.tutolistcontact.data.Group;
import gq.tiinline.www.tutolistcontact.ui.GroupListActivity;
import gq.tiinline.www.tutolistcontact.utils.DownloadImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jairo Duarte on 01/02/2017.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupCardHolder> {
    private List<Group> _groupList;
    private Activity activityParent;

    public GroupListAdapter(Activity activity) {
        activityParent = activity;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.56.1:3000/"/*"http://192.168.1.9:3000"*/) //Url de l'api
                .addConverterFactory(GsonConverterFactory.create()) //Conversion du résultat json en object java
                .build();

        // Creation de l'interface ou service rest
        AddressBookApi service = retrofit.create(AddressBookApi.class);
        service.getGroupList().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                Log.i("Retrofit", "Get OK");
                for (Group group : response.body()) {
                    Log.i("Retrofit", "Group:" + group.getTitle());
                }
                _groupList = response.body();
                notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Log.i("Retrofit", "Error");

            }
        });
        service.getGroup(2).enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                Log.i("Retrofit", "Get by id Ok:" + response.body().getTitle());
                ((TextView)activityParent.findViewById(R.id.id)).setText(Integer.toString(response.body().getId()));
                ((TextView)activityParent.findViewById(R.id.title)).setText(response.body().getTitle());

                new DownloadImage(((ImageView)activityParent.findViewById(R.id.imageView)),activityParent.getApplicationContext())
                        .execute("https://pbs.twimg.com/profile_images/630285593268752384/iD1MkFQ0.png");
                //imageView.setImageURI(Uri.parse(response.body().getImage()));
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {

            }
        });


    }

    @Override
    public GroupCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Créer les celules visibles
        View cell = LayoutInflater.from(activityParent).inflate(R.layout.group_cell,parent,false);
        GroupCardHolder holder = new GroupCardHolder(cell,activityParent.getApplicationContext());
        return holder;
    }

    @Override
    public void onBindViewHolder(GroupCardHolder holder, int position) {
        //Remplir les celules
        holder.layouyForGroup(_groupList.get(position));

    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (_groupList != null)
            itemCount = _groupList.size();
        return itemCount;
    }
}
