package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static SeekBar mSeekNoise;
    private static SeekBar mSeekActive;
    private static SeekBar mSeekFriendliness;
    private static SeekBar mSeekTraining;
    private static SeekBar mSeekHealth;

    private static Button nextButton;

    String noiseLevel;
    String activityLevel;
    String friendLevel;
    String trainingLevel;
    String healthLevel;

    public void seekBar(){
        mSeekNoise = (SeekBar)findViewById(R.id.seekBarNoise);
        mSeekActive = (SeekBar)findViewById(R.id.seekBarActive);
        mSeekFriendliness = (SeekBar)findViewById(R.id.seekBarFriendliness);
        mSeekTraining = (SeekBar)findViewById(R.id.seekBarTraining);
        mSeekHealth = (SeekBar)findViewById(R.id.seekBarHealthiness);

        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setElevation(0);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noiseLevel = String.valueOf(mSeekNoise.getProgress());
                activityLevel = String.valueOf(mSeekActive.getProgress());
                friendLevel = String.valueOf(mSeekFriendliness.getProgress());
                trainingLevel = String.valueOf(mSeekTraining.getProgress());
                healthLevel = String.valueOf(mSeekHealth.getProgress());

                // Store scores in shared preferences
                Context context = QuizActivity.this;
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.oauth), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("score", noiseLevel + activityLevel + friendLevel + trainingLevel + healthLevel );
                editor.commit();

                Intent intent = new Intent(QuizActivity.this, RankingActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setElevation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        seekBar();
    }
}
