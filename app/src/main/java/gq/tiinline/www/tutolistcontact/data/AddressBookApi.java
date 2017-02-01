package gq.tiinline.www.tutolistcontact.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jairo Duarte on 31/01/2017.
 */

public interface AddressBookApi {
    @GET("/groups")
    Call<List<Group>> getGroupList();

    @GET("/groups/{group}")
    Call<Group> getGroup(@Path("group")int groupId);
}
