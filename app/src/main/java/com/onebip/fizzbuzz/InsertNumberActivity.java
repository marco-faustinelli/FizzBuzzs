package com.onebip.fizzbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.faustinelli.android.fizzbuzzs.R;
import org.faustinelli.android.fizzbuzzs.model.FBGame;
import org.faustinelli.android.fizzbuzzs.model.FBGames;

import java.util.UUID;

// TODO: rename to NumberInputActivity
public class InsertNumberActivity extends Activity {

    public static final String EXTRA_RESULT = "com.onebip.fizzbuzz.MESSAGE";

    private TextView mTextView;

    private FBGame sGame;
    private String sInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_insert_number);
        mTextView = (TextView)findViewById(R.id.number);

        sGame = new FBGame();

        Intent intent = getIntent();

        UUID gameId = (UUID)intent.getSerializableExtra(FBGame.EXTRA_GAME_ID);

        if (gameId != null) {
            Log.d(FBGames.TAG, "received input from intent!!!");
            sGame = FBGames.get(getApplicationContext()).getGame(gameId);
            sInput = sGame.getNumber();
        }

        mTextView.setText(sInput);
    }


    public void calculateFizzBuzz(View view) {
        Intent intent = new Intent(this, DisplayResultActivity.class);
        EditText numberInput = (EditText) findViewById(R.id.number);
        String sNumber = numberInput.getText().toString();
        sGame.setNumber(sNumber);

        Log.d(FBGames.TAG, "=====> setting number " + sNumber);

        Number number = new Number(Integer.parseInt(sNumber));

        String message = number.fizzBuzz();
        intent.putExtra(EXTRA_RESULT, message);
        startActivity(intent);
    }

    /**
     * Added by Marco!!
     */
    @Override
    public void onPause() {
        super.onPause();
        FBGames.get(getApplicationContext()).saveGames();
    }
}
