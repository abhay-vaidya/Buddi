package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

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

        mRecyclerView = (RecyclerView) findViewById(R.id.attributeCards);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mData.add("Noise");
        mData.add("Activity");
        mData.add("Friendliness");
        mData.add("Patience");
        mData.add("Healthiness");

        mAdapter = new AttributeAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);


        finishButton = (Button)findViewById(R.id.finishButton);
        finishButton.setElevation(0);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> attributes = mAdapter.getAttributes();

                char noiseRank = (char)(attributes.indexOf( new String("Noise")) + 65);
                char activityRank = (char)(attributes.indexOf( new String("Activity")) + 65);
                char friendRank = (char)(attributes.indexOf( new String("Friendliness")) + 65);
                char trainingRank = (char)(attributes.indexOf( new String("Patience")) + 65);
                char healthRank = (char)(attributes.indexOf( new String("Healthiness")) + 65);

                Context context = RankingActivity.this;
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.oauth), Context.MODE_PRIVATE);

                String score = sharedPref.getString("score", null);


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


                // Store scores in shared preferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("code", code);
                editor.commit();

                Intent intent = new Intent(RankingActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });

        // init swipe to dismiss logic
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
                // callback for swipe to dismiss, removing item from data and adapter
                mData.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerView);

    }
}
