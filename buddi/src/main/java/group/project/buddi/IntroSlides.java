package group.project.buddi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Class to handle introductory slides
 *
 * @author Team Buddi
 * @version 1.0
 */
public class IntroSlides extends AppIntro {

    // DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        // Add slide fragments
        // AppIntro will automatically generate the dots indicator and buttons
        addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));

        // Override bar/separator color.
        setBarColor(Color.parseColor("#00BCD4"));
        setSeparatorColor(Color.parseColor("#80DEEA"));

        // Show Skip/Done button and progress dots
        showStatusBar(false);
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    /**
     * Method to begin login screen
     */
    private void loadLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed() {
        loadLoginActivity();
    }

    @Override
    public void onDonePressed() {
        loadLoginActivity();
    }

    @Override
    public void onSlideChanged() {
    }

    @Override
    public void onNextPressed() {
    }
}