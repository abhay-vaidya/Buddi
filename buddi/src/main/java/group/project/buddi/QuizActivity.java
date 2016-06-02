package group.project.buddi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class QuizActivity extends AppCompatActivity {

    private static SeekBar mSeekNoise;
    private static SeekBar mSeekActive;
    private static SeekBar mSeekFriendliness;
    private static SeekBar mSeekTraining;
    private static SeekBar mSeekHealth;

    private static Button nextButton;

    int noiseLevel;
    int activityLevel;
    int friendLevel;
    int trainingLevel;
    int healthLevel;

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
               noiseLevel = mSeekNoise.getProgress();
               activityLevel = mSeekActive.getProgress();
               friendLevel = mSeekFriendliness.getProgress();
               trainingLevel = mSeekTraining.getProgress();
               healthLevel = mSeekHealth.getProgress();

                Intent intent = new Intent(QuizActivity.this, HomeActivity.class);
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
