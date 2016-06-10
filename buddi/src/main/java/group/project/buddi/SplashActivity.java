package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class to handle splash screen
 *
 * @author Team Buddi
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Shared preferences for later
        Context context = SplashActivity.this;
        final SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        // New thread
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(getApplicationContext(), IntroSlides.class);
                    startActivity(i);
                    finish();

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                } else if (!sharedPref.getBoolean("is_logged_in", false)) {
                    // Go to login screen
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    // Go to home screen
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        // Start the thread
        t.start();

        super.onCreate(savedInstanceState);
    }
}
