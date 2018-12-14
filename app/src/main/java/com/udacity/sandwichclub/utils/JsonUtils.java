package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JsonUtils {
    //* The format we want our API to return */
    private static final String format = "json";

    static JSONObject jsonData;
    static JSONObject name;
    static String mainName;
    static JSONArray alsoKnownAs = new JSONArray();
    static String placeOfOrigin;
    static String description;
    static String image;
    static JSONArray ingredients = new JSONArray();

    public static Sandwich parseSandwichJson(String json) {
        Sandwich mSandwich = new Sandwich();

        try {
            jsonData = new JSONObject(json);
            name = jsonData.getJSONObject("name");
            mainName = name.getString("mainName");
            alsoKnownAs = name.getJSONArray("alsoKnownAs");
            placeOfOrigin = jsonData.getString("placeOfOrigin");
            description = jsonData.getString("description");
            image = jsonData.getString("image");
            ingredients = jsonData.getJSONArray("ingredients");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> alsoKnownAsTemp = new ArrayList<>();
        List<String> ingredientsTemp = new ArrayList<>();
        if(alsoKnownAs !=null && alsoKnownAs.length()!=0) {
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                try {
                    alsoKnownAsTemp.add(alsoKnownAs.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        if(ingredients !=null && ingredients.length()!=0) {
            for (int i = 0; i < ingredients.length(); i++) {
                try {
                    ingredientsTemp.add(ingredients.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        mSandwich.setMainName(mainName);
        if(alsoKnownAsTemp !=null && !alsoKnownAsTemp.isEmpty()) {
            mSandwich.setAlsoKnownAs(alsoKnownAsTemp);
        }
        mSandwich.setPlaceOfOrigin(placeOfOrigin);
        mSandwich.setDescription(description);
        mSandwich.setImage(image);
        if(ingredientsTemp!=null && !ingredientsTemp.isEmpty()) {
            mSandwich.setIngredients(ingredientsTemp);
        }

        return mSandwich;
    }
}
