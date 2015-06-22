package org.faustinelli.android.fizzbuzzs.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by muzietto on 20/06/15.
 */
public class FBGame {

    private static final String JSON_ID = "id";
    private static final String JSON_NUMBER = "number";
    public static final String EXTRA_GAME_ID = "org.faustinelli.android.fizzbuzzs.EXTRA_GAME_ID";

    private UUID mId;
    private String mNumber;

    public FBGame() {
        mId = UUID.randomUUID();
    }

    public FBGame(JSONObject json) {
        try {
            mId = UUID.fromString(json.getString(JSON_ID));
            mNumber = json.getString(JSON_NUMBER);
        } catch (JSONException exc) {
            Log.e(FBGames.TAG, exc.getMessage());
        }
    }

    public void setNumber(String s) {
        mNumber = s;
    }

    public UUID getId() {
        return mId;
    }

    public String getNumber() {
        return mNumber;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_NUMBER, mNumber);
        return json;
    }
}
