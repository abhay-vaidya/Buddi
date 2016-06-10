package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class to handle profile screen
 *
 * @author Team Buddi
 * @version 1.0
 */
public class ProfileFragment extends Fragment {

    // Initialize items
    TextView name;
    TextView userName;
    TextView email;
    Button prefButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);

        // Bind change preferences button to layout item
        prefButton = (Button) view.findViewById(R.id.changePrefButton);

        // Open quiz screen when clicked
        prefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QuizActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        // Bind text to layout items
        name = (TextView) view.findViewById(R.id.name);
        userName = (TextView) view.findViewById(R.id.username);
        email = (TextView) view.findViewById(R.id.emailAddress);

        // Set name, username, and email address to that of the user
        loadInfo();

        return view;
    }

    /**
     * Loads information from shared preferences and sets textviews accordingly
     */
    private void loadInfo() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        name.setText(sharedPref.getString("name", "broke"));
        userName.setText(sharedPref.getString("username", "broke"));
        email.setText(sharedPref.getString("email", "broke"));
    }
}