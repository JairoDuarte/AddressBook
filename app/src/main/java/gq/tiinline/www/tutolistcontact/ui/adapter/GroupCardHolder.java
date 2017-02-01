package gq.tiinline.www.tutolistcontact.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import gq.tiinline.www.tutolistcontact.R;
import gq.tiinline.www.tutolistcontact.data.Group;
import gq.tiinline.www.tutolistcontact.data.VolleyDownloader;

/**
 * Created by Jairo Duarte on 01/02/2017.
 */

public class GroupCardHolder extends RecyclerView.ViewHolder {

    private final TextView ui_title;
    private final NetworkImageView ui_image;
    private final Context context;

    //vue qu'on va créer dans notre layout
    public GroupCardHolder(View cell, Context context) {
        super(cell);
        ui_title = (TextView) cell.findViewById(R.id.group_title);
        ui_image = (NetworkImageView)cell.findViewById(R.id.image);
        this.context = context;
    }

    public void layouyForGroup(Group group){
        ui_title.setText(group.getTitle());
        ui_image.setImageUrl(group.getImage(), VolleyDownloader.getInstance(context).getImageLoader());
    }
}
