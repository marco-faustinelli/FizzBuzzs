package org.faustinelli.android.fizzbuzzs.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by muzietto on 20/06/15.
 */
public class FBGames {

    public static final String TAG = "FBGames";
    public static final String FILENAME = "fbgames.json";

    private static ArrayList<FBGame> mGames;

    private static FBGames sFBGames;
    private Context mAppContext;
    private FBGameJSONSerializer mSerializer;

    private FBGames(Context appContext) {
        mAppContext = appContext;
        mSerializer = new FBGameJSONSerializer(mAppContext, FILENAME);
        try {
            mGames = mSerializer.loadGames();
            Log.d(TAG, "games loaded");
        } catch (Exception exc) {
            Log.e(TAG, "error loading games: " + exc.getMessage());
        }
    }

    public static FBGames get(Context context) {

        if (sFBGames == null) {
            sFBGames = new FBGames(context.getApplicationContext());
        }
        return sFBGames;
    }

    public ArrayList<FBGame> getGames() {
        return mGames;
    }

    public void deleteGame(FBGame game) {
        mGames.remove(game);
    }

    public void addGame(FBGame game) {
        mGames.add(game);
    }

    public boolean saveGames() {
        try {
            mSerializer.saveGames(mGames);
            Log.d(TAG, "games saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "error saving games: ", e);
            return false;
        }
    }

    public FBGame getGame(UUID gameId) {
        for (FBGame c : mGames) {
            if (c.getId().equals(gameId)) {
                return c;
            }
        }
        return null;
    }
}
