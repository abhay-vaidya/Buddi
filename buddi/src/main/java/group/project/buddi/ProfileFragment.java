package group.project.buddi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class ProfileFragment extends Fragment {

    TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);

       // userName = (TextView) view.findViewById(R.id.userName);

        //loadJSON();

        return view;
    }

    private void loadJSON() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        Ion.with(getActivity())
                .load("http://ec2-52-91-255-81.compute-1.amazonaws.com/api/v1/account?access_token=" + sharedPref.getString("auth_token", "broke"))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        JsonObject account = result.get(0).getAsJsonObject();

                        userName.setText(account.get("name").getAsString());

                    }
                });
    }
}