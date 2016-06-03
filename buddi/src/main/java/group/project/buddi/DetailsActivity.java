package group.project.buddi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class DetailsActivity extends AppCompatActivity {

    TextView petName;
    TextView petAge;
    TextView petBreed;
    TextView petDescription;
    TextView petColor;
    TextView petGender;
    TextView petSize;
    TextView petIntakeDate;
    TextView petSpecialNeeds;
    ImageView imageNeutered;
    ImageView imageDeclawed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setElevation(0);

        petName = (TextView)findViewById(R.id.petName);
        petAge = (TextView)findViewById(R.id.petAge);
        petBreed = (TextView)findViewById(R.id.petBreed);
        petDescription = (TextView)findViewById(R.id.petDescription);
        petColor = (TextView)findViewById(R.id.petColor);
        petGender = (TextView)findViewById(R.id.petGender);
        petSize = (TextView)findViewById(R.id.petSize);
        petIntakeDate = (TextView)findViewById(R.id.petIntakeDate);
        petSpecialNeeds = (TextView)findViewById(R.id.petSpecialNeeds);
        imageNeutered = (ImageView)findViewById(R.id.imageNeutered);
        imageDeclawed = (ImageView)findViewById(R.id.imageDeclawed);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("pet_id");
            Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
            loadJSON(id);
        }

    }

    private void loadJSON(int id) {
        Context context = DetailsActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);


        Ion.with(context)
                .load("http://ec2-52-91-255-81.compute-1.amazonaws.com/api/v1/dogs/" + String.valueOf(id) + "?access_token=" + sharedPref.getString("auth_token", "broke"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject dog) {

                        petName.setText( dog.get("name").getAsString() + "\n(" + dog.get("reference_num").getAsString() + ")");
                        petAge.setText( dog.get("age").getAsString() );
                        petBreed.setText( dog.get("breed").getAsString() );
                        petDescription.setText( dog.get("description").getAsString() );
                        petColor.setText( dog.get("color").getAsString() );
                        petGender.setText( dog.get("gender").getAsString() );
                        petSize.setText( dog.get("size").getAsString() );
                        petIntakeDate.setText( dog.get("intake_date").getAsString() );
                        petSpecialNeeds.setText( dog.get("special_needs").getAsString() );
                        if ( dog.get("neutered").getAsBoolean() ) {
                            imageNeutered.setImageResource(R.mipmap.ic_check_circle_black_24dp);
                        } else {
                            imageNeutered.setImageResource(R.mipmap.ic_cancel_black_24dp);
                        }
                        if ( dog.get("declawed").getAsBoolean() ) {
                            imageDeclawed.setImageResource(R.mipmap.ic_check_circle_black_24dp);
                        } else {
                            imageDeclawed.setImageResource(R.mipmap.ic_cancel_black_24dp);
                        }

                    }
                });
    }
}
