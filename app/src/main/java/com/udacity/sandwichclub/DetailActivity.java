package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView mNameTextView;
    ImageView mSandwichImageView;
    TextView mIngredientsTextView;
    TextView mAlsoKnownAsTextView;
    TextView mPlaceOfOriginTextView;
    TextView mDescriptionTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        mSandwichImageView = (ImageView)findViewById(R.id.image_iv);
        mIngredientsTextView = (TextView)findViewById(R.id.ingredients_tv);
        mAlsoKnownAsTextView = (TextView)findViewById(R.id.also_known_tv);
        mPlaceOfOriginTextView = (TextView)findViewById(R.id.origin_tv);
        mDescriptionTextView = (TextView)findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        parseSandwich(json);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    void parseSandwich(String json){
        new ParseSandwichData().execute(json);

    }

    private class ParseSandwichData extends AsyncTask<String, Void, Sandwich>{

        @Override
        protected Sandwich doInBackground(String... param) {
            if (param.length == 0) {
                return null;
            }
            try {
                Sandwich mSandwich = JsonUtils.parseSandwichJson(param[0]);
                return mSandwich;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Sandwich sandwich) {
            if(sandwich != null){
                setTitle(sandwich.getMainName());
                if(sandwich.getIngredients() != null) {
                    for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                        mIngredientsTextView.append(sandwich.getIngredients().get(i) + "\n");
                    }
                }
                if(sandwich.getAlsoKnownAs() !=null) {
                    for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                        mAlsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i) + "\n");
                    }
                }
                if(sandwich.getPlaceOfOrigin() !=null) {
                    mPlaceOfOriginTextView.append(sandwich.getPlaceOfOrigin() + "\n");
                }
                if(sandwich.getDescription() != null) {
                    mDescriptionTextView.append(sandwich.getDescription() + "\n");
                }
                if(sandwich.getImage() != null) {
                    Picasso.with(getBaseContext())
                            .load(sandwich.getImage())
                            .into(mSandwichImageView);
                }
            }else{
                closeOnError();

            }


        }
    }
    //TODO: Create a class called ParseSanwichData extending AsyncTask
    //TODO: Override doInBackground method to call the JSonUtils.java function and parse data
    //TODO: Override postExecute method to print the Sandwich details out to the screen
}
