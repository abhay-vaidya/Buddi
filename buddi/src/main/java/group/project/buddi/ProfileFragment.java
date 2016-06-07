package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class ProfileFragment extends Fragment {

    TextView name;
    TextView userName;
    TextView email;

    Button prefButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);

        prefButton = (Button) view.findViewById(R.id.changePrefButton);

        prefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QuizActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        name = (TextView) view.findViewById(R.id.name);
        userName = (TextView) view.findViewById(R.id.username);
        email = (TextView) view.findViewById(R.id.emailAddress);


        loadInfo();

        return view;
    }

    private void loadInfo() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        name.setText( sharedPref.getString("name", "broke") );
        userName.setText( sharedPref.getString("username", "broke") );
        email.setText( sharedPref.getString("email", "broke") );
    }
}