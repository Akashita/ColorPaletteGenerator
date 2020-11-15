package com.nakashita.tpmobile.storage;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nakashita.tpmobile.modele.ColorBundle;
import com.nakashita.tpmobile.storage.utility.JsonFileStorage;

import org.json.JSONException;
import org.json.JSONObject;

public class ColorsJsonFileStorage extends JsonFileStorage<ColorBundle> {

    private static final String NAME = "bundle";

    private static final String BUNDLE_ID = "bundleID";
    private static final String BUNDLE_LABEL = "bundleLabel";
    private static final String COLOR_1 = "color1";
    private static final String COLOR_2 = "color2";
    private static final String COLOR_3 = "color3";

    @SuppressLint("StaticFieldLeak")
    private static ColorsJsonFileStorage STORAGE;

    private ColorsJsonFileStorage(Context context) {
        super(context, NAME);
    }

    public static ColorsJsonFileStorage get(Context context) {
        if (STORAGE == null) {
            STORAGE = new ColorsJsonFileStorage(context);
        }
        return STORAGE;
    }


    @Override
    protected JSONObject objectToJsonObject(int id, ColorBundle bun) {
        JSONObject json = new JSONObject();
        try {
            json.put(BUNDLE_ID, id);

            if(!bun.getLabel().isEmpty()){
                json.put(BUNDLE_LABEL, bun.getLabel());
            } else {
                json.put(BUNDLE_LABEL, bun.toString());
            }

            json.put(COLOR_1, bun.getColor(ColorBundle.COLOR1).getHexaString());
            json.put(COLOR_2, bun.getColor(ColorBundle.COLOR2).getHexaString());
            json.put(COLOR_3, bun.getColor(ColorBundle.COLOR3).getHexaString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected ColorBundle jsonObjectToObject(JSONObject jsonObject) {
        try {
            return new ColorBundle(jsonObject.getInt(BUNDLE_ID),
                    jsonObject.getString(BUNDLE_LABEL),
                    jsonObject.getString(COLOR_1),
                    jsonObject.getString(COLOR_2),
                    jsonObject.getString(COLOR_3));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
