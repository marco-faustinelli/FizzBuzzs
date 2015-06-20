package org.faustinelli.android.fizzbuzzs.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by muzietto on 20/06/15.
 */
public class FBGameJSONSerializer {
    private Context mContext;
    private String mFilename;

    public FBGameJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public void saveGames(ArrayList<FBGame> games)
            throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (FBGame g : games) {
            array.put(g.toJSON());

            Writer writer = null;
            try {
                OutputStream out = mContext
                        .openFileOutput(mFilename, Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(out);
                writer.write(array.toString());
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }

    public ArrayList<FBGame> loadGames() throws Exception {
        ArrayList<FBGame> games = new ArrayList<FBGame>();

        BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                games.add(new FBGame(array.getJSONObject(i)));
            }
        } catch (Exception exc) {
            Log.e(FBGames.TAG, exc.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return games;
    }
}
