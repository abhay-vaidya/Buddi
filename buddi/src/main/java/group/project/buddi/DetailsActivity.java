package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.security.Permission;
import java.util.jar.Manifest;

import group.project.buddi.model.DatabaseAdapter;
import group.project.buddi.model.Dog;

public class DetailsActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private DatabaseAdapter dbAdapter;

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

    Button phoneButton;
    String phoneNumber;
    Button mapButton;
    String locationName;
    String latitude;
    String longitude;

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

        phoneButton = (Button)findViewById(R.id.phoneButton);
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                Context context = DetailsActivity.this;

                if ( !phoneNumber.isEmpty() && phoneNumber.matches("\\d{10}") ) {
                    intent.setData(Uri.parse("tel:" + phoneNumber));

                    int hasPermission = checkCallingOrSelfPermission("android.permission.CALL_PHONE");
                    if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                        context.startActivity(intent);
                    } else {
                        requestPermission();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Phone number is invalid.",Toast.LENGTH_LONG).show();
                }
            }
        });


        mapButton = (Button)findViewById(R.id.locationButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + locationName);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("pet_id");
            loadFromDB(id);
        } else {
            Context context = DetailsActivity.this;
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
            Toast.makeText(context, "Invalid or missing dog ID.", Toast.LENGTH_SHORT).show();
        }

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
    }

    private void loadFromDB(int id) {

        // get Instance  of Database Adapter
        dbAdapter = new DatabaseAdapter(DetailsActivity.this);
        try {
            dbAdapter.open();
        } catch (Exception e) {
            Toast.makeText(DetailsActivity.this, "Error opening database.", Toast.LENGTH_SHORT).show();
        }

        // Load all dogs from database
        Dog dog = dbAdapter.getDog(id);

        if (dog != null) {

            petName.setText(dog.getName() + "\n(" + dog.getReferenceNum() + ")");
            petAge.setText(dog.getAge() + " years old");
            petBreed.setText(dog.getBreed());

        } else {
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

                        try {
                            phoneNumber = dog.get("location").getAsJsonObject().get("phone_num").getAsString();
                            locationName = dog.get("location").getAsJsonObject().get("name").getAsString();
                            latitude = dog.get("location").getAsJsonObject().get("lat").getAsString();
                            longitude = dog.get("location").getAsJsonObject().get("long").getAsString();

                            petName.setText(dog.get("name").getAsString() + "\n(" + dog.get("reference_num").getAsString() + ")");
                            petAge.setText(dog.get("age").getAsString() + " years old");
                            petBreed.setText(dog.get("breed").getAsString());
                            petDescription.setText(dog.get("description").getAsString());
                            petColor.setText(dog.get("color").getAsString());
                            petGender.setText(dog.get("gender").getAsString());
                            petSize.setText(dog.get("size").getAsString());
                            petIntakeDate.setText(dog.get("intake_date").getAsString());
                            petSpecialNeeds.setText(dog.get("special_needs").getAsString());
                            if (dog.get("neutered").getAsBoolean()) {
                                imageNeutered.setImageResource(R.mipmap.ic_check_circle_black_24dp);
                            } else {
                                imageNeutered.setImageResource(R.mipmap.ic_cancel_black_24dp);
                            }
                            if (dog.get("declawed").getAsBoolean()) {
                                imageDeclawed.setImageResource(R.mipmap.ic_check_circle_black_24dp);
                            } else {
                                imageDeclawed.setImageResource(R.mipmap.ic_cancel_black_24dp);
                            }

                        } catch (Exception x) {
                            Context context = DetailsActivity.this;
                            Intent intent = new Intent(context, HomeActivity.class);
                            context.startActivity(intent);
                            Toast.makeText(DetailsActivity.this, "Failed to load details.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close database
        dbAdapter.close();
    }
}
