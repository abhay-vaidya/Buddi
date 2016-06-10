package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import group.project.buddi.model.DatabaseAdapter;

/**
 * Class to handle the priority ranking screen
 *
 * @author Team Buddi
 * @version 1.0
 */
public class RankingActivity extends AppCompatActivity {

    // Initialize variables
    private List<String> mData = new ArrayList<String>();
    private AttributeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        getSupportActionBar().hide();

        // Set up RecyclerView for list of attributes
        mRecyclerView = (RecyclerView) findViewById(R.id.attributeCards);
        mRecyclerView.setHasFixedSize(true);

        // Set up layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Load existing data if it exists
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        String code = sharedPref.getString("code", "A0B0C0D0E0");
        List<String> categories = new ArrayList<String>();
        categories.add("Noise");
        categories.add("Activity");
        categories.add("Friendliness");
        categories.add("Patience");
        categories.add("Healthiness");

        for (int i = 0; i < 5; i++) {
            char a = (char) (i + 65);
            int rank = (int) (code.indexOf(a) / 2);
            mData.add(categories.get(rank));
        }

        // Bind RecyclerView to adapter
        mAdapter = new AttributeAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);

        // Set up finish button
        finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setElevation(0);

        // Finish button action when clicked
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> attributes = mAdapter.getAttributes();

                // Save ranking as alpha character
                char noiseRank = (char) (attributes.indexOf("Noise") + 65);
                char activityRank = (char) (attributes.indexOf("Activity") + 65);
                char friendRank = (char) (attributes.indexOf("Friendliness") + 65);
                char trainingRank = (char) (attributes.indexOf("Patience") + 65);
                char healthRank = (char) (attributes.indexOf("Healthiness") + 65);

                // Store in shared preferences
                Context context = RankingActivity.this;
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.oauth), Context.MODE_PRIVATE);

                String score = sharedPref.getString("score", null);

                // Generate matching code
                String code = new String();

                code += noiseRank;
                code += score.charAt(0);
                code += activityRank;
                code += score.charAt(1);
                code += friendRank;
                code += score.charAt(2);
                code += trainingRank;
                code += score.charAt(3);
                code += healthRank;
                code += score.charAt(4);

                // Store code in shared preferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("code", code);
                editor.commit();

                // Clear database
                DatabaseAdapter dbAdapter = new DatabaseAdapter(RankingActivity.this);
                dbAdapter.open();
                dbAdapter.clearDogs();
                dbAdapter.close();

                // Start home screen and end this activity
                Intent intent = new Intent(RankingActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });

        // Drag and hold to reorder logic
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.UP | ItemTouchHelper.DOWN) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                mAdapter.onItemMove(viewHolder.getAdapterPosition(),
                        target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Callback for swipe to dismiss, removing item from data and adapter
                mData.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
